package com.yuecheng.hops.account.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.yuecheng.hops.account.entity.CurrencyAccountAddCashRecord;


public interface CurrencyAccountAddCashRecordJpaDao extends PagingAndSortingRepository<CurrencyAccountAddCashRecord, Long>, JpaSpecificationExecutor<CurrencyAccountAddCashRecord>
{

}
