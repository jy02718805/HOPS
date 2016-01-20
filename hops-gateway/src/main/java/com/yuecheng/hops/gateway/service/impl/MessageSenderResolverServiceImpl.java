package com.yuecheng.hops.gateway.service.impl;


import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.EntityConstant.MerchantResponse;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.DateUtil;
import com.yuecheng.hops.common.utils.PrintUtil;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.common.utils.XmlUtils;
import com.yuecheng.hops.gateway.GatewayConstant;
import com.yuecheng.hops.gateway.GatewayContextUtil;
import com.yuecheng.hops.gateway.application.InboundProcesser;
import com.yuecheng.hops.gateway.application.OutboundProcesser;
import com.yuecheng.hops.gateway.message.AbstractMessage;
import com.yuecheng.hops.gateway.message.MessageBuilder;
import com.yuecheng.hops.gateway.message.MessageSenderDirector;
import com.yuecheng.hops.gateway.message.MessageSenderFulfillment;
import com.yuecheng.hops.gateway.service.MessageSenderResolverService;
import com.yuecheng.hops.gateway.webservice.WebserviceSend;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.injection.entity.InterfacePacketsDefinitionBo;
import com.yuecheng.hops.injection.service.InterfacePacketTypeConfService;
import com.yuecheng.hops.injection.service.InterfaceService;

@Service("messageSenderResolverService")
public class MessageSenderResolverServiceImpl implements MessageSenderResolverService
{
    private static Logger logger = LoggerFactory.getLogger(MessageSenderResolverServiceImpl.class);

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private InterfaceService interfaceService;

    @Autowired
    private InterfacePacketTypeConfService interfacePacketTypeConfService;

    @Autowired
    InboundProcesser inboundProcesser;

    @Autowired
    OutboundProcesser outboundProcesser;

    @Autowired
    private MessageBuilder messageBuilder;

    @Autowired
    private MessageSenderDirector messageSenderDirector;

    @Autowired
    private MessageSenderFulfillment messageSenderFulfillment;
    
    @Autowired
    private WebserviceSend webserviceSend;

