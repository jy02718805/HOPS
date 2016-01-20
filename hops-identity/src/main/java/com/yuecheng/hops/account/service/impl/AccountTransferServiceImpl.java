/*
 * 文件名：AccountTransferServiceImpl.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月15日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.account.service.impl;


import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.yuecheng.hops.account.entity.Account;
import com.yuecheng.hops.account.entity.AccountType;
import com.yuecheng.hops.account.entity.CCYAccount;
import com.yuecheng.hops.account.entity.TransactionHistory;
import com.yuecheng.hops.account.repository.AccountDao;
import com.yuecheng.hops.account.service.AccountRefundService;
import com.yuecheng.hops.account.service.AccountService;
import com.yuecheng.hops.account.service.AccountServiceFinder;
import com.yuecheng.hops.account.service.AccountTransferService;
import com.yuecheng.hops.account.service.AccountTypeService;
import com.yuecheng.hops.account.service.CurrencyAccountBalanceHistoryService;
import com.yuecheng.hops.account.service.TransactionHistoryService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.AccountModelType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.BeanUtils;


/**
 * 账户交易服务
 * 
 * @author Administrator
 * @version 2014年10月16日
 * @see AccountTransferServiceImpl
 * @since
 */
@Service("accountTransferService")
public class AccountTransferServiceImpl implements AccountTransferService
{
    private static Logger logger = LoggerFactory.getLogger(AccountTransferServiceImpl.class);

    @Autowired
    private AccountServiceFinder accountServiceFinder;

    @Autowired
    private TransactionHistoryService transactionHistoryService;

    @Autowired
    private CurrencyAccountBalanceHistoryService currencyAccountBalanceHistoryService;

    @Autowired
    private AccountTypeService accountTypeService;

    @Autowired
    private AccountRefundService accountRefundService;

    @Autowired
    private AccountDaoFinder accountDaoFinder;

