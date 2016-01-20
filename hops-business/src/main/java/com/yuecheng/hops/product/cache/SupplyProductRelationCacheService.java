/*
 * 文件名：SupplyProductRelationCacheService.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年1月7日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.product.cache;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.product.cache.builder.ProductPropertyCacheBuilder;
import com.yuecheng.hops.product.cache.selector.SupplyPropertySelector;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;
import com.yuecheng.hops.product.service.SupplyProductRelationService;

/**
 * 供货商产品关系缓存服务
 * 缓存信息在下单找供货商时使用
 * @author Administrator
 * @version 2015年1月7日
 * @see SupplyProductRelationCacheService
 * @since
 */
@Aspect
public class SupplyProductRelationCacheService
{
    @Autowired
    private SupplyProductRelationService supplyProductRelationService;
    
    @Autowired
    private SupplyPropertySelector supplyPropertySelector;
    
    public void initCache()
    {
        List<SupplyProductRelation> supplyProductRelationList = supplyProductRelationService.getAllOpenProductRelation();
        for(SupplyProductRelation supplyProductRelation : supplyProductRelationList)
        {
            List<ProductPropertyCacheBuilder> builders = supplyPropertySelector.select(supplyProductRelation);
            for(ProductPropertyCacheBuilder builder : builders){
                builder.putProductRelationToMap(supplyProductRelation);
            }
        }
    }
    
    @Pointcut("execution(*  com.yuecheng.hops.product.service.SupplyProductRelationService.updateSupplyProductRelationStatus(..))")
    private void actionMethodUpdate()
    {

    }
    
    @Pointcut("execution(*  com.yuecheng.hops.product.service.SupplyProductRelationService.editSupplyProductRelation(..))")
    private void actionMethodEdit()
    {

    }
    
    @Pointcut("execution(*  com.yuecheng.hops.product.service.SupplyProductRelationService.deleteSupplyProductRelationById(..))")
    private void actionMethodDelete()
    {

    }

    @Pointcut("execution(*  com.yuecheng.hops.product.service.SupplyProductRelationService.deleteSupplyProductRelationByProductId(..))")
    private void actionMethodDeleteAll()
    {

    }
    
    @SuppressWarnings("deprecation")
    @Around("actionMethodUpdate()")
    public Object interceptorUpdate(ProceedingJoinPoint pjp)
        throws Throwable
    {
        try
        {
            Object[] params = pjp.getArgs();
            String status = (String)params[1];
            SupplyProductRelation supplyProductRelation = (SupplyProductRelation)pjp.proceed();
            if(Constant.SupplyProductStatus.OPEN_STATUS.equalsIgnoreCase(status)){
                List<ProductPropertyCacheBuilder> builders = supplyPropertySelector.select(supplyProductRelation);
                for(ProductPropertyCacheBuilder builder : builders){
                    builder.putProductRelationToMap(supplyProductRelation);
                }
            }else if(Constant.SupplyProductStatus.CLOSE_STATUS.equalsIgnoreCase(status)){
                List<ProductPropertyCacheBuilder> builders = supplyPropertySelector.select(supplyProductRelation);
                for(ProductPropertyCacheBuilder builder : builders){
                    builder.removeFromMap(supplyProductRelation);
                }
            }
            
            return supplyProductRelation;
        }
        catch (HopsException hopsException)
        {
            throw ExceptionUtil.throwException(hopsException);
        }
    }
    
    @SuppressWarnings("deprecation")
    @Around("actionMethodEdit()")
    public Object interceptorEdit(ProceedingJoinPoint pjp)
        throws Throwable
    {
        try
        {
            Object[] params = pjp.getArgs();
            SupplyProductRelation supplyProductRelation = (SupplyProductRelation)params[0];
            if(Constant.SupplyProductStatus.OPEN_STATUS.equalsIgnoreCase(supplyProductRelation.getStatus())){
                List<ProductPropertyCacheBuilder> builders = supplyPropertySelector.select(supplyProductRelation);
                for(ProductPropertyCacheBuilder builder : builders){
                    builder.putProductRelationToMap(supplyProductRelation);
                }
            }else if(Constant.SupplyProductStatus.CLOSE_STATUS.equalsIgnoreCase(supplyProductRelation.getStatus())){
                List<ProductPropertyCacheBuilder> builders = supplyPropertySelector.select(supplyProductRelation);
                for(ProductPropertyCacheBuilder builder : builders){
                    builder.removeFromMap(supplyProductRelation);
                }
            }
            Object obj = pjp.proceed();
            return obj;
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
            Long id =  (Long)params[0];
            SupplyProductRelation supplyProductRelation = supplyProductRelationService.querySupplyProductRelationById(id);
            List<ProductPropertyCacheBuilder> builders = supplyPropertySelector.select(supplyProductRelation);
            for(ProductPropertyCacheBuilder builder : builders){
                builder.removeFromMap(supplyProductRelation);
            }
            Object obj = pjp.proceed();
            return obj;
        }
        catch (HopsException hopsException)
        {
            throw ExceptionUtil.throwException(hopsException);
        }
    }
    
    @SuppressWarnings("deprecation")
    @Around("actionMethodDeleteAll()")
    public Object interceptorDeleteAll(ProceedingJoinPoint pjp)
        throws Throwable
    {
        try
        {
            Object[] params = pjp.getArgs();
            Long productId =  (Long)params[0];
            
            List<SupplyProductRelation> supplyProductRelations = supplyProductRelationService.getAllProductRelationByProductId(productId);
            for(SupplyProductRelation supplyProductRelation : supplyProductRelations){
                List<ProductPropertyCacheBuilder> builders = supplyPropertySelector.select(supplyProductRelation);
                for(ProductPropertyCacheBuilder builder : builders){
                    builder.removeFromMap(supplyProductRelation);
                }
            }
            
            Object obj = pjp.proceed();
            return obj;
        }
        catch (HopsException hopsException)
        {
            throw ExceptionUtil.throwException(hopsException);
        }
    }
}
