package com.yuecheng.hops.account.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "account_status_transfer")
public class AccountStatusTransfer implements Serializable
{

    private static final long serialVersionUID = -1774994251458136886L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AccountStatusDefendersIdSeq")
    @SequenceGenerator(name = "AccountStatusDefendersIdSeq", sequenceName = "ACCOUNT_STATUS_DEFENDERS_ID_SEQ")
    @Column(name = "id")
    public Long id;

    @Column(name = "type_model", length = 10)
    public String typeModel; // funds or card

    @Column(name = "original_account_status")
    public Long originalAccountStatus; // 账户修改前状态

    @Column(name = "target_account_status")
    public Long targetAccountStatus; // 账户修改后状态

    @Column(name = "action_name")
    public String actionName; // 动作名称

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTypeModel()
    {
        return typeModel;
    }

    public void setTypeModel(String typeModel)
    {
        this.typeModel = typeModel;
    }

    public Long getOriginalAccountStatus()
    {
        return originalAccountStatus;
    }

    public void setOriginalAccountStatus(Long originalAccountStatus)
    {
        this.originalAccountStatus = originalAccountStatus;
    }

    public Long getTargetAccountStatus()
    {
        return targetAccountStatus;
    }

    public void setTargetAccountStatus(Long targetAccountStatus)
    {
        this.targetAccountStatus = targetAccountStatus;
    }

    public String getActionName()
    {
        return actionName;
    }

    public void setActionName(String actionName)
    {
        this.actionName = actionName;
    }
}
