/*
 * 文件名：CurrencyAccountDaoImpl.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月29日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.product.repository.impl.sql;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.DecimalPlaces;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.BigDecimalUtil;
import com.yuecheng.hops.product.entity.relation.AgentProductRelation;
import com.yuecheng.hops.product.repository.AgentProductRelationSqlDao;
import com.yuecheng.hops.product.repository.OracleSql;


@Service("agentProductRelationSqlDao")
public class AgentProductRelationSqlDaoImpl implements AgentProductRelationSqlDao
{

    @PersistenceContext
    private EntityManager em;

    private static final Logger logger = LoggerFactory.getLogger(AgentProductRelationSqlDaoImpl.class);

    @SuppressWarnings("unchecked")
    @Override
    public List<AgentProductRelation> getProductRelationByParams(Map<String, Object> searchParams)
    {
        String sql = OracleSql.AgentProductRelation.Query_List;
        List<AgentProductRelation> aprs = new ArrayList<AgentProductRelation>();
        try
        {
            Query query = em.createNativeQuery(sql, AgentProductRelation.class);
            for (Map.Entry<String, Object> entry : searchParams.entrySet())
            {
                query.setParameter(entry.getKey(),
                    BeanUtils.isNotNull(entry.getValue()) ? entry.getValue().toString() : "");
            }
            aprs = query.getResultList();
        }
        catch (Exception e)
        {
            logger.error("[AgentProductRelationSqlDaoImpl :getProductRelationByParams()] [报错:"
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
        return aprs;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AgentProductRelation> getProductRelationByParams(Map<String, Object> searchParams,
                                                                 Long identityId,
                                                                 String identityType)
    {
        String allSql = OracleSql.AgentProductRelation.Query_Product_Relation;
        String carrierNo = (String)searchParams.get(Operator.EQ
                                                    + "_"
                                                    + EntityConstant.AgentProductRelation.CARRIER_NAME);
        String proviceNo = (String)searchParams.get(Operator.EQ + "_"
                                                    + EntityConstant.AgentProductRelation.PROVINCE);
        String cityNo = (String)searchParams.get(Operator.EQ + "_"
                                                 + EntityConstant.AgentProductRelation.CITY);
        String parValue = (String)searchParams.get(Operator.EQ + "_"
                                                   + EntityConstant.AgentProductRelation.PAR_VALUE);
        logger.debug("AgentProductRelationService---getAllAgentProductRelation---allSql=" + allSql);
        Map<String, Object> fields = new HashMap<String, Object>();
        fields.put(EntityConstant.AgentProductRelation.PAR_VALUE,
            BeanUtils.isNotNull(parValue) ? parValue : "");
        fields.put(EntityConstant.AgentProductRelation.IDENTITY_TYPE,
            BeanUtils.isNotNull(identityType) ? identityType : "");
        fields.put(EntityConstant.AgentProductRelation.IDENTITY_ID,
            BeanUtils.isNotNull(identityId) ? identityId : "");
        fields.put(EntityConstant.AgentProductRelation.CARRIER_NAME,
            BeanUtils.isNotNull(carrierNo) ? carrierNo : "");
        if (StringUtils.isNotBlank(proviceNo))
        {
            allSql = allSql + "and t.province = :province ";
            fields.put(EntityConstant.AgentProductRelation.PROVINCE,
                BeanUtils.isNotNull(proviceNo) ? proviceNo : "");
        }
        else
        {
            allSql = allSql + "and t.province is null ";
        }
        if (StringUtils.isNotBlank(cityNo))
        {
            allSql = allSql + "and t.city = :city ";
            fields.put(EntityConstant.AgentProductRelation.CITY, cityNo);
        }
        else
        {
            allSql = allSql + "and t.city is null ";
        }

        Query allQuery = em.createNativeQuery(allSql, AgentProductRelation.class);
        allQuery = setParameter(fields, allQuery);
        return allQuery.getResultList();
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

    @SuppressWarnings("unchecked")
    @Override
    public YcPage<AgentProductRelation> queryPageAgentProductRelation(Map<String, Object> searchParams,
                                                                      int pageNumber, int pageSize)
    {
        String pageSql = OracleSql.AgentProductRelation.PAGE_SQL;
        String allSql = OracleSql.AgentProductRelation.PAGE_All_SQL;
        logger.debug("AgentProductRelationSqlDao---queryPageAgentProductRelation---pageSql="
                     + pageSql + ",allSql=" + allSql);
        Map<String, Object> fields = searchParams;
        fields.put(
            EntityConstant.AgentProductRelation.IDENTITY_ID,
            BeanUtils.isNotNull(fields.get(EntityConstant.AgentProductRelation.IDENTITY_ID)) ? fields.get(EntityConstant.AgentProductRelation.IDENTITY_ID) : "");
        fields.put(
            EntityConstant.AgentProductRelation.IDENTITY_TYPE,
            BeanUtils.isNotNull(fields.get(EntityConstant.AgentProductRelation.IDENTITY_TYPE)) ? fields.get(EntityConstant.AgentProductRelation.IDENTITY_TYPE) : "");
        fields.put(
            EntityConstant.AgentProductRelation.CARRIER_NAME,
            BeanUtils.isNotNull(fields.get(EntityConstant.AgentProductRelation.CARRIER_NAME)) ? fields.get(EntityConstant.AgentProductRelation.CARRIER_NAME) : "");
        fields.put(
            EntityConstant.AgentProductRelation.PROVINCE,
            BeanUtils.isNotNull(fields.get(EntityConstant.AgentProductRelation.PROVINCE)) ? fields.get(EntityConstant.AgentProductRelation.PROVINCE) : "");
        fields.put(
            EntityConstant.AgentProductRelation.CITY,
            BeanUtils.isNotNull(fields.get(EntityConstant.AgentProductRelation.CITY)) ? fields.get(EntityConstant.AgentProductRelation.CITY) : "");
        fields.put(
            EntityConstant.AgentProductRelation.PAR_VALUE,
            BeanUtils.isNotNull(fields.get(EntityConstant.AgentProductRelation.PAR_VALUE)) ? fields.get(EntityConstant.AgentProductRelation.PAR_VALUE) : "");
        fields.put(
            EntityConstant.AgentProductRelation.BUSINESSTYPE,
            BeanUtils.isNotNull(fields.get(EntityConstant.AgentProductRelation.BUSINESSTYPE)) ? fields.get(EntityConstant.AgentProductRelation.BUSINESSTYPE) : "");
        fields.put(
            EntityConstant.AgentProductRelation.DISCOUNT,
            BeanUtils.isNotNull(fields.get(EntityConstant.AgentProductRelation.DISCOUNT)) ? fields.get(EntityConstant.AgentProductRelation.DISCOUNT) : "");
        fields.put(
            EntityConstant.AgentProductRelation.MAX_DISCOUNT,
            BeanUtils.isNotNull(fields.get(EntityConstant.AgentProductRelation.MAX_DISCOUNT)) ? fields.get(EntityConstant.AgentProductRelation.MAX_DISCOUNT) : "");
        Query allQuery = em.createNativeQuery(allSql, AgentProductRelation.class);
        allQuery = setParameter(fields, allQuery);
        int countTotal = allQuery.getResultList().size();
        Double pageTotal = BigDecimalUtil.divide(new BigDecimal(countTotal),
            new BigDecimal(pageSize), DecimalPlaces.ZERO.value(), BigDecimal.ROUND_CEILING).doubleValue();

        fields.put(EntityConstant.Page.PAGE_NUMBER,
            BeanUtils.isNotNull(pageNumber) ? pageNumber : 0);
        fields.put(EntityConstant.Page.PAGE_SIZE, BeanUtils.isNotNull(pageSize) ? pageSize : 10);
        Query pageQuery = em.createNativeQuery(pageSql, AgentProductRelation.class);
        pageQuery = setParameter(fields, pageQuery);
        YcPage<AgentProductRelation> ycPage = new YcPage<AgentProductRelation>();
        ycPage.setList(pageQuery.getResultList());
        ycPage.setCountTotal(countTotal);
        ycPage.setPageTotal(pageTotal.intValue());
        return ycPage;
    }
}
