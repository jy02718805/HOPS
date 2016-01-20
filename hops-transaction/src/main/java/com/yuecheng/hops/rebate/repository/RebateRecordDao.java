package com.yuecheng.hops.rebate.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.yuecheng.hops.rebate.entity.RebateRecord;


public interface RebateRecordDao extends PagingAndSortingRepository<RebateRecord, Long>, JpaSpecificationExecutor<RebateRecord>,RebateRecordDaoSql
{
//    @Query("select rr from RebateRecord rr where rr.merchantId=:merchantId and rr.rebateMerchantId=:rebateMerchantId and rr.rebateDate=:rebateDate and rr.rebateProductId=:rebateProductId")
//    List<RebateRecord> getRebateRecordByParams(@Param("merchantId") Long merchantId,@Param("rebateMerchantId") Long rebateMerchantId,@Param("rebateDate") Date rebateDate,@Param("rebateProductId") String rebateProductId);
//    
//    @Query("select rr from RebateRecord rr where rr.merchantId=:merchantId and rr.rebateMerchantId=:rebateMerchantId and rr.rebateDate=:rebateDate and rr.rebateProductId=:rebateProductId and rr.rebateRuleId=:rebateRuleId")
//    List<RebateRecord> getRebateRecordByParams(@Param("merchantId") Long merchantId,@Param("rebateMerchantId") Long rebateMerchantId,@Param("rebateDate") Date rebateDate,@Param("rebateProductId") String rebateProductId,@Param("rebateRuleId") Long rebateRuleId);
    
    @Query("select rr from RebateRecord rr where rr.merchantId=:merchantId and rr.rebateDate>=:startDate and rr.rebateDate<=:endDate")
    List<RebateRecord> getRebateRecordByParams(@Param("merchantId") Long merchantId,@Param("startDate") Date startDate,@Param("endDate") Date rebateDate);
    
//    @Query("select rr from RebateRecord rr where rr.merchantId=:merchantId and rr.rebateDate=:rebateDate")
//    List<RebateRecord> getRebateRecordByParams(@Param("merchantId") Long merchantId,@Param("rebateDate") Date rebateDate);
    
}
