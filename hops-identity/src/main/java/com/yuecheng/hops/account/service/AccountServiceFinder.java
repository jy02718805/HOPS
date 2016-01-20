/*
 * 文件名：AccountServiceFinder.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月16日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.account.service;


import com.yuecheng.hops.common.enump.AccountModelType;


/**
 * 账户服务查找器
 * 
 * @author Administrator
 * @version 2014年10月16日
 * @see AccountServiceFinder
 * @since
 */
public interface AccountServiceFinder
{
    /**
     * 根据账户类型（funds/card）获取账户服务。
     * 
     * @param modelType
     * @return
     * @see
     */
    public AccountService getByModelType(AccountModelType modelType);
}
