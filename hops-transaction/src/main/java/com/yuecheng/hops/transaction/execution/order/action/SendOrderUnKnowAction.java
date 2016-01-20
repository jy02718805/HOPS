package com.yuecheng.hops.transaction.execution.order.action;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.PrintUtil;
import com.yuecheng.hops.injection.service.MerchantRequestService;
import com.yuecheng.hops.transaction.TransactionContextUtil;
import com.yuecheng.hops.transaction.TransactionMapKey;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.service.action.AbstractEventAction;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;

@Service("sendOrderUnKnowAction")
public class SendOrderUnKnowAction implements SendOrderAction
{
    private static final Logger    logger = LoggerFactory.getLogger(SendOrderUnKnowAction.class);

    @Autowired
    private MerchantRequestService merchantRequestService;

    @Autowired
    private DeliveryManagement     deliveryManagement;
    
    @Autowired
    @Qualifier("queryStatusWaitAction")
    private AbstractEventAction queryStatusWaitAction;
    
    @Override
    public void doAction(Delivery delivery, Map<String, Object> map)
    {
        Map<String,Object> repsonseMap = new HashMap<String, Object>();
        try
        {
            logger.debug("订单发送流程:responseStr no config!"+String.valueOf(delivery).toString());
            repsonseMap = !map.isEmpty()?map:(Map<String,Object>)TransactionContextUtil.getTransactionContextParam(TransactionMapKey.RESPONSE_MAP);
            if(BeanUtils.isNotNull(repsonseMap))
            {
                String supplyMerchantOrderNo = (String)repsonseMap.get(EntityConstant.MerchantResponse.SUPPLY_MERCHANT_ORDER_NO);
                String deliveryResult = (String)repsonseMap.get(TransactionMapKey.DELIVERY_RESULT);
                delivery.setDeliveryResult(deliveryResult);
                delivery.setSupplyMerchantOrderNo(supplyMerchantOrderNo);
                deliveryManagement.updateParams(supplyMerchantOrderNo, deliveryResult, delivery.getDeliveryId(),EntityConstant.MerchantResponse.QUERY_MSG);
            }
            TransactionContextUtil.setTransactionContext(TransactionMapKey.DELIVERY, delivery);
            ActionContextUtil.init();
            ActionContextUtil.setActionContext(ActionMapKey.DELIVERY, delivery);
            queryStatusWaitAction.handleRequest();
        }
        catch (Exception e)
        {
            logger.error("sendOrderUnKnowAction happen Exception caused by "+ ExceptionUtil.getStackTraceAsString(e) + "  params[repsonseMap" + PrintUtil.mapToString(repsonseMap) + " delivery:" + String.valueOf(delivery).toString() + "]");
            throw new ApplicationException("transaction002027",e);
        }
    }

}
