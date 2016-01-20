package com.yuecheng.hops.account.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;


@MappedSuperclass
public abstract class Account implements Serializable
{

    public static final long serialVersionUID = 2832490526079048506L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AccountIdSeq")
    @SequenceGenerator(name = "AccountIdSeq", sequenceName = "AC_ID_SEQ", allocationSize = 1)
    @Column(name = "account_id")
    protected Long accountId;

    @ManyToOne
    @JoinColumn(name = "account_type_id")
    protected AccountType accountType;

    public AccountType getAccountType()
    {
        return accountType;
    }

    public void setAccountType(AccountType accountType)
    {
        this.accountType = accountType;
    }

    public Long getAccountId()
    {
        return accountId;
    }

    public void setAccountId(Long accountId)
    {
        this.accountId = accountId;
    }

    @Column(name = "status", length = 10)
    public String status; // 状态

    @Column(name = "rmk", length = 200)
    public String rmk;

    @Column(name = "last_update_user", length = 50)
    public String lastUpdateUser;

    @Column(name = "last_update_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date lastUpdateDate;

    @Column(name = "creator", length = 50)
    public String creator;

    @Column(name = "create_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date createDate;

    @Column(name = "sign", length = 64)
    public String sign;

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getRmk()
    {
        return rmk;
    }

    public void setRmk(String rmk)
    {
        this.rmk = rmk;
    }

    public String getLastUpdateUser()
    {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser)
    {
        this.lastUpdateUser = lastUpdateUser;
    }

    public Date getLastUpdateDate()
    {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate)
    {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getCreator()
    {
        return creator;
    }

    public void setCreator(String creator)
    {
        this.creator = creator;
    }

    public Date getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }

    public String getSign()
    {
        return sign;
    }

    public void setSign(String sign)
    {
        this.sign = sign;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}
