package com.yuecheng.hops.transaction.schedule.quartz;


import com.yuecheng.hops.transaction.service.scanner.SupplyOrderPendingSendScanner;


public class SendOrdersServiceJob
{
    private SupplyOrderPendingSendScanner supplyOrderPendingSendScanner;

    public SupplyOrderPendingSendScanner getSupplyOrderPendingSendScanner()
    {
        return supplyOrderPendingSendScanner;
    }

    public void setSupplyOrderPendingSendScanner(SupplyOrderPendingSendScanner supplyOrderPendingSendScanner)
    {
        this.supplyOrderPendingSendScanner = supplyOrderPendingSendScanner;
    }

    public void execute()
    {
        supplyOrderPendingSendScanner.sendOrders();
    }
}
