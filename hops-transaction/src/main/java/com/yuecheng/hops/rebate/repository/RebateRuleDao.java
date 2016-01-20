package com.yuecheng.hops.rebate.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.yuecheng.hops.rebate.entity.RebateRule;


public interface RebateRuleDao extends PagingAndSortingRepository<RebateRule, Long>, JpaSpecificationExecutor<RebateRule>,RebateRuleDaoSql
{
    @Query("select rr from RebateRule rr where rr.status <> 2")
    List<RebateRule> selectAll();

    @Query("select rr from RebateRule rr where rr.merchantId=:merchantId and rr.status <> 2")
    List<RebateRule> getRebateRuleByMerchantId(@Param("merchantId")
    Long merchantId);

    @Query("select rr from RebateRule rr where (rr.merchantId=:merchantId or rr.rebateMerchantId=:merchantId) and rr.status <> 2")
    List<RebateRule> getRebateRuleByMerchantIdLike(@Param("merchantId")
    Long merchantId);

    @Query("select rr from RebateRule rr where rr.merchantId=:merchantId and rr.status=:status and rr.status <> 2")
    List<RebateRule> getRebateRuleByParams(@Param("merchantId")
    Long merchantId, @Param("status")
    String status);

    @Query("select rr from RebateRule rr where rr.merchantId=:merchantId and rr.rebateMerchantId=:rebateMerchantId and rr.status <> 2")
    List<RebateRule> getRebateRuleByMerchantId(@Param("merchantId")
    Long merchantId, @Param("rebateMerchantId")
    Long rebateMerchantId);

    @Query("select rr from RebateRule rr where rr.rebateProductId=:rebateProductId and rr.status <> 2")
    List<RebateRule> getRebateRule(@Param("rebateProductId")
    String rebateProductId);
    
    @Query("select rr from RebateRule rr where rr.rebateProductId=:rebateProductId and rr.rebateTimeType=:rebateTimeType and rr.status <> 2")
    List<RebateRule> getRebateRule(@Param("rebateProductId") String rebateProductId,@Param("rebateTimeType") Long rebateTimeType);

    @Query("select rr from RebateRule rr where rr.merchantId=:merchantId and rr.status <> 2")
    List<RebateRule> getRebateRuleByParams(@Param("merchantId")
    Long merchantId);

    // @Query("select rr from RebateRule rr where rr.merchantId=:merchantId and rr.productId=:productId and rr.tradingVolumeLow <= :transactionNum and rr.tradingVolumeHigh > :transactionNum")
    // List<RebateRule> getRebateRule(@Param("merchantId")Long merchantId,@Param("productId")Long
    // productId,@Param("transactionNum")Long transactionNum);
}
