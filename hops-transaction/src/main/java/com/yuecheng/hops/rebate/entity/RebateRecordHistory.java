package com.yuecheng.hops.rebate.entity;


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
@Table(name = "Rebate_Record_History")
public class RebateRecordHistory implements Serializable
{
    private static final long serialVersionUID = 4249199351402985966L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RebateHistoryIdSeq")
    @SequenceGenerator(name = "RebateHistoryIdSeq", sequenceName = "SEQ_REBATE_HISTORY_ID")
    @Column(name = "id")
    private Long id;    //主键ID

    @Column(name = "merchant_id")
    private Long merchantId;    //发生商户ID

    @Column(name = "REBATE_START_DATE")
    private Date rebateStartDate;   //返佣开始时间

    @Column(name = "REBATE_END_DATE")
    private Date rebateEndDate;     //返佣结束时间

    @Column(name = "REBATE_PRODUCT_ID")
    private String rebateProductId; //返佣产品区间ID

    @Column(name = "REBATE_MERCHANT_ID")
    private Long rebateMerchantId;  //返佣商户ID

    @Column(name = "MERCHANT_TYPE")
    private String merchantType;    //商户类型

    @Column(name = "TRANSACTION_Volume")
    private Long transactionVolume;    //总交易量

    @Column(name = "rebate_amt")
    private BigDecimal rebateAmt;   //返佣金额

    @Column(name = "ACTUL_AMOUNT")
    private BigDecimal actulAmount; //实际返佣金额

    @Column(name = "BALANCE")
    private BigDecimal balance;     //余额（返佣金额-实际返佣金额）

    @Column(name = "REBATE_STATUS")
    private String rebateStatus;    //返佣状态

    @Column(name = "BALANCE_STATUS")
    private String balanceStatus;   //余额收回状态

    @Column(name = "REBATE_Type")
    private Long rebateType;        //返佣类型 （定比返、定额返）

    @Column(name = "CREATE_USER")
    private String createUser;      //创建人

    @Column(name = "CREATE_DATE")
    private Date createDate;        //创建时间

    @Column(name = "update_USER")
    private String updateUser;      //更新人

    @Column(name = "update_DATE")
    private Date updateDate;        //更新时间

    @Column(name = "STATUS")
    private String status;       //状态

    @Column(name = "remark")
    private String remark;          //备注

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getMerchantId()
    {
        return merchantId;
    }

    public void setMerchantId(Long merchantId)
    {
        this.merchantId = merchantId;
    }

    public Date getRebateStartDate()
    {
        return rebateStartDate;
    }

    public void setRebateStartDate(Date rebateStartDate)
    {
        this.rebateStartDate = rebateStartDate;
    }

    public Date getRebateEndDate()
    {
        return rebateEndDate;
    }

    public void setRebateEndDate(Date rebateEndDate)
    {
        this.rebateEndDate = rebateEndDate;
    }

    public String getRebateProductId()
    {
        return rebateProductId;
    }

    public void setRebateProductId(String rebateProductId)
    {
        this.rebateProductId = rebateProductId;
    }

    public Long getRebateMerchantId()
    {
        return rebateMerchantId;
    }

    public void setRebateMerchantId(Long rebateMerchantId)
    {
        this.rebateMerchantId = rebateMerchantId;
    }

    public String getMerchantType()
    {
        return merchantType;
    }

    public void setMerchantType(String merchantType)
    {
        this.merchantType = merchantType;
    }

    public Long getTransactionVolume()
    {
        return transactionVolume;
    }

    public void setTransactionVolume(Long transactionVolume)
    {
        this.transactionVolume = transactionVolume;
    }

    public BigDecimal getRebateAmt()
    {
        return rebateAmt;
    }

    public void setRebateAmt(BigDecimal rebateAmt)
    {
        this.rebateAmt = rebateAmt;
    }

    public BigDecimal getActulAmount()
    {
        return actulAmount;
    }

    public void setActulAmount(BigDecimal actulAmount)
    {
        this.actulAmount = actulAmount;
    }

    public BigDecimal getBalance()
    {
        return balance;
    }

    public void setBalance(BigDecimal balance)
    {
        this.balance = balance;
    }

    public String getRebateStatus()
    {
        return rebateStatus;
    }

    public void setRebateStatus(String rebateStatus)
    {
        this.rebateStatus = rebateStatus;
    }

    public String getBalanceStatus()
    {
        return balanceStatus;
    }

    public void setBalanceStatus(String balanceStatus)
    {
        this.balanceStatus = balanceStatus;
    }

    public Long getRebateType()
    {
        return rebateType;
    }

    public void setRebateType(Long rebateType)
    {
        this.rebateType = rebateType;
    }

    public String getCreateUser()
    {
        return createUser;
    }

    public void setCreateUser(String createUser)
    {
        this.createUser = createUser;
    }

    public Date getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }

    public String getUpdateUser()
    {
        return updateUser;
    }

    public void setUpdateUser(String updateUser)
    {
        this.updateUser = updateUser;
    }

    public Date getUpdateDate()
    {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate)
    {
        this.updateDate = updateDate;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
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
        return "RebateRecordHistory [id=" + id + ", merchantId=" + merchantId
               + ", rebateStartDate=" + rebateStartDate + ", rebateEndDate=" + rebateEndDate
               + ", rebateProductId=" + rebateProductId + ", rebateMerchantId=" + rebateMerchantId
               + ", merchantType=" + merchantType + ", transactionNum=" + transactionVolume
               + ", rebateAmt=" + rebateAmt + ", actulAmount=" + actulAmount + ", balance="
               + balance + ", rebateStatus=" + rebateStatus + ", balanceStatus=" + balanceStatus
               + ", rebateType=" + rebateType + ", createUser=" + createUser + ", createDate="
               + createDate + ", updateUser=" + updateUser + ", updateDate=" + updateDate
               + ", status=" + status + ", remark=" + remark + "]";
    }

}
