package com.yuecheng.hops.security.repository.impl.jpa;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.security.entity.SecurityCredential;


/**
 * 密匙表数据访问层
 * 
 * @author：Jinger
 * @date：2013-09-26
 */

public interface SecurityCredentialJpaDao extends PagingAndSortingRepository<SecurityCredential, Long>, JpaSpecificationExecutor<SecurityCredential>
{
    @Query("select sc from SecurityCredential sc")
    public List<SecurityCredential> selectAll();

    @Query("select sc from SecurityCredential sc where sc.identityId=:identityId and sc.identityType=:identityType and sc.securityName=:securityName and sc.status='"
           + Constant.SecurityCredentialStatus.ENABLE_STATUS + "'")
    public SecurityCredential getSecurityCredentialBySecurityTypeName(@Param("identityId")
    Long identityId, @Param("identityType")
    IdentityType identityType, @Param("securityName")
    String securityName);

    @Query("select sc from SecurityCredential sc where sc.identityId=:identityId and sc.identityType=:identityType and sc.securityType.securityTypeId = :securityTypeId and sc.status!='"
           + Constant.SecurityCredentialStatus.DELETE_STATUS + "'")
    public SecurityCredential queryIdentitySecurityCredential(@Param("identityId")
    Long identityId, @Param("identityType")
    IdentityType identityType, @Param("securityTypeId")
    Long securityTypeId);

    @Query("select sc from SecurityCredential sc where sc.identityId=:identityId and sc.identityType=:identityType and sc.securityType.securityTypeId = :securityTypeId and sc.status=:status")
    public SecurityCredential queryIdentitySecurityCredential(@Param("identityId")
    Long identityId, @Param("identityType")
    IdentityType identityType, @Param("securityTypeId")
    Long securityTypeId, @Param("status")
    String status);

    @Query("select sc from SecurityCredential sc where sc.securityName=:securityName and sc.status=:status")
    public SecurityCredential getSecurityCredentialByName(@Param("securityName")
    String securityName, @Param("status")
    String status);

    @Modifying
    @Transactional
    @Query("update SecurityCredential s set s.status = :status  where s.id = :id")
    public int updateSecurityCredentialStatus(@Param("status")
    String status, @Param("id")
    Long id);

    @Query("select sc from SecurityCredential sc where sc.status=:status")
    public List<SecurityCredential> getSecurityCredentialByStatus(@Param("status")
    String status);

    @Query("select sc from SecurityCredential sc where sc.identityId=:identityId and sc.identityType=:identityType and sc.securityType.securityTypeId = :securityTypeId and sc.status !='"
           + Constant.SecurityCredentialStatus.DELETE_STATUS + "'")
    public SecurityCredential getExistSecurityCredential(@Param("identityId")
    Long identityId, @Param("identityType")
    IdentityType identityType, @Param("securityTypeId")
    Long securityTypeId);

    @Query("select sc from SecurityCredential sc where sc.securityType.securityTypeId = :securityTypeId and sc.status !='"
           + Constant.SecurityCredentialStatus.DELETE_STATUS + "'")
    public List<SecurityCredential> getSecurityCredentialByType(@Param("securityTypeId")
    Long securityTypeId);
}
