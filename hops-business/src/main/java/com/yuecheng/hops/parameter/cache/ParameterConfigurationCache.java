package com.yuecheng.hops.parameter.cache;


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
import com.yuecheng.hops.parameter.entity.ParameterConfiguration;


/**
 * @author Administrator 通过aop拦截后执行具体操作
 *         http://zhanghua.1199.blog.163.com/blog/static/464498072011111393634448/
 */
@Aspect
@Service("parameterConfigurationCache")
public class ParameterConfigurationCache
{

    public static final int RETRY_TIME = 5;

    @Pointcut("execution(*  com.yuecheng.hops.parameter.service.ParameterConfigurationService.addParameter*(..))")
    private void actionMethodSave()
    {

    }

    @Pointcut("execution(*  com.yuecheng.hops.parameter.service.ParameterConfigurationService.updateParameter*(..))")
    private void actionMethodUpdate()
    {

    }

    @Pointcut("execution(*  com.yuecheng.hops.parameter.service.ParameterConfigurationService.getParameterConfigurationByKey(..))")
    private void actionMethodGet()
    {

    }

    @SuppressWarnings("deprecation")
    @Around("actionMethodSave()")
    public Object interceptorSave(ProceedingJoinPoint pjp)
        throws Throwable
    {
        try
        {
            ParameterConfiguration parameter = (ParameterConfiguration)pjp.proceed();
            HopsCacheUtil.put(Constant.Common.BUSINESS_CACHE, parameter.getConstantName(),
                parameter);
            return parameter;
        }
        catch (Exception e)
        {
            throw ExceptionUtil.throwException(e);
        }
    }

    @SuppressWarnings("deprecation")
    @Around("actionMethodUpdate()")
    public Object interceptorUpdate(ProceedingJoinPoint pjp)
        throws Throwable
    {
        try
        {
            ParameterConfiguration parameter = (ParameterConfiguration)pjp.proceed();
            HopsCacheUtil.put(Constant.Common.BUSINESS_CACHE, parameter.getConstantName(),
                parameter);
            return parameter;
        }
        catch (Exception e)
        {
            throw ExceptionUtil.throwException(e);
        }
    }

    @SuppressWarnings("deprecation")
    @Around("actionMethodGet()")
    public Object interceptorQuery(ProceedingJoinPoint pjp)
        throws Throwable
    {
        try
        {
            Object[] params = pjp.getArgs();
            String key = (String)params[0];
            ParameterConfiguration parameterConfiguration = (ParameterConfiguration)HopsCacheUtil.get(
                Constant.Common.BUSINESS_CACHE, key);
            if (BeanUtils.isNull(parameterConfiguration))
            {
                parameterConfiguration = (ParameterConfiguration)pjp.proceed();
                HopsCacheUtil.put(Constant.Common.BUSINESS_CACHE, key, parameterConfiguration);
            }
            return parameterConfiguration;
        }
        catch (Exception e)
        {
            throw ExceptionUtil.throwException(e);
        }
    }
}
