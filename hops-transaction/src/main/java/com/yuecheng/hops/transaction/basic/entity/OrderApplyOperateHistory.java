package com.yuecheng.hops.transaction.basic.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "order_apply_operate_history")
public class OrderApplyOperateHistory implements Serializable
{

    public static final long serialVersionUID = -8191412929110894976L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OrderApplyOperateHistoryIdSeq")
    @SequenceGenerator(name = "OrderApplyOperateHistoryIdSeq", sequenceName = "ORDER_OP_HISTORY_ID_SEQ")
    @Column(name = "id")
    public Long              id;

    @Column(name = "operator_name", length = 20)
    private String           operatorName;

    @Column(name = "create_date")
    public Date              createDate;

    @Column(name = "order_no")
    public Long              orderNo;

    @Column(name = "action")
    public String            action;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getOperatorName()
    {
        return operatorName;
    }

    public void setOperatorName(String operatorName)
    {
        this.operatorName = operatorName;
    }

    public Date getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }

    public Long getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(Long orderNo)
    {
        this.orderNo = orderNo;
    }

    public String getAction()
    {
        return action;
    }

    public void setAction(String action)
    {
        this.action = action;
    }

    @Override
    public String toString()
    {
        return "OrderApplyOperateHistory [id=" + id + ", operatorName=" + operatorName
               + ", createDate=" + createDate + ", orderNo=" + orderNo + ", action=" + action
               + "]";
    }

}
