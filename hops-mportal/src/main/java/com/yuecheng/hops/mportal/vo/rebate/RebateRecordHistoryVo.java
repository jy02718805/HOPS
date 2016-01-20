package com.yuecheng.hops.mportal.vo.rebate;

public class RebateRecordHistoryVo
{
    public Long merchantId;

    public Long rebateMerchantId;

    public String rebateStatus;
    
    public String balanceStatus;

    public String rebateStartDate;

    public String rebateEndDate;

    public Long rebateRecordHistoryId;

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

    public String getRebateStartDate()
    {
        return rebateStartDate;
    }

    public void setRebateStartDate(String rebateStartDate)
    {
        this.rebateStartDate = rebateStartDate;
    }

    public String getRebateEndDate()
    {
        return rebateEndDate;
    }

    public void setRebateEndDate(String rebateEndDate)
    {
        this.rebateEndDate = rebateEndDate;
    }

	public Long getRebateRecordHistoryId() {
		return rebateRecordHistoryId;
	}

	public void setRebateRecordHistoryId(Long rebateRecordHistoryId) {
		this.rebateRecordHistoryId = rebateRecordHistoryId;
	}
    
    
}
