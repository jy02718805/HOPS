package com.yuecheng.hops.privilege.service;


import java.util.List;

import com.yuecheng.hops.privilege.entity.Menu;
import com.yuecheng.hops.privilege.entity.MenuSelect;
import com.yuecheng.hops.privilege.entity.RoleMenu;


/**
 * 菜单逻辑访问层
 * 
 * @author：Jinger
 * @date：2013-09-27
 */

public interface MenuSelectLogic
{
    /**
     * 根据菜单列表和角色菜单列表获取所有分配过的菜单，并将现在开启的状态一并展示
     * 
     * @param roleList
     * @param roleMenuList
     * @return
     */
    public List<MenuSelect> queryAllMenuSelect(List<Menu> roleList, List<RoleMenu> roleMenuList);

    /**
     * 根据新分配和原始分配的角色菜单数据列表，获取所有最新的角色菜单列表，并将状态同步（之前分配现在不分配状态关掉，之前调整为未分配现在分配的状态开启）
     * 
     * @param newRMList
     * @param oldRMList
     * @param updateName
     * @return
     */
    public List<RoleMenu> queryAllUpdateRoleMenu(List<RoleMenu> newRMList,
                                                 List<RoleMenu> oldRMList, String updateName);
}
