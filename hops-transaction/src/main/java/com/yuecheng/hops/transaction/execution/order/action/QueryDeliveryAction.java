package com.yuecheng.hops.transaction.execution.order.action;


import java.math.BigDecimal;

import com.yuecheng.hops.transaction.basic.entity.Delivery;


public interface QueryDeliveryAction
{
    public void doAction(Delivery delivery, BigDecimal orderSuccessFee);
}
