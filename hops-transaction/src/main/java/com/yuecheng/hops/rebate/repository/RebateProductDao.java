package com.yuecheng.hops.rebate.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.yuecheng.hops.rebate.entity.RebateProduct;


public interface RebateProductDao extends PagingAndSortingRepository<RebateProduct, Long>, JpaSpecificationExecutor<RebateProduct>
{
    @Query("select rp from RebateProduct rp where rp.merchantId=:merchantId ")
    List<RebateProduct> getRebateProductByMerchantId(@Param("merchantId") Long merchantId);
    
    @Query("select rp from RebateProduct rp where rp.rebateProductId=:rebateProductId ")
    List<RebateProduct> getRebateProductByRProductId(@Param("rebateProductId") String rebateProductId);
}
