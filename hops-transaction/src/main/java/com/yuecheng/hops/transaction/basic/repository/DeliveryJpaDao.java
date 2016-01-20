package com.yuecheng.hops.transaction.basic.repository;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.transaction.basic.entity.Delivery;


@Service
public interface DeliveryJpaDao extends PagingAndSortingRepository<Delivery, Long>, JpaSpecificationExecutor<Delivery>
{
    @Query("select d from Delivery d where d.orderNo = :orderNo order by deliveryId")
    public List<Delivery> findDeliveryByOrderNo(@Param("orderNo")
    Long orderNo);

    @Modifying
    @Transactional
    @Query("update Delivery d set d.queryFlag=:queryflag,d.nextQueryTime=:queryTime where d.deliveryId=:deliveryId and d.queryFlag=1")
    public int updateDeliveryNextQuery(@Param("deliveryId")
    Long deliveryId, @Param("queryflag")
    Integer queryflag, @Param("queryTime")
    Date queryTime);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Query("update Delivery d set d.deliveryStatus = :targetStatus where d.deliveryId = :deliveryId and d.deliveryStatus= :originalStatus")
    public int updateDeliveryStatus(@Param("deliveryId")
    Long deliveryId, @Param("targetStatus")
    Integer targetStatus, @Param("originalStatus")
    Integer originalStatus);

    @Modifying
    @Transactional
    @Query("update Delivery d set d.queryFlag = :targetStatus where d.deliveryId = :deliveryId and d.queryFlag= :originalStatus")
    public int updateQueryFlag(@Param("deliveryId")
    Long deliveryId, @Param("targetStatus")
    Integer targetStatus, @Param("originalStatus")
    Integer originalStatus);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Query("update Delivery d set d.deliveryStatus = :deliveryStatus,d.deliveryFinishTime= sysdate,d.deliveryResult= :deliveryResult,d.successFee= :successFee,d.queryMsg = :queryMsg where d.deliveryId = :deliveryId and d.deliveryStatus= :oldDeliveryStatus")
    public int updateDeliveryStatus(@Param("deliveryStatus")
    Integer deliveryStatus, @Param("deliveryResult")
    String deliveryResult, @Param("successFee")
    BigDecimal successFee, @Param("queryMsg")
    String queryMsg, @Param("deliveryId")
    Long deliveryId, @Param("oldDeliveryStatus")
    Integer oldDeliveryStatus);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Query("update Delivery d set d.queryFlag = :queryFlag where d.deliveryId = :deliveryId and d.queryFlag= :originalQueryFlag")
    public int updateQueryFlag(@Param("queryFlag")
    Integer queryFlag, @Param("deliveryId")
    Long deliveryId, @Param("originalQueryFlag")
    Integer originalQueryFlag);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Query("update Delivery d set d.queryFlag = :queryFlag,d.nextQueryTime = :nextQueryTime,d.deliveryResult = :deliveryResult where d.deliveryId = :deliveryId and d.queryFlag= :originalQueryFlag")
    public int updateQueryFlag(@Param("queryFlag")
    Integer queryFlag, @Param("deliveryId")
    Long deliveryId, @Param("originalQueryFlag")
    Integer originalQueryFlag, @Param("nextQueryTime")
    Date nextQueryTime, @Param("deliveryResult")
    String deliveryResult);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Query("update Delivery d set d.queryFlag = :queryFlag, d.deliveryStatus = :deliveryStatus where d.deliveryId = :deliveryId")
    public int closeDelivery(@Param("queryFlag")
    Integer queryFlag, @Param("deliveryStatus")
    Integer deliveryStatus, @Param("deliveryId")
    Long deliveryId);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Query("update Delivery d set d.supplyMerchantOrderNo = :supplyMerchantOrderNo where d.deliveryId = :deliveryId")
    public int updateSupplyMerchantOrderNo(@Param("supplyMerchantOrderNo")
    String supplyMerchantOrderNo, @Param("deliveryId")
    Long deliveryId);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Query("update Delivery d set d.supplyMerchantOrderNo = :supplyMerchantOrderNo,d.deliveryResult = :deliveryResult,d.queryMsg = :queryMsg where d.deliveryId = :deliveryId")
    public int updateParams(@Param("supplyMerchantOrderNo")
    String supplyMerchantOrderNo, @Param("deliveryResult")
    String deliveryResult, @Param("deliveryId")
    Long deliveryId, @Param("queryMsg")
    String queryMsg);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Query("update Delivery d set d.queryTimes = :queryTimes where d.deliveryId = :deliveryId")
    public int addQueryTimes(@Param("queryTimes")
    Long queryTimes, @Param("deliveryId")
    Long deliveryId);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Query("update Delivery d set d.queryMsg = :queryMsg where d.deliveryId = :deliveryId")
    public int updateQueryMsg(@Param("deliveryId")
    Long deliveryId, @Param("queryMsg")
    String queryMsg);

    @Modifying
    @Transactional
    @Query("update Delivery d set d.queryFlag = :queryFlag,d.nextQueryTime=sysdate where d.deliveryId = :deliveryId")
    public int updateQueryFlag(@Param("deliveryId")
    Long deliveryId, @Param("queryFlag")
    Integer queryFlag);

}
