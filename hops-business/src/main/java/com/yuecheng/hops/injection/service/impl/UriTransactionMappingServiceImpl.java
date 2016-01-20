/*
 * 文件名：ActionUriServiceImpl.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2014年10月19日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.injection.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.hopscache.HopsCacheUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.injection.entity.UriTransactionMapping;
import com.yuecheng.hops.injection.repository.UriTransactionMappingDao;
import com.yuecheng.hops.injection.service.UriTransactionMappingService;

@Service("uriTransactionMappingService")
public class UriTransactionMappingServiceImpl implements UriTransactionMappingService
{
    @Autowired
    private UriTransactionMappingDao uriTransactionMappingDao;

    @Override
    public List<UriTransactionMapping> findAll()
    {
        List<UriTransactionMapping> uriTransactionMappings = (List<UriTransactionMapping>)uriTransactionMappingDao.findAll();
        return uriTransactionMappings;
    }

    @Override
    public UriTransactionMapping getMappingByUri(String uri)
    {
        UriTransactionMapping uriTransactionMapping = (UriTransactionMapping)HopsCacheUtil.get(Constant.Common.BUSINESS_CACHE, Constant.CacheKey.URI_TRANSACTION_MAPPING+uri);
        if(BeanUtils.isNull(uriTransactionMapping) ||( BeanUtils.isNotNull(uriTransactionMapping) && BeanUtils.isNull(uriTransactionMapping.getActionName()))){
            Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
            filters.put(EntityConstant.UriTransactionMapping.ACTION_NAME, new SearchFilter(
                EntityConstant.UriTransactionMapping.ACTION_NAME, Operator.EQ, uri));
            Specification<UriTransactionMapping> spec = DynamicSpecifications.bySearchFilter(
                filters.values(), UriTransactionMapping.class);
            uriTransactionMapping = uriTransactionMappingDao.findOne(spec);
            HopsCacheUtil.put(Constant.Common.BUSINESS_CACHE, Constant.CacheKey.URI_TRANSACTION_MAPPING+uri, uriTransactionMapping);
        }
        return uriTransactionMapping;
    }

}
