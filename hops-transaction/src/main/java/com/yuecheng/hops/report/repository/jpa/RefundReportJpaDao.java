package com.yuecheng.hops.report.repository.jpa;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.report.entity.RefundReportInfo;


@Service
public interface RefundReportJpaDao extends PagingAndSortingRepository<RefundReportInfo, Long>, JpaSpecificationExecutor<RefundReportInfo>
{

}
