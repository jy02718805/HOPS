package com.yuecheng.hops.injection.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.injection.entity.MerchantRequest;


@Service
public interface MerchantRequestDao extends PagingAndSortingRepository<MerchantRequest, Long>, JpaSpecificationExecutor<MerchantRequest>
{

}
