/*
 * 文件名：CurrencyAccountDaoImpl.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月29日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.product.repository.impl.sql;


import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.BigDecimalUtil;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.product.entity.airtimes.AirtimeProduct;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;
import com.yuecheng.hops.product.repository.OracleSql;
import com.yuecheng.hops.product.repository.SupplyProductRelationSqlDao;


@Service("supplyProductRelationSqlDao")
public class SupplyProductRelationSqlDaoImpl implements SupplyProductRelationSqlDao
{

    @PersistenceContext
    private EntityManager em;

    private static final Logger logger = LoggerFactory.getLogger(SupplyProductRelationSqlDaoImpl.class);

    @Override
    public List<SupplyProductRelation> getProductRelationByParams(Long identityId,
                                                                  IdentityType idenityType,
                                                                  List<AirtimeProduct> products,
                                                                  String status)
    {
        String sql = "select * from Supply_Product_Relation where 1=1";
        String condition = StringUtil.initString();
        if (products.size() > 0)
        {
            String statu_sql = StringUtil.initString();
            statu_sql = statu_sql + " and (";
            for (AirtimeProduct product : products)
            {
                statu_sql = statu_sql + " or " + EntityConstant.SupplyProductRelation.PRODUCT_ID2
                            + " = " + product.getProductId();
            }
            statu_sql = statu_sql.replaceFirst("or", StringUtil.initString());
            statu_sql = statu_sql + " )";
            condition = condition + statu_sql;
            condition = condition + " and " + EntityConstant.SupplyProductRelation.STATUS + " = '"
                        + Constant.SupplyProductStatus.OPEN_STATUS + "'";
        }
        if (StringUtil.isNotBlank(status))
        {
            condition = condition + " and " + EntityConstant.SupplyProductRelation.STATUS + " = "
                        + status + " ";
        }
        if (identityId != null && identityId.compareTo(0l) > 0)
        {
            condition = condition + " and " + EntityConstant.SupplyProductRelation.IDENTITY_ID_SQL
                        + " = " + identityId + " ";
        }
        if (BeanUtils.isNotNull(idenityType))
        {
            condition = condition + " and "
                        + EntityConstant.SupplyProductRelation.IDENTITY_TYPE_SQL + " = '"
                        + idenityType.toString() + "' ";
        }
        if (condition.length() > 0)
        {
            sql = sql + condition;
        }

        List<SupplyProductRelation> uprs = new ArrayList<SupplyProductRelation>();

        try
        {
            Query query = em.createNativeQuery(sql, SupplyProductRelation.class);
            uprs = query.getResultList();
        }
        catch (Exception e)
        {
            // TODO: handle exception
            logger.error("[SupplyProductRelationSqlDaoImpl :getProductRelationByParams()] [报错:"
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
        return uprs;
    }

    @Override
    public List<SupplyProductRelation> querySupplyProductByProductId(List<AirtimeProduct> products,
                                                                     Long level,
                                                                     BigDecimal discount,
                                                                     BigDecimal quality)
    {
        String sql = "select * from Supply_Product_Relation where 1=1";
        String condition = StringUtil.initString();
        if (products.size() > 0)
        {
            String statu_sql = StringUtil.initString();
            statu_sql = statu_sql + " and (";
            for (AirtimeProduct product : products)
            {
                statu_sql = statu_sql + " or " + EntityConstant.SupplyProductRelation.PRODUCT_ID2
                            + " = " + product.getProductId();
            }
            statu_sql = statu_sql.replaceFirst("or", StringUtil.initString());
            statu_sql = statu_sql + " )";
            condition = condition + statu_sql;
            condition = condition + " and " + EntityConstant.SupplyProductRelation.STATUS + " = '"
                        + Constant.SupplyProductStatus.OPEN_STATUS + "'";
        }
        if (level != null && level > 0)
        {
            condition = condition + " and " + EntityConstant.SupplyProductRelation.MERCHANT_LEVEL2
                        + " = '" + level + "'";
        }
        if (discount != null && discount.compareTo(new BigDecimal(0)) > 0)
        {
            condition = condition + " and " + EntityConstant.SupplyProductRelation.DISCOUNT
                        + " <= '" + discount + "'";
        }
        if (quality != null && quality.compareTo(new BigDecimal(0)) > 0)
        {
            condition = condition + " and " + EntityConstant.SupplyProductRelation.QUALITY
                        + " >= '" + quality + "'";
        }
        if (condition.length() > 0)
        {
            sql = sql + condition;
        }
        List<SupplyProductRelation> uprs = new ArrayList<SupplyProductRelation>();
        try
        {
            Query query = em.createNativeQuery(sql, SupplyProductRelation.class);
            uprs = query.getResultList();
        }
        catch (Exception e)
        {
            logger.error("[SupplyProductRelationServiceImpl :querySupplyProductByProductId()]["
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
        return uprs;
    }

    @Override
    public List<SupplyProductRelation> querySupplyProductByProductId(List<AirtimeProduct> products,
                                                                     Long merchantId)
    {
        String sql = "select * from Supply_Product_Relation where 1=1";
        String condition = StringUtil.initString();
        if (products.size() > 0)
        {
            String statu_sql = StringUtil.initString();
            statu_sql = statu_sql + " and (";
            for (AirtimeProduct product : products)
            {
                statu_sql = statu_sql + " or " + EntityConstant.SupplyProductRelation.PRODUCT_ID2
                            + " = " + product.getProductId();
            }
            statu_sql = statu_sql.replaceFirst("or", StringUtil.initString());
            statu_sql = statu_sql + " )";
            condition = condition + statu_sql;
            condition = condition + " and " + EntityConstant.SupplyProductRelation.STATUS + " = '"
                        + Constant.SupplyProductStatus.OPEN_STATUS + "'";
        }
        if (merchantId != null && merchantId > 0)
        {
            condition = condition + " and " + EntityConstant.SupplyProductRelation.IDENTITY_ID2
                        + " = '" + merchantId + "'";
        }
        if (condition.length() > 0)
        {
            sql = sql + condition;
        }
        List<SupplyProductRelation> uprs = new ArrayList<SupplyProductRelation>();
        try
        {
            Query query = em.createNativeQuery(sql, SupplyProductRelation.class);
            uprs = query.getResultList();
        }
        catch (Exception e)
        {
            logger.error("[SupplyProductRelationServiceImpl :querySupplyProductByProductId()]["
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
        return uprs;
    }

    @Override
    public List<SupplyProductRelation> getProductRelationByParams(Map<String, Object> searchParams)
    {
        // TODO Auto-generated method stub
        String sql = OracleSql.SupplyProductRelation.Query_List;
        List<SupplyProductRelation> uprs = new ArrayList<SupplyProductRelation>();
        try
        {
            Query query = em.createNativeQuery(sql, SupplyProductRelation.class);
            for (Map.Entry<String, Object> entry : searchParams.entrySet())
            {
                query.setParameter(entry.getKey(),
                    BeanUtils.isNotNull(entry.getValue()) ? entry.getValue().toString() : "");
            }
            uprs = query.getResultList();
        }
        catch (Exception e)
        {
            // TODO: handle exception
            logger.error("[SupplyProductRelationSqlDaoImpl :getProductRelationByParams()] [报错:"
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
        return uprs;
    }

    public static Query setParameter(Map<String, Object> fields, Query query)
    {
        for (Map.Entry<String, Object> entry : fields.entrySet())
        {
            query.setParameter(entry.getKey(),
                BeanUtils.isNotNull(entry.getValue()) ? entry.getValue().toString() : "");
        }
        return query;
    }

    @Override
    public YcPage<SupplyProductRelation> queryPageSupplyProductRelation(Map<String, Object> searchParams,
                                                                        int pageNumber,
                                                                        int pageSize)
    {
        String pageSql = OracleSql.SupplyProductRelation.PAGE_SQL;
        String allSql = OracleSql.SupplyProductRelation.PAGE_All_SQL;
        logger.debug("SupplyProductRelationSqlDao---queryPageSupplyProductRelation---pageSql="
                     + pageSql + ",allSql=" + allSql);
        Map<String, Object> fields = searchParams;
        fields.put(
            EntityConstant.SupplyProductRelation.IDENTITY_ID,
            BeanUtils.isNotNull(fields.get(EntityConstant.SupplyProductRelation.IDENTITY_ID)) ? fields.get(EntityConstant.SupplyProductRelation.IDENTITY_ID) : "");
        fields.put(
            EntityConstant.SupplyProductRelation.IDENTITY_TYPE,
            BeanUtils.isNotNull(fields.get(EntityConstant.SupplyProductRelation.IDENTITY_TYPE)) ? fields.get(EntityConstant.SupplyProductRelation.IDENTITY_TYPE) : "");
        fields.put(
            EntityConstant.SupplyProductRelation.CARRIER_NAME,
            BeanUtils.isNotNull(fields.get(EntityConstant.SupplyProductRelation.CARRIER_NAME)) ? fields.get(EntityConstant.SupplyProductRelation.CARRIER_NAME) : "");
        fields.put(
            EntityConstant.SupplyProductRelation.PROVINCE,
            BeanUtils.isNotNull(fields.get(EntityConstant.SupplyProductRelation.PROVINCE)) ? fields.get(EntityConstant.SupplyProductRelation.PROVINCE) : "");
        fields.put(
            EntityConstant.SupplyProductRelation.CITY,
            BeanUtils.isNotNull(fields.get(EntityConstant.SupplyProductRelation.CITY)) ? fields.get(EntityConstant.SupplyProductRelation.CITY) : "");
        fields.put(
            EntityConstant.SupplyProductRelation.PAR_VALUE,
            BeanUtils.isNotNull(fields.get(EntityConstant.SupplyProductRelation.PAR_VALUE)) ? fields.get(EntityConstant.SupplyProductRelation.PAR_VALUE) : "");
        fields.put(
            EntityConstant.SupplyProductRelation.BUSINESSTYPE,
            BeanUtils.isNotNull(fields.get(EntityConstant.SupplyProductRelation.BUSINESSTYPE)) ? fields.get(EntityConstant.SupplyProductRelation.BUSINESSTYPE) : "");
        fields.put(
            EntityConstant.AgentProductRelation.DISCOUNT,
            BeanUtils.isNotNull(fields.get(EntityConstant.SupplyProductRelation.DISCOUNT)) ? fields.get(EntityConstant.SupplyProductRelation.DISCOUNT) : "");
        fields.put(
            EntityConstant.AgentProductRelation.MAX_DISCOUNT,
            BeanUtils.isNotNull(fields.get(EntityConstant.SupplyProductRelation.MAX_DISCOUNT)) ? fields.get(EntityConstant.SupplyProductRelation.MAX_DISCOUNT) : "");

        Query allQuery = em.createNativeQuery(allSql, SupplyProductRelation.class);
        allQuery = setParameter(fields, allQuery);
        int countTotal = allQuery.getResultList().size();
        Double pageTotal = BigDecimalUtil.divide(new BigDecimal(countTotal),
            new BigDecimal(pageSize), DecimalPlaces.ZERO.value(), BigDecimal.ROUND_CEILING).doubleValue();

        fields.put(EntityConstant.Page.PAGE_NUMBER,
            BeanUtils.isNotNull(pageNumber) ? pageNumber : 0);
        fields.put(EntityConstant.Page.PAGE_SIZE, BeanUtils.isNotNull(pageSize) ? pageSize : 10);
        Query pageQuery = em.createNativeQuery(pageSql, SupplyProductRelation.class);
        pageQuery = setParameter(fields, pageQuery);
        YcPage<SupplyProductRelation> ycPage = new YcPage<SupplyProductRelation>();
        ycPage.setList(pageQuery.getResultList());
        ycPage.setCountTotal(countTotal);
        ycPage.setPageTotal(pageTotal.intValue());
        return ycPage;
    }

}
