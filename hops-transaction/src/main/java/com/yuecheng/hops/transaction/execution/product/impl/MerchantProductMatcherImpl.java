package com.yuecheng.hops.transaction.execution.product.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.identity.service.merchant.MerchantPageQuery;
import com.yuecheng.hops.numsection.entity.NumSection;
import com.yuecheng.hops.product.entity.airtimes.AirtimeProduct;
import com.yuecheng.hops.product.entity.relation.AgentProductRelation;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;
import com.yuecheng.hops.product.service.AgentProductRelationService;
import com.yuecheng.hops.product.service.ProductPageQuery;
import com.yuecheng.hops.product.service.SupplyProductRelationService;
import com.yuecheng.hops.transaction.execution.product.MerchantProductMatcherTransaction;


@Service("merchantProductMatcher")
public class MerchantProductMatcherImpl implements MerchantProductMatcherTransaction
{
    private static Logger           logger = LoggerFactory.getLogger(MerchantProductMatcherImpl.class);
    @Autowired
    private MerchantPageQuery            merchantService;

    @Autowired
    private AgentProductRelationService  agentProductRelationService;

    @Autowired
    private SupplyProductRelationService supplyProductRelationService;

    @Autowired
    private ProductPageQuery             productPageQuery;
    
    @Override
    public AirtimeProduct matchAirtimeProduct(NumSection numSection, BigDecimal face, int businessType)
    {
        AirtimeProduct airtimeProduct = productPageQuery.queryAirtimeProductByParams(numSection.getProvince().getProvinceId(), face, numSection.getCarrierInfo().getCarrierNo(), numSection.getCity().getCityId(), businessType);
        if (airtimeProduct == null)
        {
            airtimeProduct = productPageQuery.queryAirtimeProductByParams(numSection.getProvince().getProvinceId(), face, numSection.getCarrierInfo().getCarrierNo(), null, businessType);
        }
        if (airtimeProduct == null)
        {
            airtimeProduct = productPageQuery.queryAirtimeProductByParams(null, face, numSection.getCarrierInfo().getCarrierNo(), null, businessType);
        }
        if(null == airtimeProduct){
            logger.error("matchAirtimeProduct is null! numSection["+numSection+"] face["+face+"] businessType["+businessType+"]");
        }
        return airtimeProduct;
    }

    @Override
    public AgentProductRelation matchAgentProduct(Long merchantId, AirtimeProduct product)
    {
        if(BeanUtils.isNotNull(product))
        {
            List<AirtimeProduct> airtimeProducts = getParentProducts(product.getProductId());
            for (AirtimeProduct temp : airtimeProducts)
            {
                AgentProductRelation relation = agentProductRelationService.queryAgentProductRelationByParams(
                    temp.getProductId(), merchantId,Constant.AgentProductStatus.OPEN_STATUS);
                if (relation != null && relation.isDefValue())
                {
                    return relation;
                }
            }
        }
        return null;
    }

    @Override
    public SupplyProductRelation matchSupplyProduct(Long merchantId, NumSection numSection,
                                                    BigDecimal face, int resourceType)
    {
        AirtimeProduct airtimeProduct = productPageQuery.queryAirtimeProductByParams(
            numSection.getProvince().getProvinceId(), face, numSection.getCarrierInfo().getCarrierNo(),
            numSection.getCity().getCityId(),resourceType);
        if (airtimeProduct == null)
        {
            airtimeProduct = productPageQuery.queryAirtimeProductByParams(
                numSection.getProvince().getProvinceId(), face, numSection.getCarrierInfo().getCarrierNo(), null, resourceType);
        }
        List<AirtimeProduct> airtimeProducts = getParentProducts(airtimeProduct.getProductId());
        for (AirtimeProduct temp : airtimeProducts)
        {
            SupplyProductRelation relation = supplyProductRelationService.querySupplyProductRelationByParams(
                temp.getProductId(), merchantId,Constant.SupplyProductStatus.OPEN_STATUS);
            if (relation != null)
            {
                return relation;
            }
        }
        return null;
    }

    public List<AirtimeProduct> getParentProducts(Long productId)
    {
        List<AirtimeProduct> airtimeProducts = new ArrayList<AirtimeProduct>();
        Long queryProductId = productId;
        while (true)
        {
            AirtimeProduct airtimeProduct = productPageQuery.queryAirtimeProductById(queryProductId);
            if (airtimeProduct != null)
            {
                airtimeProducts.add(airtimeProduct);
                if (airtimeProduct.getParentProductId().compareTo(airtimeProduct.getProductId()) == 0)
                {
                    break;
                }
                else
                {
                    queryProductId = airtimeProduct.getParentProductId();
                }
            }
            else
            {
                String[] msgParams = new String[] {productId.toString()};
                String[] viewParams = new String[] {};
                // throw new ApplicationException("transaction000001",msgParams,viewParams,new
                // Exception());
                ApplicationException ae = new ApplicationException("transaction000001", msgParams,
                    viewParams);
                throw ExceptionUtil.throwException(ae);
            }
        }
        return airtimeProducts;
    }

	@Override
	public AirtimeProduct matchAirtimeProduct(String agentProdId, Long merchantId)
	{
		AgentProductRelation aProductRelation = agentProductRelationService.queryAgentProductRelationByParams(
				agentProdId, merchantId, Constant.AgentProductStatus.OPEN_STATUS);
		if (aProductRelation == null)
		{
			return null;
		}
		return productPageQuery.queryAirtimeProductById(aProductRelation.getProductId());
	}

}
