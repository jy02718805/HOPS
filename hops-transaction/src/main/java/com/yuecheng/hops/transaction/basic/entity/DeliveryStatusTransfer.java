package com.yuecheng.hops.transaction.basic.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "delivery_status_transfer")
public class DeliveryStatusTransfer extends AbstractStatusTransfer implements Serializable
{

    private static final long serialVersionUID = -1774994251458136886L;

    @Column(name = "old_delivery_status")
    public Integer            oldDeliveryStatus;                       // 发货记录修改前状态

    @Column(name = "new_delivery_status")
    public Integer            newDeliveryStatus;                       // 发货记录修改后状态

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

    public Integer getOldDeliveryStatus()
    {
        return oldDeliveryStatus;
    }

    public void setOldDeliveryStatus(Integer oldDeliveryStatus)
    {
        this.oldDeliveryStatus = oldDeliveryStatus;
    }

    public Integer getNewDeliveryStatus()
    {
        return newDeliveryStatus;
    }

    public void setNewDeliveryStatus(Integer newDeliveryStatus)
    {
        this.newDeliveryStatus = newDeliveryStatus;
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
