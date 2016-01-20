/*
 * 文件名：RebateRuleQueryManagerImpl.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2014年10月24日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.rebate.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.Collections3;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.IdentityService;
import com.yuecheng.hops.rebate.entity.RebateRule;
import com.yuecheng.hops.rebate.entity.RebateTradingVolume;
import com.yuecheng.hops.rebate.entity.assist.RebateProductAssist;
import com.yuecheng.hops.rebate.entity.assist.RebateRuleAssist;
import com.yuecheng.hops.rebate.repository.RebateRuleDao;
import com.yuecheng.hops.rebate.service.RebateProductQueryManager;
import com.yuecheng.hops.rebate.service.RebateRuleQueryManager;
import com.yuecheng.hops.rebate.service.RebateTradingVolumeService;

@Service("rebateRuleQueryManager")
public class RebateRuleQueryManagerImpl implements RebateRuleQueryManager
{
    @Autowired
    private RebateRuleDao rebateRuleDao;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private RebateProductQueryManager rebateProductService;

    @Autowired
    private RebateTradingVolumeService rebateTradingVolumeService;

    private static Logger logger = LoggerFactory.getLogger(RebateRuleQueryManagerImpl.class);

    @Override
    public List<RebateRuleAssist> queryRebateRuleList(String rebateProductId, Long rebateTimeType)
    {
        logger.debug("[RebateRuleServiceImpl:queryRebateRuleList(" + rebateProductId + ","
                        + rebateTimeType + ")]");
        List<RebateRule> rebateRules=rebateRuleDao.getRebateRule(rebateProductId, rebateTimeType);
        List<RebateRuleAssist> rebateRuleAssists = getAllName(rebateRules);
        logger.debug("[RebateRuleServiceImpl:queryRebateRuleList(" + (BeanUtils.isNotNull(rebateRuleAssists) ? Collections3.convertToString(rebateRuleAssists, Constant.Common.SEPARATOR) :StringUtil.initString())
            + ")][返回信息]");
        return rebateRuleAssists;
    }

    @Override
    public YcPage<RebateRuleAssist> queryPageRebateRule(Map<String, Object> searchParams, int pageNumber,
                                              int pageSize, BSort bsort)
    {
        logger.debug("[RebateRuleServiceImpl:queryPageRebateRule(" + (BeanUtils.isNotNull(searchParams) ? searchParams.toString() :StringUtil.initString())
                                                                                                                     + ")]");
        YcPage<RebateRule> ycPage = rebateRuleDao.queryPageRebateRule(searchParams, pageNumber, pageSize);
        List<RebateRule> rebateRuleList = ycPage.getList();
        
        List<RebateRuleAssist> rebateRuleAssists = getAllName(ycPage.getList());
        YcPage<RebateRuleAssist> ycPageAssist=new YcPage<RebateRuleAssist>();
        ycPageAssist.setCountTotal(ycPage.getCountTotal());
        ycPageAssist.setList(rebateRuleAssists);
        ycPageAssist.setPageTotal(ycPage.getPageTotal());
        logger.debug("[RebateRuleServiceImpl:queryPageRebateRule(" + (BeanUtils.isNotNull(rebateRuleList) ? Collections3.convertToString(rebateRuleList, Constant.Common.SEPARATOR) :StringUtil.initString())
            + ")][返回信息]");
        return ycPageAssist;
    }

    @Override
    public List<RebateRuleAssist> queryRebateRulesByParams(Long merchantId, String type)
    {
        try
        {
            logger.debug("[RebateRuleServiceImpl:queryRebateRuleByParams(" + merchantId + ","
                            + type + ")]");
            List<RebateRule> rebateRuleList = new ArrayList<RebateRule>();
            //判断是否是查询发生商户或发生商户及返佣商户
            if (type.equals(Constant.RebateRuleMerchantType.MERCHANT))
            {
                //仅查询发生商户
                rebateRuleList = rebateRuleDao.getRebateRuleByMerchantId(merchantId);
            }
            else
            {
                //查询发生商户及返佣商户
                rebateRuleList = rebateRuleDao.getRebateRuleByMerchantIdLike(merchantId);
            }
            List<RebateRuleAssist> rebateRuleAssists = getAllName(rebateRuleList);
            logger.debug("[RebateRuleServiceImpl:queryRebateRuleByParams(" + (BeanUtils.isNotNull(rebateRuleAssists) ? Collections3.convertToString(rebateRuleAssists, Constant.Common.SEPARATOR) :StringUtil.initString())
                + ")][返回信息]");
            return rebateRuleAssists;
        }catch(RpcException e){
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[RebateRuleServiceImpl:queryRebateRuleByParams(" + ExceptionUtil.getStackTraceAsString(e)
                             + ")]");
            String[] msgParams = new String[] {"queryRebateRuleByParams"};
            ApplicationException ae = new ApplicationException("transaction001017", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public List<RebateRuleAssist> queryRebateRuleByParams(Long merchantId, String status)
    {
        logger.debug("[RebateRuleServiceImpl:queryRebateRuleByParams(" + merchantId + ","
                        + status + ")]");
        List<RebateRule> rebateRuleList = rebateRuleDao.getRebateRuleByParams(merchantId, status);
        List<RebateRuleAssist> rebateRuleAssists = getAllName(rebateRuleList);
        logger.debug("[RebateRuleServiceImpl:queryRebateRuleByParams(" + (BeanUtils.isNotNull(rebateRuleAssists) ? Collections3.convertToString(rebateRuleAssists, Constant.Common.SEPARATOR) :StringUtil.initString())
            + ")][返回信息]");
        return rebateRuleAssists;
    }

    @Override
    public List<RebateRuleAssist> queryRebateRuleByParams(Long merchantId, Long rebateMerchantId)
    {
            logger.debug("[RebateRuleServiceImpl:queryRebateRuleByParams(" + merchantId + ","
                        + rebateMerchantId + ")]");
        List<RebateRule> rebateRuleList = rebateRuleDao.getRebateRuleByMerchantId(merchantId,
            rebateMerchantId);
        List<RebateRuleAssist> rebateRuleAssists = getAllName(rebateRuleList);
        logger.debug("[RebateRuleServiceImpl:queryRebateRuleByParams(" + (BeanUtils.isNotNull(rebateRuleAssists) ? Collections3.convertToString(rebateRuleAssists, Constant.Common.SEPARATOR) :StringUtil.initString())
            + ")][返回信息]");
        return rebateRuleAssists;
    }

    @Override
    public List<RebateRuleAssist> queryRebateRuleByRPId(String rebateProductId)
    {
        logger.debug("[RebateRuleServiceImpl:queryRebateRuleByMId(" + rebateProductId + ")]");
        List<RebateRule> rebateRuleList = rebateRuleDao.getRebateRule(rebateProductId);
        List<RebateRuleAssist> rebateRuleAssists = getAllName(rebateRuleList);
        logger.debug("[RebateRuleServiceImpl:queryRebateRuleByMId(" + (BeanUtils.isNotNull(rebateRuleAssists) ? Collections3.convertToString(rebateRuleAssists, Constant.Common.SEPARATOR) :StringUtil.initString())
            + ")][返回信息]");
        return rebateRuleAssists;
    }

    @Override
    public List<RebateRuleAssist> queryRebateRuleByMId(Long merchantId)
    {
        logger.debug("[RebateRuleServiceImpl:queryRebateRuleByMId(" + merchantId + ")]");
        List<RebateRule> rebateRuleList = rebateRuleDao.getRebateRuleByParams(merchantId);
        List<RebateRuleAssist> rebateRuleAssists = getAllName(rebateRuleList);
        logger.debug("[RebateRuleServiceImpl:queryRebateRuleByMId(" + (BeanUtils.isNotNull(rebateRuleAssists) ? Collections3.convertToString(rebateRuleAssists, Constant.Common.SEPARATOR) :StringUtil.initString())
            + ")][返回信息]");
        return rebateRuleAssists;
    }
    
    @Override
    public List<RebateRuleAssist> getAllName(List<RebateRule> listRebateRule)
    {
        logger.debug("[RebateRuleServiceImpl:getAllName(" + (BeanUtils.isNotNull(listRebateRule) ? Collections3.convertToString(listRebateRule, Constant.Common.SEPARATOR) :StringUtil.initString())
                                                                                                                    + ")]");
        List<RebateRuleAssist> rebateRuleAssists=new ArrayList<RebateRuleAssist>();
        for (RebateRule rebateRule : listRebateRule)
        {
            RebateRuleAssist rebateRuleAssist = getAllName(rebateRule);
            rebateRuleAssists.add(rebateRuleAssist);
        }
        logger.debug("[RebateRuleServiceImpl:getAllName(" + (BeanUtils.isNotNull(rebateRuleAssists) ? Collections3.convertToString(rebateRuleAssists, Constant.Common.SEPARATOR) :StringUtil.initString())
            + ")][返回信息]");
        return rebateRuleAssists;
    }
    @Override
    public RebateRuleAssist getAllName(RebateRule rebateRule)
    {
        logger.debug("[RebateRuleServiceImpl:getAllName(" + (BeanUtils.isNotNull(rebateRule) ? rebateRule.toString() :StringUtil.initString())
                                                                                                            + ")]");
        RebateRuleAssist rebateRuleAssist=new RebateRuleAssist();
        if (BeanUtils.isNotNull(rebateRule))
        {
            //获取发生商户信息
            Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(
                rebateRule.getMerchantId(), IdentityType.MERCHANT);
            //获取返佣商户信息
            Merchant rebateMerchant = (Merchant)identityService.findIdentityByIdentityId(
                rebateRule.getRebateMerchantId(), IdentityType.MERCHANT);
            if (BeanUtils.isNotNull(merchant))
            {
                //设置发生商户名称
                rebateRuleAssist.setMerchantName(merchant.getMerchantName());
            }
            if (BeanUtils.isNotNull(rebateMerchant))
            {
                //设置返佣商户名称
                rebateRuleAssist.setRebateMerchantName(rebateMerchant.getMerchantName());
            }
            //获取返佣产品信息列表
            List<RebateProductAssist> rebateProducts = rebateProductService.queryProductsByRProductId(rebateRule.getRebateProductId());
            rebateRuleAssist.setRebateProducts(rebateProducts);
            String productNames = StringUtil.initString();
            String productNamesAlt = StringUtil.initString();
            for (int i = 0; i < rebateProducts.size(); i++ )
            {
                RebateProductAssist rebateProduct = rebateProducts.get(i);
                if (i <= 2)
                {
                    //设置返佣产品简称
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
            rebateRuleAssist.setProductNames(productNames);
            rebateRuleAssist.setProductNamesAlt(productNamesAlt);
            //获取返佣交易量区间数据信息列表
            List<RebateTradingVolume> rebateTradingVolumes = rebateTradingVolumeService.queryRebateTradingVolumesByParams(rebateRule.getRebateRuleId());
            rebateRuleAssist.setRebateTradingVolume(rebateTradingVolumes);
            rebateRuleAssist.setRebateRule(rebateRule);
        }
        logger.debug("[RebateRuleServiceImpl:getAllName(" + (BeanUtils.isNotNull(rebateRuleAssist) ? rebateRuleAssist.toString() :StringUtil.initString())
            + ")][返回信息]");
        return rebateRuleAssist;
    }
}
