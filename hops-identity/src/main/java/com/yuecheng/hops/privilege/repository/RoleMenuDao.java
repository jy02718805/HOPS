package com.yuecheng.hops.privilege.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.yuecheng.hops.privilege.entity.RoleMenu;


/**
 * 角色菜单表数据访问层
 * 
 * @author：Jinger
 * @date：2013-09-17
 */
public interface RoleMenuDao extends PagingAndSortingRepository<RoleMenu, Long>, JpaSpecificationExecutor<RoleMenu>
{
    @Query("select rm from RoleMenu rm")
    List<RoleMenu> selectAll();

    @Query("select rm from RoleMenu rm where rm.role.roleId=:roleId and rm.menu.menuLevel=:menuLevel")
    List<RoleMenu> getRoleMenuByRoleId(@Param("roleId")
    Long roleId, @Param("menuLevel")
    String menuLevel);

    @Query("select rm from RoleMenu rm where rm.menu.menuId=:menuId")
    List<RoleMenu> getRoleMenuByMenuId(@Param("menuId")
    Long menuId);

    @Query("select rm from RoleMenu rm where rm.menu.menuId=:menuId and rm.role.roleId=:roleId")
    List<RoleMenu> getRoleMenuList(@Param("menuId")
    Long menuId, @Param("roleId")
    Long roleId);

    // @Query("select rm from RoleMenu rm where rm.menu.id=:menuId and rm.role.id=:roleId and rm.privilegeType=:privilegeType")
    // List<RoleMenu> getRoleMenuList(@Param("menuId")Long menuId,@Param("roleId")Long
    // roleId,@Param("menuId")PrivilegeType privilegeType);

    @Query("select rm from RoleMenu rm where rm.role.roleId=:roleId")
    List<RoleMenu> getRoleMenuByRoleId(@Param("roleId")
    Long roleId);
}
