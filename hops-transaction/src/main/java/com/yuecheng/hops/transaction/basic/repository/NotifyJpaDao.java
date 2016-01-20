package com.yuecheng.hops.transaction.basic.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.transaction.basic.entity.Notify;


@Service
public interface NotifyJpaDao extends PagingAndSortingRepository<Notify, Long>, JpaSpecificationExecutor<Notify>
{
    @Modifying
    @Transactional
    @Query("update Notify n set n.notifyStatus = :targetStatus where n.notifyId = :notifyId and n.notifyStatus= :originalStatus")
    public int updateNotifyStatus(@Param("notifyId")Long deliveryId, @Param("targetStatus")Integer targetStatus, @Param("originalStatus")Integer originalStatus);
    
    @Modifying
    @Transactional
    @Query("update Notify n set n.notifyStatus= :targetStatus,n.notifyCntr=0,n.startTime=sysdate where n.orderNo=:orderNo")
    public int updateReNotify(@Param("orderNo")Long orderNo, @Param("targetStatus")Integer targetStatus);
}
