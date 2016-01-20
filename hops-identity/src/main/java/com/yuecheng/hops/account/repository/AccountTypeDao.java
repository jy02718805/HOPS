package com.yuecheng.hops.account.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.yuecheng.hops.account.entity.AccountType;


public interface AccountTypeDao extends PagingAndSortingRepository<AccountType, Long>, JpaSpecificationExecutor<AccountType>
{
    @Query(value = "select t from AccountType t where t.accountTypeName = :accountTypeName and t.scope=:scope and t.directory=:directory")
    public List<AccountType> queryAccountTypeByName(@Param("accountTypeName")
    String accountTypeName, @Param("scope")
    String scope, @Param("directory")
    String directory);

    @Query(value = "select t from AccountType t where t.accountTypeId = :accountTypeId ")
    public List<AccountType> queryAccountTypeByNameByAccountTypeId(@Param("accountTypeId")
    Long accountTypeId);
}
