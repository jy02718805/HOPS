package com.yuecheng.hops.rebate.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.yuecheng.hops.rebate.entity.RebateTradingVolume;


public interface RebateTradingVolumeDao extends PagingAndSortingRepository<RebateTradingVolume, Long>, JpaSpecificationExecutor<RebateTradingVolume>
{
    @Query("select rtv from RebateTradingVolume rtv where rtv.rebateRuleId=:rebateRuleId and rtv.tradingVolumeLow <=:tradingVolume and rtv.tradingVolumeHigh >:tradingVolume")
    RebateTradingVolume getRebateTradingVolumeByParams(@Param("rebateRuleId") Long rebateRuleId,@Param("tradingVolume") Long tradingVolume);
    
    @Query("select rtv from RebateTradingVolume rtv where rtv.rebateRuleId=:rebateRuleId order by rtv.tradingVolumeLow ASC")
    List<RebateTradingVolume> getRebateTradingVolumeByParams(@Param("rebateRuleId") Long rebateRuleId);
}
