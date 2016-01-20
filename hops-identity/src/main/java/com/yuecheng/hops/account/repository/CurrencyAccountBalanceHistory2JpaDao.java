package com.yuecheng.hops.account.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.account.entity.CurrencyAccountBalanceHistory2;


@Service
public interface CurrencyAccountBalanceHistory2JpaDao extends PagingAndSortingRepository<CurrencyAccountBalanceHistory2, Long>, JpaSpecificationExecutor<CurrencyAccountBalanceHistory2>
{

}
