package com.yuecheng.hops.product.service.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.Constant.IdentityConstants;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.hopscache.HopsCacheUtil;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.IdentityService;
import com.yuecheng.hops.identity.service.merchant.MerchantStatusManagement;
import com.yuecheng.hops.numsection.entity.NumSection;
import com.yuecheng.hops.parameter.entity.MerchantBindTimes;
import com.yuecheng.hops.parameter.service.MerchantBindTimesService;
import com.yuecheng.hops.product.cache.ProductCacheConstant;
import com.yuecheng.hops.product.entity.airtimes.AirtimeProduct;
import com.yuecheng.hops.product.entity.history.ProductDiscountHistory;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;
import com.yuecheng.hops.product.repository.SupplyProductRelationDao;
import com.yuecheng.hops.product.repository.SupplyProductRelationSqlDao;
import com.yuecheng.hops.product.service.ProductManagement;
import com.yuecheng.hops.product.service.ProductOperationService;
import com.yuecheng.hops.product.service.ProductService;
import com.yuecheng.hops.product.service.SupplyProductRelationService;


@Service("supplyProductRelationService")
public class SupplyProductRelationServiceImpl implements SupplyProductRelationService
{
    @Autowired
    private SupplyProductRelationDao supplyProductRelationDao;

    @Autowired
    private SupplyProductRelationSqlDao supplyProductRelationSqlDao;

    @Autowired
    private MerchantStatusManagement merchantStatusManagement;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private ProductService productService;
    
    @Autowired
    private MerchantBindTimesService merchantBindTimesService;

    @Autowired
    private ProductManagement productManagement;
    
