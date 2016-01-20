package com.yuecheng.hops.transaction.config.entify.profitImputation.vo;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.yuecheng.hops.account.entity.AccountType;


/**
 * 利润归集Vo
 * 
 * @author Administrator
 * @version 2014年10月27日
 * @see ProfitImputationVo
 * @since
 */
public class ProfitImputationVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Long profitImputationId;

    private Long profitAccountId;

    private Long middleAccountId;

    private Long merchantId;

    private String merchantName;

    private BigDecimal imputationProfit;

    private BigDecimal accounBalance;

    private Date imputationBeginDate;

    private Date imputationEndDate;

    private Long imputationStatus;

    private Long middleAccountTypeId;

    private Long profitAccountTypeId;

    private AccountType profitAccountType;

    public Long getProfitImputationId()
    {
        return profitImputationId;
    }

    public void setProfitImputationId(Long profitImputationId)
    {
        this.profitImputationId = profitImputationId;
    }

    public Long getProfitAccountId()
    {
        return profitAccountId;
    }

    public void setProfitAccountId(Long profitAccountId)
    {
        this.profitAccountId = profitAccountId;
    }

    public Long getMiddleProfitAccountId()
    {
        return middleAccountId;
    }

    public void setMiddleProfitAccountId(Long middleProfitAccountId)
    {
        this.middleAccountId = middleProfitAccountId;
    }

    public Long getMerchantId()
    {
        return merchantId;
    }

    public void setMerchantId(Long merchantId)
    {
        this.merchantId = merchantId;
    }

    public String getMerchantName()
    {
        return merchantName;
    }

    public void setMerchantName(String merchantName)
    {
        this.merchantName = merchantName;
    }

    public BigDecimal getImputationProfit()
    {
        return imputationProfit;
    }

    public void setImputationProfit(BigDecimal imputationProfit)
    {
        this.imputationProfit = imputationProfit;
    }

    public Date getImputationBeginDate()
    {
        return imputationBeginDate;
    }

    public void setImputationBeginDate(Date imputationBeginDate)
    {
        this.imputationBeginDate = imputationBeginDate;
    }

    public Date getImputationEndDate()
    {
        return imputationEndDate;
    }

    public void setImputationEndDate(Date imputationEndDate)
    {
        this.imputationEndDate = imputationEndDate;
    }

    public Long getImputationStatus()
    {
        return imputationStatus;
    }

    public void setImputationStatus(Long imputationStatus)
    {
        this.imputationStatus = imputationStatus;
    }

    public Long getMiddleProfitAccountTypeId()
    {
        return middleAccountTypeId;
    }

    public void setMiddleProfitAccountTypeId(Long middleProfitAccountTypeId)
    {
        this.middleAccountTypeId = middleProfitAccountTypeId;
    }

    public Long getProfitAccountTypeId()
    {
        return profitAccountTypeId;
    }

    public void setProfitAccountTypeId(Long profitAccountTypeId)
    {
        this.profitAccountTypeId = profitAccountTypeId;
    }

    public Long getMiddleAccountId()
    {
        return middleAccountId;
    }

    public void setMiddleAccountId(Long middleAccountId)
    {
        this.middleAccountId = middleAccountId;
    }

    public BigDecimal getAccounBalance()
    {
        return accounBalance;
    }

    public void setAccounBalance(BigDecimal accounBalance)
    {
        this.accounBalance = accounBalance;
    }

    public Long getMiddleAccountTypeId()
    {
        return middleAccountTypeId;
    }

    public void setMiddleAccountTypeId(Long middleAccountTypeId)
    {
        this.middleAccountTypeId = middleAccountTypeId;
    }

    public AccountType getProfitAccountType()
    {
        return profitAccountType;
    }

    public void setProfitAccountType(AccountType profitAccountType)
    {
        this.profitAccountType = profitAccountType;
    }

}
