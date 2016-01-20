/*
 * 文件名：RebateRecordDaoImpl.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2014年11月5日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.rebate.repository.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.DecimalPlaces;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.BigDecimalUtil;
import com.yuecheng.hops.rebate.entity.RebateRecord;
import com.yuecheng.hops.rebate.repository.OracleSql;
import com.yuecheng.hops.rebate.repository.RebateRecordDaoSql;

public class RebateRecordDaoImpl implements RebateRecordDaoSql
{
    private static final Logger logger = LoggerFactory.getLogger(RebateRecordDaoImpl.class);
    
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    @PersistenceContext
    private EntityManager em;
    
    @SuppressWarnings("unchecked")
    @Override
    public List<RebateRecord> queryRebateRecordByParams(Long merchantId, Long rebateMerchantId,
                                                        Date rebateDate, String rebateProductId,
                                                        Long rebateRuleId)
    {
        try
        {
            String rebateDateStr=BeanUtils.isNotNull(rebateDate)?format.format(rebateDate):"";
            String allSql = OracleSql.RebateRecord.All_SQL;
            logger.debug("RebateRecordSqlDao---queryRebateRecordByParams---allSql=" + allSql);
            Map<String, Object> fields = new HashMap<String,Object>();
            fields.put(EntityConstant.RebateRecord.MERCHANT_ID, BeanUtils.isNotNull(merchantId)?merchantId:"");
            fields.put(EntityConstant.RebateRecord.REBATE_MERCHANT_ID, BeanUtils.isNotNull(rebateMerchantId)?rebateMerchantId:"");
            fields.put(EntityConstant.RebateRecord.REBATE_DATE, BeanUtils.isNotNull(rebateDateStr)?rebateDateStr:"");
            fields.put(EntityConstant.RebateRecord.REBATE_PRODUCT_ID, BeanUtils.isNotNull(rebateProductId)?rebateProductId:"");
            fields.put(EntityConstant.RebateRecord.REBATE_RULE_ID, BeanUtils.isNotNull(rebateRuleId)?rebateRuleId:"");
            Query allQuery = em.createNativeQuery(allSql, RebateRecord.class);
            allQuery = setParameter(fields, allQuery);
            return allQuery.getResultList();
        }
        catch (Exception e)
        {
            logger.error("queryRebateRecordByParams exception info["+ e +"]");
            throw ExceptionUtil.throwException(e);
        }
    }
    
    
    @SuppressWarnings("unchecked")
	public YcPage<RebateRecord> queryPageRebateRecord(Map<String, Object> searchParams,
            Date beginDate, Date endDate, int pageNumber,int pageSize, BSort bsort)
	{
        try
        {
            String pageSql = OracleSql.RebateRecord.PAGE_SQL;
            String allSql = OracleSql.RebateRecord.PAGE_All_SQL;
            logger.debug("RebateRecordSqlDao---queryPageRebateRecord---pageSql=" + pageSql
            + ",allSql=" + allSql);
            Map<String, Object> fields = searchParams;
            fields.put(EntityConstant.RebateRecord.MERCHANT_ID, BeanUtils.isNotNull(fields.get(EntityConstant.RebateRecord.MERCHANT_ID))?fields.get(EntityConstant.RebateRecord.MERCHANT_ID):"");
            fields.put(EntityConstant.RebateRecord.REBATE_MERCHANT_ID, BeanUtils.isNotNull(fields.get(EntityConstant.RebateRecord.REBATE_MERCHANT_ID))?fields.get(EntityConstant.RebateRecord.REBATE_MERCHANT_ID):"");
            fields.put(EntityConstant.RebateRecord.BEGIN_DATE, BeanUtils.isNotNull(beginDate)?format.format(beginDate):"");
            fields.put(EntityConstant.RebateRecord.END_DATE, BeanUtils.isNotNull(endDate)?format.format(endDate):"");
            fields.put(EntityConstant.RebateRecord.STATUS, BeanUtils.isNotNull(fields.get(EntityConstant.RebateRecord.STATUS))?fields.get(EntityConstant.RebateRecord.STATUS):"");
            Query allQuery = em.createNativeQuery(allSql, RebateRecord.class);
            allQuery = setParameter(fields, allQuery);
            int countTotal=allQuery.getResultList().size();
            Double pageTotal = BigDecimalUtil.divide(new BigDecimal(countTotal), new BigDecimal(pageSize),
            DecimalPlaces.ZERO.value(), BigDecimal.ROUND_CEILING).doubleValue();
            
            fields.put(EntityConstant.Page.PAGE_NUMBER, BeanUtils.isNotNull(pageNumber)?pageNumber:0);
            fields.put(EntityConstant.Page.PAGE_SIZE, BeanUtils.isNotNull(pageSize)?pageSize:10);
            Query pageQuery = em.createNativeQuery(pageSql, RebateRecord.class);
            pageQuery = setParameter(fields, pageQuery);
            YcPage<RebateRecord> ycPage = new YcPage<RebateRecord>();
            ycPage.setList(pageQuery.getResultList());
            ycPage.setCountTotal(countTotal);
            ycPage.setPageTotal(pageTotal.intValue());
            return ycPage;
        }
        catch (Exception e)
        {
            logger.error("queryPageRebateRecord exception info["+ e +"]");
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
