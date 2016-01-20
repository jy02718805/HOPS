package com.yuecheng.hops.rebate.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "Rebate_Rule")
@SequenceGenerator(name = "SeqRebateRuleId", sequenceName = "SEQ_REBATE_RULE_ID")
public class RebateRule implements Serializable
{
    private static final long serialVersionUID = 7137861501428881212L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SeqRebateRuleId")
    @Column(name = "REBATE_RULE_ID")
    private Long rebateRuleId;  //返佣规则主键ID

    @Column(name = "MERCHANT_ID")
    private Long merchantId;    //发生商户ID

    @Column(name = "REBATE_MERCHANT_ID")
    private Long rebateMerchantId;  //返佣商户ID
    
    @Column(name = "MERCHANT_TYPE")
    private String merchantType;    //商户类型

    @Column(name = "REBATE_TIME_TYPE")
    private Long rebateTimeType;    //返佣周期（按天返、按月返、按季返、按年返）目前默认按天返

    @Column(name = "REBATE_Type")
    private Long rebateType;        //返佣类型（定比返、定额返）

    @Column(name = "REBATE_PRODUCT_ID")
    private String rebateProductId; //返佣产品区间ID

    @Column(name = "STATUS")
    private String status;          //状态

    @Column(name = "CREATE_USER")
    private String createUser;      //创建人

    @Column(name = "CREATE_DATE")
    private Date createDate;        //创建时间

    @Column(name = "update_USER")
    private String updateUser;      //更新人

    @Column(name = "update_DATE")
    private Date updateDate;        //更新时间

    public Long getRebateRuleId()
    {
        return rebateRuleId;
    }

    public void setRebateRuleId(Long rebateRuleId)
    {
        this.rebateRuleId = rebateRuleId;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Long getMerchantId()
    {
        return merchantId;
    }

    public void setMerchantId(Long merchantId)
    {
        this.merchantId = merchantId;
    }

    public Long getRebateMerchantId()
    {
        return rebateMerchantId;
    }

    public void setRebateMerchantId(Long rebateMerchantId)
    {
        this.rebateMerchantId = rebateMerchantId;
    }

    public Date getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }

    public Long getRebateTimeType()
    {
        return rebateTimeType;
    }

    public void setRebateTimeType(Long rebateTimeType)
    {
        this.rebateTimeType = rebateTimeType;
    }

    public Long getRebateType()
    {
        return rebateType;
    }

    public void setRebateType(Long rebateType)
    {
        this.rebateType = rebateType;
    }

    public String getRebateProductId()
    {
        return rebateProductId;
    }

    public void setRebateProductId(String rebateProductId)
    {
        this.rebateProductId = rebateProductId;
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
        return "RebateRule [rebateRuleId=" + rebateRuleId + ", merchantId=" + merchantId
               + ", rebateMerchantId=" + rebateMerchantId + ", merchantType=" + merchantType
               + ", rebateTimeType=" + rebateTimeType + ", rebateType=" + rebateType
               + ", rebateProductId=" + rebateProductId + ", status=" + status 
               + ", createUser=" + createUser + ", createDate=" + createDate
               + ", updateUser=" + updateUser + ", updateDate=" + updateDate + "]";
    }

}
