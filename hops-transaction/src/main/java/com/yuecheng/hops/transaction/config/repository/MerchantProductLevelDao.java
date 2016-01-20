package com.yuecheng.hops.transaction.config.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.transaction.config.entify.product.MerchantProductLevel;


@Service
public interface MerchantProductLevelDao extends PagingAndSortingRepository<MerchantProductLevel, Long>, JpaSpecificationExecutor<MerchantProductLevel>
{
    @Query("select ml from MerchantProductLevel ml where ml.merchantLevel=:merchantLevel")
    public MerchantProductLevel queryMerchantProductLevelList(@Param("merchantLevel")
    Long merchantLevel);
}
