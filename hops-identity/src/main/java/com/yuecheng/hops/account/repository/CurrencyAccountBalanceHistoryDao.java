package com.yuecheng.hops.account.repository;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yuecheng.hops.account.entity.CurrencyAccountBalanceHistory;
import com.yuecheng.hops.account.entity.bo.AccountBalanceHistoryBo;
import com.yuecheng.hops.account.entity.vo.AccountHistoryAssistVo;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;


public interface CurrencyAccountBalanceHistoryDao
{
    public YcPage<AccountHistoryAssistVo> queryAccountFundsChange(Map<String, Object> searchParams,
                                                                  int pageNumber, int pageSize);

    public YcPage<AccountHistoryAssistVo> queryCurrencyAccountBalanceHistory(Map<String, Object> searchParams,
                                                                             int pageNumber,
                                                                             int pageSize,
                                                                             BSort bsort);

    /**
     * 获得资金变动的账户id
     * 
     * @param beginTime
     * @param endTime
     * @return
     * @see
     */
    public List<BigDecimal> queryCurrencyAccountId(Date beginTime, Date endTime,
                                                   String accountTypeId);

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
     * 保存流水记录
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
     * 解冻检查是否有冻结操作(页面查询)
     * 
     * @param transactionId
     * @param type
     * @return
     * @see
     */
    public List<CurrencyAccountBalanceHistory> getCurrencyAccountBalanceHistoryByParams(String transactionId,
                                                                                        String descStr,
                                                                                        String type);

    /**
     * 获得资金变动历史
     * 
     * @param beginTime
     * @param endTime
     * @return
     * @see
     */
    public List<AccountBalanceHistoryBo> queryAccountReportBos(Date beginTime, Date endTime);

    /**
     * 解冻检查是否有冻结操作(交易查询)
     * 
     * @param transactionId
     * @param descStr
     * @param type
     * @return 
     * @see
     */
    public List<CurrencyAccountBalanceHistory> getFrozenBalanceHistoryByOrderNo(Long orderNo, String type);
    
    /**
     * 回滚交易时查询用(交易编号)
     * 
     * @param transferId
     * @return 
     * @see
     */
    public List<CurrencyAccountBalanceHistory> getTransferBalanceHistoryByParams(String transferId);
}
