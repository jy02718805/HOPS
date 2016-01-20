package com.yuecheng.hops.parameter.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "merchant_bind_times")
public class MerchantBindTimes implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MerchantBindTimesIDSEQ")
    @SequenceGenerator(name = "MerchantBindTimesIDSEQ", sequenceName = "merchant_bind_times_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "merchant_id")
    private Long merchantId;

    @Column(name = "bind_times")
    private Integer bindTimes;

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

    public Integer getBindTimes()
    {
        return bindTimes;
    }

    public void setBindTimes(Integer bindTimes)
    {
        this.bindTimes = bindTimes;
    }

    @Override
    public String toString()
    {
        return "MerchantBindTimes [id=" + id + ", merchantId=" + merchantId + ", bindTimes="
               + bindTimes + "]";
    }

}
