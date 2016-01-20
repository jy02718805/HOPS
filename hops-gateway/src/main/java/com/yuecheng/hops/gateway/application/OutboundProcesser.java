/*
 * 文件名：OutboundProcesser.java 版权：Copyright by www.365haoyou.com 描述： 修改人：yangyi 修改时间：2014年10月14日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.gateway.application;


import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.gateway.GatewayConstant;
import com.yuecheng.hops.gateway.GatewayContextUtil;
import com.yuecheng.hops.gateway.application.protocal.ProtocalCombiner;
import com.yuecheng.hops.gateway.application.protocal.combiner.InterfaceParamFilter;
import com.yuecheng.hops.gateway.application.protocal.selector.ProtocalCombinerSelector;
import com.yuecheng.hops.gateway.application.tranfer.BeanEncoderTransfer;
import com.yuecheng.hops.gateway.security.authentication.Verification;
import com.yuecheng.hops.gateway.security.authentication.VerificationComponent;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.injection.entity.InterfacePacketTypeConf;
import com.yuecheng.hops.injection.entity.InterfacePacketsDefinitionBo;
import com.yuecheng.hops.injection.entity.InterfaceParam;
import com.yuecheng.hops.injection.service.InterfaceParamService;
import com.yuecheng.hops.injection.service.ResponseCodeTranslationService;

@Service("outboundProcesser")
public class OutboundProcesser
{
    private static Logger logger = LoggerFactory.getLogger(OutboundProcesser.class);
    
    @Autowired
    private ResponseCodeTranslationService responseCodeTranslationService;
    
    @Autowired 
    private ProtocalCombinerSelector messageEncoderSelector;
    
    @Autowired 
    private BeanEncoderTransfer beanEncoderTransfer;
    
    @Autowired
    private MerchantService merchantService;
    
    @Autowired
    private InterfaceParamService interfaceParamService;
    
    @Autowired 
    private Verification verification;
    
    private static final Set<String> interfacePacketsDefinitionSet = new HashSet<String>();
    
    static{
        interfacePacketsDefinitionSet.add(Constant.Interface.INTERFACE_TYPE_RECIEVER_ORDER);
        interfacePacketsDefinitionSet.add(Constant.Interface.INTERFACE_TYPE_RECIEVER_TBORDER);
        interfacePacketsDefinitionSet.add(Constant.Interface.INTERFACE_TYPE_AGENT_QUERY_ORDER);
        interfacePacketsDefinitionSet.add(Constant.Interface.INTERFACE_TYPE_AGENT_QUERY_TBORDER);
        interfacePacketsDefinitionSet.add(Constant.Interface.INTERFACE_TYPE_AGENT_PRODUCT_STATUS);
    }
    

    public String resoleMessage(Map<String,Object> fields,String direction)
    {
        try {
            InterfacePacketsDefinitionBo ipd = (InterfacePacketsDefinitionBo)GatewayContextUtil.getParameter(GatewayConstant.INTERFACE_PACKETS_DEFINITION);
            GatewayContextUtil.setParameter(GatewayConstant.INTERFACE,ipd.getInterfaceType());
            Merchant merchant = (Merchant)GatewayContextUtil.getParameter(GatewayConstant.MERCHANT);
            Long interfaceMerchantId=(Long)GatewayContextUtil.getParameter(GatewayConstant.INTERFACE_MERCHANT_ID);
            if(BeanUtils.isNull(merchant) && BeanUtils.isNotNull(interfaceMerchantId))
            {
                merchant=merchantService.queryMerchantById(interfaceMerchantId);
            }
            if(BeanUtils.isNull(merchant))
            {
                merchant = (Merchant)merchantService.queryMerchantByMerchantCode((String)GatewayContextUtil.getParameter(EntityConstant.Merchant.MERCHANT_CODE));
            }
            
            String sendStr = StringUtil.initString();
            
            //获取接口定义，并组装数据。
            List<InterfaceParam> interfaceParams = null;
            InterfacePacketTypeConf packetTypeConf = null;
            if(Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST.equalsIgnoreCase(direction))
            {
                interfaceParams = ipd.getRequestParams();
                packetTypeConf = ipd.getRequestInterfacePacketTypeConf();
            }
            else
            {
                String errorCode = (String)fields.get(Constant.TransactionCode.ERROR_CODE);
                if (Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_TBORDER.equals(ipd.getInterfaceType()))
                {
                	fields.put(Constant.TransactionCode.RESULT, Constant.TrueOrFalse.TRUE);
                	interfaceParams = ipd.getResponseSuccessParams();
                }
                else if(Constant.Interface.INTERFACE_TYPE_SUPPLY_NOTIFY_ORDER.equals(ipd.getInterfaceType()))
                {
                	fields.put(Constant.TransactionCode.RESULT, Constant.TrueOrFalse.TRUE);
                	interfaceParams = ipd.getResponseSuccessParams();
                }
                else if(Constant.Interface.INTERFACE_TYPE_SUPPLY_NOTIFY_ORDER_FLOW.equals(ipd.getInterfaceType()))
                {
                    if(StringUtil.isNullOrEmpty(errorCode))
                    {
                        interfaceParams = ipd.getResponseSuccessParams();
                    }else
                    {
                        interfaceParams = ipd.getResponseFailParams();
                    }
                }
                else if(Constant.ErrorCode.SUCCESS.equalsIgnoreCase(errorCode) || Constant.ErrorCode.RECHARGE_SUCCESS.equalsIgnoreCase(errorCode) || Constant.ErrorCode.RECHARGE_FAIL.equalsIgnoreCase(errorCode)
                		|| Constant.ErrorCode.REFUND_PROCESS.equalsIgnoreCase(errorCode))
                {
                    fields.put(Constant.TransactionCode.RESULT, Constant.TrueOrFalse.TRUE);
                    interfaceParams = ipd.getResponseSuccessParams();
                }
                else if(Constant.ErrorCode.WAITING_RECHARGE.equalsIgnoreCase(errorCode))
                {
                	fields.put(Constant.TransactionCode.RESULT, Constant.TrueOrFalse.TRUE);
                	interfaceParams = ipd.getResponseUnderwayParams();
                }
                else
                {
                    fields.put(Constant.TransactionCode.RESULT, Constant.TrueOrFalse.FALSE);
                    if (null == fields.get("status"))
                    {
                    	//预下单接口，当用户为空或者签名验证失败时，默认状态为 ：2 失败
                    	fields.put("status", "2");
                    }
                    interfaceParams = ipd.getResponseFailParams();
                }
                packetTypeConf = ipd.getResponseInterfacePacketTypeConf();
            }
            
            //将系统返回码翻译成第三方系统返回码
            fields = responseCodeTranslationService.translationMapToResponse(ipd.getInterfaceType(),fields);
            logger.debug("将系统返回码翻译成第三方系统返回码  request_fields=["+fields+"]");
            //把 不存在于报文体内部的interfaceParam过滤掉
            //1.Map(OrderNo) -> Map(oid)
            Long merchantId = BeanUtils.isNull(merchant)?-1l:merchant.getId();
            String merchantCode = BeanUtils.isNull(merchant)?StringUtil.initString():merchant.getMerchantCode().getCode();
            Map<String,Object> fieldsMap = beanEncoderTransfer.encode(fields, interfaceParams, merchantId);
            
            //淘宝通知接口,对请求参数已经加密字段进行处理
            if (null != ipd.getInterfaceType() && 
            		ipd.getInterfaceType().equals(Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_TBORDER))
            {
            	if (((String)fieldsMap.get(Constant.TaoBaoNotify.TBORDERSTATUS)).equals(Constant.TaoBaoNotify.SUCCESS))
            	{
            		for (InterfaceParam i:interfaceParams)
            		{
            			if (i.getOutParamName().equals(Constant.TaoBaoNotify.TBFAILEDCODE) || i.getOutParamName().equals(Constant.TaoBaoNotify.TBFAILEDREASON))
            			{
            				i.setInBody(Constant.TrueOrFalse.FALSE);
            			}
            			if (i.getOutParamName().equals(Constant.TaoBaoNotify.SIGN))
            			{
            				i.setEncryptionParamNames(i.getEncryptionParamNames()
            						.replace("("+Constant.TaoBaoNotify.TBFAILEDCODE+")","")
            						.replace(Constant.TaoBaoNotify.TBFAILEDCODE,"")
            						.replace("("+Constant.TaoBaoNotify.TBFAILEDREASON+")","")
            						.replace(Constant.TaoBaoNotify.TBFAILEDREASON,""));
            			}
            		}
            		fieldsMap.remove(Constant.TaoBaoNotify.TBFAILEDCODE);
            		fieldsMap.remove(Constant.TaoBaoNotify.TBFAILEDREASON);
            	}
            	if (((String)fieldsMap.get(Constant.TaoBaoNotify.TBORDERSTATUS)).equals(Constant.TaoBaoNotify.FAILED))
            	{
            		for (InterfaceParam i:interfaceParams)
            		{
            			if (i.getOutParamName().equals(Constant.TaoBaoNotify.TBCOOPORDERSNAP) || i.getOutParamName().equals(Constant.TaoBaoNotify.TBCOOPORDERSUCCESSTIME))
            			{
            				i.setInBody(Constant.TrueOrFalse.FALSE);
            			}
            			if (i.getOutParamName().equals(Constant.TaoBaoNotify.SIGN))
            			{
            				i.setEncryptionParamNames(i.getEncryptionParamNames()
            						.replace("("+Constant.TaoBaoNotify.TBCOOPORDERSNAP+")","")
            						.replace(Constant.TaoBaoNotify.TBCOOPORDERSNAP,"")
            						.replace("("+Constant.TaoBaoNotify.TBCOOPORDERSUCCESSTIME+")","")
            						.replace(Constant.TaoBaoNotify.TBCOOPORDERSUCCESSTIME,""));
            			}
            		}
            		fieldsMap.put(Constant.TaoBaoNotify.TBFAILEDCODE, Constant.TaoBaoNotify.TBERRORCODE);
            		fieldsMap.put(Constant.TaoBaoNotify.TBFAILEDREASON, Constant.TaoBaoNotify.TBCLOSERENSON);
            		fieldsMap.remove(Constant.TaoBaoNotify.TBCOOPORDERSNAP);
            		fieldsMap.remove(Constant.TaoBaoNotify.TBCOOPORDERSUCCESSTIME);
            	}
            }
            
            Map<String,Object> keySignMap = new HashMap<String, Object>();
            if(BeanUtils.isNotNull(merchant)){
                //2.Map(oid)->addKey
                keySignMap = verification.signDecode(fieldsMap, merchantCode ,ipd.getInterfaceType());
                //3.Map(oid) addSign
                keySignMap = VerificationComponent.encoderSign(interfaceParams, keySignMap,ipd.getEncoding());
            }else{
                keySignMap.putAll(fieldsMap);
            }
            //4.过滤不在报文体内的参数
            interfaceParams = InterfaceParamFilter.execute(interfaceParams);
            //5.Map(oid)->responseStr
            ProtocalCombiner messageEncoder = messageEncoderSelector.select(packetTypeConf.getPacketType());
            sendStr = messageEncoder.encoder(interfaceParams, keySignMap, ipd.getEncoding());
            
            GatewayContextUtil.setParameter("encoding",ipd.getEncoding());
            GatewayContextUtil.setParameter("packetType",packetTypeConf.getPacketType());
            GatewayContextUtil.setParameter("ipd", ipd);
            return sendStr;
        } catch (Exception e) {
            logger.error("fieldsMap="+fields+"fail to resoleMessage ,caused by " + ExceptionUtil.getStackTraceAsString(e));
            throw new ApplicationException(Constant.ErrorCode.MANUAL, e);
        }
    }

}