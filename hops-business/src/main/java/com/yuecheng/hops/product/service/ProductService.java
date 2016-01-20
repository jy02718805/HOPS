package com.yuecheng.hops.product.service;


import java.util.List;

import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.product.entity.airtimes.AirtimeProduct;
import com.yuecheng.hops.product.entity.relation.ProductRelation;


public interface ProductService
{
    /**
     * 修改产品状态
     * 
     * @param ap
     * @param status
     * @return
     */
    public AirtimeProduct updateProductStatus(AirtimeProduct ap, String status);

    public List<AirtimeProduct> getAllProduct();

    public List<AirtimeProduct> getAllProductByStatus(String status);
    
    public List<AirtimeProduct> getAllNoDelProduct();
    
    public List<AirtimeProduct> getAllRootProducts();

    public List<AirtimeProduct> getChildProducts(Long parentProductId);

    public List<AirtimeProduct> getParentProducts(Long productId);

    public List<AirtimeProduct> getProductByProvinceId(String proinceId);

    /**
     * 根据 运营商、省份、城市、面值 得到所有产品树
     * 
     * @param province
     * @param parValue
     * @param carrierName
     * @param city
     * @return
     */
    public List<AirtimeProduct> getProductTree(String province, String parValue,
                                               String carrierName, String city, Integer businessType);

    /**
     * 通过
     *
     * @Title: QueryProductByIdentity
     * @Description: TODO
     * @param @param merchant
     * @param @return 设定文件
     * @return List<SupplyProductRelation> 返回类型
     * @throws
     */
    public List<ProductRelation> QueryProductByIdentity(Merchant merchant);

    
    /**
     * 通过ID获取产品信息
     * @param productId
     * @return 
     * @see
     */
    public AirtimeProduct findOne(Long productId);
}
