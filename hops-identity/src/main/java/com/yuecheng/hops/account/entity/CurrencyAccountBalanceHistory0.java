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
@Table(name = "ccy_account_balance_History_0")
public class CurrencyAccountBalanceHistory0 implements Serializable
{

    private static final long serialVersionUID = 1049253401700714611L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CurrencyAccountBalanceIdSeq")
    @SequenceGenerator(name = "CurrencyAccountBalanceIdSeq", sequenceName = "CCY_AC_BALANCE_ID_SEQ", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "transaction_id")
    private Long transactionId; // 交易ID

    @Column(name = "account_id")
    private Long accountId; // 具体账户主键

    @Column(name = "new_available_balance")
    private BigDecimal newAvailableBalance; // 修改后可用余额

    @Column(name = "new_unavailable_banlance")
    private BigDecimal newUnavailableBanlance; // 修改后不可用余额

    @Column(name = "new_creditable_banlance")
    private BigDecimal newCreditableBanlance; // 修改后授信余额

    @Column(name = "create_date")
    private Date createDate; // 修改时间

    @Column(name = "type")
    private String type; // 账户日志类型：1.交易 2.加减款 冻结

    @Column(name = "desc_str", length = 100)
    private String descStr; // 订单号【xxxx】,冻结金额为【xxxx】元

    @Column(name = "identity_name")
    private String identityName;

    @Column(name = "change_amount")
    private BigDecimal changeAmount; // xxx.xxxx

    @Column(name = "account_type_id")
    private Long accountTypeId;

    @Column(name = "remark", length = 200)
    private String remark; // 备注

    // @Column(name = "reason")
    // private String reason;//往供货商发货

    public String getType()
    {
        return type;
    }

    public Long getAccountTypeId()
    {
        return accountTypeId;
    }

    public void setAccountTypeId(Long accountTypeId)
    {
        this.accountTypeId = accountTypeId;
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

    public Long getTransactionId()
    {
        return transactionId;
    }

    public void setTransactionId(Long transactionId)
    {
        this.transactionId = transactionId;
    }

    public Long getAccountId()
    {
        return accountId;
    }

    public void setAccountId(Long accountId)
    {
        this.accountId = accountId;
    }

    public BigDecimal getNewAvailableBalance()
    {
        return newAvailableBalance;
    }

    public void setNewAvailableBalance(BigDecimal newAvailableBalance)
    {
        this.newAvailableBalance = newAvailableBalance;
    }

    public BigDecimal getNewUnavailableBanlance()
    {
        return newUnavailableBanlance;
    }

    public void setNewUnavailableBanlance(BigDecimal newUnavailableBanlance)
    {
        this.newUnavailableBanlance = newUnavailableBanlance;
    }

    public BigDecimal getNewCreditableBanlance()
    {
        return newCreditableBanlance;
    }

    public void setNewCreditableBanlance(BigDecimal newCreditableBanlance)
    {
        this.newCreditableBanlance = newCreditableBanlance;
    }

    public Date getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }

    public String getIdentityName()
    {
        return identityName;
    }

    public void setIdentityName(String identityName)
    {
        this.identityName = identityName;
    }

    public BigDecimal getChangeAmount()
    {
        return changeAmount;
    }

    public void setChangeAmount(BigDecimal changeAmount)
    {
        this.changeAmount = changeAmount;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    @Override
    public String toString()
    {
        return "CurrencyAccountBalanceHistory [id=" + id + ", transactionId=" + transactionId
               + ", accountId=" + accountId + ", newAvailableBalance=" + newAvailableBalance
               + ", newUnavailableBanlance=" + newUnavailableBanlance + ", newCreditableBanlance="
               + newCreditableBanlance + ", createDate=" + createDate + ", type=" + type
               + ", descStr=" + descStr + ", identityName=" + identityName + ", changeAmount="
               + changeAmount + "]";
    }
}
