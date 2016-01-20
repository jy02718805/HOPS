package com.yuecheng.hops.product.entity.history;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "product_discount_history")
public class ProductDiscountHistory implements Serializable
{

    private static final long serialVersionUID = -7565579431564647729L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productDiscountHistoryIdSeq")
    @SequenceGenerator(name = "productDiscountHistoryIdSeq", sequenceName = "PRODUCT_DISCOUNT_ID_SEQ")
    @Column(name = "id")
    private Long id;

    @Column(name = "action", length = 10)
    private String action;// 动作 增，删，改

    @Column(name = "province", length = 10)
    private String province;// 省份

    @Column(name = "par_value", length = 10)
    private String parValue;// 面值

    @Column(name = "carrier_name", length = 64)
    private String carrierName;// 运营商

    @Column(name = "city", length = 10)
    private String city;// 城市

    @Column(name = "business_type")
    private String businessType;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_side", length = 8)
    private String productSide;// AGENT/SUPPLY

    @Column(name = "identity_id")
    private String identityId;

    @Column(name = "identity_name", length = 64)
    private String identityName;

    @Column(name = "old_value")
    private BigDecimal oldValue;

    @Column(name = "new_value")
    private BigDecimal newValue;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "operator_name", length = 64)
    private String operatorName;

    @Column(name = "rmk", length = 100)
    private String rmk;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getAction()
    {
        return action;
    }

    public void setAction(String action)
    {
        this.action = action;
    }

    public String getProvince()
    {
        return province;
    }

    public void setProvince(String province)
    {
        this.province = province;
    }

    public String getParValue()
    {
        return parValue;
    }

    public void setParValue(String parValue)
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

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getProductSide()
    {
        return productSide;
    }

    public void setProductSide(String productSide)
    {
        this.productSide = productSide;
    }

    public String getIdentityId()
    {
        return identityId;
    }

    public void setIdentityId(String identityId)
    {
        this.identityId = identityId;
    }

    public String getIdentityName()
    {
        return identityName;
    }

    public void setIdentityName(String identityName)
    {
        this.identityName = identityName;
    }

    public BigDecimal getOldValue()
    {
        return oldValue;
    }

    public void setOldValue(BigDecimal oldValue)
    {
        this.oldValue = oldValue;
    }

    public BigDecimal getNewValue()
    {
        return newValue;
    }

    public void setNewValue(BigDecimal newValue)
    {
        this.newValue = newValue;
    }

    public Date getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }

    public String getOperatorName()
    {
        return operatorName;
    }

    public void setOperatorName(String operatorName)
    {
        this.operatorName = operatorName;
    }

    public String getRmk()
    {
        return rmk;
    }

    public void setRmk(String rmk)
    {
        this.rmk = rmk;
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
        return "ProductDiscountHistory [id=" + id + ", action=" + action + ", province="
               + province + ", parValue=" + parValue + ", carrierName=" + carrierName + ", city="
               + city + ", businessType=" + businessType + ", productName=" + productName
               + ", productSide=" + productSide + ", identityId=" + identityId + ", identityName="
               + identityName + ", oldValue=" + oldValue + ", newValue=" + newValue
               + ", createDate=" + createDate + ", operatorName=" + operatorName + ", rmk=" + rmk
               + ", getId()=" + getId() + ", getAction()=" + getAction() + ", getProvince()="
               + getProvince() + ", getParValue()=" + getParValue() + ", getCarrierName()="
               + getCarrierName() + ", getCity()=" + getCity() + ", getProductName()="
               + getProductName() + ", getProductSide()=" + getProductSide()
               + ", getIdentityId()=" + getIdentityId() + ", getIdentityName()="
               + getIdentityName() + ", getOldValue()=" + getOldValue() + ", getNewValue()="
               + getNewValue() + ", getCreateDate()=" + getCreateDate() + ", getOperatorName()="
               + getOperatorName() + ", getRmk()=" + getRmk() + ", getBusinessType()="
               + getBusinessType() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
               + ", toString()=" + super.toString() + "]";
    }

}
