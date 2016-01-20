package com.yuecheng.hops.report.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.report.entity.ReportMetadata;


@Service
public interface ReportMetadataDao extends PagingAndSortingRepository<ReportMetadata, Long>, JpaSpecificationExecutor<ReportMetadata>
{
    @Query("select m from ReportMetadata m where m.metadataType=:metadataType order by m.reportMetadataId")
    public List<ReportMetadata> getAllmetadataByType(@Param("metadataType")
    String metadataType);

    @Query("select m from ReportMetadata m where m.metadataType=:metadataType and m.metadataSign=1")
    public List<ReportMetadata> getmetadataByType(@Param("metadataType")
    String metadataType);
}
