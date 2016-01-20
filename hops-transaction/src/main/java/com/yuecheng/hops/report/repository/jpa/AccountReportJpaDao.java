package com.yuecheng.hops.report.repository.jpa;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.report.entity.AccountReportInfo;


@Service
public interface AccountReportJpaDao extends PagingAndSortingRepository<AccountReportInfo, Long>, JpaSpecificationExecutor<AccountReportInfo>
{

}
