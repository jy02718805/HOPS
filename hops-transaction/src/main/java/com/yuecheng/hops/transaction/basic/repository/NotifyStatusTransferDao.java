package com.yuecheng.hops.transaction.basic.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.transaction.basic.entity.NotifyStatusTransfer;


/**
 * 通知记录状态相关操作数据层
 */
@Service
public interface NotifyStatusTransferDao extends PagingAndSortingRepository<NotifyStatusTransfer, Long>, CrudRepository<NotifyStatusTransfer, Long>, JpaSpecificationExecutor<NotifyStatusTransfer>
{}
