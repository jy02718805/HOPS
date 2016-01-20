package com.yuecheng.hops.transaction.basic.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.transaction.basic.entity.OrderStatusTransfer;


/**
 * 订单状态相关操作数据层
 */
@Service
public interface OrderStatusTransferDao extends PagingAndSortingRepository<OrderStatusTransfer, Long>, CrudRepository<OrderStatusTransfer, Long>, JpaSpecificationExecutor<OrderStatusTransfer>
{}
