package com.yuecheng.hops.transaction.config.repository.jpa;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.transaction.config.entify.profitImputation.ProfitImputationInfo;

@Service
public interface ProfitImputationInfoJpaDao extends PagingAndSortingRepository<ProfitImputationInfo, Long>, JpaSpecificationExecutor<ProfitImputationInfo>
{

}
