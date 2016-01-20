package com.yuecheng.hops.transaction.execution.notify;


import java.util.Map;

import com.yuecheng.hops.transaction.basic.entity.Notify;
import com.yuecheng.hops.transaction.basic.entity.Order;


public interface NotifyOrderService
{
    /**
     * 通知代理商处理
     */
    public void execute(Map<String, Object> responseMap, Order order, Notify notify);

}
