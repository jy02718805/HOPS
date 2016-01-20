package com.yuecheng.hops.numsection.cache;


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
import com.yuecheng.hops.common.hopscache.HopsCacheUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.numsection.entity.NumSection;


/**
 * @author Administrator 通过aop拦截后执行具体操作
 *         http://zhanghua.1199.blog.163.com/blog/static/464498072011111393634448/
 */
@Aspect
@Service("numSectionCache")
public class NumSectionCache
{
    
    @Pointcut("execution(* com.yuecheng.hops.numsection.service.NumSectionService.saveNumSection(..))")
    private void actionMethodSave()
    {
        
    }
    
    @Pointcut("execution(* com.yuecheng.hops.numsection.service.NumSectionService.deleteNumSection(..))")
    private void actionMethodDelete()
    {
        
    }
    
    @Pointcut("execution(* com.yuecheng.hops.numsection.service.NumSectionService.findOne(..))")
    private void actionMethodQuery()
    {
        
    }

    @SuppressWarnings("deprecation")
    @Around("actionMethodSave()")
    public Object interceptorSave(ProceedingJoinPoint pjp) throws Throwable
    {
        try
        {
        	NumSection numSection = (NumSection) pjp.proceed();
            HopsCacheUtil.put(Constant.Common.NUM_SECTION_CACHE, Constant.CacheKey.NUM_SECTION+Constant.StringSplitUtil.ENCODE+numSection.getSectionId(), numSection);
            return numSection;
        }
        catch (Exception e)
        {
            throw ExceptionUtil.throwException(e);
        }
    }
    
    @SuppressWarnings("deprecation")
    @Around("actionMethodDelete()")
    public Object interceptorDelete(ProceedingJoinPoint pjp) throws Throwable
    {
        try
        {
            Object[] params = pjp.getArgs();
            String numSectionId = String.valueOf(params[0]);
            pjp.proceed();
            HopsCacheUtil.remove(Constant.Common.NUM_SECTION_CACHE, Constant.CacheKey.NUM_SECTION+Constant.StringSplitUtil.ENCODE+numSectionId);
            return numSectionId;
        }
        catch (Exception e)
        {
            throw ExceptionUtil.throwException(e);
        }
    }
    
    @SuppressWarnings("deprecation")
    @Around("actionMethodQuery()")
    public Object interceptorQuery(ProceedingJoinPoint pjp) throws Throwable
    {
        try
        {
        	Object[] params = pjp.getArgs();
        	String numSectionId = String.valueOf(params[0]);
            NumSection numSection = (NumSection)HopsCacheUtil.get(Constant.Common.NUM_SECTION_CACHE, Constant.CacheKey.NUM_SECTION+Constant.StringSplitUtil.ENCODE+numSectionId);
            if(BeanUtils.isNull(numSection))
            {
            	numSection = (NumSection)pjp.proceed();
                HopsCacheUtil.put(Constant.Common.NUM_SECTION_CACHE, Constant.CacheKey.NUM_SECTION+Constant.StringSplitUtil.ENCODE+numSectionId, numSection);
            }
            return numSection;
        }
        catch (Exception e)
        {
            throw ExceptionUtil.throwException(e);
        }
    }
}
