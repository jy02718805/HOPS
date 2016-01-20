package com.yuecheng.hops.transaction.config.entify.product;


import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "Assign_exclude")
public class AssignExclude implements Serializable
{

    private static final long serialVersionUID = -5692703934466148771L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AssignExcludeIdSeq")
    @SequenceGenerator(name = "AssignExcludeIdSeq", sequenceName = "ASSIGN_EXCLUDE_SEQ", allocationSize = 1)
    @Column(name = "id")
    private Long              id;

    // business_no business_no 业务编号 number TRUE FALSE TRUE
    // merchant_id merchant_id 商户号 number FALSE FALSE FALSE
    // merchant_type merchant_type 商户类型 varchar2(10) 10 FALSE FALSE FALSE
    // carrier_no carrier_no 运营商编号 varchar2(10) 10 FALSE FALSE FALSE
    // province_no province_no 省份代码 varchar2(5) 5 FALSE FALSE FALSE
    // city_no city_no 城市ID varchar2(5) 5 FALSE FALSE FALSE
    // product_no product_no 产品编号 number FALSE FALSE FALSE
    // rule_type rule_type 规则类型1,指定 2.排除 number FALSE FALSE FALSE
    // object_merchant_id object_merchant_id 被作用方商户号 number FALSE FALSE FALSE
    // object_merchan_typet object_merchan_typet 被作用方商户类型 number FALSE FALSE
    // FALSE

    @Column(name = "business_no", length = 32)
    private String            businessNo;

    @Column(name = "merchant_id")
    private Long              merchantId;

    @Column(name = "merchant_type", length = 10)
    private String            merchantType;

    @Column(name = "merchant_name", length = 20)
    private String            merchantName;

    @Column(name = "carrier_no", length = 10)
    private String            carrierNo;

    @Column(name = "carrier_name", length = 20)
    private String            carrierName;

    @Column(name = "province_no", length = 5)
    private String            provinceNo;

    @Column(name = "province_name", length = 20)
    private String            provinceName;

    @Column(name = "city_no", length = 5)
    private String            cityNo;

    @Column(name = "city_name", length = 5)
    private String            cityName;

    @Column(name = "product_no")
    private Long              productNo;

    @Column(name = "product_name", length = 20)
    private String            productName;

    @Column(name = "par_value")
    private BigDecimal        parValue;

    @Column(name = "rule_type")
    private Long              ruleType;

    @Column(name = "object_merchant_id")
    private Long              objectMerchantId;

    @Column(name = "object_merchant_type", length = 10)
    private String            objectMerchantType;

    @Column(name = "object_merchant_name", length = 20)
    private String            objectMerchantName;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getBusinessNo()
    {
        return businessNo;
    }

    public void setBusinessNo(String businessNo)
    {
        this.businessNo = businessNo;
    }

    public Long getMerchantId()
    {
        return merchantId;
    }

    public void setMerchantId(Long merchantId)
    {
        this.merchantId = merchantId;
    }

    public String getMerchantType()
    {
        return merchantType;
    }

    public void setMerchantType(String merchantType)
    {
        this.merchantType = merchantType;
    }

    public String getMerchantName()
    {
        return merchantName;
    }

    public void setMerchantName(String merchantName)
    {
        this.merchantName = merchantName;
    }

    public String getCarrierNo()
    {
        return carrierNo;
    }

    public void setCarrierNo(String carrierNo)
    {
        this.carrierNo = carrierNo;
    }

    public String getCarrierName()
    {
        return carrierName;
    }

    public void setCarrierName(String carrierName)
    {
        this.carrierName = carrierName;
    }

    public String getProvinceNo()
    {
        return provinceNo;
    }

    public void setProvinceNo(String provinceNo)
    {
        this.provinceNo = provinceNo;
    }

    public String getProvinceName()
    {
        return provinceName;
    }

    public void setProvinceName(String provinceName)
    {
        this.provinceName = provinceName;
    }

    public String getCityNo()
    {
        return cityNo;
    }

    public void setCityNo(String cityNo)
    {
        this.cityNo = cityNo;
    }

    public String getCityName()
    {
        return cityName;
    }

    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }

    public Long getProductNo()
    {
        return productNo;
    }

    public void setProductNo(Long productNo)
    {
        this.productNo = productNo;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public BigDecimal getParValue()
    {
        return parValue;
    }

    public void setParValue(BigDecimal parValue)
    {
        this.parValue = parValue;
    }

    public Long getRuleType()
    {
        return ruleType;
    }

    public void setRuleType(Long ruleType)
    {
        this.ruleType = ruleType;
    }

    public Long getObjectMerchantId()
    {
        return objectMerchantId;
    }

    public void setObjectMerchantId(Long objectMerchantId)
    {
        this.objectMerchantId = objectMerchantId;
    }

    public String getObjectMerchantName()
    {
        return objectMerchantName;
    }

    public void setObjectMerchantName(String objectMerchantName)
    {
        this.objectMerchantName = objectMerchantName;
    }

    public String getObjectMerchantType()
    {
        return objectMerchantType;
    }

    public void setObjectMerchantType(String objectMerchantType)
    {
        this.objectMerchantType = objectMerchantType;
    }

    @Override
    public String toString()
    {
        return "AssignExclude [id=" + id + ", businessNo=" + businessNo + ", merchantId="
               + merchantId + ", merchantType=" + merchantType + ", merchantName=" + merchantName
               + ", carrierNo=" + carrierNo + ", carrierName=" + carrierName + ", provinceNo="
               + provinceNo + ", provinceName=" + provinceName + ", cityNo=" + cityNo
               + ", cityName=" + cityName + ", productNo=" + productNo + ", productName="
               + productName + ", parValue=" + parValue + ", ruleType=" + ruleType
               + ", objectMerchantId=" + objectMerchantId + ", objectMerchantType="
               + objectMerchantType + ", objectMerchantName=" + objectMerchantName + "]";
    }

}
