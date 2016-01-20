package com.yuecheng.hops.report.repository.jpa;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.report.entity.TransactionReportRecord;


@Service
public interface TransactionReportRecordJpaDao extends PagingAndSortingRepository<TransactionReportRecord, Long>, JpaSpecificationExecutor<TransactionReportRecord>
{
    @Query(value = "select t from  TransactionReportRecord t where 1=1  and t.reportStatus!=:reportStatus and t.reportType=:reportType order by t.updateDate desc")
    public List<TransactionReportRecord> queryTransactionReportRecordList(@Param("reportStatus")
                                                                           String reportStatus,
                                                                           @Param("reportType")
                                                                           String reportType);

}
