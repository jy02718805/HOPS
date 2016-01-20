package com.yuecheng.hops.privilege.service;


import java.util.List;

import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.privilege.entity.IdentityRole;
import com.yuecheng.hops.privilege.entity.IdentityRoleSelect;
import com.yuecheng.hops.privilege.entity.Role;


public interface IdentityRoleQueryService
{

    /**
     * 根据角色ID获取用户角色列表
     * 
     * @param roleId
     *            角色ID
     * @return
     */
    public List<IdentityRole> queryIdentityRoleByRId(Long roleId);

    /**
     * 根据用户ID获取用户角色列表
     * 
     * @param identityId
     * @return
     */
    public List<IdentityRole> queryIdentityRoleByIdentityId(Long identityId);

    /**
     * 根据用户ID和用户类型获取用户角色列表
     * 
     * @param identityId
     * @param identityType
     * @return
     */
    public List<IdentityRole> queryIdentityRole(Long identityId, IdentityType identityType);

    /**
     * 根据用户ID和用户类型获取已配置的用户列表
     * 
     * @param identityId
     * @param identityType
     * @return
     */
    public List<IdentityRoleSelect> queryIdentityRoleSelect(Long identityId,
                                                            IdentityType identityType);

    /**
     * 根据用户ID和用户类型以及角色ID获取已配置的用户角色列表
     * 
     * @param identityId
     * @param identityType
     * @param roleType
     * @return
     */
    public List<IdentityRoleSelect> queryIRSelectByRoleType(Long identityId,
                                                            IdentityType identityType,
                                                            String roleType);

    /**
     * 根据用户ID和用户类型获取角色列表
     * 
     * @param identityId
     * @param identityType
     * @return
     */
    public List<Role> queryRoleByIdentity(Long identityId, IdentityType identityType);

}