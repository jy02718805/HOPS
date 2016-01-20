package com.yuecheng.hops.transaction.execution.bind.director;


import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.execution.bind.action.OrderMissBindAction;


public interface OrderMissBindSelector
{
    public OrderMissBindAction select(Order order);
}
