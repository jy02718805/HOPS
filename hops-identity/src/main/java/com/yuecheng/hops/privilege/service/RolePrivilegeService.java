package com.yuecheng.hops.privilege.service;


/**
 * 角色权限表逻辑访问层
 * 
 * @author：Jinger
 * @date：2013-09-18
 */
import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.privilege.entity.RolePrivilege;


public interface RolePrivilegeService
{
    /**
     * 保存角色权限信息
     * 
     * @param roleMenuPrivilege
     * @return
     */
    public RolePrivilege saveRolePrivilege(RolePrivilege rolePrivilege);

    /**
     * 批量保存角色权限信息
     * 
     * @param roleMenuPrivilegeList
     * @return
     */
    public String saveRolePrivilege(List<RolePrivilege> rolePrivilegeList);

    /**
     * 根据ID删除角色权限信息
     * 
     * @param roleMenuPrivilege
     */
    public void deleteRolePrivilege(Long rolePrivilege);

    /**
     * 根据ID查找角色权限信息
     * 
     * @param roleMenuPrivilegeId
     * @return
     */
    public RolePrivilege queryRolePrivilegeById(Long rolePrivilegeId);

    /**
     * 获取所有的角色权限信息
     * 
     * @return
     */
    public List<RolePrivilege> queryAllRolePrivilege();

    /**
     * 分页查询角色权限信息
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @param bsort
     * @return
     */
    public YcPage<RolePrivilege> queryRolePrivilege(Map<String, Object> searchParams,
                                                    int pageNumber, int pageSize, BSort bsort);


    /**
     * 根据角色ID删除角色权限信息
     * 
     * @param roleId
     */
    public void deleteByRoleId(Long roleId);
    
    /**
     * 根据权限ID删除角色权限信息
     * 
     * @param roleId
     */
    public void deleteByPrivilegeId(Long privilegeId);
    /**
     * 根据角色权限列表删除角色权限信息
     * 
     * @param rolePrivilegeList
     */
    public void deleteRolePrivilegeList(List<RolePrivilege> rolePrivilegeList);

}
