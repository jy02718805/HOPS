package com.yuecheng.hops.transaction.config.repository.impl.sql;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.enump.DecimalPlaces;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BigDecimalUtil;
import com.yuecheng.hops.transaction.config.entify.profitImputation.ProfitImputationInfo;
import com.yuecheng.hops.transaction.config.repository.OracleSql;
import com.yuecheng.hops.transaction.config.repository.ProfitImputationInfoDao;


@Service
public class ProfitImputationInfoSqlDao implements ProfitImputationInfoDao
{
    @PersistenceContext
    private EntityManager em;

    public YcPage<ProfitImputationInfo> queryProfitImputationInfos(Map<String, Object> searchParams,
                                                                 int pageNumber, int pageSize)
    {
        int startIndex = pageNumber * pageSize - pageSize;
        int endIndex = startIndex + pageSize;

        String pageTotal_sql = "select count(*) from ("
                               + OracleSql.ProfitImputationInfo.QUERYPROFITIMPUTATIONINFOS_SQL
                               + ")";
        Query query = em.createNativeQuery(pageTotal_sql);
        query = OracleSql.setParameter(searchParams, query, 0, 0);
        BigDecimal total = (BigDecimal)query.getSingleResult();

        Double pageTotal = BigDecimalUtil.divide(total, new BigDecimal(pageSize),
            DecimalPlaces.ZERO.value(), BigDecimal.ROUND_CEILING).doubleValue();
        String sql = "select * from ("
                     + OracleSql.ProfitImputationInfo.QUERYPROFITIMPUTATIONINFOS_SQL
                     + ") where rn>:startIndex";
        query = em.createNativeQuery(sql, ProfitImputationInfo.class);
        query = OracleSql.setParameter(searchParams, query, startIndex, endIndex);
        List<ProfitImputationInfo> profitImputationInfosList = query.getResultList();
        YcPage<ProfitImputationInfo> ycPage = new YcPage<ProfitImputationInfo>();
        ycPage.setList(profitImputationInfosList);
        ycPage.setCountTotal(total.intValue());
        ycPage.setPageTotal(pageTotal.intValue());
        return ycPage;
    }
}
