package com.yuecheng.hops.privilege.service;


import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
/**
 * 角色表逻辑访问层
 * 
 * @author：Jinger
 * @date：2013-09-17
 */
import com.yuecheng.hops.privilege.entity.Role;


public interface RoleService
{
    /**
     * 保存角色
     * 
     * @param role
     * @return
     */
    public Role saveRole(Role role);

    /**
     * 给据ID删除角色
     * 
     * @param roleId
     */
    public void deleteRole(Long roleId);

    /**
     * 根据角色ID更新状态
     * 
     * @param roleId
     * @param status
     */
    public void updateRole(Long roleId, String status);

    /**
     * 根据ID查找角色
     * 
     * @param roleId
     * @return
     */
    public Role queryRoleById(Long roleId);

    /**
     * 查询所有已开通的角色列表
     * 
     * @return
     */
    public List<Role> queryRoleListByStatus(String status);

    /**
     * 根据角色类型查询角色列表
     * 
     * @param roleType
     * @return
     */
    public List<Role> queryRoleListByRoleType(String roleType);

    /**
     * 分页查询角色列表
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @param bsort
     * @return
     */
    public YcPage<Role> queryRole(Map<String, Object> searchParams, int pageNumber, int pageSize,
                                  BSort bsort);
}
