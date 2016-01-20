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

import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.PrintUtil;
import com.yuecheng.hops.transaction.TransactionContextUtil;
import com.yuecheng.hops.transaction.TransactionMapKey;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.event.AsynQueryDeliveryEvent;
import com.yuecheng.hops.transaction.execution.order.action.QueryDeliveryAction;
import com.yuecheng.hops.transaction.execution.order.director.QueryOrderResponseSelector;
import com.yuecheng.hops.transaction.mq.producer.DeliveryQueryProducerService;
import com.yuecheng.hops.transaction.service.action.AbstractEventAction;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.action.query.QueryStatusNeedQueryAction;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;
import com.yuecheng.hops.transaction.service.order.OrderManagement;
import com.yuecheng.hops.transaction.service.order.builder.support.SupplyOrderKeyInitialization;
import com.yuecheng.hops.transaction.service.process.QueryStartUpProcess;
import com.yuecheng.hops.transaction.service.process.SupplyAmtLowProcess;


@Component("asynQueryDeliveryListener")
public class AsynQueryDeliveryListener implements ApplicationListener<AsynQueryDeliveryEvent>
{
    private static Logger                logger = LoggerFactory.getLogger(AsynQueryDeliveryListener.class);

    @Autowired@Qualifier("gatewayAction")
    private AbstractEventAction gatewayAction;
    
    @Autowired
    private SupplyAmtLowProcess supplyAmtLowProcess;

    @Autowired
    private QueryOrderResponseSelector     queryOrderResponseSelector;
    
    @Autowired
    private OrderManagement orderManagement;

    @Autowired
    private QueryStartUpProcess queryStartUpProcess;
    
    @Autowired
    private DeliveryQueryProducerService deliveryQueryProducerService;
    
    @Autowired
    private DeliveryManagement deliveryManagement;
    
    @Autowired
    private SupplyOrderKeyInitialization supplyOrderKeyInitialization;
    
    @Override
    @Async
    public void onApplicationEvent(AsynQueryDeliveryEvent event)
    {
        Map<String,Object> fileds = event.getRequestMap();
        Delivery delivery = event.getDelivery();
        TransactionContextUtil.setTransactionContext(TransactionMapKey.DELIVERY, delivery);
        Map<String,Object> responseMap = null;
        logger.debug("begin to query delivery["+String.valueOf(delivery).toString()+"]");
        ActionContextUtil.init();
        ActionContextUtil.setActionContext(ActionMapKey.REQUEST_MAP, fileds);
        try
        {
            gatewayAction.handleRequest();
            responseMap = (Map<String,Object>)ActionContextUtil.getActionContextParam(ActionMapKey.RESPONSE_MAP);
            responseMap = BeanUtils.isNotNull(responseMap)?responseMap:new HashMap<String, Object>();
            String interfaceType=(String)fileds.get(TransactionMapKey.INTERFACE_TYPE);
            responseMap.put(TransactionMapKey.INTERFACE_TYPE, interfaceType);
            
            QueryDeliveryAction action = queryOrderResponseSelector.select(delivery,responseMap);
            if(BeanUtils.isNotNull(action)){
                if(BeanUtils.isNotNull(responseMap))
                {
                    String supplyMerchantOrderNo = (String)responseMap.get(EntityConstant.MerchantResponse.SUPPLY_MERCHANT_ORDER_NO);
                    if (null != supplyMerchantOrderNo)
                    {
                        delivery.setSupplyMerchantOrderNo(supplyMerchantOrderNo);
                        deliveryManagement.updateSupplyMerchantOrderNo(supplyMerchantOrderNo, delivery.getDeliveryId());
                        event.setDelivery(delivery);
                        TransactionContextUtil.setTransactionContext(TransactionMapKey.DELIVERY, delivery);
                    }
                }
                
                BigDecimal orderSuccessFee = (BigDecimal)ActionContextUtil.getActionContextParam(TransactionMapKey.ORDER_SUCCESS_FEE);
                action.doAction(delivery, orderSuccessFee);
            }
        }
        catch (HopsException e)
        {
            delivery = deliveryManagement.findDeliveryById(delivery.getDeliveryId());
            if(e.getCode().equalsIgnoreCase("transaction002045")){
                ActionContextUtil.setActionContext(ActionMapKey.DELIVERY, delivery);
                supplyAmtLowProcess.execute();
            }
            supplyOrderKeyInitialization.deleteSupplyOrderKey(delivery.getMerchantId(), delivery.getDeliveryId());
        }
        catch (Exception e)
        {
            delivery = deliveryManagement.findDeliveryById(delivery.getDeliveryId());
            deliveryManagement.beginQuery(delivery);
            supplyOrderKeyInitialization.deleteSupplyOrderKey(delivery.getMerchantId(), delivery.getDeliveryId());
            logger.error("QueryOrderUnKnowAction_ActionContext["+PrintUtil.mapToString(ActionContextUtil.getActionContextLocal())+"]"+ExceptionUtil.getStackTraceAsString(e));
        }
    }
}
