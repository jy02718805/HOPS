package com.yuecheng.hops.numsection.repository;


/**
 * 号段表数据层
 * 
 * @author Jinger
 * @date：2013-10-15
 */
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.yuecheng.hops.numsection.entity.NumSection;


public interface NumSectionDao extends PagingAndSortingRepository<NumSection, String>, JpaSpecificationExecutor<NumSection>
{
    @Query("select ns from NumSection ns ")
    List<NumSection> selectAll();
    
    @Query("select ns from NumSection ns where ns.sectionId=:numSectionId ")
    NumSection queryNumSectionById(@Param("numSectionId") String numSectionId);
}
