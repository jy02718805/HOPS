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
@Table(name = "Refund_Report")
public class RefundReportInfo implements Serializable
{

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RefundReportIdSeq")
    @SequenceGenerator(name = "RefundReportIdSeq", sequenceName = "Refund_REPORT_ID_SEQ")
    @Column(name = "refund_Report_ID")
    private Long refundReportId;

    @Column(name = "refund_order_no")
    private Long refundOrderNo;

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

    @Column(name = "Refund_Face")
    private BigDecimal refundFace;

    @Column(name = "Cost_Fee")
    private BigDecimal costFee;

    @Column(name = "ORDER_SALES_FEE")
    private BigDecimal orderSalesFee;

    @Column(name = "Refund_profit")
    private BigDecimal refundProfit;

    @Column(name = "Refund_Num")
    private Long refundNum;

    @Column(name = "Begin_Time")
    private Date beginTime;

    @Column(name = "End_Time")
    private Date endTime;

    @Column(name = "manual_audit_date")
    private Date manualAuditDate;

    @Column(name = "business_Type")
    private Long businessType;

    public RefundReportInfo()
    {
        super();
    }

    public RefundReportInfo(Long refundReportId, Long refundOrderNo, Long merchantId,
                            String merchantName, String merchantType, String merchantTypeName,
                            String province, String provinceName, String city, String carrierName,
                            String carrierNo, String cityName, BigDecimal parValue,
                            BigDecimal totalParValue, BigDecimal refundFace, BigDecimal costFee,
                            BigDecimal orderSalesFee, BigDecimal refundProfit, Long refundNum,
                            Date beginTime, Date endTime, Date manualAuditDate, Long businessType)
    {
        super();
        this.refundReportId = refundReportId;
        this.refundOrderNo = refundOrderNo;
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
        this.refundFace = refundFace;
        this.costFee = costFee;
        this.orderSalesFee = orderSalesFee;
        this.refundProfit = refundProfit;
        this.refundNum = refundNum;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.manualAuditDate = manualAuditDate;
        this.businessType = businessType;
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

    public Long getRefundReportId()
    {
        return refundReportId;
    }

    public void setRefundReportId(Long RefundReportId)
    {
        this.refundReportId = RefundReportId;
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

    public BigDecimal getCostFee()
    {
        return costFee;
    }

    public void setCostFee(BigDecimal costFee)
    {
        this.costFee = costFee;
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

    public BigDecimal getRefundFace()
    {
        return refundFace;
    }

    public void setRefundFace(BigDecimal refundFace)
    {
        this.refundFace = refundFace;
    }

    public BigDecimal getRefundProfit()
    {
        return refundProfit;
    }

    public void setRefundProfit(BigDecimal refundProfit)
    {
        this.refundProfit = refundProfit;
    }

    public Long getRefundNum()
    {
        return refundNum;
    }

    public void setRefundNum(Long refundNum)
    {
        this.refundNum = refundNum;
    }

    public Long getRefundOrderNo()
    {
        return refundOrderNo;
    }

    public void setRefundOrderNo(Long refundOrderNo)
    {
        this.refundOrderNo = refundOrderNo;
    }

    public Date getManualAuditDate()
    {
        return manualAuditDate;
    }

    public void setManualAuditDate(Date manualAuditDate)
    {
        this.manualAuditDate = manualAuditDate;
    }

    public Long getBusinessType()
    {
        return businessType;
    }

    public void setBusinessType(Long businessType)
    {
        this.businessType = businessType;
    }

    @Override
    public String toString()
    {
        return "refundReport [RefundReportId=" + refundReportId + ", province=" + province
               + ", provinceName=" + provinceName + ", parValue=" + parValue + ", merchantId="
               + merchantId + ", merchantName=" + merchantName + ", refundFace=" + refundFace
               + ", costFee=" + costFee + ", orderSalesFee=" + orderSalesFee + ", refundProfit="
               + refundProfit + ", beginTime=" + beginTime + ", endTime=" + endTime
               + ", refundNum=" + refundNum + ", merchantType=" + merchantType + ", city=" + city
               + ", carrierName=" + carrierName + ", carrierNo=" + carrierNo + ", cityName="
               + cityName + ", merchantTypeName=" + merchantTypeName + ", totalParValue="
               + totalParValue + "]";
    }
}
