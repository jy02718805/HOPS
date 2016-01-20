/*
 * 文件名：RebateProductQueryManagerImpl.java
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.Collections3;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.IdentityService;
import com.yuecheng.hops.product.entity.relation.AgentProductRelation;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;
import com.yuecheng.hops.product.service.AgentProductRelationService;
import com.yuecheng.hops.product.service.SupplyProductRelationService;
import com.yuecheng.hops.rebate.entity.RebateProduct;
import com.yuecheng.hops.rebate.entity.assist.RebateProductAssist;
import com.yuecheng.hops.rebate.repository.RebateProductDao;
import com.yuecheng.hops.rebate.service.RebateProductQueryManager;

@Service("rebateProductQueryManager")
public class RebateProductQueryManagerImpl implements RebateProductQueryManager
{
    private static final Logger logger = LoggerFactory.getLogger(RebateProductQueryManagerImpl.class);
    @Autowired
    private RebateProductDao rebateProductDao;

    @Autowired
    private AgentProductRelationService agentProductRelationService;

    @Autowired
    private SupplyProductRelationService supplyProductRelationService;

    @Autowired
    private IdentityService identityService;
    @Override
    public List<RebateProductAssist> queryRebateProductByMerchantId(Long merchantId)
    {
        try
        {
            logger.debug("[RebateProductServiceImpl:queryRebateProductByMerchantId(" + merchantId + ")]");
            List<RebateProduct> rebateProductS = rebateProductDao.getRebateProductByMerchantId(merchantId);
            List<RebateProductAssist> rebateProductAssistS = getAllName(rebateProductS);
            logger.debug("[RebateProductServiceImpl:queryRebateProductByMerchantId(" + (BeanUtils.isNotNull(rebateProductAssistS ) ? Collections3.convertToString(rebateProductAssistS, Constant.Common.SEPARATOR) :StringUtil.initString())
                + ")][返回信息]");
            return rebateProductAssistS;
        }
        catch (Exception e)
        {
                logger.error("[RebateProductServiceImpl:queryRebateProductByMerchantId(" + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"queryRebateProductByMerchantId"};
            ApplicationException ae = new ApplicationException("transaction001007", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public List<RebateProductAssist> queryProductsByRProductId(String rebateProductId)
    {
        try
        {
            logger.debug("[RebateProductServiceImpl:queryProductsByRProductId(" + rebateProductId
                            + ")]");
            List<RebateProduct> rebateProductS = rebateProductDao.getRebateProductByRProductId(rebateProductId);
            List<RebateProductAssist> rebateProductAssistS = getAllName(rebateProductS);
            logger.debug("[RebateProductServiceImpl:queryProductsByRProductId(" + (BeanUtils.isNotNull(rebateProductAssistS ) ? Collections3.convertToString(rebateProductAssistS, Constant.Common.SEPARATOR) :StringUtil.initString())
                + ")][返回信息]");
            return rebateProductAssistS;
        }
        catch (Exception e)
        {
            logger.error("[RebateProductServiceImpl:queryProductsByRProductId(" + ExceptionUtil.getStackTraceAsString(e)
                             + ")]");
            String[] msgParams = new String[] {"queryProductsByRProductId"};
            ApplicationException ae = new ApplicationException("transaction001006", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public List<RebateProductAssist> queryProductsByRProductId(List<String> rebateProductIds)
    {
        logger.debug("[RebateProductServiceImpl:queryProductsByRProductId(" + (BeanUtils.isNotNull(rebateProductIds ) ? Collections3.convertToString(rebateProductIds, Constant.Common.SEPARATOR) :StringUtil.initString())
                                                                                                                                        + ")]");
        List<RebateProductAssist> rebateProductAssistS= new ArrayList<RebateProductAssist>();
        for (String rebateProductId : rebateProductIds)
        {
            List<RebateProductAssist> rProductAssistS = this.queryProductsByRProductId(rebateProductId);
            rebateProductAssistS.addAll(rProductAssistS);
        }
        Set<RebateProductAssist> set=new HashSet<RebateProductAssist>();
        set.addAll(rebateProductAssistS);
        rebateProductAssistS= new ArrayList<RebateProductAssist>(set);
        logger.debug("[RebateProductServiceImpl:queryProductsByRProductId(" + (BeanUtils.isNotNull(rebateProductAssistS ) ? Collections3.convertToString(rebateProductAssistS, Constant.Common.SEPARATOR) :StringUtil.initString())
            + ")][返回信息]");
        return rebateProductAssistS;
    }

    @Override
    public <T> List<T> queryNoConfigProducts(List<T> merchantProducts,
                                                               List<RebateProduct> rebateProductS,MerchantType merchantType)
    {
        logger.debug("[RebateProductServiceImpl:queryNoConfigAgentProducts(" + (BeanUtils.isNotNull(merchantProducts ) ? Collections3.convertToString(merchantProducts, Constant.Common.SEPARATOR) :StringUtil.initString())
                                                                                                                              + ","
                                                                                                                              + (BeanUtils.isNotNull(rebateProductS ) ? Collections3.convertToString(rebateProductS, Constant.Common.SEPARATOR) :StringUtil.initString())
                                                                                                                                                                                     + ")]");
        List<T> backProductS = new ArrayList<T>();
        backProductS = this.removeList(merchantProducts, rebateProductS,merchantType);
        logger.debug("[RebateProductServiceImpl:queryNoConfigAgentProducts(" + (BeanUtils.isNotNull(backProductS ) ? Collections3.convertToString(backProductS, Constant.Common.SEPARATOR) :StringUtil.initString())
            + ")][返回信息]");
        return backProductS;
    }

    public <T> List<T> removeList(List<T> products,
                                                      List<RebateProduct> rebateProductS,MerchantType merchantType)
    {
        try
        {
            logger.debug("[RebateProductServiceImpl:removeAgentList(" + (BeanUtils.isNotNull(products ) ? Collections3.convertToString(products, Constant.Common.SEPARATOR) :StringUtil.initString())
                                                                                                                              + ","
                                                                                                                              + (BeanUtils.isNotNull(rebateProductS ) ? Collections3.convertToString(rebateProductS, Constant.Common.SEPARATOR) :StringUtil.initString())
                                                                                                                                                                                     + ")]");
            List<T> backProductS = new ArrayList<T>();
            for (T merchantProduct : products)
            {
                Long productId=null;
                if(MerchantType.AGENT.equals(merchantType))
                {
                    AgentProductRelation agentProduct=(AgentProductRelation)merchantProduct;
                    productId=agentProduct.getProductId();
                }else{
                    SupplyProductRelation supplyProduct=(SupplyProductRelation)merchantProduct;
                    productId=supplyProduct.getProductId();
                }
                boolean flag = true;
                for (RebateProduct rebateProduct : rebateProductS)
                {
                    if (rebateProduct.getProductId().equals(productId))
                    {
                        flag = false;
                        break;
                    }
                }
                if (flag)
                {
                    backProductS.add(merchantProduct);
                }
            }
            logger.debug("[RebateProductServiceImpl:removeAgentList(" + (BeanUtils.isNotNull(backProductS ) ? Collections3.convertToString(backProductS, Constant.Common.SEPARATOR) :StringUtil.initString())
                + ")][返回信息]");
            return backProductS;
        }
        catch (Exception e)
        {
            logger.error("[RebateProductServiceImpl:removeAgentList(" + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"removeAgentList"};
            ApplicationException ae = new ApplicationException("transaction001005", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public List<RebateProductAssist> getAllName(List<RebateProduct> listRebateProduct)
    {
        logger.debug("[RebateProductServiceImpl:getAllName(" + (BeanUtils.isNotNull(listRebateProduct ) ? listRebateProduct.toString() :StringUtil.initString())+ ")]");
        List<RebateProductAssist> rebateProductAssistList=new ArrayList<RebateProductAssist>();
        for (RebateProduct rebateProduct : listRebateProduct)
        {
            RebateProductAssist rebateProductAssist= getAllName(rebateProduct);
            rebateProductAssistList.add(rebateProductAssist);
        }
        logger.debug("[RebateProductServiceImpl:getAllName(" + (BeanUtils.isNotNull(listRebateProduct ) ? Collections3.convertToString(listRebateProduct, Constant.Common.SEPARATOR) :StringUtil.initString())
            + ")][返回信息]");
        return rebateProductAssistList;
    }
    @Override
    public RebateProductAssist getAllName(RebateProduct rebateProduct)
    {
        try
        {
            logger.debug("[RebateProductServiceImpl:getAllName(" + (BeanUtils.isNotNull(rebateProduct ) ? rebateProduct.toString() :StringUtil.initString())
                                                                                                                         + ")]");
            RebateProductAssist rebateProductAssist=new RebateProductAssist();
            if (BeanUtils.isNotNull(rebateProduct))
            {
                //获取商户信息
                Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(
                    rebateProduct.getMerchantId(), IdentityType.MERCHANT);
                rebateProductAssist.setRebateProduct(rebateProduct);
                if (BeanUtils.isNotNull(merchant))
                {
                    rebateProductAssist.setMerchantName(merchant.getMerchantName());
                    //判断商户类型获取商户产品信息
                    if (merchant.getMerchantType().equals(MerchantType.AGENT))
                    {
                        //获取代理商产品信息
                        AgentProductRelation downProduct = agentProductRelationService.queryAgentProductRelationByParams(
                            rebateProduct.getProductId(), rebateProduct.getMerchantId(),null);
                        if (BeanUtils.isNotNull(downProduct))
                        {
                            rebateProductAssist.setProductName(downProduct.getProductName());
                        }
                    }
                    else if (merchant.getMerchantType().equals(MerchantType.SUPPLY))
                    {
                        //获取供货商产品信息
                        SupplyProductRelation supplyProduct = supplyProductRelationService.querySupplyProductRelationByParams(
                            rebateProduct.getProductId(), rebateProduct.getMerchantId(),null);
                        if (BeanUtils.isNotNull(supplyProduct))
                        {
                            rebateProductAssist.setProductName(supplyProduct.getProductName());
                        }
                    }
                    else
                    {
                        //暂无的商户类型产品名称默认暂无
                        rebateProductAssist.setProductName(Constant.Product.NO_PRODUCT_NAME);
                    }
                }
                rebateProductAssist.setRebateProduct(rebateProduct);
            }
            logger.debug("[RebateProductServiceImpl:getAllName(" + (BeanUtils.isNotNull(rebateProductAssist ) ? rebateProductAssist.toString() :StringUtil.initString())
                + ")][返回信息]");
            return rebateProductAssist;
        }catch(RpcException e){
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[RebateProductServiceImpl:getAllName(" + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"getAllName"};
            ApplicationException ae = new ApplicationException("transaction001001", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }
}
