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
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.hopscache.HopsCacheUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.identity.entity.AbstractIdentity;
import com.yuecheng.hops.identity.entity.sp.SP;


/**
 * @author Administrator 通过aop拦截后执行具体操作
 *         http://zhanghua.1199.blog.163.com/blog/static/464498072011111393634448/
 */
@Aspect
@Service("spCache")
public class SpCache
{

    @Pointcut("execution(* com.yuecheng.hops.identity.service.sp.SpService.getSP(..))")
    private void actionMethodQuery1()
    {

    }
    
    @Pointcut("execution(* com.yuecheng.hops.identity.service.IdentityService.findIdentityByIdentityId(..))")
    private void actionMethodQuery2()
    {
        
    }
    
    @SuppressWarnings("deprecation")
    @Around("actionMethodQuery1()")
    public Object interceptorQuery1(ProceedingJoinPoint pjp)
        throws Throwable
    {
        try
        {
            SP sp =  (SP)HopsCacheUtil.get(Constant.Common.IDENTITY_CACHE, Constant.CacheKey.SP);
            if(BeanUtils.isNull(sp))
            {
                sp = (SP)pjp.proceed();
                HopsCacheUtil.put(Constant.Common.IDENTITY_CACHE, Constant.CacheKey.SP, sp);
            }
            if(BeanUtils.isNull(sp.getIdentityType())){
                sp.setIdentityType(IdentityType.SP);
            }
            return sp;
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
            IdentityType identityType = (IdentityType)args[1];
            AbstractIdentity identity = null;
            if(IdentityType.SP.equals(identityType))
            {
                identity =  (SP)HopsCacheUtil.get(Constant.Common.IDENTITY_CACHE, Constant.CacheKey.SP);
                if(BeanUtils.isNull(identity))
                {
                    identity = (AbstractIdentity)pjp.proceed();
                    HopsCacheUtil.put(Constant.Common.IDENTITY_CACHE, Constant.CacheKey.SP, identity);
                }
            }else{
                identity = (AbstractIdentity)pjp.proceed();
            }
            
            return identity;
        }
        catch (HopsException hopsException)
        {
            throw ExceptionUtil.throwException(hopsException);
        }
    }
}
