package com.yuecheng.hops.account.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.account.entity.CurrencyAccountBalanceHistory1;


@Service
public interface CurrencyAccountBalanceHistory1JpaDao extends PagingAndSortingRepository<CurrencyAccountBalanceHistory1, Long>, JpaSpecificationExecutor<CurrencyAccountBalanceHistory1>
{

}
