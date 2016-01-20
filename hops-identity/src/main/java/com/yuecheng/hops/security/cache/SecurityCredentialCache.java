package com.yuecheng.hops.security.cache;


/*
 * 文件名：ActionIntercept.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年11月14日 跟踪单号： 修改单号： 修改内容：
 */

// import junit.framework.AssertionFailedError;

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
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.security.entity.SecurityCredential;
import com.yuecheng.hops.security.service.SecurityCredentialManagerService;
import com.yuecheng.hops.security.service.SecurityCredentialService;
import com.yuecheng.hops.security.service.SecurityTypeService;


/**
 * @author Administrator 通过aop拦截后执行具体操作
 *         http://zhanghua.1199.blog.163.com/blog/static/464498072011111393634448/
 */
@Aspect
@Service("securityCredentialCache")
public class SecurityCredentialCache
{

    @Autowired
    private SecurityCredentialService securityCredentialService;

    @Autowired
    private SecurityTypeService securityTypeService;

    @Autowired
    private SecurityCredentialManagerService securityCredentialManagerService;

    @Pointcut("execution(* com.yuecheng.hops.security.service.SecurityCredentialService.saveSecurityCredential(..))")
    private void actionMethodSave()
    {

    }

    @Pointcut("execution(* com.yuecheng.hops.security.service.SecurityCredentialService.deleteSecurityCredential(..))")
    private void actionMethodDelete()
    {

    }

    @Pointcut("execution(* com.yuecheng.hops.security.service.SecurityCredentialService.updateSecurityCredential(..))")
    private void actionMethodUpdate()
    {

    }

    @Pointcut("execution(* com.yuecheng.hops.security.service.SecurityCredentialService.querySecurityCredentialById(..))")
    private void actionMethodQueryById()
    {

    }

    @Pointcut("execution(* com.yuecheng.hops.security.service.SecurityCredentialService.querySecurityCredentialByParam(..))")
    private void actionMethodQueryByParam()
    {

    }

    @Pointcut("execution(* com.yuecheng.hops.security.service.SecurityCredentialService.querySecurityCredentialValueByParams(..))")
    private void actionMethodQueryValue()
    {

    }

    @SuppressWarnings("deprecation")
    @Around("actionMethodSave()")
    public Object interceptorSave(ProceedingJoinPoint pjp)
        throws Throwable
    {
        try
        {
            SecurityCredential securityCredential = (SecurityCredential)pjp.proceed();
            if (BeanUtils.isNotNull(securityCredential))
            {
                Long securityCredentialId = securityCredential.getSecurityId();
                Long identityId = securityCredential.getIdentityId();
                String securityTypeName = securityCredential.getSecurityType().getSecurityTypeName();
                HopsCacheUtil.put(Constant.Common.SECURITY_CREDENTIAL_CACHE, Constant.CacheKey.SECURITY_CREDENTIAL + Constant.StringSplitUtil.ENCODE + securityCredentialId, securityCredential);
                HopsCacheUtil.put(Constant.Common.SECURITY_CREDENTIAL_CACHE, Constant.CacheKey.SECURITY_CREDENTIAL + Constant.StringSplitUtil.ENCODE + identityId + Constant.StringSplitUtil.ENCODE + securityTypeName,
                    securityCredential);
            }
            return securityCredential;
        }
        catch (HopsException hopsException)
        {
            throw ExceptionUtil.throwException(hopsException);
        }
    }

    @SuppressWarnings("deprecation")
    @Around("actionMethodDelete()")
    public Object interceptorDelete(ProceedingJoinPoint pjp)
        throws Throwable
    {
        try
        {
            Object[] params = pjp.getArgs();
            String securityCredentialId = String.valueOf(params[0]);
            SecurityCredential securityCredential = (SecurityCredential)HopsCacheUtil.get(Constant.Common.SECURITY_CREDENTIAL_CACHE, Constant.CacheKey.SECURITY_CREDENTIAL + Constant.StringSplitUtil.ENCODE + securityCredentialId);
            if (BeanUtils.isNotNull(securityCredential))
            {
                Long identityId = securityCredential.getIdentityId();
                String securityTypeName = securityCredential.getSecurityType().getSecurityTypeName();
                HopsCacheUtil.remove(Constant.Common.SECURITY_CREDENTIAL_CACHE, Constant.CacheKey.SECURITY_CREDENTIAL + Constant.StringSplitUtil.ENCODE + identityId + Constant.StringSplitUtil.ENCODE + securityTypeName);
                HopsCacheUtil.remove(Constant.Common.SECURITY_CREDENTIAL_CACHE,Constant.CacheKey.SECURITY_CREDENTIAL + Constant.StringSplitUtil.ENCODE + securityCredentialId);
            }
            securityCredentialId = (String)pjp.proceed();
            return securityCredentialId;
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
            SecurityCredential securityCredential = (SecurityCredential)pjp.proceed();
            if (BeanUtils.isNotNull(securityCredential))
            {
                Long securityCredentialId = securityCredential.getSecurityId();
                Long identityId = securityCredential.getIdentityId();
                String securityTypeName = securityCredential.getSecurityType().getSecurityTypeName();
                HopsCacheUtil.put(Constant.Common.SECURITY_CREDENTIAL_CACHE, Constant.CacheKey.SECURITY_CREDENTIAL + Constant.StringSplitUtil.ENCODE + securityCredentialId, securityCredential);
                HopsCacheUtil.put(Constant.Common.SECURITY_CREDENTIAL_CACHE, Constant.CacheKey.SECURITY_CREDENTIAL + Constant.StringSplitUtil.ENCODE + identityId + Constant.StringSplitUtil.ENCODE + securityTypeName,
                    securityCredential);
            }
            return securityCredential;
        }
        catch (HopsException hopsException)
        {
            throw ExceptionUtil.throwException(hopsException);
        }
    }

