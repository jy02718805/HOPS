package com.yuecheng.hops.account.repository;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yuecheng.hops.account.entity.TransactionHistory;
import com.yuecheng.hops.account.entity.vo.TransactionHistoryAssistVo;
import com.yuecheng.hops.common.query.YcPage;


public interface TransactionHistoryDao
{

    public YcPage<TransactionHistoryAssistVo> queryTransactionHistoryList(Map<String, Object> searchParams,
                                                                          int pageNumber,
                                                                          int pageSize);

    public BigDecimal getTransactionHistoryOfAmtSum(Long accoutId, String logType, Date beginTime,
                                                    Date endTime);

    public List<TransactionHistory> queryNoRefundTransactionHistoryByTransactionNo(String transactionNo);
    
    public List<TransactionHistory> queryTransactionHistoryByTransactionNo(String transactionNo);
    
    public List<TransactionHistory> queryTransactionHistoryByParams(String transactionNo, String type);
}