package com.yuecheng.hops.mportal.vo.rebate;

public class RebateJsonParam
{
    private String rebateMerchantId;

    private String rebateStatus;

    private String rebateType;

    private String rebateDiscounts;

    public String getRebateMerchantId()
    {
        return rebateMerchantId;
    }

    public void setRebateMerchantId(String rebateMerchantId)
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

    public String getRebateType()
    {
        return rebateType;
    }

    public void setRebateType(String rebateType)
    {
        this.rebateType = rebateType;
    }

    public String getRebateDiscounts()
    {
        return rebateDiscounts;
    }

    public void setRebateDiscounts(String rebateDiscounts)
    {
        this.rebateDiscounts = rebateDiscounts;
    }

    @Override
    public String toString()
    {
        return "RebateJson [rebateMerchantId=" + rebateMerchantId + ", rebateStatus="
               + rebateStatus + ", rebateType=" + rebateType + ", rebateDiscounts="
               + rebateDiscounts + "]";
    }

}
