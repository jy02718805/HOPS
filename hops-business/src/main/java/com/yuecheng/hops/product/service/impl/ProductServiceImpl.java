package com.yuecheng.hops.product.service.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.product.entity.airtimes.AirtimeProduct;
import com.yuecheng.hops.product.entity.relation.AgentProductRelation;
import com.yuecheng.hops.product.entity.relation.ProductRelation;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;
import com.yuecheng.hops.product.repository.AgentProductRelationDao;
import com.yuecheng.hops.product.repository.AirtimeProductDao;
import com.yuecheng.hops.product.repository.SupplyProductRelationDao;
import com.yuecheng.hops.product.service.ProductService;


@Service("productService")
public class ProductServiceImpl implements ProductService
{
    @Autowired
    private AirtimeProductDao airtimeProductDao;

    @Autowired
    private SupplyProductRelationDao supplyProductRelationDao;

    @Autowired
    private AgentProductRelationDao agentProductRelationDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    @Transactional
    public AirtimeProduct updateProductStatus(AirtimeProduct ap, String status)
    {
        try
        {
            AirtimeProduct airtimeProduct = airtimeProductDao.findOne(ap.getProductId());
            if (airtimeProduct != null)
            {
                airtimeProduct.setProductStatus(status);
                airtimeProductDao.save(airtimeProduct);
            }
            else
            {
                // 修改产品信息时未找到产品！
                String[] msgParams = new String[] {ap.toString(), status};
                String[] viewParams = new String[] {};
                ApplicationException ae = new ApplicationException("businesss000006", msgParams,
                    viewParams);
                throw ExceptionUtil.throwException(ae);
            }
            return airtimeProduct;
        }
        catch (RpcException e)
        {
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            String[] msgParams = new String[] {ap.toString(), status};
            String[] viewParams = new String[] {};
            ApplicationException ae = new ApplicationException("businesss000007", msgParams,
                viewParams);
            throw ExceptionUtil.throwException(ae);
        }
    }
    
