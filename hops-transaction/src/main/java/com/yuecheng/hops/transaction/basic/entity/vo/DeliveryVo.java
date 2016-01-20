package com.yuecheng.hops.transaction.basic.entity.vo;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class DeliveryVo implements Serializable
{

    public static final long serialVersionUID = -8191412929110894976L;

    public Long              deliveryId;

    public Long              orderNo;

    public String            merchantOrderNo;

    public Date              deliveryStartTime;

    public Date              preDeliveryTime;

    public Integer           deliveryStatus;

    public Long              productId;

    public BigDecimal        productFace;

    public BigDecimal        productSaleDiscount;

    public BigDecimal        successFee;

    public BigDecimal        costDiscount;

    public BigDecimal        costFee;

    public Date              deliveryFinishTime;

    public String            deliveryResult;

    public Long              errorCode;

    public Date              nextQueryTime;

    public Integer           queryFlag;

    public String            queryMsg;

    public Long              queryTimes;

    public String            supplyMerchantOrderNo;

    public String            userCode;

    public String            productName;

    public String            merchantName;

    public Long getDeliveryId()
    {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId)
    {
        this.deliveryId = deliveryId;
    }

    public Long getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(Long orderNo)
    {
        this.orderNo = orderNo;
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

    public String getMerchantName()
    {
        return merchantName;
    }

    public void setMerchantName(String merchantName)
    {
        this.merchantName = merchantName;
    }

    @Override
    public String toString()
    {
        return "DeliveryVo [deliveryId=" + deliveryId + ", orderNo=" + orderNo
               + ", merchantOrderNo=" + merchantOrderNo + ", deliveryStartTime="
               + deliveryStartTime + ", preDeliveryTime=" + preDeliveryTime + ", deliveryStatus="
               + deliveryStatus + ", productId=" + productId + ", productFace=" + productFace
               + ", productSaleDiscount=" + productSaleDiscount + ", successFee=" + successFee
               + ", costDiscount=" + costDiscount + ", costFee=" + costFee
               + ", deliveryFinishTime=" + deliveryFinishTime + ", deliveryResult="
               + deliveryResult + ", errorCode=" + errorCode + ", nextQueryTime=" + nextQueryTime
               + ", queryFlag=" + queryFlag + ", queryMsg=" + queryMsg + ", queryTimes="
               + queryTimes + ", supplyMerchantOrderNo=" + supplyMerchantOrderNo + ", userCode="
               + userCode + ", productName=" + productName + ", merchantName=" + merchantName
               + "]";
    }
}
