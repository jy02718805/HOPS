package com.yuecheng.hops.product.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.PageUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.product.entity.property.ProductProperty;
import com.yuecheng.hops.product.entity.type.ProductType;
import com.yuecheng.hops.product.entity.type.relation.ProductTypeRelation;
import com.yuecheng.hops.product.repository.ProductPropertyDao;
import com.yuecheng.hops.product.repository.ProductTypeDao;
import com.yuecheng.hops.product.repository.ProductTypeRelationDao;
import com.yuecheng.hops.product.service.ProductTypeService;


@Service("productTypeService")
public class ProductTypeServiceImpl implements ProductTypeService
{
    @Autowired
    ProductTypeDao productTypeDao;

    @Autowired
    ProductPropertyDao productPropertyDao;

    @Autowired
    ProductTypeRelationDao productTypeRelationDao;

    @Override
    public ProductType saveProductType(ProductType pt)
    {
        try
        {
            pt = productTypeDao.save(pt);
            // TODO Auto-generated method stub
            List<ProductProperty> productPropertys = pt.getPropertyTypes();
            for (Iterator<ProductProperty> iterator = productPropertys.iterator(); iterator.hasNext();)
            {
                ProductProperty productProperty = iterator.next();
                Long productPropertyId = Long.valueOf(productProperty.getProductPropertyId());
                // attribute是ProductProperty的ID value是ProductPropertyValue的value
                ProductTypeRelation productTypeRelation = new ProductTypeRelation();
                productTypeRelation.setTypeId(pt.getTypeId());
                productTypeRelation.setProductPropertyId(productPropertyId);
                productTypeRelationDao.save(productTypeRelation);
            }
            return pt;
        }
        catch (Exception e)
        {
            String[] msgParams = new String[] {pt.toString()};
            String[] viewParams = new String[] {};
            ApplicationException ae = new ApplicationException("businesss000010", msgParams,
                viewParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public ProductType editProductType(ProductType pt)
    {
        try
        {
            productTypeDao.updateProductTypeNameById(pt.getProductTypeName(), pt.getTypeId());
            List<ProductTypeRelation> ptr_list = productTypeRelationDao.queryProductTypeRelationByTypeId(pt.getTypeId());
            for (ProductTypeRelation productTypeRelation : ptr_list)
            {
                productTypeRelationDao.delete(productTypeRelation.getId());
            }

            List<ProductProperty> productPropertys = pt.getPropertyTypes();
            for (Iterator<ProductProperty> iterator = productPropertys.iterator(); iterator.hasNext();)
            {
                ProductProperty productProperty = iterator.next();
                Long productPropertyId = Long.valueOf(productProperty.getProductPropertyId());
                // attribute是ProductProperty的ID value是ProductPropertyValue的value
                ProductTypeRelation productTypeRelation = new ProductTypeRelation();
                productTypeRelation.setTypeId(pt.getTypeId());
                productTypeRelation.setProductPropertyId(productPropertyId);
                productTypeRelationDao.save(productTypeRelation);
            }
            return pt;
        }
        catch (Exception e)
        {
            String[] msgParams = new String[] {pt.toString()};
            String[] viewParams = new String[] {};
            // throw new ApplicationException("businesss000008",msgParams,viewParams,new
            // Exception());
            ApplicationException ae = new ApplicationException("businesss000008", msgParams,
                viewParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public YcPage<ProductType> queryCurrencyAccount(Map<String, Object> searchParams,
                                                    int pageNumber, int pageSize, String sortType)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        YcPage<ProductType> ycPage = PageUtil.queryYcPage(productTypeDao, filters, pageNumber, pageSize,
            new Sort(Direction.DESC, EntityConstant.ProductType.TYPE_ID), ProductType.class);
        return ycPage;
    }

    @Override
    public ProductType queryProductTypeById(ProductType pt)
    {
        pt = productTypeDao.findOne(pt.getTypeId());

        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.ProductType.TYPE_ID, new SearchFilter(
            EntityConstant.ProductType.TYPE_ID, Operator.EQ, pt.getTypeId()));
        Specification<ProductTypeRelation> spec = DynamicSpecifications.bySearchFilter(
            filters.values(), ProductTypeRelation.class);
        List<ProductTypeRelation> productTypeRelations = productTypeRelationDao.findAll(spec);

        List<ProductProperty> productPropertys = new ArrayList<ProductProperty>();
        for (Iterator<ProductTypeRelation> iterator = productTypeRelations.iterator(); iterator.hasNext();)
        {
            ProductTypeRelation productTypeRelation = iterator.next();
            ProductProperty pp = productPropertyDao.findOne(productTypeRelation.getProductPropertyId());
            productPropertys.add(pp);
        }
        pt.setPropertyTypes(productPropertys);
        return pt;
    }

    @Override
    public List<ProductType> getAllProductType()
    {
        List<ProductType> allProductType = new ArrayList<ProductType>();

        List<ProductType> list = (List<ProductType>)productTypeDao.findAll();
        for (Iterator<ProductType> iterator = list.iterator(); iterator.hasNext();)
        {
            ProductType productType = iterator.next();
            List<ProductProperty> productPropertys = new ArrayList<ProductProperty>();
            List<ProductTypeRelation> productTypeRelations = productTypeRelationDao.queryProductTypeRelationByTypeId(productType.getTypeId());
            for (Iterator<ProductTypeRelation> iterator2 = productTypeRelations.iterator(); iterator2.hasNext();)
            {
                ProductTypeRelation productTypeRelation = iterator2.next();
                ProductProperty pp = productPropertyDao.findOne(productTypeRelation.getProductPropertyId());
                productPropertys.add(pp);
            }
            productType.setPropertyTypes(productPropertys);
            allProductType.add(productType);
        }
        return allProductType;
    }

    @Override
    @Transactional
    public void deleteProductType(ProductType pt)
    {
        ProductType productType = productTypeDao.findOne(pt.getTypeId());
        if (productType != null)
        {
            productTypeDao.delete(productType);
            Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
            filters.put(EntityConstant.ProductType.TYPE_ID, new SearchFilter(
                EntityConstant.ProductType.TYPE_ID, Operator.EQ, productType.getTypeId()));
            Specification<ProductTypeRelation> spec = DynamicSpecifications.bySearchFilter(
                filters.values(), ProductTypeRelation.class);
            productTypeRelationDao.delete(productTypeRelationDao.findAll(spec));
        }
        else
        {
            String[] msgParams = new String[] {pt.toString()};
            String[] viewParams = new String[] {};
            // throw new ApplicationException("businesss000009",msgParams,viewParams,new
            // Exception());
            ApplicationException ae = new ApplicationException("businesss000009", msgParams,
                viewParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

	@Override
	public ProductType queryProductTypeById(Long id)
	{
		return productTypeDao.findOne(id);
	}

}
