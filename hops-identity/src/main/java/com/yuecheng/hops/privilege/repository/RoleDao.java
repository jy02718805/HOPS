package com.yuecheng.hops.privilege.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.yuecheng.hops.privilege.entity.Role;


/**
 * 角色表数据访问层
 * 
 * @author：Jinger
 * @date：2013-09-17
 */

public interface RoleDao extends PagingAndSortingRepository<Role, Long>, JpaSpecificationExecutor<Role>
{

    @Query("select r from Role r where r.status=:status")
    List<Role> getRoleListByStatus(@Param("status")
    String status);

    @Query("select r from Role r where r.roleType=:roleType and r.status=0")
    List<Role> getRoleListByRoleType(@Param("roleType")
    String roleType);
}
