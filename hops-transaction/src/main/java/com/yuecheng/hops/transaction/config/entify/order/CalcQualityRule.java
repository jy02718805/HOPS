package com.yuecheng.hops.transaction.config.entify.order;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "calc_quality_rule")
public class CalcQualityRule implements Serializable
{

    private static final long serialVersionUID = -5692703934466148771L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CalcQualityRuleIdSeq")
    @SequenceGenerator(name = "CalcQualityRuleIdSeq", sequenceName = "CALC_QUALITY_RULE_ID_SEQ")
    @Column(name = "id")
    private Long              id;

    @Column(name = "factor")
    private Long              factor;

    @Column(name = "order_num_low")
    private Long              orderNumLow;

    @Column(name = "order_num_high")
    private Long              orderNumHigh;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getFactor()
    {
        return factor;
    }

    public void setFactor(Long factor)
    {
        this.factor = factor;
    }

    public Long getOrderNumLow()
    {
        return orderNumLow;
    }

    public void setOrderNumLow(Long orderNumLow)
    {
        this.orderNumLow = orderNumLow;
    }

    public Long getOrderNumHigh()
    {
        return orderNumHigh;
    }

    public void setOrderNumHigh(Long orderNumHigh)
    {
        this.orderNumHigh = orderNumHigh;
    }

}
