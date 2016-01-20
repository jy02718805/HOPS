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
 */
@Entity
@Table(name = "batch_job_detail")
public class BatchJobDetail implements Serializable
{
    /**
     * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
     */
    private static final long serialVersionUID = 1L;

    public static Logger logger = LoggerFactory.getLogger(BatchJobDetail.class);

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "batchJobDetailIdSeq")
    @SequenceGenerator(name = "batchJobDetailIdSeq", sequenceName = "batch_job_detail_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "batch_id")
    private Long batchId;

    @Column(name = "serial_num")
    private int serialNum;

    @Column(name = "phone_num")
    private String phoneNum;

    @Column(name = "status")
    private int status;

    @Column(name = "order_no")
    private Long orderNo;

    @Column(name = "face_value")
    private BigDecimal faceValue;

    @Column(name = "rmk")
    private String rmk;

    public synchronized Long getId()
    {
        return id;
    }

    public synchronized void setId(Long id)
    {
        this.id = id;
    }

    public synchronized Long getBatchId()
    {
        return batchId;
    }

    public synchronized void setBatchId(Long batchId)
    {
        this.batchId = batchId;
    }

    public synchronized int getSerialNum()
    {
        return serialNum;
    }

    public synchronized void setSerialNum(int serialNum)
    {
        this.serialNum = serialNum;
    }

    public synchronized String getPhoneNum()
    {
        return phoneNum;
    }

    public synchronized void setPhoneNum(String phoneNum)
    {
        this.phoneNum = phoneNum;
    }

    public synchronized int getStatus()
    {
        return status;
    }

    public synchronized void setStatus(int status)
    {
        this.status = status;
    }

    public synchronized Long getOrderNo()
    {
        return orderNo;
    }

    public synchronized void setOrderNo(Long orderNo)
    {
        this.orderNo = orderNo;
    }

    public synchronized BigDecimal getFaceValue()
    {
        return faceValue;
    }

    public synchronized void setFaceValue(BigDecimal faceValue)
    {
        this.faceValue = faceValue;
    }

    public synchronized String getRmk()
    {
        return rmk;
    }

    public synchronized void setRmk(String rmk)
    {
        this.rmk = rmk;
    }

}
