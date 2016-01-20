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
@Table(name = "Rebate_Record")
public class RebateRecord implements Serializable
{

    private static final long serialVersionUID = 9031226321647561089L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RebateRecordIdSeq")
    @SequenceGenerator(name = "RebateRecordIdSeq", sequenceName = "SEQ_REBATE_RECORD_ID")
    @Column(name = "id")
    private Long id;    //主键

    @Column(name = "merchant_id")
    private Long merchantId;    //发生商户ID

    @Column(name = "REBATE_DATE")
    private Date rebateDate;    //清算日期

    @Column(name = "rebate_amt")
    private BigDecimal rebateAmt;   //返佣金额

    @Column(name = "STATUS")
    private String status;      //返佣状态

    @Column(name = "REBATE_RULE_ID")
    private Long rebateRuleId;  //返佣规则ID

    @Column(name = "REBATE_PRODUCT_ID")
    private String rebateProductId; //返佣产品区间ID

    @Column(name = "REBATE_MERCHANT_ID")
    private Long rebateMerchantId;  //返佣商户ID

    @Column(name = "MERCHANT_TYPE")
    private String merchantType;    //商户类型

    @Column(name = "REBATE_Type")
    private Long rebateType;        //返佣类型 （定比返、定额返）

    @Column(name = "TRANSACTION_Volume")
    private Long transactionVolume;    //交易量

    @Column(name = "CREATE_USER")
    private String createUser;      //创建人

    @Column(name = "CREATE_DATE")
    private Date createDate;        //创建时间

    @Column(name = "update_USER")
    private String updateUser;      //更新人

    @Column(name = "update_DATE")
    private Date updateDate;        //更新时间

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

    public BigDecimal getRebateAmt()
    {
        return rebateAmt;
    }

    public void setRebateAmt(BigDecimal rebateAmt)
    {
        this.rebateAmt = rebateAmt;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Long getRebateRuleId()
    {
        return rebateRuleId;
    }

    public void setRebateRuleId(Long rebateRuleId)
    {
        this.rebateRuleId = rebateRuleId;
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

    public Long getTransactionVolume()
    {
        return transactionVolume;
    }

    public void setTransactionVolume(Long transactionVolume)
    {
        this.transactionVolume = transactionVolume;
    }

    public Date getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }

    public Long getRebateType()
    {
        return rebateType;
    }

    public void setRebateType(Long rebateType)
    {
        this.rebateType = rebateType;
    }

    public Date getRebateDate()
    {
        return rebateDate;
    }

    public void setRebateDate(Date rebateDate)
    {
        this.rebateDate = rebateDate;
    }

    public String getMerchantType()
    {
        return merchantType;
    }

    public void setMerchantType(String merchantType)
    {
        this.merchantType = merchantType;
    }

    public String getCreateUser()
    {
        return createUser;
    }

    public void setCreateUser(String createUser)
    {
        this.createUser = createUser;
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

    @Override
    public String toString()
    {
        return "RebateRecord [id=" + id + ", merchantId=" + merchantId + ", rebateDate="
               + rebateDate + ", rebateAmt=" + rebateAmt + ", status=" + status
               + ", rebateRuleId=" + rebateRuleId + ", rebateProductId=" + rebateProductId
               + ", rebateMerchantId=" + rebateMerchantId + ", merchantType=" + merchantType
               + ", rebateType=" + rebateType + ", transactionNum=" + transactionVolume
               + ", createUser=" + createUser + ", createDate=" + createDate + ", updateUser="
               + updateUser + ", updateDate=" + updateDate + "]";
    }

}
