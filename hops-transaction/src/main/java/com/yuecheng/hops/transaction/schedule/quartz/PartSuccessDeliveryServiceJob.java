package com.yuecheng.hops.transaction.schedule.quartz;


import com.yuecheng.hops.transaction.service.scanner.PartSuccessOrderScanner;


public class PartSuccessDeliveryServiceJob
{
    private PartSuccessOrderScanner partSuccessOrderScanner;

    public PartSuccessOrderScanner getPartSuccessOrderScanner()
    {
        return partSuccessOrderScanner;
    }

    public void setPartSuccessOrderScanner(PartSuccessOrderScanner partSuccessOrderScanner)
    {
        this.partSuccessOrderScanner = partSuccessOrderScanner;
    }

    public void execute()
    {
        partSuccessOrderScanner.bindPartSuccessOrders();
    }
}
