package com.yuecheng.hops.transaction.execution.account;


import java.math.BigDecimal;
import java.util.Date;

import com.yuecheng.hops.common.enump.AccountModelType;
import com.yuecheng.hops.common.exception.HopsException;


public interface AirtimeAccountingTransaction
{

    public void deliverySuccessTransfer(Long orderId, 
                                        Long spDebitAccountId, Long spDebitAccountTypeId, BigDecimal orderSalesFee, 
                                        Long upAccountId, Long upAccountTypeId, BigDecimal deliveryCostFee, String desc);
    
    public void orderSuccessTransfer(Long orderId, Long spDebitAccountId,
                                     Long spDebitAccountTypeId, BigDecimal orderSalesFee,
                                     Long upAccountId, Long upAccountTypeId,
                                     BigDecimal deliveryCostFee, Long spProfitAccountId,
                                     Long spProfitAccountTypeId, BigDecimal profitAmt, String desc);

    public void orderFailTransfer(Long orderId, Long agentAccountId, Long agentAccountTypeId, BigDecimal orderSalesFee,
                                  Long spDebitAccountId, Long spDebitAccountTypeId, String desc);

    public Long transfer(Long payerAccountId, Long payerAccountTypeId, Long payeeAccountId,
                         Long payeeAccountTypeId, BigDecimal amt, String desc, String type,
                         Long transactionNo)
        throws HopsException;

    public void refund(Long transactionId, Long transactionNo);
    
    public void refundTransfer(Long transactionNo, Date orderRequestTime);

    public void frozenAccount(Long accountId, Long accountTypeId, AccountModelType accountModelType,
                              BigDecimal frozenAmt, Long orderId);

    public void unfrozenAccount(Long accountId, Long accountTypeId, AccountModelType accountModelType,
                                BigDecimal frozenAmt, Long orderId);
    
    public void refundDeliveryTransfer(Long transferNo);
}
