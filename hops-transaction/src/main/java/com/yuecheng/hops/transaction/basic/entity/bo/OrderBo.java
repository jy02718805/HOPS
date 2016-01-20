package com.yuecheng.hops.transaction.basic.entity.bo;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.basic.entity.Order;


public class OrderBo implements Serializable
{
    public static final long serialVersionUID = -1882068604517443210L;

    public Long              orderNo;

    public Integer           orderStatus;

    public Integer           notifyStatus;

    public Integer           manualFlag;

    public Long              preSuccessStatus;

    public Long              merchantId;

    public String            merchantName;

    public String            merchantOrderNo;

    public String            userCode;

    public String            orderTitle;

    public String            orderDesc;

    public Long              businessType;

    public String            businessNo;

    public String            businessChannel;

    public BigDecimal        orderFee;

    public BigDecimal        orderSalesFee;

    public Long              productId;

    public String            productNo;

    public BigDecimal        productFace;

    public BigDecimal        productSaleDiscount;

    public Long              productNum;

    public Date              orderRequestTime;

    public Date              orderTimeout;

    public Date              orderFinishTime;

    public Date              orderPreSuccessTime;

    public String            ext1;

    public String            ext2;

    public String            ext3;

    public String            ext4;

    public String            closeReason;

    public String            errorCode;

    public String            orderReason;

    public BigDecimal        orderSuccessFee;

    public Date              preOrderBindTime;

    public Long              bindTimes;

    public int               version;

    public BigDecimal        orderWaitFee;

    public List<Delivery>    deliverys;

    public Integer           deliveryStatus;

    public String            orderStatusString;

    public String            supplierName;

    public String            provinceName;

    public String            carrierName;

    public String            cityName;

    public String            number;

    public String            merchantStatus;

    public Long              deliveryId;

    public String            responseStr;

    public String            notifyUrl;

    public String            key;

    public String            sign;

    public String            info1;

    public String            info2;

    public String            info3;

    public String            merchantCode;

    public String            msg;
    
    public Integer specialDown;
    
	public BigDecimal userPay;
    
    public BigDecimal getUserPay()
	{
		return userPay;
	}

	public void setUserPay(BigDecimal userPay)
	{
		this.userPay = userPay;
	}

	public Integer getSpecialDown()
	{
		return specialDown;
	}

	public void setSpecialDown(Integer specialDown)
	{
		this.specialDown = specialDown;
	}

	public Long getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(Long orderNo)
    {
        this.orderNo = orderNo;
    }

    public Integer getManualFlag()
    {
        return manualFlag;
    }

    public void setManualFlag(Integer manualFlag)
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

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public String getMerchantStatus()
    {
        return merchantStatus;
    }

    public void setMerchantStatus(String merchantStatus)
    {
        this.merchantStatus = merchantStatus;
    }

    public Long getDeliveryId()
    {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId)
    {
        this.deliveryId = deliveryId;
    }

    public String getResponseStr()
    {
        return responseStr;
    }

    public void setResponseStr(String responseStr)
    {
        this.responseStr = responseStr;
    }

    public String getNotifyUrl()
    {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl)
    {
        this.notifyUrl = notifyUrl;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getSign()
    {
        return sign;
    }

    public void setSign(String sign)
    {
        this.sign = sign;
    }

    public String getInfo1()
    {
        return info1;
    }

    public void setInfo1(String info1)
    {
        this.info1 = info1;
    }

    public String getInfo2()
    {
        return info2;
    }

    public void setInfo2(String info2)
    {
        this.info2 = info2;
    }

    public String getInfo3()
    {
        return info3;
    }

    public void setInfo3(String info3)
    {
        this.info3 = info3;
    }

    public String getMerchantCode()
    {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode)
    {
        this.merchantCode = merchantCode;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public Integer getOrderStatus()
    {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus)
    {
        this.orderStatus = orderStatus;
    }

    public Integer getNotifyStatus()
    {
        return notifyStatus;
    }

    public void setNotifyStatus(Integer notifyStatus)
    {
        this.notifyStatus = notifyStatus;
    }

    public Integer getDeliveryStatus()
    {
        return deliveryStatus;
    }

    public void setDeliveryStatus(Integer deliveryStatus)
    {
        this.deliveryStatus = deliveryStatus;
    }

    @Override
    public String toString()
    {
        return "Order [orderNo=" + orderNo + ", orderStatus=" + orderStatus + ", notifyStatus="
               + notifyStatus + ", manualFlag=" + manualFlag + ", preSuccessStatus="
               + preSuccessStatus + ", merchantId=" + merchantId + ", merchantName="
               + merchantName + ", merchantOrderNo=" + merchantOrderNo + ", userCode=" + userCode
               + ", orderTitle=" + orderTitle + ", orderDesc=" + orderDesc + ", businessType="
               + businessType + ", businessNo=" + businessNo + ", businessChannel="
               + businessChannel + ", orderFee=" + orderFee + ", orderSalesFee=" + orderSalesFee
               + ", productId=" + productId + ", productNo=" + productNo + ", productFace="
               + productFace + ", productSaleDiscount=" + productSaleDiscount + ", productNum="
               + productNum + ", orderRequestTime=" + orderRequestTime + ", orderTimeout="
               + orderTimeout + ", orderFinishTime=" + orderFinishTime + ", orderPreSuccessTime="
               + orderPreSuccessTime + ", ext1=" + ext1 + ", ext2=" + ext2 + ", ext3=" + ext3
               + ", ext4=" + ext4 + ", closeReason=" + closeReason + ", errorCode=" + errorCode
               + ", orderReason=" + orderReason + ", orderSuccessFee=" + orderSuccessFee
               + ", preOrderBindTime=" + preOrderBindTime + ", bindTimes=" + bindTimes
               + ", version=" + version + ", orderWaitFee=" + orderWaitFee + ", deliverys="
               + deliverys + ", deliveryStatus=" + deliveryStatus + ", orderStatusString="
               + orderStatusString + ", supplierName=" + supplierName + ", provinceName="
               + provinceName + ", carrierName=" + carrierName + ", cityName=" + cityName
               + ", number=" + number + ", merchantStatus=" + merchantStatus + ", deliveryId="
               + deliveryId + ", responseStr=" + responseStr + ", notifyUrl=" + notifyUrl
               + ", key=" + key + ", sign=" + sign + ", info1=" + info1 + ", info2=" + info2
               + ", info3=" + info3 + ", merchantCode=" + merchantCode + ", msg=" + msg + "]";
    }

    public Order converOrder(OrderBo vo)
    {
        Order order = new Order();
        try
        {
            BeanUtils.copyProperties(order, vo);
        }
        catch (Exception e)
        {
            return null;
        }
        return order;
    }
}
