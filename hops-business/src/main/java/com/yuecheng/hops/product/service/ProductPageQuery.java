package com.yuecheng.hops.product.service;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.product.entity.airtimes.AirtimeProduct;


public interface ProductPageQuery
{

    /**
     * 分页查询产品
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @param sortType
     * @return
     */
    public YcPage<AirtimeProduct> queryAirtimeProduct(Map<String, Object> searchParams,
                                                      int pageNumber, int pageSize, String sortType);

    public YcPage<AirtimeProduct> queryAirtimeProductList(Map<String, Object> searchParams,
                                                          int pageNumber, int pageSize,String sortType);

    public AirtimeProduct queryAirtimeProductById(Long productId);

    public List<AirtimeProduct> queryProduct(AirtimeProduct airtimeProduct);

    /**
     * 通过属性名称和所对应的值，查询出匹配的产品
     * 
     * @param paramName
     *            属性名称，比如('faceValue','city')
     * @param value
     *            属性所对应的值('50','731')
     * @param pageNumber
     *            页码
     * @param pageSize
     *            每页数量
     * @param sortType
     *            安具体属性名排序
     * @return
     */
    public YcPage<AirtimeProduct> queryAirtimeProductByProperty(List<String> paramName,
                                                                List<String> value,
                                                                int pageNumber, int pageSize,
                                                                String sortType);

    /**
     * 根据条件在查询出对应的产品
     * 
     * @param merchantId
     * @param province
     * @param parValue
     * @param carrierName
     * @param city
     * @return
     */
    public AirtimeProduct queryAirtimeProductByParams(String province, BigDecimal parValue,
                                                      String carrierName, String city, int businessType);

    /**
     * 根据 运营商、省份、城市、面值 精确查询
     * 
     * @param province
     * @param parValue
     * @param carrierName
     * @param city
     * @return
     */
    public List<AirtimeProduct> fuzzyQueryAirtimeProductsByParams(String province, String parValue,
                                                             String carrierName, String city,  String businessType);
    
    /**
     * 得到删除掉的产品数据 功能描述: <br> 参数说明: <br>
     */
    public AirtimeProduct queryAirtimeProductByDeleteId(Long productId);

}