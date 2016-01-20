package com.yuecheng.hops.rebate.service.impl;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.IdentityService;
import com.yuecheng.hops.identity.service.merchant.MerchantStatusManagement;
import com.yuecheng.hops.product.entity.relation.AgentProductRelation;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;
import com.yuecheng.hops.product.service.AgentProductRelationService;
import com.yuecheng.hops.product.service.ProductService;
import com.yuecheng.hops.product.service.SupplyProductRelationService;
import com.yuecheng.hops.rebate.entity.RebateProduct;
import com.yuecheng.hops.rebate.entity.assist.RebateProductAssist;
import com.yuecheng.hops.rebate.repository.RebateProductDao;
import com.yuecheng.hops.rebate.service.RebateProductQueryManager;
import com.yuecheng.hops.rebate.service.RebateProductService;


@Service("rebateProductService")
public class RebateProductServiceImpl implements RebateProductService
{

    @Autowired
    private RebateProductDao rebateProductDao;

    private static Logger logger = LoggerFactory.getLogger(RebateProductServiceImpl.class);

    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    @Autowired
    private AgentProductRelationService agentProductRelationService;

    @Autowired
    private SupplyProductRelationService supplyProductRelationService;

    @Autowired
    private MerchantStatusManagement merchantService;
    
    @Autowired
    private RebateProductQueryManager rebateProductQueryManager;

    @Autowired
    private ProductService productService;

    @Autowired
    private IdentityService identityService;

    @Override
    @Transactional
    public RebateProductAssist saveRebateProduct(RebateProduct rebateProduct)
    {
        logger.debug("[RebateProductServiceImpl:saveRebateProduct(" + (BeanUtils.isNotNull(rebateProduct ) ? rebateProduct.toString() :StringUtil.initString())+ ")]");
        rebateProduct= rebateProductDao.save(rebateProduct);
        RebateProductAssist rebateProductAssist=rebateProductQueryManager.getAllName(rebateProduct);
        logger.debug("[RebateProductServiceImpl:saveRebateProduct(" + (BeanUtils.isNotNull(rebateProductAssist ) ? rebateProductAssist.toString() :StringUtil.initString())
            + ")][返回信息]");
        return rebateProductAssist;
    }

    @Override
    @Transactional
    public void deleteRebateProduct(Long id)
    {
        logger.debug("[RebateProductServiceImpl:deleteRebateProduct(" + id + ")]");
        rebateProductDao.delete(id);
    }

    @Override
    public RebateProductAssist queryRebateProductById(Long id)
    {
        logger.debug("[RebateProductServiceImpl:queryRebateProductById(" + id + ")]");
        RebateProduct rebateProduct = rebateProductDao.findOne(id);
        RebateProductAssist rebateProductAssist= rebateProductQueryManager.getAllName(rebateProduct);
        logger.debug("[RebateProductServiceImpl:queryRebateProductById(" + (BeanUtils.isNotNull(rebateProductAssist ) ? rebateProductAssist.toString() :StringUtil.initString())
            + ")][返回信息]");
        return rebateProductAssist;
    }

    
    @Override
    @Transactional
    public void deleteRebateProductByRProductId(String rebateProductId)
    {
        try
        {
            logger.debug("[RebateProductServiceImpl:deleteRebateProductByRProductId("
                            + rebateProductId + ")]");
            List<RebateProductAssist> list = rebateProductQueryManager.queryProductsByRProductId(rebateProductId);
            for (RebateProductAssist rebateProduct : list)
            {
//                deleteRebateProduct(rebateProduct.getRebateProduct().getId());
            	rebateProductDao.delete(rebateProduct.getRebateProduct().getId());
            }
        }catch(RpcException e){
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[RebateProductServiceImpl:deleteRebateProductByRProductId("
                             + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"deleteRebateProductByRProductId"};
            ApplicationException ae = new ApplicationException("transaction001004", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public String getRebateProductStr(String rebateProductId)
    {
        try
        {
            logger.debug("[RebateProductServiceImpl:getRebateProductStr(" + rebateProductId
                            + ")]");
            String backStr = StringUtil.initString();
            if (BeanUtils.isNotNull(rebateProductId ) && !rebateProductId.isEmpty())
            {
                List<RebateProductAssist> rebateProductList = rebateProductQueryManager.queryProductsByRProductId(rebateProductId);
                for (RebateProductAssist rebateProduct : rebateProductList)
                {
                    backStr = backStr + rebateProduct.getProductName() + ",";
                }
            }
            logger.debug("[RebateProductServiceImpl:getRebateProductStr(" + backStr + ")][返回信息]");
            return backStr;
        }catch(RpcException e){
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[RebateProductServiceImpl:getRebateProductStr(" + ExceptionUtil.getStackTraceAsString(e)
                             + ")]");
            String[] msgParams = new String[] {"getRebateProductStr"};
            ApplicationException ae = new ApplicationException("transaction001003", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    @Transactional
    public String saveRebateProductIds(String rebateProductId, Merchant merchant, String productIds)
    {
        try
        {
            logger.debug("[RebateProductServiceImpl:saveRebateProductId(" + rebateProductId
                            + "," + (BeanUtils.isNotNull(merchant ) ? merchant.toString() :StringUtil.initString()) + ","
                                                                             + productIds + ")]");
            if (BeanUtils.isNotNull(rebateProductId ))
            {
                deleteRebateProductByRProductId(rebateProductId);
            }
            else
            {
                rebateProductId = "Pid" + formatter.format(new Date()) + StringUtil.getRandomString(4);
            }
            if (StringUtil.isNotBlank(productIds))
            {
                String[] downProductIdlist = productIds.split(Constant.StringSplitUtil.DECODE);
                for (String downProductId : downProductIdlist)
                {
                    Long productId=new Long(downProductId);
                    RebateProduct rebateProduct=saveRebateProductId(rebateProductId, merchant, productId);
                    rebateProduct= rebateProductDao.save(rebateProduct);
                }
                logger.debug("[RebateProductServiceImpl:saveRebateProductId(" + rebateProductId + ")][返回信息]");
                return rebateProductId;
            }else
            {
                logger.error("[RebateProductServiceImpl:saveRebateProductId(产品ID为空)]");
                String[] msgParams = new String[] {"saveRebateProductId"};
                ApplicationException ae = new ApplicationException("transaction002001", msgParams);
                throw ExceptionUtil.throwException(ae);
            }
        }catch(RpcException e){
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[RebateProductServiceImpl:saveRebateProductId(" + ExceptionUtil.getStackTraceAsString(e)
                             + ")]");
            String[] msgParams = new String[] {"saveRebateProductId"};
            ApplicationException ae = new ApplicationException("transaction001002", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }
    
    public RebateProduct saveRebateProductId(String rebateProductId, Merchant merchant, Long productId)
    {
        logger.debug("[RebateProductServiceImpl:saveRebateProductId("+rebateProductId+","+(BeanUtils.isNotNull(merchant)?merchant.toString():StringUtil.initString())+","+productId+")]");
        RebateProduct rebateProduct = new RebateProduct();
        rebateProduct.setRebateProductId(rebateProductId);
        rebateProduct.setMerchantId(merchant.getId());
        if (merchant.getMerchantType().equals(MerchantType.AGENT))
        {
            AgentProductRelation downProduct = agentProductRelationService.queryAgentProductRelationById(productId);
            rebateProduct.setProductId(downProduct.getProductId());
        }
        else
        {
            SupplyProductRelation supplyProduct = supplyProductRelationService.querySupplyProductRelationById(productId);
            rebateProduct.setProductId(supplyProduct.getProductId());
        }
        return rebateProduct;
    }

}
