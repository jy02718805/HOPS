package com.yuecheng.hops.parameter.cache;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.bootstrap.HopsBootstrap;
import com.yuecheng.hops.common.hopscache.HopsCacheUtil;
import com.yuecheng.hops.parameter.entity.ParameterConfiguration;
import com.yuecheng.hops.parameter.service.ParameterConfigurationService;


@Component
public class ParameterConfigurationCacheInit implements HopsBootstrap
{
    @Autowired
    private ParameterConfigurationService parameterConfigurationService;

    private static Logger logger = LoggerFactory.getLogger(ParameterConfigurationCacheInit.class);

    @Override
    public void startup()
    {
        try
        {
            List<ParameterConfiguration> parameters = parameterConfigurationService.findAll();
            for (ParameterConfiguration parameter : parameters)
            {
                HopsCacheUtil.put(Constant.Common.BUSINESS_CACHE, parameter.getConstantName(),
                    parameter);
            }
        }
        catch (Exception e)
        {
            logger.error("[CacheBeanPostProcessor init()][异常: " + e.getMessage() + "]");
        }

    }

}
