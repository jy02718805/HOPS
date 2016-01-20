package com.yuecheng.hops.transaction.basic.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * 订单通知实体
 * 
 * @author Jinger 2014-03-07
 */
@Entity
@Table(name = "Yc_notify")
public class Notify implements Serializable
{

    private static final long serialVersionUID = 5656803166007587938L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NotifyIdSeq")
    @SequenceGenerator(name = "NotifyIdSeq", sequenceName = "NOTIFY_ID_SEQ")
    @Column(name = "notify_id")
    private Long              notifyId;

    @Column(name = "order_no")
    private Long              orderNo;

    @Column(name = "merchant_id")
    private Long              merchantId;

    @Column(name = "notify_url")
    private String            notifyUrl;

    @Column(name = "create_time")
    private Date              createTime;

    @Column(name = "start_time")
    private Date              startTime;

    @Column(name = "end_time")
    private Date              endTime;

    @Column(name = "notify_cntr")
    private Long              notifyCntr;

    @Column(name = "limited_cntr")
    private Long              limitedCntr;

    @Column(name = "notify_status")
    private Integer           notifyStatus;

    @Column(name = "error_code")
    private Long              errorCode;

    @Column(name = "need_notify")
    private Long              needNotify;

    @Column(name = "next_notify_time")
    private Date              nextNotifyTime;

    @Column(name = "order_type")
    private Long              orderType;
    
	@Column(name = "failed_url")
	private String failedUrl;
	
    public String getFailedUrl()
	{
		return failedUrl;
	}

	public void setFailedUrl(String failedUrl)
	{
		this.failedUrl = failedUrl;
	}

	public Long getOrderType() {
		return orderType;
	}

	public void setOrderType(Long orderType) {
		this.orderType = orderType;
	}

	@Transient
    public String             responseStr;

    public Long getNotifyId()
    {
        return notifyId;
    }

    public void setNotifyId(Long notifyId)
    {
        this.notifyId = notifyId;
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

    public String getNotifyUrl()
    {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl)
    {
        this.notifyUrl = notifyUrl;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public Long getNotifyCntr()
    {
        return notifyCntr;
    }

    public void setNotifyCntr(Long notifyCntr)
    {
        this.notifyCntr = notifyCntr;
    }

    public Long getLimitedCntr()
    {
        return limitedCntr;
    }

    public void setLimitedCntr(Long limitedCntr)
    {
        this.limitedCntr = limitedCntr;
    }

    public Integer getNotifyStatus()
    {
        return notifyStatus;
    }

    public void setNotifyStatus(Integer notifyStatus)
    {
        this.notifyStatus = notifyStatus;
    }

    public Long getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(Long errorCode)
    {
        this.errorCode = errorCode;
    }

    public Long getNeedNotify()
    {
        return needNotify;
    }

    public void setNeedNotify(Long needNotify)
    {
        this.needNotify = needNotify;
    }

    public Date getNextNotifyTime()
    {
        return nextNotifyTime;
    }

    public void setNextNotifyTime(Date nextNotifyTime)
    {
        this.nextNotifyTime = nextNotifyTime;
    }

    public String getResponseStr()
    {
        return responseStr;
    }

    public void setResponseStr(String responseStr)
    {
        this.responseStr = responseStr;
    }

    @Override
    public String toString()
    {
        return "Notify [notifyId=" + notifyId + ", orderNo=" + orderNo + ", merchantId="
               + merchantId + ", notifyUrl=" + notifyUrl + ", createTime=" + createTime
               + ", startTime=" + startTime + ", endTime=" + endTime + ", notifyCntr="
               + notifyCntr + ", limitedCntr=" + limitedCntr + ", notifyStatus=" + notifyStatus
               + ", errorCode=" + errorCode + ", needNotify=" + needNotify + ", nextNotifyTime="
               + nextNotifyTime +"]";
    }

}
