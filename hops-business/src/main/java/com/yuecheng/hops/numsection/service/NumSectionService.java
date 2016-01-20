package com.yuecheng.hops.numsection.service;


/**
 * 号段表逻辑层
 * 
 * @author Jinger
 * @date：2013-10-15
 */
import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.numsection.entity.NumSection;


public interface NumSectionService
{
    public NumSection saveNumSection(NumSection numSection);

    public void deleteNumSection(String numSectionId);

    public void updateNumSection(String numSectionId, String status);

    public NumSection findOne(String numSectionId);

    public List<NumSection> selectAll();

    public YcPage<NumSection> queryNumSection(Map<String, Object> searchParams, int pageNumber,
                                              int pageSize, BSort bsort);

}
