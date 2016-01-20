package com.yuecheng.hops.account.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.yuecheng.hops.account.entity.CardAccount;


public interface CardAccountDao extends AccountDao<CardAccount>
{
    // @Modifying
    // @Query(value="update CardAccount c set c.status=:status where c.accountId = :account_id")
    // public int updateCardAccountStatusById(@Param("status") String status,@Param("account_id")
    // Long account_id);
    // update ccy_account c set c.available_balance=c.available_balance-9.9 where c.account_id =
    // 48000

    @Query("select ca from CardAccount ca where 1=1")
    public List<CardAccount> getAllCardAccount();
}
