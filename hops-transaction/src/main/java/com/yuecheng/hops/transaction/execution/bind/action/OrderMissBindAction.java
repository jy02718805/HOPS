package com.yuecheng.hops.transaction.execution.bind.action;

import com.yuecheng.hops.transaction.basic.entity.Order;


public interface OrderMissBindAction
{
    public void execute(Order order);
}
