/*
 * 文件名：SupplyDupNumRule.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2015年10月21日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.parameter.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "supply_dup_num_rule")
public class SupplyDupNumRule implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SupplyDupNumRuleIDSEQ")
    @SequenceGenerator(name = "SupplyDupNumRuleIDSEQ", sequenceName = "supply_dup_num_rule_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "merchant_id")
    private Long merchantId;

    @Column(name = "date_unit")
    private String dateUnit;

    @Column(name = "date_interval")
    private Integer dateInterval;// 时间间隔（分）

    @Column(name = "send_times")
    private Integer sendTimes;// 次数限制

    @Transient
    private String            merchantName;
    
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getDateUnit()
    {
        return dateUnit;
    }

    public void setDateUnit(String dateUnit)
    {
        this.dateUnit = dateUnit;
    }

    public Long getMerchantId()
    {
        return merchantId;
    }

    public void setMerchantId(Long merchantId)
    {
        this.merchantId = merchantId;
    }

    public Integer getDateInterval()
    {
        return dateInterval;
    }

    public void setDateInterval(Integer dateInterval)
    {
        this.dateInterval = dateInterval;
    }

    public Integer getSendTimes()
    {
        return sendTimes;
    }

    public void setSendTimes(Integer sendTimes)
    {
        this.sendTimes = sendTimes;
    }

    public String getMerchantName()
    {
        return merchantName;
    }

    public void setMerchantName(String merchantName)
    {
        this.merchantName = merchantName;
    }

    @Override
    public String toString()
    {
        return "SupplyDupNumRule [id=" + id + ", merchantId=" + merchantId + ", dateUnit="
               + dateUnit + ", dateInterval=" + dateInterval + ", sendTimes=" + sendTimes + "]";
    }
}