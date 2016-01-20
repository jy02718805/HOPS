package com.yuecheng.hops.injection.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.injection.entity.InterfacePacketTypeConf;


@Service
public interface InterfacePacketTypeConfDao extends PagingAndSortingRepository<InterfacePacketTypeConf, Long>, JpaSpecificationExecutor<InterfacePacketTypeConf>
{

}
