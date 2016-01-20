package com.yuecheng.hops.account.utils;


/*
 * 文件名：ActionIntercept.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年11月14日 跟踪单号： 修改单号： 修改内容：
 */

//import junit.framework.AssertionFailedError;

import java.util.Random;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.exception.SystemException;


/**
 * @author Administrator 通过aop拦截后执行具体操作
 *         http://zhanghua.1199.blog.163.com/blog/static/464498072011111393634448/
 */
@Aspect
@Service("accountServiceExceptionIntercept")
public class AccountServiceExceptionIntercept
{
    private static Logger                        logger            = LoggerFactory.getLogger(AccountServiceExceptionIntercept.class);
    
    public static final int RETRY_TIME = 10;
    
    private static Random random = new Random(); 
            
    @Pointcut("(execution(* com.yuecheng.hops.account.service.CCYAccountService.*Action(..)))")
    private void actionMethod()
    {
        
    }

    @Pointcut("(execution(* com.yuecheng.hops.account.service.AccountRefundService.refund*(..)))")
    private void actionMethod2()
    {
        
    }
    
    /**
     * 1.保证拿到锁才能调用。没有拿到的话，就等待
     * 2.捕获异常，重发（总共5次机会）
     */
    @SuppressWarnings("deprecation")
    @Around("actionMethod()")
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable
    {
        HopsException exception = null;
        Object[] args = pjp.getArgs();
        Long accountId = (Long)args[0];
        try
        {
            Boolean processFlag =  false;
            for(int i = 0 ; i < RETRY_TIME && processFlag == false ;i++ )
            {
                try
                {
                    processFlag = (Boolean) pjp.proceed();
                }
                catch (HopsException e)
                {
                    exception = e;
                    processFlag = false;
                    logger.error("AccountServiceExceptionIntercept exception ["+i+"] times");
                    Thread.currentThread().sleep(random.nextInt(1000));
                }
            }
            if (processFlag != true)
            {
                throw exception!=null?exception:new ApplicationException("identity101081");
            }
            return processFlag;
        }
        catch(Exception e)
        {
            logger.error("Account Service operation failed caused by:"+ExceptionUtil.getStackTraceAsString(e));
            if(e instanceof HopsException)
            {
                throw new Exception(e.getMessage());
            }
            else
            {
                throw new Exception(new SystemException("identity101081", new String[]{ExceptionUtil.getStackTraceAsString(e)}, e).getMessage());
            }
        }
    }
    
    /**
     * 1.保证拿到锁才能调用。没有拿到的话，就等待
     * 2.捕获异常，重发（总共5次机会）
     */
    @SuppressWarnings("deprecation")
    @Around("actionMethod2()")
    public Object interceptor2(ProceedingJoinPoint pjp) throws Throwable
    {
        HopsException exception = null;
        Object[] args = pjp.getArgs();
        try
        {
            Boolean processFlag =  false;
            for(int i = 0 ; i < RETRY_TIME && processFlag == false ;i++ )
            {
                try
                {
                    processFlag = (Boolean) pjp.proceed();
                }
                catch (HopsException e)
                {
                    exception = e;
                    processFlag = false;
                    logger.error("AccountServiceExceptionIntercept exception ["+i+"] times");
                    Thread.currentThread().sleep(random.nextInt(1000));
                }
            }
            if (processFlag != true)
            {
                throw exception!=null?exception:new ApplicationException("identity101081");
            }
            return processFlag;
        }
        catch(Exception e)
        {
            logger.error("Account Service operation failed caused by:"+ExceptionUtil.getStackTraceAsString(e));
            if(e instanceof HopsException)
            {
                throw e;
            }
            else
            {
                throw new SystemException("identity101081", new String[]{ExceptionUtil.getStackTraceAsString(e)}, e);
            }
        }
    }
}
