/*
 * 文件名：CardAccountBalanceHistoryDao.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月16日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.account.repository;


import java.util.Map;

import com.yuecheng.hops.account.entity.CardAccountBalanceHistory;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;


public interface CardAccountBalanceHistoryDao
{
    public YcPage<CardAccountBalanceHistory> queryCardAccountBalanceHistory(Map<String, Object> searchParams,
                                                                            int pageNumber,
                                                                            int pageSize,
                                                                            BSort bsort);
}
