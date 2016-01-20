package com.yuecheng.hops.transaction.basic.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.transaction.basic.entity.OrderApplyOperateHistory;

@Service
public interface OrderApplyOperateHistoryDao extends PagingAndSortingRepository<OrderApplyOperateHistory, Long>, JpaSpecificationExecutor<OrderApplyOperateHistory>
{
    @Query("select o from OrderApplyOperateHistory o where o.orderNo=:orderNo order by createDate desc")
    List<OrderApplyOperateHistory> queryApplyOperateHistoryListByOrderNo(@Param("orderNo")Long orderNo);
}