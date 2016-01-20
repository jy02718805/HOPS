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
import com.yuecheng.hops.parameter.entity.MerchantBindTimes;
import com.yuecheng.hops.parameter.entity.ParameterConfiguration;
import com.yuecheng.hops.parameter.repository.MerchantBindTimesDao;
import com.yuecheng.hops.parameter.service.MerchantBindTimesService;


@Service("merchantBindTimesService")
public class MerchantBindTimesServiceImpl implements MerchantBindTimesService
{
    @Autowired
    private MerchantBindTimesDao merchantBindTimesDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(MerchantBindTimesServiceImpl.class);

    @Override
    public MerchantBindTimes getTimes(Long id)
    {
        MerchantBindTimes merchantBindTimes = merchantBindTimesDao.findOne(id);
        return merchantBindTimes;
    }

    @Override
    public YcPage<MerchantBindTimes> queryMerchantBindTimes(Map<String, Object> searchParams,
                                                            int pageNumber, int pageSize,
                                                            String sortType)
    {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        YcPage<MerchantBindTimes> page = PageUtil.queryYcPage(merchantBindTimesDao,
            filters, pageNumber, pageSize, new Sort(Direction.DESC, sortType),
            ParameterConfiguration.class);
        return page;
    }

    @Override
    public MerchantBindTimes saveMerchantBindTimes(MerchantBindTimes merchantBindTimes)
    {
        merchantBindTimes = merchantBindTimesDao.save(merchantBindTimes);
        return merchantBindTimes;
    }

    @Override
    public MerchantBindTimes updateMerchantBindTimes(MerchantBindTimes merchantBindTimes)
    {
        merchantBindTimes = merchantBindTimesDao.save(merchantBindTimes);
        return merchantBindTimes;
    }

    @Override
    public MerchantBindTimes getMerchantBindTimesByMerchantId(Long merchantId)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.MerchantBindTimes.MERCHANT_ID, new SearchFilter(
            EntityConstant.MerchantBindTimes.MERCHANT_ID, Operator.EQ, merchantId));
        Specification<MerchantBindTimes> spec_MerchantBindTimes = DynamicSpecifications.bySearchFilter(
            filters.values(), MerchantBindTimes.class);
        MerchantBindTimes merchantBindTimes = merchantBindTimesDao.findOne(spec_MerchantBindTimes);
        return merchantBindTimes;
    }

    @Override
    public List<MerchantBindTimes> findAll()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
