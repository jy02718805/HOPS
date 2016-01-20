package com.yuecheng.hops.parameter.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.parameter.entity.MerchantBindTimes;


@Service
public interface MerchantBindTimesDao extends PagingAndSortingRepository<MerchantBindTimes, Long>, JpaSpecificationExecutor<MerchantBindTimes>
{

}
