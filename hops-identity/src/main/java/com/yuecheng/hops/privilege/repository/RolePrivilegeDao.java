package com.yuecheng.hops.privilege.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.yuecheng.hops.privilege.entity.RolePrivilege;


/**
 * 角色权限表数据访问层
 * 
 * @author：Jinger
 * @date：2013-09-23
 */

public interface RolePrivilegeDao extends PagingAndSortingRepository<RolePrivilege, Long>, JpaSpecificationExecutor<RolePrivilege>
{
    @Query("select rp from RolePrivilege rp")
    List<RolePrivilege> selectAll();

    // @Query("select rp from RolePrivilege rp where rp.role.id=:roleId and rp.functionLevel=:funcLevel")
    // List<RolePrivilege> getRolePrivilegeByRoleId(@Param("roleId")Long
    // roleId,@Param("funcLevel")String funcLevel);

    @Query("select rp from RolePrivilege rp where rp.privilege.privilegeId=:privilegeId")
    List<RolePrivilege> getRolePrivilegeByPriId(@Param("privilegeId")
    Long privilegeId);

    @Query("select rp from RolePrivilege rp where rp.privilege.privilegeId=:privilegeId and rp.role.roleId=:roleId")
    List<RolePrivilege> getRolePrivilegeList(@Param("privilegeId")
    Long privilegeId, @Param("roleId")
    Long roleId);

    // @Query("select rp from RolePrivilege rp where rp.menu.id=:menuId and rp.role.id=:roleId and rp.privilegeType=:privilegeType")
    // List<RolePrivilege> getRolePrivilegeList(@Param("menuId")Long menuId,@Param("roleId")Long
    // roleId,@Param("menuId")PrivilegeType privilegeType);

    @Query("select rp from RolePrivilege rp where rp.role.roleId=:roleId")
    List<RolePrivilege> getRolePrivilegeByRoleId(@Param("roleId")
    Long roleId);

}
