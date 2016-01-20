/*
 * 文件名：Blacklist.java 版权：Copyright by www.365haoyou.com 描述： 修改人：yao 修改时间：2015年5月25日 跟踪单号： 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.blacklist.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * 黑名单实体类
 * 
 * @author yao
 * @version 2015年5月25日
 * @see Blacklist
 * @since
 */
@Entity
@Table(name = "blacklist")
public class Blacklist implements Serializable
{
    private static final long serialVersionUID = -8476008804327744702L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BlacklistIdSeq")
    @SequenceGenerator(name = "BlacklistIdSeq", sequenceName = "BLACKLIST_ID_SEQ", allocationSize = 1)
    @Column(name = "blacklist_id")
    private Long blacklistId;

    @Column(name = "blacklist_no")
    private String blacklistNo;

    @Column(name = "business_type")
    private String businessType;

    @Column(name = "used_times")
    private int usedTimes;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "remark")
    private String remark;

    public Long getBlacklistId()
    {
        return blacklistId;
    }

    public void setBlacklistId(Long blacklistId)
    {
        this.blacklistId = blacklistId;
    }

    public String getBlacklistNo()
    {
        return blacklistNo;
    }

    public void setBlacklistNo(String blacklistNo)
    {
        this.blacklistNo = blacklistNo;
    }

    public String getBusinessType()
    {
        return businessType;
    }

    public void setBusinessType(String businessType)
    {
        this.businessType = businessType;
    }

    public int getUsedTimes()
    {
        return usedTimes;
    }

    public void setUsedTimes(int usedTimes)
    {
        this.usedTimes = usedTimes;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }

    @Override
    public String toString()
    {
        return "Blacklist [id=" + blacklistId + ", blacklistNo=" + blacklistNo
               + ", businessType=" + businessType + ", usedTimes=" + usedTimes
               + ", createTime=" + createTime + ", createTime=" + createTime
               + ", remark=" + remark + "]";
    }

}
