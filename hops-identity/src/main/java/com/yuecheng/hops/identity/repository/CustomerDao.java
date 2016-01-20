package com.yuecheng.hops.identity.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yuecheng.hops.identity.entity.customer.Customer;


/**
 * customer用户表数据访问层
 * 
 * @author：Jinger
 * @date：2013-09-24
 */

public interface CustomerDao extends IdentityDao<Customer>
{

    @Query("select c from Customer c ")
    List<Customer> selectAll();

    // 此方法有些问题需待查
    // @Modifying
    // @Query("update Customer c set c.status=?1 where c.id=?2")
    // public int updateStatusByCustomerId(String status,String customerId);

    @Query("select c from Customer c where c.person.id=:personId")
    Customer getCustomerByPersonId(@Param("personId")
    Long personId);

    @Query("select c from Customer c where c.customerName=:customerName")
    Customer queryCustomerByCustomerName(@Param("customerName")
    String customerName);
}
