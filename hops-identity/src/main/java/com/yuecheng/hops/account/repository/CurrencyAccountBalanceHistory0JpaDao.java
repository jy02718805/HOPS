package com.yuecheng.hops.account.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.account.entity.CurrencyAccountBalanceHistory0;


@Service
public interface CurrencyAccountBalanceHistory0JpaDao extends PagingAndSortingRepository<CurrencyAccountBalanceHistory0, Long>, JpaSpecificationExecutor<CurrencyAccountBalanceHistory0>
{

}
