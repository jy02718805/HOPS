package com.yuecheng.hops.account.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.yuecheng.hops.account.entity.AccountOperationHistory;


public interface AccountOperationHistoryDao extends PagingAndSortingRepository<AccountOperationHistory, Long>, JpaSpecificationExecutor<AccountOperationHistory>
{

}
