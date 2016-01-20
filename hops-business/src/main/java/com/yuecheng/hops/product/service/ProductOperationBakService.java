package com.yuecheng.hops.product.service;


import java.util.Map;

import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.product.entity.history.ProductOperationHistoryBak;


/**
 * 产品批量操作历史记录服务
 * 
 * @author Administrator
 */
public interface ProductOperationBakService
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
    public YcPage<ProductOperationHistoryBak> queryProductOperationHistory(Map<String, Object> searchParams,
                                                                        int pageNumber,
                                                                        int pageSize,
                                                                        String sortType);

    /**
     * 根据ID，查询产品批量操作记录
     * 
     * @param id
     * @return
     */
    public ProductOperationHistoryBak queryProductOperationHistoryById(Long id);

}
