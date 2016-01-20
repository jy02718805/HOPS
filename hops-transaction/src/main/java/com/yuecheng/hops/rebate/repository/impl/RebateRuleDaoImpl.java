/*
 * 文件名：RebateRuleDaoImpl.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2014年11月4日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.rebate.repository.impl;

import java.math.BigDecimal;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.DecimalPlaces;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.BigDecimalUtil;
import com.yuecheng.hops.rebate.entity.RebateRule;
import com.yuecheng.hops.rebate.repository.OracleSql;
import com.yuecheng.hops.rebate.repository.RebateRuleDaoSql;

public class RebateRuleDaoImpl implements RebateRuleDaoSql
{
    @PersistenceContext
    private EntityManager em;
    
    private static final Logger logger = LoggerFactory.getLogger(RebateRuleDaoImpl.class);

    @SuppressWarnings("unchecked")
    @Override
    public YcPage<RebateRule> queryPageRebateRule(Map<String, Object> searchParams,
                                                  int pageNumber, int pageSize)
    {
        try
        {
            String pageSql = OracleSql.RebateRule.PAGE_SQL;
            String allSql = OracleSql.RebateRule.All_SQL;
            logger.debug("RebateRuleSqlDao---queryRebateRuleList---pageSql=" + pageSql
                         + ",allSql=" + allSql);
            Map<String, Object> fields = searchParams;
            fields.put(EntityConstant.RebateRule.MERCHANT_ID, BeanUtils.isNotNull(fields.get(EntityConstant.RebateRule.MERCHANT_ID))?fields.get(EntityConstant.RebateRule.MERCHANT_ID):"");
            fields.put(EntityConstant.RebateRule.REBATE_MERCHANT_ID, BeanUtils.isNotNull(fields.get(EntityConstant.RebateRule.REBATE_MERCHANT_ID))?fields.get(EntityConstant.RebateRule.REBATE_MERCHANT_ID):"");
            fields.put(EntityConstant.RebateRule.REBATE_TIME_TYPE, BeanUtils.isNotNull(fields.get(EntityConstant.RebateRule.REBATE_TIME_TYPE))?fields.get(EntityConstant.RebateRule.REBATE_TIME_TYPE):"");
            fields.put(EntityConstant.RebateRule.REBATE_TYPE, BeanUtils.isNotNull(fields.get(EntityConstant.RebateRule.REBATE_TYPE))?fields.get(EntityConstant.RebateRule.REBATE_TYPE):"");
            fields.put(EntityConstant.RebateRule.STATUS, Constant.RebateStatus.DEL_STATUS);
            Query allQuery = em.createNativeQuery(allSql, RebateRule.class);
            allQuery = setParameter(fields, allQuery);
            int countTotal=allQuery.getResultList().size();
            Double pageTotal = BigDecimalUtil.divide(new BigDecimal(countTotal), new BigDecimal(pageSize),
                DecimalPlaces.ZERO.value(), BigDecimal.ROUND_CEILING).doubleValue();
            

            fields.put(EntityConstant.Page.PAGE_NUMBER, BeanUtils.isNotNull(pageNumber)?pageNumber:0);
            fields.put(EntityConstant.Page.PAGE_SIZE, BeanUtils.isNotNull(pageSize)?pageSize:10);
            Query pageQuery = em.createNativeQuery(pageSql, RebateRule.class);
            pageQuery = setParameter(fields, pageQuery);
            YcPage<RebateRule> ycPage = new YcPage<RebateRule>();
            ycPage.setList(pageQuery.getResultList());
            ycPage.setCountTotal(countTotal);
            ycPage.setPageTotal(pageTotal.intValue());
            return ycPage;
        }
        catch (Exception e)
        {
            logger.error("queryPageRebateRule exception info["+ ExceptionUtil.getStackTraceAsString(e) +"]");
            throw ExceptionUtil.throwException(e);
        }
    }

    public static Query setParameter(Map<String, Object> fields, Query query)
    {
        for (Map.Entry<String, Object> entry : fields.entrySet())
        {
        	query.setParameter(entry.getKey(), BeanUtils.isNotNull(entry.getValue())?entry.getValue().toString():"");
        }
        return query;
    }

}
