package com.yuecheng.hops.account.cache;


/*
 * 文件名：ActionIntercept.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年11月14日 跟踪单号： 修改单号： 修改内容：
 */

// import junit.framework.AssertionFailedError;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.account.entity.role.IdentityAccountRole;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.hopscache.HopsCacheUtil;
import com.yuecheng.hops.common.utils.BeanUtils;


/**
 * @author Administrator 通过aop拦截后执行具体操作
 *         http://zhanghua.1199.blog.163.com/blog/static/464498072011111393634448/
 */
@Aspect
@Service("identityAccountRoleCache")
public class IdentityAccountRoleCache
{
    private static Logger logger = LoggerFactory.getLogger(IdentityAccountRoleCache.class);

    @Pointcut("execution(* com.yuecheng.hops.account.service.IdentityAccountRoleService.getIdentityAccountRoleByParams(..))")
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
            Long accountTypeId = (Long)args[0];
            Long identityId = (Long)args[1];
            String identityType = (String)args[2];
            String relation = (String)args[3];
            Long transactionNo = (Long)args[4];
            IdentityAccountRole identityAccountRole =  (IdentityAccountRole)HopsCacheUtil.get(Constant.Common.IDENTITY_CACHE, Constant.CacheKey.ACCOUNT + Constant.StringSplitUtil.ENCODE + accountTypeId + Constant.StringSplitUtil.ENCODE + identityId + Constant.StringSplitUtil.ENCODE + identityType + Constant.StringSplitUtil.ENCODE + relation + Constant.StringSplitUtil.ENCODE + transactionNo);
            if(BeanUtils.isNull(identityAccountRole))
            {
                identityAccountRole = (IdentityAccountRole)pjp.proceed();
                HopsCacheUtil.put(Constant.Common.IDENTITY_CACHE, Constant.CacheKey.ACCOUNT + Constant.StringSplitUtil.ENCODE + accountTypeId + Constant.StringSplitUtil.ENCODE + identityId + Constant.StringSplitUtil.ENCODE + identityType + Constant.StringSplitUtil.ENCODE + relation + Constant.StringSplitUtil.ENCODE + transactionNo, identityAccountRole);
            }
            logger.debug("IdentityAccountRoleService.getIdentityAccountRoleByParams(" + (BeanUtils.isNotNull(identityAccountRole) ? String.valueOf(identityAccountRole).toString() : "NULL") + ")[返回信息]");
            return identityAccountRole;
        }
        catch (HopsException hopsException)
        {
            throw ExceptionUtil.throwException(hopsException);
        }
    }
    
}
