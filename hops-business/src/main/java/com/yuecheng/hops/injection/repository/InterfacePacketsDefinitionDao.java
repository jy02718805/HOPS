package com.yuecheng.hops.injection.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.injection.entity.InterfacePacketsDefinition;


@Service
public interface InterfacePacketsDefinitionDao extends PagingAndSortingRepository<InterfacePacketsDefinition, Long>, JpaSpecificationExecutor<InterfacePacketsDefinition>
{
    @Query("select ipd from InterfacePacketsDefinition ipd where ipd.merchantId=:merchantId and ipd.interfaceType=:interfaceType and ipd.status=:status")
    public InterfacePacketsDefinition queryInterfacePacketsDefinitionByParams(@Param("merchantId") Long merchantId, @Param("interfaceType") String interfaceType, @Param("status") String status);
    
    @Query("select ipd from InterfacePacketsDefinition ipd where ipd.interfaceType=:interfaceType and ipd.status=:status")
    public InterfacePacketsDefinition queryInterfacePacketsDefinitionByParams(@Param("interfaceType") String interfaceType, @Param("status") String status);
}
