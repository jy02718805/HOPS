package com.yuecheng.hops.mportal.vo.account;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 账户日志
 */
public class CurrencyAccountBalanceHistoryVo implements Serializable
{

    private static final long serialVersionUID = 1L;

    public String transactionId;

    public String accountId;

    public String accountTypeId;

    public String beginDate;

    public String endDate;

    public String identityName;

    public String transactionNo;

    private Long id;

    private BigDecimal oldAvailableBalance; // 修改前可用余额

    private BigDecimal oldUnavailableBanlance; // 修改前不可用余额

    private BigDecimal oldCreditableBanlance; // 修改前授信余额

    private BigDecimal newAvailableBalance; // 修改后可用余额

    private BigDecimal newUnavailableBanlance; // 修改后不可用余额

    private BigDecimal newCreditableBanlance; // 修改后授信余额

    private BigDecimal changeAmount;

    private Date createDate; // 修改时间

    private String type; // 账户日志类型：1.交易 2.加减款

    private String identityId;

    private String relation;

    private String descStr;

    public String getTransactionId()
    {
        return transactionId;
    }

    public void setTransactionId(String transactionId)
    {
        this.transactionId = transactionId;
    }

    public String getAccountId()
    {
        return accountId;
    }

    public void setAccountId(String accountId)
    {
        this.accountId = accountId;
    }

    public String getAccountTypeId()
    {
        return accountTypeId;
    }

    public void setAccountTypeId(String accountTypeId)
    {
        this.accountTypeId = accountTypeId;
    }

    public String getBeginDate()
    {
        return beginDate;
    }

    public void setBeginDate(String beginDate)
    {
        this.beginDate = beginDate;
    }

    public String getEndDate()
    {
        return endDate;
    }

    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }

    public String getIdentityName()
    {
        return identityName;
    }

    public void setIdentityName(String identityName)
    {
        this.identityName = identityName;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getTransactionNo()
    {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo)
    {
        this.transactionNo = transactionNo;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public BigDecimal getOldAvailableBalance()
    {
        return oldAvailableBalance;
    }

    public void setOldAvailableBalance(BigDecimal oldAvailableBalance)
    {
        this.oldAvailableBalance = oldAvailableBalance;
    }

    public BigDecimal getOldUnavailableBanlance()
    {
        return oldUnavailableBanlance;
    }

    public void setOldUnavailableBanlance(BigDecimal oldUnavailableBanlance)
    {
        this.oldUnavailableBanlance = oldUnavailableBanlance;
    }

    public BigDecimal getOldCreditableBanlance()
    {
        return oldCreditableBanlance;
    }

    public void setOldCreditableBanlance(BigDecimal oldCreditableBanlance)
    {
        this.oldCreditableBanlance = oldCreditableBanlance;
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

    public BigDecimal getChangeAmount()
    {
        return changeAmount;
    }

    public void setChangeAmount(BigDecimal changeAmount)
    {
        this.changeAmount = changeAmount;
    }

    public Date getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }

    public String getDescStr()
    {
        return descStr;
    }

    public void setDescStr(String descStr)
    {
        this.descStr = descStr;
    }

    public String getIdentityId()
    {
        return identityId;
    }

    public void setIdentityId(String identityId)
    {
        this.identityId = identityId;
    }

    public String getRelation()
    {
        return relation;
    }

    public void setRelation(String relation)
    {
        this.relation = relation;
    }

    @Override
    public String toString()
    {
        return "CurrencyAccountBalanceHistoryVo [transactionId=" + transactionId + ", accountId="
               + accountId + ", accountTypeId=" + accountTypeId + ", beginDate=" + beginDate
               + ", endDate=" + endDate + ", identityName=" + identityName + ", transactionNo="
               + transactionNo + ", id=" + id + ", oldAvailableBalance=" + oldAvailableBalance
               + ", oldUnavailableBanlance=" + oldUnavailableBanlance + ", oldCreditableBanlance="
               + oldCreditableBanlance + ", newAvailableBalance=" + newAvailableBalance
               + ", newUnavailableBanlance=" + newUnavailableBanlance + ", newCreditableBanlance="
               + newCreditableBanlance + ", changeAmount=" + changeAmount + ", createDate="
               + createDate + ", type=" + type + ", descStr=" + descStr + "]";
    }

}
