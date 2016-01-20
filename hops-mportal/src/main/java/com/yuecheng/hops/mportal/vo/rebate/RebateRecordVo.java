package com.yuecheng.hops.mportal.vo.rebate;


public class RebateRecordVo
{

    private String rebateRecordId;
    
    private String rebateProductId;
    
    private String merchantType;
    
    private String transactionVolume;
    
    private String rebateAmt;
    
    private String rebateType;
    
    public String merchantId;

    public String rebateMerchantId;

    public String status;

    public String beginDate;

    public String endDate;

    public String getMerchantId()
    {
        return merchantId;
    }

    public void setMerchantId(String merchantId)
    {
        this.merchantId = merchantId;
    }

    public String getRebateMerchantId()
    {
        return rebateMerchantId;
    }

    public void setRebateMerchantId(String rebateMerchantId)
    {
        this.rebateMerchantId = rebateMerchantId;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getBeginDate()
    {
        return beginDate;
    }

    public void setBeginDate(String beginDate)
    {
        this.beginDate = beginDate;
    }

    public String getEndDate()
    {
        return endDate;
    }

    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }

    public String getRebateRecordId()
    {
        return rebateRecordId;
    }

    public void setRebateRecordId(String rebateRecordId)
    {
        this.rebateRecordId = rebateRecordId;
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

    public String getTransactionVolume()
    {
        return transactionVolume;
    }

    public void setTransactionVolume(String transactionVolume)
    {
        this.transactionVolume = transactionVolume;
    }

    public String getRebateAmt()
    {
        return rebateAmt;
    }

    public void setRebateAmt(String rebateAmt)
    {
        this.rebateAmt = rebateAmt;
    }

    public String getRebateType()
    {
        return rebateType;
    }

    public void setRebateType(String rebateType)
    {
        this.rebateType = rebateType;
    }

    @Override
    public String toString()
    {
        return "RebateRecordVo [rebateRecordId=" + rebateRecordId + ", rebateProductId="
               + rebateProductId + ", merchantType=" + merchantType + ", transactionVolume="
               + transactionVolume + ", rebateAmt=" + rebateAmt + ", rebateType=" + rebateType
               + ", merchantId=" + merchantId + ", rebateMerchantId=" + rebateMerchantId
               + ", status=" + status + ", beginDate=" + beginDate + ", endDate=" + endDate + "]";
    }

}
