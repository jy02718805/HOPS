package com.yuecheng.hops.product.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;


@MappedSuperclass
public abstract class AbstractProduct implements Product, Serializable
{
    private static final long serialVersionUID = -8847345281358896918L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productIdSeq")
    @SequenceGenerator(name = "productIdSeq", sequenceName = "PRODUCT_ID_SEQ")
    @Column(name = "product_id")
    protected Long productId;

    @Column(name = "product_name", length = 64)
    protected String productName;

    @Column(name = "product_type")
    protected Long typeId;

    @Column(name = "product_status", length = 10)
    protected String productStatus;

    /**
     * @return the productId
     */
    public Long getProductId()
    {
        return productId;
    }

    /**
     * @param productId
     *            the productId to set
     */
    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

    /**
     * @return the productName
     */
    public String getProductName()
    {
        return productName;
    }

    /**
     * @param productName
     *            the productName to set
     */
    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public Long getTypeId()
    {
        return typeId;
    }

    public void setTypeId(Long typeId)
    {
        this.typeId = typeId;
    }

    public String getProductStatus()
    {
        return productStatus;
    }

    public void setProductStatus(String productStatus)
    {
        this.productStatus = productStatus;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((productId == null) ? 0 : productId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        AbstractProduct other = (AbstractProduct)obj;
        if (productId == null)
        {
            if (other.productId != null) return false;
        }
        else if (!productId.equals(other.productId)) return false;
        return true;
    }

}
