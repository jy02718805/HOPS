package com.yuecheng.hops.injection.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.hopscache.HopsCacheUtil;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.PageUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.DateUtil;
import com.yuecheng.hops.injection.entity.MerchantRequest;
import com.yuecheng.hops.injection.entity.MerchantResponse;
import com.yuecheng.hops.injection.repository.MerchantRequestDao;
import com.yuecheng.hops.injection.service.MerchantRequestService;
import com.yuecheng.hops.parameter.entity.ParameterConfiguration;
import com.yuecheng.hops.parameter.service.ParameterConfigurationService;


/**
 * 上游返回规则服务层
 * 
 * @author Jinger 2014-03-26
 */

@Service("merchantRequestService")
public class MerchantRequestServiceImpl implements MerchantRequestService
{
    private static Logger logger = LoggerFactory.getLogger(MerchantRequestServiceImpl.class);

    @Autowired
    private MerchantRequestDao merchantRequestDao;
    
    @Autowired
    private ParameterConfigurationService parameterConfigurationService;

    @Override
    public List<MerchantRequest> getAllMerchantRequest()
    {
        List<MerchantRequest> result = (List<MerchantRequest>)merchantRequestDao.findAll();
        return result;
    }

    @Override
    public MerchantRequest saveMerchantRequest(MerchantRequest merchantRequest)
    {
        merchantRequest = merchantRequestDao.save(merchantRequest);
        return merchantRequest;
    }

