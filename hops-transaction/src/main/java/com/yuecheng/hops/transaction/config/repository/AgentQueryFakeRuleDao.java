package com.yuecheng.hops.transaction.config.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.transaction.config.entify.fake.AgentQueryFakeRule;


@Service
public interface AgentQueryFakeRuleDao extends PagingAndSortingRepository<AgentQueryFakeRule, Long>, JpaSpecificationExecutor<AgentQueryFakeRule>
{

}
