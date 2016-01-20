package com.yuecheng.hops.privilege.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.privilege.entity.IdentityRole;


/**
 * 用户角色表逻辑访问层
 * 
 * @author：Jinger
 * @date：2013-09-27
 */

public interface IdentityRoleDao extends PagingAndSortingRepository<IdentityRole, Long>, JpaSpecificationExecutor<IdentityRole>
{
    @Query("select ir from IdentityRole ir")
    List<IdentityRole> selectAll();

    @Query("select ir from IdentityRole ir where ir.role.roleId=:roleId")
    List<IdentityRole> getIdentityRoleListByRole(@Param("roleId")
    Long roleId);

    @Query("select ir from IdentityRole ir where ir.identityId=:identityId")
    List<IdentityRole> getIdentityRoleListByIdentity(@Param("identityId")
    Long identityId);

    @Query("select ir from IdentityRole ir where ir.role.roleId=:roleId and ir.identityId=:identityId and ir.identityType=:identityType")
    IdentityRole findOneByMany(@Param("roleId")
    Long roleId, @Param("identityId")
    Long identityId, @Param("identityType")
    IdentityType identityType);

    @Query("select ir from IdentityRole ir where ir.identityId=:identityId and ir.identityType=:identityType")
    List<IdentityRole> getIdentityRoleList(@Param("identityId")
    Long identityId, @Param("identityType")
    IdentityType identityType);
}
