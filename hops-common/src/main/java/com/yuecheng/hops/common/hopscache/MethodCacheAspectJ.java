package com.yuecheng.hops.common.hopscache;


import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;

import com.yuecheng.hops.common.utils.DateUtil;


public class MethodCacheAspectJ
{
    private static Log logger = LogFactory.getLog(MethodCacheAspectJ.class);

    private static final String METHOD_CACHE_NAME = "UserCache";

    /**
     * getter method
     * 
     * @return the methodCacheName
     */

    public static String getMethodCacheName()
    {
        return METHOD_CACHE_NAME;
    }

    @Pointcut("@annotation(com.yuecheng.hops.common.cache.MethodCache)")
    public void methodCachePointcut()
    {}

    @Around("methodCachePointcut()")
    public Object methodCacheHold(ProceedingJoinPoint joinPoint)
        throws Throwable
    {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Object result = null;
        String cacheKey = getCacheKey(targetName, methodName, arguments);
        Object element = HopsCacheUtil.get(METHOD_CACHE_NAME, cacheKey);
        if (element == null)
        {
            try
            {
                result = joinPoint.proceed();
            }
            catch (Exception e)
            {
                logger.info(e);
            }
            if (result != null)
            {
                try
                {
                    Class targetClass = Class.forName(targetName);
                    Method[] method = targetClass.getMethods();
                    int second = 0;
                    for (Method m : method)
                    {
                        if (m.getName().equals(methodName))
                        {
                            Class[] tmpCs = m.getParameterTypes();
                            if (tmpCs.length == arguments.length)
                            {
                                MethodCache methodCache = m.getAnnotation(MethodCache.class);
                                second = methodCache.second();
                                break;
                            }
                        }
                    }
                    if (second > 0)
                    { // annotation没有设second值则使用ehcache-cluster.xml中自定义值
                    }
                    HopsCacheUtil.put(METHOD_CACHE_NAME, cacheKey, (Serializable)result);
                }
                catch (Exception e)
                {
                    logger.info("!!!!!!!!!" + cacheKey + "!!!!!!!!!未能执行方法缓存" + e);
                }
            }
        }
        return element;
    }

    private String getCacheKey(String targetName, String methodName, Object[] arguments)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(targetName).append(".").append(methodName);
        if ((arguments != null) && (arguments.length != 0))
        {
            for (int i = 0; i < arguments.length; i++ )
            {
                if (arguments[i] instanceof Date)
                {
                    sb.append(".").append(DateUtil.dateToString((Date)arguments[i]));
                }
                else
                {
                    sb.append(".").append(arguments[i]);
                }
            }
        }
        return sb.toString();
    }
}
