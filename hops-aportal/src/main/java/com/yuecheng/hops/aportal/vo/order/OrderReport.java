package com.yuecheng.hops.aportal.vo.order;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.yuecheng.hops.transaction.basic.entity.Delivery;


public class OrderReport
{
    public Long orderNo;

    public Long orderStatus;

    public Long notifyStatus;

    public Long manualFlag;

    public Long preSuccessStatus;

    public Long merchantId;

    public String merchantName;

    public String merchantOrderNo;

    public String userCode;

    public String orderTitle;

    public String orderDesc;

    public Long businessType;

    public String businessNo;

    public String businessChannel;

    public BigDecimal orderFee;

    public BigDecimal orderSalesFee;

    public Long productId;

    public String productNo;

    public BigDecimal productFace;

    public BigDecimal productSaleDiscount;

    public Long productNum;

    public Date orderRequestTime;

    public Date orderTimeout;

    public Date orderFinishTime;

    public Date orderPreSuccessTime;

    public String ext1;

    public String ext2;

    public String ext3;

    public String ext4;

    public String closeReason;

    public String errorCode;

    public String orderReason;

    public BigDecimal orderSuccessFee;

    public Date preOrderBindTime;

    public Long bindTimes;

    public int version;

    public BigDecimal orderWaitFee;

    public List<Delivery> deliverys;

    public Long deliveryStatus;

    public String orderStatusString;// 订单状态字符串

    public String supplierName;

    public String provinceName;

    public String carrierName;

    public String cityName;

    public Long getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(Long orderNo)
    {
        this.orderNo = orderNo;
    }

    public Long getOrderStatus()
    {
        return orderStatus;
    }

    public void setOrderStatus(Long orderStatus)
    {
        this.orderStatus = orderStatus;
    }

    public Long getNotifyStatus()
    {
        return notifyStatus;
    }

    public void setNotifyStatus(Long notifyStatus)
    {
        this.notifyStatus = notifyStatus;
    }

    public Long getManualFlag()
    {
        return manualFlag;
    }

    public void setManualFlag(Long manualFlag)
    {
        this.manualFlag = manualFlag;
    }

    public Long getPreSuccessStatus()
    {
        return preSuccessStatus;
    }