    @Autowired
    private ProductOperationService productOperationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SupplyProductRelationServiceImpl.class);

    @Override
    @Transactional
    public SupplyProductRelation doSaveSupplyProductRelation(SupplyProductRelation upr, String operatorName)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.SupplyProductRelation.IDENTITY_ID, new SearchFilter(
            EntityConstant.SupplyProductRelation.IDENTITY_ID, Operator.EQ, upr.getIdentityId()));
        filters.put(
            EntityConstant.SupplyProductRelation.IDENTITY_TYPE,
            new SearchFilter(EntityConstant.SupplyProductRelation.IDENTITY_TYPE, Operator.EQ,
                upr.getIdentityType()));
        filters.put(EntityConstant.SupplyProductRelation.PRODUCT_ID, new SearchFilter(
            EntityConstant.SupplyProductRelation.PRODUCT_ID, Operator.EQ, upr.getProductId()));
        Specification<SupplyProductRelation> spec = DynamicSpecifications.bySearchFilter(
            filters.values(), SupplyProductRelation.class);
        SupplyProductRelation upProductRelation = supplyProductRelationDao.findOne(spec);
        if (upProductRelation == null)
        {
            ProductDiscountHistory productDiscountHistory = productOperationService.buildProductDiscountHistory(upr, Constant.Product.PRODUCT_TYPE_SUPPLY, null, null, operatorName, Constant.Product.PRODUCT_DISCOUNT_HISTORY_SAVE);
            productOperationService.saveProductDiscountHistory(productDiscountHistory);
            upr = supplyProductRelationDao.save(upr);
        }
        else
        {
            String[] msgParams = new String[] {upr.toString()};
            String[] viewParams = new String[] {};
            ApplicationException ae = new ApplicationException("businesss000021", msgParams,
                viewParams);
            throw ExceptionUtil.throwException(ae);
        }
        return upr;
    }

    @Override
    public SupplyProductRelation querySupplyProductRelationByParams(Long productId,
                                                                    Long merchantId, String status)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        if (productId != null)
        {
            filters.put(EntityConstant.SupplyProductRelation.PRODUCT_ID, new SearchFilter(
                EntityConstant.SupplyProductRelation.PRODUCT_ID, Operator.EQ, productId));
        }
        if (merchantId != null)
        {
            filters.put(EntityConstant.SupplyProductRelation.IDENTITY_ID, new SearchFilter(
                EntityConstant.SupplyProductRelation.IDENTITY_ID, Operator.EQ, merchantId));
            filters.put(EntityConstant.SupplyProductRelation.IDENTITY_TYPE, new SearchFilter(
                EntityConstant.SupplyProductRelation.IDENTITY_TYPE, Operator.EQ, "MERCHANT"));
        }
        if (StringUtils.isNotBlank(status))
        {
            filters.put(EntityConstant.SupplyProductRelation.STATUS, new SearchFilter(
                EntityConstant.SupplyProductRelation.STATUS, Operator.EQ, status));
        }
        Specification<SupplyProductRelation> spec_UpProductRelation = DynamicSpecifications.bySearchFilter(
            filters.values(), SupplyProductRelation.class);
        SupplyProductRelation dpr = supplyProductRelationDao.findOne(spec_UpProductRelation);
        return dpr;
    }

    @Override
    public YcPage<SupplyProductRelation> querySupplyProductRelation(Map<String, Object> searchParams,
                                                                    int pageNumber, int pageSize,
                                                                    BSort bsort)
    {
//        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
//        YcPage<SupplyProductRelation> ycPage = PageUtil.queryYcPage(supplyProductRelationDao,
//            filters, pageNumber, pageSize, new Sort(Direction.DESC, bsort.getCloumn()),
//            SupplyProductRelation.class);
        YcPage<SupplyProductRelation> ycPage=supplyProductRelationSqlDao.queryPageSupplyProductRelation(searchParams, pageNumber, pageSize);
        return ycPage;
    }

    @Override
    @Transactional
    public SupplyProductRelation editSupplyProductRelation(SupplyProductRelation upr, String operatorName)
    {
        SupplyProductRelation oldSupplyProduct = supplyProductRelationDao.findOne(upr.getId());
        if(!oldSupplyProduct.getDiscount().equals(upr.getDiscount()))
        {
            //折扣变更，记录
            ProductDiscountHistory productDiscountHistory = productOperationService.buildProductDiscountHistory(upr, Constant.Product.PRODUCT_TYPE_SUPPLY, oldSupplyProduct.getDiscount(), upr.getDiscount(), operatorName, Constant.Product.PRODUCT_DISCOUNT_HISTORY_UPDATE);
            productOperationService.saveProductDiscountHistory(productDiscountHistory);
        }
        upr = supplyProductRelationDao.save(upr);
        return upr;
    }

    @Override
    public SupplyProductRelation querySupplyProductRelationById(Long id)
    {
        SupplyProductRelation upr = supplyProductRelationDao.findOne(id);
        return upr;
    }

    @Override
    public List<SupplyProductRelation> getAllProductBySupplyMerchantId(Map<String, Object> searchParams,
                                                                       Long merchantId,
                                                                       String identityType,
                                                                       String status)
    {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        filters.put(EntityConstant.SupplyProductRelation.IDENTITY_ID, new SearchFilter(
            EntityConstant.SupplyProductRelation.IDENTITY_ID, Operator.EQ, merchantId));
        filters.put(EntityConstant.SupplyProductRelation.IDENTITY_TYPE, new SearchFilter(
            EntityConstant.SupplyProductRelation.IDENTITY_TYPE, Operator.EQ, identityType));
        // filters.put(EntityConstant.UpProductRelation.STATUS, new
        // SearchFilter(EntityConstant.UpProductRelation.STATUS, Operator.EQ,
        // Constant.UpProductStatus.OPEN_STATUS));
        if (StringUtil.isNotBlank(status))
        {
            filters.put(EntityConstant.SupplyProductRelation.STATUS, new SearchFilter(
                EntityConstant.SupplyProductRelation.STATUS, Operator.EQ, status));
        }
        Specification<SupplyProductRelation> spec = DynamicSpecifications.bySearchFilter(
            filters.values(), SupplyProductRelation.class);
        List<SupplyProductRelation> uprs = supplyProductRelationDao.findAll(spec);
        return uprs;
    }

    @Override
    public List<SupplyProductRelation> querySupplyProductByProductId(List<AirtimeProduct> products,
                                                                     Long level,
                                                                     BigDecimal discount,
                                                                     BigDecimal quality)
    {
        List<SupplyProductRelation> uprs = supplyProductRelationSqlDao.querySupplyProductByProductId(products, level, discount, quality);
        List<SupplyProductRelation> result = new ArrayList<SupplyProductRelation>(); 
        for (SupplyProductRelation upr : uprs)
        {
            Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(upr.getIdentityId(), IdentityType.MERCHANT);
            if (IdentityConstants.MERCHANT_ENABLE.equalsIgnoreCase(merchant.getIdentityStatus().getStatus()))
            {
                MerchantBindTimes merchantBindTimes = merchantBindTimesService.getMerchantBindTimesByMerchantId(merchant.getId());
                if(BeanUtils.isNotNull(merchantBindTimes)){
                    upr.setBindTimes(merchantBindTimes.getBindTimes());
                }else{
                    upr.setBindTimes(1);
                }
                result.add(upr);
            }
        }
        return result;
    }
    
    @Override
    public List<SupplyProductRelation> querySupplyProductByProductId(List<AirtimeProduct> products, Long merchantId){
        List<SupplyProductRelation> uprs = supplyProductRelationSqlDao.querySupplyProductByProductId(products, merchantId);
        return uprs;
    }

    @Override
    public SupplyProductRelation updateSupplyProductRelationStatus(Long id, String status)
    {
        String productName="";
        try{
            SupplyProductRelation upr = supplyProductRelationDao.findOne(id);
            productName=upr.getProductName();
            upr.setStatus(status);
            boolean isUpdate=productManagement.validateLockProductStatus(upr.getProductId(), status);
            Assert.isTrue(isUpdate);
            if (isUpdate)
            {
                upr = supplyProductRelationDao.save(upr);
            }
            return upr;
        }catch(Exception e)
        {
            LOGGER.error("[SupplyProductRelationServiceImpl:updateSupplyProductRelationStatus(" + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"updateSupplyProductRelationStatus",productName};
            String[] viewParams=new String[]{productName};
            ApplicationException ae = new ApplicationException("businesss100003", msgParams,viewParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public List<SupplyProductRelation> getAllSupplyProductRelationByDown(Long productId)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.SupplyProductRelation.PRODUCT_ID, new SearchFilter(
            EntityConstant.SupplyProductRelation.PRODUCT_ID, Operator.EQ, productId));
        Specification<SupplyProductRelation> spec = DynamicSpecifications.bySearchFilter(
            filters.values(), SupplyProductRelation.class);
        List<SupplyProductRelation> uprs = supplyProductRelationDao.findAll(spec);
        return uprs;
    }

    @Override
    public void deleteSupplyProductRelationById(Long id, String operatorName)
    {
        SupplyProductRelation upr = supplyProductRelationDao.findOne(id);
        ProductDiscountHistory productDiscountHistory = productOperationService.buildProductDiscountHistory(upr, Constant.Product.PRODUCT_TYPE_SUPPLY, null, null, operatorName, Constant.Product.PRODUCT_DISCOUNT_HISTORY_DELETE);
        productOperationService.saveProductDiscountHistory(productDiscountHistory);
        supplyProductRelationDao.delete(id);
    }

    @Override
    public void cloneSupplyMerchantProduct(Long newMerchantId, Long oldMerchantId,
                                           String productIds,String operatorName)
    {
        Map<String, SearchFilter> filters = null;
        Merchant newMerchant = (Merchant)identityService.findIdentityByIdentityId(newMerchantId,
            IdentityType.MERCHANT);
        Merchant oldMerchant = (Merchant)identityService.findIdentityByIdentityId(oldMerchantId,
            IdentityType.MERCHANT);
        String newMerchantType = newMerchant.getMerchantType().toString();
        String oldMerchantType = oldMerchant.getMerchantType().toString();
        if (!newMerchantType.equals(oldMerchantType)
            || !newMerchantType.equals(MerchantType.SUPPLY.toString()))
        {
            String[] msgParams = new String[] {newMerchantId.toString(), oldMerchantId.toString()};
            String[] viewParams = new String[] {};
            ApplicationException ae = new ApplicationException("businesss000019", msgParams,
                viewParams);
            throw ExceptionUtil.throwException(ae);
        }

        List<SupplyProductRelation> result = new ArrayList<SupplyProductRelation>();
        filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.SupplyProductRelation.IDENTITY_ID, new SearchFilter(
            EntityConstant.SupplyProductRelation.IDENTITY_ID, Operator.EQ, newMerchantId));
        filters.put(EntityConstant.SupplyProductRelation.IDENTITY_TYPE,
            new SearchFilter(EntityConstant.SupplyProductRelation.IDENTITY_TYPE, Operator.EQ,
                IdentityType.MERCHANT.toString()));
        Specification<SupplyProductRelation> spec_UpProductRelation = DynamicSpecifications.bySearchFilter(
            filters.values(), SupplyProductRelation.class);
        List<SupplyProductRelation> newMerchantProducts = supplyProductRelationDao.findAll(spec_UpProductRelation);
        List<Long> newMerchantProductIds = new ArrayList<Long>();
        for (SupplyProductRelation newMerchantProduct : newMerchantProducts)
        {
            newMerchantProductIds.add(newMerchantProduct.getProductId());
        }

        List<SupplyProductRelation> oldMerchantProducts = new ArrayList<SupplyProductRelation>();
        String[] upProductRelations = productIds.split(",");
        for (int i = 0; i < upProductRelations.length; i++ )
        {
            SupplyProductRelation upr = supplyProductRelationDao.findOne(Long.valueOf(upProductRelations[i]));
            oldMerchantProducts.add(upr);
        }

        for (SupplyProductRelation oldMerchantProduct : oldMerchantProducts)
        {
            if (newMerchantProductIds.contains(oldMerchantProduct.getProductId()))
            {
                filters = new HashMap<String, SearchFilter>();
                filters.put(EntityConstant.SupplyProductRelation.IDENTITY_ID, new SearchFilter(
                    EntityConstant.SupplyProductRelation.IDENTITY_ID, Operator.EQ, newMerchantId));
                filters.put(EntityConstant.SupplyProductRelation.IDENTITY_TYPE, new SearchFilter(
                    EntityConstant.SupplyProductRelation.IDENTITY_TYPE, Operator.EQ,
                    IdentityType.MERCHANT.toString()));
                filters.put(EntityConstant.SupplyProductRelation.PRODUCT_ID, new SearchFilter(
                    EntityConstant.SupplyProductRelation.PRODUCT_ID, Operator.EQ,
                    oldMerchantProduct.getProductId()));
                spec_UpProductRelation = DynamicSpecifications.bySearchFilter(filters.values(),
                    SupplyProductRelation.class);
                SupplyProductRelation sameUpProductRelation = supplyProductRelationDao.findOne(spec_UpProductRelation);

                if (sameUpProductRelation != null)
                {
                    sameUpProductRelation.setProductId(oldMerchantProduct.getProductId());
                    sameUpProductRelation.setProductName(oldMerchantProduct.getProductName());
                    sameUpProductRelation.setProvince(oldMerchantProduct.getProvince());
                    sameUpProductRelation.setParValue(oldMerchantProduct.getParValue());
                    sameUpProductRelation.setCarrierName(oldMerchantProduct.getCarrierName());
                    sameUpProductRelation.setCity(oldMerchantProduct.getCity());
                    sameUpProductRelation.setDiscount(oldMerchantProduct.getDiscount());
                    sameUpProductRelation.setQuality(oldMerchantProduct.getQuality());
                    sameUpProductRelation.setPrice(oldMerchantProduct.getPrice());
                    sameUpProductRelation.setStatus(Constant.SupplyProductStatus.CLOSE_STATUS);
                    sameUpProductRelation.setMerchantLevel(oldMerchantProduct.getMerchantLevel());
                    sameUpProductRelation.setSupplyProdId(oldMerchantProduct.getSupplyProdId());
                    sameUpProductRelation.setDisplayValue(oldMerchantProduct.getDisplayValue());
                    sameUpProductRelation.setBusinessType(oldMerchantProduct.getBusinessType());
                    result.add(sameUpProductRelation);
                }
            }
            else
            {
                SupplyProductRelation sameUpProductRelation = new SupplyProductRelation();
                sameUpProductRelation.setIdentityId(newMerchant.getId());
                sameUpProductRelation.setIdentityName(newMerchant.getMerchantName());
                sameUpProductRelation.setIdentityType(IdentityType.MERCHANT.toString());
                sameUpProductRelation.setProductId(oldMerchantProduct.getProductId());
                sameUpProductRelation.setProductName(oldMerchantProduct.getProductName());
                sameUpProductRelation.setProvince(oldMerchantProduct.getProvince());
                sameUpProductRelation.setParValue(oldMerchantProduct.getParValue());
                sameUpProductRelation.setCarrierName(oldMerchantProduct.getCarrierName());
                sameUpProductRelation.setCity(oldMerchantProduct.getCity());
                sameUpProductRelation.setDiscount(oldMerchantProduct.getDiscount());
                sameUpProductRelation.setQuality(oldMerchantProduct.getQuality());
                sameUpProductRelation.setPrice(oldMerchantProduct.getPrice());
                sameUpProductRelation.setStatus(Constant.SupplyProductStatus.CLOSE_STATUS);
                sameUpProductRelation.setMerchantLevel(oldMerchantProduct.getMerchantLevel());
                sameUpProductRelation.setSupplyProdId(oldMerchantProduct.getSupplyProdId());
                sameUpProductRelation.setDisplayValue(oldMerchantProduct.getDisplayValue());
                sameUpProductRelation.setBusinessType(oldMerchantProduct.getBusinessType());
                result.add(sameUpProductRelation);
            }
        }

        for (Iterator<SupplyProductRelation> iterator = result.iterator(); iterator.hasNext();)
        {
            SupplyProductRelation upProductRelation = iterator.next();
            ProductDiscountHistory productDiscountHistory = productOperationService.buildProductDiscountHistory(upProductRelation, Constant.Product.PRODUCT_TYPE_SUPPLY, null, null, operatorName, Constant.Product.PRODUCT_DISCOUNT_HISTORY_SAVE);
            productOperationService.saveProductDiscountHistory(productDiscountHistory);
            supplyProductRelationDao.save(upProductRelation);
        }
    }

    @Override
    public void deleteSupplyProductRelationByProductId(Long productId, String operatorName)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.SupplyProductRelation.PRODUCT_ID, new SearchFilter(
            EntityConstant.SupplyProductRelation.PRODUCT_ID, Operator.EQ, productId));
        Specification<SupplyProductRelation> spec = DynamicSpecifications.bySearchFilter(
            filters.values(), SupplyProductRelation.class);
        List<SupplyProductRelation> uprs = supplyProductRelationDao.findAll(spec);
        for (int i = 0; i < uprs.size(); i++ )
        {
            ProductDiscountHistory productDiscountHistory = productOperationService.buildProductDiscountHistory(uprs.get(i), Constant.Product.PRODUCT_TYPE_SUPPLY, null, null, operatorName, Constant.Product.PRODUCT_DISCOUNT_HISTORY_DELETE);
            productOperationService.saveProductDiscountHistory(productDiscountHistory);
        }
        supplyProductRelationDao.delete(uprs);
    }

    @Override
    public List<SupplyProductRelation> getAllProductRelationByProductId(Long productId)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.SupplyProductRelation.PRODUCT_ID, new SearchFilter(
            EntityConstant.SupplyProductRelation.PRODUCT_ID, Operator.EQ, productId));
        Specification<SupplyProductRelation> spec = DynamicSpecifications.bySearchFilter(
            filters.values(), SupplyProductRelation.class);
        List<SupplyProductRelation> supplyProductRelations = supplyProductRelationDao.findAll(spec);
        return supplyProductRelations;
    }

    @Override
    public List<SupplyProductRelation> getSupplyProductRelationByParams(Long productId,
                                                                        String status)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.SupplyProductRelation.PRODUCT_ID, new SearchFilter(
            EntityConstant.SupplyProductRelation.PRODUCT_ID, Operator.EQ, productId));
        if (StringUtil.isNotBlank(status))
        {
            filters.put(EntityConstant.SupplyProductRelation.STATUS, new SearchFilter(
                EntityConstant.SupplyProductRelation.STATUS, Operator.EQ, status));
        }
        Specification<SupplyProductRelation> spec = DynamicSpecifications.bySearchFilter(
            filters.values(), SupplyProductRelation.class);
        List<SupplyProductRelation> ups = supplyProductRelationDao.findAll(spec);
        return ups;
    }

    @Override
    public void updateProductNameById(Long productId, String productName)
    {
        supplyProductRelationDao.updateProductNameById(productId, productName);
    }

    @Override
    public List<SupplyProductRelation> getProductRelationByParams(Long identityId,
                                                                  IdentityType idenityType,
                                                                  Long productId, String status)
    {
        AirtimeProduct airtimeProduct = productService.findOne(productId);
        List<AirtimeProduct> products = productService.getProductTree(
            airtimeProduct.getProvince(), airtimeProduct.getParValue().toString(),
            airtimeProduct.getCarrierName(), airtimeProduct.getCity(),null);
        List<SupplyProductRelation> supplyProductRelations = supplyProductRelationSqlDao.getProductRelationByParams(
            identityId, idenityType, products, status);
        return supplyProductRelations;
    }

    @Override
    public List<SupplyProductRelation> getAllOpenProductRelation()
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.SupplyProductRelation.STATUS, new SearchFilter(
            EntityConstant.SupplyProductRelation.STATUS, Operator.EQ,
            Constant.SupplyProductStatus.OPEN_STATUS));
        Specification<SupplyProductRelation> spec = DynamicSpecifications.bySearchFilter(
            filters.values(), SupplyProductRelation.class);
        List<SupplyProductRelation> supplyProductRelations = (List<SupplyProductRelation>)supplyProductRelationDao.findAll(spec);
        return supplyProductRelations;
    }

    @Override
    public List<SupplyProductRelation> matchProduct(NumSection numSection, BigDecimal faceVal,
                                                    BigDecimal discount, Long level,
                                                    BigDecimal quality)
    {
        // 面值对应MAP
        Map<String, Set<SupplyProductRelation>> faceValMap = (Map<String, Set<SupplyProductRelation>>)HopsCacheUtil.get(
            Constant.Common.BUSINESS_CACHE, ProductCacheConstant.SUPPLY_PRODUCT_CACHE_PARVALUE_MAP);
        faceValMap = BeanUtils.isNotNull(faceValMap) ? faceValMap : new HashMap<String, Set<SupplyProductRelation>>();
        Set<SupplyProductRelation> faceValSet = faceValMap.get(faceVal.toString());
        // 折扣对应MAP
        Map<String, Set<SupplyProductRelation>> discountMap = (Map<String, Set<SupplyProductRelation>>)HopsCacheUtil.get(
            Constant.Common.BUSINESS_CACHE, ProductCacheConstant.SUPPLY_PRODUCT_CACHE_DISCOUNT_MAP);
        discountMap = BeanUtils.isNotNull(discountMap) ? discountMap : new HashMap<String, Set<SupplyProductRelation>>();
        Set<SupplyProductRelation> discountSet = discountMap.get(discount.toString());
        // 产品等级MAP
        Map<String, Set<SupplyProductRelation>> levelMap = (Map<String, Set<SupplyProductRelation>>)HopsCacheUtil.get(
            Constant.Common.BUSINESS_CACHE,
            ProductCacheConstant.SUPPLY_PRODUCT_CACHE_MERCHANT_LEVEL_MAP);
        levelMap = BeanUtils.isNotNull(levelMap) ? levelMap : new HashMap<String, Set<SupplyProductRelation>>();
        Set<SupplyProductRelation> levelSet = levelMap.get(level.toString());
        // 质量对应MAP
        Map<String, Set<SupplyProductRelation>> qualityMap = (Map<String, Set<SupplyProductRelation>>)HopsCacheUtil.get(
            Constant.Common.BUSINESS_CACHE, ProductCacheConstant.SUPPLY_PRODUCT_CACHE_QUALITY_MAP);
        qualityMap = BeanUtils.isNotNull(qualityMap) ? qualityMap : new HashMap<String, Set<SupplyProductRelation>>();
        Set<SupplyProductRelation> qualitySet = qualityMap.get(quality.toString());
        // 省份对应MAP
        Map<String, Set<SupplyProductRelation>> provinceMap = (Map<String, Set<SupplyProductRelation>>)HopsCacheUtil.get(
            Constant.Common.BUSINESS_CACHE, ProductCacheConstant.SUPPLY_PRODUCT_CACHE_PROVINCE_MAP);
        provinceMap = BeanUtils.isNotNull(provinceMap) ? provinceMap : new HashMap<String, Set<SupplyProductRelation>>();
        Set<SupplyProductRelation> provinceSet = provinceMap.get(numSection.getProvince().getProvinceId());
        // 城市对应MAP
        Map<String, Set<SupplyProductRelation>> cityMap = (Map<String, Set<SupplyProductRelation>>)HopsCacheUtil.get(
            Constant.Common.BUSINESS_CACHE, ProductCacheConstant.SUPPLY_PRODUCT_CACHE_CITY_MAP);
        cityMap = BeanUtils.isNotNull(cityMap) ? cityMap : new HashMap<String, Set<SupplyProductRelation>>();
        Set<SupplyProductRelation> citySet = cityMap.get(numSection.getCity().getCityId());
        // 运营商对应MAP
        Map<String, Set<SupplyProductRelation>> carrierMap = (Map<String, Set<SupplyProductRelation>>)HopsCacheUtil.get(
            Constant.Common.BUSINESS_CACHE, ProductCacheConstant.SUPPLY_PRODUCT_CACHE_CITY_MAP);
        carrierMap = BeanUtils.isNotNull(carrierMap) ? carrierMap : new HashMap<String, Set<SupplyProductRelation>>();
        Set<SupplyProductRelation> carrierSet = carrierMap.get(numSection.getCarrierInfo().getCarrierNo());
        // 全国对应MAP
        Set<SupplyProductRelation> countrySet = (Set<SupplyProductRelation>)HopsCacheUtil.get(
            Constant.Common.BUSINESS_CACHE, ProductCacheConstant.SUPPLY_PRODUCT_CACHE_COUNTRY_MAP);

        List<SupplyProductRelation> result = new ArrayList<SupplyProductRelation>();

        result.addAll(countrySet);
        result.addAll(provinceSet);
        result.addAll(citySet);
        result.retainAll(carrierSet);
        result.retainAll(faceValSet);
        result.retainAll(discountSet);
        result.retainAll(levelSet);
        result.retainAll(qualitySet);

        return result;
    }

    @Override
    public List<SupplyProductRelation> getProductRelationByIdentityId(Long merchantId)
    {
        // TODO Auto-generated method stub
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.SupplyProductRelation.IDENTITY_ID, new SearchFilter(
            EntityConstant.SupplyProductRelation.IDENTITY_ID, Operator.EQ, merchantId));
        Specification<SupplyProductRelation> spec = DynamicSpecifications.bySearchFilter(
            filters.values(), SupplyProductRelation.class);
        List<SupplyProductRelation> uprs = supplyProductRelationDao.findAll(spec);
        return uprs;
    }

    @Override
    public List<SupplyProductRelation> getProductRelationByParams(Map<String, Object> searchParams)
    {
        List<SupplyProductRelation> uprsList= supplyProductRelationSqlDao.getProductRelationByParams(searchParams);
        return uprsList;
    }

    @Override
    public boolean isExistsSupplyProductId(Long merchantId, String supplyProductId)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.SupplyProductRelation.IDENTITY_ID, new SearchFilter(
            EntityConstant.SupplyProductRelation.IDENTITY_ID, Operator.EQ, merchantId));
        filters.put(EntityConstant.SupplyProductRelation.SUPPLY_PROD_ID, new SearchFilter(
            EntityConstant.SupplyProductRelation.SUPPLY_PROD_ID, Operator.EQ, supplyProductId));
        Specification<SupplyProductRelation> spec = DynamicSpecifications.bySearchFilter(
            filters.values(), SupplyProductRelation.class);
        List<SupplyProductRelation> uprs = supplyProductRelationDao.findAll(spec);
        if(uprs!=null&&uprs.size()>0)
        {
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int updateStatusByParamAll(String status, String carrierName, String province,
                                      String city, String identityType, Long identityId,String businessType)
    {
        int result= supplyProductRelationDao.updateStatusByParamAll(status, carrierName, province, city, identityType, identityId,Integer.valueOf(businessType));
        return result;
    }

}
