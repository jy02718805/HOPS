package com.yuecheng.hops.transaction.execution.order.action;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.yuecheng.hops.transaction.mq.producer.DeliveryQueryProducerService;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;
import com.yuecheng.hops.transaction.service.process.QueryStartUpProcess;


@Service("sendOrderSuccessAction")
public class SendOrderSuccessAction implements SendOrderAction
{
    private static final Logger logger = LoggerFactory.getLogger(SendOrderSuccessAction.class);

    @Autowired
    private MerchantRequestService merchantRequestService;

    @Autowired
    private DeliveryManagement deliveryManagement;

    @Autowired
    private DeliveryQueryProducerService deliveryQueryProducerService;
    
    @Autowired
    private QueryStartUpProcess queryStartUpProcess;
    
    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void doAction(Delivery delivery, Map<String, Object> map)
    {
        Map<String,Object> repsonseMap = new HashMap<String, Object>();
        try
        {
            logger.debug("订单发送流程:SUCCESS_STATUS!"+ String.valueOf(delivery).toString());
            repsonseMap = !map.isEmpty()?map:(Map<String,Object>)TransactionContextUtil.getTransactionContextParam(TransactionMapKey.RESPONSE_MAP);
            if(BeanUtils.isNotNull(repsonseMap))
            {
                String supplyMerchantOrderNo = (String)repsonseMap.get(EntityConstant.MerchantResponse.SUPPLY_MERCHANT_ORDER_NO);
                delivery.setSupplyMerchantOrderNo(supplyMerchantOrderNo);
                deliveryManagement.updateSupplyMerchantOrderNo(supplyMerchantOrderNo, delivery.getDeliveryId());
            }
            TransactionContextUtil.setTransactionContext(TransactionMapKey.DELIVERY, delivery);
            logger.debug("订单发送流程:save(delivery)![" + delivery.toString() + "]");
            ActionContextUtil.init();
            ActionContextUtil.setActionContext(ActionMapKey.DELIVERY, delivery);
            queryStartUpProcess.execute();
            
            delivery = (Delivery)TransactionContextUtil.getTransactionContextParam(TransactionMapKey.DELIVERY);
            //发起MQ事件
            Calendar startDate = Calendar.getInstance();
            Calendar nowDate = Calendar.getInstance();
            startDate.setTime(BeanUtils.isNotNull(delivery.getNextQueryTime())?delivery.getNextQueryTime():new Date());
            nowDate.setTime(new Date());
            long delay = startDate.getTimeInMillis() - nowDate.getTimeInMillis();
            logger.debug("延迟 "+delay+"毫秒 发起查询事件！");
            deliveryQueryProducerService.sendMessage(delivery.getDeliveryId(), delay);
        }
        catch (Exception e)
        {
            logger.error("sendOrderSuccessAction happen Exception caused by "+ ExceptionUtil.getStackTraceAsString(e) + "  params[repsonseMap" + PrintUtil.mapToString(repsonseMap) + " delivery:" + String.valueOf(delivery).toString() + "]");
            throw new ApplicationException("transaction002026", new String[]{ExceptionUtil.getStackTraceAsString(e)},e);
        }
    }

}
