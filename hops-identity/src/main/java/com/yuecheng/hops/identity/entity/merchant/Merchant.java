package com.yuecheng.hops.identity.entity.merchant;


import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.enump.MerchantLevel;
import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.common.enump.MirrorRoleType;
import com.yuecheng.hops.identity.entity.AbstractOrganizationIdentity;


@Entity
@Table(name = "MERCHANT")
public class Merchant extends AbstractOrganizationIdentity
{
    private static final long serialVersionUID = -5072577113752402476L;

    @Column(name = "MERCHANT_NAME", length = 64)
    private String merchantName;

    @Column(name = "PARENT_IDENTITY_ID")
    private Long parentIdentityId;

    @Column(name = "DISCRIPTION", length = 64)
    private String discription;

    @Column(name = "MERCHANT_LEVEL", length = 20)
    @Enumerated(EnumType.STRING)
    private MerchantLevel merchantLevel;

    @Column(name = "IS_REBATE")
    private Long isRebate;

    @Column(name = "MERCHANT_TYPE", length = 10)
    @Enumerated(EnumType.STRING)
    private MerchantType merchantType;
    
    @Column(name = "create_user", length = 20)
    private String createUser;
    
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_user", length = 20)
    private String updateUser;

    @Column(name = "update_time")
    private Date updateTime;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "code", column = @Column(name = "MERCHANT_CODE"))})
    private MerchantCode merchantCode;

    public Merchant()
    {
        this.setIdentityType(IdentityType.MERCHANT);
        this.setIdentityRoleType(MirrorRoleType.OrganizationRole);
    }

    public Merchant(String merchantName, MerchantType merchantType, MerchantCode merchantCode)
    {
        this.setIdentityType(IdentityType.MERCHANT);
        this.setIdentityRoleType(MirrorRoleType.OrganizationRole);
        this.merchantName = merchantName;
        this.merchantType = merchantType;
        this.merchantCode = merchantCode;
    }

    public String getMerchantName()
    {
        return merchantName;
    }

    public void setMerchantName(String merchantName)
    {
        this.merchantName = merchantName;
    }

    public MerchantType getMerchantType()
    {
        return merchantType;
    }

    public void setMerchantType(MerchantType merchantType)
    {
        this.merchantType = merchantType;
    }

    public MerchantCode getMerchantCode()
    {
        return merchantCode;
    }

    public void setMerchantCode(MerchantCode merchantCode)
    {
        this.merchantCode = merchantCode;
        this.addIdentifier(merchantCode);
    }

    public Long getParentIdentityId()
    {
        return parentIdentityId;
    }

    public void setParentIdentityId(Long parentIdentityId)
    {
        this.parentIdentityId = parentIdentityId;
    }

    public String getDiscription()
    {
        return discription;
    }

    public void setDiscription(String discription)
    {
        this.discription = discription;
    }

    public MerchantLevel getMerchantLevel()
    {
        return merchantLevel;
    }

    public void setMerchantLevel(MerchantLevel merchantLevel)
    {
        this.merchantLevel = merchantLevel;
    }

    public Long getIsRebate()
    {
        return isRebate;
    }

    public void setIsRebate(Long isRebate)
    {
        this.isRebate = isRebate;
    }

    public String getCreateUser()
    {
        return createUser;
    }

    public void setCreateUser(String createUser)
    {
        this.createUser = createUser;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public String getUpdateUser()
    {
        return updateUser;
    }

    public void setUpdateUser(String updateUser)
    {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    @Override
    public String toString()
    {
        return "Merchant [merchantName=" + merchantName + ", parentIdentityId=" + parentIdentityId
               + ", discription=" + discription + ", merchantLevel=" + merchantLevel
               + ", isRebate=" + isRebate + ", merchantType=" + merchantType + ", createUser="
               + createUser + ", createTime=" + createTime + ", updateUser=" + updateUser
               + ", updateTime=" + updateTime + ", merchantCode=" + merchantCode + "]";
    }


}
