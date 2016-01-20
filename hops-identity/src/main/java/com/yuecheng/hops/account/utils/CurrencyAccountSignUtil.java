/*
 * 文件名：CurrencyAccountSignUtil.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月16日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.account.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuecheng.hops.account.entity.CCYAccount;
import com.yuecheng.hops.common.security.MD5Util;


public class CurrencyAccountSignUtil
{
    private static Logger logger = LoggerFactory.getLogger(CurrencyAccountSignUtil.class);

    public static String getSign(CCYAccount currencyAccount)
    {
        String md5_Str = "" + currencyAccount.getAccountId() + "" + currencyAccount.getStatus()
                         + "" + currencyAccount.getAvailableBalance().intValue() + ""
                         + currencyAccount.getUnavailableBanlance().intValue() + ""
                         + currencyAccount.getCreditableBanlance().intValue();
        String md5 = MD5Util.getMD5Sign(md5_Str);
        logger.debug("------------原串:" + md5_Str + "-----------md5:" + md5
                     + "------------------------------------------------------------");
        return md5;
    }
}