    @Override
    public List<AirtimeProduct> getAllProductByStatus(String status)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        if(StringUtils.isNotBlank(status))
        {
	        filters.put(EntityConstant.AirtimeProduct.PRODUCT_STATUS, new SearchFilter(
	            EntityConstant.AirtimeProduct.PRODUCT_STATUS, Operator.EQ,status));
        }
        Specification<AirtimeProduct> spec = DynamicSpecifications.bySearchFilter(
            filters.values(), AirtimeProduct.class);
        List<AirtimeProduct> list = airtimeProductDao.findAll(spec);
        return list;
    }
    
    @Override
    public List<AirtimeProduct> getAllNoDelProduct()
    {
        List<AirtimeProduct> list = (List<AirtimeProduct>)airtimeProductDao.findAllProduct();
        return list;
    }
   
    @Override
    public List<AirtimeProduct> getAllProduct()
    {
        List<AirtimeProduct> list = (List<AirtimeProduct>)airtimeProductDao.findAll();
        return list;
    }

    @Override
    public List<AirtimeProduct> getAllRootProducts()
    {
        List<AirtimeProduct> rootProducts = airtimeProductDao.getAllRootProducts();
        return rootProducts;
    }

    @Override
    public List<AirtimeProduct> getChildProducts(Long parentProductId)
    {
        try
        {
            List<AirtimeProduct> childProducts = airtimeProductDao.getChildProductByParentId(parentProductId);
            return childProducts;
        }
        catch (Exception e)
        {
            LOGGER.error("[ProductServiceImpl. getChildProducts(parentProductId:"+parentProductId+")][异常: "+e.getMessage()+"]");
            return null;
        }
    }

    @Override
    public List<AirtimeProduct> getParentProducts(Long productId)
    {
        try
        {
            List<AirtimeProduct> result = new ArrayList<AirtimeProduct>();
            AirtimeProduct ap = airtimeProductDao.findOne(productId);
            if (ap == null)
            {
                return null;
            }
            if (ap.getProductId().compareTo(ap.getParentProductId()) == 0)
            {
                result.add(ap);
            }
            else
            {
                while (ap.getProductId().compareTo(ap.getParentProductId()) != 0)
                {
                    result.add(ap);
                    ap = airtimeProductDao.findOne(ap.parentProductId);
                }
                result.add(ap);
            }
            return result;
        }
        catch (Exception e)
        {
            LOGGER.error("[ProductServiceImpl. getParentProducts(productId:"+productId+")][异常: "+e.getMessage()+"]");
            return null;
        }
    }

    @Override
    public List<AirtimeProduct> getProductByProvinceId(String proinceId)
    {
        List<AirtimeProduct> list = airtimeProductDao.getProductsByProvince(proinceId);
        return list;
    }

    @Override
    public List<AirtimeProduct> getProductTree(String province, String parValue,
                                               String carrierName, String city, Integer businessType)
    {
		List<AirtimeProduct> result = new ArrayList<AirtimeProduct>();

		if (null == businessType)
		{
			List<AirtimeProduct> ap_cp = airtimeProductDao.queryAirtimeProductsByProperty(carrierName, new BigDecimal(
					parValue));
			List<AirtimeProduct> ap_cpp = airtimeProductDao.queryAirtimeProductsByProperty(carrierName, province,
					new BigDecimal(parValue));
			List<AirtimeProduct> ap_cppc = airtimeProductDao.queryAirtimeProductsByProperty(carrierName, province,
					new BigDecimal(parValue), city);
			result.addAll(ap_cp);
			result.addAll(ap_cpp);
			result.addAll(ap_cppc);
		}
		else
		{
			List<AirtimeProduct> ap_cp = airtimeProductDao.queryAirtimeProductsByPropertyByBusinessType(carrierName,
					new BigDecimal(parValue), businessType);
			List<AirtimeProduct> ap_cpp = airtimeProductDao.queryAirtimeProductsByPropertyByBusinessType(carrierName,
					province, new BigDecimal(parValue), businessType);
			List<AirtimeProduct> ap_cppc = airtimeProductDao.queryAirtimeProductsByPropertyByBusinessType(carrierName,
					province, new BigDecimal(parValue), city, businessType);
			result.addAll(ap_cp);
			result.addAll(ap_cpp);
			result.addAll(ap_cppc);

		}

		return result;
    }

    /*
     * <p>Title: QueryProductByIdentity</p> <p>Description: </p>
     * @param merchant
     * @return
     * @see
     * com.yuecheng.hops.product.service.ProductService#QueryProductByIdentity(com.yuecheng.hops
     * .identity.entity.merchant.Merchant)
     */
    @Override
    public List<ProductRelation> QueryProductByIdentity(Merchant merchant)
    {
        List<ProductRelation> result = new ArrayList<ProductRelation>();
        // 判断产品类型后再处理
        if (merchant.getMerchantType().toString().equalsIgnoreCase(
            Constant.Common.MERCHANTTYPE_AGENT))
        {
            List<AgentProductRelation> agentProductRelationList = agentProductRelationDao.selectProductInfoByIdentity(
                merchant.getId(), merchant.getMerchantName());
            result.addAll(agentProductRelationList);
        }
        else if (merchant.getMerchantType().toString().equalsIgnoreCase(
            Constant.Common.MERCHANTTYPE_SUPPLY))
        {
            List<SupplyProductRelation> supplyProductRelationList = supplyProductRelationDao.selectProductInfoByIdentity(
                merchant.getId(), merchant.getMerchantName());
            result.addAll(supplyProductRelationList);
        }
        return result;
    }

    @Override
    public AirtimeProduct findOne(Long productId)
    {
        AirtimeProduct airtimeProduct = airtimeProductDao.findOne(productId);
        return airtimeProduct;
    }
}
