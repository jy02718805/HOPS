package com.yuecheng.hops.transaction.event;


import java.util.Map;

import com.yuecheng.hops.common.event.HopsRequestEvent;
import com.yuecheng.hops.transaction.basic.entity.Notify;
import com.yuecheng.hops.transaction.basic.entity.Order;


public class AsynNotifyDeliveryEvent extends HopsRequestEvent
{

    private static final long serialVersionUID = 6850752275804227575L;

    private Map<String, Object> requestMap;

    private Order order;

    private Notify notify;

    public Map<String, Object> getRequestMap()
    {
        return requestMap;
    }

    public void setRequestMap(Map<String, Object> requestMap)
    {
        this.requestMap = requestMap;
    }

    public Order getOrder()
    {
        return order;
    }

    public void setOrder(Order order)
    {
        this.order = order;
    }

    public Notify getNotify()
    {
        return notify;
    }

    public void setNotify(Notify notify)
    {
        this.notify = notify;
    }

    public AsynNotifyDeliveryEvent(Object source, Map<String, Object> requestMap,Order order, Notify notify)
    {
        super(source);
        this.requestMap = requestMap;
        this.order = order;
        this.notify = notify;
    }

}
