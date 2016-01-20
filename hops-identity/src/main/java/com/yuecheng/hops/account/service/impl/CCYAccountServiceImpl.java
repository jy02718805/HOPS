package com.yuecheng.hops.account.service.impl;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springside.modules.persistence.SearchFilter;

import com.yuecheng.hops.account.entity.AccountType;
import com.yuecheng.hops.account.entity.CCYAccount;
import com.yuecheng.hops.account.entity.CurrencyAccount;
import com.yuecheng.hops.account.entity.CurrencyAccountBalanceHistory;
import com.yuecheng.hops.account.repository.AccountDao;
import com.yuecheng.hops.account.repository.CCYAccountDao;
import com.yuecheng.hops.account.repository.CurrencyAccountBalanceHistoryDao;
import com.yuecheng.hops.account.repository.CurrencyAccountDao;
import com.yuecheng.hops.account.repository.CurrencyAccountJpaDao;
import com.yuecheng.hops.account.service.AccountTypeService;
import com.yuecheng.hops.account.service.CCYAccountService;
import com.yuecheng.hops.account.service.CurrencyAccountBalanceHistoryService;
import com.yuecheng.hops.account.utils.CurrencyAccountSignUtil;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.PageUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;


@Service("ccyAccountService")
public class CCYAccountServiceImpl implements CCYAccountService, Serializable
{
    private static final long serialVersionUID = 2077555911643583712L;

    private static Logger logger = LoggerFactory.getLogger(CCYAccountServiceImpl.class);

    @Autowired
    private CurrencyAccountBalanceHistoryService currencyAccountBalanceHistoryService;

    @Autowired
    private CurrencyAccountJpaDao currencyAccountJpaDao;

    @Autowired
    private CurrencyAccountBalanceHistoryDao currencyAccountBalanceHistoryDao;

    @Autowired
    private CurrencyAccountDao currencyAccountDao;

    @Autowired
    private AccountTypeService accountTypeService;

    @Autowired
    private transient AccountDaoFinder accountDaoFinder;

    @Autowired
    private transient CCYAccountFinder ccyAccountFinder;

