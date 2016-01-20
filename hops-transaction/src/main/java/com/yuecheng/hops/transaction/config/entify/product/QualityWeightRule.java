package com.yuecheng.hops.transaction.config.entify.product;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "quality_weight_rule")
public class QualityWeightRule implements Serializable
{

    private static final long serialVersionUID = -5692703934466148771L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QualityWeightRuleIdSeq")
    @SequenceGenerator(name = "QualityWeightRuleIdSeq", sequenceName = "QUALITY_WEIGHT_RULE_ID_SEQ")
    @Column(name = "id")
    private Long              id;

    @Column(name = "speed_weight")
    private Long              speedWeight;

    @Column(name = "success_weight")
    private Long              successWeight;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getSpeedWeight()
    {
        return speedWeight;
    }

    public void setSpeedWeight(Long speedWeight)
    {
        this.speedWeight = speedWeight;
    }

    public Long getSuccessWeight()
    {
        return successWeight;
    }

    public void setSuccessWeight(Long successWeight)
    {
        this.successWeight = successWeight;
    }

}
