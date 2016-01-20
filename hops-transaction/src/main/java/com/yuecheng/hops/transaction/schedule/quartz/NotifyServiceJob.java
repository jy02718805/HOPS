package com.yuecheng.hops.transaction.schedule.quartz;


import com.yuecheng.hops.transaction.service.scanner.AgentOrderPendingNotifyScanner;


public class NotifyServiceJob
{
    private AgentOrderPendingNotifyScanner agentOrderPendingNotifyScanner;

    public AgentOrderPendingNotifyScanner getAgentOrderPendingNotifyScanner()
    {
        return agentOrderPendingNotifyScanner;
    }

    public void setAgentOrderPendingNotifyScanner(AgentOrderPendingNotifyScanner agentOrderPendingNotifyScanner)
    {
        this.agentOrderPendingNotifyScanner = agentOrderPendingNotifyScanner;
    }

    public void execute()
    {
        agentOrderPendingNotifyScanner.sendNotifys();
    }
}
