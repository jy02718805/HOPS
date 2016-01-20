package com.yuecheng.hops.transaction.basic.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.transaction.basic.entity.DeliveryQueryStatusTransfer;


/**
 * 发货记录查询状态相关操作数据层
 */
@Service
public interface DeliveryQueryStatusTransferDao extends PagingAndSortingRepository<DeliveryQueryStatusTransfer, Long>, CrudRepository<DeliveryQueryStatusTransfer, Long>, JpaSpecificationExecutor<DeliveryQueryStatusTransfer>
{}
