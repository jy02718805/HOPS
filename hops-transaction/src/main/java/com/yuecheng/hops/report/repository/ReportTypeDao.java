package com.yuecheng.hops.report.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.report.entity.ReportType;


@Service
public interface ReportTypeDao extends PagingAndSortingRepository<ReportType, Long>, JpaSpecificationExecutor<ReportType>
{
    @Query("select r from ReportType r where r.reportMetadataType=:reportMetadataType")
    public List<ReportType> getReportTypeByType(@Param("reportMetadataType")
    String reportMetadataType);

    @Query("select r from ReportType r where r.reportFileName=:reportFileName and r.reportMetadataType=:reportMetadataType")
    public List<ReportType> getReportTypeByReportFileName(@Param("reportFileName")
    String reportFileName, @Param("reportMetadataType")
    String reportMetadataType);
}
