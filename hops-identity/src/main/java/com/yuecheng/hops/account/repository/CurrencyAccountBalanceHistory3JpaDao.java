package com.yuecheng.hops.account.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.account.entity.CurrencyAccountBalanceHistory3;


@Service
public interface CurrencyAccountBalanceHistory3JpaDao extends PagingAndSortingRepository<CurrencyAccountBalanceHistory3, Long>, JpaSpecificationExecutor<CurrencyAccountBalanceHistory3>
{

}
