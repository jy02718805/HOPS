package com.yuecheng.hops.product.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.yuecheng.hops.product.entity.property.ProductProperty;


public interface ProductPropertyDao extends PagingAndSortingRepository<ProductProperty, Long>, JpaSpecificationExecutor<ProductProperty>
{

    // @Query("select c from ProductProperty c")
    // List<ProductProperty> queryProductPropertyByProductTypeId(Long );

    // 此方法有些问题需待查
    // @Modifying
    // @Query("update Customer c set c.status=?1 where c.id=?2")
    // public int updateStatusByCustomerId(String status,String customerId);

    // @Query("select c from Customer c where c.person.id=:personId")
    // AirtimeProduct getCustomerByPersonId(@Param("personId")Long personId);
}
