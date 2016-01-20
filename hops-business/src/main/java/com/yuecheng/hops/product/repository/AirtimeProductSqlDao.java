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

import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.product.entity.airtimes.AirtimeProduct;

public interface AirtimeProductSqlDao
{
    /**
     * 根据条件进行分页查询
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @return 
     * @see
     */
    public YcPage<AirtimeProduct> queryPageAirtimeProduct(Map<String, Object> searchParams,int pageNumber,int pageSize);
    
    /**
     * 根据条件查询系统产品
     *  
     * @param province 省份
     * @param parValue 面值
     * @param carrierName 运营商
     * @param city 城市
     * @return 
     * @see
     */
    public List<AirtimeProduct> fuzzyQueryAirtimeProductsByParams(String province, String parValue, String carrierName, String city,  String businessType);
}
