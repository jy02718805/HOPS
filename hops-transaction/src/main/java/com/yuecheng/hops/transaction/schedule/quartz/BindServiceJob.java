package com.yuecheng.hops.transaction.schedule.quartz;


import com.yuecheng.hops.transaction.service.scanner.AgentOrderPengdingBindScanner;


public class BindServiceJob
{
    private AgentOrderPengdingBindScanner agentOrderPengdingBindScanner;

    public AgentOrderPengdingBindScanner getAgentOrderPengdingBindScanner()
    {
        return agentOrderPengdingBindScanner;
    }

    public void setAgentOrderPengdingBindScanner(AgentOrderPengdingBindScanner agentOrderPengdingBindScanner)
    {
        this.agentOrderPengdingBindScanner = agentOrderPengdingBindScanner;
    }

    public void execute()
    {
        agentOrderPengdingBindScanner.bindOrders();
    }
}
