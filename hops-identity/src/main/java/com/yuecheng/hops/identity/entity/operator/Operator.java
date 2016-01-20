package com.yuecheng.hops.identity.entity.operator;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.enump.OperatorType;
import com.yuecheng.hops.identity.entity.AbstractPersonalIdentity;


@Entity
@Table(name = "Operator")
public class Operator extends AbstractPersonalIdentity
{
    private static final long serialVersionUID = 5778805644245421650L;

    @JoinColumn(name = "OWNER_IDENTITY_ID", nullable = false)
    private Long ownerIdentityId;

    @Column(name = "owner_identity_type", length = 2)
    @Enumerated(EnumType.STRING)
    private IdentityType ownerIdentityType;

    @Column(name = "last_update_user")
    private String lastUpdateUser;

    @Column(name = "last_update_date")
    private Date lastUpdateDate;

    @Column(name = "remark")
    private String remark;

    @Column(name = "operator_name", length = 20)
    private String operatorName;

    @Column(name = "display_name", length = 20)
    private String displayName;

    @Transient
    private String loginPassword;

    @Column(name = "operator_TYPE", length = 20)
    @Enumerated(EnumType.STRING)
    public OperatorType operatorType;

    @Transient
    private String ownerIdentityName;

    public String getOperatorName()
    {
        return operatorName;
    }

    public void setOperatorName(String operatorName)
    {
        this.operatorName = operatorName;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public String getOwnerIdentityName()
    {
        return ownerIdentityName;
    }

    public void setOwnerIdentityName(String ownerIdentityName)
    {
        this.ownerIdentityName = ownerIdentityName;
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

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public String getLoginPassword()
    {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword)
    {
        this.loginPassword = loginPassword;
    }

    public OperatorType getOperatorType()
    {
        return operatorType;
    }

    public void setOperatorType(OperatorType operatorType)
    {
        this.operatorType = operatorType;
    }

    public Long getOwnerIdentityId()
    {
        return ownerIdentityId;
    }

    public void setOwnerIdentityId(Long ownerIdentityId)
    {
        this.ownerIdentityId = ownerIdentityId;
    }

    public IdentityType getOwnerIdentityType()
    {
        return ownerIdentityType;
    }

    public void setOwnerIdentityType(IdentityType ownerIdentityType)
    {
        this.ownerIdentityType = ownerIdentityType;
    }

    @Override
    public String toString()
    {
        return "Operator [ownerIdentityId=" + ownerIdentityId + ", ownerIdentityType="
               + ownerIdentityType + ", lastUpdateUser=" + lastUpdateUser + ", lastUpdateDate="
               + lastUpdateDate + ", remark=" + remark + ", operatorName=" + operatorName
               + ", displayName=" + displayName + ", loginPassword=" + loginPassword
               + ", operatorType=" + operatorType + ", ownerIdentityName=" + ownerIdentityName
               + "]";
    }

    public Operator()
    {

    }

}
