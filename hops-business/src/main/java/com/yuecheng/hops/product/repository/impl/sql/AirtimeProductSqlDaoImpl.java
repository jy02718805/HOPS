/*
 * 文件名：CurrencyAccountDaoImpl.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月29日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.product.repository.impl.sql;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.DecimalPlaces;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.BigDecimalUtil;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.product.entity.airtimes.AirtimeProduct;
import com.yuecheng.hops.product.repository.AirtimeProductSqlDao;
import com.yuecheng.hops.product.repository.OracleSql;


@Service("airtimeProductSqlDao")
public class AirtimeProductSqlDaoImpl implements AirtimeProductSqlDao
{

    @PersistenceContext
    private EntityManager em;

    private static final Logger logger = LoggerFactory.getLogger(AirtimeProductSqlDaoImpl.class);

    public static Query setParameter(Map<String, Object> fields, Query query)
    {
        for (Map.Entry<String, Object> entry : fields.entrySet())
        {
            query.setParameter(entry.getKey(), BeanUtils.isNotNull(entry.getValue())?entry.getValue().toString():"");
        }
        return query;
    }

    @SuppressWarnings("unchecked")
    @Override
    public YcPage<AirtimeProduct> queryPageAirtimeProduct(Map<String, Object> searchParams,int pageNumber, int pageSize)
    {
        String pageSql = OracleSql.AirtimeProduct.PAGE_SQL;
        String allSql = OracleSql.AirtimeProduct.PAGE_All_SQL;
        logger.debug("AirtimeProductSqlDaoImpl---queryPageAirtimeProduct---pageSql=" + pageSql
            + ",allSql=" + allSql);
        Map<String, Object> fields = searchParams;
        fields.put(EntityConstant.AirtimeProduct.CARRIER_NAME, BeanUtils.isNotNull(fields.get(EntityConstant.AirtimeProduct.CARRIER_NAME))?fields.get(EntityConstant.AirtimeProduct.CARRIER_NAME):"");
        fields.put(EntityConstant.AirtimeProduct.PROVINCE, BeanUtils.isNotNull(fields.get(EntityConstant.AirtimeProduct.PROVINCE))?fields.get(EntityConstant.AirtimeProduct.PROVINCE):"");
        fields.put(EntityConstant.AirtimeProduct.CITY, BeanUtils.isNotNull(fields.get(EntityConstant.AirtimeProduct.CITY))?fields.get(EntityConstant.AirtimeProduct.CITY):"");
        fields.put(EntityConstant.AirtimeProduct.PRODUCT_STATUS, Constant.Product.PRODUCT_DELETE);
        fields.put(EntityConstant.AirtimeProduct.PARVALUE, BeanUtils.isNotNull(fields.get(EntityConstant.AirtimeProduct.PARVALUE))?fields.get(EntityConstant.AirtimeProduct.PARVALUE):"");
        fields.put(EntityConstant.AirtimeProduct.BUSINESS_TYPE, BeanUtils.isNotNull(fields.get(EntityConstant.AirtimeProduct.BUSINESS_TYPE))?fields.get(EntityConstant.AirtimeProduct.BUSINESS_TYPE):"");
        Query allQuery = em.createNativeQuery(allSql, AirtimeProduct.class);
        allQuery = setParameter(fields, allQuery);
        int countTotal=allQuery.getResultList().size();
        Double pageTotal = BigDecimalUtil.divide(new BigDecimal(countTotal), new BigDecimal(pageSize),
        DecimalPlaces.ZERO.value(), BigDecimal.ROUND_CEILING).doubleValue();
        
        fields.put(EntityConstant.Page.PAGE_NUMBER, BeanUtils.isNotNull(pageNumber)?pageNumber:0);
        fields.put(EntityConstant.Page.PAGE_SIZE, BeanUtils.isNotNull(pageSize)?pageSize:10);
        Query pageQuery = em.createNativeQuery(pageSql, AirtimeProduct.class);
        pageQuery = setParameter(fields, pageQuery);
        YcPage<AirtimeProduct> ycPage = new YcPage<AirtimeProduct>();
        ycPage.setList(pageQuery.getResultList());
        ycPage.setCountTotal(countTotal);
        ycPage.setPageTotal(pageTotal.intValue());
        return ycPage;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AirtimeProduct> fuzzyQueryAirtimeProductsByParams(String province,
                                                                  String parValue,
                                                                  String carrierName, String city, String businessType)
    {
        String allSql = OracleSql.AirtimeProduct.PAGE_All_SQL;
        Map<String, Object> fields = new HashMap<String, Object>();
        fields.put(EntityConstant.AirtimeProduct.CARRIER_NAME,!StringUtil.isNullOrEmpty(carrierName)?carrierName:StringUtil.initString());
        fields.put(EntityConstant.AirtimeProduct.PROVINCE,!StringUtil.isNullOrEmpty(province)?province:StringUtil.initString());
        fields.put(EntityConstant.AirtimeProduct.CITY,!StringUtil.isNullOrEmpty(city)?city:StringUtil.initString());
        fields.put(EntityConstant.AirtimeProduct.PARVALUE,!StringUtil.isNullOrEmpty(parValue)?parValue:StringUtil.initString());
        fields.put(EntityConstant.AirtimeProduct.BUSINESS_TYPE,!StringUtil.isNullOrEmpty(businessType)?businessType:StringUtil.initString());
        fields.put(EntityConstant.AirtimeProduct.PRODUCT_STATUS,Constant.Product.PRODUCT_DELETE);
        Query allQuery = em.createNativeQuery(allSql, AirtimeProduct.class);
        allQuery = setParameter(fields, allQuery);
        List<AirtimeProduct> products= allQuery.getResultList();
        return products;
    }
}
