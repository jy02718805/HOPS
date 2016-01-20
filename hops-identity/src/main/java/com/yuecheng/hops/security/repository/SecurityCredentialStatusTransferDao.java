package com.yuecheng.hops.security.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.yuecheng.hops.security.entity.SecurityCredentialStatusTransfer;


/**
 * SecurityCredential状态机数据访问层
 * 
 * @author Jinger 2014-10-22
 */
public interface SecurityCredentialStatusTransferDao extends PagingAndSortingRepository<SecurityCredentialStatusTransfer, Long>, JpaSpecificationExecutor<SecurityCredentialStatusTransfer>
{

}
