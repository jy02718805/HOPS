package com.yuecheng.hops.transaction.cache;


/*
 * 文件名：ActionIntercept.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年11月14日 跟踪单号： 修改单号： 修改内容：
 */

//import junit.framework.AssertionFailedError;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.hopscache.HopsCacheUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.transaction.config.entify.product.TmallTSC;


/**
 * @author Administrator 通过aop拦截后执行具体操作
 *         http://zhanghua.1199.blog.163.com/blog/static/464498072011111393634448/
 */
@Aspect
@Service("tmallTSCCache")
public class TmallTSCCache
{
    
    public static final int RETRY_TIME = 5;
    
    @Pointcut("execution(* com.yuecheng.hops.transaction.config.product.TmallTSCService.findOne(..))")
    private void actionMethodQuery()
    {
        
    }

    @SuppressWarnings("deprecation")
    @Around("actionMethodQuery()")
    public Object interceptorQuery(ProceedingJoinPoint pjp) throws Throwable
    {
        try
        {
            Object[] params = pjp.getArgs();
            String tsc = (String)params[0];
            TmallTSC tmallTSC = (TmallTSC)HopsCacheUtil.get(Constant.Common.TMALL_TSC_CACHE, Constant.CacheKey.TMALL_TSC+Constant.StringSplitUtil.ENCODE+tsc);
            if(BeanUtils.isNull(tmallTSC))
            {
            	tmallTSC = (TmallTSC)pjp.proceed();
                HopsCacheUtil.put(Constant.Common.TMALL_TSC_CACHE, Constant.CacheKey.TMALL_TSC+Constant.StringSplitUtil.ENCODE+tsc, tmallTSC);
            }
            return tmallTSC;
        }
        catch (HopsException hopsException)
        {
            throw ExceptionUtil.throwException(hopsException);
        }
    }
}
