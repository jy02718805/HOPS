package com.yuecheng.hops.security.service;


import java.util.List;

import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.security.entity.SecurityCredentialRule;


/**
 * @ClassName: SecurityRuleService
 * @Description: TODO
 * @author 肖进
 * @date 2014年9月1日 下午3:23:45
 */

public interface SecurityRuleService
{
    /**
     * 按条件分页查询：
     * 
     * @param securityrulename
     * @param letter
     * @param figure
     * @param specialcharacter
     * @param status
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public YcPage<SecurityCredentialRule> queryPageSecurityRule(String securityrulename,
                                                                String letter, String figure,
                                                                String specialcharacter,
                                                                Long status, int pageNumber,
                                                                int pageSize);

    /**
     * 添加规则：
     * 
     * @param securityRule
     * @return
     */
    public SecurityCredentialRule addSecurityRule(SecurityCredentialRule securityRule);

    /**
     * 编辑规则：
     * 
     * @param securityRule
     * @return
     */
    public SecurityCredentialRule editSecurityRule(SecurityCredentialRule securityRule);

    /**
     * 删除规则：
     * 
     * @param securityruleid
     */
    public void delSecurityRule(Long securityruleid);

    /**
     * 根据ID查询规则：
     * 
     * @param securityruleid
     * @return
     */
    public SecurityCredentialRule querySecurityRuleById(Long securityruleid);

    /**
     * 根据ID和状态进行状态修改：
     * 
     * @param securityruleid
     * @param status
     * @return
     */
    public SecurityCredentialRule updateSecurityRuleStatus(Long securityruleid, Long status);

    /**
     * 得到除删除状态的全部记录
     * 
     * @return
     */
    public List<SecurityCredentialRule> queryAllDelStatusSecurityRule();

    /**
     * 取密钥规则
     * 
     * @param name
     * @return
     */
    public SecurityCredentialRule getSecurityRuleByName(String name);
}
