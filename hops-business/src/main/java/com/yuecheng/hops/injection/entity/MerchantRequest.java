package com.yuecheng.hops.injection.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "Merchant_Request")
public class MerchantRequest implements Serializable
{

    private static final long serialVersionUID = -5692703934466148771L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MerchantRequestIdSeq")
    @SequenceGenerator(name = "MerchantRequestIdSeq", sequenceName = "MERCHANT_REQUEST_ID_SEQ")
    @Column(name = "id")
    private Long id;

    @Column(name = "merchant_id")
    private Long merchantId;// 商户号

    @Column(name = "interface_type", length = 32)
    private String interfaceType;// 接口定义ID

    @Column(name = "interval_time")
    private Long intervalTime;// 时间间隔量

    @Column(name = "interval_unit", length = 5)
    private String intervalUnit;// 时间间隔单位

    @Column(name = "time_difference_low")
    private Long timeDifferenceLow;

    @Column(name = "time_difference_high")
    private Long timeDifferenceHigh;

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

    public String getInterfaceType()
    {
        return interfaceType;
    }

    public void setInterfaceType(String interfaceType)
    {
        this.interfaceType = interfaceType;
    }

    public Long getIntervalTime()
    {
        return intervalTime;
    }

    public void setIntervalTime(Long intervalTime)
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

    public Long getTimeDifferenceLow()
    {
        return timeDifferenceLow;
    }

    public void setTimeDifferenceLow(Long timeDifferenceLow)
    {
        this.timeDifferenceLow = timeDifferenceLow;
    }

    public Long getTimeDifferenceHigh()
    {
        return timeDifferenceHigh;
    }

    public void setTimeDifferenceHigh(Long timeDifferenceHigh)
    {
        this.timeDifferenceHigh = timeDifferenceHigh;
    }

}
