package com.yuecheng.hops.blacklist.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.yuecheng.hops.blacklist.entity.Blacklist;


/**
 * 黑名单DAO
 * 
 * @author yao
 * @version 2015年5月25日
 * @see BlacklistDao
 * @since
 */
public interface BlacklistDao extends PagingAndSortingRepository<Blacklist, Long>, JpaSpecificationExecutor<Blacklist>
{
    @Query("select bl from Blacklist bl ")
    List<Blacklist> selectAll();

    @Query("select bl from Blacklist bl where bl.blacklistId=:blacklistId ")
    Blacklist queryBlacklistById(@Param("blacklistId") Long blacklistId);

    @Query("select bl from Blacklist bl where bl.blacklistNo=:blacklistNo ")
    Blacklist queryBlacklistByNo(@Param("blacklistNo") String blacklistNo);

    @Query("select bl from Blacklist bl where bl.blacklistNo=:blacklistNo and bl.businessType=:businessType")
    Blacklist queryBlacklistByNoAndTpye(@Param("blacklistNo") String blacklistNo,
            @Param("businessType") String businessType);
}
