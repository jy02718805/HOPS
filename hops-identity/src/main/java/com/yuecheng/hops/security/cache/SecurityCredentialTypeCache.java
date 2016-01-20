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
import com.yuecheng.hops.security.entity.SecurityCredentialType;
import com.yuecheng.hops.security.service.SecurityRuleService;
import com.yuecheng.hops.security.service.SecurityTypeService;


/**
 * @author Administrator 通过aop拦截后执行具体操作
 *         http://zhanghua.1199.blog.163.com/blog/static/464498072011111393634448/
 */
@Aspect
@Service("securityCredentialTypeCache")
public class SecurityCredentialTypeCache
{
    
	@Autowired
	private SecurityTypeService securityTypeService;
	@Autowired
	private SecurityRuleService securityRuleService;
    
    @Pointcut("execution(* com.yuecheng.hops.security.service.SecurityTypeService.addSecurityType(..))")
    private void actionMethodSave()
    {
        
    }
    
    @Pointcut("execution(* com.yuecheng.hops.security.service.SecurityTypeService.delSecurityType(..))")
    private void actionMethodDelete()
    {
        
    }
    
    @Pointcut("execution(* com.yuecheng.hops.security.service.SecurityTypeService.editSecurityType(..))")
    private void actionMethodUpdate()
    {
        
    }
    
    @Pointcut("execution(* com.yuecheng.hops.security.service.SecurityTypeService.getSecurityType(..))")
    private void actionMethodQueryById()
    {
        
    }
    
    @Pointcut("execution(* com.yuecheng.hops.security.service.SecurityTypeService.updateSecurityTypeStatus(..))")
    private void actionMethodUpdateTypeStatus()
    {
        
    }
    
    @Pointcut("execution(* com.yuecheng.hops.security.service.SecurityTypeService.querySecurityTypeByTypeName(..))")
    private void actionMethodQueryByTypeName()
    {
        
    }

    @SuppressWarnings("deprecation")
    @Around("actionMethodSave()")
    public Object interceptorSave(ProceedingJoinPoint pjp) throws Throwable
    {
        try
        {
        	SecurityCredentialType securityType = (SecurityCredentialType) pjp.proceed();
            HopsCacheUtil.put(Constant.Common.SECURITY_CREDENTIAL_TYPE_CACHE, Constant.CacheKey.SECURITY_CREDENTIAL_TYPE+Constant.StringSplitUtil.ENCODE+securityType.getSecurityTypeId(), securityType);
            HopsCacheUtil.put(Constant.Common.SECURITY_CREDENTIAL_TYPE_CACHE, Constant.CacheKey.SECURITY_CREDENTIAL_TYPE+Constant.StringSplitUtil.ENCODE+securityType.getSecurityTypeName(), securityType);
            return securityType;
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
            String securityTypeId = String.valueOf(params[0]);
            HopsCacheUtil.remove(Constant.Common.SECURITY_CREDENTIAL_TYPE_CACHE, Constant.CacheKey.SECURITY_CREDENTIAL_TYPE+Constant.StringSplitUtil.ENCODE+securityTypeId);
            SecurityCredentialType securityType =securityTypeService.getSecurityType(new Long(securityTypeId));
            HopsCacheUtil.remove(Constant.Common.SECURITY_CREDENTIAL_TYPE_CACHE, Constant.CacheKey.SECURITY_CREDENTIAL_TYPE+Constant.StringSplitUtil.ENCODE+securityType.getSecurityTypeName());
            pjp.proceed();
            return securityTypeId;
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
        	SecurityCredentialType securityType = (SecurityCredentialType) pjp.proceed();
            HopsCacheUtil.put(Constant.Common.SECURITY_CREDENTIAL_TYPE_CACHE, Constant.CacheKey.SECURITY_CREDENTIAL_TYPE+Constant.StringSplitUtil.ENCODE+securityType.getSecurityTypeId(), securityType);
            HopsCacheUtil.put(Constant.Common.SECURITY_CREDENTIAL_TYPE_CACHE, Constant.CacheKey.SECURITY_CREDENTIAL_TYPE+Constant.StringSplitUtil.ENCODE+securityType.getSecurityTypeName(), securityType);
            return securityType;
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
        	String securityTypeId = String.valueOf(params[0]);
        	SecurityCredentialType securityType = (SecurityCredentialType)HopsCacheUtil.get(Constant.Common.SECURITY_CREDENTIAL_TYPE_CACHE, Constant.CacheKey.SECURITY_CREDENTIAL_TYPE+Constant.StringSplitUtil.ENCODE+securityTypeId);
            if(BeanUtils.isNull(securityType))
            {
            	securityType = (SecurityCredentialType)pjp.proceed();
                HopsCacheUtil.put(Constant.Common.SECURITY_CREDENTIAL_TYPE_CACHE, Constant.CacheKey.SECURITY_CREDENTIAL_TYPE+Constant.StringSplitUtil.ENCODE+securityTypeId, securityType);
            }else{
            	SecurityCredentialRule securityRule=securityType.getSecurityRule();
            	if(BeanUtils.isNotNull(securityRule))
            	{
            		securityRule=securityRuleService.querySecurityRuleById(securityRule.getSecurityRuleId());
            		securityType.setSecurityRule(securityRule);
            	}
            }
            return securityType;
        }
        catch (HopsException hopsException)
        {
            throw ExceptionUtil.throwException(hopsException);
        }
    }
    
