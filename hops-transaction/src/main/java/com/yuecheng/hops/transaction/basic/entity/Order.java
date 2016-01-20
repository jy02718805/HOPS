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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Entity
@Table(name = "yc_order")
public class Order implements Serializable
{
    public static Logger logger = LoggerFactory.getLogger(Order.class);

    public static final long serialVersionUID = -1882068604517443210L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OrderIdSeq")
    @SequenceGenerator(name = "OrderIdSeq", sequenceName = "ORDER_ID_SEQ", allocationSize = 1)
    @Column(name = "order_no")
    private Long orderNo;

    @Column(name = "order_status")
    private Integer orderStatus;

    @Column(name = "notify_status")
    private Integer notifyStatus;

    @Column(name = "manual_flag")
    private Integer manualFlag;

    @Column(name = "pre_success_status")
    private Integer preSuccessStatus;

    @Column(name = "merchant_id")
    private Long merchantId;

    @Column(name = "merchantName")
    private String merchantName;

    @Column(name = "merchant_order_no", length = 32)
    private String merchantOrderNo;

    @Column(name = "user_code", length = 20)
    private String userCode;

    @Column(name = "order_title", length = 256)
    private String orderTitle;

    @Column(name = "order_desc", length = 256)
    private String orderDesc;

    @Column(name = "business_type")
    private Long businessType;

    @Column(name = "business_no", length = 20)
    private String businessNo;

    @Column(name = "business_channel", length = 3)
    private String businessChannel;

    @Column(name = "order_fee")
    private BigDecimal orderFee;

    @Column(name = "order_sales_fee")
    private BigDecimal orderSalesFee;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_no", length = 32)
    private String productNo;

    @Column(name = "product_face")
    private BigDecimal productFace;

    @Column(name = "product_sale_discount")
    private BigDecimal productSaleDiscount;

    @Column(name = "product_num")
    private Long productNum;

    @Column(name = "order_request_time")
    private Date orderRequestTime;

    @Column(name = "order_timeout")
    private Date orderTimeout;

    @Column(name = "order_finish_time")
    private Date orderFinishTime;

    @Column(name = "order_pre_success_time")
    private Date orderPreSuccessTime;

    @Column(name = "ext1", length = 20)
    private String ext1;

    @Column(name = "ext2", length = 20)
    private String ext2;

    @Column(name = "ext3", length = 20)
    private String ext3;

    @Column(name = "ext4", length = 20)
    private String ext4;

    @Column(name = "close_reason", length = 20)
    private String closeReason;

    @Column(name = "error_code", length = 20)
    private String errorCode;

    @Column(name = "order_reason", length = 20)
    private String orderReason;

    @Column(name = "order_success_fee")
    private BigDecimal orderSuccessFee;

    @Column(name = "pre_order_bind_time")
    private Date preOrderBindTime;

    @Column(name = "bind_times")
    private Long bindTimes;

    @Column(name = "limit_bind_times")
    private Long limitBindTimes;
    
    @Column(name = "display_value")
    private BigDecimal displayValue;
    
    @Column(name = "user_pay")
    private BigDecimal userPay;
    
    @Column(name = "special_down")
    private Integer specialDown;

    @Transient
    private BigDecimal orderWaitFee;

    
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

    public Integer getManualFlag()
    {
        return manualFlag;
    }

    public void setManualFlag(Integer manualFlag)
    {
        this.manualFlag = manualFlag;
    }

    public Integer getPreSuccessStatus()
    {
        return preSuccessStatus;
    }

    public void setPreSuccessStatus(Integer preSuccessStatus)
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

    public BigDecimal getOrderWaitFee()
    {
        return orderWaitFee;
    }

    public void setOrderWaitFee(BigDecimal orderWaitFee)
    {
        this.orderWaitFee = orderWaitFee;
    }

    public Long getLimitBindTimes()
    {
        return limitBindTimes;
    }

    public void setLimitBindTimes(Long limitBindTimes)
    {
        this.limitBindTimes = limitBindTimes;
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
               + ", limitBindTimes=" + limitBindTimes + ", orderWaitFee=" + orderWaitFee + "]";
    }

}
