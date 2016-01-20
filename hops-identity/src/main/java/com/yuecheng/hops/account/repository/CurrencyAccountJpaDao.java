package com.yuecheng.hops.account.repository;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.account.entity.CurrencyAccount;


@Service
public interface CurrencyAccountJpaDao extends AccountDao<CurrencyAccount>
{
    @Modifying
    @Transactional
    @Query("update CurrencyAccount c set c.availableBalance = c.availableBalance + :amt where c.accountId = :accountId")
    public int addAvailableBalanceByAccountId(@Param("accountId")Long accountId, @Param("amt")BigDecimal amt);
    
    @Modifying
    @Transactional
    @Query("update CurrencyAccount c set c.availableBalance = c.availableBalance - :amt where c.accountId = :accountId and :amt < c.availableBalance + c.creditableBanlance")
    public int subAvailableBalanceByAccountId(@Param("accountId")Long accountId, @Param("amt")BigDecimal amt);

    @Modifying
    @Transactional
    @Query("update CurrencyAccount c set c.creditableBanlance = c.creditableBanlance + :amt where c.accountId = :accountId")
    public int addCreditableBalanceByAccountId(@Param("accountId")Long accountId, @Param("amt")BigDecimal amt);
    
    @Modifying
    @Transactional
    @Query("update CurrencyAccount c set c.creditableBanlance = c.creditableBanlance - :amt where c.accountId = :accountId and c.availableBalance + c.creditableBanlance - :amt >= 0")
    public int subCreditableBalanceByAccountId(@Param("accountId")Long accountId, @Param("amt")BigDecimal amt);
    
    @Modifying
    @Transactional
    @Query("update CurrencyAccount c set c.availableBalance = c.availableBalance - :amt,c.unavailableBanlance = c.unavailableBanlance + :amt where c.accountId = :accountId and c.availableBalance + c.creditableBanlance >= :amt ")
    public int frozenBalanceByAccountId(@Param("accountId")Long accountId, @Param("amt")BigDecimal amt);
    
    @Modifying
    @Transactional
    @Query("update CurrencyAccount c set c.availableBalance = c.availableBalance + :amt,c.unavailableBanlance = c.unavailableBanlance - :amt where c.accountId = :accountId and c.unavailableBanlance < :amt ")
    public int unFrozenBalanceByAccountId(@Param("accountId")Long accountId, @Param("amt")BigDecimal amt);
    
//    @Modifying
//    @Transactional
//    @Query("update CurrencyAccount c set c.availableBalance = :availableBalance,c.unavailableBanlance = :unavailableBanlance where c.accountId = :accountId")
//    public int updateBalanceByAccountId(@Param("accountId")
//    Long accountId, @Param("availableBalance")
//    BigDecimal availableBalance, @Param("unavailableBanlance")
//    BigDecimal unavailableBanlance);
//
//    @Modifying
//    @Transactional
//    @Query("update CurrencyAccount c set c.creditableBanlance = :creditableBanlance where c.accountId = :accountId")
//    public int updateCreditableBanlanceByAccountId(@Param("accountId")
//    Long accountId, @Param("creditableBanlance")
//    BigDecimal creditableBanlance);

    @Query("select ca from CurrencyAccount ca,IdentityAccountRole iar where  ca.accountType.accountTypeId = :accountTypeId"
           + " and  iar.accountId = ca.accountId "
           + " and  iar.identityId = :identityId "
           + " and  iar.identityType=:identityType" + " and  iar.relation = :relation")
    public CurrencyAccount getAccountByParams(@Param("accountTypeId")
    Long accountTypeId, @Param("identityId")
    Long identityId, @Param("identityType")
    String identityType, @Param("relation")
    String relation);

    @Query("select ca from CurrencyAccount ca where 1=1")
    public List<CurrencyAccount> getAllCurrencyAccount();

    @Query("select ccya from Merchant m,IdentityAccountRole iar,CurrencyAccount ccya "
           + "where iar.identityId = m.id and ccya.accountId = iar.accountId and m.merchantName = :merchantName or m.id= :id")
    public List<CurrencyAccount> selectAccountInfoByIdentity(@Param("merchantName")
    String merchantName, @Param("id")
    Long id);

}
