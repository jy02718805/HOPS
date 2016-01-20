package com.yuecheng.hops.mportal.vo.product;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;

import com.yuecheng.hops.product.entity.airtimes.AirtimeProduct;
import com.yuecheng.hops.product.entity.relation.ProductRelation;


public class SupplyProductRelationVo implements ProductRelation, Serializable, Comparator<SupplyProductRelationVo>
{

    private static final long serialVersionUID = 146933368053871821L;

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

    private BigDecimal quality;

    private BigDecimal price;

    private String status;

    private Long merchantLevel;

    private BigDecimal totalPoint;

    private AirtimeProduct airtimeProduct;

    private String businessType;

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

    public Long getProductId()
    {
        return productId;
    }

    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

    public BigDecimal getDiscount()
    {
        return discount;
    }

    public void setDiscount(BigDecimal discount)
    {
        this.discount = discount;
    }

    public BigDecimal getQuality()
    {
        return quality;
    }

    public void setQuality(BigDecimal quality)
    {
        this.quality = quality;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Long getMerchantLevel()
    {
        return merchantLevel;
    }

    public void setMerchantLevel(Long merchantLevel)
    {
        this.merchantLevel = merchantLevel;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public BigDecimal getTotalPoint()
    {
        return totalPoint;
    }

    public void setTotalPoint(BigDecimal totalPoint)
    {
        this.totalPoint = totalPoint;
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
        return "UpProductRelation [id=" + id + ", identityId=" + identityId + ", identityType="
               + identityType + ", identityName=" + identityName + ", productId=" + productId
               + ", productName=" + productName + ", province=" + province + ", parValue="
               + parValue + ", carrierName=" + carrierName + ", city=" + city + ", discount="
               + discount + ", quality=" + quality + ", price=" + price + ", status=" + status
               + ", merchantLevel=" + merchantLevel + ", totalPoint=" + totalPoint + "]";
    }

    // 总分 = 质量分*质量比重 + 价格分*价格比重
    // 质量分=成功率分*成功率比重+速度分*速度比重
    // 成功率分 = 成功订单数/总订单数 * 100
    // 速度分= tanh(成功率/修正值) * 100
    // 价格分 价格分=100*(销售价格折扣-成本价格折扣)/(1-成本价格折扣）
    @Override
    public int compare(SupplyProductRelationVo upProductRelation0,
                       SupplyProductRelationVo upProductRelation1)
    {
        int flag = upProductRelation0.getTotalPoint().compareTo(upProductRelation1.getTotalPoint());
        return flag;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((carrierName == null) ? 0 : carrierName.hashCode());
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((discount == null) ? 0 : discount.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((identityId == null) ? 0 : identityId.hashCode());
        result = prime * result + ((identityName == null) ? 0 : identityName.hashCode());
        result = prime * result + ((identityType == null) ? 0 : identityType.hashCode());
        result = prime * result + ((merchantLevel == null) ? 0 : merchantLevel.hashCode());
        result = prime * result + ((parValue == null) ? 0 : parValue.hashCode());
        result = prime * result + ((price == null) ? 0 : price.hashCode());
        result = prime * result + ((productId == null) ? 0 : productId.hashCode());
        result = prime * result + ((productName == null) ? 0 : productName.hashCode());
        result = prime * result + ((province == null) ? 0 : province.hashCode());
        result = prime * result + ((quality == null) ? 0 : quality.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((totalPoint == null) ? 0 : totalPoint.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        SupplyProductRelationVo other = (SupplyProductRelationVo)obj;
        if (carrierName == null)
        {
            if (other.carrierName != null) return false;
        }
        else if (!carrierName.equals(other.carrierName)) return false;
        if (city == null)
        {
            if (other.city != null) return false;
        }
        else if (!city.equals(other.city)) return false;
        if (discount == null)
        {
            if (other.discount != null) return false;
        }
        else if (!discount.equals(other.discount)) return false;
        if (id == null)
        {
            if (other.id != null) return false;
        }
        else if (!id.equals(other.id)) return false;
        if (identityId == null)
        {
            if (other.identityId != null) return false;
        }
        else if (!identityId.equals(other.identityId)) return false;
        if (identityName == null)
        {
            if (other.identityName != null) return false;
        }
        else if (!identityName.equals(other.identityName)) return false;
        if (identityType == null)
        {
            if (other.identityType != null) return false;
        }
        else if (!identityType.equals(other.identityType)) return false;
        if (merchantLevel == null)
        {
            if (other.merchantLevel != null) return false;
        }
        else if (!merchantLevel.equals(other.merchantLevel)) return false;
        if (parValue == null)
        {
            if (other.parValue != null) return false;
        }
        else if (!parValue.equals(other.parValue)) return false;
        if (price == null)
        {
            if (other.price != null) return false;
        }
        else if (!price.equals(other.price)) return false;
        if (productId == null)
        {
            if (other.productId != null) return false;
        }
        else if (!productId.equals(other.productId)) return false;
        if (productName == null)
        {
            if (other.productName != null) return false;
        }
        else if (!productName.equals(other.productName)) return false;
        if (province == null)
        {
            if (other.province != null) return false;
        }
        else if (!province.equals(other.province)) return false;
        if (quality == null)
        {
            if (other.quality != null) return false;
        }
        else if (!quality.equals(other.quality)) return false;
        if (status == null)
        {
            if (other.status != null) return false;
        }
        else if (!status.equals(other.status)) return false;
        if (totalPoint == null)
        {
            if (other.totalPoint != null) return false;
        }
        else if (!totalPoint.equals(other.totalPoint)) return false;
        return true;
    }

}
