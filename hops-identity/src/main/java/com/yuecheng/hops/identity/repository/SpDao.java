package com.yuecheng.hops.identity.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yuecheng.hops.identity.entity.sp.SP;


public interface SpDao extends IdentityDao<SP>
{
    @Query("select s from SP s where s.business=:business")
    public SP getSpByBusiness(@Param("business")
    String business);
}
