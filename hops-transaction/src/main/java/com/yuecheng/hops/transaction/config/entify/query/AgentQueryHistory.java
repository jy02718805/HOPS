package com.yuecheng.hops.transaction.config.entify.query;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "down_query_history")
public class AgentQueryHistory implements Serializable
{

    private static final long serialVersionUID = -5692703934466148771L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DownQueryHistoryIdSeq")
    @SequenceGenerator(name = "DownQueryHistoryIdSeq", sequenceName = "DOWN_QUERY_HISTORY_ID_SEQ")
    @Column(name = "id")
    private Long              id;

    @Column(name = "merchant_id")
    private Long              merchantId;                               // 商户号

    @Column(name = "times")
    private Long              times;                                    // 时间段内的查询次数

    @Column(name = "begin_time", length = 5)
    private Date              beginTime;                                // 查询开始时间

    @Column(name = "end_time")
    private Date              endTime;                                  // 查询结束时间

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

    public Long getTimes()
    {
        return times;
    }

    public void setTimes(Long times)
    {
        this.times = times;
    }

    public Date getBeginTime()
    {
        return beginTime;
    }

    public void setBeginTime(Date beginTime)
    {
        this.beginTime = beginTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

}
