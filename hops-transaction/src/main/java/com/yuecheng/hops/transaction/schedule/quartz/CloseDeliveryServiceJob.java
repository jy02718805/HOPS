package com.yuecheng.hops.transaction.schedule.quartz;


import com.yuecheng.hops.transaction.service.scanner.CloseDeliveryScanner;


public class CloseDeliveryServiceJob
{
    private CloseDeliveryScanner closeDeliveryScanner;

    public CloseDeliveryScanner getCloseDeliveryScanner()
    {
        return closeDeliveryScanner;
    }

    public void setCloseDeliveryScanner(CloseDeliveryScanner closeDeliveryScanner)
    {
        this.closeDeliveryScanner = closeDeliveryScanner;
    }

    public void execute()
    {
        closeDeliveryScanner.closeDelivery();
    }
}
