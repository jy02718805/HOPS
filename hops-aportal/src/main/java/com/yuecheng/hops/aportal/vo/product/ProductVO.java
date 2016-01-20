package com.yuecheng.hops.aportal.vo.product;


import java.math.BigDecimal;


public class ProductVO
{
    public String productName;

    public String typeId;

    public String productStatus;

    private String province;// 省份

    private BigDecimal parValue;// 面值

    private String carrierName;// 运营商

    private String city;

    private Long parentProductId;

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getTypeId()
    {
        return typeId;
    }

    public void setTypeId(String typeId)
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

    public String getProvince()
    {
        return province;
    }

    public void setProvince(String province)
    {
        this.province = province;
    }

    public BigDecimal getParValue()
    {
        return parValue;
    }

    public void setParValue(BigDecimal parValue)
    {
        this.parValue = parValue;
    }

    public String getCarrierName()
    {
        return carrierName;
    }

    public void setCarrierName(String carrierName)
    {
        this.carrierName = carrierName;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public Long getParentProductId()
    {
        return parentProductId;
    }

    public void setParentProductId(Long parentProductId)
    {
        this.parentProductId = parentProductId;
    }

}
