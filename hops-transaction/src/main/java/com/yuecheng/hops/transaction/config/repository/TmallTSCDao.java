package com.yuecheng.hops.transaction.config.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.transaction.config.entify.product.TmallTSC;


/**
 * 天猫产品数据访问层
 * 
 * @author Jinger 2014-03-11
 */
@Service
public interface TmallTSCDao extends PagingAndSortingRepository<TmallTSC, String>, JpaSpecificationExecutor<TmallTSC>
{

    @Query("select tb from TmallTSC tb ")
    List<TmallTSC> selectAll();
}
