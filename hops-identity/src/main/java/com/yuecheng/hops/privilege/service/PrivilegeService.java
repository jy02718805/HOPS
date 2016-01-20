package com.yuecheng.hops.privilege.service;


/**
 * 权限表逻辑访问层
 * 
 * @author：Jinger
 * @date：2014-08-29
 */
import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.privilege.entity.Privilege;


public interface PrivilegeService
{

    /**
     * 保存权限
     * 
     * @param privilege
     * @return
     */
    public Privilege savaPrivilege(Privilege privilege);

    /**
     * 根据ID删除权限
     * 
     * @param privilegeId
     */
    public void delPrivilege(Long privilegeId);

    /**
     * 更新权限信息
     * 
     * @param privilege
     * @return
     */
    public Privilege updatePrivilege(Privilege privilege);

    /**
     * 根据ID查找权限
     * 
     * @param privilegeId
     * @return
     */
    public Privilege queryPrivilegeById(Long privilegeId);

    /**
     * 根据父ID获取子权限列表
     * 
     * @param parentPrivilegeId
     * @return
     */
    public List<Privilege> queryPrivilegeByPId(Long parentPrivilegeId);

    /**
     * 根据条件分页查询
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @param bsort
     * @return
     */
    public YcPage<Privilege> queryPagePrivilege(Map<String, Object> searchParams, int pageNumber,
                                                int pageSize, BSort bsort);

    // 得到全部权限信息
    public List<Privilege> queryAllPrivilege();
}
