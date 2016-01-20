package com.yuecheng.hops.transaction.event;


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
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.event.HopsPublisher;
import com.yuecheng.hops.common.event.HopsRequestEvent;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.transaction.TransactionContextUtil;
import com.yuecheng.hops.transaction.TransactionMapKey;
import com.yuecheng.hops.transaction.TransactionResponse;


/**
 * @author Administrator 通过aop拦截后执行具体操作
 *         http://zhanghua.1199.blog.163.com/blog/static/464498072011111393634448/
 */
@Aspect
@Order(2)
@Service("transactionEventPublisher")
public class TransactionEventPublisher 
{
    @Autowired
    private HopsPublisher publisher;
    
    @Pointcut("execution(* com.yuecheng.hops.transaction.service.builder.*.doTransaction(..))")
    private void doTransaction()
    {
        
    }
    
    @SuppressWarnings("deprecation")
    @Around("doTransaction()")
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable
    {
        TransactionResponse transactionResponse = (TransactionResponse) pjp.proceed();
        HopsRequestEvent hre = (HopsRequestEvent)TransactionContextUtil.getTransactionContextParam(TransactionMapKey.HOPS_REQUEST_EVENT);
        if(BeanUtils.isNotNull(hre))
        {
            publisher.publishRequestEvent(hre);
        }
        return transactionResponse;
    }
}
