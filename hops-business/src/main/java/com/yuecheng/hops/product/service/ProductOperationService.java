package com.yuecheng.hops.product.service;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.product.entity.history.ProductDiscountHistory;
import com.yuecheng.hops.product.entity.history.ProductOperationHistory;
import com.yuecheng.hops.product.entity.history.assist.ProductOperationHistoryAssist;
import com.yuecheng.hops.product.entity.relation.ProductRelation;


/**
 * 产品批量操作服务
 * 
 * @author Administrator
 */
public interface ProductOperationService
{
    /**
     * 查询产品操作记录
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @param sortType
     * @return
     */
    public YcPage<ProductOperationHistory> queryProductOperationHistory(Map<String, Object> searchParams,
                                                                        int pageNumber,
                                                                        int pageSize,
                                                                        String sortType);

    /**
     * 根据ID，查询产品批量操作记录
     * 
     * @param id
     * @return
     */
    public ProductOperationHistory queryProductOperationHistoryById(Long id);

    /**
     * 保存产品批量操作记录
     * 
     * @param productOperationHistory
     * @return
     */
    public ProductOperationHistory saveProductOperationHistory(ProductOperationHistory productOperationHistory,String merchantIds,String updateUser);

    /**
     * 开启批量操作任务
     * 
     * @param productOperationHistory
     * @return
     */
    public ProductOperationHistoryAssist doProductOperationHistory(ProductOperationHistory productOperationHistory);

    /**
     * 回滚批量操作任务
     * 
     * @param productOperationHistory
     * @return
     */
    public ProductOperationHistory refProductOperationHistory(ProductOperationHistory productOperationHistory);

    /**
     * 删除产品批量操作任务
     * 
     * @param id
     */
    public void deleteProductOperationHistory(Long id);
    
    /**
     * 记录产品变动（增加、删除、修改）
     * @return 
     * @see
     */
    public ProductDiscountHistory saveProductDiscountHistory(ProductDiscountHistory productDiscountHistory);
    
    /**
     * 组装产品变动历史
     * @param productRelation
     * @param productSide
     * @param oldValue
     * @param newValue
     * @param operatorName
     * @return 
     * @see
     */
    public ProductDiscountHistory buildProductDiscountHistory(ProductRelation productRelation, String productSide, BigDecimal oldValue, BigDecimal newValue, String operatorName, String action);
    
    /**
     * 页面查询
     * @param paramsMap
     * @return 
     * @see
     */
    public YcPage<ProductDiscountHistory> queryProductDiscountHistory(Map<String, Object> searchParams,int pageNumber,int pageSize,String sortType,Date beginDate);
    
}