    @Autowired
    private CCYAccountDao ccyAccountDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean addCreditableBanlanceAction(Long accountId, Long accountTypeId, BigDecimal amt,String remark)
    {
        try
        {
            Assert.isTrue(amt.compareTo(new BigDecimal("0")) > 0);
        }
        catch (Exception e)
        {
            logger.info("addCreditableBanlance amt is zero!");
            throw ExceptionUtil.throwException(new ApplicationException("identity001118"));
        }
        try
        {
            String tableName = accountTypeService.chooseTable(accountTypeId, null);
            currencyAccountDao.addCreditableBanlance(accountId, accountTypeId, amt, tableName, remark);
        }
        catch (Exception e)
        {
            throw new ApplicationException("identity101061");
        }
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean subCreditableBanlanceAction(Long accountId, Long accountTypeId, BigDecimal amt,String remark)
    {
        try
        {
            Assert.isTrue(amt.compareTo(new BigDecimal("0")) > 0);
        }
        catch (Exception e)
        {
            logger.info("subCreditableBanlance amt is zero!");
            throw ExceptionUtil.throwException(new ApplicationException("identity001118"));
        }
        try
        {
            String tableName = accountTypeService.chooseTable(accountTypeId, null);
            currencyAccountDao.subCreditableBanlance(accountId, accountTypeId, amt, tableName, remark);
        }
        catch (Exception e)
        {
            throw new ApplicationException("identity101062");
        }
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean debitAction(Long accountId, Long accountTypeId, BigDecimal amt, Long transactionId,
                         Long transactionNo, String type, String remark)
    {
        logger.debug("借记交易 [开始] accountId[" + accountId + "] amt[" + amt + "]");
        try
        {
            Assert.isTrue(amt.compareTo(new BigDecimal("0")) > 0);
        }
        catch (Exception e)
        {
            logger.info("debit amt is zero!");
            throw ExceptionUtil.throwException(new ApplicationException("identity001118"));
        }
        try
        {
            transactionId = BeanUtils.isNotNull(transactionId)?transactionId:-1l;
            String tableName = accountTypeService.chooseTable(accountTypeId, transactionNo);
            currencyAccountDao.debit(accountId, amt, transactionId, type, accountTypeId, tableName, remark);
        }
        catch (Exception e)
        {
            throw new ApplicationException("identity101077", new String[] {accountId.toString(),
                amt.toString(), transactionId.toString(), type, remark});
        }
        logger.debug("借记交易 [结束]");
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean creditAction(Long accountId, Long accountTypeId, BigDecimal amt, Long transactionId,
                          Long transactionNo, String type, String remark)
    {
        logger.debug("贷记交易 [开始] accountId[" + accountId + "] amt[" + amt + "]");
        try
        {
            Assert.isTrue(amt.compareTo(new BigDecimal("0")) > 0);
        }
        catch (Exception e)
        {
            logger.info("credit amt is zero!");
            throw ExceptionUtil.throwException(new ApplicationException("identity001118"));
        }
        try
        {
            transactionId = BeanUtils.isNotNull(transactionId)?transactionId:-1l;
            String tableName = accountTypeService.chooseTable(accountTypeId, transactionNo);
            currencyAccountDao.credit(accountId, amt, transactionId, type, accountTypeId,
                tableName, remark);
        }
        catch (Exception e)
        {
            throw new ApplicationException("identity101078", new String[] {accountId.toString(),
                amt.toString(), transactionId.toString(), type, remark});
        }
        logger.debug("贷记交易 [结束]");
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CCYAccount saveCCYAccount(AccountType accountType, String accountStatus, String rmk,
                                     String relation, Long transactionNo)
    {

        try
        {
            logger.debug("保存现金账户   AccountType" + accountType + "  accountStatus ["
                         + accountStatus + "] rmk[" + rmk + "] relation[" + relation + "] [开始]");

            AccountDao accountDao = accountDaoFinder.getByModelType(accountType, transactionNo);
            CCYAccount aCurrencyAccount = ccyAccountFinder.getAccountByAccountType(accountType,
                transactionNo);
            aCurrencyAccount.setCreateDate(new Date());
            aCurrencyAccount.setAccountType(accountType);
            aCurrencyAccount.setStatus(accountStatus);
            aCurrencyAccount.setRmk(rmk);
            aCurrencyAccount.setAvailableBalance(new BigDecimal(Constant.Account.DEFAULT_BALANCE));
            aCurrencyAccount.setCreditableBanlance(new BigDecimal(Constant.Account.DEFAULT_BALANCE));
            aCurrencyAccount.setUnavailableBanlance(new BigDecimal(
                Constant.Account.DEFAULT_BALANCE));
            aCurrencyAccount = (CCYAccount)accountDao.save(aCurrencyAccount);
            String signStr = CurrencyAccountSignUtil.getSign(aCurrencyAccount);
            aCurrencyAccount.setSign(signStr);
            aCurrencyAccount = (CCYAccount)accountDao.save(aCurrencyAccount);
            logger.debug("保存现金账户 [结束]");
            return aCurrencyAccount;
        }
        catch (Exception e)
        {
            throw new ApplicationException("identity101063",
                new String[] {ExceptionUtil.getStackTraceAsString(e)}, e);
        }
    }

    /**
     * 冻结账户款项
     * 
     * @param ca
     * @param frozenAmt
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean frozenAccountAmountAction(Long accountId, Long accountTypeId, BigDecimal frozenAmt,
                                       Long orderId)
    {
        try
        {
            Assert.isTrue(frozenAmt.compareTo(new BigDecimal("0")) > 0);
        }
        catch (Exception e)
        {
            logger.info("frozenAccountAmount amt is zero!");
            throw ExceptionUtil.throwException(new ApplicationException("identity001118"));
        }
        try
        {
            String tableName = accountTypeService.chooseTable(accountTypeId, orderId);
            currencyAccountDao.frozenBanlance(accountId, accountTypeId, frozenAmt, orderId,
                tableName);
        }
        catch (Exception e)
        {
            throw new ApplicationException("identity000017");
        }
        return true;
    }

    /**
     * 解结账户款项
     * 
     * @param ca
     * @param frozenAmt
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean unfrozenAccountAmountAction(Long accountId, Long accountTypeId,
                                         BigDecimal unfrozenAmt, Long orderId)
    {
        try
        {
            Assert.isTrue(unfrozenAmt.compareTo(new BigDecimal("0")) > 0);
        }
        catch (Exception e)
        {
            logger.info("unfrozenAccountAmount amt is zero!");
            throw ExceptionUtil.throwException(new ApplicationException("identity001118"));
        }
        try
        {
            Assert.notNull(orderId);
            List<CurrencyAccountBalanceHistory> forzenList = currencyAccountBalanceHistoryDao.getFrozenBalanceHistoryByOrderNo(
                orderId, Constant.AccountBalanceOperationType.ACT_BAL_OPR_FORZEN);
            
            List<CurrencyAccountBalanceHistory> unforzenList = currencyAccountBalanceHistoryDao.getFrozenBalanceHistoryByOrderNo(
                orderId, Constant.AccountBalanceOperationType.ACT_BAL_OPR_UNFORZEN);
            
            int forzenTime = forzenList.size();
            int unforzenTime = unforzenList.size();
            
            if (forzenTime > unforzenTime)
            {
                String tableName = accountTypeService.chooseTable(accountTypeId, orderId);
                currencyAccountDao.unFrozenBanlance(accountId, accountTypeId, unfrozenAmt,
                    orderId, tableName);
            }

        }
        catch (Exception e)
        {
            throw new ApplicationException("identity000018");
        }
        return true;
    }

    @Override
    public YcPage<CurrencyAccount> queryCurrencyAccountList(Map<String, Object> searchParams,
                                                            int pageNumber, int pageSize,
                                                            String sortType)
    {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        YcPage<CurrencyAccount> ycPage = PageUtil.queryYcPage(currencyAccountJpaDao, filters,
            pageNumber, pageSize, new Sort(Direction.DESC, Constant.Account.STATUS),
            CurrencyAccount.class);
        return ycPage;
    }

    @Override
    public CCYAccount getAccountByParams(Long accountId, Long accountTypeId, Long transactionNo)
    {
        AccountType accountType = accountTypeService.queryAccountTypeById(accountTypeId);
        AccountDao accountDao = accountDaoFinder.getByModelType(accountType, transactionNo);
        CCYAccount account = (CCYAccount)accountDao.findOne(accountId);
        return account;
    }

    @Override
    public CCYAccount getCcyAccountById(Long accountId)
    {
        // TODO Auto-generated method stub
        CCYAccount ccyAccount = ccyAccountDao.getCcyAccountById(accountId);
        return ccyAccount;
    }

    public List<CCYAccount> queryCCYAccounts(Long accountTypeId, Long identityId,String relation)
    {
        List<CCYAccount> ccyAccounts = ccyAccountDao.queryCCYAccounts(accountTypeId, identityId,relation);
        return ccyAccounts;
    }
}
