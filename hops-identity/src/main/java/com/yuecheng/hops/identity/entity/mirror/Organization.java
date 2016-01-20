package com.yuecheng.hops.identity.entity.mirror;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.yuecheng.hops.identity.entity.OrganizationRole;
import com.yuecheng.hops.identity.entity.merchant.Merchant;


@Entity
@Table(name = "organization")
@SequenceGenerator(name = "OrganizationIdSeq", sequenceName = "ORG_ID_SEQ")
public class Organization implements Serializable
{
    private static final long serialVersionUID = 5967471564724820380L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OrganizationIdSeq")
    @Column(name = "organizationId")
    private Long organizationId; // 主键ID

    @Column(name = "organization_name", length = 64)
    private String organizationName; // 组织名称

    @Column(name = "organization_registration_no", length = 50)
    private String organizationRegistrationNo; // 工商注册号

    @Column(name = "organization_registration_addr", length = 200)
    private String organizationRegistrationAddr; // 组织注册地址

    @Column(name = "organization_industry", length = 50)
    private String organizationIndustry; // 组织行业

    @Column(name = "organization_website", length = 50)
    private String organizationWebsite; // 组织网站

    @Column(name = "legal", length = 20)
    private String legal; // 法人

    @Column(name = "postcode", length = 20)
    private String postcode; // 邮编

    @Column(name = "regdate")
    private Date regDate; // 注册日期

    @Column(name = "enddate")
    private Date endDate; // 使用截止日期

    @Column(name = "open_type", length = 2)
    private String openType; // 来源1:手工新增2:在线注册

    @Column(name = "organization_level", length = 20)
    private String organizationLevel; // 组织级别

    @Column(name = "copurl", length = 50)
    private String copurl; // 组织网址

    @Column(name = "area_code", length = 50)
    private String areaCode; // 地区编码

    @Column(name = "area_name", length = 50)
    private String areaName; // 地区名称

    @Transient
    private List<Merchant> merchants;

    @Transient
    private OrganizationRole organizationRole;

    public Organization()
    {}

    public Long getOrganizationId()
    {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId)
    {
        this.organizationId = organizationId;
    }

    public String getOrganizationName()
    {
        return organizationName;
    }

    public void setOrganizationName(String organizationName)
    {
        this.organizationName = organizationName;
    }

    public String getOrganizationRegistrationNo()
    {
        return organizationRegistrationNo;
    }

    public void setOrganizationRegistrationNo(String organizationRegistrationNo)
    {
        this.organizationRegistrationNo = organizationRegistrationNo;
    }

    public String getOrganizationRegistrationAddr()
    {
        return organizationRegistrationAddr;
    }

    public void setOrganizationRegistrationAddr(String organizationRegistrationAddr)
    {
        this.organizationRegistrationAddr = organizationRegistrationAddr;
    }

    public String getOrganizationIndustry()
    {
        return organizationIndustry;
    }

    public void setOrganizationIndustry(String organizationIndustry)
    {
        this.organizationIndustry = organizationIndustry;
    }

    public String getOrganizationWebsite()
    {
        return organizationWebsite;
    }

    public void setOrganizationWebsite(String organizationWebsite)
    {
        this.organizationWebsite = organizationWebsite;
    }

    public String getLegal()
    {
        return legal;
    }

    public void setLegal(String legal)
    {
        this.legal = legal;
    }

    public String getPostcode()
    {
        return postcode;
    }

    public void setPostcode(String postcode)
    {
        this.postcode = postcode;
    }

    public Date getRegDate()
    {
        return regDate;
    }

    public void setRegDate(Date regDate)
    {
        this.regDate = regDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public String getOpenType()
    {
        return openType;
    }

    public void setOpenType(String openType)
    {
        this.openType = openType;
    }

    public String getOrganizationLevel()
    {
        return organizationLevel;
    }

    public void setOrganizationLevel(String organizationLevel)
    {
        this.organizationLevel = organizationLevel;
    }

    public String getCopurl()
    {
        return copurl;
    }

    public void setCopurl(String copurl)
    {
        this.copurl = copurl;
    }

    public String getAreaCode()
    {
        return areaCode;
    }

    public void setAreaCode(String areaCode)
    {
        this.areaCode = areaCode;
    }

    public String getAreaName()
    {
        return areaName;
    }

    public void setAreaName(String areaName)
    {
        this.areaName = areaName;
    }

    public OrganizationRole getOrganizationRole()
    {
        return organizationRole;
    }

    public void setOrganizationRole(OrganizationRole organizationRole)
    {
        this.organizationRole = organizationRole;
    }

    public List<Merchant> getMerchants()
    {
        return merchants;
    }

    public void setMerchants(List<Merchant> merchants)
    {
        this.merchants = merchants;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}
