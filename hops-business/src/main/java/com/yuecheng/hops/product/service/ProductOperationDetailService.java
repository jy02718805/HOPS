/*
 * 文件名：ProductOperationDetailService.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015-6-25
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.product.service;

import java.util.List;

import com.yuecheng.hops.product.entity.history.ProductOperationDetail;

public interface ProductOperationDetailService
{

    public List<ProductOperationDetail> getProductOperationDetailList(Long historyId,String merchantType);
    
    public void saveProductOperationDetail(ProductOperationDetail productOperationDetail);
    
    public void deleteProductOperationDetails(List<ProductOperationDetail> productOperationDetails);
    
    public void deleteProductOperationDetail(Long productOperationDetailId);
}
