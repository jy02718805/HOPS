package com.yuecheng.hops.injection.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.injection.entity.MerchantResponse;
import com.yuecheng.hops.injection.repository.MerchantResponseDao;
import com.yuecheng.hops.injection.service.MerchantResponseService;


/**
 * 上游返回规则服务层
 * 
 * @author Jinger 2014-03-26
 */

@Service("merchantResponseService")
public class MerchantResponseServiceImpl implements MerchantResponseService
{
    private static Logger logger = LoggerFactory.getLogger(MerchantResponseServiceImpl.class);

    @Autowired
    private MerchantResponseDao merchantResponseDao;


    @Override
    public List<MerchantResponse> saveMerchantResponseList(List<MerchantResponse> merchantResponseList,
                                         Long merchantId, String interfaceType)
    {
        List<MerchantResponse> result = new ArrayList<MerchantResponse>();
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.MerchantResponse.MERCHANT_ID, new SearchFilter(EntityConstant.MerchantResponse.MERCHANT_ID, Operator.EQ, merchantId));
        filters.put(EntityConstant.MerchantResponse.INTERFACE_TYPE, new SearchFilter(EntityConstant.MerchantResponse.INTERFACE_TYPE, Operator.EQ, interfaceType));
        Specification<MerchantResponse> spec_MerchantResponse = DynamicSpecifications.bySearchFilter(filters.values(), MerchantResponse.class);
        List<MerchantResponse> check_MerchantResponses = merchantResponseDao.findAll(spec_MerchantResponse);
        if (null != check_MerchantResponses && check_MerchantResponses.size() > 0)
        {
            merchantResponseDao.delete(check_MerchantResponses);
        }
        if (BeanUtils.isNotNull(merchantResponseList) && merchantResponseList.size() > 0)
        {
            for (MerchantResponse merchantResponse : merchantResponseList)
            {
                merchantResponseDao.save(merchantResponse);
                result.add(merchantResponse);
            }
        }
        return result;
    }

    @Override
    public List<MerchantResponse> getMerchantResponseListByParams(Long merchantId, String interfaceType)
    {
        if (logger.isInfoEnabled())
        {
            logger.info("[MerchantResponseServiceImpl:getMerchantResponseByParams(" + merchantId
                        + "," + interfaceType + ")]");

        }
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.MerchantResponse.MERCHANT_ID, new SearchFilter(
            EntityConstant.MerchantResponse.MERCHANT_ID, Operator.EQ, merchantId));
        filters.put(EntityConstant.MerchantResponse.INTERFACE_TYPE, new SearchFilter(
            EntityConstant.MerchantResponse.INTERFACE_TYPE, Operator.EQ, interfaceType));
        Specification<MerchantResponse> spec_MerchantResponse = DynamicSpecifications.bySearchFilter(
            filters.values(), MerchantResponse.class);
        List<MerchantResponse> merchantResponses = merchantResponseDao.findAll(spec_MerchantResponse);
        return merchantResponses;
    }

    @Override
    public MerchantResponse getMerchantResponseByParams(Long merchantId, String interfaceType,
                                                        String errorCode, String merchantStatus)
    {
        if (logger.isInfoEnabled())
        {
            logger.info("[MerchantResponseServiceImpl:getMerchantResponseByParams(" + merchantId
                        + "," + interfaceType + "," + errorCode + "," + merchantStatus + ")]");

        }
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.MerchantResponse.MERCHANT_ID, new SearchFilter(
            EntityConstant.MerchantResponse.MERCHANT_ID, Operator.EQ, merchantId));
        filters.put(EntityConstant.MerchantResponse.INTERFACE_TYPE, new SearchFilter(
            EntityConstant.MerchantResponse.INTERFACE_TYPE, Operator.EQ, interfaceType));
        if(BeanUtils.isNotNull(errorCode)){
            filters.put(EntityConstant.MerchantResponse.ERROR_CODE, new SearchFilter(
                EntityConstant.MerchantResponse.ERROR_CODE, Operator.EQ, errorCode));
        }
        if (merchantStatus != null && !merchantStatus.isEmpty())
        {
            filters.put(EntityConstant.MerchantResponse.MERCHANT_STATUS, new SearchFilter(
                EntityConstant.MerchantResponse.MERCHANT_STATUS, Operator.EQ, merchantStatus));
        }
        Specification<MerchantResponse> spec_MerchantResponse = DynamicSpecifications.bySearchFilter(
            filters.values(), MerchantResponse.class);
        MerchantResponse merchantResponse = merchantResponseDao.findOne(spec_MerchantResponse);
        return merchantResponse;
    }
}
