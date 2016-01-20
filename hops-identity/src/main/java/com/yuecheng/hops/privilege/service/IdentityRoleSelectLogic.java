package com.yuecheng.hops.privilege.service;


import java.util.List;

import com.yuecheng.hops.privilege.entity.IdentityRole;
import com.yuecheng.hops.privilege.entity.IdentityRoleSelect;
import com.yuecheng.hops.privilege.entity.Role;


/**
 * 用户在全区角色中订购的角色逻辑层
 * 
 * @author Jinger
 * @date 2013-10-14
 */

public interface IdentityRoleSelectLogic
{
    /**
     * 根据角色列表和用户角色列表获取所有分配过的角色，并将现在开启的状态一并展示
     * 
     * @param roleList
     * @param identityRoleList
     * @return
     */
    public List<IdentityRoleSelect> queryAllIdentityRoleSelect(List<Role> roleList,
                                                               List<IdentityRole> identityRoleList);

    /**
     * 根据新分配和原始分配的用户角色数据列表，获取所有最新的用户角色列表，并将状态同步（之前分配现在不分配状态关掉，之前调整为未分配现在分配的状态开启）
     * 
     * @param newIRList
     * @param oldIRList
     * @param updateName
     * @return
     */
    public List<IdentityRole> queryAllUpdateIdentityRole(List<IdentityRole> newIRList,
                                                         List<IdentityRole> oldIRList,
                                                         String updateName);
}
