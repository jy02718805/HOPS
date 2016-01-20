package com.yuecheng.hops.injection.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.injection.entity.UriTransactionMapping;


@Service
public interface UriTransactionMappingDao extends PagingAndSortingRepository<UriTransactionMapping, Long>, JpaSpecificationExecutor<UriTransactionMapping>
{
}
