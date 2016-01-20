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
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.hopscache.HopsCacheUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.injection.entity.InterfaceConstant;


/**
 * @author Administrator 通过aop拦截后执行具体操作
 *         http://zhanghua.1199.blog.163.com/blog/static/464498072011111393634448/
 */
@Aspect
@Service("interfaceConstantCache")
public class InterfaceConstantCache
{

    @Pointcut("execution(*  com.yuecheng.hops.injection.service.InterfaceConstantService.saveInterfaceConstant(..))")
    private void actionMethodSave()
    {

    }
    
    @Pointcut("execution(*  com.yuecheng.hops.injection.service.InterfaceConstantService.getInterfaceConstant(..))")
    private void actionMethodQuery()
    {

    }
    
    @Pointcut("execution(*  com.yuecheng.hops.injection.service.InterfaceConstantService.deleteInterfaceConstant(..))")
    private void actionMethodDelete()
    {

    }
    
    @Pointcut("execution(*  com.yuecheng.hops.injection.service.InterfaceConstantService.updateInterfaceConstant(..))")
    private void actionMethodUpdate()
    {

    }
    
    @SuppressWarnings("deprecation")
    @Around("actionMethodSave()")
    public Object interceptorSave(ProceedingJoinPoint pjp)
        throws Throwable
    {
        try
        {
            InterfaceConstant interfaceConstant = (InterfaceConstant)pjp.proceed();
            HopsCacheUtil.put(Constant.Common.BUSINESS_CACHE, Constant.CacheKey.INTERFACE_CONSTANT + Constant.StringSplitUtil.ENCODE + interfaceConstant.getIdentityId() + Constant.StringSplitUtil.ENCODE + interfaceConstant.getIdentityType() + Constant.StringSplitUtil.ENCODE + interfaceConstant.getKey(), interfaceConstant);
            return interfaceConstant;
        }
        catch (Exception e)
        {
            throw ExceptionUtil.throwException(e);
        }
    }
    
    @SuppressWarnings("deprecation")
    @Around("actionMethodQuery()")
    public Object interceptorQuery(ProceedingJoinPoint pjp)
        throws Throwable
    {
        try
        {
            Object[] args = pjp.getArgs();
            Long identityId = (Long)args[0];
            String identityType = (String)args[1];
            String key = (String)args[2];
            InterfaceConstant interfaceConstant =  (InterfaceConstant)HopsCacheUtil.get(Constant.Common.BUSINESS_CACHE, Constant.CacheKey.INTERFACE_CONSTANT + Constant.StringSplitUtil.ENCODE + identityId + Constant.StringSplitUtil.ENCODE + identityType + Constant.StringSplitUtil.ENCODE + key);
            if(BeanUtils.isNull(interfaceConstant))
            {
                interfaceConstant = (InterfaceConstant)pjp.proceed();
                HopsCacheUtil.put(Constant.Common.BUSINESS_CACHE, Constant.CacheKey.INTERFACE_CONSTANT + Constant.StringSplitUtil.ENCODE + interfaceConstant.getIdentityId() + Constant.StringSplitUtil.ENCODE + interfaceConstant.getIdentityType() + Constant.StringSplitUtil.ENCODE + interfaceConstant.getKey(), interfaceConstant);
            }
            return interfaceConstant;
        }
        catch (Exception e)
        {
            throw ExceptionUtil.throwException(e);
        }
    }
    
    @SuppressWarnings("deprecation")
    @Around("actionMethodDelete()")
    public Object interceptorDelete(ProceedingJoinPoint pjp)
        throws Throwable
    {
        try
        {
            InterfaceConstant interfaceConstant = (InterfaceConstant)pjp.proceed();
            HopsCacheUtil.remove(Constant.Common.BUSINESS_CACHE, Constant.CacheKey.INTERFACE_CONSTANT + Constant.StringSplitUtil.ENCODE + interfaceConstant.getIdentityId() + Constant.StringSplitUtil.ENCODE + interfaceConstant.getIdentityType() + Constant.StringSplitUtil.ENCODE + interfaceConstant.getKey());
            return interfaceConstant;
        }
        catch (HopsException hopsException)
        {
            throw ExceptionUtil.throwException(hopsException);
        }
    }
    
    @SuppressWarnings("deprecation")
    @Around("actionMethodUpdate()")
    public Object interceptorUpdate(ProceedingJoinPoint pjp)
        throws Throwable
    {
        try
        {
            InterfaceConstant interfaceConstant = (InterfaceConstant)pjp.proceed();
            HopsCacheUtil.put(Constant.Common.BUSINESS_CACHE, Constant.CacheKey.INTERFACE_CONSTANT + Constant.StringSplitUtil.ENCODE + interfaceConstant.getIdentityId() + Constant.StringSplitUtil.ENCODE + interfaceConstant.getIdentityType() + Constant.StringSplitUtil.ENCODE + interfaceConstant.getKey(), interfaceConstant);
            return interfaceConstant;
        }
        catch (Exception e)
        {
            throw ExceptionUtil.throwException(e);
        }
    }
}
