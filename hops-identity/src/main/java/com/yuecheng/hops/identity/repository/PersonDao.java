package com.yuecheng.hops.identity.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.identity.entity.mirror.Person;


/**
 * person表数据访问层
 * 
 * @author：Jinger
 * @date：2013-09-24
 */
@Service
public interface PersonDao extends PagingAndSortingRepository<Person, Long>, JpaSpecificationExecutor<Person>
{
    @Query("select p from Person p")
    public List<Person> selectAll();
}