    @Override
    public void deleteMerchantRequest(Long merchantId, String interfaceType)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.MerchantRequest.MERCHANT_ID, new SearchFilter(
            EntityConstant.MerchantRequest.MERCHANT_ID, Operator.EQ, merchantId));
        filters.put(EntityConstant.MerchantRequest.INTERFACE_TYPE, new SearchFilter(
            EntityConstant.MerchantRequest.INTERFACE_TYPE, Operator.EQ, interfaceType));
        Specification<MerchantRequest> spec_MerchantRequest = DynamicSpecifications.bySearchFilter(
            filters.values(), MerchantRequest.class);
        List<MerchantRequest> merchantRequests = merchantRequestDao.findAll(spec_MerchantRequest);
        if (merchantRequests != null && merchantRequests.size() > 0)
        {
            merchantRequestDao.delete(merchantRequests);
        }
    }

    @Override
    public MerchantRequest updateMerchantRequest(MerchantRequest merchantRequest)
    {
        merchantRequest = merchantRequestDao.save(merchantRequest);
        return merchantRequest;
    }

    @Override
    public MerchantRequest getMerchantRequestById(Long merchantRequestId)
    {
        MerchantRequest merchantRequest = merchantRequestDao.findOne(merchantRequestId);
        return merchantRequest;
    }

    @Override
    public YcPage<MerchantRequest> queryMerchantRequest(Map<String, Object> searchParams,
                                                        int pageNumber, int pageSize, BSort bsort)
    {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        String orderCloumn = bsort == null ? "id" : bsort.getCloumn();
        String orderDirect = bsort == null ? "DESC" : bsort.getDirect().toString();
        Sort sort = new Sort(Direction.valueOf(Direction.class, orderDirect), orderCloumn);
        Page<MerchantRequest> page = PageUtil.queryPage(merchantRequestDao, filters, pageNumber,
            pageSize, sort, MerchantRequest.class);
        YcPage<MerchantRequest> ycPage = new YcPage<MerchantRequest>();
        ycPage.setList(page.getContent());
        ycPage.setPageTotal(page.getTotalPages());
        ycPage.setCountTotal((int)page.getTotalElements());
        return ycPage;
    }

    @Override
    public void saveMerchantRequestList(List<MerchantRequest> merchantRequestList,
                                        Long merchantId, String interfaceType)
    {
        List<MerchantRequest> merchantRequests = new ArrayList<MerchantRequest>();
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.MerchantRequest.MERCHANT_ID, new SearchFilter(
            EntityConstant.MerchantRequest.MERCHANT_ID, Operator.EQ, merchantId));
        filters.put(EntityConstant.MerchantRequest.INTERFACE_TYPE, new SearchFilter(
            EntityConstant.MerchantRequest.INTERFACE_TYPE, Operator.EQ, interfaceType));
        Specification<MerchantRequest> spec_MerchantRequest = DynamicSpecifications.bySearchFilter(
            filters.values(), MerchantRequest.class);
        List<MerchantRequest> check_merchantRequests = merchantRequestDao.findAll(spec_MerchantRequest);
        if (null != check_merchantRequests && check_merchantRequests.size() > 0)
        {
            merchantRequestDao.delete(check_merchantRequests);
        }
        if (merchantRequestList != null && merchantRequestList.size() > 0)
        {
            for (MerchantRequest merchantRequest : merchantRequestList)
            {
                merchantRequest = merchantRequestDao.save(merchantRequest);
                merchantRequests.add(merchantRequest);
            }
        }
        HopsCacheUtil.put(Constant.Common.BUSINESS_CACHE, Constant.CacheKey.MERCHANT_REQUEST + Constant.StringSplitUtil.ENCODE + merchantId + Constant.StringSplitUtil.ENCODE + interfaceType, merchantRequests);
    }

    @Override
    public List<MerchantRequest> getMerchantRequestByParams(Long merchantId, String interfaceType)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.MerchantRequest.MERCHANT_ID, new SearchFilter(
            EntityConstant.MerchantRequest.MERCHANT_ID, Operator.EQ, merchantId));
        filters.put(EntityConstant.MerchantRequest.INTERFACE_TYPE, new SearchFilter(
            EntityConstant.MerchantRequest.INTERFACE_TYPE, Operator.EQ, interfaceType));
        Specification<MerchantRequest> spec_MerchantRequest = DynamicSpecifications.bySearchFilter(
            filters.values(), MerchantRequest.class);
        List<MerchantRequest> merchantRequests = merchantRequestDao.findAll(spec_MerchantRequest,
            new Sort(Direction.DESC, EntityConstant.MerchantRequest.TIME_DIFFERENCE_LOW));
        return merchantRequests;
    }

    @Override
    public Date getNextQueryDate(Date pastTime, Date now, Long merchantId, String interfaceType)
    {
        long date1 = pastTime.getTime();
        long date2 = now.getTime();
        long millisecond = Math.abs(date2 - date1);
        Date resultDate = new Date();
        Boolean flag = false;
        try
        {
            List<MerchantRequest> merchantRequests = (List<MerchantRequest>)HopsCacheUtil.get(Constant.Common.BUSINESS_CACHE, Constant.CacheKey.MERCHANT_REQUEST + Constant.StringSplitUtil.ENCODE + merchantId + Constant.StringSplitUtil.ENCODE + interfaceType);
            
            if(BeanUtils.isNull(merchantRequests) || merchantRequests.size() == 0)
            {
                Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
                filters.put(EntityConstant.MerchantRequest.MERCHANT_ID, new SearchFilter(EntityConstant.MerchantRequest.MERCHANT_ID, Operator.EQ, merchantId));
                filters.put(EntityConstant.MerchantRequest.INTERFACE_TYPE, new SearchFilter(EntityConstant.MerchantRequest.INTERFACE_TYPE, Operator.EQ, interfaceType));
                Specification<MerchantRequest> spec_MerchantRequest = DynamicSpecifications.bySearchFilter(filters.values(), MerchantRequest.class);
                merchantRequests = (List<MerchantRequest>)merchantRequestDao.findAll(spec_MerchantRequest,new Sort(Direction.DESC, EntityConstant.MerchantRequest.TIME_DIFFERENCE_LOW));
                HopsCacheUtil.put(Constant.Common.BUSINESS_CACHE, Constant.CacheKey.MERCHANT_REQUEST + Constant.StringSplitUtil.ENCODE + merchantId + Constant.StringSplitUtil.ENCODE + interfaceType, merchantRequests);
            }
            
            for (int i = 0; i < merchantRequests.size(); i++ )
            {
                MerchantRequest merchantRequest = merchantRequests.get(i);
                if(merchantRequest.getTimeDifferenceLow() <= millisecond && merchantRequest.getTimeDifferenceHigh() >= millisecond){
                    resultDate = DateUtil.addTime(merchantRequest.getIntervalUnit(), merchantRequest.getIntervalTime().intValue());
                    flag = true;
                }
            }
        }
        catch (Exception e)
        {
            flag = false;
        }
        finally
        {
            if(!flag){
                ParameterConfiguration parameterConfiguration = parameterConfigurationService.getParameterConfigurationByKey(Constant.ParameterConfiguration.DEFAULT_INTERVAL_TIME);
                if(BeanUtils.isNotNull(parameterConfiguration)){
                    resultDate = DateUtil.addTime(parameterConfiguration.getConstantUnitValue(), Integer.valueOf(parameterConfiguration.getConstantValue()));
                }else{
                    resultDate = DateUtil.addTime(Constant.DateUnit.TIME_UNIT_MINUTE, Long.valueOf(Constant.DateUnit.DEFAULT_INTERVAL_TIME).intValue());
                }
            }
        }
        
        return resultDate;
    }
}
