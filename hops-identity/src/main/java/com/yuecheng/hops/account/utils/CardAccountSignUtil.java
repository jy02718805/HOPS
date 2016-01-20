/*
 * 文件名：CardSignUtil.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator 修改时间：2014年10月16日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.account.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuecheng.hops.account.entity.CardAccount;
import com.yuecheng.hops.common.security.MD5Util;


public class CardAccountSignUtil
{
    private static Logger logger = LoggerFactory.getLogger(CardAccountSignUtil.class);

    public static String getSign(CardAccount cardAccount)
    {
        String md5_Str = cardAccount.getAccountId() + cardAccount.getStatus()
                         + cardAccount.getCardNum() + cardAccount.getBalance();
        String md5 = MD5Util.getMD5Sign(md5_Str);
        logger.debug("------------原串:" + md5_Str + "-----------md5:" + md5
                     + "------------------------------------------------------------");
        return md5;
    }
}
