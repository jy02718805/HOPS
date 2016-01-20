package com.yuecheng.hops.identity.repository.impl;


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
import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.BigDecimalUtil;
import com.yuecheng.hops.common.utils.Collections3;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.repository.MerchantDaoSql;
import com.yuecheng.hops.identity.repository.OracleSql;


@Service
public class MerchantDaoImpl implements MerchantDaoSql
{
    @PersistenceContext
    private EntityManager em;

    private static final Logger logger = LoggerFactory.getLogger(MerchantDaoImpl.class);

    @Override
    public List<Merchant> queryChildMerchantTreeListById(Long id)
    {
        try
        {
            String sql = "SELECT * FROM Merchant m START WITH m.identity_Id = " + id
                         + " CONNECT BY m.parent_Identity_Id = PRIOR m.identity_Id";
            logger.debug("MerchantSqlDaoImpl:queryChildMerchantTreeListById(" + id + ")[查询语句："
                         + sql + "]");
            Query query = em.createNativeQuery(sql, Merchant.class);
            @SuppressWarnings("unchecked")
            List<Merchant> merchantList = query.getResultList();
            logger.debug("MerchantSqlDaoImpl:queryChildMerchantTreeListById(" + id + ")[返回结果："
                         + merchantList != null ? Collections3.convertToString(merchantList,
                Constant.Common.SEPARATOR) : null + "]");
            return merchantList;
        }
        catch (Exception e)
        {
            logger.error("queryChildMerchantTreeListById exception info[" + e + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

    @Override
    public List<Merchant> queryChildMerchantTreeListByPId(Long pid)
    {

        try
        {
            String sql = "SELECT * FROM Merchant m START WITH m.parent_Identity_Id = " + pid
                         + " CONNECT BY m.parent_Identity_Id = PRIOR m.identity_Id";
            logger.debug("MerchantSqlDaoImpl:queryChildMerchantTreeListByPId(" + pid + ")[查询语句："
                         + sql + "]");
            Query query = em.createNativeQuery(sql, Merchant.class);
            @SuppressWarnings("unchecked")
            List<Merchant> merchantList = query.getResultList();
            logger.debug("MerchantSqlDaoImpl:queryChildMerchantTreeListByPId(" + pid + ")[返回结果："
                         + merchantList != null ? Collections3.convertToString(merchantList,
                Constant.Common.SEPARATOR) : null + "]");
            return merchantList;
        }
        catch (Exception e)
        {
            logger.error("queryChildMerchantTreeListByPId exception info[" + e + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

    @Override
    public List<Merchant> queryParentMerchantTreeListById(Long id)
    {

        try
        {
            String sql = "SELECT * FROM Merchant m START WITH m.identity_Id = " + id
                         + " CONNECT BY PRIOR m.parent_Identity_Id = m.identity_Id";
            logger.debug("MerchantSqlDaoImpl:queryParentMerchantTreeListById(" + id + ")[查询语句："
                         + sql + "]");
            Query query = em.createNativeQuery(sql, Merchant.class);
            @SuppressWarnings("unchecked")
            List<Merchant> merchantList = query.getResultList();
            logger.debug("MerchantSqlDaoImpl:queryParentMerchantTreeListById(" + id + ")[返回结果："
                         + merchantList != null ? Collections3.convertToString(merchantList,
                Constant.Common.SEPARATOR) : null + "]");
            return merchantList;
        }
        catch (Exception e)
        {
            logger.error("queryParentMerchantTreeListById exception info[" + e + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

    @Override
    public List<Merchant> queryParentMerchantTreeListByPId(Long pid)
    {
        try
        {
            String sql = "SELECT * FROM Merchant m START WITH m.parent_Identity_Id = " + pid
                         + " CONNECT BY PRIOR m.parent_Identity_Id = m.identity_Id";
            logger.debug("MerchantSqlDaoImpl:queryParentMerchantTreeListByPId(" + pid + ")[查询语句："
                         + sql + "]");
            Query query = em.createNativeQuery(sql, Merchant.class);
            @SuppressWarnings("unchecked")
            List<Merchant> merchantList = query.getResultList();
            logger.debug("MerchantSqlDaoImpl:queryChildMerchantTreeListById(" + pid + ")[返回结果："
                         + merchantList != null ? Collections3.convertToString(merchantList,
                Constant.Common.SEPARATOR) : null + "]");
            return merchantList;
        }
        catch (Exception e)
        {
            logger.error("queryParentMerchantTreeListById exception info[" + e + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public YcPage<Merchant> queryPageMerchant(Map<String, Object> searchParams, int pageNumber,
                                              int pageSize, String sortType,
                                              MerchantType merchantType, String merchantName)
    {
        try
        {
            String pageSql = OracleSql.Merchant.PAGE_SQL;
            String allSql = OracleSql.Merchant.All_SQL;
            logger.debug("SecurityRuleSqlDao---querySecurityRuleList---pageSql=" + pageSql
                         + ",allSql=" + allSql);
            Map<String, Object> fields = new HashMap<String, Object>();
            fields.put(EntityConstant.Merchant.MERCHANT_NAME,
                BeanUtils.isNotNull(merchantName) ? merchantName : "");
            fields.put(EntityConstant.Merchant.MERCHANT_TYPE,
                BeanUtils.isNotNull(merchantType) ? merchantType.toString() : "");
            Query allQuery = em.createNativeQuery(allSql, Merchant.class);
            allQuery = setParameter(fields, allQuery);
            Double pageTotal = BigDecimalUtil.divide(
                new BigDecimal(allQuery.getResultList().size()), new BigDecimal(pageSize),
                DecimalPlaces.ZERO.value(), BigDecimal.ROUND_CEILING).doubleValue();

            fields.put(EntityConstant.Page.PAGE_NUMBER,
                BeanUtils.isNotNull(pageNumber) ? pageNumber : 0);
            fields.put(EntityConstant.Page.PAGE_SIZE,
                BeanUtils.isNotNull(pageSize) ? pageSize : 10);
            Query pageQuery = em.createNativeQuery(pageSql, Merchant.class);
            pageQuery = setParameter(fields, pageQuery);
            YcPage<Merchant> ycPage = new YcPage<Merchant>();
            ycPage.setList(pageQuery.getResultList());
            ycPage.setCountTotal(allQuery.getResultList().size());
            ycPage.setPageTotal(pageTotal.intValue());
            return ycPage;
        }
        catch (Exception e)
        {
            logger.error("queryPageMerchant exception info[" + e + "]");
            throw ExceptionUtil.throwException(e);
        }
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

    public List<Merchant> queryMerchantsByName(String merchantName)
    {
        try
        {
            String sql = "SELECT * FROM Merchant m where m.merchant_name like '%'||:merchantName||'%'";
            logger.debug("MerchantSqlDaoImpl: queryMerchantsByName(" + merchantName + ")[查询语句："
                         + sql + "]");
            Query query = em.createNativeQuery(sql, Merchant.class);
            query.setParameter("merchantName", merchantName);
            @SuppressWarnings("unchecked")
            List<Merchant> merchantList = query.getResultList();
            logger.debug("MerchantSqlDaoImpl: queryMerchantsByName(" + merchantName + ")[返回结果："
                         + merchantList != null ? Collections3.convertToString(merchantList,
                Constant.Common.SEPARATOR) : null + "]");
            return merchantList;
        }
        catch (Exception e)
        {
            logger.error("queryMerchantsByName exception info[" + e + "]");
            throw ExceptionUtil.throwException(e);
        }

    }
}