    public void setPreSuccessStatus(Long preSuccessStatus)
    {
        this.preSuccessStatus = preSuccessStatus;
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

    public String getMerchantOrderNo()
    {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo)
    {
        this.merchantOrderNo = merchantOrderNo;
    }

    public String getUserCode()
    {
        return userCode;
    }

    public void setUserCode(String userCode)
    {
        this.userCode = userCode;
    }

    public String getOrderTitle()
    {
        return orderTitle;
    }

    public void setOrderTitle(String orderTitle)
    {
        this.orderTitle = orderTitle;
    }

    public String getOrderDesc()
    {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc)
    {
        this.orderDesc = orderDesc;
    }

    public Long getBusinessType()
    {
        return businessType;
    }

    public void setBusinessType(Long businessType)
    {
        this.businessType = businessType;
    }

    public String getBusinessNo()
    {
        return businessNo;
    }

    public void setBusinessNo(String businessNo)
    {
        this.businessNo = businessNo;
    }

    public String getBusinessChannel()
    {
        return businessChannel;
    }

    public void setBusinessChannel(String businessChannel)
    {
        this.businessChannel = businessChannel;
    }

    public BigDecimal getOrderFee()
    {
        return orderFee;
    }

    public void setOrderFee(BigDecimal orderFee)
    {
        this.orderFee = orderFee;
    }

    public BigDecimal getOrderSalesFee()
    {
        return orderSalesFee;
    }

    public void setOrderSalesFee(BigDecimal orderSalesFee)
    {
        this.orderSalesFee = orderSalesFee;
    }

    public Long getProductId()
    {
        return productId;
    }

    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

    public String getProductNo()
    {
        return productNo;
    }

    public void setProductNo(String productNo)
    {
        this.productNo = productNo;
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

    public Long getProductNum()
    {
        return productNum;
    }

    public void setProductNum(Long productNum)
    {
        this.productNum = productNum;
    }

    public Date getOrderRequestTime()
    {
        return orderRequestTime;
    }

    public void setOrderRequestTime(Date orderRequestTime)
    {
        this.orderRequestTime = orderRequestTime;
    }

    public Date getOrderTimeout()
    {
        return orderTimeout;
    }

    public void setOrderTimeout(Date orderTimeout)
    {
        this.orderTimeout = orderTimeout;
    }

    public Date getOrderFinishTime()
    {
        return orderFinishTime;
    }

    public void setOrderFinishTime(Date orderFinishTime)
    {
        this.orderFinishTime = orderFinishTime;
    }

    public Date getOrderPreSuccessTime()
    {
        return orderPreSuccessTime;
    }

    public void setOrderPreSuccessTime(Date orderPreSuccessTime)
    {
        this.orderPreSuccessTime = orderPreSuccessTime;
    }

    public String getExt1()
    {
        return ext1;
    }

    public void setExt1(String ext1)
    {
        this.ext1 = ext1;
    }

    public String getExt2()
    {
        return ext2;
    }

    public void setExt2(String ext2)
    {
        this.ext2 = ext2;
    }

    public String getExt3()
    {
        return ext3;
    }

    public void setExt3(String ext3)
    {
        this.ext3 = ext3;
    }

    public String getExt4()
    {
        return ext4;
    }

    public void setExt4(String ext4)
    {
        this.ext4 = ext4;
    }

    public String getCloseReason()
    {
        return closeReason;
    }

    public void setCloseReason(String closeReason)
    {
        this.closeReason = closeReason;
    }

    public String getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }

    public String getOrderReason()
    {
        return orderReason;
    }

    public void setOrderReason(String orderReason)
    {
        this.orderReason = orderReason;
    }

    public BigDecimal getOrderSuccessFee()
    {
        return orderSuccessFee;
    }

    public void setOrderSuccessFee(BigDecimal orderSuccessFee)
    {
        this.orderSuccessFee = orderSuccessFee;
    }

    public Date getPreOrderBindTime()
    {
        return preOrderBindTime;
    }

    public void setPreOrderBindTime(Date preOrderBindTime)
    {
        this.preOrderBindTime = preOrderBindTime;
    }

    public Long getBindTimes()
    {
        return bindTimes;
    }

    public void setBindTimes(Long bindTimes)
    {
        this.bindTimes = bindTimes;
    }

    public int getVersion()
    {
        return version;
    }

    public void setVersion(int version)
    {
        this.version = version;
    }

    public BigDecimal getOrderWaitFee()
    {
        return orderWaitFee;
    }

    public void setOrderWaitFee(BigDecimal orderWaitFee)
    {
        this.orderWaitFee = orderWaitFee;
    }

    public List<Delivery> getDeliverys()
    {
        return deliverys;
    }

    public void setDeliverys(List<Delivery> deliverys)
    {
        this.deliverys = deliverys;
    }

    public Long getDeliveryStatus()
    {
        return deliveryStatus;
    }

    public void setDeliveryStatus(Long deliveryStatus)
    {
        this.deliveryStatus = deliveryStatus;
    }

    public String getOrderStatusString()
    {
        return orderStatusString;
    }

    public void setOrderStatusString(String orderStatusString)
    {
        this.orderStatusString = orderStatusString;
    }

    public String getSupplierName()
    {
        return supplierName;
    }

    public void setSupplierName(String supplierName)
    {
        this.supplierName = supplierName;
    }

    public String getProvinceName()
    {
        return provinceName;
    }

    public void setProvinceName(String provinceName)
    {
        this.provinceName = provinceName;
    }

    public String getCarrierName()
    {
        return carrierName;
    }

    public void setCarrierName(String carrierName)
    {
        this.carrierName = carrierName;
    }

    public String getCityName()
    {
        return cityName;
    }

    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }
}
