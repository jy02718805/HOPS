package com.yuecheng.hops.account.service;


import java.util.Map;

import com.yuecheng.hops.account.entity.CardAccountBalanceHistory;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;


public interface CardAccountBalanceHistoryService
{

    public YcPage<CardAccountBalanceHistory> queryCardAccountBalanceHistory(Map<String, Object> searchParams,
                                                                            int pageNumber,
                                                                            int pageSize,
                                                                            BSort bsort);
}
