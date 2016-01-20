package com.yuecheng.hops.account.entity;


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
@Table(name = "account_Operation_History")
public class AccountOperationHistory implements Serializable
{

    private static final long serialVersionUID = 1535121547570142720L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AccountOperationIdSeq")
    @SequenceGenerator(name = "AccountOperationIdSeq", sequenceName = "AC_OPERATION_ID_SEQ")
    @Column(name = "id")
    public Long id;

    @Column(name = "account_id")
    public Long accountId; // 具体账户主键

    @Column(name = "type_model", length = 10)
    public String typeModel; // 账户类型

    @Column(name = "old_status", length = 10)
    public String oldStatus; // 修改前状态

    @Column(name = "new_status", length = 10)
    public String newStatus; // 修改后状态

    @Column(name = "desc_str", length = 100)
    public String descStr; // 描述

    @Column(name = "creator", length = 50)
    public String operator; // 操作人(名字)

    @Column(name = "create_date")
    public Date operatorDate; // 修改时间

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getAccountId()
    {
        return accountId;
    }

    public void setAccountId(Long accountId)
    {
        this.accountId = accountId;
    }

    public String getTypeModel()
    {
        return typeModel;
    }

    public void setTypeModel(String typeModel)
    {
        this.typeModel = typeModel;
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

    public AccountOperationHistory()
    {

    }

    public String getDescStr()
    {
        return descStr;
    }

    public void setDescStr(String descStr)
    {
        this.descStr = descStr;
    }

    public String getOperator()
    {
        return operator;
    }

    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    public Date getOperatorDate()
    {
        return operatorDate;
    }

    public void setOperatorDate(Date operatorDate)
    {
        this.operatorDate = operatorDate;
    }

    @Override
    public String toString()
    {
        return "AccountOperationHistory [id=" + id + ", accountId=" + accountId + ", typeModel="
               + typeModel + ", oldStatus=" + oldStatus + ", newStatus=" + newStatus
               + ", descStr=" + descStr + ", operator=" + operator + ", operatorDate="
               + operatorDate + "]";
    }

}
