package com.yuecheng.hops.account.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.account.service.AccountService;
import com.yuecheng.hops.account.service.AccountServiceFinder;
import com.yuecheng.hops.common.enump.AccountModelType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;


/**
 * 账户服务查找器
 * 
 * @author Administrator
 * @version 2014年10月16日
 * @see AccountServiceFinderImpl
 * @since
 */
@Service("accountServiceFinder")
public class AccountServiceFinderImpl implements AccountServiceFinder
{

    @Autowired
    @Qualifier("ccyAccountService")
    private AccountService ccyAccountService;

    @Autowired
    @Qualifier("cardAccountService")
    private AccountService cardAccountService;

    /**
     * 根据账户类型(funds/card)匹配账户Service
     */
    public AccountService getByModelType(AccountModelType modelType)
    {
        if (AccountModelType.FUNDS.equals(modelType))
        {
            return ccyAccountService;
        }
        else if (AccountModelType.CARD.equals(modelType))
        {
            return cardAccountService;
        }
        else
        {
            String[] msgParams = new String[] {modelType.toString()};
            String[] viewParams = new String[] {};
            ApplicationException ae = new ApplicationException("identity101054", msgParams,
                viewParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

}
