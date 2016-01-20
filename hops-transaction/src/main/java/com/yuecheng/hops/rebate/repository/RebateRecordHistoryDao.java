package com.yuecheng.hops.rebate.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.yuecheng.hops.rebate.entity.RebateRecordHistory;


public interface RebateRecordHistoryDao extends PagingAndSortingRepository<RebateRecordHistory, Long>, JpaSpecificationExecutor<RebateRecordHistory>
{

}
