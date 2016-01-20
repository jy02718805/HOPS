package com.yuecheng.hops.identity.service;


import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.identity.entity.IdentityStatus;
import com.yuecheng.hops.identity.entity.IdentityStatusTransfer;


/**
 * Identity状态机逻辑访问层
 * 
 * @author：Jinger
 * @date：2014-10-16
 */
public interface IdentityStatusTransferService
{
    /**
     * 保存Identity状态机数据
     * 
     * @param identityStatusTransfer
     * @return
     */
    public IdentityStatusTransfer savaIdentityStatusTransfer(IdentityStatusTransfer identityStatusTransfer);

    /**
     * 根据Id查询Identity状态机数据
     * 
     * @param id
     * @return
     */
    public IdentityStatusTransfer queryIdentityStatusTransferById(Long id);

    /**
     * 根据条件查询Identity状态及数据
     * 
     * @param oldStatus
     * @param newStatus
     * @param identityType
     * @return
     */
    public IdentityStatusTransfer queryIdentityStatusTransferByParams(IdentityStatus oldStatus,
                                                                      IdentityStatus newStatus,
                                                                      IdentityType identityType);

    /**
     * 根据ID删除Identity状态机数据
     * 
     * @param id
     */
    public void deleteIdentityStatusTransferById(Long id);

    /**
     * 根据条件删除Identity状态机数据
     * 
     * @param oldStatus
     * @param newStatus
     * @param identityType
     */
    public void deleteIdentityStatusTransferByParams(IdentityStatus oldStatus,
                                                     IdentityStatus newStatus,
                                                     IdentityType identityType);

    /**
     * 更新Identity状态机数据
     * 
     * @param identityStatusTransfer
     * @return
     */
    public IdentityStatusTransfer updateIdentityStatusTransfer(IdentityStatusTransfer identityStatusTransfer);

    /**
     * 根据用户类型查询Identity状态机数据列表
     * 
     * @param identityType
     * @return
     */
    public List<IdentityStatusTransfer> queryIdentityStatusTransfer(IdentityType identityType);

    /**
     * 根据条件分页查询Identity状态机数据
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @param bsort
     * @param identityType
     * @return
     */
    public YcPage<IdentityStatusTransfer> queryPageIdentityStatusTransfer(Map<String, Object> searchParams,
                                                                          int pageNumber,
                                                                          int pageSize,
                                                                          BSort bsort,
                                                                          IdentityType identityType);
}
