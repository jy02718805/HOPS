package com.yuecheng.hops.report.repository.jpa;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.report.entity.RefundReportRecord;


@Service
public interface RefundReportRecordJpaDao extends PagingAndSortingRepository<RefundReportRecord, Long>, JpaSpecificationExecutor<RefundReportRecord>
{
//    @Query(value = "select p from  refundReportRecord p where 1=1  and p.reportStatus!=:reportStatus order by p.updateDate desc")
//    public List<RefundReportRecord> queryRefundReportRecordList(@Param("reportStatus")
//    String reportStatus);
}
