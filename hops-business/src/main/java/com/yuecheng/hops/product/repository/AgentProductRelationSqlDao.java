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
import com.yuecheng.hops.product.entity.relation.AgentProductRelation;

public interface AgentProductRelationSqlDao
{
    List<AgentProductRelation> getProductRelationByParams(Map<String, Object> searchParams);
    
    /**
     * 根据条件获取数据需要加入空值
     * @param searchParams
     * @return 
     * @see
     */
    List<AgentProductRelation> getProductRelationByParams(Map<String, Object> searchParams, Long identityId, String identityType);
    
    /**
     * 根据条件进行分页查询
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @return 
     * @see
     */
    public YcPage<AgentProductRelation> queryPageAgentProductRelation(Map<String, Object> searchParams,int pageNumber,int pageSize);
}
