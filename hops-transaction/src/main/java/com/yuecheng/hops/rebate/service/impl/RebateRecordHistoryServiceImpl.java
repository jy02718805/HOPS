package com.yuecheng.hops.rebate.service.impl;


import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.PageUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.Collections3;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.IdentityService;
import com.yuecheng.hops.rebate.entity.RebateRecordHistory;
import com.yuecheng.hops.rebate.entity.assist.RebateProductAssist;
import com.yuecheng.hops.rebate.entity.assist.RebateRecordHistoryAssist;
import com.yuecheng.hops.rebate.repository.RebateRecordHistoryDao;
import com.yuecheng.hops.rebate.service.RebateProductQueryManager;
import com.yuecheng.hops.rebate.service.RebateRecordHistoryService;


@Service("rebateRecordHistoryService")
public class RebateRecordHistoryServiceImpl implements RebateRecordHistoryService
{
    @Autowired
    private RebateRecordHistoryDao rebateRecordHistoryDao;

    @Autowired
    private RebateProductQueryManager rebateProductService;

    @Autowired
    private IdentityService identityService;

    private static final Logger logger = LoggerFactory.getLogger(RebateRecordHistoryServiceImpl.class);

    @Override
    @Transactional
    public RebateRecordHistoryAssist saveRebateRecordHistory(RebateRecordHistory rebateRecordHistory)
    {
        logger.debug("RebateRecordHistoryServiceImpl:saveRebateRecordHistory("+(BeanUtils.isNotNull(rebateRecordHistory)?rebateRecordHistory.toString():StringUtil.initString())+")");
        rebateRecordHistory= rebateRecordHistoryDao.save(rebateRecordHistory);
        RebateRecordHistoryAssist rebateRecordHistoryAssist=getAllName(rebateRecordHistory);
        logger.debug("RebateRecordHistoryServiceImpl:saveRebateRecordHistory("+(BeanUtils.isNotNull(rebateRecordHistoryAssist)?rebateRecordHistoryAssist.toString():StringUtil.initString())+")[返回信息]");
        return rebateRecordHistoryAssist;
    }

    @Override
    @Transactional
    public void deleteRebateRecordHistory(Long rebateRecordHistoryId)
    {
        logger.debug("RebateRecordHistoryServiceImpl:deleteRebateRecordHistory("
                        + rebateRecordHistoryId + ")");
        rebateRecordHistoryDao.delete(rebateRecordHistoryId);
    }

    @Override
    public RebateRecordHistoryAssist queryRebateRecordHistoryById(Long rebateRecordHistoryId)
    {
        logger.debug("RebateRecordHistoryServiceImpl:queryRebateRecordHistoryById(" + rebateRecordHistoryId + ")");
        RebateRecordHistory rebateRecordHistory = rebateRecordHistoryDao.findOne(rebateRecordHistoryId);
        RebateRecordHistoryAssist rebateRecordHistoryAssist = getAllName(rebateRecordHistory);
        logger.debug("RebateRecordHistoryServiceImpl:queryRebateRecordHistoryById("+(BeanUtils.isNotNull(rebateRecordHistoryAssist)?rebateRecordHistoryAssist.toString():StringUtil.initString())+")[返回信息]");
        return rebateRecordHistoryAssist;
    }

