package com.yuecheng.hops.product.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.PageUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.product.entity.property.ProductProperty;
import com.yuecheng.hops.product.repository.ProductPropertyDao;
import com.yuecheng.hops.product.service.ProductPropertyService;


@Service("productPropertyService")
public class ProductPropertyServiceImpl implements ProductPropertyService
{

    @Autowired
    ProductPropertyDao productPropertyDao;

    // @Autowired
    // ProductPropertyValueDao productPropertyValueDao;

    @Override
    public ProductProperty saveProductProperty(ProductProperty pp)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.ProductProperty.PARAM_NAME, new SearchFilter(
            EntityConstant.ProductProperty.PARAM_NAME, Operator.EQ, pp.getParamName()));

        Specification<ProductProperty> spec = DynamicSpecifications.bySearchFilter(
            filters.values(), ProductProperty.class);
        List<ProductProperty> pp_list = productPropertyDao.findAll(spec, new Sort(Direction.ASC,
            EntityConstant.ProductProperty.PRODUCT_PROPERTY_ID));
        if (pp_list.size() > 0)
        {
            // 已经存在该名字的属性
            String[] msgParams = new String[] {pp.toString()};
            String[] viewParams = new String[] {};
            // throw new ApplicationException("businesss000017",msgParams,viewParams,new
            // Exception());
            ApplicationException e = new ApplicationException("businesss000017", msgParams,
                viewParams);
            throw ExceptionUtil.throwException(e);
        }
        else
        {
            pp = productPropertyDao.save(pp);
            return pp;
        }
    }

    @Override
    public ProductProperty editProductProperty(ProductProperty pp)
    {
        return null;
    }

    // @Override
    // public ProductProperty editProductProperty(ProductProperty pp) {
    // Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
    // filters.put("paramName", new SearchFilter("paramName", Operator.EQ, pp.getParamName()));
    // Specification<ProductProperty> spec = DynamicSpecifications.bySearchFilter(filters.values(),
    // ProductProperty.class);
    // ProductProperty old_property = productPropertyDao.findOne(spec);
    // if(old_property != null){
    // //查询到原始属性
    // // pp = productPropertyDao.save(pp);
    // filters = new HashMap<String, SearchFilter>();
    // filters.put("productPropertyId", new SearchFilter("productPropertyId", Operator.EQ,
    // old_property.getProductPropertyId()));
    // Specification<ProductPropertyRelation> spec_ProductPropertyRelation =
    // DynamicSpecifications.bySearchFilter(filters.values(), ProductPropertyRelation.class);
    // ProductPropertyRelation ptr =
    // productPropertyRelationDao.findOne(spec_ProductPropertyRelation);
    // if(ptr != null){
    // //保存属性对应值
    // filters = new HashMap<String, SearchFilter>();
    // filters.put("productPropertyId", new SearchFilter("productPropertyValueId", Operator.EQ,
    // ptr.getProductPropertyValueId()));
    // Specification<ProductPropertyValue> spec_ProductPropertyValue =
    // DynamicSpecifications.bySearchFilter(filters.values(), ProductPropertyValue.class);
    // ProductPropertyValue ppValue = productPropertyValueDao.findOne(spec_ProductPropertyValue);
    // ppValue.setValue(pp.getPpValue().getValue());
    // productPropertyValueDao.save(ppValue);
    // }else{
    // ProductPropertyValue ppValue = null;
    // ptr = new ProductPropertyRelation();
    //
    // ppValue = pp.getPpValue();
    // if(ppValue != null){
    // ppValue = productPropertyValueDao.save(ppValue);
    // }else{
    // //未找到属性对应的值
    // throw new ApplicationException("000002");
    // }
    //
    // ptr.setProductPropertyId(pp.getProductPropertyId());
    // ptr.setProductPropertyValueId(ppValue.getProductPropertyValueId());
    // productPropertyRelationDao.save(ptr);
    //
    // }
    // pp = productPropertyDao.save(pp);
    // }else{
    // //未找到该属性
    // throw new ApplicationException("000002");
    // }
    // return pp;
    // }

    @Override
    public ProductProperty deleteProductProperty(ProductProperty pp)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.ProductProperty.PARAM_NAME, new SearchFilter(
            EntityConstant.ProductProperty.PARAM_NAME, Operator.EQ, pp.getParamName()));
        Specification<ProductProperty> spec = DynamicSpecifications.bySearchFilter(
            filters.values(), ProductProperty.class);
        ProductProperty old_property = productPropertyDao.findOne(spec);
        if (old_property != null)
        {
            productPropertyDao.delete(pp);
        }
        else
        {
            // 未找到该属性
            String[] msgParams = new String[] {pp.toString()};
            String[] viewParams = new String[] {};
            // throw new ApplicationException("businesss000002",msgParams,viewParams,new
            // Exception());
            ApplicationException e = new ApplicationException("businesss000002", msgParams,
                viewParams);
            throw ExceptionUtil.throwException(e);
        }
        return null;
    }

    @Override
    public YcPage<ProductProperty> queryProductProperty(Map<String, Object> searchParams,
                                                        int pageNumber, int pageSize,
                                                        String sortType)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        YcPage<ProductProperty> ycPage = PageUtil.queryYcPage(productPropertyDao, filters, pageNumber,
            pageSize, new Sort(Direction.DESC, EntityConstant.ProductProperty.PRODUCT_PROPERTY_ID), ProductProperty.class);
        return ycPage;
    }

    @Override
    public ProductProperty queryProductPropertyById(Long productPropertyId)
    {
        ProductProperty productProperty = productPropertyDao.findOne(productPropertyId);
        return productProperty;
    }

    @Override
    public List<ProductProperty> getAllProductProperty()
    {
        List<ProductProperty> pageList = (List<ProductProperty>)productPropertyDao.findAll();
        return pageList;
    }
}
