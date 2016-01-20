package com.yuecheng.hops.report.repository.jpa;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.report.entity.AccountReportRecord;


@Service
public interface AccountReportRecordJpaDao extends PagingAndSortingRepository<AccountReportRecord, Long>, JpaSpecificationExecutor<AccountReportRecord>
{
    @Query(value = "select a from  AccountReportRecord a where 1=1  and a.reportStatus!=:reportStatus order by a.updateDate desc")
    public List<AccountReportRecord> queryAccountReportControlList(@Param("reportStatus")
    String reportStatus);

}
