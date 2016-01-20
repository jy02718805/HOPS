/*
 * 文件名：AccountStatusManagement.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月15日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.account.service;


import com.yuecheng.hops.account.entity.AccountType;


public interface AccountStatusManagement
{
    /**
     * 更新账户状态
     * 
     * @param account
     * @param updateUser
     * @return
     * @see
     */
    public void updateAccountStatus(Long accountId, AccountType accountType,
                                    String status, String updateUser);
}
