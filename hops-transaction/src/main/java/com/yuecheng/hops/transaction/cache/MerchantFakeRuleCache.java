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
import com.yuecheng.hops.transaction.config.entify.fake.AgentQueryFakeRule;


/**
 * @author Administrator 通过aop拦截后执行具体操作
 *         http://zhanghua.1199.blog.163.com/blog/static/464498072011111393634448/
 */
@Aspect
@Service("merchantFakeRuleCache")
public class MerchantFakeRuleCache
{
    
    public static final int RETRY_TIME = 5;
      
    
    @Pointcut("execution(* com.yuecheng.hops.transaction.config.AgentQueryFakeRuleService.save(..))")
    private void actionMethodSave()
    {
        
    }
    
    @Pointcut("execution(* com.yuecheng.hops.transaction.config.AgentQueryFakeRuleService.delete*(..))")
    private void actionMethodDelete()
    {
        
    }
    
    @Pointcut("execution(* com.yuecheng.hops.transaction.config.AgentQueryFakeRuleService.queryAgentQueryFakeRuleById(..))")
    private void actionMethodQuery()
    {
        
    }

    @SuppressWarnings("deprecation")
    @Around("actionMethodSave()")
    public Object interceptorSave(ProceedingJoinPoint pjp) throws Throwable
    {
        try
        {
            AgentQueryFakeRule agentFakeRule = (AgentQueryFakeRule) pjp.proceed();
            HopsCacheUtil.put(Constant.Common.TRANSACTION_CACHE, Constant.CacheKey.FAKE_RULE+Constant.StringSplitUtil.ENCODE+agentFakeRule.getMerchantId(), agentFakeRule);
            return agentFakeRule;
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
            Long merchantId = (Long) pjp.proceed();
            HopsCacheUtil.remove(Constant.Common.TRANSACTION_CACHE, Constant.CacheKey.FAKE_RULE+Constant.StringSplitUtil.ENCODE+merchantId);
            return merchantId;
        }
        catch (HopsException hopsException)
        {
            throw ExceptionUtil.throwException(hopsException);
        }
    }
    
    @SuppressWarnings("deprecation")
    @Around("actionMethodQuery()")
    public Object interceptorQuery(ProceedingJoinPoint pjp) throws Throwable
    {
        try
        {
            Object[] params = pjp.getArgs();
            Long merchantId = (Long)params[0];
            AgentQueryFakeRule agentQueryFakeRule = (AgentQueryFakeRule)HopsCacheUtil.get(Constant.Common.TRANSACTION_CACHE, Constant.CacheKey.FAKE_RULE+Constant.StringSplitUtil.ENCODE+merchantId);
            if(BeanUtils.isNull(agentQueryFakeRule))
            {
                agentQueryFakeRule = (AgentQueryFakeRule)pjp.proceed();
                HopsCacheUtil.put(Constant.Common.TRANSACTION_CACHE, Constant.CacheKey.FAKE_RULE+Constant.StringSplitUtil.ENCODE+merchantId, agentQueryFakeRule);
            }
            return agentQueryFakeRule;
        }
        catch (HopsException hopsException)
        {
            throw ExceptionUtil.throwException(hopsException);
        }
    }
}
