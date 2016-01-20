package com.yuecheng.hops.report.repository.jpa;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.report.entity.AgentTransactionReportInfo;


@Service
public interface AgentTransactionReportJpaDao extends PagingAndSortingRepository<AgentTransactionReportInfo, Long>, JpaSpecificationExecutor<AgentTransactionReportInfo>
{}
