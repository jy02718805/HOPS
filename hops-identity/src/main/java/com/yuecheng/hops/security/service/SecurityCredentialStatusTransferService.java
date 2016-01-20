package com.yuecheng.hops.security.service;


import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.security.entity.SecurityCredentialStatusTransfer;


/**
 * SecurityCredential状态机服务层(逻辑访问层)
 * 
 * @author Jinger 2014-10-22
 */
public interface SecurityCredentialStatusTransferService
{
    /**
     * 保存SecurityCredential状态机数据
     * 
     * @param SecurityCredentialStatusTransfer
     * @return
     */
    public SecurityCredentialStatusTransfer savaSecurityCredentialStatusTransfer(SecurityCredentialStatusTransfer securityCredentialStatusTransfer);

    /**
     * 根据Id查询SecurityCredential状态机数据
     * 
     * @param id
     * @return
     */
    public SecurityCredentialStatusTransfer querySecurityCredentialStatusTransferById(Long id);

    /**
     * 根据条件查询SecurityCredential状态及数据
     * 
     * @param oldStatus
     * @param newStatus
     * @return
     */
    public SecurityCredentialStatusTransfer querySecurityCredentialStatusTransferByParams(String oldStatus,
                                                                                          String newStatus);

    /**
     * 根据ID删除SecurityCredential状态机数据
     * 
     * @param id
     */
    public void deleteSecurityCredentialStatusTransferById(Long id);

    /**
     * 根据条件删除SecurityCredential状态机数据
     * 
     * @param oldStatus
     * @param newStatus
     */
    public void deleteSecurityCredentialStatusTransferByParams(String oldStatus, String newStatus);

    /**
     * 更新SecurityCredential状态机数据
     * 
     * @param SecurityCredentialStatusTransfer
     * @return
     */
    public SecurityCredentialStatusTransfer updateSecurityCredentialStatusTransfer(SecurityCredentialStatusTransfer securityCredentialStatusTransfer);

    /**
     * 根据用户类型查询SecurityCredential状态机数据列表
     * 
     * @return
     */
    public List<SecurityCredentialStatusTransfer> querySecurityCredentialStatusTransfer();

    /**
     * 根据条件分页查询SecurityCredential状态机数据
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @param bsort
     * @return
     */
    public YcPage<SecurityCredentialStatusTransfer> queryPageSecurityCredentialStatusTransfer(Map<String, Object> searchParams,
                                                                                              int pageNumber,
                                                                                              int pageSize,
                                                                                              BSort bsort);
}
