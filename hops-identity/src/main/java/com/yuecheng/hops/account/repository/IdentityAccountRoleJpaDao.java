package com.yuecheng.hops.account.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.yuecheng.hops.account.entity.role.IdentityAccountRole;


public interface IdentityAccountRoleJpaDao extends PagingAndSortingRepository<IdentityAccountRole, Long>, JpaSpecificationExecutor<IdentityAccountRole>
{

    @Query(value = "select t from IdentityAccountRole t where t.accountId = :accountId and t.relation = :relation")
    public IdentityAccountRole queryIdentityAccountRoleByParames(@Param("accountId")
    Long accountId, @Param("relation")
    String relation);

    @Query(value = "select t from IdentityAccountRole t where t.identityId = :identityId and t.identityType = :identityType")
    public List<IdentityAccountRole> queryIdentityAccountRoleByIdentity(@Param("identityId")
    Long identityId, @Param("identityType")
    String identityType);

    @Query(value = "select t from IdentityAccountRole t where t.accountId = :accountId")
    public List<IdentityAccountRole> queryIdentityAccountRoles(@Param("accountId")
    Long accountId);

    @Query(value = "select t from IdentityAccountRole t where t.identityId = :identityId and t.identityType = :identityType and t.relation = :relation")
    public List<IdentityAccountRole> queryIdentityAccountRoleByParames(
        @Param("identityId")Long identityId, 
        @Param("identityType")String identityType, 
        @Param("relation")String relation);
    
    @Query(value = "select t from IdentityAccountRole t where t.identityId = :identityId and t.identityType = :identityType and t.accountType = :accountType and t.relation = :relation and t.tableName=:tableName")
    public IdentityAccountRole queryIdentityAccountRoleByParames(
        @Param("identityId")Long identityId, 
        @Param("identityType")String identityType, 
        @Param("accountType")Long accountTypeId, 
        @Param("relation")String relation,
        @Param("tableName")String tableName);
}
