package com.yuecheng.hops.account.service.impl;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.account.entity.CCYAccount;
import com.yuecheng.hops.account.entity.CurrencyAccountBalanceHistory;
import com.yuecheng.hops.account.entity.CurrencyAccountBalanceHistory0;
import com.yuecheng.hops.account.entity.CurrencyAccountBalanceHistory1;
import com.yuecheng.hops.account.entity.CurrencyAccountBalanceHistory2;
import com.yuecheng.hops.account.entity.CurrencyAccountBalanceHistory3;
import com.yuecheng.hops.account.entity.bo.AccountBalanceHistoryBo;
import com.yuecheng.hops.account.entity.vo.AccountHistoryAssistVo;
import com.yuecheng.hops.account.repository.CurrencyAccountBalanceHistory0JpaDao;
import com.yuecheng.hops.account.repository.CurrencyAccountBalanceHistory1JpaDao;
import com.yuecheng.hops.account.repository.CurrencyAccountBalanceHistory2JpaDao;
import com.yuecheng.hops.account.repository.CurrencyAccountBalanceHistory3JpaDao;
import com.yuecheng.hops.account.repository.CurrencyAccountBalanceHistoryDao;
import com.yuecheng.hops.account.service.CurrencyAccountBalanceHistoryService;
import com.yuecheng.hops.account.service.IdentityAccountRoleService;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.identity.service.IdentityService;


/**
 * 现金账户交易记录服务
 * 
 * @author Administrator
 * @version 2014年10月16日
 * @see CurrencyAccountBalanceHistoryServiceImpl
 * @since
 */
@Service("currencyAccountBalanceHistoryService")
public class CurrencyAccountBalanceHistoryServiceImpl implements CurrencyAccountBalanceHistoryService
{
    private static Logger logger = LoggerFactory.getLogger(CurrencyAccountBalanceHistoryServiceImpl.class);

    @Autowired
    private CurrencyAccountBalanceHistoryDao currencyAccountBalanceHistoryDao;

    @Autowired
    private CurrencyAccountBalanceHistory0JpaDao currencyAccountBalanceHistory0JpaDao;
    
    @Autowired
    private CurrencyAccountBalanceHistory1JpaDao currencyAccountBalanceHistory1JpaDao;
    
    @Autowired
    private CurrencyAccountBalanceHistory2JpaDao currencyAccountBalanceHistory2JpaDao;
    
    @Autowired
    private CurrencyAccountBalanceHistory3JpaDao currencyAccountBalanceHistory3JpaDao;
    
    @Autowired
    private IdentityAccountRoleService identityAccountRoleService;

    @Autowired
    private IdentityService identityService;

    @Override
    public List<CurrencyAccountBalanceHistory> getTransferBalanceHistoryByParams(String transferId)
    {
        List<CurrencyAccountBalanceHistory> currencyAccountBalanceHistorys = currencyAccountBalanceHistoryDao.getTransferBalanceHistoryByParams(transferId);
        return currencyAccountBalanceHistorys;
    }
    
    @Override
    public List<CurrencyAccountBalanceHistory> getFrozenBalanceHistoryByOrderNo(Long orderNo, String type)
    {
        List<CurrencyAccountBalanceHistory> currencyAccountBalanceHistorys = currencyAccountBalanceHistoryDao.getFrozenBalanceHistoryByOrderNo(orderNo, type);
        return currencyAccountBalanceHistorys;
    }


