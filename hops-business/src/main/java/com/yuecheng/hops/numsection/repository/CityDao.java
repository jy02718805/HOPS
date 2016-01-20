package com.yuecheng.hops.numsection.repository;


/**
 * 城市表数据层
 * 
 * @author Jinger
 * @date：2013-10-15
 */
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.yuecheng.hops.numsection.entity.City;


public interface CityDao extends PagingAndSortingRepository<City, String>, JpaSpecificationExecutor<City>
{
    @Query("select c from City c")
    List<City> selectAll();

    @Query("select c from City c where c.provinceId=:provinceId")
    List<City> getCityList(@Param("provinceId") String province);

    @Query("select c from City c where c.cityName=:cityName")
    City getByCityName(@Param("cityName") String cityName);
}
