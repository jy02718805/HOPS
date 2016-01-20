package com.yuecheng.hops.security.repository.impl.jpa;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.yuecheng.hops.security.entity.SecurityCredentialRule;


/**
 * @Title: SecurityRuleDao.java
 * @Package com.yuecheng.hops.identity.repository
 * @Description: 密钥规则 Copyright: Copyright (c) 2011 Company:湖南跃程网络科技有限公司
 * @version V1.0
 * @ClassName: SecurityRuleDao
 * @Description: 密钥规则
 * @author 肖进
 * @date 2014年9月1日 下午2:47:48
 */

public interface SecurityRuleJpaDao extends PagingAndSortingRepository<SecurityCredentialRule, Long>, JpaSpecificationExecutor<SecurityCredentialRule>
{

    @Query(value = "select c from  SecurityCredentialRule c where 1=1 and c.status <> :status ")
    public List<SecurityCredentialRule> getSecurityRuleAll(@Param("status")
    Long status);

    // 通过securityruleid得到SecurityRule对象
    @Query(value = "select c from  SecurityCredentialRule c where c.securityRuleId=:securityRuleId and c.status <> :status  ")
    public SecurityCredentialRule getSecurityRuleById(@Param("securityRuleId")
    Long securityRuleId, @Param("status")
    Long status);
    
    @Query(value = "select sr from  SecurityCredentialRule sr where sr.securityRuleName=:securityRuleName and sr.status !=2  ")
    public SecurityCredentialRule getSecurityRuleByName(@Param("securityRuleName")
    String securityRuleName);
}
