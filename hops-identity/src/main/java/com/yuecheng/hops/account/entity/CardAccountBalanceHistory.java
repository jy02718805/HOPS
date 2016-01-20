package com.yuecheng.hops.account.entity;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "card_account_balance_History")
public class CardAccountBalanceHistory implements Serializable
{

    private static final long serialVersionUID = 1049253401700714611L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CardAccountBalanceIdSeq")
    @SequenceGenerator(name = "CardAccountBalanceIdSeq", sequenceName = "CARD_AC_BALANCE_ID_SEQ")
    @Column(name = "id")
    private Long id;

    @Column(name = "transaction_id")
    private Long transactionId; // 交易ID

    @Column(name = "account_id")
    private Long accountId; // 具体账户主键

    @Column(name = "old_value")
    private BigDecimal oldCardNum; // 修改前卡数

    @Column(name = "old_balance")
    private BigDecimal oldBalance; // 修改前总价值

    @Column(name = "new_value")
    private BigDecimal newCardNum; // 修改后卡数

    @Column(name = "new_balance")
    private BigDecimal newBalance; // 修改后总价值

    @Column(name = "create_date")
    private Date createDate; // 修改时间

    @Column(name = "type")
    private String type; // 账户日志类型：1.交易 2.加减款

    @Column(name = "desc_str", length = 100)
    private String descStr;

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getDescStr()
    {
        return descStr;
    }

    public void setDescStr(String descStr)
    {
        this.descStr = descStr;
    }

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

    public Long getTransactionId()
    {
        return transactionId;
    }

    public void setTransactionId(Long transactionId)
    {
        this.transactionId = transactionId;
    }

    public Date getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }

    public CardAccountBalanceHistory()
    {

    }

    public BigDecimal getOldBalance()
    {
        return oldBalance;
    }

    public void setOldBalance(BigDecimal oldBalance)
    {
        this.oldBalance = oldBalance;
    }

    public BigDecimal getNewBalance()
    {
        return newBalance;
    }

    public void setNewBalance(BigDecimal newBalance)
    {
        this.newBalance = newBalance;
    }

    public BigDecimal getOldCardNum()
    {
        return oldCardNum;
    }

    public void setOldCardNum(BigDecimal oldCardNum)
    {
        this.oldCardNum = oldCardNum;
    }

    public BigDecimal getNewCardNum()
    {
        return newCardNum;
    }

    public void setNewCardNum(BigDecimal newCardNum)
    {
        this.newCardNum = newCardNum;
    }

    @Override
    public String toString()
    {
        return "CardAccountBalanceHistory [id=" + id + ", transactionId=" + transactionId
               + ", accountId=" + accountId + ", oldCardNum=" + oldCardNum + ", oldBalance="
               + oldBalance + ", newCardNum=" + newCardNum + ", newBalance=" + newBalance
               + ", createDate=" + createDate + ", type=" + type + ", descStr=" + descStr + "]";
    }

}
