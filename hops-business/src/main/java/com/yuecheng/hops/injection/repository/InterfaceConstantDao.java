package com.yuecheng.hops.injection.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.yuecheng.hops.injection.entity.InterfaceConstant;


public interface InterfaceConstantDao extends PagingAndSortingRepository<InterfaceConstant, Long>, JpaSpecificationExecutor<InterfaceConstant>
{
    @Query("select ic from InterfaceConstant ic")
    public List<InterfaceConstant> queryInterfaceConstantList();

    @Query("select ic from InterfaceConstant ic where ic.identityId=:identityId")
    public List<InterfaceConstant> queryInterfaceConstantList(@Param("identityId") Long identityId);
    
    @Query("select ic from InterfaceConstant ic where ic.identityId=:identityId and ic.identityType=:identityType and ic.key=:key")
    public InterfaceConstant queryInterfaceConstantByParams(@Param("identityId") Long identityId,@Param("identityType") String identityType,@Param("key") String key);
}
