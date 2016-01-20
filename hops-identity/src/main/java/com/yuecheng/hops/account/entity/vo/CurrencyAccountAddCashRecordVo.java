/*
 * 文件名：CurrencyAccountAddCashRecord.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年11月7日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.account.entity.vo;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class CurrencyAccountAddCashRecordVo implements Serializable
{
    private static final long serialVersionUID = 5173093802774773981L;

    private Long              id;

    private Long              merchantId;

    private String            merchantName;

    private String            operatorName;

    private String              beginApplyTime;

    private String              endApplyTime;

    private Date              beginVerifyTime;

    private Date              endVerifyTime;

    private BigDecimal        amt;                                    // 加款金额

    private Integer           verifyStatus;                           // 1.待审核 2.审核成功 3.审核失败

    private Long              accountId;

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

    public String getMerchantName()
    {
        return merchantName;
    }

    public void setMerchantName(String merchantName)
    {
        this.merchantName = merchantName;
    }

    public String getOperatorName()
    {
        return operatorName;
    }

    public void setOperatorName(String operatorName)
    {
        this.operatorName = operatorName;
    }

    public String getBeginApplyTime()
    {
        return beginApplyTime;
    }

    public void setBeginApplyTime(String beginApplyTime)
    {
        this.beginApplyTime = beginApplyTime;
    }

    public String getEndApplyTime()
    {
        return endApplyTime;
    }

    public void setEndApplyTime(String endApplyTime)
    {
        this.endApplyTime = endApplyTime;
    }

    public Date getBeginVerifyTime()
    {
        return beginVerifyTime;
    }

    public void setBeginVerifyTime(Date beginVerifyTime)
    {
        this.beginVerifyTime = beginVerifyTime;
    }

    public Date getEndVerifyTime()
    {
        return endVerifyTime;
    }

    public void setEndVerifyTime(Date endVerifyTime)
    {
        this.endVerifyTime = endVerifyTime;
    }

    public BigDecimal getAmt()
    {
        return amt;
    }

    public void setAmt(BigDecimal amt)
    {
        this.amt = amt;
    }

    public Integer getVerifyStatus()
    {
        return verifyStatus;
    }

    public void setVerifyStatus(Integer verifyStatus)
    {
        this.verifyStatus = verifyStatus;
    }

    public Long getAccountId()
    {
        return accountId;
    }

    public void setAccountId(Long accountId)
    {
        this.accountId = accountId;
    }

}
