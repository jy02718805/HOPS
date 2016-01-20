package com.yuecheng.hops.product.entity.airtimes;


import java.math.BigDecimal;
import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.product.entity.AbstractProduct;


@Entity
@Table(name = "AirtimeProduct")
public class AirtimeProduct extends AbstractProduct implements Comparator
{
    private static final long serialVersionUID = 3587722283839739716L;

    @Column(name = "province", length = 10)
    private String province;// 省份

    @Column(name = "par_value", length = 10)
    private BigDecimal parValue;// 面值

    @Column(name = "carrier_name", length = 64)
    private String carrierName;// 运营商

    @Column(name = "city", length = 10)
    private String city;// 城市

    @Column(name = "parent_product_id")
    public Long parentProductId;

    @Column(name = "product_no")
    public String productNo;

    @Column(name = "IS_COMMON_USE")
    public Long isCommonUse;
    
    @Column(name = "display_value")
    private BigDecimal displayValue;
    
    @Column(name = "business_type")
    private int businessType;
	
	public int getBusinessType()
	{
		return businessType;
	}

	public void setBusinessType(int businessType)
	{
		this.businessType = businessType;
	}

	public BigDecimal getDisplayValue()
	{
		return displayValue;
	}

	public void setDisplayValue(BigDecimal displayValue)
	{
		this.displayValue = displayValue;
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

    public String getProductNo()
    {
        return productNo;
    }

    public void setProductNo(String productNo)
    {
        this.productNo = productNo;
    }

    public Long getIsCommonUse()
    {
        return isCommonUse;
    }

    public void setIsCommonUse(Long isCommonUse)
    {
        this.isCommonUse = isCommonUse;
    }

    public AirtimeProduct()
    {

    }

    public AirtimeProduct(String province, BigDecimal parValue, String carrierName, String city,
                          Long parentProductId, String productNo,int businessType)
    {
        super();
        this.province = province;
        this.parValue = parValue;
        this.carrierName = carrierName;
        this.city = city;
        this.parentProductId = parentProductId;
        this.productNo = productNo;
        this.businessType = businessType;
    }

    @Override
    public int compare(Object o1, Object o2)
    {
        // TODO Auto-generated method stub
        AirtimeProduct airtimeProduct1 = (AirtimeProduct)o1;
        AirtimeProduct airtimeProduct2 = (AirtimeProduct)o2;
        int flag = airtimeProduct1.getProductId().compareTo(airtimeProduct2.getProductId());
        return flag;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((carrierName == null) ? 0 : carrierName.hashCode());
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((parValue == null) ? 0 : parValue.hashCode());
        result = prime * result + ((parentProductId == null) ? 0 : parentProductId.hashCode());
        result = prime * result + ((productNo == null) ? 0 : productNo.hashCode());
        result = prime * result + ((province == null) ? 0 : province.hashCode());
        result = prime * result + (BeanUtils.isNotNull(businessType) ? 0 : businessType);
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        AirtimeProduct other = (AirtimeProduct)obj;
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
        if (parValue == null)
        {
            if (other.parValue != null) return false;
        }
        else if (!parValue.equals(other.parValue)) return false;
        if (parentProductId == null)
        {
            if (other.parentProductId != null) return false;
        }
        else if (!parentProductId.equals(other.parentProductId)) return false;
        if (productNo == null)
        {
            if (other.productNo != null) return false;
        }
        else if (!productNo.equals(other.productNo)) return false;
        if (province == null)
        {
            if (other.province != null) return false;
        }
        else if (!province.equals(other.province)) return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "AirtimeProduct [province=" + province + ", parValue=" + parValue
               + ", carrierName=" + carrierName + ", city=" + city + ", parentProductId="
               + parentProductId + ", productNo=" + productNo + ", businessType=" + businessType + "]";
    }
}
