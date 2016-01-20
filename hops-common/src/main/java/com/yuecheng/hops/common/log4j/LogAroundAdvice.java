package com.yuecheng.hops.common.log4j;


import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Aspect
public class LogAroundAdvice
{
	private static final Logger logger = LoggerFactory.getLogger(LogAroundAdvice.class);
    
    public Object invoke(ProceedingJoinPoint pjp)
        throws Throwable
    {
        logger.info("begin enter advice:" + pjp.getSignature().getName());
        MethodSignature joinPointObject = (MethodSignature)pjp.getSignature();
        Method method = joinPointObject.getMethod();
        boolean flag = method.isAnnotationPresent(LogAnnotation.class);
        if (flag)
        {
            LogAnnotation logAction = method.getAnnotation(LogAnnotation.class);
            String methodName = logAction.methodname();
            String serviceName = logAction.servicename();
            String description = logAction.description();
            logger.info("Annotation:LogAction ServiceName:" + serviceName + " methodName:"
                               + methodName + " description:" + description);
        }

        long time = System.currentTimeMillis();
        Object retVal = pjp.proceed();
        time = System.currentTimeMillis() - time;
        logger.info("process time: " + time + " ms");
        logger.info("end of advice:" + pjp.getSignature().getName());
        return retVal;
    }

}
