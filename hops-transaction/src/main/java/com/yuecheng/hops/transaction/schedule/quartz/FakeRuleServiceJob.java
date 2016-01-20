package com.yuecheng.hops.transaction.schedule.quartz;


import com.yuecheng.hops.transaction.service.scanner.PendingFakeOrderScanner;


public class FakeRuleServiceJob
{
    private PendingFakeOrderScanner pendingFakeOrderScanner;

    public PendingFakeOrderScanner getPendingFakeOrderScanner()
    {
        return pendingFakeOrderScanner;
    }

    public void setPendingFakeOrderScanner(PendingFakeOrderScanner pendingFakeOrderScanner)
    {
        this.pendingFakeOrderScanner = pendingFakeOrderScanner;
    }

    public void execute()
    {
        pendingFakeOrderScanner.updateOrderNotifyStatus();
    }
}
