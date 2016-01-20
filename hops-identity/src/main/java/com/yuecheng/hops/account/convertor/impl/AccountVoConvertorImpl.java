/*
 * 文件名：AccountVoConvertorImpl.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月15日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.account.convertor.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.account.convertor.AccountVoConvertor;
import com.yuecheng.hops.account.entity.CCYAccount;
import com.yuecheng.hops.account.entity.role.IdentityAccountRole;
import com.yuecheng.hops.account.entity.vo.CCYAccountVo;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.entity.sp.SP;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.identity.service.sp.SpService;


/**
 * 账户对象转换类
 * 
 * @author Administrator
 * @version 2014年10月16日
 * @see AccountVoConvertorImpl
 * @since
 */
@Service("accountVoConvertor")
public class AccountVoConvertorImpl implements AccountVoConvertor
{
    private static Logger logger = LoggerFactory.getLogger(AccountVoConvertorImpl.class);

    @Autowired
    private MerchantService merchantService;
    
    @Autowired
    private SpService spService;

    @Override
    public CCYAccountVo execute(CCYAccount ccyAccount,
                                     IdentityAccountRole identityAccountRole)
    {
        logger.debug("转换VO对象 [开始] currencyAccount[" + ccyAccount + "]  identityAccountRole["
                     + identityAccountRole + "]");
        CCYAccountVo currencyAccountVo = new CCYAccountVo();
        try
        {
            BeanUtils.copyProperties(currencyAccountVo, ccyAccount);
            //优化项   AbstractIdentity 添加identityName字段!!!!
            if(IdentityType.MERCHANT.toString().equalsIgnoreCase(identityAccountRole.getIdentityType()))
            {
                Merchant merchant = merchantService.queryMerchantById(identityAccountRole.getIdentityId());
                currencyAccountVo.setMerchant(merchant);
            }
            else if(IdentityType.SP.toString().equalsIgnoreCase(identityAccountRole.getIdentityType()))
            {
                SP sp = spService.getSP();
                currencyAccountVo.setSp(sp);
            }
        }
        catch (Exception e)
        {
            throw new ApplicationException("identity101056", new String[] {
            		ccyAccount.toString(), identityAccountRole.toString(), ExceptionUtil.getStackTraceAsString(e)},
                new String[] {});
        }
       
        logger.debug("转换VO对象 [开始] currencyAccountVo[" + currencyAccountVo + "]");
        return currencyAccountVo;
    }

}
