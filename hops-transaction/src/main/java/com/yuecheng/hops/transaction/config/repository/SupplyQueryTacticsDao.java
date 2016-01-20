package com.yuecheng.hops.transaction.config.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.transaction.config.entify.query.SupplyQueryTactics;


@Service
public interface SupplyQueryTacticsDao extends PagingAndSortingRepository<SupplyQueryTactics, Long>, JpaSpecificationExecutor<SupplyQueryTactics>
{

}
