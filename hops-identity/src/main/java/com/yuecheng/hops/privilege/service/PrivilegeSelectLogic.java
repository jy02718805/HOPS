package com.yuecheng.hops.privilege.service;


import java.util.List;

import com.yuecheng.hops.privilege.entity.RolePrivilege;


public interface PrivilegeSelectLogic
{
    /**
     * 根据新分配和原始分配的角色权限数据列表，获取所有最新的角色权限列表，并将状态同步（之前分配现在不分配状态关掉，之前调整为未分配现在分配的状态开启）
     * 
     * @param newRPList
     * @param oldRPList
     * @param updateName
     * @return
     */
    public List<RolePrivilege> queryAllUpdateRolePrivilege(List<RolePrivilege> newRPList,
                                                           List<RolePrivilege> oldRPList,
                                                           String updateName);
}
