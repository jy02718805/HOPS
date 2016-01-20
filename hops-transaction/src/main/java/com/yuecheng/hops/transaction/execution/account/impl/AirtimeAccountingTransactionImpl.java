package com.yuecheng.hops.transaction.execution.account.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.account.entity.CurrencyAccountBalanceHistory;
import com.yuecheng.hops.account.service.AccountRefundService;
import com.yuecheng.hops.account.service.AccountTransferService;
import com.yuecheng.hops.account.service.CCYAccountService;
import com.yuecheng.hops.account.service.CurrencyAccountBalanceHistoryService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.AccountModelType;
import com.yuecheng.hops.common.enump.DecimalPlaces;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.transaction.execution.account.AirtimeAccountingTransaction;


@Service("airtimeAccountingTransaction")
public class AirtimeAccountingTransactionImpl implements AirtimeAccountingTransaction
{
    private static Logger logger = LoggerFactory.getLogger(AirtimeAccountingTransactionImpl.class);

    @Autowired
    private CCYAccountService ccyAccountService;

    @Autowired
    private AccountRefundService accountRefundService;

    @Autowired
    private AccountTransferService accountTransferService;

    @Autowired
    private CurrencyAccountBalanceHistoryService currencyAccountBalanceHistoryService;

    @Override
    public void refundDeliveryTransfer(Long transferNo)
    {
        accountRefundService.refundDeliveryTransfer(transferNo, new Date());
    }
    
    @Override
    public void deliverySuccessTransfer(Long orderId, 
                                        Long spDebitAccountId, Long spDebitAccountTypeId, BigDecimal orderSalesFee, 
                                        Long upAccountId, Long upAccountTypeId, BigDecimal deliveryCostFee, String desc)
    {
        Long transactionNo = orderId;
        boolean unfrizenFlag = false;
        try
        {

            ccyAccountService.unfrozenAccountAmountAction(spDebitAccountId, spDebitAccountTypeId, orderSalesFee, orderId);
            unfrizenFlag = true;

            if(deliveryCostFee.compareTo(new BigDecimal("0")) > 0)
            {
                accountTransferService.doTransfer(spDebitAccountId, spDebitAccountTypeId, upAccountId,
                    upAccountTypeId, deliveryCostFee, desc, Constant.TransferType.TRANSFER_SUPPLY_ORDER_FINISH,
                    transactionNo);
            }
        }
        catch(Exception e)
        {
            if (unfrizenFlag)
            {
                ccyAccountService.frozenAccountAmountAction(spDebitAccountId, spDebitAccountTypeId, orderSalesFee, orderId);
            }
            throw new ApplicationException("transaction002044",
                new String[] {ExceptionUtil.getStackTraceAsString(e)}, e);
        }
    }
    
