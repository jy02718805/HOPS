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
 * @see ProfitReportBo
 * @since
 */
public class ProfitReportBo implements Serializable
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

    private BigDecimal orderSalesFee;// 销售金额

    private BigDecimal successFace;// 成功

    private BigDecimal costFee;// 成本

    private BigDecimal profit;// 利润

    private BigDecimal totalParValue;// 总面值

    private Long profitNum;// 数量

    private String reportStatus;// 状态

    private Date beginTime;// 开始统计时间

    private Date endTime;// 结束时间
    
    private Long businessType;

    public Long getBusinessType() {
		return businessType;
	}





	public void setBusinessType(Long businessType) {
		this.businessType = businessType;
	}





	public ProfitReportBo()
    {
        super();
    }
    
    
    
    

    public ProfitReportBo(Long merchantId, String carrierNo, String province, String city,
                          BigDecimal parValue, BigDecimal orderSalesFee, BigDecimal successFace,
                          BigDecimal costFee, BigDecimal profit, BigDecimal totalParValue,
                          Long profitNum, int reportStatus)
    {
        super();
        this.merchantId = merchantId;
        this.carrierNo = carrierNo;
        this.province = province;
        this.city = city;
        this.parValue = parValue;
        this.orderSalesFee = orderSalesFee;
        this.successFace = successFace;
        this.costFee = costFee;
        this.profit = profit;
        this.totalParValue = totalParValue;
        this.profitNum = profitNum;
        this.reportStatus = reportStatus+"";
    }
    public ProfitReportBo(Long merchantId, String carrierNo, String province, String city,
            BigDecimal parValue,Long businessType, BigDecimal orderSalesFee, BigDecimal successFace,
            BigDecimal costFee, BigDecimal profit, BigDecimal totalParValue,
            Long profitNum, int reportStatus)
	{
		super();
		this.merchantId = merchantId;
		this.carrierNo = carrierNo;
		this.province = province;
		this.city = city;
		this.parValue = parValue;
		this.orderSalesFee = orderSalesFee;
		this.successFace = successFace;
		this.costFee = costFee;
		this.profit = profit;
		this.totalParValue = totalParValue;
		this.profitNum = profitNum;
		this.reportStatus = reportStatus+"";
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

    public BigDecimal getOrderSalesFee()
    {
        return orderSalesFee;
    }

    public void setOrderSalesFee(BigDecimal orderSalesFee)
    {
        this.orderSalesFee = orderSalesFee;
    }

    public BigDecimal getProfit()
    {
        return profit;
    }

    public void setProfit(BigDecimal profit)
    {
        this.profit = profit;
    }

    public Long getProfitNum()
    {
        return profitNum;
    }

    public void setProfitNum(Long profitNum)
    {
        this.profitNum = profitNum;
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

    @Override
    public String toString()
    {
        return "ProfitReportPo [merchantId=" + merchantId + ", carrierNo=" + carrierNo
               + ", province=" + province + ", city=" + city + ", parValue=" + parValue
               + ", orderSalesFee=" + orderSalesFee + ", successFace=" + successFace
               + ", costFee=" + costFee + ", profit=" + profit + ", totalParValue="
               + totalParValue + ", profitNum=" + profitNum + ", reportStatus=" + reportStatus
               + ", beginTime=" + beginTime + ", endTime=" + endTime + "]";
    }

}
