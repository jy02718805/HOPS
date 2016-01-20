package com.yuecheng.hops.account.service;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yuecheng.hops.account.entity.TransactionHistory;
import com.yuecheng.hops.account.entity.vo.TransactionHistoryAssistVo;
import com.yuecheng.hops.account.entity.vo.TransactionHistoryVo;
import com.yuecheng.hops.common.query.YcPage;


public interface TransactionHistoryService
{
    /**
     * 查询订单的历史记录
     * 
     * @param orderNo
     * @return
     */
    public List<TransactionHistoryVo> getTransactionHistoryByTransactionNo(String transactionNo);

    /**
     * 查询账户日志
     * 
     * @param
     * @return
     */
    public YcPage<TransactionHistoryAssistVo> queryTransactionHistoryList(Map<String, Object> searchParams,
                                                                  int pageNumber, int pageSize,
                                                                  String sortType);

    public TransactionHistory saveTransactionHistory(TransactionHistory th);

    /**
     * 获得单笔交易
     * 
     * @param orderNo
     * @return
     */
    public TransactionHistory getTransactionHistoryById(Long transactionId);



    /**
     * 根据交易号查询交易记录 : 退款时查询用
     * 
     * @param transactionNo
     * @return 
     * @see
     */
    public List<TransactionHistory> queryNoRefundTransactionHistoryByTransactionNo(String transactionNo);
    
    /**
     * 用账户id,时间获取交易历史的交易金额总和
     * 
     * @param accoutId
     * @param beginTime
     * @param endTime
     * @return
     */
    public BigDecimal getTransactionHistoryOfAmtSum(Long accoutId, String logType, Date beginTime,
                                                    Date endTime);
    
    /**
     * 编辑交易记录是否退款标示（退款时使用）
     * 
     * @param transactionId
     * @param isRefund
     * @return 
     * @see
     */
    public TransactionHistory updateIsRefundByParams(TransactionHistory transaction,Long isRefund);
    
    /**
     * 做正交易时，需要验证交易号和类型是否已经做过此类操作了
     * 
     * @param transactionNo
     * @param type
     * @return 
     * @see
     */
    public Boolean queryTransactionHistoryByParams(String transactionNo,String type);
}
