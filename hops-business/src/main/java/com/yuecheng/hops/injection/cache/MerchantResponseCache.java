package com.yuecheng.hops.injection.cache;


/*
 * 文件名：ActionIntercept.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年11月14日 跟踪单号： 修改单号： 修改内容：
 */

// import junit.framework.AssertionFailedError;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.hopscache.HopsCacheUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.injection.entity.MerchantResponse;


/**
 * @author Administrator 通过aop拦截后执行具体操作
 *         http://zhanghua.1199.blog.163.com/blog/static/464498072011111393634448/
 */
@Aspect
@Service("merchantResponseCache")
public class MerchantResponseCache
{
    private static Logger logger = LoggerFactory.getLogger(MerchantResponseCache.class);

    @Pointcut("execution(* com.yuecheng.hops.injection.service.MerchantResponseService.saveMerchantResponseList(..))")
    private void actionMethodSave()
    {

    }
    
    @Pointcut("execution(* com.yuecheng.hops.injection.service.MerchantResponseService.getMerchantResponseByParams(..))")
    private void actionMethodQuery()
    {

    }
    
    @Pointcut("execution(* com.yuecheng.hops.injection.service.MerchantResponseService.getMerchantResponseListByParams(..))")
    private void actionMethodQueryList()
    {

    }
    
    @SuppressWarnings("deprecation")
    @Around("actionMethodSave()")
    public Object interceptorSave(ProceedingJoinPoint pjp)
        throws Throwable
    {
        try
        {
            Object[] args = pjp.getArgs();
            Long merchantId = (Long)args[1];
            String interfaceType = (String)args[2];
            logger.debug("remove merchantId:"+merchantId+" interfaceType:"+interfaceType);
            HopsCacheUtil.remove(Constant.Common.BUSINESS_CACHE, Constant.CacheKey.MERCHANT_RESPONSE + Constant.StringSplitUtil.ENCODE + merchantId + Constant.StringSplitUtil.ENCODE + interfaceType);
            List<MerchantResponse> merchantResponses = (List<MerchantResponse>)pjp.proceed();
            logger.debug("merchantResponses:"+merchantResponses.toString());
            HopsCacheUtil.put(Constant.Common.BUSINESS_CACHE, Constant.CacheKey.MERCHANT_RESPONSE + Constant.StringSplitUtil.ENCODE + merchantId + Constant.StringSplitUtil.ENCODE + interfaceType, merchantResponses);
            return merchantResponses;
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
            Long merchantId = (Long)args[0];
            String interfaceType = (String)args[1];
            String errorCode = BeanUtils.isNotNull(args[2]) ? (String)args[2] : StringUtil.initString();
            String merchantStatus = BeanUtils.isNotNull(args[3]) ? (String)args[3] : StringUtil.initString();
            
            MerchantResponse result = null;
            logger.debug("merchantId:"+merchantId+"  interfaceType:"+interfaceType+"  errorCode:"+ errorCode +"  merchantStatus:"+merchantStatus);
            List<MerchantResponse> merchantResponses = (List<MerchantResponse>)HopsCacheUtil.get(Constant.Common.BUSINESS_CACHE, Constant.CacheKey.MERCHANT_RESPONSE + Constant.StringSplitUtil.ENCODE + merchantId + Constant.StringSplitUtil.ENCODE + interfaceType);
            if(BeanUtils.isNotNull(merchantResponses))
            {
                for(MerchantResponse merchantResponse : merchantResponses)
                {
                    if(BeanUtils.isNotNull(merchantResponse)){
                        String tempMerchantStatus = BeanUtils.isNotNull(merchantResponse) ? merchantResponse.getMerchantStatus() : StringUtil.initString();
                        tempMerchantStatus = BeanUtils.isNotNull(tempMerchantStatus) ? tempMerchantStatus : StringUtil.initString();
                        String tempErrorCode = BeanUtils.isNotNull(merchantResponse) ? merchantResponse.getErrorCode() : StringUtil.initString();
                        tempErrorCode = BeanUtils.isNotNull(tempErrorCode) ? tempErrorCode : StringUtil.initString();
                        if(errorCode.trim().equalsIgnoreCase(tempErrorCode.trim()) && merchantStatus.trim().equalsIgnoreCase(tempMerchantStatus.trim()))
                        {
                            result = merchantResponse;
                        }
                    }else{
                        continue;
                    }
                }
            }
            else
            {
                logger.debug("merchantResponses is null!");
                result = (MerchantResponse)pjp.proceed();
            }
            if (null != result)
            {
            	logger.debug("result:"+result.toString());
            }
            return result;
        }
        catch (Exception e)
        {
            throw ExceptionUtil.throwException(e);
        }
    }
    
    @SuppressWarnings("deprecation")
    @Around("actionMethodQueryList()")
    public Object interceptorQueryList(ProceedingJoinPoint pjp)
        throws Throwable
    {
        try
        {
            Object[] args = pjp.getArgs();
            Long merchantId = (Long)args[0];
            String interfaceType = (String)args[1];
            
            List<MerchantResponse> merchantResponses = (List<MerchantResponse>)HopsCacheUtil.get(Constant.Common.BUSINESS_CACHE, Constant.CacheKey.MERCHANT_RESPONSE + Constant.StringSplitUtil.ENCODE + merchantId + Constant.StringSplitUtil.ENCODE + interfaceType);
            if(BeanUtils.isNull(merchantResponses))
            {
                logger.debug("merchantResponses is null!");
                Object obj = pjp.proceed();
                if(BeanUtils.isNotNull(obj))
                {
                    merchantResponses = (List<MerchantResponse>)obj;
                    HopsCacheUtil.put(Constant.Common.BUSINESS_CACHE, Constant.CacheKey.MERCHANT_RESPONSE + Constant.StringSplitUtil.ENCODE + merchantId + Constant.StringSplitUtil.ENCODE + interfaceType, merchantResponses);
                    logger.debug("put merchantResponses in cache!");
                }
            }
            logger.debug("merchantResponses:["+merchantResponses.toString()+"]");
            return merchantResponses;
        }
        catch (Exception e)
        {
            throw ExceptionUtil.throwException(e);
        }
    }
}