    @Override
    @Transactional
    public CurrencyAccountBalanceHistory saveCurrencyAccountBalanceHistory(Long transactionId,
                                                                           Long accountId,
                                                                           CCYAccount account,
                                                                           String type,
                                                                           String descStr,
                                                                           BigDecimal changeAmount) throws Exception
    {
        logger.debug("保存现金交易记录[开始]");
        CurrencyAccountBalanceHistory cabh = new CurrencyAccountBalanceHistory();
        cabh.setAccountId(accountId);
        cabh.setCreateDate(new Date());
        cabh.setNewAvailableBalance(account.getAvailableBalance());
        cabh.setNewCreditableBanlance(account.getCreditableBanlance());
        cabh.setNewUnavailableBanlance(account.getUnavailableBanlance());
        if (transactionId != null)
        {
            cabh.setTransactionId(transactionId);
        }
        cabh.setType(type);
        cabh.setDescStr(descStr);
        cabh.setRemark(descStr);
        cabh.setChangeAmount(changeAmount);
        cabh.setAccountTypeId(account.getAccountType().getAccountTypeId());
        String identityName = identityAccountRoleService.queryIdentityNameByAccountId(accountId);
        cabh.setIdentityName(identityName);
        
        Long historyNo = BeanUtils.isNotNull(transactionId)?transactionId:0%4;
        if(historyNo.compareTo(0l) == 0)
        {
            CurrencyAccountBalanceHistory0 c0 = new CurrencyAccountBalanceHistory0();
            BeanUtils.copyProperties(c0, cabh);
            c0.setCreateDate(new Date());
            currencyAccountBalanceHistory0JpaDao.save(c0);
        }
        else if(historyNo.compareTo(1l) == 0)
        {
            CurrencyAccountBalanceHistory1 c1 = new CurrencyAccountBalanceHistory1();
            BeanUtils.copyProperties(c1, cabh);
            c1.setCreateDate(new Date());
            currencyAccountBalanceHistory1JpaDao.save(c1);
        }
        else if(historyNo.compareTo(2l) == 0)
        {
            CurrencyAccountBalanceHistory2 c2 = new CurrencyAccountBalanceHistory2();
            BeanUtils.copyProperties(c2, cabh);
            c2.setCreateDate(new Date());
            currencyAccountBalanceHistory2JpaDao.save(c2);
        }
        else if(historyNo.compareTo(3l) == 0)
        {
            CurrencyAccountBalanceHistory3 c3 = new CurrencyAccountBalanceHistory3();
            BeanUtils.copyProperties(c3, cabh);
            c3.setCreateDate(new Date());
            currencyAccountBalanceHistory3JpaDao.save(c3);
        }
        
        logger.debug("保存现金交易记录[结束]");
        return cabh;
    }
    

    @Override
    public YcPage<AccountHistoryAssistVo> queryAccountFundsChange(Map<String, Object> searchParams,
                                                                  int pageNumber, int pageSize)
    {
        // TODO Auto-generated method stub
        YcPage<AccountHistoryAssistVo> ycPage = new YcPage<AccountHistoryAssistVo>();
        try
        {
            ycPage = currencyAccountBalanceHistoryDao.queryAccountFundsChange(searchParams,
                pageNumber, pageSize);
        }
        catch (Exception e)
        {
            throw new ApplicationContextException(
                "[资金变动查询][CurrencyAccountBalanceHistoryService.queryAccountFundChange())]"
                    + ExceptionUtil.getStackTraceAsString(e), e);
        }
        return ycPage;
    }

    @Override
    public YcPage<AccountHistoryAssistVo> queryCurrencyAccountBalanceHistory(Map<String, Object> searchParams,
                                                                             int pageNumber,
                                                                             int pageSize,
                                                                             BSort bsort)
    {
        YcPage<AccountHistoryAssistVo> ycPage = currencyAccountBalanceHistoryDao.queryCurrencyAccountBalanceHistory(
            searchParams, pageNumber, pageSize, bsort);
        return ycPage;
    }

    @Override
    public List<BigDecimal> getCensusAccountIdList(String accountTypeId, Date beginTime,
                                                   Date endTime)
    {
        List<BigDecimal> accountIds = currencyAccountBalanceHistoryDao.queryCurrencyAccountId(
            beginTime, endTime, accountTypeId);
        return accountIds;
    }

    @Override
    public AccountBalanceHistoryBo getAccountReportBo(Long accountId, Date beginDate, Date endDate)
    {
        AccountBalanceHistoryBo accountBalanceHistoryBo = currencyAccountBalanceHistoryDao.getAccountReportBo(
            accountId, beginDate, endDate);
        return accountBalanceHistoryBo;
    }

    @Override
    public void saveHistory(Long transactionId, Long accountId, BigDecimal newAvailableBalance,
                            BigDecimal newUnavailableBanlance, BigDecimal newCreditableBanlance,
                            Date createDate, String historyType, String descStr,
                            String identityName)
    {
        currencyAccountBalanceHistoryDao.saveHistory(transactionId, accountId,
            newAvailableBalance, newUnavailableBanlance, newCreditableBanlance, createDate,
            historyType, descStr, identityName);
    }

    @Override
    public List<AccountBalanceHistoryBo> queryAccountReportBos(Date beginTime, Date endTime)
    {
        // TODO Auto-generated method stub
        List<AccountBalanceHistoryBo> abList = currencyAccountBalanceHistoryDao.queryAccountReportBos(
            beginTime, endTime);
        return abList;
    }
}
