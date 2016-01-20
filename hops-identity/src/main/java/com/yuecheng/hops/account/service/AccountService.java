package com.yuecheng.hops.account.service;


import java.math.BigDecimal;

import com.yuecheng.hops.account.entity.Account;


public interface AccountService
{
    /**
     * 贷记
     * 
     * @param accountId
     * @param amt
     * @param transactionId
     * @param type
     * @param desc
     * @see
     */
    public boolean creditAction(Long accountId, Long accountTypeId, BigDecimal amt, Long transactionId, Long transactionNo, String type, String remark);

    /**
     * 借记
     * 
     * @param accountId
     * @param amt
     * @param transactionId
     * @param type
     * @param desc
     * @see
     */
    public boolean debitAction(Long accountId, Long accountTypeId, BigDecimal amt, Long transactionId, Long transactionNo, String type, String remark);
    
    /**
     * 根据账户ID、账户类型、订单编号获取对应账户
     * 
     * @param accountId
     * @param accountTypeId
     * @param transactionNo
     * @return 
     * @see
     */
    public Account getAccountByParams(Long accountId, Long accountTypeId, Long transactionNo);
}
