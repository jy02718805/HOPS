package com.yuecheng.hops.privilege.service;


import java.util.List;

import com.yuecheng.hops.privilege.entity.MenuSelect;
import com.yuecheng.hops.privilege.entity.Role;
import com.yuecheng.hops.privilege.entity.RoleMenu;


public interface RoleMenuQueryService
{
    /**
     * 根绝角色和菜单级别获取角色权限信息
     * 
     * @param role
     * @param funcLevel
     * @return
     */
    public List<RoleMenu> queryRoleMenuByRole(Role role, String funcLevel);

    /**
     * 根据角色列表和菜单级别获取所有角色权限信息
     * 
     * @param roleList
     * @param funcLevel
     * @return
     */
    public List<RoleMenu> queryRoleMenuByRoleList(List<Role> roleList, String funcLevel);

    /**
     * 根据菜单ID获取角色权限信息
     * 
     * @param menuId
     * @return
     */
    public List<RoleMenu> queryRoleMenuByMenuId(Long menuId);

    /**
     * 根据菜单ID和角色ID获取角色权限信息
     * 
     * @param menuId
     * @param roleId
     * @return
     */
    public List<RoleMenu> queryRoleMenuByParams(Long menuId, Long roleId);

    /**
     * 根据菜单级别和角色列表查找已配置的菜单
     * 
     * @param menuLevel
     * @param roleList
     * @return
     */
    public List<MenuSelect> queryMenuSelectByParams(String menuLevel, List<Role> roleList);

    /**
     * 根据角色ID获取角色权限信息
     * 
     * @param roleId
     * @return
     */
    public List<RoleMenu> queryRoleMenuByRoleId(Long roleId);
    /**
     * 批量保存角色权限信息
     * 
     * @param menuid
     * @param role
     * @param updateName
     * @return
     */
    public String saveRoleMenuList(String menuid, Role role, String updateName);
}
