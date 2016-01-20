package com.yuecheng.hops.report.entity;


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
@Table(name = "agent_transaction_report")
public class AgentTransactionReportInfo implements Serializable
{
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agentReportIdSeq")
    @SequenceGenerator(name = "agentReportIdSeq", sequenceName = "agent_report_id_seq")
    @Column(name = "agent_transaction_report_id")
    private Long agentTransactionReportId;

    @Column(name = "merchant_id")
    private Long merchantId;

    @Column(name = "merchant_name")
    private String merchantName;

    @Column(name = "merchant_type")
    private String merchantType;

    @Column(name = "merchant_type_name")
    private String merchantTypeName;

    @Column(name = "begin_time")
    private Date beginTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "agent_transaction_num")
    private Long transactionNum;

    @Column(name = "reports_status")
    private String reportsStatus;

    @Column(name = "reports_status_name")
    private String reportsStatusName;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "carrier_no")
    private String carrierNo;

    @Column(name = "carrier_name")
    private String carrierName;

    @Column(name = "province")
    private String province;

    @Column(name = "province_name")
    private String provinceName;

    @Column(name = "city")
    private String city;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "total_par_value")
    private BigDecimal totalParValue;

    @Column(name = "total_sales_fee")
    private BigDecimal totalSalesFee;

    @Column(name = "par_value")
    private BigDecimal parValue;
    
    @Column(name = "BUSINESS_TYPE")
    private Long businessType;
    

    public Long getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Long businessType) {
		this.businessType = businessType;
	}

    public Long getAgentTransactionReportId()
    {
        return agentTransactionReportId;
    }

    public void setAgentTransactionReportId(Long agentTransactionReportId)
    {
        this.agentTransactionReportId = agentTransactionReportId;
    }

    public Long getMerchantId()
    {
        return merchantId;
    }

    public void setMerchantId(Long merchantId)
    {
        this.merchantId = merchantId;
    }

    public String getMerchantName()
    {
        return merchantName;
    }

    public void setMerchantName(String merchantName)
    {
        this.merchantName = merchantName;
    }

    public String getMerchantType()
    {
        return merchantType;
    }

    public void setMerchantType(String merchantType)
    {
        this.merchantType = merchantType;
    }

    public String getMerchantTypeName()
    {
        return merchantTypeName;
    }

    public void setMerchantTypeName(String merchantTypeName)
    {
        this.merchantTypeName = merchantTypeName;
    }

    public Date getBeginTime()
    {
        return beginTime;
    }

    public void setBeginTime(Date beginTime)
    {
        this.beginTime = beginTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public Long getTransactionNum()
    {
        return transactionNum;
    }

    public void setTransactionNum(Long transactionNum)
    {
        this.transactionNum = transactionNum;
    }

    public String getReportsStatus()
    {
        return reportsStatus;
    }

    public void setReportsStatus(String reportsStatus)
    {
        this.reportsStatus = reportsStatus;
    }

    public String getReportsStatusName()
    {
        return reportsStatusName;
    }

    public void setReportsStatusName(String reportsStatusName)
    {
        this.reportsStatusName = reportsStatusName;
    }

    public Long getProductId()
    {
        return productId;
    }

    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
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

    public String getProvince()
    {
        return province;
    }

    public void setProvince(String province)
    {
        this.province = province;
    }

    public String getProvinceName()
    {
        return provinceName;
    }

    public void setProvinceName(String provinceName)
    {
        this.provinceName = provinceName;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getCityName()
    {
        return cityName;
    }

    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }

    public BigDecimal getTotalParValue()
    {
        return totalParValue;
    }

    public void setTotalParValue(BigDecimal totalParValue)
    {
        this.totalParValue = totalParValue;
    }

    public BigDecimal getTotalSalesFee()
    {
        return totalSalesFee;
    }

    public void setTotalSalesFee(BigDecimal totalSalesFee)
    {
        this.totalSalesFee = totalSalesFee;
    }

    public BigDecimal getParValue()
    {
        return parValue;
    }

    public void setParValue(BigDecimal parValue)
    {
        this.parValue = parValue;
    }

}
