package com.yuecheng.hops.product.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.yuecheng.hops.product.entity.type.relation.ProductTypeRelation;


public interface ProductTypeRelationDao extends PagingAndSortingRepository<ProductTypeRelation, Long>, JpaSpecificationExecutor<ProductTypeRelation>
{

    @Query("select p from ProductTypeRelation p where p.typeId = :typeId")
    List<ProductTypeRelation> queryProductTypeRelationByTypeId(@Param("typeId") Long typeId);

    // 此方法有些问题需待查
    // @Modifying
    // @Query("update Customer c set c.status=?1 where c.id=?2")
    // public int updateStatusByCustomerId(String status,String customerId);

    // @Query("select c from Customer c where c.person.id=:personId")
    // AirtimeProduct getCustomerByPersonId(@Param("personId")Long personId);
}
