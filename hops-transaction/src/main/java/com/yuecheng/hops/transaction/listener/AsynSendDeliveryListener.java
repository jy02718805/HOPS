package com.yuecheng.hops.transaction.listener;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.yuecheng.hops.account.entity.role.IdentityAccountRole;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.transaction.TransactionContextUtil;
import com.yuecheng.hops.transaction.TransactionMapKey;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.event.AsynSendDeliveryEvent;
import com.yuecheng.hops.transaction.execution.order.action.SendOrderAction;
import com.yuecheng.hops.transaction.execution.order.director.SendOrderResponseSelector;
import com.yuecheng.hops.transaction.service.action.AbstractEventAction;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;


@Component("asynSendDeliveryListener")
public class AsynSendDeliveryListener implements ApplicationListener<AsynSendDeliveryEvent>
{
    private static Logger                logger = LoggerFactory.getLogger(AsynSendDeliveryListener.class);

    @Autowired@Qualifier("gatewayAction")
    private AbstractEventAction gatewayAction;
    
    @Autowired
    private SendOrderResponseSelector    sendOrderResponseSelector;
    
    @Autowired@Qualifier("sendOrderUnKnowAction")
    private SendOrderAction        sendOrderUnKnowAction;

    @Override
    @Async
    public void onApplicationEvent(AsynSendDeliveryEvent event)
    {
        Delivery delivery = event.getDelivery();
        Map<String,Object> responseMap = null;
        try
        {
            logger.debug("begin to send delivery["+String.valueOf(delivery).toString()+"]");
            BigDecimal frozenAmt = event.getFrozenAmt();
            IdentityAccountRole payee = event.getPayee();
            Map<String,Object> fileds = event.getRequestMap();
            TransactionContextUtil.init();
            TransactionContextUtil.setTransactionContext(TransactionMapKey.FROZEN_AMT, frozenAmt);
            TransactionContextUtil.setTransactionContext(TransactionMapKey.SYSTEM_DEBIT_ACCOUNT, payee);
            
            ActionContextUtil.setActionContext(ActionMapKey.REQUEST_MAP, fileds);
            gatewayAction.handleRequest();
            responseMap = (Map<String,Object>)ActionContextUtil.getActionContextParam(ActionMapKey.RESPONSE_MAP);
            responseMap = BeanUtils.isNotNull(responseMap)?responseMap:new HashMap<String, Object>();
            String interfaceType=(String)fileds.get(TransactionMapKey.INTERFACE_TYPE);
            responseMap.put(TransactionMapKey.INTERFACE_TYPE, interfaceType);
            TransactionContextUtil.setTransactionContext(TransactionMapKey.RESPONSE_MAP, responseMap);
            SendOrderAction action = sendOrderResponseSelector.select(delivery, responseMap);
            delivery = (Delivery)ActionContextUtil.getActionContextParam(TransactionMapKey.DELIVERY);
            logger.debug("action:["+action+"]  delivery:"+String.valueOf(delivery).toString());
            action.doAction(delivery, responseMap);
        }
        catch (Exception e)
        {
            logger.error("订单发送流程：发生异常,异常信息e:[" + ExceptionUtil.getStackTraceAsString(e) + "]   发起回滚交易!");
            //订单没有发送
            responseMap = BeanUtils.isNotNull(TransactionContextUtil.getTransactionContextParam(TransactionMapKey.RESPONSE_MAP))?responseMap:new HashMap<String, Object>();
            sendOrderUnKnowAction.doAction(delivery, responseMap);
        }
    }
}
