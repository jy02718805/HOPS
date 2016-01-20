package com.yuecheng.hops.injection.cache;


/*
 * 文件名：ActionIntercept.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年11月14日 跟踪单号： 修改单号： 修改内容：
 */

// import junit.framework.AssertionFailedError;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.hopscache.HopsCacheUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.injection.entity.InterfacePacketsDefinition;
import com.yuecheng.hops.injection.entity.InterfacePacketsDefinitionBo;


/**
 * @author Administrator 通过aop拦截后执行具体操作
 *         http://zhanghua.1199.blog.163.com/blog/static/464498072011111393634448/
 */
@Aspect
@Service("interfacePacketsDefinitionCache")
public class InterfacePacketsDefinitionCache
{

    public static final int RETRY_TIME = 5;

    @Pointcut("execution(*  com.yuecheng.hops.injection.service.InterfaceService.updateInterfacePacketsDefinition*(..))")
    private void actionMethodUpdate()
    {

    }
    
    @Pointcut("execution(*  com.yuecheng.hops.injection.service.InterfaceService.getInterfacePacketsDefinitionByParams(..))")
    private void actionMethodQuery()
    {

    }
    
    @Pointcut("execution(*  com.yuecheng.hops.injection.service.InterfaceService.checkInterfaceIsExist(..))")
    private void actionMethodCheck()
    {

    }

    @SuppressWarnings("deprecation")
    @Around("actionMethodUpdate()")
    public Object interceptorUpdate(ProceedingJoinPoint pjp)
        throws Throwable
    {
        try
        {
            InterfacePacketsDefinition ipd = (InterfacePacketsDefinition)pjp.proceed();
            InterfacePacketsDefinitionBo ipdb = new InterfacePacketsDefinitionBo();
            BeanUtils.copyProperties(ipdb, ipd);
            ipdb.setRequestInterfacePacketTypeConf(ipd.getRequestInterfacePacketTypeConf());
            ipdb.setResponseInterfacePacketTypeConf(ipd.getResponseInterfacePacketTypeConf());
            ipdb.setRequestParams(ipd.getRequestParams());
            ipdb.setResponseSuccessParams(ipd.getResponseParams());
            ipdb.setResponseFailParams(null);
            if(Constant.Interface.OPEN.equalsIgnoreCase(ipdb.getStatus())){
                HopsCacheUtil.put(Constant.Common.BUSINESS_CACHE, Constant.CacheKey.INTERFACE_PACKET_DEFINITION + Constant.StringSplitUtil.ENCODE + ipdb.getMerchantId()+Constant.StringSplitUtil.ENCODE+ipdb.getInterfaceType(), ipdb);
            }else{
                HopsCacheUtil.remove(Constant.Common.BUSINESS_CACHE, Constant.CacheKey.INTERFACE_PACKET_DEFINITION + Constant.StringSplitUtil.ENCODE + ipdb.getMerchantId()+Constant.StringSplitUtil.ENCODE+ipdb.getInterfaceType());
            }
            return ipd;
        }
        catch (Exception e)
        {
            throw ExceptionUtil.throwException(e);
        }
    }
    
    @SuppressWarnings("deprecation")
    @Around("actionMethodQuery()")
    public Object interceptorQuery(ProceedingJoinPoint pjp)
        throws Throwable
    {
        try
        {
            Object[] args = pjp.getArgs();
            Long merchantId = BeanUtils.isNotNull(args[0])?(Long)args[0]:0l;
            String interfaceType = (String)args[1];
            InterfacePacketsDefinitionBo ipdb = (InterfacePacketsDefinitionBo)HopsCacheUtil.get(Constant.Common.BUSINESS_CACHE, Constant.CacheKey.INTERFACE_PACKET_DEFINITION + Constant.StringSplitUtil.ENCODE + merchantId + Constant.StringSplitUtil.ENCODE + interfaceType);
            if(BeanUtils.isNull(ipdb))
            {
                ipdb = (InterfacePacketsDefinitionBo)pjp.proceed();
                HopsCacheUtil.put(Constant.Common.BUSINESS_CACHE, Constant.CacheKey.INTERFACE_PACKET_DEFINITION + Constant.StringSplitUtil.ENCODE + ipdb.getMerchantId()+Constant.StringSplitUtil.ENCODE+ipdb.getInterfaceType(), ipdb);
            }
            return ipdb;
        }
        catch (Exception e)
        {
            throw ExceptionUtil.throwException(e);
        }
    }
    
    @SuppressWarnings("deprecation")
    @Around("actionMethodCheck()")
    public Object interceptorValidateCheck(ProceedingJoinPoint pjp)
        throws Throwable
    {
        try
        {
            Object[] args = pjp.getArgs();
            Long merchantId = BeanUtils.isNotNull(args[0])?(Long)args[0]:0l;
            String interfaceType = (String)args[1];
            InterfacePacketsDefinitionBo ipdb = (InterfacePacketsDefinitionBo)HopsCacheUtil.get(Constant.Common.BUSINESS_CACHE, Constant.CacheKey.INTERFACE_PACKET_DEFINITION + Constant.StringSplitUtil.ENCODE + merchantId + Constant.StringSplitUtil.ENCODE + interfaceType);
            boolean checkFlag = false;
            if(BeanUtils.isNull(ipdb))
            {
                checkFlag = (Boolean)pjp.proceed();
            }
            else
            {
                checkFlag = true;
            }
            return checkFlag;
        }
        catch (Exception e)
        {
            throw ExceptionUtil.throwException(e);
        }
    }
}
