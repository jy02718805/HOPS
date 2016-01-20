package com.yuecheng.hops.security.repository.impl.jpa;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.yuecheng.hops.security.entity.SecurityCredentialType;


/**
 * @Title: SecurityTypeJpaDao.java
 * @Package com.yuecheng.hops.identity.repository
 * @Description: 密钥规则 Copyright: Copyright (c) 2011 Company:湖南跃程网络科技有限公司
 * @version V1.0
 * @ClassName: SecurityTypeJpaDao
 * @Description: 密钥规则
 * @author 肖进
 * @date 2014年9月1日 下午2:47:48
 */

public interface SecurityTypeJpaDao extends PagingAndSortingRepository<SecurityCredentialType, Long>, JpaSpecificationExecutor<SecurityCredentialType>
{

    // 2为删除状态
    @Query(value = "select c from  SecurityCredentialType c where 1=1 and c.status <> 2 ")
    public List<SecurityCredentialType> getSecurityTypeAll();

    // 通过securityruleid得到SecurityType对象，2为删除状态
    @Query(value = "select c from  SecurityCredentialType c where c.securityTypeId=:securityTypeId and c.status <> 2  ")
    public SecurityCredentialType getSecurityTypeById(@Param("securityTypeId")
    Long securityTypeId);

    @Query(value = "select c from  SecurityCredentialType c where 1=1 and c.status=:status")
    public List<SecurityCredentialType> getSecurityTypeBystatus(@Param("status")
    Long status);

    @Query(value = "select st from  SecurityCredentialType st where st.securityTypeName=:securityTypeName and st.status !=2  ")
    public SecurityCredentialType getSecurityTypeByTypeName(@Param("securityTypeName")
    String securityTypeName);

    @Query(value = "select st from  SecurityCredentialType st where st.securityTypeName=:securityTypeName and st.status !=2  ")
    public SecurityCredentialType querySecurityTypeByRule(@Param("securityTypeName")
    String securityTypeName);

    @Query(value = "select st from  SecurityCredentialType st where st.securityRule.securityRuleId=:securityRuleId and st.status !=2  ")
    public List<SecurityCredentialType> querySecurityTypeByRule(@Param("securityRuleId")
    Long securityRuleId);
}
