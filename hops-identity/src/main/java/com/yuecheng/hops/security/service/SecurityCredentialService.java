package com.yuecheng.hops.security.service;


import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.identity.entity.AbstractIdentity;
import com.yuecheng.hops.security.entity.SecurityCredential;
import com.yuecheng.hops.security.entity.vo.SecurityCredentialVo;


/**
 * 密匙表逻辑访问层
 * 
 * @author：Jinger
 * @date：2013-09-26
 */

public interface SecurityCredentialService
{
    

    /**
     * 保存密匙信息
     * 
     * @param securityCredential
     *            密匙信息
     * @return
     */
    public SecurityCredential saveSecurityCredential(SecurityCredential securityCredential);

    /**
     * 根据ID删除密匙信息
     * 
     * @param securityCredentialId
     *            密匙信息ID
     */
    public void deleteSecurityCredential(Long securityCredentialId);

    /**
     * 更新密匙信息
     * 
     * @param securityCredential
     *            密匙信息
     * @return
     */
    public SecurityCredential updateSecurityCredential(SecurityCredential securityCredential);


    
    /**
     * 根据ID查找密匙信息
     * 
     * @param securityCredentialId
     * @return
     */
    public SecurityCredential querySecurityCredentialById(Long securityCredentialId);

    /**
     * 获取所有密匙信息列表
     * 
     * @return
     */
    public List<SecurityCredential> queryAllSecurityCredential();

    /**
     * 分页查询密匙信息列表
     * 
     * @param searchParams
     *            查询条件Map
     * @param pageNumber
     *            页码
     * @param pageSize
     *            页大小
     * @param bsort
     *            排序
     * @return
     */
    YcPage<SecurityCredentialVo> queryPageSecurityCredential(Map<String, Object> searchParams,
                                                             int pageNumber, int pageSize);

    /**
     * 根据条件取开启的密钥信息
     * 
     * @param identityId
     *            用户ID
     * @param identityType
     *            用户类型
     * @param securityPropertyName
     *            密匙类型名称
     * @return
     */
    public SecurityCredential querySecurityCredentialByParams(Long identityId,
                                                              IdentityType identityType,
                                                              Long securityTypeId, String status);

    /**
     * 根据Identity查询密匙信息列表
     * 
     * @param identity
     *            用户
     * @return
     */
    public List<SecurityCredential> querySecurityCredentialByIdentity(AbstractIdentity identity);

    /**
     * 取密钥
     * 
     * @param name
     * @return
     */
    public SecurityCredential getSecurityCredentialByName(String name);

    /**
     * 根据条件查询密匙信息列表
     * 
     * @param status
     *            状态
     * @return
     */
    public List<SecurityCredential> getSecurityCredentialListByStatus(String status);

    /**
     * 根据条件取密匙信息(明文)
     * 
     * @param identityId
     *            用户ID
     * @param identityType
     *            用户类型
     * @param securitytypeid
     *            密匙类型
     * @return
     */
    public SecurityCredential queryProclaimedSecurityByIdentity(Long identityId,
                                                                IdentityType identityType,
                                                                Long securityTypeId);


    /**
     * 根据条件 检查是否 密钥信息存在
     * 
     * @param identityId
     *            用户ID
     * @param identityType
     *            用户类型
     * @param securityPropertyName
     *            密匙类型名称
     * @return
     */
    public boolean checkIsExistSecurity(Long identityId, IdentityType identityType,
                                        Long securityTypeId);

    /**
     * 根据条件取开启的密钥信息
     * 
     * @param identityId
     *            用户ID
     * @param identityType
     *            用户类型
     * @param securityPropertyName
     *            密匙类型名称
     * @return
     */
    public SecurityCredential querySecurityCredentialByParam(Long identityId,
                                                             IdentityType identityType,
                                                             String securityTypeName, String status);

    /**
     * 根据条件取开启的密钥值
     * 
     * @param identityId
     *            用户ID
     * @param identityType
     *            用户类型
     * @param securityPropertyName
     *            密匙类型名称
     * @return
     */
    public String querySecurityCredentialValueByParams(Long identityId, IdentityType identityType,
                                                       String securityTypeName, String status);

    /**
     * 用密钥类型找密钥
     */
    List<SecurityCredential> getSecurityCredentialByType(Long securityTypeId);
}