    @Override
    @Transactional
    public void orderSuccessTransfer(Long orderId, Long spDebitAccountId,
                                     Long spDebitAccountTypeId, BigDecimal orderSalesFee,
                                     Long upAccountId, Long upAccountTypeId,
                                     BigDecimal deliveryCostFee, Long spProfitAccountId,
                                     Long spProfitAccountTypeId, BigDecimal profitAmt, String desc)
    {
        Long transactionNo = orderId;
        List<Long> transactionIds = new ArrayList<Long>();
        boolean unfrizenFlag = false;
        try
        {

            orderSalesFee = orderSalesFee.setScale(DecimalPlaces.THREE.value(),
                BigDecimal.ROUND_HALF_UP);

            ccyAccountService.unfrozenAccountAmountAction(spDebitAccountId, spDebitAccountTypeId,
                orderSalesFee, orderId);
            unfrizenFlag = true;

            Long transactionId = null;
            if (deliveryCostFee.compareTo(new BigDecimal("0")) > 0)
            {
                deliveryCostFee = deliveryCostFee.setScale(DecimalPlaces.THREE.value(),
                    BigDecimal.ROUND_HALF_UP);
                transactionId = accountTransferService.doTransfer(spDebitAccountId,
                    spDebitAccountTypeId, upAccountId, upAccountTypeId, deliveryCostFee, desc,
                    Constant.TransferType.TRANSFER_SUPPLY_ORDER_FINISH, transactionNo);
                transactionIds.add(transactionId);
            }

            if (profitAmt.compareTo(new BigDecimal("0")) > 0)
            {
                profitAmt = profitAmt.setScale(DecimalPlaces.THREE.value(),
                    BigDecimal.ROUND_HALF_UP);
                transactionId = accountTransferService.doTransfer(spDebitAccountId,
                    spDebitAccountTypeId, spProfitAccountId, spProfitAccountTypeId, profitAmt,
                    desc, Constant.TransferType.TRANSFER_SYSTEM_AGENT_PROFIT, transactionNo);
                transactionIds.add(transactionId);
            }
        }
        catch (Exception e)
        {
            logger.error("failed to orderSuccessTransfer caused by:"
                         + ExceptionUtil.getStackTraceAsString(e));
            for (Long transactionId : transactionIds)
            {
                accountRefundService.refund(transactionId, transactionNo);
            }
            if (unfrizenFlag)
            {
                ccyAccountService.frozenAccountAmountAction(spDebitAccountId,
                    spDebitAccountTypeId, orderSalesFee, orderId);
            }
            throw new ApplicationException("transaction001022",
                new String[] {ExceptionUtil.getStackTraceAsString(e)}, e);
        }
    }

    @Override
    @Transactional
    public void orderFailTransfer(Long orderId, Long agentAccountId, Long agentAccountTypeId,
                                  BigDecimal orderSalesFee, Long spDebitAccountId,
                                  Long spDebitAccountTypeId, String desc)
    {
        Long transactionNo = orderId;
        boolean unfrizenFlag = false;
        try
        {
            orderSalesFee = orderSalesFee.setScale(DecimalPlaces.THREE.value(),
                BigDecimal.ROUND_HALF_UP);

            List<CurrencyAccountBalanceHistory> historys = currencyAccountBalanceHistoryService.getFrozenBalanceHistoryByOrderNo(
                orderId, Constant.AccountBalanceOperationType.ACT_BAL_OPR_FORZEN);
            if (historys.size() > 0)
            {
                // 解冻系统账户
                ccyAccountService.unfrozenAccountAmountAction(spDebitAccountId,
                    spDebitAccountTypeId, orderSalesFee, orderId);
                unfrizenFlag = true;
            }

            if (orderSalesFee.compareTo(new BigDecimal("0")) > 0)
            {
                // 退款
                accountTransferService.doTransfer(spDebitAccountId, spDebitAccountTypeId,
                    agentAccountId, agentAccountTypeId, orderSalesFee, desc,
                    Constant.TransferType.TRANSFER_UN_AGENT_ORDERED, transactionNo);
            }
        }
        catch (RpcException rpce)
        {
            throw rpce;
        }
        catch (Exception e)
        {
            logger.error("failed to orderFailTransfer caused by:"
                         + ExceptionUtil.getStackTraceAsString(e));
            if (unfrizenFlag)
            {
                ccyAccountService.frozenAccountAmountAction(spDebitAccountId,
                    spDebitAccountTypeId, orderSalesFee, orderId);
            }
            throw ExceptionUtil.throwException(new ApplicationException("account000002",
                new String[] {ExceptionUtil.getStackTraceAsString(e)}));
        }
    }

