package com.yuecheng.hops.report.entity;


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
@Table(name = "Account_Report")
public class AccountReportInfo implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AccountReportIdSeq")
    @SequenceGenerator(name = "AccountReportIdSeq", sequenceName = "ACCOUNT_REPORT_ID_SEQ")
    @Column(name = "Account_Report_Id")
    private Long accountReportId;

    @Column(name = "Account_Id")
    private Long accountId;

    @Column(name = "Account_Type_Id")
    private Long accountTypeId;

    @Column(name = "identity_Id")
    private Long identityId;

    @Column(name = "identity_Name")
    private String identityName;

    @Column(name = "Transaction_Type")
    private Long transactionType;

    @Column(name = "Previous_Balance")
    private BigDecimal previousBalance;

    @Column(name = "Period_Plus_Section")
    private BigDecimal periodPlusSection;

    @Column(name = "Period_Add_Amt")
    private BigDecimal periodAddAmt;
    
    @Column(name = "Current_Expenditure")
    private BigDecimal currentExpenditure;

    @Column(name = "Period_Balance")
    private BigDecimal periodBalance;

    @Column(name = "Begin_Time")
    private Date beginTime;

    @Column(name = "End_Time")
    private Date endTime;

    @Column(name = "account_Add_Num")
    private Long accountAddNum;

    @Column(name = "Account_Expenses_Num")
    private Long accountExpensesNum;

    @Column(name = "identity_Type", length = 10)
    private String identityType;

    @Column(name = "ACCOUNT_TYPE_NAME", length = 10)
    private String accountTypeName;

    @Column(name = "identity_type_name")
    private String identityTypeName;
    
    @Column(name = "PERIOD_UNAVAILABLE_BALANCE")
    private BigDecimal periodUnavailableBalance;

    @Column(name = "PREVIOUS_UNAVAILABLE_BALANCE")
    private BigDecimal previousUnavailableBalance;

    public Long getTransactionType()
    {
        return transactionType;
    }

    public void setTransactionType(Long transactionType)
    {
        this.transactionType = transactionType;
    }

    public BigDecimal getPreviousBalance()
    {
        return previousBalance;
    }

    public void setPreviousBalance(BigDecimal previousBalance)
    {
        this.previousBalance = previousBalance;
    }

    public BigDecimal getPeriodPlusSection()
    {
        return periodPlusSection;
    }

    public void setPeriodPlusSection(BigDecimal periodPlusSection)
    {
        this.periodPlusSection = periodPlusSection;
    }

    public BigDecimal getCurrentExpenditure()
    {
        return currentExpenditure;
    }

    public void setCurrentExpenditure(BigDecimal currentExpenditure)
    {
        this.currentExpenditure = currentExpenditure;
    }

    public BigDecimal getPeriodBalance()
    {
        return periodBalance;
    }

    public void setPeriodBalance(BigDecimal periodBalance)
    {
        this.periodBalance = periodBalance;
    }

    public Date getBeginTime()
    {
        return beginTime;
    }

    public void setBeginTime(Date beginTime)
    {
        this.beginTime = beginTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public Long getAccountReportId()
    {
        return accountReportId;
    }

    public void setAccountReportId(Long accountReportId)
    {
        this.accountReportId = accountReportId;
    }

    public Long getAccountId()
    {
        return accountId;
    }

    public void setAccountId(Long accountId)
    {
        this.accountId = accountId;
    }

    public Long getAccountAddNum()
    {
        return accountAddNum;
    }

    public void setAccountAddNum(Long accountAddNum)
    {
        this.accountAddNum = accountAddNum;
    }

    public Long getAccountExpensesNum()
    {
        return accountExpensesNum;
    }

    public void setAccountExpensesNum(Long accountExpensesNum)
    {
        this.accountExpensesNum = accountExpensesNum;
    }

    public Long getAccountTypeId()
    {
        return accountTypeId;
    }

    public void setAccountTypeId(Long accountTypeId)
    {
        this.accountTypeId = accountTypeId;
    }

    public String getAccountTypeName()
    {
        return accountTypeName;
    }

    public void setAccountTypeName(String accountTypeName)
    {
        this.accountTypeName = accountTypeName;
    }

    public Long getIdentityId()
    {
        return identityId;
    }

    public void setIdentityId(Long identityId)
    {
        this.identityId = identityId;
    }

    public String getIdentityName()
    {
        return identityName;
    }

    public void setIdentityName(String identityName)
    {
        this.identityName = identityName;
    }

    public String getIdentityType()
    {
        return identityType;
    }

    public void setIdentityType(String identityType)
    {
        this.identityType = identityType;
    }

    public String getIdentityTypeName()
    {
        return identityTypeName;
    }

    public void setIdentityTypeName(String identityTypeName)
    {
        this.identityTypeName = identityTypeName;
    }
    
    public BigDecimal getPeriodUnavailableBalance()
    {
        return periodUnavailableBalance;
    }

    public void setPeriodUnavailableBalance(BigDecimal periodUnavailableBalance)
    {
        this.periodUnavailableBalance = periodUnavailableBalance;
    }

    public BigDecimal getPreviousUnavailableBalance()
    {
        return previousUnavailableBalance;
    }

    public void setPreviousUnavailableBalance(BigDecimal previousUnavailableBalance)
    {
        this.previousUnavailableBalance = previousUnavailableBalance;
    }

    
    public BigDecimal getPeriodAddAmt()
    {
        return periodAddAmt;
    }

    public void setPeriodAddAmt(BigDecimal periodAddAmt)
    {
        this.periodAddAmt = periodAddAmt;
    }

    @Override
    public String toString()
    {
        return "AccountReportInfo [accountReportId=" + accountReportId + ", accountId="
               + accountId + ", accountTypeId=" + accountTypeId + ", identityId=" + identityId
               + ", identityName=" + identityName + ", transactionType=" + transactionType
               + ", previousBalance=" + previousBalance + ", periodPlusSection="
               + periodPlusSection + ", periodAddAmt=" + periodAddAmt + ", currentExpenditure="
               + currentExpenditure + ", periodBalance=" + periodBalance + ", beginTime="
               + beginTime + ", endTime=" + endTime + ", accountAddNum=" + accountAddNum
               + ", accountExpensesNum=" + accountExpensesNum + ", identityType=" + identityType
               + ", accountTypeName=" + accountTypeName + ", identityTypeName=" + identityTypeName
               + ", periodUnavailableBalance=" + periodUnavailableBalance
               + ", previousUnavailableBalance=" + previousUnavailableBalance + "]";
    }

}
