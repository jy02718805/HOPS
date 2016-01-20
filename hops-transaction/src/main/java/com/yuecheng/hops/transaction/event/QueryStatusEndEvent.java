package com.yuecheng.hops.transaction.event;


import com.yuecheng.hops.common.event.HopsRequestEvent;
import com.yuecheng.hops.transaction.basic.entity.Delivery;


public class QueryStatusEndEvent extends HopsRequestEvent
{

    private static final long serialVersionUID = 6850752275804227575L;

    private Delivery delivery;

    public Delivery getDelivery()
    {
        return delivery;
    }

    public void setDelivery(Delivery delivery)
    {
        this.delivery = delivery;
    }

    public QueryStatusEndEvent(Object source, Delivery delivery)
    {
        super(source);
        this.delivery = delivery;
    }

}