    @SuppressWarnings("deprecation")
    @Around("actionMethodQueryById()")
    public Object interceptorQueryById(ProceedingJoinPoint pjp)
        throws Throwable
    {
        try
        {
            Object[] params = pjp.getArgs();
            String securityCredentialId = String.valueOf(params[0]);
            SecurityCredential securityCredential = (SecurityCredential)HopsCacheUtil.get(
                Constant.Common.SECURITY_CREDENTIAL_CACHE, Constant.CacheKey.SECURITY_CREDENTIAL + Constant.StringSplitUtil.ENCODE + securityCredentialId);
            if (BeanUtils.isNull(securityCredential))
            {
                securityCredential = (SecurityCredential)pjp.proceed();
                if (BeanUtils.isNotNull(securityCredential)&&!Constant.SecurityCredentialStatus.DELETE_STATUS.equals(securityCredential.getStatus()))
                {
                    Long identityId = securityCredential.getIdentityId();
                    String securityTypeName = securityCredential.getSecurityType().getSecurityTypeName();
                    HopsCacheUtil.put(Constant.Common.SECURITY_CREDENTIAL_CACHE, Constant.CacheKey.SECURITY_CREDENTIAL + Constant.StringSplitUtil.ENCODE + identityId + Constant.StringSplitUtil.ENCODE + securityTypeName, securityCredential);
                    HopsCacheUtil.put(Constant.Common.SECURITY_CREDENTIAL_CACHE, Constant.CacheKey.SECURITY_CREDENTIAL + Constant.StringSplitUtil.ENCODE
                            + securityCredentialId, securityCredential);

                }
            }
            return securityCredential;
        }
        catch (HopsException hopsException)
        {
            throw ExceptionUtil.throwException(hopsException);
        }
    }

    @SuppressWarnings("deprecation")
    @Around("actionMethodQueryByParam()")
    public Object interceptorQueryByParam(ProceedingJoinPoint pjp)
        throws Throwable
    {
        try
        {
            Object[] params = pjp.getArgs();
            String identityId = String.valueOf(params[0]);
            String securityTypeName = String.valueOf(params[2]);
            SecurityCredential securityCredential = (SecurityCredential)HopsCacheUtil.get(
                Constant.Common.SECURITY_CREDENTIAL_CACHE, Constant.CacheKey.SECURITY_CREDENTIAL + Constant.StringSplitUtil.ENCODE + identityId + Constant.StringSplitUtil.ENCODE + securityTypeName);
            if (BeanUtils.isNull(securityCredential))
            {
                securityCredential = (SecurityCredential)pjp.proceed();
                if (BeanUtils.isNotNull(securityCredential))
                {
                    String securityCredentialId = String.valueOf(securityCredential.getSecurityId());
                    HopsCacheUtil.put(Constant.Common.SECURITY_CREDENTIAL_CACHE,
                        Constant.CacheKey.SECURITY_CREDENTIAL + Constant.StringSplitUtil.ENCODE + identityId + Constant.StringSplitUtil.ENCODE + securityTypeName, securityCredential);
                    HopsCacheUtil.put(Constant.Common.SECURITY_CREDENTIAL_CACHE,
                        Constant.CacheKey.SECURITY_CREDENTIAL + Constant.StringSplitUtil.ENCODE + securityCredentialId, securityCredential);
                }
            }
            return securityCredential;
        }
        catch (HopsException hopsException)
        {
            throw ExceptionUtil.throwException(hopsException);
        }
    }

    @SuppressWarnings("deprecation")
    @Around("actionMethodQueryValue()")
    public Object interceptorQueryValue(ProceedingJoinPoint pjp)
        throws Throwable
    {
        try
        {
            Object[] params = pjp.getArgs();
            String securityCredentialValue = StringUtil.initString();
            String identityId = String.valueOf(params[0]);
            String securityTypeName = String.valueOf(params[2]);
            SecurityCredential securityCredential = (SecurityCredential)HopsCacheUtil.get(
                Constant.Common.SECURITY_CREDENTIAL_CACHE, Constant.CacheKey.SECURITY_CREDENTIAL + Constant.StringSplitUtil.ENCODE + identityId + Constant.StringSplitUtil.ENCODE + securityTypeName);
            if (BeanUtils.isNull(securityCredential))
            {
                securityCredentialValue = (String)pjp.proceed();
            }
            else
            {
                if (Constant.SecurityCredentialStatus.ENABLE_STATUS.equals(securityCredential.getStatus()))
                {
                    securityCredentialValue = securityCredentialManagerService.decryptKeyBySecurityId(securityCredential.getSecurityId());
                }

            }
            return securityCredentialValue;
        }
        catch (HopsException hopsException)
        {
            throw ExceptionUtil.throwException(hopsException);
        }
    }
}