    @Override
    public List<RebateRecordHistoryAssist> queryAllRebateRecordHistory()
    {
        logger.debug("RebateRecordHistoryServiceImpl:queryAllRebateRecordHistory()");
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.RebateRecordHistory.STATUS, new SearchFilter(
            EntityConstant.RebateRecordHistory.STATUS, Operator.EQ,
            Constant.RebateStatus.ENABLE));
        Specification<RebateRecordHistory> spec_RebateRecordHistory = DynamicSpecifications.bySearchFilter(
            filters.values(), RebateRecordHistory.class);
        List<RebateRecordHistory> rebateRecordHistorys = rebateRecordHistoryDao.findAll(spec_RebateRecordHistory);
        List<RebateRecordHistoryAssist> rebateRecordHistoryAssists = getAllName(rebateRecordHistorys);
        logger.debug("RebateRecordHistoryServiceImpl:queryAllRebateRecordHistory("+(BeanUtils.isNotNull(rebateRecordHistoryAssists)?Collections3.convertToString(rebateRecordHistoryAssists, Constant.Common.SEPARATOR):StringUtil.initString())+")[返回信息]");
        return rebateRecordHistoryAssists;
    }

    @Override
    public YcPage<RebateRecordHistoryAssist> queryPageRebateRecordHistory(Map<String, Object> searchParams,
                                                                int pageNumber, int pageSize,
                                                                BSort bsort,Date beginTime,Date endTime)
    {
        logger.debug("[RebateRecordHistoryServiceImpl:queryPageRebateRecordHistory("
                        + (BeanUtils.isNotNull(searchParams ) ? searchParams.toString() :StringUtil.initString()) + ")]");
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        filters.put(EntityConstant.RebateRecordHistory.STATUS, new SearchFilter(
            EntityConstant.RebateRecordHistory.STATUS, Operator.EQ,
            Constant.RebateStatus.ENABLE));
        if(BeanUtils.isNotNull(beginTime))
        {
            filters.put(EntityConstant.RebateRecordHistory.REBATE_START_DATE, new SearchFilter(
                EntityConstant.RebateRecordHistory.REBATE_START_DATE, Operator.GTE,
                    beginTime));
        }
        if(BeanUtils.isNotNull(endTime))
        {
            filters.put(EntityConstant.RebateRecordHistory.REBATE_END_DATE, new SearchFilter(
                EntityConstant.RebateRecordHistory.REBATE_END_DATE, Operator.LTE,
                endTime));
        }
        YcPage<RebateRecordHistory> ycPage = PageUtil.queryYcPage(rebateRecordHistoryDao, filters,
            pageNumber, pageSize, new Sort(Direction.DESC, bsort.getCloumn()),
            RebateRecordHistory.class);
        List<RebateRecordHistory> rebateRecordHistorys = ycPage.getList();
        
        List<RebateRecordHistoryAssist> rebateRecordHistoryAssists = getAllName(rebateRecordHistorys);
        YcPage<RebateRecordHistoryAssist> ycPageAssist=new YcPage<RebateRecordHistoryAssist>(rebateRecordHistoryAssists, ycPage.getPageTotal(), ycPage.getCountTotal());
        logger.debug("RebateRecordHistoryServiceImpl:queryPageRebateRecordHistory("+(BeanUtils.isNotNull(rebateRecordHistoryAssists)?Collections3.convertToString(rebateRecordHistoryAssists, Constant.Common.SEPARATOR):StringUtil.initString())+")[返回信息]");
        return ycPageAssist;
    }

    public List<RebateRecordHistoryAssist> getAllName(List<RebateRecordHistory> listRebateRecordHistory)
    {
        logger.debug("[RebateRecordHistoryServiceImpl:getAllName(" + (BeanUtils.isNotNull(listRebateRecordHistory ) ? Collections3.convertToString(listRebateRecordHistory, Constant.Common.SEPARATOR) :StringUtil.initString())
                                                                                                                                               + ")]");
        List<RebateRecordHistoryAssist> rebateRecordHistoryAssists=new ArrayList<RebateRecordHistoryAssist>();
        for (RebateRecordHistory rebateRecordHistory : listRebateRecordHistory)
        {
            RebateRecordHistoryAssist rebateRecordHistoryAssist = getAllName(rebateRecordHistory);
            rebateRecordHistoryAssists.add(rebateRecordHistoryAssist);
        }
        logger.debug("RebateRecordHistoryServiceImpl:getAllName("+(BeanUtils.isNotNull(rebateRecordHistoryAssists)?Collections3.convertToString(rebateRecordHistoryAssists, Constant.Common.SEPARATOR):StringUtil.initString())+")[返回信息]");
        return rebateRecordHistoryAssists;
    }

    public RebateRecordHistoryAssist getAllName(RebateRecordHistory rebateRecordHistory)
    {
        try
        {
            logger.debug("[RebateRecordHistoryServiceImpl:getAllName(" + (BeanUtils.isNotNull(rebateRecordHistory ) ? rebateRecordHistory.toString() :StringUtil.initString())
                                                                                                                                           + ")]");
            RebateRecordHistoryAssist rebateRecordHistoryAssist=new RebateRecordHistoryAssist();
            if (BeanUtils.isNotNull(rebateRecordHistory))
            {
                Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(
                    rebateRecordHistory.getMerchantId(), IdentityType.MERCHANT);
                Merchant rebateMerchant = (Merchant)identityService.findIdentityByIdentityId(
                    rebateRecordHistory.getRebateMerchantId(), IdentityType.MERCHANT);
                if (BeanUtils.isNotNull(merchant))
                {
                    rebateRecordHistoryAssist.setMerchantName(merchant.getMerchantName());
                }
                if (BeanUtils.isNotNull(rebateMerchant))
                {
                    rebateRecordHistoryAssist.setRebateMerchantName(rebateMerchant.getMerchantName());
                }
                List<RebateProductAssist> rebateProducts = rebateProductService.queryProductsByRProductId(rebateRecordHistory.getRebateProductId());
                rebateRecordHistoryAssist.setRebateProducts(rebateProducts);
                String productNames = StringUtil.initString();
                String productNamesAlt = StringUtil.initString();
                for (int i = 0; i < rebateProducts.size(); i++ )
                {
                    RebateProductAssist rebateProduct = rebateProducts.get(i);
                    if (i <= 2)
                    {
                        productNames = rebateProduct.getProductName();
                    }
                    if(StringUtil.isBlank(productNamesAlt))
                    {
                    	productNamesAlt = rebateProduct.getProductName();
                    }else{
    	                //设置返佣产品全称
    	                productNamesAlt = productNamesAlt + "," + rebateProduct.getProductName();
                    }
                }
                rebateRecordHistoryAssist.setProductNames(productNames);
                rebateRecordHistoryAssist.setProductNamesAlt(productNamesAlt);
                rebateRecordHistoryAssist.setRebateRecordHistory(rebateRecordHistory);
            }
            logger.debug("RebateRecordHistoryServiceImpl:getAllName("+(BeanUtils.isNotNull(rebateRecordHistoryAssist)?rebateRecordHistoryAssist.toString():StringUtil.initString())+")[返回信息]");
            return rebateRecordHistoryAssist;
        }catch(RpcException e){
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[RebateRecordHistoryServiceImpl:getAllName(" + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"getAllName"};
            ApplicationException ae = new ApplicationException("transaction001016", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

}
