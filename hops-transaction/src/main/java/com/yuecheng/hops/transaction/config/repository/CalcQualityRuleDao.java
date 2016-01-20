package com.yuecheng.hops.transaction.config.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.transaction.config.entify.order.CalcQualityRule;


@Service
public interface CalcQualityRuleDao extends PagingAndSortingRepository<CalcQualityRule, Long>, JpaSpecificationExecutor<CalcQualityRule>
{

}
