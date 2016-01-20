package com.yuecheng.hops.transaction.basic.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "order_status_transfer")
public class OrderStatusTransfer extends AbstractStatusTransfer implements Serializable
{

    private static final long serialVersionUID = -1774994251458136886L;

    @Column(name = "old_order_status")
    public Long               oldOrderStatus;                           // 订单修改前状态

    @Column(name = "new_order_status")
    public Long               newOrderStatus;                           // 订单修改后状态

    @Column(name = "action_name")
    public String             actionName;                               // 动作名称

    public String getActionName()
    {
        return actionName;
    }

    public void setActionName(String actionName)
    {
        this.actionName = actionName;
    }

    public Long getOldOrderStatus()
    {
        return oldOrderStatus;
    }

    public void setOldOrderStatus(Long oldOrderStatus)
    {
        this.oldOrderStatus = oldOrderStatus;
    }

    public Long getNewOrderStatus()
    {
        return newOrderStatus;
    }

    public void setNewOrderStatus(Long newOrderStatus)
    {
        this.newOrderStatus = newOrderStatus;
    }

}
