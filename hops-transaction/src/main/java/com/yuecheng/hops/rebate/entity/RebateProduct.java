package com.yuecheng.hops.rebate.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "Rebate_Product")
@SequenceGenerator(name = "SeqRebateProductId", sequenceName = "SEQ_REBATE_PRODUCT_ID")
public class RebateProduct implements Serializable
{
    private static final long serialVersionUID = 4109371246963505462L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SeqRebateProductId")
    @Column(name = "ID")
    private Long id;    //主键ID

    @Column(name = "REBATE_PRODUCT_ID")
    private String rebateProductId; //返佣产品区间ID

    @Column(name = "MERCHANT_ID")
    private Long merchantId;    //发生商户ID

    @Column(name = "PRODUCT_ID")
    private Long productId;    //商户产品ID

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getRebateProductId()
    {
        return rebateProductId;
    }

    public void setRebateProductId(String rebateProductId)
    {
        this.rebateProductId = rebateProductId;
    }

    public Long getMerchantId()
    {
        return merchantId;
    }

    public void setMerchantId(Long merchantId)
    {
        this.merchantId = merchantId;
    }

    public Long getProductId()
    {
        return productId;
    }

    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

    @Override
    public String toString()
    {
        return "RebateProduct [id=" + id + ", rebateProductId=" + rebateProductId
               + ", merchantId=" + merchantId + ", productId=" + productId + "]";
    }

}
