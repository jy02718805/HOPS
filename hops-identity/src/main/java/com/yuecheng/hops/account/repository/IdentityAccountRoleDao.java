/*
 * 文件名：CurrencyAccountService.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月29日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.account.repository;


import java.util.Map;

import com.yuecheng.hops.account.entity.CCYAccount;
import com.yuecheng.hops.account.entity.vo.SpAccountVo;
import com.yuecheng.hops.common.query.YcPage;


public interface IdentityAccountRoleDao
{

    YcPage<SpAccountVo> queryCurrencyAccountBySp(Map<String, Object> searchParams, int pageNumber,
                                           int pageSize);

    YcPage<CCYAccount> queryCurrencyAccountByMerchant(Map<String, Object> searchParams,
                                                           int pageNumber, int pageSize);

}
