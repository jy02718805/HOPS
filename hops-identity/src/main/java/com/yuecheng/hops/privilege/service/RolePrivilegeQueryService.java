package com.yuecheng.hops.privilege.service;


import java.util.List;

import com.yuecheng.hops.privilege.entity.Role;
import com.yuecheng.hops.privilege.entity.RolePrivilege;


public interface RolePrivilegeQueryService
{

    /**
     * 根绝角色和菜单级别获取角色权限信息
     * 
     * @param role
     * @param funcLevel
     * @return
     */
    public List<RolePrivilege> queryRolePrivilegeByRole(Role role);

    //
    // /**
    // * 根据角色列表和菜单级别获取所有角色权限信息
    // * @param roleList
    // * @param funcLevel
    // * @return
    // */
    // public List<RolePrivilege> getRolePrivilegeByRoleList(
    // List<Role> roleList);
    /**
     * 根据角色列表和菜单级别获取所有角色权限信息
     * 
     * @param roleList
     * @param funcLevel
     * @return
     */
    public List<RolePrivilege> queryRolePrivilegeByRoleList(List<Role> roleList);

    /**
     * 根据角色列表和菜单级别获取所有角色权限信息
     * 
     * @param roleList
     * @param funcLevel
     * @return
     */
    public List<String> queryPermissionsByRoleList(List<Role> roleList);

    /**
     * 根据角色列表和菜单级别获取所有角色权限信息
     * 
     * @param roleList
     * @param funcLevel
     * @return
     */
    public List<String> queryPermissionsByRoleId(Long roleId);

    /**
     * 根据菜单ID获取角色权限信息
     * 
     * @param menuId
     * @return
     */
    public List<RolePrivilege> queryRolePrivilegeByMenuId(Long menuId);

    /**
     * 根据角色ID获取角色权限信息
     * 
     * @param roleId
     * @return
     */
    public List<RolePrivilege> queryRolePrivilegeByRoleId(Long roleId);
    /**
     * 根据privilegeId和roleId得到RolePrivilege集合
     * 
     * @param privilegeId
     * @param roleId
     * @return
     */
    public List<RolePrivilege> queryRolePrivilegeByParams(Long privilegeId, Long roleId);
    /**
     * 根据privilegeId得到RolePrivilege集合
     * 
     * @param privilegeId
     * @return
     */
    public List<RolePrivilege> queryRolePrivilegeByPriId(Long privilegeId);
    /**
     * 批量保存角色权限信息
     * 
     * @param menuid
     * @param role
     * @param updateName
     * @return
     */
    public String saveRolePrivilegeList(String menuid, Role role, String updateName);
}