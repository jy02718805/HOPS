package com.yuecheng.hops.transaction.execution.product;

public interface SupplyMerchantTransaction
{
    /**
     * 每5分钟计算上游质量
     */
    public void calculateSupplyProductQuality();

}
