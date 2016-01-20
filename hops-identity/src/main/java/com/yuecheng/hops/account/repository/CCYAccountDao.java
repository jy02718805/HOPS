/*
 * 文件名：CCYAccountDao.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator 修改时间：2015年1月5日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.account.repository;


import java.util.List;

import com.yuecheng.hops.account.entity.CCYAccount;


public interface CCYAccountDao
{
    CCYAccount getCcyAccountById(Long accountId);

    List<CCYAccount> queryCCYAccounts(Long accountTypeId, Long identityId,String relation);
}
