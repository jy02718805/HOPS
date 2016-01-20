package com.yuecheng.hops.privilege.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.yuecheng.hops.privilege.entity.PageResource;


/**
 * 页面资源权限表数据访问层
 * 
 * @author：Jinger
 * @date：2013-09-17
 */

public interface PageResourceDao extends PagingAndSortingRepository<PageResource, Long>, JpaSpecificationExecutor<PageResource>
{
    @Query("select pr from PageResource pr order by pr.pageUrl ")
    List<PageResource> selectAll();

    @Query("select pr from PageResource pr where pr.status=:status")
    List<PageResource> queryPageResourceByStatus(@Param("status")
    String status);

    @Query("select pr from PageResource pr where pr.pageUrl=:pageUrl")
    PageResource getPageResourceByUrl(@Param("pageUrl")
    String pageUrl);
}
