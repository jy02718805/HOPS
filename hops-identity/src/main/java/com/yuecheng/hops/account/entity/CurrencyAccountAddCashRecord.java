/*
 * 文件名：CurrencyAccountAddCashRecord.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年11月7日 跟踪单号： 修改单号： 修改内容：
 */

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
@Table(name = "ccy_Account_Add_Cash_Record")
public class CurrencyAccountAddCashRecord implements Serializable
{
    private static final long serialVersionUID = 5173093802774773981L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CurrencyAccountAddCashRecordIdSeq")
    @SequenceGenerator(name = "CurrencyAccountAddCashRecordIdSeq", sequenceName = "AC_ADD_CASH_R_ID_SEQ")
    @Column(name = "id")
    private Long              id;

    @Column(name = "merchant_id")
    private Long              merchantId;

    @Column(name = "merchant_name")
    private String            merchantName;

    @Column(name = "operator_name")
    private String            operatorName;

    @Column(name = "apply_time")
    private Date              applyTime;

    @Column(name = "verify_time")
    private Date              verifyTime;

    @Column(name = "apply_amt")
    private BigDecimal        applyAmt;                                    // 申请加款金额
    
    @Column(name = "success_amt")
    private BigDecimal        successAmt;                                    // 审核成功金额

    @Column(name = "verify_status")
    private Integer           verifyStatus;                           // 1.待审核 2.审核成功 3.审核失败

    @Column(name = "account_id")
    private Long              accountId;

    @Column(name = "rmk")
    private String            rmk;

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

    public Date getApplyTime()
    {
        return applyTime;
    }

    public void setApplyTime(Date applyTime)
    {
        this.applyTime = applyTime;
    }

    public Date getVerifyTime()
    {
        return verifyTime;
    }

    public void setVerifyTime(Date verifyTime)
    {
        this.verifyTime = verifyTime;
    }

   
    public BigDecimal getApplyAmt() {
		return applyAmt;
	}

	public void setApplyAmt(BigDecimal applyAmt) {
		this.applyAmt = applyAmt;
	}

	public BigDecimal getSuccessAmt() {
		return successAmt;
	}

	public void setSuccessAmt(BigDecimal successAmt) {
		this.successAmt = successAmt;
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

    public String getRmk()
    {
        return rmk;
    }

    public void setRmk(String rmk)
    {
        this.rmk = rmk;
    }

	@Override
	public String toString() {
		return "CurrencyAccountAddCashRecord [id=" + id + ", merchantId="
				+ merchantId + ", merchantName=" + merchantName
				+ ", operatorName=" + operatorName + ", applyTime=" + applyTime
				+ ", verifyTime=" + verifyTime + ", applyAmt=" + applyAmt
				+ ", successAmt=" + successAmt + ", verifyStatus="
				+ verifyStatus + ", accountId=" + accountId + ", rmk=" + rmk
				+ "]";
	}

   
}
