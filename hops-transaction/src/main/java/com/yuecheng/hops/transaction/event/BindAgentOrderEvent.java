package com.yuecheng.hops.transaction.event;


import com.yuecheng.hops.common.event.HopsRequestEvent;


public class BindAgentOrderEvent extends HopsRequestEvent
{

    private static final long serialVersionUID = 6850752275804227575L;

    private Long              orderNo;

    public Long getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(Long orderNo)
    {
        this.orderNo = orderNo;
    }

    public BindAgentOrderEvent(Object source, Long orderNo)
    {
        super(source);
        this.orderNo = orderNo;
    }

}
