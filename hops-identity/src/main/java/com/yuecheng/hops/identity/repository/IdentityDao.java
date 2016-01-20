package com.yuecheng.hops.identity.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.yuecheng.hops.identity.entity.AbstractIdentity;


public interface IdentityDao<T extends AbstractIdentity> extends PagingAndSortingRepository<T, Long>, JpaSpecificationExecutor<T>
{

}
