package com.yuecheng.hops.identity.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.yuecheng.hops.identity.entity.IdentityStatusTransfer;


public interface IdentityStatusTransferDao extends PagingAndSortingRepository<IdentityStatusTransfer, Long>, CrudRepository<IdentityStatusTransfer, Long>, JpaSpecificationExecutor<IdentityStatusTransfer>
{

}
