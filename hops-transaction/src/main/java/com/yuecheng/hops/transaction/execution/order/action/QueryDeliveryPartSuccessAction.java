package com.yuecheng.hops.transaction.execution.order.action;


import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;
import com.yuecheng.hops.transaction.service.order.OrderManagement;
import com.yuecheng.hops.transaction.service.process.QueryPartSuccessProcess;

@Scope("prototype")
@Service("queryDeliveryPartSuccessAction")
public class QueryDeliveryPartSuccessAction implements QueryDeliveryAction
{
    private static final Logger logger = LoggerFactory.getLogger(QueryDeliveryPartSuccessAction.class);

    
    @Autowired
    private DeliveryManagement deliveryManagement;
    
    @Autowired
    private OrderManagement orderManagement;

    @Autowired
    private QueryPartSuccessProcess queryPartSuccessProcess;

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void doAction(Delivery delivery, BigDecimal orderSuccessFee)
    {
        try
        {
            logger.debug("查询供货商订单信息流程:查询订单部分成功!" + String.valueOf(delivery).toString());
            Order order = orderManagement.findOne(delivery.getOrderNo());
            ActionContextUtil.init();
            ActionContextUtil.setActionContext(ActionMapKey.DELIVERY, delivery);
            ActionContextUtil.setActionContext(ActionMapKey.ORDER, order);
            ActionContextUtil.setActionContext(ActionMapKey.ORDER_SUCCESS_FEE,
                orderSuccessFee);
            queryPartSuccessProcess.execute();
        }
        catch (Exception e)
        {
            logger.error("queryOrderPartSuccessAction happen Exception caused by "+ ExceptionUtil.getStackTraceAsString(e));
            throw new ApplicationException("transaction002039", e);
        }
    }

}
