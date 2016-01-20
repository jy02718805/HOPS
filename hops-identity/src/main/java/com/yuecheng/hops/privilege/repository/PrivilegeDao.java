package com.yuecheng.hops.privilege.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.yuecheng.hops.privilege.entity.Privilege;


/**
 * 权限表数据访问层
 * 
 * @author：Jinger
 * @date：2014-08-29
 */

public interface PrivilegeDao extends PagingAndSortingRepository<Privilege, Long>, JpaSpecificationExecutor<Privilege>
{

    @Query("select p from Privilege p where p.parentPrivilegeId=:parentPrivilegeId order by p.privilegeId")
    List<Privilege> getPrivilegeList(@Param("parentPrivilegeId")
    Long parentPrivilegeId);

    @Query("select p from Privilege p ")
    List<Privilege> getAllPrivilegeList();

}
