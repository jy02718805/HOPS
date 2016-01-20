package com.yuecheng.hops.product.service.impl;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.yuecheng.hops.common.Constant.StringSplitUtil;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.DecimalPlaces;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.hopscache.HopsCacheUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.BigDecimalUtil;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.product.entity.airtimes.AirtimeProduct;
import com.yuecheng.hops.product.repository.AirtimeProductDao;
import com.yuecheng.hops.product.service.AgentProductRelationService;
import com.yuecheng.hops.product.service.ProductManagement;
import com.yuecheng.hops.product.service.ProductPageQuery;
import com.yuecheng.hops.product.service.SupplyProductRelationService;


@Service("productManagement")
public class ProductManagementImpl implements ProductManagement
{
    @Autowired
    private AirtimeProductDao airtimeProductDao;

    @Autowired
    private AgentProductRelationService agentProductRelationService;

    @Autowired
    private SupplyProductRelationService supplyProductRelationService;

    @Autowired
    private ProductPageQuery productPageQuery;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductManagementImpl.class);

    @Override
    @Transactional
    public AirtimeProduct saveProduct(AirtimeProduct ap)
    {
        try
        {
            LOGGER.debug("[产品保存][ProductManagementImpl :saveProduct(AirtimeProduct:"
                         + ap.toString() + ")]");
            exisProduct(ap);
            Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
            String productNo = getProductNo(ap, filters);
            ap.setProductNo(productNo);

            filters.put(EntityConstant.AirtimeProduct.PRODUCT_NO, new SearchFilter(
                EntityConstant.AirtimeProduct.PRODUCT_NO, Operator.EQ, productNo));
            filters.put(EntityConstant.AirtimeProduct.PRODUCT_STATUS, new SearchFilter(
                EntityConstant.AirtimeProduct.PRODUCT_STATUS, Operator.LT,
                Constant.Product.PRODUCT_DELETE));
            Specification<AirtimeProduct> spec = DynamicSpecifications.bySearchFilter(
                filters.values(), AirtimeProduct.class);
            AirtimeProduct product = airtimeProductDao.findOne(spec);
            // 产品状态：在删除下
            if (product == null)
            {
                ap = airtimeProductDao.save(ap);
                if (ap.getParentProductId() == -1l)
                {
                    ap.setParentProductId(ap.getProductId());
                    airtimeProductDao.save(ap);
                    String cacheKey = Constant.CacheKey.PRODUCT + ap.getCarrierName()
                                      + StringSplitUtil.ENCODE + ap.getProvince()
                                      + StringSplitUtil.ENCODE + ap.getCity()
                                      + StringSplitUtil.ENCODE + ap.getParValue()
                                      + StringSplitUtil.ENCODE + ap.getBusinessType();
                    HopsCacheUtil.put(Constant.Common.BUSINESS_CACHE, cacheKey, ap);
                }
            }
            else
            {
                ApplicationException e = new ApplicationException("businesss000023",
                    new String[] {ap.toString()});
                throw ExceptionUtil.throwException(e);
            }

            LOGGER.debug("[产品保存结束][ProductManagementImpl :saveProduct(AirtimeProduct:"
                         + ap.toString() + ")]");
            return ap;
        }
        catch (RpcException e)
        {
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            String[] msgParams = new String[] {ap.toString()};
            String[] viewParams = new String[] {};
            // throw new ApplicationException("businesss000003",msgParams,viewParams,new
            // Exception());
            ApplicationException ae = new ApplicationException("businesss000003", msgParams,
                viewParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    /**
     * 判断产品是否存在
     */
    private void exisProduct(AirtimeProduct ap)
    {
        AirtimeProduct airtimeProduct = airtimeProductDao.queryAirtimeProductByProperty(
            ap.getCarrierName(), ap.getProvince(), ap.getParValue(), ap.getCity(),ap.getBusinessType());
        if (BeanUtils.isNotNull(airtimeProduct))
        {
            String[] msgParams = new String[] {ap.toString()};
            String[] viewParams = new String[] {};
            ApplicationException e = new ApplicationException("businesss000023", msgParams,
                viewParams);
            throw ExceptionUtil.throwException(e);
        }
    }

    private String getProductNo(AirtimeProduct ap, Map<String, SearchFilter> filters)
    {
        String carrierNameStr = StringUtil.initString();
        String provinceStr = StringUtil.initString();
        String cityStr = StringUtil.initString();
        String parValueStr = StringUtil.initString();

        if (ap.getProvince() != null && !ap.getProvince().isEmpty())
        {
            filters.put(EntityConstant.AirtimeProduct.PROVINCE, new SearchFilter(
                EntityConstant.AirtimeProduct.PROVINCE, Operator.EQ, ap.getProvince()));
            provinceStr = ap.getProvince();
        }
        else
        {
            provinceStr = "***";
        }
        if (ap.getParValue() != null)
        {
            filters.put(EntityConstant.AirtimeProduct.PARVALUE, new SearchFilter(
                EntityConstant.AirtimeProduct.PARVALUE, Operator.EQ, ap.getParValue()));
            parValueStr = ap.getParValue().toString();
        }
        else
        {
            parValueStr = new BigDecimal(0).toString();
        }
        if (ap.getCarrierName() != null && !ap.getCarrierName().isEmpty())
        {
            filters.put(EntityConstant.AirtimeProduct.CARRIER_NAME, new SearchFilter(
                EntityConstant.AirtimeProduct.CARRIER_NAME, Operator.EQ, ap.getCarrierName()));
            carrierNameStr = ap.getCarrierName();
        }
        else
        {
            carrierNameStr = "**";
        }
        if (ap.getCity() != null && !ap.getCity().isEmpty())
        {
            filters.put(EntityConstant.AirtimeProduct.CITY, new SearchFilter(
                EntityConstant.AirtimeProduct.CITY, Operator.EQ, ap.getCity()));
            cityStr = ap.getCity();
        }
        else
        {
            cityStr = "****";
        }
        parValueStr = BigDecimalUtil.multiply(new BigDecimal(parValueStr), new BigDecimal(100),
            DecimalPlaces.ZERO.value(), BigDecimal.ROUND_UP).toString();
        int length = parValueStr.length();
        while (length < 8)
        {
            parValueStr = "0" + parValueStr;
            length = parValueStr.length();
        }
		String TypeStr = EntityConstant.AirtimeProduct.HF;
		
		if(ap.getBusinessType() == Integer.valueOf(Constant.BusinessType.BUSINESS_TYPE_FLOW))
		{
			TypeStr = EntityConstant.AirtimeProduct.FLOW;
		}else if(ap.getBusinessType() == Integer.valueOf(Constant.BusinessType.BUSINESS_TYPE_FIXED)){
		    TypeStr = EntityConstant.AirtimeProduct.FIXED;
		}
        // 类型（HF）2+运营商（YD）2+省份（全国 *）3+地市（全省 *）4+面值（均缩写）8
        String productNo = TypeStr + carrierNameStr + provinceStr
                           + cityStr + parValueStr;
        return productNo;
    }

    @Override
    @Transactional
    public AirtimeProduct editProduct(AirtimeProduct ap)
    {
        try
        {
            if (ap.getProductId() != null)
            {
                ap = airtimeProductDao.save(ap);
                if (BeanUtils.isNotNull(ap))
                {
                    agentProductRelationService.updateProductNameById(ap.getProductId(),
                        ap.getProductName());
                    supplyProductRelationService.updateProductNameById(ap.getProductId(),
                        ap.getProductName());
                }
                // 判断是否修改成顶级产品 author:jinger
                if (ap.getProductId().equals(ap.getParentProductId()))
                {
                    // 是 需要修改代理商产品关系表中的状态（默认状态：非默认、根节点状态：是）
                    agentProductRelationService.updateStatusByProductId(true, true,
                        ap.getProductId());
                }
                else
                {
                    // 否 需要修改代理商产品关系表中的状态（根节点状态：否）
                    agentProductRelationService.updateIsRootByProductId(false, ap.getProductId());
                }
            }
            else
            {
                throw ExceptionUtil.throwException(new ApplicationException("businesss000011",
                    new String[] {ap.toString()}));
            }
            String cacheKey = Constant.CacheKey.PRODUCT + ap.getCarrierName()
                              + StringSplitUtil.ENCODE + ap.getProvince() + StringSplitUtil.ENCODE
                              + ap.getCity() + StringSplitUtil.ENCODE + ap.getParValue()
                              + StringSplitUtil.ENCODE + ap.getBusinessType();
            HopsCacheUtil.put(Constant.Common.BUSINESS_CACHE, cacheKey, ap);
            return ap;
        }
        catch (RpcException e)
        {
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            throw ExceptionUtil.throwException(new ApplicationException("businesss000012",
                new String[] {ap.toString()}));
        }
    }

    @Override
    @Transactional
    public String deleteProduct(AirtimeProduct ap, String operatorName)
    {
        LOGGER.debug("[ProductManagementImpl :deleteProduct(AirtimeProduct:" + ap.toString()
                     + ")]");
        try
        {
            Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
            AirtimeProduct airtimeProduct = airtimeProductDao.findOne(ap.getProductId());
            if (airtimeProduct != null)
            {
                agentProductRelationService.deleteAgentProductRelationByProductId(ap.getProductId(), operatorName);
                supplyProductRelationService.deleteSupplyProductRelationByProductId(ap.getProductId(), operatorName);
                filters.put(EntityConstant.AirtimeProduct.PARENT_PRODUCT_ID,
                    new SearchFilter(EntityConstant.AirtimeProduct.PARENT_PRODUCT_ID, Operator.EQ,
                        ap.getProductId()));
                filters.put(EntityConstant.AirtimeProduct.PRODUCT_STATUS, new SearchFilter(
                    EntityConstant.AirtimeProduct.PRODUCT_STATUS, Operator.LT,
                    Constant.Product.PRODUCT_DELETE));
                Specification<AirtimeProduct> spec = DynamicSpecifications.bySearchFilter(
                    filters.values(), AirtimeProduct.class);
                List<AirtimeProduct> products = airtimeProductDao.findAll(spec);
                for (AirtimeProduct product : products)
                {
                    if (airtimeProduct.getParentProductId().compareTo(
                        airtimeProduct.getProductId()) == 0)
                    {
                        // 根产品
                        product.setParentProductId(product.getProductId());
                    }
                    else
                    {
                        // 非根产品
                        product.setParentProductId(airtimeProduct.getParentProductId());
                    }
                    airtimeProductDao.save(product);
                    String cacheKey = Constant.CacheKey.PRODUCT + product.getCarrierName()
                                      + StringSplitUtil.ENCODE + product.getProvince()
                                      + StringSplitUtil.ENCODE + product.getCity()
                                      + StringSplitUtil.ENCODE + product.getParValue()
                                      + StringSplitUtil.ENCODE + product.getBusinessType();
                    HopsCacheUtil.put(Constant.Common.BUSINESS_CACHE, cacheKey, product);
                }
                airtimeProductDao.updateStatusByProductId(Constant.Product.PRODUCT_DELETE,
                    airtimeProduct.getProductId());
                String cacheKey = Constant.CacheKey.PRODUCT + airtimeProduct.getCarrierName()
                                  + StringSplitUtil.ENCODE + airtimeProduct.getProvince()
                                  + StringSplitUtil.ENCODE + airtimeProduct.getCity()
                                  + StringSplitUtil.ENCODE + airtimeProduct.getParValue()
                                   + StringSplitUtil.ENCODE + airtimeProduct.getBusinessType();
                HopsCacheUtil.remove(Constant.Common.BUSINESS_CACHE, cacheKey);
            }
            else
            {
                // 准备删除的产品不存在！
                throw ExceptionUtil.throwException(new ApplicationException("businesss000005",
                    new String[] {ap.toString()}));
            }
            return Constant.Common.SUCCESS;
        }
        catch (Exception e)
        {
            throw ExceptionUtil.throwException(new ApplicationException("businesss000004",
                new String[] {ap.toString()}));
        }
    }

    @Override
    public boolean validateLockProductStatus(Long productId, String productStatus)
    {
        AirtimeProduct airtimeProduct = productPageQuery.queryAirtimeProductById(productId);
        if (Constant.Product.PRODUCT_OPEN.equals(productStatus)
            && Constant.Product.PRODUCT_CLOSE.equals(airtimeProduct.getProductStatus()))
        {
            return false;
        }
        return true;
    }

}
