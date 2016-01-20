package com.yuecheng.hops.security.cache;

/*
 * 文件名：ActionIntercept.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年11月14日 跟踪单号： 修改单号： 修改内容：
 */

//import junit.framework.AssertionFailedError;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.hopscache.HopsCacheUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.security.entity.SecurityCredentialRule;
import com.yuecheng.hops.security.service.SecurityRuleService;


/**
 * @author Administrator 通过aop拦截后执行具体操作
 *         http://zhanghua.1199.blog.163.com/blog/static/464498072011111393634448/
 */
@Aspect
@Service("securityCredentialRuleCache")
public class SecurityCredentialRuleCache
{
    
	@Autowired
	private SecurityRuleService securityRuleService;
    
    @Pointcut("execution(* com.yuecheng.hops.security.service.SecurityRuleService.addSecurityRule(..))")
    private void actionMethodSave()
    {
        
    }
    
    @Pointcut("execution(* com.yuecheng.hops.security.service.SecurityRuleService.delSecurityRule(..))")
    private void actionMethodDelete()
    {
        
    }
    
    @Pointcut("execution(* com.yuecheng.hops.security.service.SecurityRuleService.editSecurityRule(..))")
    private void actionMethodUpdate()
    {
        
    }
    
    @Pointcut("execution(* com.yuecheng.hops.security.service.SecurityRuleService.querySecurityRuleById(..))")
    private void actionMethodQueryById()
    {
        
    }

    @SuppressWarnings("deprecation")
    @Around("actionMethodSave()")
    public Object interceptorSave(ProceedingJoinPoint pjp) throws Throwable
    {
        try
        {
        	SecurityCredentialRule securityRule = (SecurityCredentialRule) pjp.proceed();
            HopsCacheUtil.put(Constant.Common.SECURITY_CREDENTIAL_RULE_CACHE, Constant.CacheKey.SECURITY_CREDENTIAL_RULE+Constant.StringSplitUtil.ENCODE+securityRule.getSecurityRuleId(), securityRule);
            return securityRule;
        }
        catch (HopsException hopsException)
        {
            throw ExceptionUtil.throwException(hopsException);
        }
    }
    
    @SuppressWarnings("deprecation")
    @Around("actionMethodDelete()")
    public Object interceptorDelete(ProceedingJoinPoint pjp) throws Throwable
    {
        try
        {
            Object[] params = pjp.getArgs();
            String securityRuleId = String.valueOf(params[0]);
            HopsCacheUtil.remove(Constant.Common.SECURITY_CREDENTIAL_RULE_CACHE, Constant.CacheKey.SECURITY_CREDENTIAL_RULE+Constant.StringSplitUtil.ENCODE+securityRuleId);
            pjp.proceed();
            return securityRuleId;
        }
        catch (HopsException hopsException)
        {
            throw ExceptionUtil.throwException(hopsException);
        }
    }
    
    @SuppressWarnings("deprecation")
    @Around("actionMethodUpdate()")
    public Object interceptorUpdate(ProceedingJoinPoint pjp) throws Throwable
    {
        try
        {
        	SecurityCredentialRule securityRule = (SecurityCredentialRule) pjp.proceed();
            HopsCacheUtil.put(Constant.Common.SECURITY_CREDENTIAL_RULE_CACHE, Constant.CacheKey.SECURITY_CREDENTIAL_RULE+Constant.StringSplitUtil.ENCODE+securityRule.getSecurityRuleId(), securityRule);
            return securityRule;
        }
        catch (HopsException hopsException)
        {
            throw ExceptionUtil.throwException(hopsException);
        }
    }
    
    @SuppressWarnings("deprecation")
    @Around("actionMethodQueryById()")
    public Object interceptorQueryById(ProceedingJoinPoint pjp) throws Throwable
    {
        try
        {
        	Object[] params = pjp.getArgs();
        	String securityRuleId = String.valueOf(params[0]);
        	SecurityCredentialRule securityRule = (SecurityCredentialRule)HopsCacheUtil.get(Constant.Common.SECURITY_CREDENTIAL_RULE_CACHE, Constant.CacheKey.SECURITY_CREDENTIAL_RULE+Constant.StringSplitUtil.ENCODE+securityRuleId);
            if(BeanUtils.isNull(securityRule))
            {
            	securityRule = (SecurityCredentialRule)pjp.proceed();
                HopsCacheUtil.put(Constant.Common.SECURITY_CREDENTIAL_RULE_CACHE, Constant.CacheKey.SECURITY_CREDENTIAL_RULE+Constant.StringSplitUtil.ENCODE+securityRuleId, securityRule);
            }
            return securityRule;
        }
        catch (HopsException hopsException)
        {
            throw ExceptionUtil.throwException(hopsException);
        }
    }
}
