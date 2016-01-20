package com.yuecheng.hops.transaction.event;


import com.yuecheng.hops.common.event.HopsRequestEvent;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;


public class SendOrderEvent extends HopsRequestEvent
{

    private static final long serialVersionUID = 6850752275804227575L;

    private Long              deliveryId;
    
    private int            businessType;
    
    private SupplyProductRelation   supplyProductRelation;

    public Long getDeliveryId()
    {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId)
    {
        this.deliveryId = deliveryId;
    }

    public int getBusinessType()
    {
        return businessType;
    }

    public void setBusinessType(int businessType)
    {
        this.businessType = businessType;
    }

    public SupplyProductRelation getSupplyProductRelation()
    {
        return supplyProductRelation;
    }

    public void setSupplyProductRelation(SupplyProductRelation supplyProductRelation)
    {
        this.supplyProductRelation = supplyProductRelation;
    }

    public SendOrderEvent(Object source, Long deliveryId,int businessType,SupplyProductRelation supplyProductRelation)
    {
        super(source);
        this.deliveryId = deliveryId;
        this.businessType = businessType;
        this.supplyProductRelation = supplyProductRelation;
    }

}
