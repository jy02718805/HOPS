package com.yuecheng.hops.transaction.execution.order.action;


import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.injection.service.MerchantRequestService;
import com.yuecheng.hops.transaction.TransactionContextUtil;
import com.yuecheng.hops.transaction.TransactionMapKey;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.mq.producer.DeliveryQueryProducerService;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;
import com.yuecheng.hops.transaction.service.process.QueryStartUpProcess;

@Scope("prototype")
@Service("queryDeliveryUnKnowAction")
public class QueryDeliveryUnKnowAction implements QueryDeliveryAction
{
    private static final Logger       logger = LoggerFactory.getLogger(QueryDeliveryUnKnowAction.class);

    @Autowired
    private MerchantRequestService    merchantRequestService;

    @Autowired
    private DeliveryManagement        deliveryManagement;
    
    @Autowired
    private QueryStartUpProcess queryStartUpProcess;
    
    @Autowired
    private DeliveryQueryProducerService deliveryQueryProducerService;

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void doAction(Delivery delivery, BigDecimal orderSuccessFee)
    {
        try
        {
            logger.debug("查询供货商订单信息流程:未找到匹配项!" + String.valueOf(delivery).toString());
            
            ActionContextUtil.init();
            ActionContextUtil.setActionContext(ActionMapKey.DELIVERY, delivery);
            ActionContextUtil.setActionContext(ActionMapKey.DELIVERY_RESULT, TransactionContextUtil.getTransactionContextParam(TransactionMapKey.DELIVERY_RESULT));
            ActionContextUtil.setActionContext(ActionMapKey.QUERY_MSG, TransactionContextUtil.getTransactionContextParam(TransactionMapKey.QUERY_MSG));
            
            queryStartUpProcess.execute();
            TransactionContextUtil.setTransactionContext(TransactionMapKey.DELIVERY, ActionContextUtil.getActionContextParam(ActionMapKey.DELIVERY));
            
            Calendar startDate = Calendar.getInstance();
            Calendar nowDate = Calendar.getInstance();
            startDate.setTime( delivery.getNextQueryTime());
            nowDate.setTime(new Date());
            long delay = startDate.getTimeInMillis() - nowDate.getTimeInMillis();
            logger.debug("延迟 "+delay+"毫秒 发起查询事件！");
            deliveryQueryProducerService.sendMessage(delivery.getDeliveryId(), delay);
        }
        catch (Exception e)
        {
            logger.error("queryOrderUnKnowAction happen Exception caused by "+ ExceptionUtil.getStackTraceAsString(e));
            throw new ApplicationException("transaction002021", e);
        }

    }

}
