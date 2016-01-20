package com.yuecheng.hops.transaction.config.repository.jpa;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.transaction.config.entify.profitImputation.ProfitImputationRecord;

@Service
public interface ProfitImputationRecordJpaDao extends PagingAndSortingRepository<ProfitImputationRecord, Long>, JpaSpecificationExecutor<ProfitImputationRecord>
{
    @Query(value = "select p from  ProfitImputationRecord p where 1=1  and p.recordStatus!=:recordStatus order by p.recordUpdateDate desc")
    public List<ProfitImputationRecord> queryProfitImputationRecordList(@Param("recordStatus") String recordStatus);
    
}
