package com.yuecheng.hops.security.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * SecurityCredential状态机表实体
 * 
 * @author Jinger 2014-10-22
 */
@Entity
@Table(name = "SECURITY_STATUS_TRANSFER")
public class SecurityCredentialStatusTransfer implements Serializable
{
    private static final long serialVersionUID = 6485433643249695773L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SecurityStatusTransferSeq")
    @SequenceGenerator(name = "SecurityStatusTransferSeq", sequenceName = "SECURITY_STATUS_TRANSFER_SEQ")
    @Column(name = "id")
    private Long id;

    @Column(name = "old_status")
    private String oldStatus;// 修改前状态

    @Column(name = "new_status")
    private String newStatus;// 修改后状态

    @Column(name = "action_name")
    private String actionName;// 动作名称

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getOldStatus()
    {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus)
    {
        this.oldStatus = oldStatus;
    }

    public String getNewStatus()
    {
        return newStatus;
    }

    public void setNewStatus(String newStatus)
    {
        this.newStatus = newStatus;
    }

    public String getActionName()
    {
        return actionName;
    }

    public void setActionName(String actionName)
    {
        this.actionName = actionName;
    }

    @Override
    public String toString()
    {
        return "SecurityCredentialTransfer [id=" + id + ", oldStatus=" + oldStatus
               + ", newStatus=" + newStatus + ", actionName=" + actionName + "]";
    }

}
