package com.yuecheng.hops.numsection.service;


/**
 * 城市逻辑层
 * 
 * @author Jinger
 * @date：2013-10-18
 */
import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.numsection.entity.City;


public interface CityService
{
    public City saveCity(City city);

    public void deleteCity(String cityId);

    public void updateCity(String cityId, String status);

    public City findOne(String cityId);

    public City getByCityName(String cityName);

    public List<City> selectAll();

    public List<City> getCityByProvince(String provinceId);

    public YcPage<City> queryCity(Map<String, Object> searchParams, int pageNumber, int pageSize,
                                  BSort bsort);

}
