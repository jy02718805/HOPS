package com.yuecheng.hops.parameter.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.query.PageUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.parameter.entity.ParameterConfiguration;
import com.yuecheng.hops.parameter.repository.ParameterConfigurationDao;
import com.yuecheng.hops.parameter.service.ParameterConfigurationService;


@Service("parameterConfigurationService")
public class ParameterConfigurationServiceImpl implements ParameterConfigurationService
{
    @Autowired
    private ParameterConfigurationDao ParameterConfigurationDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(ParameterConfigurationServiceImpl.class);

    @Override
    public ParameterConfiguration getParameterConfiguration(Long id)
    {
        LOGGER.debug("[ParameterConfigurationService:getParameterConfiguration()]");
        ParameterConfiguration ParameterConfiguration = ParameterConfigurationDao.findOne(id);
        return ParameterConfiguration;
    }

    @Override
    public YcPage<ParameterConfiguration> queryParameterConfiguration(Map<String, Object> searchParams,
                                                                    int pageNumber, int pageSize,
                                                                    String sortType)
    {
        LOGGER.debug("[ParameterConfigurationService:getParameterConfigurations]");
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        YcPage<ParameterConfiguration> page = PageUtil.queryYcPage(ParameterConfigurationDao,
            filters, pageNumber, pageSize, new Sort(Direction.DESC, sortType),
            ParameterConfiguration.class);
        return page;
    }

    @Override
    public ParameterConfiguration addParameterConfiguration(ParameterConfiguration hConstant)
    {
        LOGGER.debug("[ParameterConfigurationService:addParameterConfiguration(ParameterConfiguration:"
                     + hConstant.toString() + ")]");
        ParameterConfiguration parameterConfiguration = ParameterConfigurationDao.save(hConstant);
        return parameterConfiguration;
    }

    @Override
    public ParameterConfiguration updateParameterConfiguration(ParameterConfiguration hConstant)
    {
        LOGGER.debug("[ParameterConfigurationService:updateParameterConfiguration(ParameterConfiguration:"
                     + hConstant.toString() + ")");
        ParameterConfiguration parameterConfiguration = ParameterConfigurationDao.save(hConstant);
        return parameterConfiguration;
    }

    @Override
    public ParameterConfiguration getParameterConfigurationByKey(String constantName)
    {
        LOGGER.debug("[ParameterConfigurationService:getParameterConfigurationByKey(constantName:"
                     + constantName + ")]");
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.ParameterConfiguration.CONSTANT_NAME, new SearchFilter(
            EntityConstant.ParameterConfiguration.CONSTANT_NAME, Operator.EQ, constantName));
        Specification<ParameterConfiguration> spec_Constant = DynamicSpecifications.bySearchFilter(
            filters.values(), ParameterConfiguration.class);
        ParameterConfiguration ParameterConfiguration = ParameterConfigurationDao.findOne(spec_Constant);
        return ParameterConfiguration;
    }


    @Override
    public List<ParameterConfiguration> findAll()
    {
        List<ParameterConfiguration> parameters=(List<ParameterConfiguration>)ParameterConfigurationDao.findAll();
        return parameters;
    }

}
