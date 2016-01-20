/*
 * 文件名：ActionUriService.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2014年10月19日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.injection.service;

import java.util.List;

import com.yuecheng.hops.injection.entity.UriTransactionMapping;

public interface UriTransactionMappingService
{
    public List<UriTransactionMapping> findAll();
    
    public UriTransactionMapping getMappingByUri(String uri);
}
