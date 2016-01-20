package com.yuecheng.hops.privilege.service;


import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.privilege.entity.RoleMenu;


public interface RoleMenuService
{
    /**
     * 保存角色权限信息
     * 
     * @param roleMenuPrivilege
     * @return
     */
    public RoleMenu saveRoleMenu(RoleMenu roleMenu);

    /**
     * 批量保存角色权限信息
     * 
     * @param roleMenuPrivilegeList
     * @return
     */
    public String saveRoleMenu(List<RoleMenu> roleMenuList);

    /**
     * 根据ID删除角色权限信息
     * 
     * @param roleMenuPrivilege
     */
    public void deleteRoleMenuById(Long roleMenuId);

    /**
     * 根据ID查找角色权限信息
     * 
     * @param roleMenuPrivilegeId
     * @return
     */
    public RoleMenu queryRoleMenuById(Long roleMenuId);

    /**
     * 获取所有的角色权限信息
     * 
     * @return
     */
    public List<RoleMenu> queryAllRoleMenu();

    /**
     * 分页查询角色权限信息
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @param bsort
     * @return
     */
    public YcPage<RoleMenu> queryRoleMenu(Map<String, Object> searchParams, int pageNumber,
                                          int pageSize, BSort bsort);


    /**
     * 根据角色ID删除角色权限信息
     * 
     * @param roleId
     */
    public void deleteRoleMenuByRoleId(Long roleId);

    /**
     * 根据菜单ID删除所有权限信息
     * 
     * @param menuId
     * @return
     */
    public void deleteRoleMenuByMenuId(Long menuId);

    /**
     * 根据ID批量删除所有权限信息
     * 
     * @param roleMenuList
     * @return
     */
    public void deleteRoleMenuList(List<RoleMenu> roleMenuList);
}
