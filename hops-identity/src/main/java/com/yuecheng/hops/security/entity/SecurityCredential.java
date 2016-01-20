package com.yuecheng.hops.security.entity;


import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.security.MD5Util;


/**
 * 密匙表实体
 * 
 * @author：Jinger
 * @date：2013-09-26
 */

@Entity
@Table(name = "security_credential")
@SequenceGenerator(name = "SecurityCredentialIdSeq", sequenceName = "Security_Credential_ID_SEQ")
public class SecurityCredential implements Serializable
{
    private static final long serialVersionUID = -1148566081036561845L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SecurityCredentialIdSeq")
    @Column(name = "security_id")
    private Long securityId;

    @JoinColumn(name = "identity_Id", nullable = false)
    private Long identityId;

    @Column(name = "identity_type")
    @Enumerated(EnumType.STRING)
    private IdentityType identityType;

    @Column(name = "status")
    private String status;

    @Column(name = "security_name")
    private String securityName;

    @Column(name = "security_value")
    private String securityValue;

    @ManyToOne
    @JoinColumn(name = "security_type_id")
    private SecurityCredentialType securityType;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "update_user")
    private String updateUser;

    @Column(name = "validity_date")
    private Date validityDate; // 到期时间

    public String getSecurityName()
    {
        return securityName;
    }

    public void setSecurityName(String securityName)
    {
        this.securityName = securityName;
    }

    public String getSecurityValue()
    {
        return securityValue;
    }

    public void setSecurityValue(String securityValue)
    {
        this.securityValue = securityValue;
    }

    public Long getSecurityId()
    {
        return securityId;
    }

    public void setSecurityId(Long securityId)
    {
        this.securityId = securityId;
    }

    public IdentityType getIdentityType()
    {
        return identityType;
    }

    public void setIdentityType(IdentityType identityType)
    {
        this.identityType = identityType;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Long getIdentityId()
    {
        return identityId;
    }

    public void setIdentityId(Long identityId)
    {
        this.identityId = identityId;
    }

    public static String updateSecurityPropertyValue()
    {
        String value = MD5Util.getMD5Sign(UUID.randomUUID().toString());
        return value;
    }

    public Date getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }

    public String getCreateUser()
    {
        return createUser;
    }

    public void setCreateUser(String createUser)
    {
        this.createUser = createUser;
    }

    public Date getUpdateDate()
    {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate)
    {
        this.updateDate = updateDate;
    }

    public String getUpdateUser()
    {
        return updateUser;
    }

    public void setUpdateUser(String updateUser)
    {
        this.updateUser = updateUser;
    }

    public Date getValidityDate()
    {
        return validityDate;
    }

    public void setValidityDate(Date validityDate)
    {
        this.validityDate = validityDate;
    }

    public SecurityCredentialType getSecurityType()
    {
        return securityType;
    }

    public void setSecurityType(SecurityCredentialType securityType)
    {
        this.securityType = securityType;
    }

    @Override
    public String toString()
    {
        return "SecurityCredential [id=" + securityId + ", identityId=" + identityId
               + ", identityType=" + identityType + ", status=" + status + ", securityName="
               + securityName + ", securityValue=" + securityValue + ", securityType="
               + securityType + ", createDate=" + createDate + ", createUser=" + createUser
               + ", updateDate=" + updateDate + ", updateUser=" + updateUser + ", validityDate="
               + validityDate + "]";
    }

}
