package com.yuecheng.hops.numsection.service;


/**
 * 省份逻辑层
 * 
 * @author Jinger
 * @date：2013-10-18
 */
import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.numsection.entity.Province;


public interface ProvinceService
{
    public Province saveProvince(Province province);

    public void deleteProvince(String provinceId);

    public void updateProvince(String provinceId, String status);

    public Province findOne(String provinceId);

    public List<Province> getAllProvince();

    public YcPage<Province> queryProvince(Map<String, Object> searchParams, int pageNumber,
                                          int pageSize, BSort bsort);

}
