package com.yuecheng.hops.account.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.yuecheng.hops.account.entity.TransactionHistory;


public interface TransactionHistoryJpaDao extends PagingAndSortingRepository<TransactionHistory, Long>, JpaSpecificationExecutor<TransactionHistory>
{

}
