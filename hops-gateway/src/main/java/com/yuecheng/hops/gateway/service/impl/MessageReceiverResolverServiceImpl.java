package com.yuecheng.hops.gateway.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.EntityConstant.MerchantResponse;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.gateway.GatewayConstant;
import com.yuecheng.hops.gateway.GatewayContextUtil;
import com.yuecheng.hops.gateway.application.InboundProcesser;
import com.yuecheng.hops.gateway.application.OutboundProcesser;
import com.yuecheng.hops.gateway.message.AbstractMessage;
import com.yuecheng.hops.gateway.message.HttpResponseMessage;
import com.yuecheng.hops.gateway.service.MessageReceiverResolverService;
import com.yuecheng.hops.gateway.transaction.TransactionRemoteService;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.injection.entity.InterfacePacketTypeConf;
import com.yuecheng.hops.injection.entity.InterfacePacketsDefinitionBo;
import com.yuecheng.hops.injection.entity.UriTransactionMapping;
import com.yuecheng.hops.injection.service.ErrorCodeService;
import com.yuecheng.hops.injection.service.InterfaceService;
import com.yuecheng.hops.injection.service.UriTransactionMappingService;

@Service("messageReceiverResolverService")
public class MessageReceiverResolverServiceImpl implements MessageReceiverResolverService {
    private static Logger logger = LoggerFactory.getLogger(MessageReceiverResolverServiceImpl.class);
    
    @Autowired
    private InboundProcesser                     inboundProcesser;

    @Autowired
    private OutboundProcesser                    outboundProcesser;

    @Autowired
    private ErrorCodeService                     errorCodeService;

    @Autowired
    private TransactionRemoteService             transactionRemoteService;

    @Autowired
    private UriTransactionMappingService uriTransactionMappingService;
    
    @Autowired
    private InterfaceService interfaceService;
    
    @Autowired
    private MerchantService merchantService;
    
    private static AtomicInteger caller = new AtomicInteger();
    
