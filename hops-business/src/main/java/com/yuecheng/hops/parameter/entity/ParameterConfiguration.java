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
@Table(name = "Parameter_configuration")
public class ParameterConfiguration implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ParameterConfigurationIDSEQ")
    @SequenceGenerator(name = "ParameterConfigurationIDSEQ", sequenceName = "parameter_configuration_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "CONSTANT_VALUE")
    private String constantValue;

    @Column(name = "CONSTANT_NAME")
    private String constantName;

    @Column(name = "CONSTANT_UNIT_VALUE")
    private String constantUnitValue;

    @Column(name = "CONSTANT_UNIT_NAME")
    private String constantUnitName;

    @Column(name = "EXT1")
    private String ext1;

    @Column(name = "EXT2")
    private String ext2;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getConstantValue()
    {
        return constantValue;
    }

    public void setConstantValue(String constantValue)
    {
        this.constantValue = constantValue;
    }

    public String getConstantName()
    {
        return constantName;
    }

    public void setConstantName(String constantName)
    {
        this.constantName = constantName;
    }

    public String getConstantUnitValue()
    {
        return constantUnitValue;
    }

    public void setConstantUnitValue(String constantUnitValue)
    {
        this.constantUnitValue = constantUnitValue;
    }

    public String getConstantUnitName()
    {
        return constantUnitName;
    }

    public void setConstantUnitName(String constantUnitName)
    {
        this.constantUnitName = constantUnitName;
    }

    public String getExt1()
    {
        return ext1;
    }

    public void setExt1(String ext1)
    {
        this.ext1 = ext1;
    }

    public String getExt2()
    {
        return ext2;
    }

    public void setExt2(String ext2)
    {
        this.ext2 = ext2;
    }

    @Override
    public String toString()
    {
        return "ParameterConfiguration [id=" + id + ", constantValue=" + constantValue
               + ", constantName=" + constantName + ", constantUnitValue=" + constantUnitValue
               + ", constantUnitName=" + constantUnitName + ", ext1=" + ext1 + ", ext2=" + ext2
               + "]";
    }

}
