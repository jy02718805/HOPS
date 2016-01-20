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
@Table(name = "Profit_Report")
public class ProfitReportInfo implements Serializable
{

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ProfitReportIdSeq")
    @SequenceGenerator(name = "ProfitReportIdSeq", sequenceName = "PROFIT_REPORT_ID_SEQ")
    @Column(name = "Profit_Report_ID")
    private Long profitReportId;

    @Column(name = "Merchant_Id")
    private Long merchantId;

    @Column(name = "Merchant_Name")
    private String merchantName;

    @Column(name = "merchant_type", length = 10)
    private String merchantType;

    @Column(name = "merchant_type_name")
    private String merchantTypeName;

    @Column(name = "Province")
    private String province;

    @Column(name = "Province_Name")
    private String provinceName;

    @Column(name = "CITY")
    private String city;

    @Column(name = "CARRIER_NAME")
    private String carrierName;

    @Column(name = "CARRIER_NO")
    private String carrierNo;

    @Column(name = "CITY_NAME")
    private String cityName;

    @Column(name = "Par_Value")
    private BigDecimal parValue;

    @Column(name = "total_Par_Value")
    private BigDecimal totalParValue;

    @Column(name = "Success_Face")
    private BigDecimal successFace;

    @Column(name = "Cost_Fee")
    private BigDecimal costFee;

    @Column(name = "ORDER_SALES_FEE")
    private BigDecimal orderSalesFee;

    @Column(name = "Profit")
    private BigDecimal profit;

    @Column(name = "Profit_Num")
    private Long profitNum;

    @Column(name = "Begin_Time")
    private Date beginTime;

    @Column(name = "End_Time")
    private Date endTime;

    @Column(name = "BUSINESS_TYPE")
    private Long businessType;
    

    public Long getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Long businessType) {
		this.businessType = businessType;
	}
    
    
    public ProfitReportInfo()
    {
        super();
    }

    public ProfitReportInfo(Long profitReportId, Long merchantId, String merchantName,
                            String merchantType, String merchantTypeName, String province,
                            String provinceName, String city, String carrierName,
                            String carrierNo, String cityName, BigDecimal parValue,
                            BigDecimal totalParValue, BigDecimal successFace, BigDecimal costFee,
                            BigDecimal orderSalesFee, BigDecimal profit, Long profitNum,
                            Date beginTime, Date endTime)
    {
        super();
        this.profitReportId = profitReportId;
        this.merchantId = merchantId;
        this.merchantName = merchantName;
        this.merchantType = merchantType;
        this.merchantTypeName = merchantTypeName;
        this.province = province;
        this.provinceName = provinceName;
        this.city = city;
        this.carrierName = carrierName;
        this.carrierNo = carrierNo;
        this.cityName = cityName;
        this.parValue = parValue;
        this.totalParValue = totalParValue;
        this.successFace = successFace;
        this.costFee = costFee;
        this.orderSalesFee = orderSalesFee;
        this.profit = profit;
        this.profitNum = profitNum;
        this.beginTime = beginTime;
        this.endTime = endTime;
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

    public Long getProfitReportId()
    {
        return profitReportId;
    }

    public void setProfitReportId(Long profitReportId)
    {
        this.profitReportId = profitReportId;
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

    public BigDecimal getSuccessFace()
    {
        return successFace;
    }

    public void setSuccessFace(BigDecimal successFace)
    {
        this.successFace = successFace;
    }

    public BigDecimal getCostFee()
    {
        return costFee;
    }

    public void setCostFee(BigDecimal costFee)
    {
        this.costFee = costFee;
    }

    public BigDecimal getProfit()
    {
        return profit;
    }

    public void setProfit(BigDecimal profit)
    {
        this.profit = profit;
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

    public Long getProfitNum()
    {
        return profitNum;
    }

    public void setProfitNum(Long profitNum)
    {
        this.profitNum = profitNum;
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

    public String getProvinceName()
    {
        return provinceName;
    }

    public void setProvinceName(String provinceName)
    {
        this.provinceName = provinceName;
    }

    public String getCarrierNo()
    {
        return carrierNo;
    }

    public void setCarrierNo(String carrierNo)
    {
        this.carrierNo = carrierNo;
    }

    public String getMerchantTypeName()
    {
        return merchantTypeName;
    }

    public void setMerchantTypeName(String merchantTypeName)
    {
        this.merchantTypeName = merchantTypeName;
    }

    public String getCityName()
    {
        return cityName;
    }

    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }

    public BigDecimal getOrderSalesFee()
    {
        return orderSalesFee;
    }

    public void setOrderSalesFee(BigDecimal orderSalesFee)
    {
        this.orderSalesFee = orderSalesFee;
    }

    public BigDecimal getTotalParValue()
    {
        return totalParValue;
    }

    public void setTotalParValue(BigDecimal totalParValue)
    {
        this.totalParValue = totalParValue;
    }

    @Override
    public String toString()
    {
        return "ProfitReport [profitReportId=" + profitReportId + ", province=" + province
               + ", provinceName=" + provinceName + ", parValue=" + parValue + ", merchantId="
               + merchantId + ", merchantName=" + merchantName + ", successFace=" + successFace
               + ", costFee=" + costFee + ", orderSalesFee=" + orderSalesFee + ", profit="
               + profit + ", beginTime=" + beginTime + ", endTime=" + endTime + ", profitNum="
               + profitNum + ", merchantType=" + merchantType + ", city=" + city
               + ", carrierName=" + carrierName + ", carrierNo=" + carrierNo + ", cityName="
               + cityName + ", merchantTypeName=" + merchantTypeName + ", totalParValue="
               + totalParValue + "]";
    }
}
