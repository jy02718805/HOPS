package com.yuecheng.hops.transaction.service;

import java.lang.reflect.Method;

import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Component;

/**
 */
@Aspect
@Component
public class TransactionIntercept implements MethodBeforeAdvice
{
    private static Logger logger = LoggerFactory.getLogger(TransactionIntercept.class);

    @Override
    public void before(Method method, Object[] args, Object target)
        throws Throwable
    {
        logger.debug("开始检查账户签名 methodName:" + method.getName() );
    }
}
