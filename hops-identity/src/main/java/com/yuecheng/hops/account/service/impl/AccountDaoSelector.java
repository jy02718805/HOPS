/*
 * 文件名：AccountTypeSeletor.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2014年12月25日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.account.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.account.entity.AccountType;
import com.yuecheng.hops.account.repository.AccountDao;
import com.yuecheng.hops.account.repository.CurrencyAccountJpaDao;
import com.yuecheng.hops.account.repository.MerchantCreditAccountJpaDao;
import com.yuecheng.hops.account.repository.MerchantDebitAccountJpaDao;
import com.yuecheng.hops.account.repository.SystemDebitAccount0JpaDao;
import com.yuecheng.hops.account.repository.SystemDebitAccount1JpaDao;
import com.yuecheng.hops.account.repository.SystemDebitAccount2JpaDao;
import com.yuecheng.hops.account.repository.SystemDebitAccount3JpaDao;
import com.yuecheng.hops.account.repository.SystemDebitAccount4JpaDao;
import com.yuecheng.hops.account.repository.SystemDebitAccount5JpaDao;
import com.yuecheng.hops.account.repository.SystemDebitAccount6JpaDao;
import com.yuecheng.hops.account.repository.SystemDebitAccount7JpaDao;
import com.yuecheng.hops.account.repository.SystemDebitAccount8JpaDao;
import com.yuecheng.hops.account.repository.SystemDebitAccount9JpaDao;
import com.yuecheng.hops.account.service.AccountTypeService;
import com.yuecheng.hops.common.utils.StringUtil;

@Service("accountDaoSeletor")
public class AccountDaoSelector
{
    @Autowired
    private CurrencyAccountJpaDao currencyAccountDao;
    
    @Autowired
    private SystemDebitAccount0JpaDao systemDebitAccount0JpaDao;
    
    @Autowired
    private SystemDebitAccount1JpaDao systemDebitAccount1JpaDao;
    
    @Autowired
    private SystemDebitAccount2JpaDao systemDebitAccount2JpaDao;
    
    @Autowired
    private SystemDebitAccount3JpaDao systemDebitAccount3JpaDao;
    
    @Autowired
    private SystemDebitAccount4JpaDao systemDebitAccount4JpaDao;
    
    @Autowired
    private SystemDebitAccount5JpaDao systemDebitAccount5JpaDao;
    
    @Autowired
    private SystemDebitAccount6JpaDao systemDebitAccount6JpaDao;
    
    @Autowired
    private SystemDebitAccount7JpaDao systemDebitAccount7JpaDao;
    
    @Autowired
    private SystemDebitAccount8JpaDao systemDebitAccount8JpaDao;
    
    @Autowired
    private SystemDebitAccount9JpaDao systemDebitAccount9JpaDao;
    
    @Autowired
    private MerchantCreditAccountJpaDao merchantCreditAccountJpaDao;
    
    @Autowired
    private MerchantDebitAccountJpaDao merchantDebitAccountJpaDao;
    
    @Autowired
    private AccountTypeService accountTypeService;
    
    private static Map<String,AccountDao> accountDaos = null;
    
    public void init()
    {
        accountDaos = new HashMap<String, AccountDao>();
        accountDaos.put("ccy_account", currencyAccountDao);
        accountDaos.put("merchant_debit_account", merchantDebitAccountJpaDao);
        accountDaos.put("merchant_credit_account", merchantCreditAccountJpaDao);
        
        accountDaos.put("system_debit_account0", systemDebitAccount0JpaDao);
        accountDaos.put("system_debit_account1", systemDebitAccount1JpaDao);
        accountDaos.put("system_debit_account2", systemDebitAccount2JpaDao);
        accountDaos.put("system_debit_account3", systemDebitAccount3JpaDao);
        accountDaos.put("system_debit_account4", systemDebitAccount4JpaDao);
        accountDaos.put("system_debit_account5", systemDebitAccount5JpaDao);
        accountDaos.put("system_debit_account6", systemDebitAccount6JpaDao);
        accountDaos.put("system_debit_account7", systemDebitAccount7JpaDao);
        accountDaos.put("system_debit_account8", systemDebitAccount8JpaDao);
        accountDaos.put("system_debit_account9", systemDebitAccount9JpaDao);
    }
    
    public AccountDao selectDao(AccountType accountType,Long transactionNo)
    {
        AccountDao accountDao = null;
        String beanName = getBeanName(accountType, transactionNo);
        accountDao = accountDaos.get(beanName);
        return accountDao;
    }
    
    public String getBeanName(AccountType accountType,Long transactionNo)
    {
        String beanName = StringUtil.initString();
        beanName = accountTypeService.chooseTable(accountType.getAccountTypeId(), transactionNo);
        return beanName;
    }
}
