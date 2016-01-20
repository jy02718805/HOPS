package com.yuecheng.hops.transaction.basic.entity;


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
import javax.persistence.Transient;


@Entity
@Table(name = "yc_delivery")
public class Delivery implements Serializable
{

    public static final long serialVersionUID = -8191412929110894976L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DeliveryIdSeq")
    @SequenceGenerator(name = "DeliveryIdSeq", sequenceName = "DELIVERY_ID_SEQ", allocationSize = 1)
    @Column(name = "delivery_id")
    public Long deliveryId;

    @Column(name = "order_no")
    public Long orderNo;

    @Column(name = "merchant_id")
    public Long merchantId;

    @Column(name = "merchant_order_no", length = 32)
    public String merchantOrderNo;

    @Column(name = "delivery_start_time")
    public Date deliveryStartTime;

    @Column(name = "pre_delivery_time")
    public Date preDeliveryTime;

    @Column(name = "delivery_status")
    public Integer deliveryStatus;

    @Column(name = "product_id")
    public Long productId;

    @Column(name = "product_face")
    public BigDecimal productFace;

    @Column(name = "product_sale_discount")
    public BigDecimal productSaleDiscount;

    @Column(name = "success_fee")
    public BigDecimal successFee;

    @Column(name = "cost_discount")
    public BigDecimal costDiscount;

    @Column(name = "cost_fee")
    public BigDecimal costFee;

    @Column(name = "delivery_finish_time")
    public Date deliveryFinishTime;

    @Column(name = "delivery_result", length = 32)
    public String deliveryResult;

    @Column(name = "error_code")
    public Long errorCode;

    @Column(name = "next_query_time")
    public Date nextQueryTime;

    @Column(name = "query_flag", length = 1)
    public Integer queryFlag;

    @Column(name = "query_msg", length = 128)
    public String queryMsg;

    @Column(name = "query_times")
    public Long queryTimes;

    @Column(name = "supply_Merchant_Order_No")
    public String supplyMerchantOrderNo;
    
    @Column(name = "display_value")
    public BigDecimal displayValue;
    
    @Column(name = "par_value")
    public BigDecimal parValue;

    @Column(name = "user_code", length = 20)
    public String userCode;

	@Transient
    public String productName;

	public BigDecimal getParValue()
	{
		return parValue;
	}

	public void setParValue(BigDecimal parValue)
	{
		this.parValue = parValue;
	}

	public Long getDeliveryId()
    {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId)
    {
        this.deliveryId = deliveryId;
    }

    public BigDecimal getDisplayValue()
	{
		return displayValue;
	}

	public void setDisplayValue(BigDecimal displayValue)
	{
		this.displayValue = displayValue;
	}

	public Long getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(Long orderNo)
    {
        this.orderNo = orderNo;
    }

    public Long getMerchantId()
    {
        return merchantId;
    }

    public void setMerchantId(Long merchantId)
    {
        this.merchantId = merchantId;
    }

    public String getMerchantOrderNo()
    {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo)
    {
        this.merchantOrderNo = merchantOrderNo;
    }

    public Date getDeliveryStartTime()
    {
        return deliveryStartTime;
    }

    public void setDeliveryStartTime(Date deliveryStartTime)
    {
        this.deliveryStartTime = deliveryStartTime;
    }

    public Date getPreDeliveryTime()
    {
        return preDeliveryTime;
    }

    public void setPreDeliveryTime(Date preDeliveryTime)
    {
        this.preDeliveryTime = preDeliveryTime;
    }

    public Integer getDeliveryStatus()
    {
        return deliveryStatus;
    }

    public void setDeliveryStatus(Integer deliveryStatus)
    {
        this.deliveryStatus = deliveryStatus;
    }

    public Long getProductId()
    {
        return productId;
    }

    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

    public BigDecimal getProductFace()
    {
        return productFace;
    }

    public void setProductFace(BigDecimal productFace)
    {
        this.productFace = productFace;
    }

    public BigDecimal getProductSaleDiscount()
    {
        return productSaleDiscount;
    }

    public void setProductSaleDiscount(BigDecimal productSaleDiscount)
    {
        this.productSaleDiscount = productSaleDiscount;
    }

    public BigDecimal getSuccessFee()
    {
        return successFee;
    }

    public void setSuccessFee(BigDecimal successFee)
    {
        this.successFee = successFee;
    }

    public BigDecimal getCostDiscount()
    {
        return costDiscount;
    }

    public void setCostDiscount(BigDecimal costDiscount)
    {
        this.costDiscount = costDiscount;
    }

    public BigDecimal getCostFee()
    {
        return costFee;
    }

    public void setCostFee(BigDecimal costFee)
    {
        this.costFee = costFee;
    }

    public Date getDeliveryFinishTime()
    {
        return deliveryFinishTime;
    }

    public void setDeliveryFinishTime(Date deliveryFinishTime)
    {
        this.deliveryFinishTime = deliveryFinishTime;
    }

    public String getDeliveryResult()
    {
        return deliveryResult;
    }

    public void setDeliveryResult(String deliveryResult)
    {
        this.deliveryResult = deliveryResult;
    }

    public Long getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(Long errorCode)
    {
        this.errorCode = errorCode;
    }

    public Date getNextQueryTime()
    {
        return nextQueryTime;
    }

    public void setNextQueryTime(Date nextQueryTime)
    {
        this.nextQueryTime = nextQueryTime;
    }

    public Integer getQueryFlag()
    {
        return queryFlag;
    }

    public void setQueryFlag(Integer queryFlag)
    {
        this.queryFlag = queryFlag;
    }

    public String getQueryMsg()
    {
        return queryMsg;
    }

    public void setQueryMsg(String queryMsg)
    {
        this.queryMsg = queryMsg;
    }

    public Long getQueryTimes()
    {
        return queryTimes;
    }

    public void setQueryTimes(Long queryTimes)
    {
        this.queryTimes = queryTimes;
    }

    public String getSupplyMerchantOrderNo()
    {
        return supplyMerchantOrderNo;
    }

    public void setSupplyMerchantOrderNo(String supplyMerchantOrderNo)
    {
        this.supplyMerchantOrderNo = supplyMerchantOrderNo;
    }

    public String getUserCode()
    {
        return userCode;
    }

    public void setUserCode(String userCode)
    {
        this.userCode = userCode;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    @Override
    public String toString()
    {
        return "Delivery [deliveryId=" + deliveryId + ", orderNo=" + orderNo + ", merchantId="
               + merchantId + ", merchantOrderNo=" + merchantOrderNo + ", deliveryStartTime="
               + deliveryStartTime + ", preDeliveryTime=" + preDeliveryTime + ", deliveryStatus="
               + deliveryStatus + ", productId=" + productId + ", productFace=" + productFace
               + ", productSaleDiscount=" + productSaleDiscount + ", successFee=" + successFee
               + ", costDiscount=" + costDiscount + ", costFee=" + costFee
               + ", deliveryFinishTime=" + deliveryFinishTime + ", deliveryResult="
               + deliveryResult + ", errorCode=" + errorCode + ", nextQueryTime=" + nextQueryTime
               + ", queryFlag=" + queryFlag + ", queryMsg=" + queryMsg + ", queryTimes="
               + queryTimes + ", supplyMerchantOrderNo=" + supplyMerchantOrderNo + ", userCode="
               + userCode + ", productName=" + productName + "]";
    }
}
