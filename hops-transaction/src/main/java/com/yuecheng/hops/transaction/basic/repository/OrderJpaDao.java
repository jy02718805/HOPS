package com.yuecheng.hops.transaction.basic.repository;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.transaction.basic.entity.Order;


/**
 * 璁㈠崟鐩稿叧鎿嶄綔鏁版嵁灞�
 * 
 * @author Jinger 2014-03-05
 */
@Service
public interface OrderJpaDao extends PagingAndSortingRepository<Order, Long>, CrudRepository<Order, Long>, JpaSpecificationExecutor<Order>
{
    @Query("select o from Order o where o.orderStatus = :orderStatus and o.preOrderBindTime <= sysdate and o.bindTimes <= o.limitBindTimes and rownum<=50")
    List<Order> findOrdersByParams(@Param("orderStatus")
    Integer orderStatus);

    @Query("select o from Order o where o.orderStatus = :orderStatus and o.preOrderBindTime >= :preOrderBindTime and o.bindTimes <= o.limitBindTimes")
    List<Order> findPartSuccessOrders(@Param("orderStatus")
    Integer orderStatus, @Param("preOrderBindTime")
    Date preOrderBindTime);

    @Query("select o from Order o where o.merchantOrderNo=:merchantOrderNo and o.merchantId = :merchantId")
    Order getOrderByMerchantOrderNo(@Param("merchantId")
    Long merchantId, @Param("merchantOrderNo")
    String merchantOrderNo);

    @Query("select o from Order o where o.preSuccessStatus = :preSuccessStatus and o.orderPreSuccessTime < sysdate and rownum<=50")
    List<Order> findFakeOrdersByParams(@Param("preSuccessStatus")
    Integer orderStatus);

    @Modifying
    @Transactional
    @Query("update Order o set o.orderStatus = :orderStatus,o.bindTimes = :bindTimes where o.orderNo = :orderNo")
    public int updateOrderStatusByOrderNo(@Param("orderStatus")
    Long orderStatus, @Param("bindTimes")
    Long bindTimes, @Param("orderNo")
    Long orderNo);

    @Modifying
    @Transactional
    @Query("update Order o set o.orderStatus = :targetStatus where o.orderNo = :orderNo and o.orderStatus= :originalStatus")
    public int updateOrderStatus(@Param("orderNo")
    Long orderNo, @Param("targetStatus")
    Integer targetStatus, @Param("originalStatus")
    Integer originalStatus);

    @Modifying
    @Transactional
    @Query("update Order o set o.preSuccessStatus = :preSuccessStatus where o.orderNo = :orderNo")
    public int updateOrderPreSuccessStatus(@Param("preSuccessStatus")
    int preSuccessStatus, @Param("orderNo")
    Long orderNo);

    @Modifying
    @Transactional
    @Query("update Order o set o.manualFlag = :manualFlag where o.orderNo = :orderNo")
    public int updateOrderManualFlag(@Param("manualFlag")
    int preSuccessStatus, @Param("orderNo")
    Long orderNo);

    @Modifying
    @Transactional
    @Query("update Order o set o.notifyStatus = :notifyStatus where o.orderNo = :orderNo")
    public int updateOrderNotifyStatus(@Param("notifyStatus")
    int notifyStatus, @Param("orderNo")
    Long orderNo);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Query("update Order o set o.orderStatus = :targetStatus,o.orderSuccessFee = :orderSuccessFee,o.orderFinishTime = sysdate where o.orderNo = :orderNo and o.orderStatus= :originalStatus")
    public int updateOrderSuccess(@Param("orderNo")
    Long orderNo, @Param("targetStatus")
    Integer targetStatus, @Param("originalStatus")
    Integer originalStatus, @Param("orderSuccessFee")
    BigDecimal orderSuccessFee);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Query("update Order o set o.manualFlag = :manualFlag where o.orderNo = :orderNo and o.manualFlag= :originalManualFlag")
    public int updateOrderManualFlag(@Param("orderNo")
    Long orderNo, @Param("manualFlag")
    Integer manualFlag, @Param("originalManualFlag")
    Integer originalManualFlag);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Query("update Order o set o.manualFlag = :manualFlag where o.orderNo = :orderNo")
    public int updateOrderManualFlag(@Param("orderNo")
    Long orderNo, @Param("manualFlag")
    Integer manualFlag);
    
    @Modifying
    @Transactional
    @Query("update Order o set o.preSuccessStatus=:preSuccessStatus,o.orderReason = :orderReason where o.orderNo = :orderNo")
    public int updateOrderReason(@Param("preSuccessStatus")
    Integer preSuccessStatus, @Param("orderReason")
    String orderReason, @Param("orderNo")
    Long orderNo);

}
