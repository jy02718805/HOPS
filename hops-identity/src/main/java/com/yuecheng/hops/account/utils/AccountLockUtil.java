/*
 * 文件名：AccountLockUtil.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年11月27日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.account.utils;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AccountLockUtil
{
    private static Logger                        logger            = LoggerFactory.getLogger(AccountLockUtil.class);

    private static ConcurrentHashMap<Long, Long> concurrentHashMap = new ConcurrentHashMap<Long, Long>();

    public static boolean getLock(Long accountId)
    {
        for (Map.Entry<Long, Long> entry : concurrentHashMap.entrySet())
        {
            logger.debug("AccountLockUtil concurrentHashMap["+entry.getKey() + "]:[" + entry.getValue() + "]\t");
        }
        logger.debug("AccountLockUtil concurrentHashMap size["+concurrentHashMap.size() + "]");
        Long curThreadId = Thread.currentThread().getId();
        Long oldThreadId = concurrentHashMap.putIfAbsent(accountId, curThreadId);
        return oldThreadId == null;

    }

    public static void releaseLock(Long accountId)
    {
        if (concurrentHashMap.containsKey(accountId))
        {
            Long lockedThreadId = concurrentHashMap.get(accountId);
            Long curThreadId = Thread.currentThread().getId();
            if (curThreadId.equals(lockedThreadId))
            {
                concurrentHashMap.remove(accountId);
            }
        }
    }
}
