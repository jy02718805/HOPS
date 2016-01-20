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
@Table(name = "interface_constant")
public class InterfaceConstant implements Serializable
{
    private static final long serialVersionUID = -3794739578533065823L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "InterfaceConstantIdSeq")
    @SequenceGenerator(name = "InterfaceConstantIdSeq", sequenceName = "SEQ_interface_constant")
    @Column(name = "id")
    private Long id;

    @Column(name = "identity_id")
    private Long identityId;// 用户ID

    @Column(name = "identity_name", length = 32)
    private String identityName;// 用户名称

    @Column(name = "identity_type", length = 32)
    private String identityType;// 用户类型

    @Column(name = "key", length = 32)
    private String key;// 关键字

    @Column(name = "value", length = 32)
    private String value;// 值

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getIdentityId()
    {
        return identityId;
    }

    public void setIdentityId(Long identityId)
    {
        this.identityId = identityId;
    }

    public String getIdentityName()
    {
        return identityName;
    }

    public void setIdentityName(String identityName)
    {
        this.identityName = identityName;
    }

    public String getIdentityType()
    {
        return identityType;
    }

    public void setIdentityType(String identityType)
    {
        this.identityType = identityType;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return "InterfaceConstant [id=" + id + ", identityId=" + identityId + ", identityName="
               + identityName + ", identityType=" + identityType + ", key=" + key + ", value="
               + value + "]";
    }

}
