package com.yuecheng.hops.transaction.config.entify.fake;


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
@Table(name = "agent_query_fake_rule")
public class AgentQueryFakeRule implements Serializable
{

    private static final long serialVersionUID = -5692703934466148771L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AgentQueryFakeRuleIdSeq")
    @SequenceGenerator(name = "AgentQueryFakeRuleIdSeq", sequenceName = "AGENT_QUERY_FAKE_RULE_ID_SEQ")
    @Column(name = "id")
    private Long              id;

    @Column(name = "merchant_id")
    private Long              merchantId;

    @Column(name = "interval_time")
    private Integer           intervalTime;

    @Column(name = "interval_unit", length = 5)
    private String            intervalUnit;

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

    public Long getMerchantId()
    {
        return merchantId;
    }

    public void setMerchantId(Long merchantId)
    {
        this.merchantId = merchantId;
    }

    public Integer getIntervalTime()
    {
        return intervalTime;
    }

    public void setIntervalTime(Integer intervalTime)
    {
        this.intervalTime = intervalTime;
    }

    public String getIntervalUnit()
    {
        return intervalUnit;
    }

    public void setIntervalUnit(String intervalUnit)
    {
        this.intervalUnit = intervalUnit;
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
        return "AgentQueryFakeRule [id=" + id + ", merchantId=" + merchantId + ", intervalTime="
               + intervalTime + ", intervalUnit=" + intervalUnit + "]";
    }

}