    @SuppressWarnings("deprecation")
    @Around("actionMethodUpdateTypeStatus()")
    public Object interceptorUpdateTypeStatus(ProceedingJoinPoint pjp) throws Throwable
    {
        try
        {
        	Object[] params = pjp.getArgs();
        	String securityTypeId = String.valueOf(params[0]);
        	SecurityCredentialType securityType = (SecurityCredentialType) pjp.proceed();
            HopsCacheUtil.put(Constant.Common.SECURITY_CREDENTIAL_TYPE_CACHE, Constant.CacheKey.SECURITY_CREDENTIAL_TYPE+Constant.StringSplitUtil.ENCODE+securityTypeId, securityType);
            HopsCacheUtil.put(Constant.Common.SECURITY_CREDENTIAL_TYPE_CACHE, Constant.CacheKey.SECURITY_CREDENTIAL_TYPE+Constant.StringSplitUtil.ENCODE+securityType.getSecurityTypeName(), securityType);
            return securityType;
        }
        catch (HopsException hopsException)
        {
            throw ExceptionUtil.throwException(hopsException);
        }
    }
    
    @SuppressWarnings("deprecation")
    @Around("actionMethodQueryByTypeName()")
    public Object interceptorQueryByTypeName(ProceedingJoinPoint pjp) throws Throwable
    {
        try
        {
        	Object[] params = pjp.getArgs();
        	String securityTypeName = String.valueOf(params[0]);
        	SecurityCredentialType securityType = (SecurityCredentialType)HopsCacheUtil.get(Constant.Common.SECURITY_CREDENTIAL_TYPE_CACHE, Constant.CacheKey.SECURITY_CREDENTIAL_TYPE+Constant.StringSplitUtil.ENCODE+securityTypeName);
            if(BeanUtils.isNull(securityType))
            {
            	securityType = (SecurityCredentialType)pjp.proceed();
                HopsCacheUtil.put(Constant.Common.SECURITY_CREDENTIAL_TYPE_CACHE, Constant.CacheKey.SECURITY_CREDENTIAL_TYPE+Constant.StringSplitUtil.ENCODE+securityTypeName, securityType);
            }else{
            	SecurityCredentialRule securityRule=securityType.getSecurityRule();
            	if(BeanUtils.isNotNull(securityRule))
            	{
            		securityRule=securityRuleService.querySecurityRuleById(securityRule.getSecurityRuleId());
            		securityType.setSecurityRule(securityRule);
            	}
            }
            return securityType;
        }
        catch (HopsException hopsException)
        {
            throw ExceptionUtil.throwException(hopsException);
        }
    }
}
