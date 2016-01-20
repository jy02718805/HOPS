package com.yuecheng.hops.identity.entity.customer;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.yuecheng.hops.common.enump.UserTypeEnum;
import com.yuecheng.hops.identity.entity.AbstractPersonalIdentity;


/**
 * Customer表实体
 * 
 * @author：Jinger
 * @date：2013-09-24
 */
@Entity
@Table(name = "customer")
@SequenceGenerator(name = "CustomerIdSeq", sequenceName = "Customer_ID_SEQ")
public class Customer extends AbstractPersonalIdentity
{
    private static final long serialVersionUID = -6392913775232736728L;

    @Column(name = "user_type", length = 20)
    @Enumerated(EnumType.STRING)
    private UserTypeEnum userType;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_Name", length = 20)
    private String updateName;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "remark", length = 200)
    private String remark;

    @Column(name = "customer_name", length = 20)
    private String customerName;

    @Column(name = "display_name", length = 20)
    private String displayName;

    @Transient
    private String loginPwd;

    @Transient
    private String payPwd;

    public String getLoginPwd()
    {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd)
    {
        this.loginPwd = loginPwd;
    }

    public String getPayPwd()
    {
        return payPwd;
    }

    public void setPayPwd(String payPwd)
    {
        this.payPwd = payPwd;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public UserTypeEnum getUserType()
    {
        return userType;
    }

    public void setUserType(UserTypeEnum userType)
    {
        this.userType = userType;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public String getUpdateName()
    {
        return updateName;
    }

    public void setUpdateName(String updateName)
    {
        this.updateName = updateName;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    @Override
    public String toString()
    {
        return "Customer [userType=" + userType + ", createTime=" + createTime + ", updateName="
               + updateName + ", updateTime=" + updateTime + ", remark=" + remark
               + ", customerName=" + customerName + ", displayName=" + displayName + "]";
    }

}
