package com.yuecheng.hops.report.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.report.entity.ReportTerm;


@Service
public interface ReportTermDao extends PagingAndSortingRepository<ReportTerm, Long>, JpaSpecificationExecutor<ReportTerm>
{
    @Query("select r from ReportTerm r where r.reportTypeId=:reportTypeId and r.reportTermStatus="
           + Constant.ReportType.REPORT_TERM_STATUS_OPEN)
    public List<ReportTerm> getReportTermsByReportTypeId(@Param("reportTypeId")
    Long reportTypeId);

    @Query("select r from ReportTerm r where r.reportMetadataId=:reportMetadataId and r.reportTypeId=:reportTypeId")
    public ReportTerm getReportTermsByMetadataId(@Param("reportMetadataId")
    Long reportMetadataId, @Param("reportTypeId")
    Long reportTypeId);

}
