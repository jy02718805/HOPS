package com.yuecheng.hops.product.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.product.entity.history.ProductOperationRule;


@Service
public interface ProductOperationRuleDao extends PagingAndSortingRepository<ProductOperationRule, Long>, JpaSpecificationExecutor<ProductOperationRule>
{
	@Query("select por from ProductOperationRule por where por.historyId=:historyId ")
    public List<ProductOperationRule> queryProductOperationRuleByHisId(@Param("historyId") Long historyId);
}
