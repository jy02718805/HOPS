package com.yuecheng.hops.transaction.event;


import com.yuecheng.hops.common.event.HopsRequestEvent;


public class QueryOrderEvent extends HopsRequestEvent
{

    private static final long serialVersionUID = 6850752275804227575L;

    private Long              deliveryId;

    public Long getDeliveryId()
    {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId)
    {
        this.deliveryId = deliveryId;
    }

    public QueryOrderEvent(Object source, Long deliveryId)
    {
        super(source);
        this.deliveryId = deliveryId;
    }

}
