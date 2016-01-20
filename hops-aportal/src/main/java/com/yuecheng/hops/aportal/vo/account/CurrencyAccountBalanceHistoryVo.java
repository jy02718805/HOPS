package com.yuecheng.hops.aportal.vo.account;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CurrencyAccountBalanceHistoryVo implements Serializable
{
    
    private static final long serialVersionUID = 1L;

    private Long productId;// 产品

    private String transactionNo;// 订单号

    private String merchantOrderNo;

    private String userCode;

    private BigDecimal productFace;

    private BigDecimal amt;

    private BigDecimal changeAmount;// 变动金额

    private String creator;// 操作人

    public String productNo;
    
    private Long transactionId; // 交易ID

    private Long accountId; // 具体账户主键

    private BigDecimal oldAvailableBalance; // 修改前可用余额

    private BigDecimal oldUnavailableBanlance; // 修改前不可用余额

    private BigDecimal oldCreditableBanlance; // 修改前授信余额

    private BigDecimal newAvailableBalance; // 修改后可用余额

    private BigDecimal newUnavailableBanlance; // 修改后不可用余额

    private BigDecimal newCreditableBanlance; // 修改后授信余额

    private Date createDate; // 修改时间

    private String type; // 账户日志类型：1.交易 2.加减款
    
    private String transactionType;
    
    public Long getProductId()
    {
        return productId;
    }

    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

    public String getTransactionNo()
    {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo)
    {
        this.transactionNo = transactionNo;
    }

    public String getMerchantOrderNo()
    {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo)
    {
        this.merchantOrderNo = merchantOrderNo;
    }

    public String getUserCode()
    {
        return userCode;
    }

    public void setUserCode(String userCode)
    {
        this.userCode = userCode;
    }

    public BigDecimal getProductFace()
    {
        return productFace;
    }

    public void setProductFace(BigDecimal productFace)
    {
        this.productFace = productFace;
    }

    public BigDecimal getAmt()
    {
        return amt;
    }

    public void setAmt(BigDecimal amt)
    {
        this.amt = amt;
    }

    public String getCreator()
    {
        return creator;
    }

    public void setCreator(String creator)
    {
        this.creator = creator;
    }

    public CurrencyAccountBalanceHistoryVo()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getProductNo()
    {
        return productNo;
    }

    public void setProductNo(String productNo)
    {
        this.productNo = productNo;
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

    public Date getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public BigDecimal getChangeAmount()
    {
        return changeAmount;
    }

    public void setChangeAmount(BigDecimal changeAmount)
    {
        this.changeAmount = changeAmount;
    }

    public String getTransactionType()
    {
        return transactionType;
    }

    public void setTransactionType(String transactionType)
    {
        this.transactionType = transactionType;
    }

    @Override
    public String toString()
    {
        return "CurrencyAccountBalanceHistoryVo [productId=" + productId + ", transactionNo="
               + transactionNo + ", merchantOrderNo=" + merchantOrderNo + ", userCode=" + userCode
               + ", productFace=" + productFace + ", amt=" + amt + ", changeAmount="
               + changeAmount + ", creator=" + creator + ", productNo=" + productNo
               + ", transactionId=" + transactionId + ", accountId=" + accountId
               + ", oldAvailableBalance=" + oldAvailableBalance + ", oldUnavailableBanlance="
               + oldUnavailableBanlance + ", oldCreditableBanlance=" + oldCreditableBanlance
               + ", newAvailableBalance=" + newAvailableBalance + ", newUnavailableBanlance="
               + newUnavailableBanlance + ", newCreditableBanlance=" + newCreditableBanlance
               + ", createDate=" + createDate + ", type=" + type + ", transactionType="
               + transactionType + "]";
    }
}
