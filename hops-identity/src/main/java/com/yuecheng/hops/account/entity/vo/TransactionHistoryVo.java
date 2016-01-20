package com.yuecheng.hops.account.entity.vo;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 交易历史记录VO对象
 * 
 * @author Administrator
 * @version 2014年10月16日
 * @see TransactionHistoryVo
 * @since
 */
public class TransactionHistoryVo implements Serializable
{

    private static final long serialVersionUID = 7958013489221922263L;

    private Long transactionId;

    private String transactionNo; // 流水ID

    private Long payerAccountId; // 付款方账户ID

    private String payerTypeModel; // 付款方账户类型

    private Long payeeAccountId; // 收款方账户ID

    public String payeeTypeModel; // 收款方账户类型

    public String descStr;

    public BigDecimal amt; // 交易金额

    public Date createDate; // 创建时间

    public String type; // 订单ID

    public String creator; // 创建人

    public String payeeName; // 收款人

    public String payerName; // 付款人

    public String payeeTypeName; // 收款账户类型

    public String payerTypeName; // 付款账户类型

    public String payerAccountType; //付款方账户类型ID

    public String payeeAccountType;//收款方账户类型ID

    public String getPayeeName()
    {
        return payeeName;
    }

    public void setPayeeName(String payeeName)
    {
        this.payeeName = payeeName;
    }

    public String getPayerName()
    {
        return payerName;
    }

    public void setPayerName(String payerName)
    {
        this.payerName = payerName;
    }

    public String getPayeeTypeName()
    {
        return payeeTypeName;
    }

    public void setPayeeTypeName(String payeeTypeName)
    {
        this.payeeTypeName = payeeTypeName;
    }

    public String getPayerTypeName()
    {
        return payerTypeName;
    }

    public void setPayerTypeName(String payerTypeName)
    {
        this.payerTypeName = payerTypeName;
    }

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

    public String getPayerTypeModel()
    {
        return payerTypeModel;
    }

    public void setPayerTypeModel(String payerTypeModel)
    {
        this.payerTypeModel = payerTypeModel;
    }

    public Long getPayeeAccountId()
    {
        return payeeAccountId;
    }

    public void setPayeeAccountId(Long payeeAccountId)
    {
        this.payeeAccountId = payeeAccountId;
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

    public TransactionHistoryVo()
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

    
    
    public String getPayerAccountType() {
		return payerAccountType;
	}

	public void setPayerAccountType(String payerAccountType) {
		this.payerAccountType = payerAccountType;
	}

	public String getPayeeAccountType() {
		return payeeAccountType;
	}

	public void setPayeeAccountType(String payeeAccountType) {
		this.payeeAccountType = payeeAccountType;
	}

	@Override
	public String toString() {
		return "TransactionHistoryVo [transactionId=" + transactionId
				+ ", transactionNo=" + transactionNo + ", payerAccountId="
				+ payerAccountId + ", payerTypeModel=" + payerTypeModel
				+ ", payeeAccountId=" + payeeAccountId + ", payeeTypeModel="
				+ payeeTypeModel + ", descStr=" + descStr + ", amt=" + amt
				+ ", createDate=" + createDate + ", type=" + type
				+ ", creator=" + creator + ", payeeName=" + payeeName
				+ ", payerName=" + payerName + ", payeeTypeName="
				+ payeeTypeName + ", payerTypeName=" + payerTypeName
				+ ", payerAccountType=" + payerAccountType
				+ ", payeeAccountType=" + payeeAccountType + "]";
	}


}
