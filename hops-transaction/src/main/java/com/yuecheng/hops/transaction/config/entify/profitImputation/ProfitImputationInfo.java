package com.yuecheng.hops.transaction.config.entify.profitImputation;


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

/**
 * 
 * 利润归集
 * @author Administrator
 * @version 2014年10月27日
 * @see ProfitImputationInfo
 * @since
 */
@Entity
@Table(name = "Profit_Imputation")
public class ProfitImputationInfo implements Serializable
{

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profitImputationIdSeq")
    @SequenceGenerator(name = "profitImputationIdSeq", sequenceName = "PROFIT_IMPUTATION_ID_SEQ")
    @Column(name = "profit_Imputation_Id")
    private Long profitImputationId;

    @Column(name = "profit_account_id")
    private Long profitAccountId;

    @Column(name = "middle_account_id")
    private Long middleAccountId;

    @Column(name = "merchant_Id")
    private Long merchantId;

    @Column(name = "merchant_Name")
    private String merchantName;

    @Column(name = "imputation_Profit")
    private BigDecimal imputationProfit;

    @Column(name = "account_balance")
    private BigDecimal accounBalance;

    @Column(name = "imputation_Begin_Date")
    private Date imputationBeginDate;

    @Column(name = "imputation_End_Date")
    private Date imputationEndDate;

    @Column(name = "imputation_Status")
    private Long imputationStatus;

    @Column(name = "middle_Account_Typeid")
    private Long middleAccountTypeId;

    @Column(name = "profit_Account_Typeid")
    private Long profitAccountTypeId;
    
    @Column(name = "DESCRIBE")
    private String describe;
    
    
    
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

    public String getDescribe()
    {
        return describe;
    }

    public void setDescribe(String describe)
    {
        this.describe = describe;
    }
}
