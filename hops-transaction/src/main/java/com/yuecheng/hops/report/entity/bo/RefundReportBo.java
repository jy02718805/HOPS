/*
 * 文件名：ProductAttributes.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月15日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.report.entity.bo;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * assist 辅助类 〈一句话功能简述〉 〈功能详细描述〉 利润统计辅助
 * 
 * @author Administrator
 * @version 2014年10月17日
 * @see RefundReportBo
 * @since
 */
public class RefundReportBo implements Serializable
{
    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = 1L;

    private Long merchantId;// 商户

    private Long refundOrderNo;// 退款订单

    private String carrierNo;// 运营商

    private String province;// 省份

    private String city;// 城市

    private BigDecimal parValue;// 面值

    private BigDecimal orderSalesFee;// 销售金额

    private BigDecimal refundFace;// 退款面值

    private BigDecimal costFee;// 成本

    private BigDecimal refundProfit;// 利润

    private BigDecimal totalParValue;// 总面值

    private Long refundNum;// 数量

    private String reportStatus;// 状态

    private Date beginTime;// 开始统计时间

    private Date endTime;// 结束时间

    private Date manualAuditDate;

    private Long businessType;

    public RefundReportBo()
    {
        super();
    }

    public RefundReportBo(Long merchantId, String carrierNo, String province, String city,
                          BigDecimal parValue, BigDecimal orderSalesFee, BigDecimal refundFace,
                          BigDecimal costFee, BigDecimal refundProfit, BigDecimal totalParValue,
                          Date beginTime, Date endTime, Long refundOrderNo, Date manualAuditDate,
                          Long businessType)
    {
        super();
        this.merchantId = merchantId;
        this.carrierNo = carrierNo;
        this.province = province;
        this.city = city;
        this.parValue = parValue;
        this.orderSalesFee = orderSalesFee;
        this.refundFace = refundFace;
        this.costFee = costFee;
        this.refundProfit = refundProfit;
        this.totalParValue = totalParValue;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.refundOrderNo = refundOrderNo;
        this.manualAuditDate = manualAuditDate;
        this.businessType = businessType;
    }

    public Long getMerchantId()
    {
        return merchantId;
    }

    public void setMerchantId(Long merchantId)
    {
        this.merchantId = merchantId;
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

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getCarrierNo()
    {
        return carrierNo;
    }

    public void setCarrierNo(String carrierNo)
    {
        this.carrierNo = carrierNo;
    }

    public BigDecimal getRefundFace()
    {
        return refundFace;
    }

    public void setRefundFace(BigDecimal refundFace)
    {
        this.refundFace = refundFace;
    }

    public BigDecimal getCostFee()
    {
        return costFee;
    }

    public void setCostFee(BigDecimal costFee)
    {
        this.costFee = costFee;
    }

    public BigDecimal getOrderSalesFee()
    {
        return orderSalesFee;
    }

    public void setOrderSalesFee(BigDecimal orderSalesFee)
    {
        this.orderSalesFee = orderSalesFee;
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

    public String getReportStatus()
    {
        return reportStatus;
    }

    public void setReportStatus(String reportStatus)
    {
        this.reportStatus = reportStatus;
    }

    public BigDecimal getTotalParValue()
    {
        return totalParValue;
    }

    public void setTotalParValue(BigDecimal totalParValue)
    {
        this.totalParValue = totalParValue;
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
        return "refundReportPo [merchantId=" + merchantId + ", carrierNo=" + carrierNo
               + ", province=" + province + ", city=" + city + ", parValue=" + parValue
               + ", orderSalesFee=" + orderSalesFee + ", refundFace=" + refundFace + ", costFee="
               + costFee + ", refundProfit=" + refundProfit + ", totalParValue=" + totalParValue
               + ", refundNum=" + refundNum + ", reportStatus=" + reportStatus + ", beginTime="
               + beginTime + ", endTime=" + endTime + "]";
    }

}
