package com.yuecheng.hops.security.service;


import java.util.List;

import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.security.entity.SecurityCredentialType;


/**
 * @ClassName: SecurityTypeService
 * @Description: TODO
 * @author 肖进
 * @date 2014年9月1日 下午3:23:45
 */

public interface SecurityTypeService
{
    /**
     * 按条件分页查询：
     * 
     * @param securitytypename
     * @param modeltype
     * @param encrypttype
     * @param securityruleid
     * @param status
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public YcPage<SecurityCredentialType> queryPageSecurityType(String securitytypename,
                                                                String modeltype,
                                                                String encrypttype,
                                                                Long securityruleid, Long status,
                                                                int pageNumber, int pageSize);

    /**
     * 添加密钥类型：
     * 
     * @param securityType
     * @return
     */
    public SecurityCredentialType addSecurityType(SecurityCredentialType securityType);

    /**
     * 编辑密钥类型：
     * 
     * @param securityType
     * @return
     */
    public SecurityCredentialType editSecurityType(SecurityCredentialType securityType);

    /**
     * 删除密钥类型：
     * 
     * @param securityTypeId
     */
    public void delSecurityType(Long securityTypeId);

    /**
     * 根据ID查询密钥类型：
     * 
     * @param securityTypeId
     * @return
     */
    public SecurityCredentialType getSecurityType(Long securityTypeId);

    /**
     * 根据ID和状态进行状态修改：
     * 
     * @param securityTypeId
     * @param status
     * @return
     */
    public SecurityCredentialType updateSecurityTypeStatus(Long securityTypeId, Long status);

    /**
     * 得到除删除状态外全部记录
     * 
     * @return
     */
    public List<SecurityCredentialType> querySecurityTypeAll();

    /**
     * 得到状态下的类型
     * 
     * @param status
     * @return
     */
    public List<SecurityCredentialType> querySecurityTypeBystatus(Long status);

    /**
     * 根据类型名称找密钥类型
     * 
     * @param typeName
     * @return
     */
    public SecurityCredentialType querySecurityTypeByTypeName(String typeName);

    /**
     * 根据密钥规则找密钥类型
     * 
     * @param typeName
     * @return
     */
    public List<SecurityCredentialType> querySecurityTypeByRule(Long securityRuleId);

}
