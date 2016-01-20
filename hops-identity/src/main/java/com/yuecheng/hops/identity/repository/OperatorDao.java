package com.yuecheng.hops.identity.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yuecheng.hops.identity.entity.operator.Operator;


public interface OperatorDao extends IdentityDao<Operator>
{

    @Query("select o from Operator o where o.operatorName = :operatorName")
    public Operator getOperatorByOperatorName(@Param("operatorName")
    String operatorName);

    @Query("select o from Operator o where o.person.id = :personId")
    public Operator getOperatorByPersonId(@Param("personId")
    Long personId);

    @Query("select o from Operator o where o.ownerIdentityId = :ownerIdentityId")
    public List<Operator> getOperatorByMerchant(@Param("ownerIdentityId")
    Long ownerIdentityId);
}
