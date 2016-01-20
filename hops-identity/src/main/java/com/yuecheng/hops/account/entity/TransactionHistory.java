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
@Table(name = "transaction_History")
public class TransactionHistory implements Serializable
{

    private static final long serialVersionUID = 7958013489221922263L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TransactionHistoryIdSeq")
    @SequenceGenerator(name = "TransactionHistoryIdSeq", sequenceName = "TRANSACTION_HIS_ID_SEQ")
    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(name = "transaction_no")
    private String transactionNo; // 流水ID

    @Column(name = "payer_account_id")
    private Long payerAccountId; // 付款方账户ID

    @Column(name = "payer_acc_type_id", length = 10)
    private Long payerAccountTypeId; // 付款方账户类型

    @Column(name = "payee_account_id")
    private Long payeeAccountId; // 收款方账户ID

    @Column(name = "payee_acc_type_id", length = 10)
    private Long payeeAccountTypeId; // 收款方账户类型

    @Column(name = "desc_str", length = 100)
    private String descStr;

    @Column(name = "amt")
    private BigDecimal amt; // 交易金额

    @Column(name = "create_Date")
    private Date createDate; // 创建时间

    @Column(name = "type", length = 10)
    private String type; // 订单ID

    @Column(name = "CREATOR")
    private String creator; // 创建人
    
    @Column(name ="PAYER_IDENTITY_NAME")
    private String payerIdentityName;
    
    @Column(name ="PAYEE_IDENTITY_NAME")
    private String payeeIdentityName;
    
    @Column(name ="IS_REFUND")
    private Long isRefund;//是否退款 0.未退款  1.已经退款
    
    public Long getTransactionId()
    {
        return transactionId;
    }

    public void setTransactionId(Long transactionId)
    {
        this.transactionId = transactionId;
    }

    public Long getPayerAccountId()
    {
        return payerAccountId;
    }

    public void setPayerAccountId(Long payerAccountId)
    {
        this.payerAccountId = payerAccountId;
    }

    public Long getPayeeAccountId()
    {
        return payeeAccountId;
    }

    public void setPayeeAccountId(Long payeeAccountId)
    {
        this.payeeAccountId = payeeAccountId;
    }


    public Long getPayerAccountTypeId()
    {
        return payerAccountTypeId;
    }

    public void setPayerAccountTypeId(Long payerAccountTypeId)
    {
        this.payerAccountTypeId = payerAccountTypeId;
    }

    public Long getPayeeAccountTypeId()
    {
        return payeeAccountTypeId;
    }

    public void setPayeeAccountTypeId(Long payeeAccountTypeId)
    {
        this.payeeAccountTypeId = payeeAccountTypeId;
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

    public TransactionHistory()
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

    public String getCreator()
    {
        return creator;
    }

    public void setCreator(String creator)
    {
        this.creator = creator;
    }

    public String getPayerIdentityName()
    {
        return payerIdentityName;
    }

    public void setPayerIdentityName(String payerIdentityName)
    {
        this.payerIdentityName = payerIdentityName;
    }

    public String getPayeeIdentityName()
    {
        return payeeIdentityName;
    }

    public void setPayeeIdentityName(String payeeIdentityName)
    {
        this.payeeIdentityName = payeeIdentityName;
    }

    public Long getIsRefund()
    {
        return isRefund;
    }

    public void setIsRefund(Long isRefund)
    {
        this.isRefund = isRefund;
    }

    @Override
    public String toString()
    {
        return "TransactionHistory [transactionId=" + transactionId + ", transactionNo="
               + transactionNo + ", payerAccountId=" + payerAccountId + ", payerAccountTypeId="
               + payerAccountTypeId + ", payeeAccountId=" + payeeAccountId
               + ", payeeAccountTypeId=" + payeeAccountTypeId + ", descStr=" + descStr + ", amt="
               + amt + ", createDate=" + createDate + ", type=" + type + ", creator=" + creator
               + ", payerIdentityName=" + payerIdentityName + ", payeeIdentityName="
               + payeeIdentityName + ", isRefund=" + isRefund + "]";
    }
}
