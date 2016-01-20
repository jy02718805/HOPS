package com.yuecheng.hops.account.service;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.yuecheng.hops.account.entity.AccountType;
import com.yuecheng.hops.account.entity.CCYAccount;
import com.yuecheng.hops.account.entity.CurrencyAccount;
import com.yuecheng.hops.common.query.YcPage;


public interface CCYAccountService extends AccountService
{

    /**
     * 保存账户
     * 
     * @param ac
     * @return
     */
    public CCYAccount saveCCYAccount(AccountType accountType, String accountStatus, String rmk,
                                     String relation, Long transactionNo);

    /**
     * 查询系统帐号列表
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @param sortType
     * @return
     */
    YcPage<CurrencyAccount> queryCurrencyAccountList(Map<String, Object> searchParams,
                                                     int pageNumber, int pageSize, String sortType);

    /**
     * 冻结账户
     * 
     * @param accountId
     * @param unfrozenAmt
     * @param orderId
     * @return
     * @see
     */
    public boolean frozenAccountAmountAction(Long accountId, Long accountTypeId,
                                             BigDecimal unfrozenAmt, Long orderId);

    /**
     * 解冻账户
     * 
     * @param accountId
     * @param unfrozenAmt
     * @param orderId
     * @return
     * @see
     */
    public boolean unfrozenAccountAmountAction(Long accountId, Long accountTypeId,
                                               BigDecimal unfrozenAmt, Long orderId);

    /**
     * 增加授信金额
     * 
     * @param accountId
     * @param amt
     * @throws
     */
    boolean addCreditableBanlanceAction(Long accountId, Long accountTypeId, BigDecimal amt,String remark);

    /**
     * 减少授信金额
     * 
     * @param accountId
     * @param amt
     * @throws
     */
    boolean subCreditableBanlanceAction(Long accountId, Long accountTypeId, BigDecimal amt,String remark);

    /**
     * 通过id获取账户
     */
    CCYAccount getCcyAccountById(Long accountId);

    /**
     * 通过账户类型，identityid确定账户 功能描述: <br> 参数说明: <br>
     */
    List<CCYAccount> queryCCYAccounts(Long accountTypeId, Long identityId, String relation);
}