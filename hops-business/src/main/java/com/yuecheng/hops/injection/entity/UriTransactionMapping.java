package com.yuecheng.hops.injection.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * 订单通知实体
 * 
 * @author Jinger 2014-03-07
 */
@Entity
@Table(name = "uri_transaction_mapping")
public class UriTransactionMapping implements Serializable
{

    private static final long serialVersionUID = 5656803166007587938L;

    // HOST_IP VARCHAR2(20) not null,
    // HOST_PORT VARCHAR2(4) default 80 not null,
    // ACTION_NAME VARCHAR2(100),
    // MERCHANT_ID NUMBER,
    // INTERFACE_TYPE VARCHAR2(30),
    // TRANSACTION_CODE NUMBER

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NotifyIdSeq")
    @SequenceGenerator(name = "NotifyIdSeq", sequenceName = "NOTIFY_ID_SEQ")
    @Column(name = "id")
    private Long              id;

    @Column(name = "HOST_IP")
    private String            hostIp;

    @Column(name = "HOST_PORT")
    private String            hostPort;

    @Column(name = "ACTION_NAME")
    private String            actionName;

    @Column(name = "MERCHANT_ID")
    private Long              merchantId;

    @Column(name = "INTERFACE_TYPE")
    private String            interfaceType;

    @Column(name = "TRANSACTION_CODE")
    private String            transactionCode;
    
    @Column(name = "BUSINESS_TYPE")
    private int businessType;
    
    @Column(name = "SPECIAL_DOWN")
    private Integer specialDown;
    
    public Integer getSpecialDown()
	{
		return specialDown;
	}

	public void setSpecialDown(Integer specialDown)
	{
		this.specialDown = specialDown;
	}

	public int getBusinessType()
	{
		return businessType;
	}

	public void setBusinessType(int businessType)
	{
		this.businessType = businessType;
	}

	public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getHostIp()
    {
        return hostIp;
    }

    public void setHostIp(String hostIp)
    {
        this.hostIp = hostIp;
    }

    public String getHostPort()
    {
        return hostPort;
    }

    public void setHostPort(String hostPort)
    {
        this.hostPort = hostPort;
    }

    public String getActionName()
    {
        return actionName;
    }

    public void setActionName(String actionName)
    {
        this.actionName = actionName;
    }

    public Long getMerchantId()
    {
        return merchantId;
    }

    public void setMerchantId(Long merchantId)
    {
        this.merchantId = merchantId;
    }

    public String getInterfaceType()
    {
        return interfaceType;
    }

    public void setInterfaceType(String interfaceType)
    {
        this.interfaceType = interfaceType;
    }

    public String getTransactionCode()
    {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode)
    {
        this.transactionCode = transactionCode;
    }

    @Override
    public String toString()
    {
        return "UriTransactionMapping [id=" + id + ", hostIp=" + hostIp + ", hostPort=" + hostPort
               + ", actionName=" + actionName + ", merchantId=" + merchantId + ", interfaceType="
               + interfaceType + ", transactionCode=" + transactionCode + "]";
    }

}
