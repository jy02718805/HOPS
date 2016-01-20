package com.yuecheng.hops.transaction.config.product;


import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.product.entity.relation.AgentProductRelation;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;
import com.yuecheng.hops.transaction.config.entify.product.AssignExclude;


/**
 * 指定排除返回规则服务层
 * 
 * @author Jinger 2014-03-26
 */
public interface AssignExcludeService
{

    /**
     * 获取所有指定排除列表
     * 
     * @return
     */
    public List<AssignExclude> getAllAssignExclude();


    /**
     * 根据ID删除信息
     * 
     * @param assignExcludeId
     */
    public void deleteAssignExclude(Long assignExcludeId);

    /**
     * 根据ID获取信息
     * 
     * @param assignExcludeId
     * @return
     */
    public AssignExclude getAssignExcludeById(Long assignExcludeId);

    /**
     * 分页查询指定排除信息
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @param bsort
     * @return
     */
    public YcPage<AssignExclude> queryAssignExclude(Map<String, Object> searchParams,
                                                    int pageNumber, int pageSize, BSort bsort);

    /**
     * 根据条件获取指定排除列表信息
     * 
     * @param merchantType
     *            商户类型
     * @param merchantId
     *            商户ID
     * @param ruleType
     *            规则类型
     * @param productNo
     *            产品编号
     * @return
     */
    public List<AssignExclude> getAllAssignExclude(String merchantType, Long merchantId,
                                                   Long ruleType, Long productNo);
    /**
     * 根据条件获取指定排除列表信息
     * 
     * @param searchParams 条件
     * @return 
     * @see
     */
    public List<AssignExclude> getAllAssignExclude(Map<String, Object> searchParams);
    /**
     * 根据条件获取指定排除列表信息(包括父级)
     * 
     * @param searchParams 条件
     * @return 
     * @see
     */
    public List<AssignExclude> getAllAssignExcludeRelation(Map<String, Object> searchParams);

    /**
     * 获取所有配置此产品的代理商
     * 
     * @param agentProductList
     *            代理商产品列表
     * @param assignExcludeList
     * @return
     */
    public List<String[]> getAllAgentMerchant(List<AgentProductRelation> agentProductList,
                                              List<AssignExclude> assignExcludeList);

    /**
     * 获取所有配置此产品的供货商
     * 
     * @param supplyProductList
     * @param assignExcludeList
     * @return
     */
    public List<String[]> getAllSupplyMerchant(List<SupplyProductRelation> supplyProductList,
                                               List<AssignExclude> assignExcludeList);
    /**
     * 获取所有配置此商户的产品
     * 
     * @param agentProductList
     * @param assignExcludeList
     * @return 
     * @see
     */
    public List<String[]> getAllAgentProduct(List<AgentProductRelation> agentProductList,
        List<AssignExclude> assignExcludeList);
    /**
     * 获取所有配置此商户的产品
     * 
     * @param supplyProductList
     * @param assignExcludeList
     * @return 
     * @see
     */
    public List<String[]> getAllSupplyProduct(List<SupplyProductRelation> supplyProductList,
        List<AssignExclude> assignExcludeList);
    /**
     * 批量保存指定排除信息
     * 
     * @param assignExcludeList
     *            待保存的指定排除信息
     * @return
     */
    public List<AssignExclude> saveAssignExcludeList(List<AssignExclude> assignExcludeList);

    /**
     * 根据条件删除规则信息
     * 
     * @param merchantType
     *            商户类型
     * @param merchantId
     *            商户ID
     * @param ruleType
     *            规则类型（指定、排除）
     * @param productNo
     *            产品编号
     */
    public void deleteAssignExcludeList(String merchantType, Long merchantId, Long ruleType,
                                        Long productNo);
    /**
     * 批量删除指定排除列表
     * 
     * @param assignExcludeList 
     * @see
     */
    public void deleteAssignExcludeList(List<AssignExclude> assignExcludeList);

    /**
     * 根据条件查询指定排除列表
     * 
     * @param merchantId
     *            商户ID
     * @param rule
     *            规则类型（指定、排除）
     * @return
     */
    public List<AssignExclude> findAssignExcludeByParams(Long merchantId, String rule);
}
