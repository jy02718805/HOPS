package com.yuecheng.hops.mportal.vo.product;


import java.io.Serializable;
import java.math.BigDecimal;

import com.yuecheng.hops.product.entity.airtimes.AirtimeProduct;
import com.yuecheng.hops.product.entity.relation.ProductRelation;


public class AgentProductRelationVo implements ProductRelation, Serializable
{

    private static final long serialVersionUID = 1785238022124442737L;

    public Long id;

    private Long identityId;

    private String identityType;

    private String identityName;

    private Long productId;

    private String productName;

    private String province;// 省份

    private BigDecimal parValue;// 面值

    private String carrierName;// 运营商

    private String city;// 城市

    private BigDecimal discount;

    private BigDecimal discountWeight;

    private BigDecimal quality;

    private BigDecimal qualityWeight;

    private BigDecimal price;

    private String status;

    private Boolean defValue;// 选定 1,未选 2

    private Boolean isRoot;// true是 false否

    private Long pid;

    private AirtimeProduct airtimeProduct;
    
    private BigDecimal display_value;
    
    private String businessType;
    
    public BigDecimal getDisplay_value()
	{
		return display_value;
	}

	public void setDisplay_value(BigDecimal display_value)
	{
		this.display_value = display_value;
	}

	public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getIdentityId()
    {
        return identityId;
    }

    public void setIdentityId(Long identityId)
    {
        this.identityId = identityId;
    }

    public String getIdentityType()
    {
        return identityType;
    }

    public void setIdentityType(String identityType)
    {
        this.identityType = identityType;
    }

    public String getIdentityName()
    {
        return identityName;
    }

    public void setIdentityName(String identityName)
    {
        this.identityName = identityName;
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

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Long getProductId()
    {
        return productId;
    }

    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

    public Boolean isDefValue()
    {
        return defValue;
    }

    public void setDefValue(Boolean defValue)
    {
        this.defValue = defValue;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public BigDecimal getDiscount()
    {
        return discount;
    }

    public void setDiscount(BigDecimal discount)
    {
        this.discount = discount;
    }

    public BigDecimal getDiscountWeight()
    {
        return discountWeight;
    }

    public void setDiscountWeight(BigDecimal discountWeight)
    {
        this.discountWeight = discountWeight;
    }

    public BigDecimal getQuality()
    {
        return quality;
    }

    public void setQuality(BigDecimal quality)
    {
        this.quality = quality;
    }

    public BigDecimal getQualityWeight()
    {
        return qualityWeight;
    }

    public void setQualityWeight(BigDecimal qualityWeight)
    {
        this.qualityWeight = qualityWeight;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public Boolean getIsRoot()
    {
        return isRoot;
    }

    public void setIsRoot(Boolean isRoot)
    {
        this.isRoot = isRoot;
    }

    public Boolean getDefValue()
    {
        return defValue;
    }

    public Long getPid()
    {
        return pid;
    }

    public void setPid(Long pid)
    {
        this.pid = pid;
    }

    public AirtimeProduct getAirtimeProduct()
    {
        return airtimeProduct;
    }

    public void setAirtimeProduct(AirtimeProduct airtimeProduct)
    {
        this.airtimeProduct = airtimeProduct;
    }

    
    public String getBusinessType()
    {
        return businessType;
    }

    public void setBusinessType(String businessType)
    {
        this.businessType = businessType;
    }

    @Override
    public String toString()
    {
        return "DownProductRelation [id=" + id + ", identityId=" + identityId + ", identityType="
               + identityType + ", identityName=" + identityName + ", productId=" + productId
               + ", productName=" + productName + ", province=" + province + ", parValue="
               + parValue + ", carrierName=" + carrierName + ", city=" + city + ", discount="
               + discount + ", discountWeight=" + discountWeight + ", quality=" + quality
               + ", qualityWeight=" + qualityWeight + ", price=" + price + ", status=" + status
               + ", defValue=" + defValue + ", isRoot=" + isRoot + "]";
    }

}
