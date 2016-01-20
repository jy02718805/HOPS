package com.yuecheng.hops.injection.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.injection.entity.MerchantResponse;


@Service
public interface MerchantResponseDao extends PagingAndSortingRepository<MerchantResponse, Long>, JpaSpecificationExecutor<MerchantResponse>
{
    @Query("select mr from MerchantResponse mr")
    public List<MerchantResponse> queryMerchantResponseList();
}
