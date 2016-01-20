package com.yuecheng.hops.product.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.product.entity.history.ProductDiscountHistory;


@Service
public interface ProductDiscountHistoryDao extends PagingAndSortingRepository<ProductDiscountHistory, Long>, JpaSpecificationExecutor<ProductDiscountHistory>
{
//    @Query("select dpr from AgentProductRelation dpr where dpr.identityId=:identityId and dpr.identityType=:identityType and dpr.status=:status")
//    public List<AgentProductRelation> getAllProductByMerchant(@Param("identityId") Long identityId,
//                                                              @Param("identityType") String identityType,
//                                                              @Param("status") String status);
}
