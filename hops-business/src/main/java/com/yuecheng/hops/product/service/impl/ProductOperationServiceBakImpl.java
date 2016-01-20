package com.yuecheng.hops.product.service.impl;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springside.modules.persistence.SearchFilter;

import com.yuecheng.hops.common.query.PageUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.product.entity.history.ProductOperationHistory;
import com.yuecheng.hops.product.entity.history.ProductOperationHistoryBak;
import com.yuecheng.hops.product.repository.ProductOperationHistoryBakDao;
import com.yuecheng.hops.product.service.ProductOperationBakService;


@Service("productOperationBakService")
public class ProductOperationServiceBakImpl implements ProductOperationBakService
{
    @Autowired
    private ProductOperationHistoryBakDao productOperationHistoryBakDao;

    @Override
    public YcPage<ProductOperationHistoryBak> queryProductOperationHistory(Map<String, Object> searchParams,
                                                                        int pageNumber,
                                                                        int pageSize,
                                                                        String sortType)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        YcPage<ProductOperationHistoryBak> ycPage = PageUtil.queryYcPage(productOperationHistoryBakDao,
            filters, pageNumber, pageSize, new Sort(Direction.DESC, sortType),
            ProductOperationHistory.class);
        return ycPage;
    }

    @Override
    public ProductOperationHistoryBak queryProductOperationHistoryById(Long id)
    {
        ProductOperationHistoryBak productOperationHistoryBak = productOperationHistoryBakDao.findOne(id);
        return productOperationHistoryBak;
    }

}
