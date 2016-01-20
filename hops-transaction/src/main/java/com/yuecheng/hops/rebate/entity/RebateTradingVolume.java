package com.yuecheng.hops.rebate.entity;


import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "Rebate_Trading_Volume")
@SequenceGenerator(name = "SeqRebateTradingVolumeId", sequenceName = "SEQ_REBATE_Trading_Volume_ID")
public class RebateTradingVolume implements Serializable
{

    private static final long serialVersionUID = -3587916614862886723L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SeqRebateTradingVolumeId")
    @Column(name = "Rebate_Trading_Volume_ID")
    private Long rebateTradingVolumeID; //返佣交易量区间配置

    @Column(name = "Discount")
    private BigDecimal discount;        //折扣、实际金额（根据返佣类型来定）

    @Column(name = "Rebate_Rule_ID")
    private Long rebateRuleId;          //返佣规则ID

    @Column(name = "Trading_Volume_Low")
    private Long tradingVolumeLow;      //交易量下线

    @Column(name = "Trading_Volume_High")
    private Long tradingVolumeHigh;     //交易量上限

    public Long getRebateTradingVolumeID()
    {
        return rebateTradingVolumeID;
    }

    public void setRebateTradingVolumeID(Long rebateTradingVolumeID)
    {
        this.rebateTradingVolumeID = rebateTradingVolumeID;
    }

    public BigDecimal getDiscount()
    {
        return discount;
    }

    public void setDiscount(BigDecimal discount)
    {
        this.discount = discount;
    }

    public Long getRebateRuleId()
    {
        return rebateRuleId;
    }

    public void setRebateRuleId(Long rebateRuleId)
    {
        this.rebateRuleId = rebateRuleId;
    }

    public Long getTradingVolumeLow()
    {
        return tradingVolumeLow;
    }

    public void setTradingVolumeLow(Long tradingVolumeLow)
    {
        this.tradingVolumeLow = tradingVolumeLow;
    }

    public Long getTradingVolumeHigh()
    {
        return tradingVolumeHigh;
    }

    public void setTradingVolumeHigh(Long tradingVolumeHigh)
    {
        this.tradingVolumeHigh = tradingVolumeHigh;
    }

    @Override
    public String toString()
    {
        return "RebateTradingVolume [rebateTradingVolumeID=" + rebateTradingVolumeID
               + ", discount=" + discount + ", rebateRuleId=" + rebateRuleId
               + ", tradingVolumeLow=" + tradingVolumeLow + ", tradingVolumeHigh="
               + tradingVolumeHigh + "]";
    }

}
