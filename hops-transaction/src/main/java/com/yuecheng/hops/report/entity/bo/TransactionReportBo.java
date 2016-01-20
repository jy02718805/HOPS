/*
 * 文件名：ProductAttributes.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月15日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.report.entity.bo;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.yuecheng.hops.report.entity.po.TransactionReportPo;


/**
 * assist 辅助类 〈一句话功能简述〉 〈功能详细描述〉 交易量统计 初始数据。。。
 * 
 * @author Administrator
 * @version 2014年10月17日
 * @see TransactionReportPo
 * @since
 */
public class TransactionReportBo implements Serializable
{
    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = 1L;

    private Long merchantId;// 商户

    private String carrierNo;// 运营商

    private String province;// 省份

    private String city;// 城市

    private BigDecimal parValue;// 面值

    private BigDecimal totalSalesFee;// 销售金额

    private BigDecimal totalParValue;// 总面值

    private Long transactionNum;// 数量

    private String reportsStatus;// 状态

    private Date beginTime;// 开始统计时间

    private Date endTime;// 结束时间

    public BigDecimal costFee;// 成本金额

    public String userCode;// 手机号码

    public Long businessType;// 业务类型

    public String merchantOrderNo;

    public TransactionReportBo()
    {
        super();
    }

    public TransactionReportBo(Long merchantId, String carrierNo, Long transactionNum,
                               BigDecimal totalParValue)
    {
        super();
        this.merchantId = merchantId;
        this.carrierNo = carrierNo;
        this.transactionNum = transactionNum;
        this.totalParValue = totalParValue;
    }

    public TransactionReportBo(Long merchantId, String carrierNo, String province, String city,
                               BigDecimal parValue, BigDecimal orderSalesFee)
    {
        super();
        this.merchantId = merchantId;
        this.carrierNo = carrierNo;
        this.province = province;
        this.city = city;
        this.parValue = parValue;
        this.totalSalesFee = orderSalesFee;
    }

    public TransactionReportBo(Long merchantId, String carrierNo, String province, String city,
                               BigDecimal parValue, BigDecimal orderSalesFee,
                               BigDecimal totalParValue, Long transactionNum)
    {
        super();
        this.merchantId = merchantId;
        this.carrierNo = carrierNo;
        this.province = province;
        this.city = city;
        this.parValue = parValue;
        this.totalSalesFee = orderSalesFee;
        this.totalParValue = totalParValue;
        this.transactionNum = transactionNum;
    }

    public TransactionReportBo(Long merchantId, String carrierNo, String province, String city,
                               BigDecimal parValue, BigDecimal orderSalesFee,
                               BigDecimal totalParValue, BigDecimal costFee,
                               String transactionNum, int reportStatus, String userCode,
                               Long businessType, String merchantOrderNo)
    {
        super();
        this.merchantId = merchantId;
        this.carrierNo = carrierNo;
        this.province = province;
        this.city = city;
        this.parValue = parValue;
        this.totalSalesFee = orderSalesFee;
        this.totalParValue = totalParValue;
        this.costFee = costFee;
        this.transactionNum = 1L;
        this.reportsStatus = reportStatus + "";
        this.userCode = userCode;
        this.businessType = businessType;
        this.merchantOrderNo = merchantOrderNo;
    }

    public TransactionReportBo(Long merchantId, String carrierNo, String province, String city,
                               BigDecimal parValue, BigDecimal orderSalesFee,
                               BigDecimal totalParValue, BigDecimal costFee, Long transactionNum,
                               int reportStatus)
    {
        super();
        this.merchantId = merchantId;
        this.carrierNo = carrierNo;
        this.province = province;
        this.city = city;
        this.parValue = parValue;
        this.totalSalesFee = orderSalesFee;
        this.totalParValue = totalParValue;
        this.costFee = costFee;
        this.transactionNum = transactionNum;
        this.reportsStatus = reportStatus + "";
    }

    public TransactionReportBo(Long merchantId, String carrierNo, String province, String city,
                               BigDecimal parValue, BigDecimal orderSalesFee,
                               BigDecimal totalParValue, String transactionNum, int reportStatus,
                               String userCode)
    {
        super();
        this.merchantId = merchantId;
        this.carrierNo = carrierNo;
        this.province = province;
        this.city = city;
        this.parValue = parValue;
        this.totalSalesFee = orderSalesFee;
        this.totalParValue = totalParValue;
        this.transactionNum = 1L;
        this.reportsStatus = reportStatus + "";
        this.userCode = userCode;
    }

    public TransactionReportBo(Long merchantId, String carrierNo, String province, String city,
                               BigDecimal parValue, BigDecimal orderSalesFee,
                               BigDecimal totalParValue, String transactionNum, int reportStatus,
                               String userCode, Long businessType, String merchantOrderNo)
    {
        super();
        this.merchantId = merchantId;
        this.carrierNo = carrierNo;
        this.province = province;
        this.city = city;
        this.parValue = parValue;
        this.totalSalesFee = orderSalesFee;
        this.totalParValue = totalParValue;
        this.transactionNum = 1L;
        this.reportsStatus = reportStatus + "";
        this.userCode = userCode;
        this.businessType = businessType;
        this.merchantOrderNo = merchantOrderNo;
    }

    public TransactionReportBo(Long merchantId, String carrierNo, String province, String city,
                               BigDecimal parValue, BigDecimal orderSalesFee,
                               BigDecimal totalParValue, Long transactionNum, int reportStatus)
    {
        super();
        this.merchantId = merchantId;
        this.carrierNo = carrierNo;
        this.province = province;
        this.city = city;
        this.parValue = parValue;
        this.totalSalesFee = orderSalesFee;
        this.totalParValue = totalParValue;
        this.transactionNum = transactionNum;
        this.reportsStatus = reportStatus + "";
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

    public BigDecimal getTotalSalesFee()
    {
        return totalSalesFee;
    }

    public void setTotalSalesFee(BigDecimal totalSalesFee)
    {
        this.totalSalesFee = totalSalesFee;
    }

    public String getReportsStatus()
    {
        return reportsStatus;
    }

    public void setReportsStatus(String reportsStatus)
    {
        this.reportsStatus = reportsStatus;
    }

    public BigDecimal getTotalParValue()
    {
        return totalParValue;
    }

    public void setTotalParValue(BigDecimal totalParValue)
    {
        this.totalParValue = totalParValue;
    }

    public Long getTransactionNum()
    {
        return transactionNum;
    }

    public void setTransactionNum(Long transactionNum)
    {
        this.transactionNum = transactionNum;
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

    public BigDecimal getCostFee()
    {
        return costFee;
    }

    public void setCostFee(BigDecimal costFee)
    {
        this.costFee = costFee;
    }

    public String getUserCode()
    {
        return userCode;
    }

    public void setUserCode(String userCode)
    {
        this.userCode = userCode;
    }

    public Long getBusinessType()
    {
        return businessType;
    }

    public void setBusinessType(Long businessType)
    {
        this.businessType = businessType;
    }

    

    public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}

	@Override
    public String toString()
    {
        return "TransactionReportBo [merchantId=" + merchantId + ", carrierNo=" + carrierNo
               + ", province=" + province + ", city=" + city + ", parValue=" + parValue
               + ", totalSalesFee=" + totalSalesFee + ", totalParValue=" + totalParValue
               + ", transactionNum=" + transactionNum + ", reportsStatus=" + reportsStatus
               + ", beginTime=" + beginTime + ", endTime=" + endTime + ", costFee=" + costFee
               + ",userCode=" + userCode + ",businessType-" + businessType + "]";
    }

}
