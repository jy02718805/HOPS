package com.yuecheng.hops.transaction.basic.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "delivery_query_status_transfer")
public class DeliveryQueryStatusTransfer extends AbstractStatusTransfer implements Serializable
{

    private static final long serialVersionUID = -1774994251458136886L;

    @Column(name = "old_query_status")
    public Integer            oldQueryStatus;                          // 订单修改前状态

    @Column(name = "new_query_status")
    public Integer            newQueryStatus;                          // 订单修改后状态

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

    public Integer getOldQueryStatus()
    {
        return oldQueryStatus;
    }

    public void setOldQueryStatus(Integer oldQueryStatus)
    {
        this.oldQueryStatus = oldQueryStatus;
    }

    public Integer getNewQueryStatus()
    {
        return newQueryStatus;
    }

    public void setNewQueryStatus(Integer newQueryStatus)
    {
        this.newQueryStatus = newQueryStatus;
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
