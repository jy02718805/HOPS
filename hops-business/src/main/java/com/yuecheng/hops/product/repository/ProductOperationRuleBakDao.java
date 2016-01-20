package com.yuecheng.hops.product.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.product.entity.history.ProductOperationRuleBak;


@Service
public interface ProductOperationRuleBakDao extends PagingAndSortingRepository<ProductOperationRuleBak, Long>, JpaSpecificationExecutor<ProductOperationRuleBak>
{
	@Query("select por from ProductOperationRuleBak por where por.historyId=:historyId ")
    public List<ProductOperationRuleBak> queryProductOperationRuleBakByHisId(@Param("historyId") Long historyId);
}
