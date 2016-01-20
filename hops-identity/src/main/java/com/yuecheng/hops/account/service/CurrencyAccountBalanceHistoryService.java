package com.yuecheng.hops.account.service;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yuecheng.hops.account.entity.CCYAccount;
import com.yuecheng.hops.account.entity.CurrencyAccountBalanceHistory;
import com.yuecheng.hops.account.entity.bo.AccountBalanceHistoryBo;
import com.yuecheng.hops.account.entity.vo.AccountHistoryAssistVo;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;


public interface CurrencyAccountBalanceHistoryService
{
    public CurrencyAccountBalanceHistory saveCurrencyAccountBalanceHistory(Long transactionId,
                                                                           Long accountId,
                                                                           CCYAccount newAccount,
                                                                           String type,
                                                                           String descStr,
                                                                           BigDecimal changeAmount)  throws Exception;

    /**
     * 回滚transfer时使用
     * 
     * @param transferId
     * @return
     * @see
     */
    public List<CurrencyAccountBalanceHistory> getTransferBalanceHistoryByParams(String transferId);

    /**
     * 查询订单对应冻结记录
     * 
     * @param orderNo
     * @param type
     * @return
     * @see
     */
    public List<CurrencyAccountBalanceHistory> getFrozenBalanceHistoryByOrderNo(Long orderNo,
                                                                                String type);

    /**
     * 查询账户资金变动
     * 
     * @param identityId
     * @param currencyAccountBalanceHistory
     * @param transactionNo
     * @param timeFrame
     * @param beginDate
     * @param endDate
     * @param pageNumber
     * @param pageSize
     * @return
     * @see
     */
    public YcPage<AccountHistoryAssistVo> queryAccountFundsChange(Map<String, Object> searchParams,
                                                                  int pageNumber, int pageSize);

    /**
     * 得到账户流水
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @param bsort
     * @return
     * @see
     */
    public YcPage<AccountHistoryAssistVo> queryCurrencyAccountBalanceHistory(Map<String, Object> searchParams,
                                                                             int pageNumber,
                                                                             int pageSize,
                                                                             BSort bsort);

    /**
     * 获得时间段内的AccountId
     * 
     * @param beginTime
     * @param endTime
     * @return
     * @see
     */
    public List<BigDecimal> getCensusAccountIdList(String accountTypeId, Date beginTime,
                                                   Date endTime);

    /**
     * 通过accountid 查询出账户的期初，末余额， 加款，支出
     * 
     * @param accountId
     * @param beginDate
     * @param endDate
     * @param newTime
     * @return
     * @see
     */
    public AccountBalanceHistoryBo getAccountReportBo(Long accountId, Date beginDate, Date endDate);

    /**
     * 通过存储过程保存流水记录
     * 
     * @param transactionId
     * @param accountId
     * @param newAvailableBalance
     * @param newUnavailableBanlance
     * @param newCreditableBanlance
     * @param createDate
     * @param historyType
     * @param descStr
     * @param identityName
     * @see
     */
    public void saveHistory(Long transactionId, Long accountId, BigDecimal newAvailableBalance,
                            BigDecimal newUnavailableBanlance, BigDecimal newCreditableBanlance,
                            Date createDate, String historyType, String descStr,
                            String identityName);

    /**
     * 获得资金变动历史
     * 
     * @param beginTime
     * @param endTime
     * @return
     * @see
     */
    public List<AccountBalanceHistoryBo> queryAccountReportBos(Date beginTime, Date endTime);
}
