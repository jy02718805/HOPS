package com.yuecheng.hops.transaction.basic.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.transaction.basic.entity.AgentOrderKey;


@Service
public interface AgentOrderKeyJpaDao extends PagingAndSortingRepository<AgentOrderKey, Long>, JpaSpecificationExecutor<AgentOrderKey>
{
    @Query("select aok from AgentOrderKey aok where aok.agentOrderKeyPK.merchantId = :merchantId and aok.agentOrderKeyPK.merchantOrderNo = :merchantOrderNo")
    public List<AgentOrderKey> findAgentOrderKeyByParams(@Param("merchantId")Long merchantId,@Param("merchantOrderNo")String merchantOrderNo);
}
