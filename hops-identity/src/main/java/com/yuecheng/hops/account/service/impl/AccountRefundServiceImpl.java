/*
 * 文件名：AccountRefundServiceImpl.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月18日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.account.service.impl;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.yuecheng.hops.account.entity.CCYAccount;
import com.yuecheng.hops.account.entity.CurrencyAccountBalanceHistory;
import com.yuecheng.hops.account.entity.TransactionHistory;
import com.yuecheng.hops.account.service.AccountRefundService;
import com.yuecheng.hops.account.service.AccountServiceFinder;
import com.yuecheng.hops.account.service.AccountTypeService;
import com.yuecheng.hops.account.service.CCYAccountService;
import com.yuecheng.hops.account.service.CurrencyAccountBalanceHistoryService;
import com.yuecheng.hops.account.service.IdentityAccountRoleService;
import com.yuecheng.hops.account.service.TransactionHistoryService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.DecimalPlaces;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.DateUtil;
import com.yuecheng.hops.identity.entity.sp.SP;
import com.yuecheng.hops.identity.service.sp.SpService;


@Service("accountRefundService")
public class AccountRefundServiceImpl implements AccountRefundService
{
    private static Logger logger = LoggerFactory.getLogger(AccountRefundServiceImpl.class);

    @Autowired
    private AccountServiceFinder accountServiceFinder;
    
    @Autowired
    private CCYAccountService ccyAccountService;

    @Autowired
    private TransactionHistoryService transactionHistoryService;
    
    @Autowired
    private AccountTypeService accountTypeService;
    
    @Autowired
    private CurrencyAccountBalanceHistoryService currencyAccountBalanceHistoryService;
    
    @Autowired
    private IdentityAccountRoleService identityAccountRoleService;
    
    @Autowired
    private SpService spService;
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    
    @Override
    @Transactional
    public boolean refundTransfer(Long transferNo, Date orderRequestTime)
    {
        Assert.notNull(transferNo);
        Assert.notNull(orderRequestTime);
        try
        {
            List<TransactionHistory> transactionHistorys = transactionHistoryService.queryNoRefundTransactionHistoryByTransactionNo(String.valueOf(transferNo));
            for(TransactionHistory transactionHistory : transactionHistorys)
            {
                Long payerAccountId = transactionHistory.getPayeeAccountId();
                Long payerAccountTypeId = transactionHistory.getPayeeAccountTypeId();
                Long payeeAccountId = transactionHistory.getPayerAccountId();
                Long payeeAccountTypeId = transactionHistory.getPayerAccountTypeId();
                Boolean isTransferSystemAgentProfit = false;
                if(Constant.TransferType.TRANSFER_SYSTEM_AGENT_PROFIT.equalsIgnoreCase(transactionHistory.getType()) && DateUtil.daysBetween(sdf.parse(sdf.format(orderRequestTime)), new Date()) > 3){
                    //退款订单日期大于等于3天      退款账户为系统信用账户
                    SP sp = spService.getSP();
                    // 系统信用户
                    CCYAccount sysCreditAccount = (CCYAccount)identityAccountRoleService.getAccountNoCache(
                        Constant.AccountType.SYSTEM_CREDIT, sp.getId(), IdentityType.SP.toString(),
                        Constant.Account.ACCOUNT_RELATION_TYPE_OWN, null);
                    payerAccountId = sysCreditAccount.getAccountId();
                    payerAccountTypeId = sysCreditAccount.getAccountType().getAccountTypeId();
                    payeeAccountId = transactionHistory.getPayerAccountId();
                    payeeAccountTypeId = transactionHistory.getPayerAccountTypeId();
                    isTransferSystemAgentProfit = true;
                }
                
                //1.记录流水
                TransactionHistory th = new TransactionHistory();
                th.setAmt(transactionHistory.getAmt());
                th.setCreateDate(new Date());
                th.setDescStr("回退交易：收款方:["+payeeAccountId+"]、付款方:["+payerAccountId+"] 交易金额为:["+transactionHistory.getAmt().setScale(DecimalPlaces.THREE.value(), BigDecimal.ROUND_HALF_UP)+"]");
                th.setTransactionNo(transactionHistory.getTransactionNo());
                th.setPayerAccountId(payerAccountId);
                th.setPayerAccountTypeId(payerAccountTypeId);
                th.setPayeeAccountId(payeeAccountId);
                th.setPayeeAccountTypeId(payeeAccountTypeId);
                th.setType(Constant.TransferType.TRANSFER_UN_AGENT_ORDERED);
                th.setIsRefund(Constant.RefundConfiguration.REFUNDED);
                th = transactionHistoryService.saveTransactionHistory(th);
                transactionHistoryService.updateIsRefundByParams(transactionHistory, Constant.RefundConfiguration.REFUNDED);
                //2.回退交易
                List<CurrencyAccountBalanceHistory> historys = currencyAccountBalanceHistoryService.getTransferBalanceHistoryByParams(transactionHistory.getTransactionId().toString());
                for(CurrencyAccountBalanceHistory history : historys)
                {
                    Long accountId = history.getAccountId();
                    Long accountTypeId = history.getAccountTypeId();
                    if(isTransferSystemAgentProfit && Constant.AccountBalanceOperationType.ACT_BAL_OPR_DEBIT.equalsIgnoreCase(history.getType())){
                        accountId = payerAccountId;
                        accountTypeId = payerAccountTypeId;
                    }
                    String desc = "回滚交易 交易号 transactionId[" + transactionHistory.getTransactionId() + "]";
                    if(Constant.AccountBalanceOperationType.ACT_BAL_OPR_CREDIT.equalsIgnoreCase(history.getType()))
                    {
                        //贷记 
                        ccyAccountService.debitAction(accountId, accountTypeId, history.getChangeAmount(), th.getTransactionId(), transferNo, Constant.AccountBalanceOperationType.ACT_BAL_OPR_DEBIT, desc);
                    }
                    else if(Constant.AccountBalanceOperationType.ACT_BAL_OPR_DEBIT.equalsIgnoreCase(history.getType()))
                    {
                        //借记 
                        ccyAccountService.creditAction(accountId, accountTypeId, history.getChangeAmount(), th.getTransactionId(), transferNo, Constant.AccountBalanceOperationType.ACT_BAL_OPR_CREDIT, desc);
                    }
                    else if(Constant.AccountBalanceOperationType.ACT_BAL_OPR_FORZEN.equalsIgnoreCase(history.getType()))
                    {
                        //冻结
                        ccyAccountService.unfrozenAccountAmountAction(accountId, accountTypeId, history.getChangeAmount(), th.getTransactionId());
                    }
                    else if(Constant.AccountBalanceOperationType.ACT_BAL_OPR_UNFORZEN.equalsIgnoreCase(history.getType()))
                    {
                        //解冻
                        ccyAccountService.frozenAccountAmountAction(accountId, accountTypeId, history.getChangeAmount(), th.getTransactionId());
                    }
                }
            }
            return true;
        }
        catch (Exception e)
        {
            throw ExceptionUtil.throwException(new ApplicationException("identity101087",
                new String[] {ExceptionUtil.getStackTraceAsString(e)}, e));
        }
    }
    
    @Override
    @Transactional
    public boolean refundDeliveryTransfer(Long transferNo, Date orderRequestTime)
    {
        Assert.notNull(transferNo);
        Assert.notNull(orderRequestTime);
        try
        {
            List<TransactionHistory> transactionHistorys = transactionHistoryService.queryNoRefundTransactionHistoryByTransactionNo(String.valueOf(transferNo));
            for(TransactionHistory transactionHistory : transactionHistorys)
            {
                if(transactionHistory.getType().equalsIgnoreCase(Constant.TransferType.TRANSFER_SUPPLY_ORDER_FINISH) || transactionHistory.getType().equalsIgnoreCase(Constant.TransferType.TRANSFER_SYSTEM_AGENT_PROFIT)){
                    Long payerAccountId = transactionHistory.getPayeeAccountId();
                    Long payerAccountTypeId = transactionHistory.getPayeeAccountTypeId();
                    Long payeeAccountId = transactionHistory.getPayerAccountId();
                    Long payeeAccountTypeId = transactionHistory.getPayerAccountTypeId();
                    Boolean isTransferSystemAgentProfit = false;
                    if(Constant.TransferType.TRANSFER_SYSTEM_AGENT_PROFIT.equalsIgnoreCase(transactionHistory.getType()) && DateUtil.daysBetween(sdf.parse(sdf.format(orderRequestTime)), new Date()) > 3){
                        //退款订单日期大于等于3天      退款账户为系统信用账户
                        SP sp = spService.getSP();
                        // 系统信用户
                        CCYAccount sysCreditAccount = (CCYAccount)identityAccountRoleService.getAccountNoCache(
                            Constant.AccountType.SYSTEM_CREDIT, sp.getId(), IdentityType.SP.toString(),
                            Constant.Account.ACCOUNT_RELATION_TYPE_OWN, null);
                        payerAccountId = sysCreditAccount.getAccountId();
                        payerAccountTypeId = sysCreditAccount.getAccountType().getAccountTypeId();
                        payeeAccountId = transactionHistory.getPayerAccountId();
                        payeeAccountTypeId = transactionHistory.getPayerAccountTypeId();
                        isTransferSystemAgentProfit = true;
                    }
                    
                    //1.记录流水
                    TransactionHistory th = new TransactionHistory();
                    th.setAmt(transactionHistory.getAmt());
                    th.setCreateDate(new Date());
                    th.setDescStr("回退交易：收款方:["+payeeAccountId+"]、付款方:["+payerAccountId+"] 交易金额为:["+transactionHistory.getAmt().setScale(DecimalPlaces.THREE.value(), BigDecimal.ROUND_HALF_UP)+"]");
                    th.setTransactionNo(transactionHistory.getTransactionNo());
                    th.setPayerAccountId(payerAccountId);
                    th.setPayerAccountTypeId(payerAccountTypeId);
                    th.setPayeeAccountId(payeeAccountId);
                    th.setPayeeAccountTypeId(payeeAccountTypeId);
                    th.setType(Constant.TransferType.TRANSFER_UN_AGENT_ORDERED);
                    th.setIsRefund(Constant.RefundConfiguration.REFUNDED);
                    th = transactionHistoryService.saveTransactionHistory(th);
                    transactionHistoryService.updateIsRefundByParams(transactionHistory, Constant.RefundConfiguration.REFUNDED);
                    //2.回退交易
                    List<CurrencyAccountBalanceHistory> historys = currencyAccountBalanceHistoryService.getTransferBalanceHistoryByParams(transactionHistory.getTransactionId().toString());
                    for(CurrencyAccountBalanceHistory history : historys)
                    {
                        Long accountId = history.getAccountId();
                        Long accountTypeId = history.getAccountTypeId();
                        if(isTransferSystemAgentProfit && Constant.AccountBalanceOperationType.ACT_BAL_OPR_DEBIT.equalsIgnoreCase(history.getType())){
                            accountId = payerAccountId;
                            accountTypeId = payerAccountTypeId;
                        }
                        String desc = "回滚交易 交易号 transactionId[" + transactionHistory.getTransactionId() + "]";
                        if(Constant.AccountBalanceOperationType.ACT_BAL_OPR_CREDIT.equalsIgnoreCase(history.getType()))
                        {
                            //贷记 
                            ccyAccountService.debitAction(accountId, accountTypeId, history.getChangeAmount(), th.getTransactionId(), transferNo, Constant.AccountBalanceOperationType.ACT_BAL_OPR_DEBIT, desc);
                        }
                        else if(Constant.AccountBalanceOperationType.ACT_BAL_OPR_DEBIT.equalsIgnoreCase(history.getType()))
                        {
                            //借记 
                            ccyAccountService.creditAction(accountId, accountTypeId, history.getChangeAmount(), th.getTransactionId(), transferNo, Constant.AccountBalanceOperationType.ACT_BAL_OPR_CREDIT, desc);
                        }
                        else if(Constant.AccountBalanceOperationType.ACT_BAL_OPR_FORZEN.equalsIgnoreCase(history.getType()))
                        {
                            //冻结
                            ccyAccountService.unfrozenAccountAmountAction(accountId, accountTypeId, history.getChangeAmount(), th.getTransactionId());
                        }
                        else if(Constant.AccountBalanceOperationType.ACT_BAL_OPR_UNFORZEN.equalsIgnoreCase(history.getType()))
                        {
                            //解冻
                            ccyAccountService.frozenAccountAmountAction(accountId, accountTypeId, history.getChangeAmount(), th.getTransactionId());
                        }
                    }
                }
            }
            return true;
        }
        catch (Exception e)
        {
            throw ExceptionUtil.throwException(new ApplicationException("identity101087",
                new String[] {ExceptionUtil.getStackTraceAsString(e)}, e));
        }
    }
    
    @Override
    @Transactional
    public boolean refund(Long transactionId, Long transactionNo)
    {
        try
        {
            logger.debug("回滚交易 交易号 transactionId[" + transactionId + "]");
            TransactionHistory transactionHistory = transactionHistoryService.getTransactionHistoryById(transactionId);
            TransactionHistory th = new TransactionHistory();
            th.setAmt(transactionHistory.getAmt());
            th.setCreateDate(new Date());
            th.setDescStr("回退交易：收款方:["+transactionHistory.getPayerAccountId()+"]、付款方:["+transactionHistory.getPayeeAccountId()+"] 交易金额为:["+transactionHistory.getAmt().setScale(DecimalPlaces.THREE.value(), BigDecimal.ROUND_HALF_UP)+"]");
            th.setTransactionNo(transactionHistory.getTransactionNo());
            th.setPayerAccountId(transactionHistory.getPayeeAccountId());
            th.setPayerAccountTypeId(transactionHistory.getPayeeAccountTypeId());
            th.setPayeeAccountId(transactionHistory.getPayerAccountId());
            th.setPayeeAccountTypeId(transactionHistory.getPayerAccountTypeId());
            th.setType(Constant.TransferType.TRANSFER_UN_AGENT_ORDERED);
            th.setIsRefund(Constant.RefundConfiguration.REFUNDED);
            th = transactionHistoryService.saveTransactionHistory(th);
            transactionHistoryService.updateIsRefundByParams(transactionHistory, Constant.RefundConfiguration.REFUNDED);
            
            String desc = "回滚交易 交易号 transactionId[" + transactionId + "]";
            List<CurrencyAccountBalanceHistory> historys = currencyAccountBalanceHistoryService.getTransferBalanceHistoryByParams(transactionId.toString());
            for(CurrencyAccountBalanceHistory history : historys)
            {
                if(Constant.AccountBalanceOperationType.ACT_BAL_OPR_CREDIT.equalsIgnoreCase(history.getType()))
                {
                    //贷记 
                    ccyAccountService.debitAction(history.getAccountId(), history.getAccountTypeId(), history.getChangeAmount(), th.getTransactionId(), transactionNo, Constant.AccountBalanceOperationType.ACT_BAL_OPR_DEBIT, desc);
                }
                else if(Constant.AccountBalanceOperationType.ACT_BAL_OPR_DEBIT.equalsIgnoreCase(history.getType()))
                {
                    //借记 
                    ccyAccountService.creditAction(history.getAccountId(), history.getAccountTypeId(), history.getChangeAmount(), th.getTransactionId(), transactionNo, Constant.AccountBalanceOperationType.ACT_BAL_OPR_CREDIT, desc);
                }
                else if(Constant.AccountBalanceOperationType.ACT_BAL_OPR_FORZEN.equalsIgnoreCase(history.getType()))
                {
                    //冻结
                    ccyAccountService.unfrozenAccountAmountAction(history.getAccountId(), history.getAccountTypeId(), history.getChangeAmount(), th.getTransactionId());
                }
                else if(Constant.AccountBalanceOperationType.ACT_BAL_OPR_UNFORZEN.equalsIgnoreCase(history.getType()))
                {
                    //解冻
                    ccyAccountService.frozenAccountAmountAction(history.getAccountId(), history.getAccountTypeId(), history.getChangeAmount(), th.getTransactionId());
                }
            }
            return true;
        }catch (Exception e)
        {
            throw new ApplicationException("identity101058", new String[] {ExceptionUtil.getStackTraceAsString(e)}, e);
        }
    }

}
