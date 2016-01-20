package com.yuecheng.hops.transaction.execution.order.action;


import java.util.Map;

import com.yuecheng.hops.transaction.basic.entity.Delivery;


public interface SendOrderAction
{
    public void doAction(Delivery delivery, Map<String, Object> map);
}
