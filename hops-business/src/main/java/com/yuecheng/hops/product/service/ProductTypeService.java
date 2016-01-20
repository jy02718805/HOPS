package com.yuecheng.hops.product.service;


import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.product.entity.type.ProductType;


public interface ProductTypeService
{
    /**
     * 保存产品类型及其对应产品属性
     * 
     * @param pt
     * @return
     */
    public ProductType saveProductType(ProductType pt);

    /**
     * 分页展示产品类型
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @param sortType
     * @return
     */
    public YcPage<ProductType> queryCurrencyAccount(Map<String, Object> searchParams,
                                                    int pageNumber, int pageSize, String sortType);

    /**
     * 查询产品类型详细信息
     * 
     * @param pt
     * @return
     */
    public ProductType queryProductTypeById(ProductType pt);

    /**
     * 获取所有产品类型
     * 
     * @return
     */
    public List<ProductType> getAllProductType();

    /**
     * 修改产品类型
     * 
     * @param pt
     * @return
     */
    public ProductType editProductType(ProductType pt);

    /**
     * deleteProductType(删除产品类型)
     * 
     * @Title: deleteProductType @Description: TODO @param @param pt 设定文件 @return void 返回类型 @throws
     */
    public void deleteProductType(ProductType pt);
    
    /**
     * 根据ID查询产品类型
     * 
     * @param id
     * @return
     */
    public ProductType queryProductTypeById(Long id);

}