    @Override
    @Transactional
    public Long transfer(Long payerAccountId, Long payerAccountTypeId, Long payeeAccountId,
                         Long payeeAccountTypeId, BigDecimal amt, String desc, String type,
                         Long transactionNo)
        throws HopsException
    {
        try
        {
            Long transactionId = -1L;
            if (amt.compareTo(new BigDecimal("0")) > 0)
            {
                amt = amt.setScale(DecimalPlaces.THREE.value(), BigDecimal.ROUND_HALF_UP);

                transactionId = accountTransferService.doTransfer(payerAccountId,
                    payerAccountTypeId, payeeAccountId, payeeAccountTypeId, amt, desc, type,
                    transactionNo);
            }
            return transactionId;
        }
        catch (RpcException rpce)
        {
            throw rpce;
        }
        catch (Exception e)
        {
            logger.error("failed to transfer(" + payerAccountId + "," + payerAccountTypeId + ","
                         + payeeAccountId + "," + payeeAccountTypeId + "," + amt + "," + desc
                         + "," + type + "," + transactionNo + ") caused by:"
                         + ExceptionUtil.getStackTraceAsString(e));
            throw new ApplicationException("account000001",
                new String[] {ExceptionUtil.getStackTraceAsString(e)}, e);
        }
    }

    @Override
    @Transactional
    public void refund(Long transactionId, Long transactionNo)
    {
        try
        {
            accountRefundService.refund(transactionId, transactionNo);
        }
        catch (RpcException rpce)
        {
            throw rpce;
        }
        catch (Exception e)
        {
            logger.error("failed to refund(" + transactionId + "," + transactionNo
                         + ") caused by:" + ExceptionUtil.getStackTraceAsString(e));
            throw new ApplicationException("account000002",
                new String[] {ExceptionUtil.getStackTraceAsString(e)}, e);
        }
    }

    @Override
    @Transactional
    public void refundTransfer(Long transactionNo, Date orderRequestTime)
    {
        try
        {
            accountRefundService.refundTransfer(transactionNo, orderRequestTime);
        }
        catch (RpcException rpce)
        {
            throw rpce;
        }
        catch (Exception e)
        {
            logger.error("failed to refund(" + transactionNo + ") caused by:"
                         + ExceptionUtil.getStackTraceAsString(e));
            throw new ApplicationException("account000005",
                new String[] {ExceptionUtil.getStackTraceAsString(e)}, e);
        }
    }

    @Override
    @Transactional
    public void frozenAccount(Long accountId, Long accountTypeId,
                              AccountModelType accountModelType, BigDecimal frozenAmt, Long orderId)
    {
        try
        {
            frozenAmt = frozenAmt.setScale(DecimalPlaces.THREE.value(), BigDecimal.ROUND_HALF_UP);

            if (AccountModelType.FUNDS.equals(accountModelType))
            {
                ccyAccountService.frozenAccountAmountAction(accountId, accountTypeId, frozenAmt,
                    orderId);
            }
            else
            {
                // CardAccountService
            }
        }
        catch (RpcException rpce)
        {
            throw rpce;
        }
        catch (Exception e)
        {
            logger.error("failed to frozenAccount(" + accountId + "," + accountTypeId + ","
                         + accountModelType + "," + frozenAmt + "," + orderId + ") caused by:"
                         + ExceptionUtil.getStackTraceAsString(e));
            throw new ApplicationException("account000003",
                new String[] {ExceptionUtil.getStackTraceAsString(e)}, e);
        }
    }

    @Override
    @Transactional
    public void unfrozenAccount(Long accountId, Long accountTypeId,
                                AccountModelType accountModelType, BigDecimal frozenAmt,
                                Long orderId)
    {
        try
        {
            frozenAmt = frozenAmt.setScale(DecimalPlaces.THREE.value(), BigDecimal.ROUND_HALF_UP);
            if (AccountModelType.FUNDS.equals(accountModelType) && frozenAmt.compareTo(new BigDecimal("0")) > 0)
            {
                ccyAccountService.unfrozenAccountAmountAction(accountId, accountTypeId, frozenAmt,
                    orderId);
            }
            else
            {
                // CardAccountService
            }
        }
        catch (RpcException rpce)
        {
            throw rpce;
        }
        catch (Exception e)
        {
            logger.error("failed to unfrozenAccount(" + accountId + "," + accountTypeId + ","
                         + accountModelType + "," + frozenAmt + "," + orderId + ") caused by:"
                         + ExceptionUtil.getStackTraceAsString(e));
            throw new ApplicationException("account000004",
                new String[] {ExceptionUtil.getStackTraceAsString(e)}, e);
        }

    }

}