    @Override
    public AbstractMessage recieve(AbstractMessage requestMessage)
    {
        logger.info("Begin with request messge:"+String.valueOf(requestMessage).toString());
        
        String result = Constant.Interface.DEFAULT_ERROR_MSG;
        String encoding = Constant.Common.DEFAULT_ENCODING;
        String packetType = Constant.Interface.PACKET_TYPE_XML;
        String interfaceType = StringUtil.initString();
        Map<String,Object> responseFileds = new HashMap<String, Object>();
        boolean responseIsPlan=true;
        //预下单接口，当用户为空或者签名验证失败时，默认状态为 ：2 失败
       // responseFileds.put("status","2");
        try {
            String uri = (String)GatewayContextUtil.getParameter(GatewayConstant.GATEWAT_CONTEXT_KEY_URI);
            UriTransactionMapping uriTransactionMapping =  uriTransactionMappingService.getMappingByUri(uri);
            
            Assert.notNull(uriTransactionMapping);
            interfaceType = uriTransactionMapping.getInterfaceType();
            
            InterfacePacketsDefinitionBo ipd = interfaceService.getInterfacePacketsDefinitionByParams(uriTransactionMapping.getMerchantId(), interfaceType);
            if(BeanUtils.isNotNull(ipd))
            {
                InterfacePacketTypeConf interfacePacketTypeConf= ipd.getResponseInterfacePacketTypeConf();
                if(BeanUtils.isNotNull(interfacePacketTypeConf)&&interfacePacketTypeConf.getPacketType().equals(Constant.Interface.PACKET_TYPE_XML))
                {
                    responseIsPlan=false;
                }
            }
            String requestStr = requestMessage.getMessage();
            logger.debug("Resolve the Interface Type:["+String.valueOf(interfaceType).toString()+"request String:"+String.valueOf(requestStr).toString());
            GatewayContextUtil.setParameter(GatewayConstant.GATEWAT_CONTEXT_KEY_INTERFACE_TYPE, interfaceType);
            GatewayContextUtil.setParameter(GatewayConstant.TRANSACTION_CODE, uriTransactionMapping.getTransactionCode());
            GatewayContextUtil.setParameter(GatewayConstant.INTERFACE_PACKETS_DEFINITION, ipd);
            GatewayContextUtil.setParameter(GatewayConstant.INTERFACE_MERCHANT_ID, uriTransactionMapping.getMerchantId());
            
            Map<String,Object> requestFileds = inboundProcesser.resoleMessage(requestStr,Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST);
			Long interfaceMerchantId = BeanUtils.isNotNull(uriTransactionMapping.getMerchantId()) ? uriTransactionMapping
					.getMerchantId() : -1l;

			int businessType = BeanUtils.isNotNull(uriTransactionMapping.getBusinessType()) ? uriTransactionMapping
					.getBusinessType() : -1;
					
			int specialDown = BeanUtils.isNotNull(uriTransactionMapping.getSpecialDown()) ? uriTransactionMapping
					.getSpecialDown() : -1;
					

			requestFileds.put(GatewayConstant.INTERFACE_MERCHANT_ID, interfaceMerchantId);
			requestFileds.put(GatewayConstant.REQUESTSTR, requestStr);
			requestFileds.put(MerchantResponse.DELIVERY_RESULT, requestStr);
			if (BeanUtils.isNull(requestFileds.get(GatewayConstant.MERCHANT_ID)) && interfaceMerchantId != -1l)
			{
				requestFileds.put(GatewayConstant.MERCHANT_ID, interfaceMerchantId);
			}
			if (BeanUtils.isNull(requestFileds.get(GatewayConstant.BUSINESS_TYPE)) && businessType != -1)
			{
				requestFileds.put(GatewayConstant.BUSINESS_TYPE, businessType);
			}
			if (BeanUtils.isNull(requestFileds.get(GatewayConstant.SPECIAL_DOWN)) && specialDown != -1)
			{
				requestFileds.put(GatewayConstant.SPECIAL_DOWN, specialDown);
			}
			
            logger.info("Call transaction:"+ System.currentTimeMillis()+",caller:"+caller.addAndGet(1)+" with request:"+String.valueOf(requestFileds).toString());
            responseFileds = transactionRemoteService.execute(requestFileds);
            logger.info("Call transaction:"+ System.currentTimeMillis()+",caller:"+caller.addAndGet(-1)+" with result:"+String.valueOf(responseFileds).toString());
        } catch (HopsException e) {
            logger.error("Catch a Exception with:"+String.valueOf(ExceptionUtil.getStackTraceAsString(e)).toString());
            responseFileds.put(EntityConstant.Order.RESULT, Constant.TrueOrFalse.FALSE);
            responseFileds.put(EntityConstant.Order.ERROR_CODE, e.getCode());
            responseFileds.put(EntityConstant.Order.MSG, errorCodeService.getErrorCode(e.getCode()));
            logger.info("Call transaction:"+ System.currentTimeMillis()+",caller:"+caller.addAndGet(-1)+" with result:"+String.valueOf(responseFileds).toString());
        } catch(Exception e){
            logger.error("Catch a Exception with:"+String.valueOf(ExceptionUtil.getStackTraceAsString(e)).toString());
            responseFileds.put(EntityConstant.Order.RESULT, Constant.TrueOrFalse.FALSE);
            responseFileds.put(EntityConstant.Order.ERROR_CODE, Constant.ErrorCode.MANUAL);
            responseFileds.put(EntityConstant.Order.MSG, errorCodeService.getErrorCode(Constant.ErrorCode.MANUAL));
            logger.info("Call transaction:"+ System.currentTimeMillis()+",caller:"+caller.addAndGet(-1)+" with result:"+String.valueOf(responseFileds).toString());
        }

        try
        {
        	InterfacePacketsDefinitionBo ipd = (InterfacePacketsDefinitionBo)GatewayContextUtil.getParameter(GatewayConstant.INTERFACE_PACKETS_DEFINITION);
            if(Constant.Interface.INTERFACE_TYPE_SUPPLY_NOTIFY_ORDER.equalsIgnoreCase(interfaceType) && 
            		Constant.Interface.PACKET_TYPE_TEXT.equalsIgnoreCase(ipd.getResponseInterfacePacketTypeConf().getPacketType()))
            {
            	if (BeanUtils.isNotNull(responseFileds))
            	{
            		result = outboundProcesser.resoleMessage(responseFileds,Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE);
                	String[] results = result.split("=");
                	if (results.length > 1)
                	{
                		result = results[1];
                	}
                	else
                	{
                		result = Constant.Common.SUCCESS.toUpperCase();
                	}
            	}
            	else
            	{
            		result = Constant.Common.SUCCESS.toUpperCase();
            	}
            	
            }
            else if(Constant.Interface.INTERFACE_TYPE_SUPPLY_NOTIFY_ORDER_FLOW.equalsIgnoreCase(interfaceType))
            {
				if (responseIsPlan)
				{
					result = outboundProcesser.resoleMessage(responseFileds,
							Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE);
					String[] results = result.split("=");
					if (results.length > 1)
					{
						result = results[1];
					}
					else
					{
						result = Constant.Common.SUCCESS;
					}
				}
				else
                {
                    result = outboundProcesser.resoleMessage(responseFileds,Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE);
                }
            }
            else
            {
                result = outboundProcesser.resoleMessage(responseFileds,Constant.Interface.INTERFACE_CONNECTION_MODULE_RESPONSE);
            }
        }
        catch (Exception e)
        {
            logger.error("Catch a Exception with:"+String.valueOf(ExceptionUtil.getStackTraceAsString(e)).toString());
        }
        
        encoding = (String)GatewayContextUtil.getParameter("encoding");
        packetType = (String)GatewayContextUtil.getParameter("packetType");
        
        HttpResponseMessage resultMessage = new HttpResponseMessage();
        resultMessage.setContentType(packetType==null?Constant.Interface.PACKET_TYPE_XML:packetType);
        
        if (Constant.Interface.INTERFACE_TYPE_RECIEVER_TBORDER.equalsIgnoreCase(interfaceType)
        		||Constant.Interface.INTERFACE_TYPE_AGENT_QUERY_TBORDER.equalsIgnoreCase(interfaceType))
        {
        	resultMessage.setMessage(result==null?Constant.Interface.DEFAULT_ERROR_MSG_TB:result);
        	resultMessage.setEncoding(Constant.Common.DEFAULT_ENCODING);
        }
        else if(Constant.Interface.INTERFACE_TYPE_SUPPLY_NOTIFY_ORDER_FLOW.equalsIgnoreCase(interfaceType))
        {
            if(null==responseFileds||responseFileds.size()==0)
            {
                result=Constant.Interface.DEFAULT_ERROR_MSG_YS;
                resultMessage.setContentType(Constant.Interface.PACKET_TYPE_XML);
            }
            resultMessage.setMessage(result==null?Constant.Interface.DEFAULT_ERROR_MSG_YS:result);
            resultMessage.setEncoding(encoding==null?Constant.Common.DEFAULT_ENCODING:encoding);
        }
        else
        {
        	resultMessage.setMessage(result==null?Constant.Interface.DEFAULT_ERROR_MSG:result);
        	resultMessage.setEncoding(encoding==null?Constant.Common.DEFAULT_ENCODING:encoding);
        }
        logger.info("End with response messge:"+String.valueOf(resultMessage).toString());
        return resultMessage;
    }
}