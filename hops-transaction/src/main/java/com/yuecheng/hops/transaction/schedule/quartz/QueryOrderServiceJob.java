package com.yuecheng.hops.transaction.schedule.quartz;


import com.yuecheng.hops.transaction.service.scanner.SupplyOrderPendingQueryScanner;


public class QueryOrderServiceJob
{
    SupplyOrderPendingQueryScanner supplyOrderPendingQueryScanner;

    public SupplyOrderPendingQueryScanner getSupplyOrderPendingQueryScanner()
    {
        return supplyOrderPendingQueryScanner;
    }

    public void setSupplyOrderPendingQueryScanner(SupplyOrderPendingQueryScanner supplyOrderPendingQueryScanner)
    {
        this.supplyOrderPendingQueryScanner = supplyOrderPendingQueryScanner;
    }

    public void execute()
    {
        supplyOrderPendingQueryScanner.querySupplyOrders();
    }
}
