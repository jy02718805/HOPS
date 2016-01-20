package com.yuecheng.hops.transaction.event;


import java.util.Map;

import com.yuecheng.hops.common.event.HopsRequestEvent;
import com.yuecheng.hops.transaction.basic.entity.Delivery;


public class AsynQueryDeliveryEvent extends HopsRequestEvent
{

    private static final long serialVersionUID = 6850752275804227575L;

    private Map<String, Object> requestMap;

    private Delivery delivery;

    public Map<String, Object> getRequestMap()
    {
        return requestMap;
    }

    public void setRequestMap(Map<String, Object> requestMap)
    {
        this.requestMap = requestMap;
    }

    public Delivery getDelivery()
    {
        return delivery;
    }

    public void setDelivery(Delivery delivery)
    {
        this.delivery = delivery;
    }

    public AsynQueryDeliveryEvent(Object source, Map<String, Object> requestMap, Delivery delivery)
    {
        super(source);
        this.requestMap = requestMap;
        this.delivery = delivery;
    }

}
