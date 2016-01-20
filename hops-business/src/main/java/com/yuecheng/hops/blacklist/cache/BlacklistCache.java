package com.yuecheng.hops.blacklist.cache;


/*
 * 文件名：ActionIntercept.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年11月14日 跟踪单号： 修改单号： 修改内容：
 */

// import junit.framework.AssertionFailedError;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.blacklist.entity.Blacklist;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.hopscache.HopsCacheUtil;
import com.yuecheng.hops.common.utils.BeanUtils;


/**
 * @author Administrator 通过aop拦截后执行具体操作
 *         http://zhanghua.1199.blog.163.com/blog/static/464498072011111393634448/
 */
@Aspect
@Service("blacklistCache")
public class BlacklistCache
{

    @Pointcut("execution(* com.yuecheng.hops.blacklist.service.BlacklistService.saveBlacklist(..))")
    private void actionMethodSave()
    {

    }

    @Pointcut("execution(* com.yuecheng.hops.blacklist.service.BlacklistService.deleteBlacklist(..))")
    private void actionMethodDelete()
    {

    }

    @Pointcut("execution(* com.yuecheng.hops.blacklist.service.BlacklistService.findOne(..))")
    private void actionMethodQuery()
    {

    }

    @Around("actionMethodSave()")
    public Object interceptorSave(ProceedingJoinPoint pjp)
            throws Throwable
    {
        try
        {
            Blacklist blacklist = (Blacklist)pjp.proceed();
            HopsCacheUtil.put(
                    Constant.Common.BLACKLIST_CACHE,
                    Constant.CacheKey.BLACKLIST
                            + Constant.StringSplitUtil.ENCODE
                            + blacklist.getBlacklistId(), blacklist);
            return blacklist;
        }
        catch (Exception e)
        {
            throw ExceptionUtil.throwException(e);
        }
    }

    @Around("actionMethodDelete()")
    public Object interceptorDelete(ProceedingJoinPoint pjp)
            throws Throwable
    {
        try
        {
            String blacklistId = (String)pjp.proceed();
            HopsCacheUtil.remove(Constant.Common.BLACKLIST_CACHE,
                    Constant.CacheKey.BLACKLIST
                            + Constant.StringSplitUtil.ENCODE + blacklistId);
            return blacklistId;
        }
        catch (Exception e)
        {
            throw ExceptionUtil.throwException(e);
        }
    }

    @Around("actionMethodQuery()")
    public Object interceptorQuery(ProceedingJoinPoint pjp)
            throws Throwable
    {
        try
        {
            Object[] params = pjp.getArgs();
            String blacklistId = String.valueOf(params[0]);
            Blacklist blacklist = (Blacklist)HopsCacheUtil.get(
                    Constant.Common.BLACKLIST_CACHE,
                    Constant.CacheKey.BLACKLIST
                            + Constant.StringSplitUtil.ENCODE + blacklistId);
            if (BeanUtils.isNull(blacklist))
            {
                blacklist = (Blacklist)pjp.proceed();
                HopsCacheUtil.put(
                        Constant.Common.BLACKLIST_CACHE,
                        Constant.CacheKey.BLACKLIST
                                + Constant.StringSplitUtil.ENCODE + blacklistId,
                        blacklist);
            }
            return blacklist;
        }
        catch (Exception e)
        {
            throw ExceptionUtil.throwException(e);
        }
    }
}
