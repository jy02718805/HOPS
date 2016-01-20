package com.yuecheng.hops.product.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.product.entity.type.ProductType;


@Service
public interface ProductTypeDao extends PagingAndSortingRepository<ProductType, Long>, JpaSpecificationExecutor<ProductType>
{

    // @Query("select c from Customer c")
    // List<AirtimeProduct> selectAll();

    // 此方法有些问题需待查
    @Modifying
    @Query("update ProductType c set c.productTypeName=?1 where c.typeId=?2")
    public int updateProductTypeNameById(String productTypeName, Long typeId);

    // @Query("select c from Customer c where c.person.id=:personId")
    // AirtimeProduct getCustomerByPersonId(@Param("personId")Long personId);
}
