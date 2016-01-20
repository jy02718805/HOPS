package com.yuecheng.hops.transaction.basic.entity;


import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Entity
@Table(name = "supply_order_key")
public class SupplyOrderKey implements Serializable
{
    public static Logger logger = LoggerFactory.getLogger(SupplyOrderKey.class);

    public static final long serialVersionUID = -1882068604517443210L;

    @EmbeddedId
    private SupplyOrderKeyPK supplyOrderKeyPK;

    public SupplyOrderKeyPK getSupplyOrderKeyPK()
    {
        return supplyOrderKeyPK;
    }

    public void setSupplyOrderKeyPK(SupplyOrderKeyPK supplyOrderKeyPK)
    {
        this.supplyOrderKeyPK = supplyOrderKeyPK;
    }

}
