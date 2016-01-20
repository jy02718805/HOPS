package com.yuecheng.hops.parameter.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.PageUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.IdentityService;
import com.yuecheng.hops.parameter.entity.ParameterConfiguration;
import com.yuecheng.hops.parameter.entity.SupplyDupNumRule;
import com.yuecheng.hops.parameter.repository.SupplyDupNumRuleDao;
import com.yuecheng.hops.parameter.service.SupplyDupNumRuleService;


@Service("supplyDupNumRuleService")
public class SupplyDupNumRuleServiceImpl implements SupplyDupNumRuleService
{
    @Autowired
    private SupplyDupNumRuleDao supplyDupNumRuleDao;
    
    @Autowired
    private IdentityService identityService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SupplyDupNumRuleServiceImpl.class);

    @Override
    public SupplyDupNumRule getSupplyDupNumRule(Long id)
    {
        SupplyDupNumRule supplyDupNumRule = supplyDupNumRuleDao.findOne(id);
        return supplyDupNumRule;
    }

    @Override
    public YcPage<SupplyDupNumRule> querySupplyDupNumRule(Map<String, Object> searchParams,
                                                            int pageNumber, int pageSize,
                                                            String sortType)
    {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        YcPage<SupplyDupNumRule> page = PageUtil.queryYcPage(supplyDupNumRuleDao,
            filters, pageNumber, pageSize, new Sort(Direction.DESC, sortType),
            ParameterConfiguration.class);
        List<SupplyDupNumRule> supplyDupNumRuleList = page.getList();
        for (SupplyDupNumRule supplyDupNumRule : supplyDupNumRuleList)
        {
            Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(
                supplyDupNumRule.getMerchantId(), IdentityType.MERCHANT);
            supplyDupNumRule.setMerchantName(merchant.getMerchantName());
        }
        page.setList(supplyDupNumRuleList);
        return page;
    }
    
    @Override
    public void deleteSupplyDupNumRule(Long id){
        supplyDupNumRuleDao.delete(id);
    }

    @Override
    public SupplyDupNumRule saveSupplyDupNumRule(SupplyDupNumRule supplyDupNumRule)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.MerchantBindTimes.MERCHANT_ID, new SearchFilter(
            EntityConstant.MerchantBindTimes.MERCHANT_ID, Operator.EQ, supplyDupNumRule.getMerchantId()));
        Specification<SupplyDupNumRule> spec_SupplyDupNumRule = DynamicSpecifications.bySearchFilter(
            filters.values(), SupplyDupNumRule.class);
        List<SupplyDupNumRule> supplyDupNumRules = supplyDupNumRuleDao.findAll(spec_SupplyDupNumRule);
        if(BeanUtils.isNotNull(supplyDupNumRules) && supplyDupNumRules.size() > 0){
            throw ExceptionUtil.throwException(new ApplicationException("businesss000026",
                new String[] {String.valueOf(supplyDupNumRule).toString()}));
        }
        supplyDupNumRule = supplyDupNumRuleDao.save(supplyDupNumRule);
        return supplyDupNumRule;
    }

    @Override
    public SupplyDupNumRule updateSupplyDupNumRule(SupplyDupNumRule supplyDupNumRule)
    {
        supplyDupNumRule = supplyDupNumRuleDao.save(supplyDupNumRule);
        return supplyDupNumRule;
    }

    @Override
    public SupplyDupNumRule getSupplyDupNumRuleByMerchantId(Long merchantId)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.MerchantBindTimes.MERCHANT_ID, new SearchFilter(
            EntityConstant.MerchantBindTimes.MERCHANT_ID, Operator.EQ, merchantId));
        Specification<SupplyDupNumRule> spec_SupplyDupNumRule = DynamicSpecifications.bySearchFilter(
            filters.values(), SupplyDupNumRule.class);
        SupplyDupNumRule supplyDupNumRule = supplyDupNumRuleDao.findOne(spec_SupplyDupNumRule);
        return supplyDupNumRule;
    }

    @Override
    public List<SupplyDupNumRule> findAll()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
