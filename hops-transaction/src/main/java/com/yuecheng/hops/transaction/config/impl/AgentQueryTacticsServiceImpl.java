package com.yuecheng.hops.transaction.config.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.transaction.config.AgentQueryTacticsService;
import com.yuecheng.hops.transaction.config.entify.query.AgentQueryTactics;
import com.yuecheng.hops.transaction.config.repository.AgentQueryTacticsDao;


@Service("agentQueryTacticsService")
public class AgentQueryTacticsServiceImpl implements AgentQueryTacticsService
{
    @Autowired
    private AgentQueryTacticsDao agentQueryTacticsDao;

    @Override
    public List<AgentQueryTactics> findAll()
    {
        List<AgentQueryTactics> agentQueryTactics = (List<AgentQueryTactics>)agentQueryTacticsDao.findAll();
        return agentQueryTactics;
    }

}
