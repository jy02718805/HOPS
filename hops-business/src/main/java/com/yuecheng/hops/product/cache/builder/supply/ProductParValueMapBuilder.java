/*
 * 文件名：ProductProvinceMapBuilder.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年1月7日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.product.cache.builder.supply;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.hopscache.HopsCacheUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.product.cache.ProductCacheConstant;
import com.yuecheng.hops.product.cache.builder.ProductPropertyCacheBuilder;
import com.yuecheng.hops.product.entity.relation.ProductRelation;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;

@Service("productParValueMapBuilder")
public class ProductParValueMapBuilder implements ProductPropertyCacheBuilder
{
    @Override
    public void putProductRelationToMap(ProductRelation pr)
    {
        SupplyProductRelation spr = (SupplyProductRelation)pr;
        Map<String,Set<SupplyProductRelation>> map = (Map<String,Set<SupplyProductRelation>>)HopsCacheUtil.get(Constant.Common.BUSINESS_CACHE, ProductCacheConstant.SUPPLY_PRODUCT_CACHE_PARVALUE_MAP);
        map = BeanUtils.isNotNull(map)?map:new HashMap<String, Set<SupplyProductRelation>>();
        Set<SupplyProductRelation> set = map.get(spr.getParValue().toString());
        set = BeanUtils.isNotNull(set)?set: new HashSet<SupplyProductRelation>();
        set.add(spr);
        map.put(spr.getParValue().toString(), set);
        HopsCacheUtil.put(Constant.Common.BUSINESS_CACHE, ProductCacheConstant.SUPPLY_PRODUCT_CACHE_PARVALUE_MAP, map);
    }

    @Override
    public void removeFromMap(ProductRelation pr)
    {
        SupplyProductRelation spr = (SupplyProductRelation)pr;
        Map<String,Set<SupplyProductRelation>> map = (Map<String,Set<SupplyProductRelation>>)HopsCacheUtil.get(Constant.Common.BUSINESS_CACHE, ProductCacheConstant.SUPPLY_PRODUCT_CACHE_PARVALUE_MAP);
        if(BeanUtils.isNotNull(map)){
            Set<SupplyProductRelation> set = map.get(spr.getParValue().toString());
            if(BeanUtils.isNotNull(set)){
                set.remove(spr);
                map.put(spr.getParValue().toString(), set);
                HopsCacheUtil.put(Constant.Common.BUSINESS_CACHE, ProductCacheConstant.SUPPLY_PRODUCT_CACHE_PARVALUE_MAP, map);
            }
        }
    }
}
