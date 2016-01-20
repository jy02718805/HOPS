package com.yuecheng.hops.account.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuecheng.hops.account.entity.AccountType;
import com.yuecheng.hops.account.repository.AccountDao;
import com.yuecheng.hops.account.repository.CardAccountDao;
import com.yuecheng.hops.account.repository.CurrencyAccountJpaDao;
import com.yuecheng.hops.common.enump.AccountModelType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;


/**
 * 账户DAO查找器
 * 
 * @author Administrator
 * @version 2014年10月16日
 * @see AccountDaoFinder
 * @since
 */
@Component
public class AccountDaoFinder
{
    @Autowired
    private CurrencyAccountJpaDao currencyAccountDao;

    @Autowired
    private CardAccountDao cardAccountDao;
    
    @Autowired
    private AccountDaoSelector accountDaoSeletor;
    
    public AccountDao getByModelType(AccountType accountType,Long transactionNo)
    {
        AccountDao accountDao = null;
        if (AccountModelType.FUNDS.equals(accountType.getTypeModel()))
        {
            accountDao = accountDaoSeletor.selectDao(accountType, transactionNo);
            return accountDao;
        }
        else if (AccountModelType.CARD.equals(accountType.getTypeModel()))
        {
            return cardAccountDao;
        }
        else
        {
            String[] msgParams = new String[] {accountType.getTypeModel().toString()};
            String[] viewParams = new String[] {};
            ApplicationException ae = new ApplicationException("identity101053", msgParams,
                viewParams);
            throw ExceptionUtil.throwException(ae);
        }
    }
}
