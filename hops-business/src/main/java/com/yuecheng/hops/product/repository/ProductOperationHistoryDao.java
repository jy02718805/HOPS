package com.yuecheng.hops.product.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.product.entity.history.ProductOperationHistory;


@Service
public interface ProductOperationHistoryDao extends PagingAndSortingRepository<ProductOperationHistory, Long>, JpaSpecificationExecutor<ProductOperationHistory>
{}
