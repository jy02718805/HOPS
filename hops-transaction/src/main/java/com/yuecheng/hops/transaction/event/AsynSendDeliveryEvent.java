package com.yuecheng.hops.transaction.event;


import java.math.BigDecimal;
import java.util.Map;

import com.yuecheng.hops.account.entity.role.IdentityAccountRole;
import com.yuecheng.hops.common.event.HopsRequestEvent;
import com.yuecheng.hops.transaction.basic.entity.Delivery;


public class AsynSendDeliveryEvent extends HopsRequestEvent
{

    private static final long serialVersionUID = 6850752275804227575L;

    private BigDecimal frozenAmt;

    private IdentityAccountRole payee;

    private Delivery delivery;

    private Map<String, Object> requestMap;

    public BigDecimal getFrozenAmt()
    {
        return frozenAmt;
    }

    public void setFrozenAmt(BigDecimal frozenAmt)
    {
        this.frozenAmt = frozenAmt;
    }

    public IdentityAccountRole getPayee()
    {
        return payee;
    }

    public void setPayee(IdentityAccountRole payee)
    {
        this.payee = payee;
    }

    public Delivery getDelivery()
    {
        return delivery;
    }

    public void setDelivery(Delivery delivery)
    {
        this.delivery = delivery;
    }

    public Map<String, Object> getRequestMap()
    {
        return requestMap;
    }

    public void setRequestMap(Map<String, Object> requestMap)
    {
        this.requestMap = requestMap;
    }

    public AsynSendDeliveryEvent(Object source, Map<String, Object> requestMap,
                                 BigDecimal frozenAmt, IdentityAccountRole payee, Delivery delivery)
    {
        super(source);
        this.requestMap = requestMap;
        this.delivery = delivery;
        this.frozenAmt = frozenAmt;
        this.payee = payee;
    }

}
