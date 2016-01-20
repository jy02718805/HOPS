package com.yuecheng.hops.product.service;


import com.yuecheng.hops.product.entity.airtimes.AirtimeProduct;


public interface ProductManagement
{

    /**
     * 保存产品信息
     * 
     * @param ap
     * @return
     */
    public AirtimeProduct saveProduct(AirtimeProduct ap);

    /**
     * 修改产品信息
     * 
     * @param ap
     * @return
     */
    public AirtimeProduct editProduct(AirtimeProduct ap);

    /**
     * 删除产品信息
     * 
     * @param ap
     * @return
     */
    public String deleteProduct(AirtimeProduct ap, String operatorName);
    
    /**
     * 验证产品
     * 功能描述: <br>
     * 参数说明: <br>
     */
    public boolean validateLockProductStatus(Long productId,String productStatus);
}