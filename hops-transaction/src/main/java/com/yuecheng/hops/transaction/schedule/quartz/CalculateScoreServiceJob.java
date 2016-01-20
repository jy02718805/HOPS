package com.yuecheng.hops.transaction.schedule.quartz;


import com.yuecheng.hops.transaction.execution.product.SupplyMerchantTransaction;


public class CalculateScoreServiceJob
{
    private SupplyMerchantTransaction supplyMerchantTransaction;

    public SupplyMerchantTransaction getSupplyMerchantTransaction()
    {
        return supplyMerchantTransaction;
    }

    public void setSupplyMerchantTransaction(SupplyMerchantTransaction supplyMerchantTransaction)
    {
        this.supplyMerchantTransaction = supplyMerchantTransaction;
    }

    public void execute()
    {
        supplyMerchantTransaction.calculateSupplyProductQuality();
    }
}
