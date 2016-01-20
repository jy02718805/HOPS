package com.yuecheng.hops.report.repository.jpa;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.report.entity.ProfitReportRecord;


@Service
public interface ProfitReportRecordJpaDao extends PagingAndSortingRepository<ProfitReportRecord, Long>, JpaSpecificationExecutor<ProfitReportRecord>
{
    @Query(value = "select p from  ProfitReportRecord p where 1=1  and p.reportStatus!=:reportStatus order by p.updateDate desc")
    public List<ProfitReportRecord> queryProfitReportRecordList(@Param("reportStatus")
    String reportStatus);
}
