package com.yuecheng.hops.report.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.report.entity.ReportProperty;


@Service
public interface ReportPropertyDao extends PagingAndSortingRepository<ReportProperty, Long>, JpaSpecificationExecutor<ReportProperty>
{
    @Query("select r from ReportProperty r where r.reportTypeId=:reportTypeId order by reportPropertyNum")
    List<ReportProperty> queryAll(@Param("reportTypeId")
    Long reportTypeId);
}
