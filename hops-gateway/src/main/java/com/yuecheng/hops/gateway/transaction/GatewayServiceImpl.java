package com.yuecheng.hops.gateway.transaction;


import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.service.GatewayService;
import com.yuecheng.hops.gateway.GatewayConstant;
import com.yuecheng.hops.gateway.GatewayContextUtil;
import com.yuecheng.hops.gateway.service.MessageSenderResolverService;


@Service("gatewayService")
public class GatewayServiceImpl implements GatewayService
{
    private static Logger logger = LoggerFactory.getLogger(GatewayServiceImpl.class);
    @Autowired
    private MessageSenderResolverService messageSenderResolverService;
    
    private static AtomicInteger  processingRequest = new AtomicInteger();
    
    @Override
    public Map<String, Object> request(Map<String, Object> fields)
        throws HopsException
    {
        try
        {
            logger.info("begin request in processing num:"+processingRequest.addAndGet(1));
            /************* 获取ipd定义 **************/
            String interfaceType = (String)fields.get(GatewayConstant.GATEWAT_CONTEXT_KEY_INTERFACE_TYPE);
            Long merchantId = (Long)fields.get(GatewayConstant.INTERFACE_MERCHANT_ID);
            GatewayContextUtil.setParameter(GatewayConstant.GATEWAT_CONTEXT_KEY_INTERFACE_TYPE, interfaceType);
            GatewayContextUtil.setParameter(GatewayConstant.GATEWAT_CONTEXT_KEY_MERCHANT_ID, merchantId);
            Map<String, Object> result = messageSenderResolverService.execute(fields);
            logger.info("end request in processing num:"+processingRequest.addAndGet(-1));
            return result;
        }
        catch (Exception e)
        {
            logger.error("end request in processing num:"+processingRequest.addAndGet(-1));
            throw new ApplicationException("getway000002", new String[]{ExceptionUtil.getStackTraceAsString(e)}, e);
        }
        
    }

}
