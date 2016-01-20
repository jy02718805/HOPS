package com.yuecheng.hops.injection.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.injection.entity.InterfaceParam;


@Service
public interface InterfaceParamDao extends PagingAndSortingRepository<InterfaceParam, Long>, JpaSpecificationExecutor<InterfaceParam>
{

}
