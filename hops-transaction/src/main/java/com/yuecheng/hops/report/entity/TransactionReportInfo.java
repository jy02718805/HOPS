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


/**
 * 交易量报表
 */
@Entity
@Table(name = "Transaction_Report")
public class TransactionReportInfo implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TransactionReportIdSeq")
    @SequenceGenerator(name = "TransactionReportIdSeq", sequenceName = "TRANSACTION_REPORT_ID_SEQ")
    @Column(name = "Transaction_Report_ID")
    private Long transactionReportId;

    @Column(name = "merchant_Id")
    private Long merchantId;

    @Column(name = "Merchant_Name")
    private String merchantName;

    @Column(name = "merchant_type", length = 10)
    private String merchantType;

    @Column(name = "merchant_type_name")
    private String merchantTypeName;

    @Column(name = "province")
    private String province;

    @Column(name = "Province_Name")
    private String provinceName;

    @Column(name = "CITY")
    private String city;

    @Column(name = "CITY_NAME")
    private String cityName;

    @Column(name = "CARRIER_NO")
    private String carrierNo;

    @Column(name = "CARRIER_NAME")
    private String carrierName;

    @Column(name = "Par_Value")
    private BigDecimal parValue;

    @Column(name = "Total_Par_Value")
    private BigDecimal totalParValue;

    @Column(name = "total_sales_fee")
    private BigDecimal totalSalesFee;

    @Column(name = "reports_status")
    private String reportsStatus;

    @Column(name = "reports_status_name")
    private String reportsStatusName;
    
    @Column(name = "transaction_num")
    private Long transactionNum;

    @Column(name = "begin_time")
    private Date beginTime;

    @Column(name = "End_Time")
    private Date endTime;
    
    @Column(name = "USER_CODE")
    private String userCode;
    
    @Column(name = "BUSINESS_TYPE")
    private Long businessType;
    
    @Column(name = "MERCHANT_ORDER_NO")
    private String merchantOrderNo;
    
    

    public String getMerchantOrderNo() {
		return merchantOrderNo;
	}





	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}










	public Long getBusinessType() {
		return businessType;
	}





	public void setBusinessType(Long businessType) {
		this.businessType = businessType;
	}





	public String getUserCode() {
		return userCode;
	}





	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}





	public TransactionReportInfo()
    {
        super();
    }

    
    
    

    public TransactionReportInfo(Long merchantId, String merchantName, String merchantType,
                                 String merchantTypeName, String province, String provinceName,
                                 String city, String cityName, String carrierNo,
                                 String carrierName, BigDecimal parValue,
                                 BigDecimal totalParValue, BigDecimal totalSalesFee,
                                 String reportsStatus, String reportsStatusName,
                                 Long transactionNum, Date beginTime, Date endTime)
    {
        super();
        this.merchantId = merchantId;
        this.merchantName = merchantName;
        this.merchantType = merchantType;
        this.merchantTypeName = merchantTypeName;
        this.province = province;
        this.provinceName = provinceName;
        this.city = city;
        this.cityName = cityName;
        this.carrierNo = carrierNo;
        this.carrierName = carrierName;
        this.parValue = parValue;
        this.totalParValue = totalParValue;
        this.totalSalesFee = totalSalesFee;
        this.reportsStatus = reportsStatus;
        this.reportsStatusName = reportsStatusName;
        this.transactionNum = transactionNum;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }





    public Long getTransactionReportId()
    {
        return transactionReportId;
    }

    public void setTransactionReportId(Long transactionReportId)
    {
        this.transactionReportId = transactionReportId;
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

    public String getProvinceName()
    {
        return provinceName;
    }

    public void setProvinceName(String provinceName)
    {
        this.provinceName = provinceName;
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

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getCarrierName()
    {
        return carrierName;
    }

    public void setCarrierName(String carrierName)
    {
        this.carrierName = carrierName;
    }

    public String getReportsStatus()
    {
        return reportsStatus;
    }

    public void setReportsStatus(String reportsStatus)
    {
        this.reportsStatus = reportsStatus;
    }

    public String getCarrierNo()
    {
        return carrierNo;
    }

    public void setCarrierNo(String carrierNo)
    {
        this.carrierNo = carrierNo;
    }

    public String getCityName()
    {
        return cityName;
    }

    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }

    public String getMerchantTypeName()
    {
        return merchantTypeName;
    }

    public void setMerchantTypeName(String merchantTypeName)
    {
        this.merchantTypeName = merchantTypeName;
    }

    public String getReportsStatusName()
    {
        return reportsStatusName;
    }

    public void setReportsStatusName(String reportsStatusName)
    {
        this.reportsStatusName = reportsStatusName;
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

    @Override
    public String toString()
    {
        return "TransactionReportInfo [transactionReportId=" + transactionReportId
               + ", merchantId=" + merchantId + ", merchantName=" + merchantName
               + ", merchantType=" + merchantType + ", merchantTypeName=" + merchantTypeName
               + ", province=" + province + ", provinceName=" + provinceName + ", city=" + city
               + ", cityName=" + cityName + ", carrierNo=" + carrierNo + ", carrierName="
               + carrierName + ", parValue=" + parValue + ", totalParValue=" + totalParValue
               + ", totalSalesFee=" + totalSalesFee + ", reportsStatus=" + reportsStatus
               + ", reportsStatusName=" + reportsStatusName + ", beginTime=" + beginTime
               + ", endTime=" + endTime + ", transactionNum=" + transactionNum + ",userCode="+userCode+",businessType="+businessType+"]";
    }

}
