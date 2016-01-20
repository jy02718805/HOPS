package com.yuecheng.hops.identity.cache;


/*
 * 文件名：ActionIntercept.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年11月14日 跟踪单号： 修改单号： 修改内容：
 */

// import junit.framework.AssertionFailedError;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.hopscache.HopsCacheUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.identity.entity.AbstractIdentity;
import com.yuecheng.hops.identity.entity.merchant.Merchant;


/**
 * @author Administrator 通过aop拦截后执行具体操作
 *         http://zhanghua.1199.blog.163.com/blog/static/464498072011111393634448/
 */
@Aspect
@Service("merchantCache")
public class MerchantCache
{
    private static Logger logger = LoggerFactory.getLogger(MerchantCache.class);

    @Pointcut("execution(* com.yuecheng.hops.identity.service.merchant.MerchantService.queryMerchantByMerchantCode(..))")
    private void actionMethodQuery1()
    {

    }
    
    @Pointcut("execution(* com.yuecheng.hops.identity.service.IdentityService.findIdentityByIdentityId(..))")
    private void actionMethodQuery2()
    {
        
    }
    
    @Pointcut("execution(* com.yuecheng.hops.identity.service.merchant.MerchantService.queryMerchantById(..))")
    private void actionMethodQuery3()
    {
        
    }
    
    @Pointcut("execution(* com.yuecheng.hops.identity.service.merchant.MerchantService.saveMerchant(..))")
    private void actionMethodSave()
    {

    }
    
    @Pointcut("execution(* com.yuecheng.hops.identity.service.IdentityService.saveIdentity(..))")
    private void actionMerthodUpdate()
    {
        
    }
    
    @Pointcut("execution(* com.yuecheng.hops.identity.service.IdentityService.updateIdentityStatus(..))")
    private void actionMerthodUpdateStatus()
    {
        
    }
    
    @SuppressWarnings("deprecation")
    @Around("actionMethodQuery1()")
    public Object interceptorQuery1(ProceedingJoinPoint pjp)
        throws Throwable
    {
        try
        {
            Object[] args = pjp.getArgs();
            String merchantCode = (String)args[0];
            Merchant merchant =  (Merchant)HopsCacheUtil.get(Constant.Common.IDENTITY_CACHE, Constant.CacheKey.MERCHANT_INFO + Constant.StringSplitUtil.ENCODE + merchantCode);
            if(BeanUtils.isNull(merchant))
            {
                merchant = (Merchant)pjp.proceed();
                HopsCacheUtil.put(Constant.Common.IDENTITY_CACHE, Constant.CacheKey.MERCHANT_INFO + Constant.StringSplitUtil.ENCODE + merchantCode, merchant);
            }
            logger.debug("MerchantService.queryMerchantByMerchantCode(" + (BeanUtils.isNotNull(merchant) ? String.valueOf(merchant).toString() : "NULL") + ")[返回信息]");
            return merchant;
        }
        catch (HopsException hopsException)
        {
            throw ExceptionUtil.throwException(hopsException);
        }
    }
    
    @SuppressWarnings("deprecation")
    @Around("actionMethodQuery2()")
    public Object interceptorQuery2(ProceedingJoinPoint pjp)
        throws Throwable
    {
        try
        {
            Object[] args = pjp.getArgs();
            Long identityId = (Long)args[0];
            IdentityType identityType = (IdentityType)args[1];
            AbstractIdentity identity = null;
            if(IdentityType.MERCHANT.equals(identityType))
            {
                identity =  (Merchant)HopsCacheUtil.get(Constant.Common.IDENTITY_CACHE, Constant.CacheKey.MERCHANT_INFO + Constant.StringSplitUtil.ENCODE + identityId);
                if(BeanUtils.isNull(identity))
                {
                    identity = (AbstractIdentity)pjp.proceed();
                    HopsCacheUtil.put(Constant.Common.IDENTITY_CACHE, Constant.CacheKey.MERCHANT_INFO + Constant.StringSplitUtil.ENCODE + identityId, identity);
                }
            }else{
                identity = (AbstractIdentity)pjp.proceed();
            }
            logger.debug("MerchantService.findIdentityByIdentityId(" + (BeanUtils.isNotNull(identity) ? String.valueOf(identity).toString() : "NULL") + ")[返回信息]");
            return identity;
        }
        catch (HopsException hopsException)
        {
            throw ExceptionUtil.throwException(hopsException);
        }
    }
    
