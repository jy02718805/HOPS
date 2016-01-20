package com.yuecheng.hops.transaction.config.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.transaction.config.AgentQueryHistoryService;
import com.yuecheng.hops.transaction.config.entify.query.AgentQueryHistory;
import com.yuecheng.hops.transaction.config.repository.AgentQueryHistoryDao;


@Service("agentQueryHistoryService")
public class AgentQueryHistoryServiceImpl implements AgentQueryHistoryService
{
    @Autowired
    private AgentQueryHistoryDao agentQueryHistoryDao;

    @Override
    public AgentQueryHistory findAgentQueryHistoryByMerchantId(Long merchantId)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.DownQueryHistory.MERCHANT_ID, new SearchFilter(
            EntityConstant.DownQueryHistory.MERCHANT_ID, Operator.EQ, merchantId));
        Specification<AgentQueryHistory> spec_DownQueryHistory = DynamicSpecifications.bySearchFilter(
            filters.values(), AgentQueryHistory.class);
        AgentQueryHistory dqh = agentQueryHistoryDao.findOne(spec_DownQueryHistory);
        return dqh;
    }

    @Override
    public List<AgentQueryHistory> findAll()
    {
        List<AgentQueryHistory> agentQueryHistorys = (List<AgentQueryHistory>)agentQueryHistoryDao.findAll();
        return agentQueryHistorys;
    }

    @Override
    @Transactional
    public void save(AgentQueryHistory agentQueryHistory)
    {
        agentQueryHistoryDao.save(agentQueryHistory);
    }

}
