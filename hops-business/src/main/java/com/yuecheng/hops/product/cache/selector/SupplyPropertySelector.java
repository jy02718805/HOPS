/*
 * 文件名：SupplyPropertySelector.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年1月7日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.product.cache.selector;

import java.util.List;

import com.yuecheng.hops.product.cache.builder.ProductPropertyCacheBuilder;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;

public interface SupplyPropertySelector
{
    public List<ProductPropertyCacheBuilder> select(SupplyProductRelation supplyProductRelation);
}
