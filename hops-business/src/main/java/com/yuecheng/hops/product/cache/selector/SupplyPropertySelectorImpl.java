/*
 * 文件名：PropertySelector.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年1月7日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.product.cache.selector;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.product.cache.builder.ProductPropertyCacheBuilder;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;

@Service("supplyPropertySelector")
public class SupplyPropertySelectorImpl implements SupplyPropertySelector
{
    @Autowired
    @Qualifier("productCarrierNameMapBuilder")
    private ProductPropertyCacheBuilder productCarrierNameMapBuilder;
    
    @Autowired
    @Qualifier("productCityMapBuilder")
    private ProductPropertyCacheBuilder productCityMapBuilder;
    
    @Autowired
    @Qualifier("productCountryMapBuilder")
    private ProductPropertyCacheBuilder productCountryMapBuilder;
    
    @Autowired
    @Qualifier("productParValueMapBuilder")
    private ProductPropertyCacheBuilder productParValueMapBuilder;
    
    @Autowired
    @Qualifier("productProvinceMapBuilder")
    private ProductPropertyCacheBuilder productProvinceMapBuilder;
    
    @Autowired
    @Qualifier("productQualityMapBuilder")
    private ProductPropertyCacheBuilder productQualityMapBuilder;
    
    @Autowired
    @Qualifier("productDiscountMapBuilder")
    private ProductPropertyCacheBuilder productDiscountMapBuilder;
    
    @Autowired
    @Qualifier("productMerchantLevelMapBuilder")
    private ProductPropertyCacheBuilder productMerchantLevelMapBuilder;
    
    public List<ProductPropertyCacheBuilder> select(SupplyProductRelation supplyProductRelation)
    {
        List<ProductPropertyCacheBuilder> builders = new ArrayList<ProductPropertyCacheBuilder>();
        if(StringUtil.isNotBlank(supplyProductRelation.getCarrierName()))
        {
            builders.add(productCarrierNameMapBuilder);
        }
        if(StringUtil.isNotBlank(supplyProductRelation.getProvince()))
        {
            builders.add(productProvinceMapBuilder);
        }
        if(StringUtil.isNotBlank(supplyProductRelation.getCity()))
        {
            builders.add(productCityMapBuilder);
        }
        if(StringUtil.isBlank(supplyProductRelation.getCity()) && StringUtil.isBlank(supplyProductRelation.getProvince()))
        {
            builders.add(productCountryMapBuilder);
        }
        if(StringUtil.isNotBlank(supplyProductRelation.getParValue().toString()))
        {
            builders.add(productParValueMapBuilder);
        }
        builders.add(productQualityMapBuilder);
        builders.add(productDiscountMapBuilder);
        builders.add(productMerchantLevelMapBuilder);
        return builders;
    }
}
