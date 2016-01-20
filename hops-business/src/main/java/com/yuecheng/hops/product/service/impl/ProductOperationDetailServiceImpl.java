/*
 * 文件名：ProductOperationDetailServiceImpl.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015-6-25
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.product.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.product.entity.history.ProductOperationDetail;
import com.yuecheng.hops.product.repository.ProductOperationDetailDao;
import com.yuecheng.hops.product.service.ProductOperationDetailService;

@Service("productOperationDetailService")
public class ProductOperationDetailServiceImpl implements ProductOperationDetailService
{
    @Autowired
    private ProductOperationDetailDao productOperationDetailDao;

    @Override
    public List<ProductOperationDetail> getProductOperationDetailList(Long historyId,
                                                                      String merchantType)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.ProductOperationDetail.PRODUCT_OPERATION_HISTORY_ID,
            new SearchFilter(
                EntityConstant.ProductOperationDetail.PRODUCT_OPERATION_HISTORY_ID,
                Operator.EQ, historyId));
        if(!StringUtil.isNullOrEmpty(merchantType))
        {
            filters.put(EntityConstant.ProductOperationDetail.MERCHANT_TYPE, new SearchFilter(
                EntityConstant.ProductOperationDetail.MERCHANT_TYPE, Operator.EQ,
                merchantType));
        }
        Specification<ProductOperationDetail> spec = DynamicSpecifications.bySearchFilter(
            filters.values(), ProductOperationDetail.class);
        List<ProductOperationDetail> productOperationDetails = productOperationDetailDao.findAll(spec);
        return productOperationDetails;
    }

    @Override
    public void saveProductOperationDetail(ProductOperationDetail productOperationDetail)
    {
        productOperationDetailDao.save(productOperationDetail);
        
    }

    @Override
    public void deleteProductOperationDetails(List<ProductOperationDetail> productOperationDetails)
    {
        productOperationDetailDao.delete(productOperationDetails);
        
    }

    @Override
    public void deleteProductOperationDetail(Long productOperationDetailId)
    {
        productOperationDetailDao.delete(productOperationDetailId);
        
    }

}
