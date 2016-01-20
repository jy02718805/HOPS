/*
 * 文件名：SupplyProductRelationSqlDao.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2014年11月24日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.product.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.product.entity.airtimes.AirtimeProduct;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;

public interface SupplyProductRelationSqlDao
{
    List<SupplyProductRelation> getProductRelationByParams(Long identityId,IdentityType idenityType,List<AirtimeProduct> products,String status);
    
    List<SupplyProductRelation> querySupplyProductByProductId(List<AirtimeProduct> products,
        Long level,
        BigDecimal discount,
        BigDecimal quality);
    
    List<SupplyProductRelation> getProductRelationByParams(Map<String, Object> searchParams);
    /**
     * 
     * 根据条件获取分页查询
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @return 
     * @see
     */
    public YcPage<SupplyProductRelation> queryPageSupplyProductRelation(Map<String, Object> searchParams,int pageNumber,int pageSize);
    
    /**
     * 根据商户编号和产品匹配出产品列表
     */
    public List<SupplyProductRelation> querySupplyProductByProductId(List<AirtimeProduct> products, Long merchantId);
}
