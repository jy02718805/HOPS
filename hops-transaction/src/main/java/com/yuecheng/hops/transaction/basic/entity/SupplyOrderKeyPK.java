package com.yuecheng.hops.transaction.basic.entity;


import java.io.Serializable;

import javax.persistence.Embeddable;


@Embeddable
public class SupplyOrderKeyPK implements Serializable
{
    public static final long serialVersionUID = -1882068604517443210L;

    private Long merchantId;

    private Long deliveryId;

    public Long getMerchantId()
    {
        return merchantId;
    }

    public void setMerchantId(Long merchantId)
    {
        this.merchantId = merchantId;
    }

    public Long getDeliveryId()
    {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId)
    {
        this.deliveryId = deliveryId;
    }

    public SupplyOrderKeyPK()
    {

    }

    public SupplyOrderKeyPK(Long merchantId, Long deliveryId)
    {
        super();
        this.merchantId = merchantId;
        this.deliveryId = deliveryId;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((deliveryId == null) ? 0 : deliveryId.hashCode());
        result = prime * result + ((merchantId == null) ? 0 : merchantId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        SupplyOrderKeyPK other = (SupplyOrderKeyPK)obj;
        if (deliveryId == null)
        {
            if (other.deliveryId != null) return false;
        }
        else if (!deliveryId.equals(other.deliveryId)) return false;
        if (merchantId == null)
        {
            if (other.merchantId != null) return false;
        }
        else if (!merchantId.equals(other.merchantId)) return false;
        return true;
    }

}
