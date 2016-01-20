package com.yuecheng.hops.numsection.repository;


/**
 * 运营商表数据层
 * 
 * @author Jinger
 * @date：2013-10-15
 */
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.yuecheng.hops.numsection.entity.CarrierInfo;


public interface CarrierInfoDao extends PagingAndSortingRepository<CarrierInfo, String>, JpaSpecificationExecutor<CarrierInfo>
{
    @Query("select c from CarrierInfo c")
    List<CarrierInfo> selectAll();

    @Query("select c from CarrierInfo c where c.carrierName=:carrierName")
    CarrierInfo getByCityName(@Param("carrierName") String carrierName);
}
