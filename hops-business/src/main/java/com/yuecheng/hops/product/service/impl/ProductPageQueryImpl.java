package com.yuecheng.hops.product.service.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.Constant.StringSplitUtil;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.hopscache.HopsCacheUtil;
import com.yuecheng.hops.common.query.PageUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.product.entity.airtimes.AirtimeProduct;
import com.yuecheng.hops.product.repository.AirtimeProductDao;
import com.yuecheng.hops.product.repository.AirtimeProductSqlDao;
import com.yuecheng.hops.product.service.ProductPageQuery;


@Service("productPageQuery")
public class ProductPageQueryImpl implements ProductPageQuery
{
    @Autowired
    private AirtimeProductDao airtimeProductDao;
    @Autowired
    private AirtimeProductSqlDao airtimeProductSqlDao;

    @Override
    public YcPage<AirtimeProduct> queryAirtimeProduct(Map<String, Object> searchParams,
                                                      int pageNumber, int pageSize, String sortType)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        YcPage<AirtimeProduct> ycPage = PageUtil.queryYcPage(airtimeProductDao, filters,
            pageNumber, pageSize, new Sort(Direction.DESC,
                EntityConstant.AirtimeProduct.PRODUCT_ID), AirtimeProduct.class);
        return ycPage;
    }

    @Override
    public AirtimeProduct queryAirtimeProductById(Long productId)
    {
        AirtimeProduct product = airtimeProductDao.getProductById(productId);
        return product;
    }

    @Override
    public YcPage<AirtimeProduct> queryAirtimeProductByProperty(List<String> paramName,
                                                                List<String> value,
                                                                int pageNumber, int pageSize,
                                                                String sortType)
    {
        PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, new Sort(
            Direction.DESC, sortType));
        List<AirtimeProduct> list = new ArrayList<AirtimeProduct>();
        Page<AirtimeProduct> page = new PageImpl<AirtimeProduct>(list, pageRequest, list.size());
        YcPage<AirtimeProduct> ycPage = new YcPage<AirtimeProduct>();
        ycPage.setList(page.getContent());
        ycPage.setCountTotal((int)page.getTotalElements());
        ycPage.setPageTotal(page.getTotalPages());
        return ycPage;
    }

    @Override
    public AirtimeProduct queryAirtimeProductByParams(String province, BigDecimal parValue,
                                                      String carrierName, String city, int businessType)
    {
        String cacheKey = Constant.CacheKey.PRODUCT + carrierName + StringSplitUtil.ENCODE
                          + province + StringSplitUtil.ENCODE + city + StringSplitUtil.ENCODE
                          + parValue + StringSplitUtil.ENCODE + businessType;
        AirtimeProduct ap = (AirtimeProduct)HopsCacheUtil.get(Constant.Common.BUSINESS_CACHE,
            cacheKey);

        if (BeanUtils.isNotNull(ap))
        {
            return ap;
        }
        if (carrierName != null && parValue != null && province != null && city == null)
        {
            ap = airtimeProductDao.queryAirtimeProductByProperty(carrierName, province, parValue,businessType);
        }
        else if (carrierName != null && parValue != null && province != null && city != null)
        {
            ap = airtimeProductDao.queryAirtimeProductByProperty(carrierName, province, parValue,
                city,businessType);
        }
        else if (carrierName != null && parValue != null && province == null && city == null)
        {
            ap = airtimeProductDao.queryAirtimeProductByProperty(carrierName, parValue,businessType);
        }
        else
        {
            ap = null;
        }
        return ap;
    }

    @Override
    public List<AirtimeProduct> fuzzyQueryAirtimeProductsByParams(String province,
                                                                  String parValue,
                                                                  String carrierName, String city,  String businessType)
    {
        return airtimeProductSqlDao.fuzzyQueryAirtimeProductsByParams(province, parValue, carrierName, city, businessType);
//        List<AirtimeProduct> ap = null;
//        if (!StringUtil.isBlank(carrierName) && StringUtil.isBlank(parValue)
//            && StringUtil.isBlank(province) && StringUtil.isBlank(city))
//        {
//            ap = airtimeProductDao.fuzzyQueryAirtimeProductsByProperty(carrierName);
//        }
//        else if (!StringUtil.isBlank(carrierName) && !StringUtil.isBlank(parValue)
//                 && StringUtil.isBlank(province) && StringUtil.isBlank(city))
//        {
//            ap = airtimeProductDao.fuzzyQueryAirtimeProductsByProperty(carrierName,
//                new BigDecimal(parValue));
//        }
//        else if (!StringUtil.isBlank(carrierName) && !StringUtil.isBlank(parValue)
//                 && !StringUtil.isBlank(province) && StringUtil.isBlank(city))
//        {
//            ap = airtimeProductDao.fuzzyQueryAirtimeProductsByProperty(carrierName, province,
//                new BigDecimal(parValue));
//        }
//        else if (!StringUtil.isBlank(carrierName) && !StringUtil.isBlank(parValue)
//                 && !StringUtil.isBlank(province) && !StringUtil.isBlank(city))
//        {
//            ap = airtimeProductDao.fuzzyQueryAirtimeProductsByProperty(carrierName, province,
//                new BigDecimal(parValue), city);
//        }
//        else if (!StringUtil.isBlank(carrierName) && StringUtil.isBlank(parValue)
//                 && !StringUtil.isBlank(province) && StringUtil.isBlank(city))
//        {
//            ap = airtimeProductDao.fuzzyQueryAirtimeProductsByProperty(carrierName, province);
//        }
//        else if (!StringUtil.isBlank(carrierName) && StringUtil.isBlank(parValue)
//                 && !StringUtil.isBlank(province) && !StringUtil.isBlank(city))
//        {
//            ap = airtimeProductDao.fuzzyQueryAirtimeProductsByProperty(carrierName, province, city);
//        }
//        else if (StringUtil.isBlank(carrierName) && !StringUtil.isBlank(parValue)
//                 && StringUtil.isBlank(province) && StringUtil.isBlank(city))
//        {
//            ap = airtimeProductDao.fuzzyQueryAirtimeProductsByProperty(new BigDecimal(parValue));
//        }
//        else if (StringUtil.isBlank(carrierName) && !StringUtil.isBlank(parValue)
//            && !StringUtil.isBlank(province) && StringUtil.isBlank(city))
//        {
//           ap = airtimeProductDao.fuzzyQueryAirtimeProductsByProperty(new BigDecimal(parValue), province);
//        }
//        else
//        {
//            ap = (List<AirtimeProduct>)airtimeProductDao.findAllProduct();
//        }
//        return ap;
    }

    @Override
    public YcPage<AirtimeProduct> queryAirtimeProductList(Map<String, Object> searchParams,
                                                          int pageNumber, int pageSize,
                                                          String sortType)
    {
        // TODO Auto-generated method stub
//        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
//        YcPage<AirtimeProduct> ycPage = PageUtil.queryYcPage(airtimeProductDao, filters,
//            pageNumber, pageSize, new Sort(Direction.DESC, sortType), AirtimeProduct.class);
        YcPage<AirtimeProduct> ycPage = airtimeProductSqlDao.queryPageAirtimeProduct(searchParams, pageNumber, pageSize);
        return ycPage;
    }

    @Override
    public List<AirtimeProduct> queryProduct(AirtimeProduct airtimeProduct)
    {
        // TODO Auto-generated method stub
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        if (!airtimeProduct.getCarrierName().isEmpty())
        {
            filters.put(EntityConstant.AirtimeProduct.CARRIER_NAME,
                new SearchFilter(EntityConstant.AirtimeProduct.CARRIER_NAME, Operator.EQ,
                    airtimeProduct.getCarrierName()));
        }

        if (!airtimeProduct.getProvince().isEmpty())
        {
            filters.put(EntityConstant.AirtimeProduct.PROVINCE, new SearchFilter(
                EntityConstant.AirtimeProduct.PROVINCE, Operator.EQ, airtimeProduct.getProvince()));
        }

        if (!(airtimeProduct.getParValue() == null))
        {
            filters.put(EntityConstant.AirtimeProduct.PARVALUE, new SearchFilter(
                EntityConstant.AirtimeProduct.PARVALUE, Operator.EQ, airtimeProduct.getParValue()));
        }
        filters.put(EntityConstant.AirtimeProduct.PARVALUE, new SearchFilter(
            EntityConstant.AirtimeProduct.PARVALUE, Operator.EQ, airtimeProduct.getParValue()));
        Specification<AirtimeProduct> spec = DynamicSpecifications.bySearchFilter(
            filters.values(), AirtimeProduct.class);
        List<AirtimeProduct> list = airtimeProductDao.findAll(spec);
        return list;
    }

    @Override
    public AirtimeProduct queryAirtimeProductByDeleteId(Long productId)
    {
        // TODO Auto-generated method stub
        AirtimeProduct airtimeProduct = airtimeProductDao.getProductByDeleteId(productId);
        return airtimeProduct;
    }
}
