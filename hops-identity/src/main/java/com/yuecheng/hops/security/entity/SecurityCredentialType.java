package com.yuecheng.hops.security.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * @Title: SecurityRule.java
 * @Package com.yuecheng.hops.identity.entity.security
 * @Description: 密钥类型 Copyright: Copyright (c) 2011 Company:湖南跃程网络科技有限公司
 * @author 肖进
 * @date 2014年9月1日 下午2:27:27
 * @ClassName: SecurityRule
 */
@Entity
@Table(name = "SECURITY_credential_TYPE")
public class SecurityCredentialType implements Serializable
{
    /**
     * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
     */

    private static final long serialVersionUID = 7039139218131030269L;

    /**
     * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
     */

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "securitytypeseq")
    @SequenceGenerator(name = "securitytypeseq", sequenceName = "SECURITY_TYPE_SEQ")
    @Column(name = "SECURITY_TYPE_ID")
    public Long securityTypeId;

    @Column(name = "SECURITY_TYPE_NAME")
    public String securityTypeName; // 密钥类型名称（登录密码、代理商MD5Key、供货商MD5Key、系统MD5Key、供货商公钥）

    @Column(name = "MODEL_TYPE")
    public String modelType; // 密钥类型属性：Password、MD5Key、RSA公钥

    @Column(name = "ENCRYPT_TYPE")
    public String encryptType; // 加密类型：MD5、3DES、RSA

    @Column(name = "MIN_LENGTH")
    public Long minLength; // 最小长度

    @Column(name = "MAX_LENGTH")
    public Long maxLength; // 最大长度

    @Column(name = "VALIDITY")
    public Long validity; // 有效期（0：永久，30天，100天）

    @Column(name = "STATUS")
    public Long status;

    @Column(name = "IDENTITY_TYPE")
    public String identityType; // 可使用Identity类型：Operator、Customer、Sp、Merchant（Agent、Supply）

    @ManyToOne
    @JoinColumn(name = "SECURITY_RULE_ID")
    public SecurityCredentialRule securityRule;

    @Override
    public String toString()
    {
        return "SecurityRule:[" + " ; securitytypeid=" + securityTypeId + " ; securitytypename="
               + securityTypeName + " ; modeltype=" + modelType + " ; encrypttype=" + encryptType
               + " ; minlength=" + minLength + " ; maxlength=" + maxLength + " ; validity="
               + validity + " ; status=" + status + " ; identitytype=" + identityType + "]";

    }

    /**
     * getter method
     * 
     * @return the securitytypeid
     */

    public Long getSecurityTypeId()
    {
        return securityTypeId;
    }

    /**
     * setter method
     * 
     * @param securitytypeid
     *            the securitytypeid to set
     */

    public void setSecurityTypeId(Long securityTypeId)
    {
        this.securityTypeId = securityTypeId;
    }

    /**
     * getter method
     * 
     * @return the securitytypename
     */

    public String getSecurityTypeName()
    {
        return securityTypeName;
    }

    /**
     * setter method
     * 
     * @param securitytypename
     *            the securitytypename to set
     */

    public void setSecurityTypeName(String securityTypeName)
    {
        this.securityTypeName = securityTypeName;
    }

    /**
     * getter method
     * 
     * @return the modeltype
     */

    public String getModelType()
    {
        return modelType;
    }

    /**
     * setter method
     * 
     * @param modeltype
     *            the modeltype to set
     */

    public void setModelType(String modelType)
    {
        this.modelType = modelType;
    }

    /**
     * getter method
     * 
     * @return the encrypttype
     */

    public String getEncryptType()
    {
        return encryptType;
    }

    /**
     * setter method
     * 
     * @param encrypttype
     *            the encrypttype to set
     */

    public void setEncryptType(String encryptType)
    {
        this.encryptType = encryptType;
    }

    /**
     * getter method
     * 
     * @return the minlength
     */

    public Long getMinLength()
    {
        return minLength;
    }

    /**
     * setter method
     * 
     * @param minlength
     *            the minlength to set
     */

    public void setMinLength(Long minLength)
    {
        this.minLength = minLength;
    }

    /**
     * getter method
     * 
     * @return the maxlength
     */

    public Long getMaxLength()
    {
        return maxLength;
    }

    /**
     * setter method
     * 
     * @param maxlength
     *            the maxlength to set
     */

    public void setMaxLength(Long maxLength)
    {
        this.maxLength = maxLength;
    }

    /**
     * getter method
     * 
     * @return the validity
     */

    public Long getValidity()
    {
        return validity;
    }

    /**
     * setter method
     * 
     * @param validity
     *            the validity to set
     */

    public void setValidity(Long validity)
    {
        this.validity = validity;
    }

    /**
     * getter method
     * 
     * @return the status
     */

    public Long getStatus()
    {
        return status;
    }

    /**
     * setter method
     * 
     * @param status
     *            the status to set
     */

    public void setStatus(Long status)
    {
        this.status = status;
    }

    /**
     * getter method
     * 
     * @return the identitytype
     */

    public String getIdentityType()
    {
        return identityType;
    }

    /**
     * setter method
     * 
     * @param identitytype
     *            the identitytype to set
     */

    public void setIdentityType(String identityType)
    {
        this.identityType = identityType;
    }

    /**
     * getter method
     * 
     * @return the securityRule
     */

    public SecurityCredentialRule getSecurityRule()
    {
        return securityRule;
    }

    /**
     * setter method
     * 
     * @param securityRule
     *            the securityRule to set
     */

    public void setSecurityRule(SecurityCredentialRule securityRule)
    {
        this.securityRule = securityRule;
    }

}
