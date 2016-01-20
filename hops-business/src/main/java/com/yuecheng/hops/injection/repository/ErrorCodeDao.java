package com.yuecheng.hops.injection.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.yuecheng.hops.injection.entity.ErrorCode;


public interface ErrorCodeDao extends PagingAndSortingRepository<ErrorCode, String>, JpaSpecificationExecutor<ErrorCode>
{

}