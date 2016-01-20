/*
 * 文件名：ProductAttributes.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月15日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.report.entity.bo;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * assist 辅助类 〈一句话功能简述〉 〈功能详细描述〉 商户交易量统计
 * 
 * @author Administrator
 * @version 2014年10月17日
 * @see MerchantTransactionReportBo
 * @since
 */
public class MerchantTransactionReportBo implements Serializable
{
    /**
     * 意义，目的和功能，以及被用到的地方<br> o.merchant_id, o.product_id,sum(o.product_face)as
     * total_par_value,sum(o.order_sales_fee)as order_sales_fee,o.order_status
     */
    private static final long serialVersionUID = 1L;

    private Long merchantId;// 商户

    private Long productId;// 产品id

    private BigDecimal totalSalesFee;// 销售金额

    private BigDecimal totalParValue;// 总面值

    private Long transactionNum;// 数量

    private String reportsStatus;// 状态

    private Date beginTime;// 开始统计时间

    private Date endTime;// 结束时间

    private BigDecimal costFee;// 成本金额
    
    private Long businessType;

    

	public MerchantTransactionReportBo(Long merchantId, Long productId, BigDecimal orderSalesFee,
                                       BigDecimal totalParValue, BigDecimal costFee,
                                       Long transactionNum, int reportStatus,Long businessType)
    {
        super();
        this.merchantId = merchantId;
        this.productId = productId;
        this.totalSalesFee = orderSalesFee;
        this.totalParValue = totalParValue;
        this.costFee = costFee;
        this.transactionNum = transactionNum;
        this.reportsStatus = reportStatus + "";
        this.businessType = businessType;
    }

    public MerchantTransactionReportBo(Long merchantId, Long productId, BigDecimal orderSalesFee,
                                       BigDecimal totalParValue, Long transactionNum,
                                       int reportStatus,Long businessType)
    {
        super();
        this.merchantId = merchantId;
        this.productId = productId;
        this.totalSalesFee = orderSalesFee;
        this.totalParValue = totalParValue;
        this.transactionNum = transactionNum;
        this.reportsStatus = reportStatus + "";
        this.businessType = businessType;
    }

    public MerchantTransactionReportBo()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    public Long getMerchantId()
    {
        return merchantId;
    }

    public void setMerchantId(Long merchantId)
    {
        this.merchantId = merchantId;
    }

    public Long getProductId()
    {
        return productId;
    }

    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

    public BigDecimal getTotalSalesFee()
    {
        return totalSalesFee;
    }

    public void setTotalSalesFee(BigDecimal totalSalesFee)
    {
        this.totalSalesFee = totalSalesFee;
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

    public String getReportsStatus()
    {
        return reportsStatus;
    }

    public void setReportsStatus(String reportsStatus)
    {
        this.reportsStatus = reportsStatus;
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

    public Long getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Long businessType) {
		this.businessType = businessType;
	}
    @Override
    public String toString()
    {
        return "MerchantTransactionReportBo [merchantId=" + merchantId + ", productId="
               + productId + ", totalSalesFee=" + totalSalesFee + ", totalParValue="
               + totalParValue + ", transactionNum=" + transactionNum + ", reportsStatus="
               + reportsStatus + ", beginTime=" + beginTime + ", endTime=" + endTime
               + ", costFee=" + costFee + "]";
    }

}
