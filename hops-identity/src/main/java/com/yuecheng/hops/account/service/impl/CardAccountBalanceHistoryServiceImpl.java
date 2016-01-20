package com.yuecheng.hops.account.service.impl;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.account.entity.CardAccountBalanceHistory;
import com.yuecheng.hops.account.repository.CardAccountBalanceHistoryDao;
import com.yuecheng.hops.account.service.CardAccountBalanceHistoryService;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;


/**
 * 卡账户交易历史服务
 * 
 * @author Administrator
 * @version 2014年10月16日
 * @see CardAccountBalanceHistoryServiceImpl
 * @since
 */
@Service("cardAccountBalanceHistoryService")
public class CardAccountBalanceHistoryServiceImpl implements CardAccountBalanceHistoryService
{
    @Autowired
    private CardAccountBalanceHistoryDao cardAccountBalanceHistoryDao;

    /**
     * 查询卡账户交易记录
     */
    public YcPage<CardAccountBalanceHistory> queryCardAccountBalanceHistory(Map<String, Object> searchParams,
                                                                            int pageNumber,
                                                                            int pageSize,
                                                                            BSort bsort)
    {
        return cardAccountBalanceHistoryDao.queryCardAccountBalanceHistory(searchParams,
            pageNumber, pageSize, bsort);
    }

}
