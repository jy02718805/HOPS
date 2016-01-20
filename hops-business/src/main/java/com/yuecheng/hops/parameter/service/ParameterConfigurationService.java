package com.yuecheng.hops.parameter.service;


import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.parameter.entity.ParameterConfiguration;


public interface ParameterConfigurationService
{
    public ParameterConfiguration getParameterConfiguration(Long id);

    public YcPage<ParameterConfiguration> queryParameterConfiguration(Map<String, Object> searchParams,
                                                                    int pageNumber, int pageSize,
                                                                    String sortType);

    public ParameterConfiguration addParameterConfiguration(ParameterConfiguration ParameterConfiguration);

    public ParameterConfiguration updateParameterConfiguration(ParameterConfiguration ParameterConfiguration);
    // 取得缓存中参数
    public ParameterConfiguration getParameterConfigurationByKey(String constantName);
    //取得所有参数
    public List<ParameterConfiguration> findAll();
}
