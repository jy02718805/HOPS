/*
 * 文件名：CacheUriMappingProcessor.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2014年10月21日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.gateway.init;

import org.springframework.beans.factory.annotation.Autowired;

import com.yuecheng.hops.injection.service.UriTransactionMappingService;

public class CacheUriMappingProcessor
{
    @Autowired
    private UriTransactionMappingService uriTransactionMappingService;
    
    /**
     * 初始化Uri interfaceType transactionCode 映射关系
     *  
     * @see
     */
    public void init()
    {
//        List<UriTransactionMapping> uriTransactionMappings = uriTransactionMappingService.findAll();
//        for (UriTransactionMapping uriTransactionMapping : uriTransactionMappings) 
//        {
//            HopsCacheUtil.put(Constant.Common.GETWAY_CACHE,uriTransactionMapping.getActionName(), uriTransactionMapping);
//        }
    }
}
