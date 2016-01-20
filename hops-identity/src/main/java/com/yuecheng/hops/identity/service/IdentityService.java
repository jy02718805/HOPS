package com.yuecheng.hops.identity.service;


import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.identity.entity.AbstractIdentity;
import com.yuecheng.hops.identity.entity.IdentityStatus;


/**
 * Identity逻辑访问层
 * 
 * @author：Jinger
 * @date：2013-09-24
 */
public interface IdentityService
{
    /**
     * 根据ID和类型查找Identity
     * 
     * @param identityId
     *            ID
     * @param identityType
     *            类型
     * @return
     */
    public AbstractIdentity findIdentityByIdentityId(Long identityId, IdentityType identityType);

    /**
     * 根据ID和类型删除Identity
     * 
     * @param identityId
     *            ID
     * @param identityType
     *            类型
     */
    public void deleteIdentity(Long identityId, IdentityType identityType);

    /**
     * 保存Identity
     * 
     * @param identity
     *            Identity
     * @param identityType
     *            类型
     * @return
     */
    public AbstractIdentity saveIdentity(AbstractIdentity identity, String updateUser);

    /**
     * 分页查询Identity列表
     * 
     * @param searchParams
     *            分页查询条件
     * @param pageNumber
     *            页码
     * @param pageSize
     *            页大小
     * @param bsort
     *            排序
     * @param identityType
     *            类型
     * @return
     */
    public YcPage<AbstractIdentity> queryIdentity(Map<String, Object> searchParams,
                                                  int pageNumber, int pageSize, BSort bsort,
                                                  IdentityType identityType);

    /**
     * 获取所有Identity信息列表
     * 
     * @param identityType
     *            类型
     * @return
     */
    public List<AbstractIdentity> getAllIdentityList(IdentityType identityType);

    /**
     * 修改Identity的状态
     * 
     * @param status
     * @param identity
     * @return
     */
    public AbstractIdentity updateIdentityStatus(IdentityStatus status, Long identityId,
                                                 IdentityType identityType);
}
