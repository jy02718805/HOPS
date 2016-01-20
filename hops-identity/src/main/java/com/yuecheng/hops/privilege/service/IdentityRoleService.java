package com.yuecheng.hops.privilege.service;


/**
 * 用户角色表逻辑访问层
 * 
 * @author：Jinger
 * @date：2013-09-27
 */

import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.privilege.entity.IdentityRole;


public interface IdentityRoleService
{
    /**
     * 保存用户角色
     * 
     * @param identityRole
     * @return
     */
    public IdentityRole saveIdentityRole(IdentityRole identityRole);

    /**
     * 批量保存用户角色
     * 
     * @param identityRoleList
     * @return
     */
    public String saveIdentityRole(List<IdentityRole> identityRoleList);

    /**
     * 根据ID删除用户角色
     * 
     * @param identityRoleId
     */
    public void deleteIdentityRole(Long identityRoleId);

    /**
     * 根据ID查找用户角色
     * 
     * @param identityRoleId
     * @return
     */
    public IdentityRole queryIdentityRoleById(Long identityRoleId);

    /**
     * 根据不同条件获取用户角色
     * 
     * @param identityRoleId
     *            ID
     * @param identityId
     *            用户ID
     * @param identityType
     *            用户类型
     * @return
     */
    public IdentityRole queryIdentityRoleByIdentity(Long identityRoleId, Long identityId,
                                                    IdentityType identityType);

    /**
     * 获取所有的用户角色列表
     * 
     * @return
     */
    public List<IdentityRole> queryAllIdentityRole();

    /**
     * 分页查询用户角色列表
     * 
     * @param searchParams
     *            分页条件
     * @param pageNumber
     *            页码
     * @param pageSize
     *            页大小
     * @param bsort
     *            排序
     * @return
     */
    public YcPage<IdentityRole> queryIdentityRole(Map<String, Object> searchParams,
                                                  int pageNumber, int pageSize, BSort bsort);

    /**
     * 保存用户角色列表
     * 
     * @param roleid
     * @param identityId
     * @param identityType
     * @param updateName
     * @return
     */
    public String saveIdentityRoleList(String[] roleid, Long identityId,
                                       IdentityType identityType, String updateName);
}
