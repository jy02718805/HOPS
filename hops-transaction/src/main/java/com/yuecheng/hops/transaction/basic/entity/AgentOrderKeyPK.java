package com.yuecheng.hops.transaction.basic.entity;


import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class AgentOrderKeyPK implements Serializable
{
    public static final long serialVersionUID = -1882068604517443210L;

    private Long merchantId;

    private String merchantOrderNo;

    public Long getMerchantId()
    {
        return merchantId;
    }

    public void setMerchantId(Long merchantId)
    {
        this.merchantId = merchantId;
    }

    public String getMerchantOrderNo()
    {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo)
    {
        this.merchantOrderNo = merchantOrderNo;
    }
    
    public AgentOrderKeyPK()
    {
        
    }

    public AgentOrderKeyPK(Long merchantId, String merchantOrderNo)
    {
        super();
        this.merchantId = merchantId;
        this.merchantOrderNo = merchantOrderNo;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((merchantId == null) ? 0 : merchantId.hashCode());
        result = prime * result + ((merchantOrderNo == null) ? 0 : merchantOrderNo.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        AgentOrderKeyPK other = (AgentOrderKeyPK)obj;
        if (merchantId == null)
        {
            if (other.merchantId != null) return false;
        }
        else if (!merchantId.equals(other.merchantId)) return false;
        if (merchantOrderNo == null)
        {
            if (other.merchantOrderNo != null) return false;
        }
        else if (!merchantOrderNo.equals(other.merchantOrderNo)) return false;
        return true;
    }


}
