package com.yuecheng.hops.transaction.config.entify.product;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "merchant_level")
public class MerchantProductLevel implements Serializable
{

    private static final long serialVersionUID = -5692703934466148771L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MerchantLevelIdSeq")
    @SequenceGenerator(name = "MerchantLevelIdSeq", sequenceName = "MERCHANT_LEVEL_ID_SEQ")
    @Column(name = "id")
    private Long              id;

    @Column(name = "merchant_level")
    private Long              merchantLevel;

    @Column(name = "order_percentage_low")
    private Long              orderPercentagelow;

    @Column(name = "order_percentage_high")
    private Long              orderPercentageHigh;

    @Column(name = "merchant_name")
    private String            merchantName;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getMerchantLevel()
    {
        return merchantLevel;
    }

    public void setMerchantLevel(Long merchantLevel)
    {
        this.merchantLevel = merchantLevel;
    }

    public Long getOrderPercentagelow()
    {
        return orderPercentagelow;
    }

    public void setOrderPercentagelow(Long orderPercentagelow)
    {
        this.orderPercentagelow = orderPercentagelow;
    }

    public Long getOrderPercentageHigh()
    {
        return orderPercentageHigh;
    }

    public void setOrderPercentageHigh(Long orderPercentageHigh)
    {
        this.orderPercentageHigh = orderPercentageHigh;
    }

    public String getMerchantName()
    {
        return merchantName;
    }

    public void setMerchantName(String merchantName)
    {
        this.merchantName = merchantName;
    }

    @Override
    public String toString()
    {
        return "MerchantLevel [id=" + id + ", merchantLevel=" + merchantLevel
               + ", orderPercentagelow=" + orderPercentagelow + ", orderPercentageHigh="
               + orderPercentageHigh + ", merchantName=" + merchantName + "]";
    }
}
