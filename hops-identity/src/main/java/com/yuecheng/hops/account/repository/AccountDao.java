package com.yuecheng.hops.account.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.yuecheng.hops.account.entity.Account;


public interface AccountDao<T extends Account> extends PagingAndSortingRepository<T, Long>, JpaSpecificationExecutor<T>
{

}
