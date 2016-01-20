package com.yuecheng.hops.transaction.config;


import java.util.List;

import com.yuecheng.hops.transaction.config.entify.query.AgentQueryHistory;


public interface AgentQueryHistoryService
{
    public AgentQueryHistory findAgentQueryHistoryByMerchantId(Long merchantId);

    public List<AgentQueryHistory> findAll();

    public void save(AgentQueryHistory agentQueryHistory);
}
