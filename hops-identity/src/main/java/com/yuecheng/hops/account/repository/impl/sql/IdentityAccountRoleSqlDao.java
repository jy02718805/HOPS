package com.yuecheng.hops.account.repository.impl.sql;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.account.entity.CCYAccount;
import com.yuecheng.hops.account.entity.CurrencyAccount;
import com.yuecheng.hops.account.entity.vo.SpAccountVo;
import com.yuecheng.hops.account.repository.IdentityAccountRoleDao;
import com.yuecheng.hops.account.repository.OracleSql;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.DecimalPlaces;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.BigDecimalUtil;


@Service("identityAccountRoleDao")
public class IdentityAccountRoleSqlDao implements IdentityAccountRoleDao
{

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(IdentityAccountRoleSqlDao.class);

    @Override
    public YcPage<SpAccountVo> queryCurrencyAccountBySp(Map<String, Object> searchParams,
                                                        int pageNumber, int pageSize)
    {
        int startIndex = pageNumber * pageSize - pageSize;
        int endIndex = startIndex + pageSize;

        YcPage<SpAccountVo> ycPage = new YcPage<SpAccountVo>();
        List<SpAccountVo> accountSpVos = new ArrayList<SpAccountVo>();
        try
        {
            String sqlCount = "select count(*) from "
                              + OracleSql.IdentityAccountRole.queryCurrencyAccountBySp_Sql;
            Query query = em.createNativeQuery(sqlCount);
            setCurrencyAccountBySpParameter(searchParams, query, 0, 0);
            BigDecimal total = (BigDecimal)query.getSingleResult();
            Double pageTotal = BigDecimalUtil.divide(total, new BigDecimal(pageSize),
                DecimalPlaces.ZERO.value(), BigDecimal.ROUND_CEILING).doubleValue();
            String sql = "select * from ("
                         + OracleSql.IdentityAccountRole.queryCurrencyAccountBySp_Insidesql
                         + OracleSql.IdentityAccountRole.queryCurrencyAccountBySp_Sql
                         + ") where rn>:startIndex";
            query = em.createNativeQuery(sql);
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            setCurrencyAccountBySpParameter(searchParams, query, startIndex, endIndex);
            @SuppressWarnings("rawtypes")
            List list = query.getResultList();
            for (Object item : list)
            {
                Map<String, Object> row = (Map<String, Object>)item;
                SpAccountVo spAccountVo = new SpAccountVo();
                BeanUtils.transMap2Bean(row, spAccountVo);
                accountSpVos.add(spAccountVo);
            }

            ycPage.setList(accountSpVos);
            ycPage.setCountTotal(total.intValue());
            ycPage.setPageTotal(pageTotal.intValue());
        }
        catch (Exception e)
        {
            LOGGER.error("[IdentityAccountRoleSqlDao :queryAccountWithSp()][报错" + ExceptionUtil.getStackTraceAsString(e)
                         + "]");
            throw ExceptionUtil.throwException(e);
        }
        return ycPage;
    }

    public YcPage<CCYAccount> queryCurrencyAccountByMerchant(Map<String, Object> searchParams,
                                                             int pageNumber, int pageSize)
    {
        try
        {
            int startIndex = pageNumber * pageSize - pageSize;
            int endIndex = startIndex + pageSize;

            String pageTotal_sql = "select count(*) from "
                                   + OracleSql.IdentityAccountRole.queryCurrencyAccountByMerchant_Sql;
            Query query = em.createNativeQuery(pageTotal_sql);

            setCurrencyAccountByMerchantParameter(searchParams, query, 0, 0);
            BigDecimal total = (BigDecimal)query.getSingleResult();
            Double pageTotal = BigDecimalUtil.divide(total, new BigDecimal(pageSize),
                DecimalPlaces.ZERO.value(), BigDecimal.ROUND_CEILING).doubleValue();

            String sql = "select * from ("
                         + OracleSql.IdentityAccountRole.queryCurrencyAccountByMerchant_Insidesql
                         + OracleSql.IdentityAccountRole.queryCurrencyAccountByMerchant_Sql
                         + ") where rn>:startIndex";
            query = em.createNativeQuery(sql, CurrencyAccount.class);
            setCurrencyAccountByMerchantParameter(searchParams, query, startIndex, endIndex);
            @SuppressWarnings("unchecked")
            List<CCYAccount> cabList = query.getResultList();

            YcPage<CCYAccount> ycPage = new YcPage<CCYAccount>();
            ycPage.setList(cabList);
            ycPage.setCountTotal(total.intValue());
            ycPage.setPageTotal(pageTotal.intValue());
            return ycPage;
        }
        catch (Exception e)
        {
            LOGGER.error("queryCurrencyAccountByMerchant exception info[" + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

    private void setCurrencyAccountByMerchantParameter(Map<String, Object> searchParams,
                                                       Query query, int startIndex, int endIndex)
    {
        // TODO Auto-generated method stub
        query.setParameter("merchantName", searchParams.get(EntityConstant.Merchant.MERCHANT_NAME));

        query.setParameter("relation",
            searchParams.get(EntityConstant.IdentityAccountRole.RELATION));
        query.setParameter("identityType",
            searchParams.get(EntityConstant.IdentityAccountRole.IDENTITY_TYPE));
        query.setParameter("merchantType", searchParams.get(EntityConstant.Merchant.MERCHANT_TYPE));
        query.setParameter("accountTypeId",
            searchParams.get(EntityConstant.IdentityAccountRole.ACCOUNT_TYPE));

        if (endIndex > 0)
        {
            query.setParameter("rownum", endIndex);

            query.setParameter("startIndex", startIndex);
        }
        else
        {
            query.setParameter("rownum", "");
        }

    }

    private void setCurrencyAccountBySpParameter(Map<String, Object> searchParams, Query query,
                                                 int startIndex, int endIndex)
    {
        // TODO Auto-generated method stub
        query.setParameter("identityType",
            searchParams.get(EntityConstant.SPAccount.IDENTITY_TYPE));
        query.setParameter("typeModel", searchParams.get(EntityConstant.SPAccount.TYPE_MODEL));
        query.setParameter("accountTypeId",
            searchParams.get(EntityConstant.SPAccount.ACCOUNT_TYPE));

        query.setParameter("relation", searchParams.get(EntityConstant.SPAccount.RELATION));
        query.setParameter("merchantName",
            searchParams.get(EntityConstant.SPAccount.MERCHANT_NAME));
        query.setParameter("spName", searchParams.get(EntityConstant.SPAccount.SP_NAME));

        if (endIndex > 0)
        {
            query.setParameter("rownum", endIndex);

            query.setParameter("startIndex", startIndex);
        }
        else
        {
            query.setParameter("rownum", "");
        }

    }
}