    @SuppressWarnings("deprecation")
    @Around("actionMethodQuery3()")
    public Object interceptorQuery3(ProceedingJoinPoint pjp)
        throws Throwable
    {
        try
        {
            Object[] args = pjp.getArgs();
            Long merchantId = (Long)args[0];
            Merchant merchant = (Merchant)HopsCacheUtil.get(Constant.Common.IDENTITY_CACHE, Constant.CacheKey.MERCHANT_INFO + Constant.StringSplitUtil.ENCODE + merchantId);
            if(BeanUtils.isNull(merchant))
            {
                merchant =  (Merchant)pjp.proceed();
                HopsCacheUtil.put(Constant.Common.IDENTITY_CACHE, Constant.CacheKey.MERCHANT_INFO + Constant.StringSplitUtil.ENCODE + merchantId, merchant);
            }
            logger.debug("MerchantService.queryMerchantById(" + (BeanUtils.isNotNull(merchant) ? String.valueOf(merchant).toString() : "NULL") + ")[返回信息]");
            return merchant;
        }
        catch (HopsException hopsException)
        {
            throw ExceptionUtil.throwException(hopsException);
        }
    }
    
    @SuppressWarnings("deprecation")
    @Around("actionMethodSave()")
    public Object interceptorSave(ProceedingJoinPoint pjp)
        throws Throwable
    {
        try
        {
            Merchant merchant = (Merchant)pjp.proceed();
            HopsCacheUtil.put(Constant.Common.IDENTITY_CACHE, Constant.CacheKey.MERCHANT_INFO + Constant.StringSplitUtil.ENCODE + merchant.getMerchantCode(), merchant);
            HopsCacheUtil.put(Constant.Common.IDENTITY_CACHE, Constant.CacheKey.MERCHANT_INFO + Constant.StringSplitUtil.ENCODE + merchant.getId(), merchant);
            return merchant;
        }
        catch (HopsException hopsException)
        {
            throw ExceptionUtil.throwException(hopsException);
        }
    }
    
    @SuppressWarnings("deprecation")
    @Around("actionMerthodUpdate()")
    public Object interceptorUpdate(ProceedingJoinPoint pjp)
        throws Throwable
    {
        try
        {
            Object[] args = pjp.getArgs();
            AbstractIdentity identity = (AbstractIdentity)args[0];
            IdentityType identityType = identity.getIdentityType();
            
            AbstractIdentity abstractIdentity = (AbstractIdentity)pjp.proceed();
            if(IdentityType.MERCHANT.equals(identityType))
            {
                Merchant merchant = (Merchant)abstractIdentity;
                HopsCacheUtil.put(Constant.Common.IDENTITY_CACHE, Constant.CacheKey.MERCHANT_INFO + Constant.StringSplitUtil.ENCODE + merchant.getMerchantCode(), merchant);
                HopsCacheUtil.put(Constant.Common.IDENTITY_CACHE, Constant.CacheKey.MERCHANT_INFO + Constant.StringSplitUtil.ENCODE + merchant.getId(), merchant);
                return merchant;
            }
            return abstractIdentity;
        }
        catch (HopsException hopsException)
        {
            throw ExceptionUtil.throwException(hopsException);
        }
    }
    
    @SuppressWarnings("deprecation")
    @Around("actionMerthodUpdateStatus()")
    public Object interceptorStatus(ProceedingJoinPoint pjp)
        throws Throwable
    {
        try
        {
            Object[] args = pjp.getArgs();
            IdentityType identityType = (IdentityType)args[2];
            AbstractIdentity abstractIdentity = (AbstractIdentity)pjp.proceed();
            if(IdentityType.MERCHANT.equals(identityType))
            {
                Merchant merchant = (Merchant)abstractIdentity;
                HopsCacheUtil.put(Constant.Common.IDENTITY_CACHE, Constant.CacheKey.MERCHANT_INFO + Constant.StringSplitUtil.ENCODE + merchant.getMerchantCode(), merchant);
                HopsCacheUtil.put(Constant.Common.IDENTITY_CACHE, Constant.CacheKey.MERCHANT_INFO + Constant.StringSplitUtil.ENCODE + merchant.getId(), merchant);
                return merchant;
            }
            return abstractIdentity;
        }
        catch (HopsException hopsException)
        {
            throw ExceptionUtil.throwException(hopsException);
        }
    }
}
