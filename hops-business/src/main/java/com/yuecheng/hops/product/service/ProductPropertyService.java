package com.yuecheng.hops.product.service;


import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.product.entity.property.ProductProperty;


public interface ProductPropertyService
{
    /**
     * 保存 产品属性、产品属性值、对应关系
     * 
     * @param pp
     * @return
     */
    public ProductProperty saveProductProperty(ProductProperty pp);

    /**
     * 修改属性信息
     * 
     * @param pp
     * @return
     */
    public ProductProperty editProductProperty(ProductProperty pp);

    /**
     * 删除产品属性、属性值、关系
     * 
     * @param pp
     * @return
     */
    public ProductProperty deleteProductProperty(ProductProperty pp);

    /**
     * 分页展示所有属性
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @param sortType
     * @return
     */
    public YcPage<ProductProperty> queryProductProperty(Map<String, Object> searchParams,
                                                        int pageNumber, int pageSize,
                                                        String sortType);

    /**
     * 根据ID查询产品属性信息
     * 
     * @param productPropertyId
     * @return
     */
    public ProductProperty queryProductPropertyById(Long productPropertyId);

    /**
     * 获取所有产品属性
     * 
     * @return
     */
    public List<ProductProperty> getAllProductProperty();

}
