package com.yuecheng.hops.transaction.config.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.transaction.config.entify.product.AssignExclude;


@Service
public interface AssignExcludeDao extends PagingAndSortingRepository<AssignExclude, Long>, JpaSpecificationExecutor<AssignExclude>
{

    @Query("select de from AssignExclude de where de.merchantId=:merchantId and de.ruleType=:ruleType")
    public List<AssignExclude> queryAssignExcludeByParames(Long merchantId, Long ruleType);

    @Query("select de from AssignExclude de")
    public List<AssignExclude> queryAssignExcludeList();
}
