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


/**
 * @author Administrator 通过aop拦截后执行具体操作
 *         http://zhanghua.1199.blog.163.com/blog/static/464498072011111393634448/
 */
@Aspect
@Service("statusTransferCache")
public class StatusTransferCache
{
    
    public static final String ORDER_STATUS_TRANSFER = "order_status_transfer";

    public static final String DELIVERY_STATUS_TRANSFER = "delivery_status_transfer";
    
    public static final String NOTIFY_STATUS_TRANSFER = "notify_status_transfer";
    
    public static final String QUERY_STATUS_TRANSFER = "query_status_transfer";
    
    @Pointcut("execution(* com.yuecheng.hops.transaction.execution.status.OrderStatusTransferService.checkStatus(..))")
    private void orderStatusCheck()
    {
        
    }
    
    @Pointcut("execution(* com.yuecheng.hops.transaction.execution.status.DeliveryStatusTransferService.checkStatus(..))")
    private void deliveryStatusCheck()
    {
        
    }

    @Pointcut("execution(* com.yuecheng.hops.transaction.execution.status.NotifyStatusTransferService.checkStatus(..))")
    private void notifyStatusCheck()
    {
        
    }
    
    @Pointcut("execution(* com.yuecheng.hops.transaction.execution.status.QueryStatusTransferService.checkStatus(..))")
    private void queryStatusCheck()
    {
        
    }
    
    @SuppressWarnings("deprecation")
    @Around("orderStatusCheck()")
    public Object interceptorOrderStatusCheck(ProceedingJoinPoint pjp) throws Throwable
    {
        try
        {
            Object[] params = pjp.getArgs();
            Integer targetStatus = (Integer)params[0];
            Integer originalStatus = (Integer)params[1];
            Boolean flag = (Boolean)HopsCacheUtil.get(Constant.Common.TRANSACTION_CACHE, ORDER_STATUS_TRANSFER+Constant.StringSplitUtil.ENCODE+targetStatus+Constant.StringSplitUtil.ENCODE+originalStatus);
            if(BeanUtils.isNull(flag))
            {
                flag = (Boolean)pjp.proceed();
                HopsCacheUtil.put(Constant.Common.TRANSACTION_CACHE, ORDER_STATUS_TRANSFER+Constant.StringSplitUtil.ENCODE+targetStatus+Constant.StringSplitUtil.ENCODE+originalStatus, flag);
            }
            return flag;
        }
        catch (HopsException hopsException)
        {
            throw ExceptionUtil.throwException(hopsException);
        }
    }
    
    @SuppressWarnings("deprecation")
    @Around("deliveryStatusCheck()")
    public Object interceptorDeliveryStatusCheck(ProceedingJoinPoint pjp) throws Throwable
    {
        try
        {
            Object[] params = pjp.getArgs();
            Integer targetStatus = (Integer)params[0];
            Integer originalStatus = (Integer)params[1];
            Boolean flag = (Boolean)HopsCacheUtil.get(Constant.Common.TRANSACTION_CACHE, DELIVERY_STATUS_TRANSFER+Constant.StringSplitUtil.ENCODE+targetStatus+Constant.StringSplitUtil.ENCODE+originalStatus);
            if(BeanUtils.isNull(flag))
            {
                flag = (Boolean)pjp.proceed();
                HopsCacheUtil.put(Constant.Common.TRANSACTION_CACHE, DELIVERY_STATUS_TRANSFER+Constant.StringSplitUtil.ENCODE+targetStatus+Constant.StringSplitUtil.ENCODE+originalStatus, flag);
            }
            return flag;
        }
        catch (HopsException hopsException)
        {
            throw ExceptionUtil.throwException(hopsException);
        }
    }
    
    @SuppressWarnings("deprecation")
    @Around("notifyStatusCheck()")
    public Object interceptorNotifyStatusCheck(ProceedingJoinPoint pjp) throws Throwable
    {
        try
        {
            Object[] params = pjp.getArgs();
            Integer targetStatus = (Integer)params[0];
            Integer originalStatus = (Integer)params[1];
            Boolean flag = (Boolean)HopsCacheUtil.get(Constant.Common.TRANSACTION_CACHE, NOTIFY_STATUS_TRANSFER+Constant.StringSplitUtil.ENCODE+targetStatus+Constant.StringSplitUtil.ENCODE+originalStatus);
            if(BeanUtils.isNull(flag))
            {
                flag = (Boolean)pjp.proceed();
                HopsCacheUtil.put(Constant.Common.TRANSACTION_CACHE, NOTIFY_STATUS_TRANSFER+Constant.StringSplitUtil.ENCODE+targetStatus+Constant.StringSplitUtil.ENCODE+originalStatus, flag);
            }
            return flag;
        }
        catch (HopsException hopsException)
        {
            throw ExceptionUtil.throwException(hopsException);
        }
    }
    
    @SuppressWarnings("deprecation")
    @Around("queryStatusCheck()")
    public Object interceptorQueryStatusCheck(ProceedingJoinPoint pjp) throws Throwable
    {
        try
        {
            Object[] params = pjp.getArgs();
            Integer targetStatus = (Integer)params[0];
            Integer originalStatus = (Integer)params[1];
            Boolean flag = (Boolean)HopsCacheUtil.get(Constant.Common.TRANSACTION_CACHE, QUERY_STATUS_TRANSFER+Constant.StringSplitUtil.ENCODE+targetStatus+Constant.StringSplitUtil.ENCODE+originalStatus);
            if(BeanUtils.isNull(flag))
            {
                flag = (Boolean)pjp.proceed();
                HopsCacheUtil.put(Constant.Common.TRANSACTION_CACHE, QUERY_STATUS_TRANSFER+Constant.StringSplitUtil.ENCODE+targetStatus+Constant.StringSplitUtil.ENCODE+originalStatus, flag);
            }
            return flag;
        }
        catch (HopsException hopsException)
        {
            throw ExceptionUtil.throwException(hopsException);
        }
    }
}
