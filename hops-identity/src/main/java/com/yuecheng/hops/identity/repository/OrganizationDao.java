package com.yuecheng.hops.identity.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.yuecheng.hops.identity.entity.mirror.Organization;


public interface OrganizationDao extends PagingAndSortingRepository<Organization, Long>, JpaSpecificationExecutor<Organization>
{
    @Query("select o from Organization o where o.organizationName = :organizationName")
    public Organization getOrganizationByName(@Param("organizationName")
    String organizationName);

    @Query("select o from Organization o where o.organizationRegistrationNo = :organizationRegistrationNo")
    public Organization getOrganizationByNo(@Param("organizationRegistrationNo")
    String organizationRegistrationNo);
}
