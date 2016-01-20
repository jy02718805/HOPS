package com.yuecheng.hops.report.repository.jpa;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.report.entity.ProfitReportInfo;


@Service
public interface ProfitReportJpaDao extends PagingAndSortingRepository<ProfitReportInfo, Long>, JpaSpecificationExecutor<ProfitReportInfo>
{

}
