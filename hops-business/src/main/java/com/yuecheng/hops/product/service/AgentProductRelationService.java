package com.yuecheng.hops.product.service;


import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.product.entity.relation.AgentProductRelation;


public interface AgentProductRelationService
{

    /**
     * 查询代理商产品关系
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @param bsort
     * @return
     */
    public YcPage<AgentProductRelation> queryAgentProductRelation(Map<String, Object> searchParams,
                                                                  int pageNumber, int pageSize,
                                                                  BSort bsort);

    /**
     * 编辑下游商户产品关系表
     * 
     * @param upr
     * @return
     */
    public AgentProductRelation editAgentProductRelation(AgentProductRelation dpr,String operatorName);

    /**
     * 根据ID查询下游商户与产品关系数据
     * 
     * @param id
     * @return
     */
    public AgentProductRelation queryAgentProductRelationById(Long id);

    /**
     * 保存下游商户与产品的关系
     * 
     * @param dpr
     * @return
     */
    public AgentProductRelation doSaveAgentProductRelation(AgentProductRelation dpr, String operatorName);

    /**
     * 根据产品ID与商户ID获取下游商户产品信息
     * 
     * @param productId
     * @param merchantId
     * @return
     */
    public AgentProductRelation queryAgentProductRelationByParams(Long productId, Long merchantId,
                                                                  String status);

    /**
     * 根据ID，修改下游关系状态
     * 
     * @param id
     * @param status
     * @return
     */
    public AgentProductRelation updateAgentProductRelationStatus(Long id, String status);

    /**
     * 得到所有下游商户产品的根节点
     * 
     * @return
     */
    public List<AgentProductRelation> getAllRootAgentProductRelationService(Map<String, Object> searchParams,
                                                                            Long identityId,
                                                                            String identityType);

    /**
     * 得到所有下游商户产品
     * 
     * @return
     */
    public List<AgentProductRelation> getAllAgentProductRelationService(Map<String, Object> searchParams,
                                                                        Long identityId,
                                                                        String identityType);
    /**
     * 得到所有下游商户产品(需匹配运省城面所有字段)
     * 
     * @return
     */
    public List<AgentProductRelation> getAllAgentProductRelation(Map<String, Object> searchParams,
                                                                        Long identityId,
                                                                        String identityType);

    /**
     * 获得所有当前节点之后的节点
     * 
     * @param id
     * @return
     */
    public List<AgentProductRelation> getAfterRootAgentProductRelation(Long id);

    /**
     * 修改产品默认标示
     * 
     * @throws
     */
    public void updateDefValueByIds(String changeNodes, Long merchantId);

    /**
     * 根据产品Id获取所有配置该产品的商户产品信息
     * 
     * @param productId
     * @return
     */
    public List<AgentProductRelation> getAllAgentProductRelationByUp(Long productId);

    /**
     * newMerchant克隆oldMerchant的所有产品信息
     */
    public void cloneAgentMerchantProduct(Long newMerchantId, Long oldMerchantId, String productIds,String operatorName);

    /**
     * 根据商户ID获取此代理商所有产品关系信息
     * 
     * @param merchantId
     * @param identityType
     * @param status
     * @return
     */
    public List<AgentProductRelation> getAllProductByAgentMerchantId(Long merchantId,
                                                                     String identityType,
                                                                     String status);

    /**
     * 根据ID，删除代理商产品关系信息
     * 
     * @param id
     */
    public void deleteAgentProductRelationById(Long id, String operatorName);

    /**
     * 根据产品ID，删除与此产品ID相关的所有关系信息。
     * 
     * @param productId
     */
    public void deleteAgentProductRelationByProductId(Long productId, String operatorName);

    /**
     * 根据产品ID，状态 获取代理商产品关系信息。
     * 
     * @param productId
     * @param status
     * @return
     */
    public List<AgentProductRelation> getAgentProductRelationByParams(Long productId, String status);

    /**
     * 修改产品根节点标示
     * 
     * @param flag
     * @param productId
     */
    public void updateIsRootByProductId(boolean flag, Long productId);

    /**
     * 修改产品根节点标示
     * 
     * @param isRoot
     *            是否为父节点
     * @param isDefValue
     *            是否默认
     * @param productId
     *            产品ID
     */
    public void updateStatusByProductId(boolean isRoot, boolean isDefValue, Long productId);

    /**
     * 通过产品id修改产品名称
     * 
     * @param productId
     */
    public void updateProductNameById(Long productId, String productName);

    /**
     * 外部服务调用。用商户id获取该商户的所有产品关系
     * 
     * @return
     * @see
     */
    public List<AgentProductRelation> getProductRelationByIdentityId(Long merchantId);

    
    /**
     * 查询 (常用产品)
     * 
     * @return
     * @see
     */
    public List<AgentProductRelation> getProductRelationByParams(Map<String, Object> searchParams);
    
    /**
     * 根据外部产品ID与商户ID获取下游商户产品信息
     * 
     * @param productId
     * @param merchantId
     * @return
     */
    public  AgentProductRelation queryAgentProductRelationByParams(String agentProdId, Long merchantId,
            String status);
    /**
     * 根据条件批量修改产品关系状态
     * 
     * @param status
     * @param carrierName
     * @param province
     * @param city
     * @param identityType
     * @param identityId
     * @return 
     * @see
     */
    public int updateStatusByParamAll(String status,String carrierName, String province,String city, String identityType, Long identityId,String businessType);
}
