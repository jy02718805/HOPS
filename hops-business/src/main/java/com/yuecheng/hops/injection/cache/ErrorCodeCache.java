package com.yuecheng.hops.injection.cache;


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

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.hopscache.HopsCacheUtil;
import com.yuecheng.hops.common.utils.BeanUtils;


/**
 * @author Administrator 通过aop拦截后执行具体操作
 *         http://zhanghua.1199.blog.163.com/blog/static/464498072011111393634448/
 */
@Aspect
@Service("errorCodeCache")
public class ErrorCodeCache
{

    @Pointcut("execution(* com.yuecheng.hops.injection.service.ErrorCodeService.getErrorCode(..))")
    private void actionMethodQuery()
    {

    }
    
    
    @SuppressWarnings("deprecation")
    @Around("actionMethodQuery()")
    public Object interceptorQuery(ProceedingJoinPoint pjp)
        throws Throwable
    {
        try
        {
            Object[] args = pjp.getArgs();
            String code = (String)args[0];
            String msg =  (String)HopsCacheUtil.get(Constant.Common.BUSINESS_CACHE, Constant.CacheKey.ERROR_CODE + Constant.StringSplitUtil.ENCODE + code);
            if(BeanUtils.isNull(msg))
            {
                msg = (String)pjp.proceed();
                HopsCacheUtil.put(Constant.Common.BUSINESS_CACHE, Constant.CacheKey.ERROR_CODE + Constant.StringSplitUtil.ENCODE + code, msg);
            }
            return msg;
        }
        catch (Exception e)
        {
            throw ExceptionUtil.throwException(e);
        }
    }
    
}