    @Override
    public Map<String, Object> execute(Map<String, Object> fileds)
    {
        try
        {
            Long merchantId = (Long)GatewayContextUtil.getParameter(GatewayConstant.GATEWAT_CONTEXT_KEY_MERCHANT_ID);
            String interfaceType = (String)GatewayContextUtil.getParameter(GatewayConstant.GATEWAT_CONTEXT_KEY_INTERFACE_TYPE);
            Merchant merchant = merchantService.queryMerchantById(merchantId);
            Assert.notNull(merchant);
            InterfacePacketsDefinitionBo ipd = interfaceService.getInterfacePacketsDefinitionByParams(merchantId, interfaceType);
            
            String sendMesg = null;
            
            GatewayContextUtil.setParameter(GatewayConstant.INTERFACE_PACKETS_DEFINITION, ipd);
            GatewayContextUtil.setParameter(GatewayConstant.MERCHANT, merchant);
            fileds.put("merchantCode", merchant.getMerchantCode().getCode());
			if (String.valueOf(fileds.get(GatewayConstant.SPECIAL_DOWN)).equals(
					String.valueOf(Constant.SpecialDown.PAIPAI)))
			{
				String orderNo = String.valueOf(fileds.get("orderNo"));
				if (orderNo.length() > 8)
				{
					orderNo = orderNo.substring(orderNo.length() - 8);
				}
				else if (orderNo.length() < 8)
				{
					String tempStr = "00000000";
					orderNo = tempStr.substring(orderNo.length()) + orderNo;
				}
				String spRefundId = DateUtil.getDate("yyyyMMdd") + orderNo;
				fileds.put("spRefundId", spRefundId);

				int randomValue = new Random().nextInt();
				fileds.put("randomValue", Math.abs(randomValue));
			}
			
            sendMesg = outboundProcesser.resoleMessage(fileds, Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST);

            
            String url = (String)fileds.get(EntityConstant.Notify.NOTIFY_URL);
            if (StringUtil.isBlank(url))
            {
                url = ipd.getRequestUrl();
            }
            logger.info("httpRequest:url[" + url + "] sendStr[" + sendMesg + "]");
            String responseStr = "";
            if ("webservice".equalsIgnoreCase(ipd.getConnectionType()))
            {
            	StringBuffer sb = new StringBuffer();
            	Document dd = DocumentHelper.parseText(sendMesg); 
            	org.dom4j.Element st = dd.getRootElement() ;
            	org.dom4j.Element s = st.element("CTRL-INFO");
            	String WEBSVRCODE = s.attributeValue("WEBSVRCODE");
            	String APPFROM = s.attributeValue("APPFROM");
				sb.append(WEBSVRCODE).append("|").append(APPFROM).append("|").append("V1.0|").append(url).append("|"); 
				logger.info("webservice requestr[" +sb.toString()+"================"+ sendMesg + "]");
				responseStr = webserviceSend.send(sb.toString(),sendMesg);
            }
            
            else
            {
            	AbstractMessage message = messageBuilder.builder(url, sendMesg, ipd, interfaceType);
                AbstractMessage response = messageSenderFulfillment.doSend(message);
                Assert.notNull(response);
                responseStr = response.getMessage();
            }
			
            
            
            if (StringUtils.isNotEmpty(responseStr) 
            		&& Constant.Interface.PACKET_TYPE_TEXT.equalsIgnoreCase(ipd.getResponseInterfacePacketTypeConf().getPacketType())
            			&&!responseStr.contains("=")
            				&& !interfaceType.contains("notify"))
            {
            	//说明返回的是000000|agent00001|order123123|151*******|49.5|3|220aae48a8efbd74e8420a787a9c07c1类字符串
            	String[] strArr = responseStr.split("\\|");
            	if (null != strArr & strArr.length > 1)
            	{
            		responseStr = "returnCode="+strArr[0]+"&merchantStatus="+strArr[5];
            	}
            	else
            	{
            		responseStr = "returnCode="+strArr[0];
            	}
            }
            logger.info("responseStr[" + responseStr + "]");
            
            GatewayContextUtil.setParameter(EntityConstant.Merchant.MERCHANT_CODE,
                merchant.getMerchantCode().getCode());

            Map<String, Object> responseMap = inboundProcesser.resoleMessage(responseStr,
                Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE);
            
            responseMap.put(MerchantResponse.DELIVERY_RESULT, responseStr);
          //如果是淘宝通知接口，responseStr需要用 T或者F替换
            if (null != interfaceType && interfaceType.equals(Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_TBORDER))
            {
            	Document doc = null;
                try {
    				doc = DocumentHelper.parseText(responseStr);
    			} catch (DocumentException e) {
    				e.printStackTrace();
    			} 
                Map<String,Object> mapfortb = XmlUtils.Dom2Map(doc);
                responseMap.put(EntityConstant.MerchantResponse.RESPONSE_STR, mapfortb.get(Constant.Common.TBORDERSUCCESS));
            }
			// 如果是拍拍通知接口，如果返回码为0表示请求成功
			else if (Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_ORDER_FAIL_PAIPAI.equals(interfaceType)
					|| Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_ORDER_SUCCESS_PAIPAI.equals(interfaceType))
			{
				Document doc = null;
				try
				{
					doc = DocumentHelper.parseText(responseStr);
				}
				catch (DocumentException e)
				{
					e.printStackTrace();
				}
				Map<String, Object> mapfortb = XmlUtils.Dom2Map(doc);
				
				//如果返回码为0表示请求成功
				if("0".equals(String.valueOf(mapfortb.get(EntityConstant.ResponseCodeTranslation.ERROR_CODE))))
				{
					responseMap.put(EntityConstant.MerchantResponse.RESPONSE_STR, Constant.Common.SUCCESS);
				}
				else
	            {
	            	responseMap.put(EntityConstant.MerchantResponse.RESPONSE_STR, responseStr);
	            }
			}
			else
            {
            	responseMap.put(EntityConstant.MerchantResponse.RESPONSE_STR, responseStr);
            }
            
            
            logger.info("responseMap[" + PrintUtil.mapToString(responseMap) + "]");
            return responseMap;
        }
        catch (Exception e)
        {
            logger.error(ExceptionUtil.getStackTraceAsString(e));
            throw ExceptionUtil.throwException(new ApplicationException("gateway00007", new String[] {e.toString()}));
        }
    }
}
