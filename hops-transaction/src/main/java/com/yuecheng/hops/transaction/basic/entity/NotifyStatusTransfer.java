package com.yuecheng.hops.transaction.basic.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "notify_status_transfer")
public class NotifyStatusTransfer extends AbstractStatusTransfer implements Serializable
{

    private static final long serialVersionUID = -1774994251458136886L;

    @Column(name = "old_notify_status")
    public Integer            oldNotifyStatus;                         // 通知记录修改前状态

    @Column(name = "new_notify_status")
    public Integer            newNotifyStatus;                         // 通知记录修改后状态

    @Column(name = "action_name")
    public String             actionName;                              // 动作名称

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Integer getOldNotifyStatus()
    {
        return oldNotifyStatus;
    }

    public void setOldNotifyStatus(Integer oldNotifyStatus)
    {
        this.oldNotifyStatus = oldNotifyStatus;
    }

    public Integer getNewNotifyStatus()
    {
        return newNotifyStatus;
    }

    public void setNewNotifyStatus(Integer newNotifyStatus)
    {
        this.newNotifyStatus = newNotifyStatus;
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
