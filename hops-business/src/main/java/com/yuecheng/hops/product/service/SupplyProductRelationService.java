package com.yuecheng.hops.product.service;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.numsection.entity.NumSection;
import com.yuecheng.hops.product.entity.airtimes.AirtimeProduct;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;


public interface SupplyProductRelationService
{
    public YcPage<SupplyProductRelation> querySupplyProductRelation(Map<String, Object> searchParams,
                                                                    int pageNumber, int pageSize,
                                                                    BSort bsort);

    /**
     * 编辑上游商户产品关系表
     * 
     * @param Supplyr
     * @return
     */
    public SupplyProductRelation editSupplyProductRelation(SupplyProductRelation Supplyr, String operatorName);

    /**
     * 根据ID查询上游商户与产品关系数据
     * 
     * @param id
     * @return
     */
    public SupplyProductRelation querySupplyProductRelationById(Long id);

    /**
     * 保存上游商户与产品的关系
     * 
     * @param Supplyr
     * @return
     */
    public SupplyProductRelation doSaveSupplyProductRelation(SupplyProductRelation Supplyr, String operatorName);

    /**
     * 根据商户ID查询
     * 
     * @param merchantId
     * @return
     */
    public List<SupplyProductRelation> getAllProductBySupplyMerchantId(Map<String, Object> searchParams,
                                                                       Long merchantId,
                                                                       String identityType,
                                                                       String status);

    /**
     * 根据产品ID，获取上游商户全集，计算上游比分需要！
     * 
     * @param product
     * @return
     */
    public List<SupplyProductRelation> querySupplyProductByProductId(List<AirtimeProduct> products,
                                                                     Long level,
                                                                     BigDecimal discount,
                                                                     BigDecimal quality);

    /**
     * 根据ID修改上游产品关系的状态
     * 
     * @param id
     * @param status
     * @return
     */
    public SupplyProductRelation updateSupplyProductRelationStatus(Long id, String status);

    /**
     * 根据产品Id获取所有配置该产品的商户产品信息
     * 
     * @param productId
     * @return
     */
    public List<SupplyProductRelation> getAllSupplyProductRelationByDown(Long productId);

    public SupplyProductRelation querySupplyProductRelationByParams(Long productId, Long merchantId,String status);

    /**
     * 根据ID删除关系
     * 
     * @param @param id 设定文件 @return void 返回类型 @throws
     */
    public void deleteSupplyProductRelationById(Long id, String operatorName);

    /**
     * newMerchant克隆oldMerchant的所有产品信息
     */
    public void cloneSupplyMerchantProduct(Long newMerchantId, Long oldMerchantId,
                                           String productIds,String operatorName);

    public void deleteSupplyProductRelationByProductId(Long productId, String operatorName);

    /**
     * 根据产品编号，状态。批量获取对应的关系。（批量开关功能点用）
     * 
     * @param productId
     * @param status
     * @return
     */
    public List<SupplyProductRelation> getSupplyProductRelationByParams(Long productId,
                                                                        String status);
    
    /**
     * 通过产品id修改产品名称
     * @param productId
     */
    public void updateProductNameById(Long productId,String productName);
    
    /**
     * 审核时，根据用户ID，产品ID，获取与此用户有关的产品树。
     * @param identityId
     * @param idenityType
     * @param productId
     * @param status
     * @return 
     * @see
     */
    public List<SupplyProductRelation> getProductRelationByParams(Long identityId,IdentityType idenityType,Long productId,String status);
    
    /**
     * 获取所有供货商产品关系（启动时缓存，用于下单匹配供货商）
     * @return 
     * @see
     */
    public List<SupplyProductRelation> getAllOpenProductRelation();
    
    /**
     * 外部服务调用deleteSupplyProductRelationByProductId方法时使用。aop切面删除缓存。
     * 
     * @return 
     * @see
     */
    public List<SupplyProductRelation> getAllProductRelationByProductId(Long productId);
    
    /**
     * 根据号段、面值、折扣、产品等级、质量分获取合适的供货商产品关系列表
     * 
     * @return 
     * @see
     */
    public List<SupplyProductRelation> matchProduct(NumSection numSection, BigDecimal faceVal, BigDecimal discount,Long level, BigDecimal quality);
    
    
    
    /**
     * 外部服务调用。用商户id获取该商户的所有产品关系
     * 
     * @return 
     * @see
     */
    public List<SupplyProductRelation> getProductRelationByIdentityId(Long merchantId);
    
    
    /**
     * 查询 (常用产品)
     * 
     * @return
     * @see
     */
    public List<SupplyProductRelation> getProductRelationByParams(Map<String, Object> searchParams);
    
    public boolean isExistsSupplyProductId(Long merchantId,String supplyProductId);
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

    /**
     * 根据商户编号与产品列表匹配相关产品
     * Description: <br>
     */
    public List<SupplyProductRelation> querySupplyProductByProductId(List<AirtimeProduct> products, Long merchantId);
}
