/*
 * 文件名：AccountVoConvertor.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月15日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.account.convertor;


import com.yuecheng.hops.account.entity.CCYAccount;
import com.yuecheng.hops.account.entity.role.IdentityAccountRole;
import com.yuecheng.hops.account.entity.vo.CCYAccountVo;


/**
 * 账户对象转换接口
 * 
 * @author Administrator
 * @version 2014年10月16日
 * @see AccountVoConvertor
 * @since
 */
public interface AccountVoConvertor
{
    /**
     * 转换
     * 
     * @param currencyAccount
     * @param identityAccountRole
     * @return
     * @see
     */
    CCYAccountVo execute(CCYAccount ccyAccount,
                              IdentityAccountRole identityAccountRole);
}