    /**
     * 交易
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Long doTransfer(Long payerAccountId, Long payerAccountTypeId, Long payeeAccountId,
                           Long payeeAccountTypeId, BigDecimal amt, String desc, String type,
                           Long transactionNo)
        throws Exception
    {
        try
        {
            Assert.isTrue(amt.compareTo(new BigDecimal("0")) > 0);
        }
        catch (Exception e)
        {
            logger.info("doTransfer amt is zero!");
            throw ExceptionUtil.throwException(new ApplicationException("identity101083", e));
        }

        try
        {
            // 检查交易是否重复 订单号、类型、是否回退
            Boolean checkFlag = transactionHistoryService.queryTransactionHistoryByParams(
                String.valueOf(transactionNo), type);
            Assert.isTrue(checkFlag);
        }
        catch (Exception e)
        {
            logger.error("transfer is Already done! transactionNo:" + transactionNo + "    type:"
                         + type);
            throw new Exception(new ApplicationException("identity101084", e).getMessage());
        }

        TransactionHistory th = null;
        try
        {
            long beginTime = System.currentTimeMillis();
            logger.debug("doTransfer begin! beginTime[" + beginTime + "]");
            logger.debug("开始交易，transactionNo[" + transactionNo + "] payerAccountId:["
                         + payerAccountId + "] payerAccountTypeId:[" + payerAccountTypeId
                         + "] payeeAccountId[" + payeeAccountId + "] payeeAccountTypeId["
                         + payeeAccountTypeId + "] amt[" + amt + "] desc[" + desc + "] type["
                         + type + "]");
            AccountType payerAccountType = accountTypeService.queryAccountTypeById(payerAccountTypeId);
            AccountService payerAccountService = accountServiceFinder.getByModelType(payerAccountType.getTypeModel());
            Assert.notNull(payerAccountService, "identity001052");

            AccountType payeeAccountType = accountTypeService.queryAccountTypeById(payeeAccountTypeId);
            AccountService payeeAccountService = accountServiceFinder.getByModelType(payeeAccountType.getTypeModel());
            Assert.notNull(payeeAccountService, "identity001052");

            th = new TransactionHistory();
            th.setAmt(amt);
            th.setCreateDate(new Date());
            th.setDescStr(desc);
            th.setTransactionNo(String.valueOf(transactionNo));
            th.setPayeeAccountId(payeeAccountId);
            th.setPayeeAccountTypeId(payeeAccountTypeId);
            th.setPayerAccountId(payerAccountId);
            th.setPayerAccountTypeId(payerAccountTypeId);
            th.setType(type);
            th.setIsRefund(Constant.RefundConfiguration.NO_REFUND);
            th = transactionHistoryService.saveTransactionHistory(th);

            try
            {
                logger.debug("payer credit");
                AccountDao payerAccountDao = accountDaoFinder.getByModelType(payerAccountType,
                    transactionNo);
                Account payerAccount = (Account)payerAccountDao.findOne(payerAccountId);

                CCYAccount payerCCYAccount = null;

                if (payerAccountType.getTypeModel().equals(AccountModelType.FUNDS))
                {
                    payerCCYAccount = (CCYAccount)payerAccount;
                    // try {
                    // Assert.isTrue(payerCCYAccount.getAvailableBalance().add(payerCCYAccount.getCreditableBanlance()).compareTo(amt)>=0);
                    // } catch (Exception e2) {
                    // logger.error(ExceptionUtil.getStackTraceAsString(e2));
                    // throw new ApplicationException("identity001119", new String[]
                    // {payerAccount.getAccountId().toString()}, e2);
                    // }
                }

                if (payerAccountType.getAccountTypeId() != Constant.AccountType.EXTERNAL_ACCOUNT
                    && payerAccountType.getTypeModel().equals(AccountModelType.FUNDS))
                {
                    payerAccountService.creditAction(payerAccount.getAccountId(),
                        payerAccountType.getAccountTypeId(), amt, th.getTransactionId(),
                        transactionNo, Constant.AccountBalanceOperationType.ACT_BAL_OPR_CREDIT,
                        desc);
                }
                else if (payerAccountType.getAccountTypeId() == Constant.AccountType.EXTERNAL_ACCOUNT
                         && payerAccountType.getTypeModel().equals(AccountModelType.FUNDS))
                {
                    currencyAccountBalanceHistoryService.saveCurrencyAccountBalanceHistory(
                        th.getTransactionId(), payerAccount.getAccountId(), payerCCYAccount,
                        Constant.AccountBalanceOperationType.ACT_BAL_OPR_CREDIT, desc, amt);
                }
                else
                {

                }

            }
            catch (HopsException e)
            {
                throw e;
            }
            catch (Exception e)
            {
                logger.error(ExceptionUtil.getStackTraceAsString(e));
                if (BeanUtils.isNotNull(th) && BeanUtils.isNotNull(th.getTransactionId()))
                {
                    accountRefundService.refund(th.getTransactionId(), transactionNo);
                }
                throw new ApplicationException("identity001117",
                    new String[] {ExceptionUtil.getStackTraceAsString(e)}, e);
            }

            try
            {
                logger.debug("payee debit");
                AccountDao payeeAccountDao = accountDaoFinder.getByModelType(payeeAccountType,
                    transactionNo);
                Account payeeAccount = (Account)payeeAccountDao.findOne(payeeAccountId);

                if (payeeAccountType.getAccountTypeId() != Constant.AccountType.EXTERNAL_ACCOUNT
                    && payeeAccountType.getTypeModel().equals(AccountModelType.FUNDS))
                {
                    payeeAccountService.debitAction(payeeAccount.getAccountId(),
                        payeeAccountType.getAccountTypeId(), amt, th.getTransactionId(),
                        transactionNo, Constant.AccountBalanceOperationType.ACT_BAL_OPR_DEBIT,
                        desc);
                }
                else if (payeeAccountType.getAccountTypeId() == Constant.AccountType.EXTERNAL_ACCOUNT
                         && payeeAccountType.getTypeModel().equals(AccountModelType.FUNDS))
                {
                    CCYAccount payeeCCYAccount = (CCYAccount)payeeAccount;
                    currencyAccountBalanceHistoryService.saveCurrencyAccountBalanceHistory(
                        transactionNo, payeeAccount.getAccountId(), payeeCCYAccount,
                        Constant.AccountBalanceOperationType.ACT_BAL_OPR_DEBIT, desc, amt);
                }
            }
            catch (Exception e)
            {
                logger.error(ExceptionUtil.getStackTraceAsString(e));
                if (BeanUtils.isNotNull(th) && BeanUtils.isNotNull(th.getTransactionId()))
                {
                    accountRefundService.refund(th.getTransactionId(), transactionNo);
                }
                throw new ApplicationException("identity001117",
                    new String[] {ExceptionUtil.getStackTraceAsString(e)}, e);
            }

            long cost_time = System.currentTimeMillis() - beginTime;
            logger.debug("doTransfer end! costTime[" + cost_time + "]");
            return th.getTransactionId();
        }
        catch (HopsException e)
        {
            logger.error("tranfer failed casued by HopsException :"
                         + ExceptionUtil.getStackTraceAsString(e));
            // throw ExceptionUtil.throwException(e);
            throw new Exception(e.getMessage());
        }
        catch (Exception e)
        {
            logger.error("tranfer failed casued by Exception :"
                         + ExceptionUtil.getStackTraceAsString(e));
            // throw ExceptionUtil.throwException(new ApplicationException("identity001117",
            // new String[] {ExceptionUtil.getStackTraceAsString(e)}, e));
            throw new Exception(new ApplicationException("identity001117",
                new String[] {ExceptionUtil.getStackTraceAsString(e)}, e).getMessage());
        }
    }
}
