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
@Table(name = "interface_sendtimes_conf")
public class InterfaceSendtimesConf implements Serializable
{
    private static final long serialVersionUID = -8910290116781491320L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "InterfaceSendtimesConfIdSeq")
    @SequenceGenerator(name = "InterfaceSendtimesConfIdSeq", sequenceName = "SEQ_interface_sendtimes_conf")
    @Column(name = "id")
    private Long id;

    @Column(name = "merchant_id")
    private Long merchantId;// 商户ID

    @Column(name = "merchant_name", length = 32)
    private String merchantName;// 用户名称

    @Column(name = "interface_type", length = 32)
    private String interfaceType;// 接口类型：下单、通知、查询

    @Column(name = "total_times")
    private Long totalTimes;// 接口发送最大次数

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

    public String getMerchantName()
    {
        return merchantName;
    }

    public void setMerchantName(String merchantName)
    {
        this.merchantName = merchantName;
    }

    public String getInterfaceType()
    {
        return interfaceType;
    }

    public void setInterfaceType(String interfaceType)
    {
        this.interfaceType = interfaceType;
    }

    public Long getTotalTimes()
    {
        return totalTimes;
    }

    public void setTotalTimes(Long totalTimes)
    {
        this.totalTimes = totalTimes;
    }

    @Override
    public String toString()
    {
        return "InterfaceSendtimesConf [id=" + id + ", merchantId=" + merchantId
               + ", merchantName=" + merchantName + ", interfaceType=" + interfaceType
               + ", totalTimes=" + totalTimes + "]";
    }

}
