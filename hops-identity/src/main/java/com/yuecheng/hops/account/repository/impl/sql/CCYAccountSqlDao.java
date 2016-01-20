/*
 * 文件名：CCYAccountSqlDao.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator 修改时间：2015年1月5日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.account.repository.impl.sql;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.account.entity.CCYAccount;
import com.yuecheng.hops.account.entity.CurrencyAccount;
import com.yuecheng.hops.account.repository.CCYAccountDao;
import com.yuecheng.hops.account.repository.OracleSql;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.exception.ExceptionUtil;


@Service("ccyAccountDao")
public class CCYAccountSqlDao implements CCYAccountDao
{
    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(CCYAccountSqlDao.class);

    @Override
    public CCYAccount getCcyAccountById(Long accountId)
    {
        try
        {
            Query query = em.createNativeQuery(OracleSql.CCYAccount.getCcyAccountById_Sql,
                CurrencyAccount.class);
            query.setParameter(EntityConstant.Account.ACCOUNT_ID, accountId);
            @SuppressWarnings("unchecked")
            List<CCYAccount> ccyAccounts = query.getResultList();
            return ccyAccounts.get(0);
        }
        catch (Exception e)
        {
            LOGGER.error("[CCYAccountSqlDao.getCcyAccountById(accountID:" + accountId
                         + ")] exception info[" + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

    public List<CCYAccount> queryCCYAccounts(Long accountTypeId, Long identityId, String relation)
    {
        try
        {
            Query query = em.createNativeQuery(OracleSql.CCYAccount.QUERYCCYACCOUNTID_SQL,
                CurrencyAccount.class);
            query.setParameter("accountTypeId", accountTypeId);
            query.setParameter("identityId", identityId);
            query.setParameter("relation", relation);
            @SuppressWarnings("unchecked")
            List<CCYAccount> ccyAccounts = query.getResultList();
            return ccyAccounts;
        }
        catch (Exception e)
        {
            LOGGER.error("[CCYAccountSqlDao.queryCCYAccountId(accountTypeId:" + accountTypeId
                         + ",identityId" + identityId + ")] exception info["
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

}
