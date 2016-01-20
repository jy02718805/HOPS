package com.yuecheng.hops.injection.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.yuecheng.hops.injection.entity.InterfaceSendtimesConf;


public interface InterfaceSendtimesConfDao extends PagingAndSortingRepository<InterfaceSendtimesConf, Long>, JpaSpecificationExecutor<InterfaceSendtimesConf>
{
    @Query("select isc from InterfaceSendtimesConf isc")
    public List<InterfaceSendtimesConf> queryInterfaceSendtimesConfList();
}
