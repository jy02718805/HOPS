/**
 * @Title: BatchOrderRequestHandler.java
 * @Package com.yuecheng.hops.transaction.entity.order
 * @Description: TODO Copyright: Copyright (c) 2011 Company:湖南跃程网络科技有限公司
 * @author 肖进
 * @date 2014年8月14日 上午10:10:20
 * @version V1.0
 */

package com.yuecheng.hops.batch.entity;


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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @ClassName: BatchOrderRequestHandler
 * @Description: TODO
 * @author 肖进
 * @date 2014年8月14日 上午10:10:20
 */
@Entity
@Table(name = "BATCH_ORDER_REQUEST_HANDLER")
public class BatchOrderRequestHandler implements Serializable
{
    /**
     * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
     */
    private static final long serialVersionUID = 1L;

    public static Logger      logger           = LoggerFactory.getLogger(BatchOrderRequestHandler.class);

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "batchorderrequesthandlerseq")
    @SequenceGenerator(name = "batchorderrequesthandlerseq", sequenceName = "BATCH_ORDERRE_ID_SEQ")
    @Column(name = "ID")
    public Long               id;

    // 手机号码
    @Column(name = "phone_no")
    public String             phoneNo;

    // 金额
    @Column(name = "order_fee")
    public BigDecimal         orderFee;

    // 创建时间
    @Column(name = "order_request_time")
    public Date               orderRequestTime;

    // 上传文件名
    @Column(name = "up_file")
    public String             upFile;

    // 操作员
    @Column(name = "operator")
    public String             operator;

    // 状态
    @Column(name = "order_status")
    public Long               orderStatus;

    // 备注
    @Column(name = "remark")
    public String             remark;

    // 商户号
    @Column(name = "merchant_id")
    public Long               merchantId;

    // 商户订单号
    @Column(name = "merchant_order_no", length = 32)
    public String             merchantOrderNo;

    // 补单时间
    @Column(name = "order_finish_time")
    public Date               orderFinishTime;

    // 商户名
    @Column(name = "merchant_name")
    public String             merchantName;

    // 删除时间
    @Column(name = "order_delete_time")
    public Date               orderDeleteTime;

    @Override
    public String toString()
    {
        return "BatchOrderRequestHandler[" + "ID=" + id + ";" + "phoneNo=" + phoneNo + ";"
               + "orderFee=" + orderFee + ";" + "orderRequestTime=" + orderRequestTime + ";"
               + "upFile=" + upFile + ";" + "operator=" + operator + ";" + "orderStatus="
               + orderStatus + ";" + "remark=" + remark + ";" + "merchantId=" + merchantId + ";"
               + "merchantOrderNo=" + merchantOrderNo + ";" + "orderFinishTime=" + orderFinishTime
               + ";" + "merchantName=" + merchantName + ";" + "]";
    }

    /**
     * getter method
     * 
     * @return the logger
     */

    public static Logger getLogger()
    {
        return logger;
    }

    /**
     * setter method
     * 
     * @param logger
     *            the logger to set
     */

    public static void setLogger(Logger logger)
    {
        BatchOrderRequestHandler.logger = logger;
    }

    /**
     * getter method
     * 
     * @return the id
     */

    public Long getId()
    {
        return id;
    }

    /**
     * setter method
     * 
     * @param id
     *            the id to set
     */

    public void setId(Long id)
    {
        this.id = id;
    }

    /**
     * getter method
     * 
     * @return the phoneNo
     */

    public String getPhoneNo()
    {
        return phoneNo;
    }

    /**
     * setter method
     * 
     * @param phoneNo
     *            the phoneNo to set
     */

    public void setPhoneNo(String phoneNo)
    {
        this.phoneNo = phoneNo;
    }

    /**
     * getter method
     * 
     * @return the orderFee
     */

    public BigDecimal getOrderFee()
    {
        return orderFee;
    }

    /**
     * setter method
     * 
     * @param orderFee
     *            the orderFee to set
     */

    public void setOrderFee(BigDecimal orderFee)
    {
        this.orderFee = orderFee;
    }

    /**
     * getter method
     * 
     * @return the orderRequestTime
     */

    public Date getOrderRequestTime()
    {
        return orderRequestTime;
    }

    /**
     * setter method
     * 
     * @param orderRequestTime
     *            the orderRequestTime to set
     */

    public void setOrderRequestTime(Date orderRequestTime)
    {
        this.orderRequestTime = orderRequestTime;
    }

    /**
     * getter method
     * 
     * @return the upFile
     */

    public String getUpFile()
    {
        return upFile;
    }

    /**
     * setter method
     * 
     * @param upFile
     *            the upFile to set
     */

    public void setUpFile(String upFile)
    {
        this.upFile = upFile;
    }

    /**
     * getter method
     * 
     * @return the operator
     */

    public String getOperator()
    {
        return operator;
    }

    /**
     * setter method
     * 
     * @param operator
     *            the operator to set
     */

    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    /**
     * getter method
     * 
     * @return the orderStatus
     */

    public Long getOrderStatus()
    {
        return orderStatus;
    }

    /**
     * setter method
     * 
     * @param orderStatus
     *            the orderStatus to set
     */

    public void setOrderStatus(Long orderStatus)
    {
        this.orderStatus = orderStatus;
    }

    /**
     * getter method
     * 
     * @return the remark
     */

    public String getRemark()
    {
        return remark;
    }

    /**
     * setter method
     * 
     * @param remark
     *            the remark to set
     */

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    /**
     * getter method
     * 
     * @return the merchantId
     */

    public Long getMerchantId()
    {
        return merchantId;
    }

    /**
     * setter method
     * 
     * @param merchantId
     *            the merchantId to set
     */

    public void setMerchantId(Long merchantId)
    {
        this.merchantId = merchantId;
    }

    /**
     * getter method
     * 
     * @return the merchantOrderNo
     */

    public String getMerchantOrderNo()
    {
        return merchantOrderNo;
    }

    /**
     * setter method
     * 
     * @param merchantOrderNo
     *            the merchantOrderNo to set
     */

    public void setMerchantOrderNo(String merchantOrderNo)
    {
        this.merchantOrderNo = merchantOrderNo;
    }

    /**
     * getter method
     * 
     * @return the orderFinishTime
     */

    public Date getOrderFinishTime()
    {
        return orderFinishTime;
    }

    /**
     * setter method
     * 
     * @param orderFinishTime
     *            the orderFinishTime to set
     */

    public void setOrderFinishTime(Date orderFinishTime)
    {
        this.orderFinishTime = orderFinishTime;
    }

    /**
     * getter method
     * 
     * @return the serialversionuid
     */

    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }

    /**
     * getter method
     * 
     * @return the merchantName
     */

    public String getMerchantName()
    {
        return merchantName;
    }

    /**
     * setter method
     * 
     * @param merchantName
     *            the merchantName to set
     */

    public void setMerchantName(String merchantName)
    {
        this.merchantName = merchantName;
    }

    /**
     * getter method
     * 
     * @return the orderDeleteTime
     */

    public Date getOrderDeleteTime()
    {
        return orderDeleteTime;
    }

    /**
     * setter method
     * 
     * @param orderDeleteTime
     *            the orderDeleteTime to set
     */

    public void setOrderDeleteTime(Date orderDeleteTime)
    {
        this.orderDeleteTime = orderDeleteTime;
    }

}
