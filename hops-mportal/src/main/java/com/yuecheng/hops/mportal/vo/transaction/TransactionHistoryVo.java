package com.yuecheng.hops.mportal.vo.transaction;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class TransactionHistoryVo implements Serializable
{
    private static final long serialVersionUID = 7958013489221922263L;

    public String transactionId;

    public String accountId;// 具体账户主键

    public String accountTypeId;

    public String orderNo;// 订单ID

    public String payerAccountId;// 付款方账户ID

    public String payerTypeModel;// 付款方账户类型

    public String payeeAccountId;// 收款方账户ID

    public String payeeTypeModel;// 收款方账户类型

    public String descStr;

    public BigDecimal amt;// 交易金额

    public Date createDate;// 创建时间

    public String payeenewStatus;// 收款状态

    public String payernewStatus;// 付款状态

    public String getPayeenewStatus()
    {
        return payeenewStatus;
    }

    public void setPayeenewStatus(String payeenewStatus)
    {
        this.payeenewStatus = payeenewStatus;
    }

    public String getPayernewStatus()
    {
        return payernewStatus;
    }

    public String getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }

    public String getPayerAccountId()
    {
        return payerAccountId;
    }

    public void setPayerAccountId(String payerAccountId)
    {
        this.payerAccountId = payerAccountId;
    }

    public String getPayeeAccountId()
    {
        return payeeAccountId;
    }

    public void setPayeeAccountId(String payeeAccountId)
    {
        this.payeeAccountId = payeeAccountId;
    }

    public void setPayernewStatus(String payernewStatus)
    {
        this.payernewStatus = payernewStatus;
    }

    public String getTransactionId()
    {
        return transactionId;
    }

    public void setTransactionId(String transactionId)
    {
        this.transactionId = transactionId;
    }

    public String getPayerTypeModel()
    {
        return payerTypeModel;
    }

    public void setPayerTypeModel(String payerTypeModel)
    {
        this.payerTypeModel = payerTypeModel;
    }

    public String getPayeeTypeModel()
    {
        return payeeTypeModel;
    }

    public void setPayeeTypeModel(String payeeTypeModel)
    {
        this.payeeTypeModel = payeeTypeModel;
    }

    public BigDecimal getAmt()
    {
        return amt;
    }

    public void setAmt(BigDecimal amt)
    {
        this.amt = amt;
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

}
