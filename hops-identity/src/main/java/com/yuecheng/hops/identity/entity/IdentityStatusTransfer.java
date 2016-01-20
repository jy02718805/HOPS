package com.yuecheng.hops.identity.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.yuecheng.hops.common.enump.IdentityType;


@Entity
@Table(name = "identity_status_transfer")
public class IdentityStatusTransfer implements Serializable
{
    private static final long serialVersionUID = 1958138270795732120L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdentityStatusTransferSeq")
    @SequenceGenerator(name = "IdentityStatusTransferSeq", sequenceName = "identity_status_transfer_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "old_identity_status")
    private Long oldIdentityStatus;// 修改前状态

    @Column(name = "new_Identity_status")
    private Long newIdentityStatus;// 修改后状态

    @Column(name = "action_name")
    private String actionName;// 动作名称

    @Column(name = "Identity_type")
    @Enumerated(EnumType.STRING)
    private IdentityType identityType;// 用户类型

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getOldIdentityStatus()
    {
        return oldIdentityStatus;
    }

    public void setOldIdentityStatus(Long oldIdentityStatus)
    {
        this.oldIdentityStatus = oldIdentityStatus;
    }

    public Long getNewIdentityStatus()
    {
        return newIdentityStatus;
    }

    public void setNewIdentityStatus(Long newIdentityStatus)
    {
        this.newIdentityStatus = newIdentityStatus;
    }

    public String getActionName()
    {
        return actionName;
    }

    public void setActionName(String actionName)
    {
        this.actionName = actionName;
    }

    public IdentityType getIdentityType()
    {
        return identityType;
    }

    public void setIdentityType(IdentityType identityType)
    {
        this.identityType = identityType;
    }

    @Override
    public String toString()
    {
        return "IdentityStatusDefenders [id=" + id + ", oldIdentityStatus=" + oldIdentityStatus
               + ", newIdentityStatus=" + newIdentityStatus + ", actionName=" + actionName
               + ", identityType=" + identityType + "]";
    }

}
