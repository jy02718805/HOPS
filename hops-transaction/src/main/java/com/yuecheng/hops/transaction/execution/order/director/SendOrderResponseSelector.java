package com.yuecheng.hops.transaction.execution.order.director;


import java.util.Map;

import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.execution.order.action.SendOrderAction;


public interface SendOrderResponseSelector
{
    public SendOrderAction select(Delivery delivery,Map<String, Object> map);
}
