package com.yuecheng.hops.report.repository.impl.sql;


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

import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.DecimalPlaces;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.BigDecimalUtil;
import com.yuecheng.hops.report.entity.ReportProperty;
import com.yuecheng.hops.report.entity.po.AccountReportPo;
import com.yuecheng.hops.report.repository.AccountReportDao;
import com.yuecheng.hops.report.tool.ReportTool;


@Service
public class AccountReportSqlDao implements AccountReportDao
{
    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountReportSqlDao.class);

    public YcPage<AccountReportPo> queryAccountPageReports(Map<String, Object> searchParams,
                                                           List<ReportProperty> rpList,
                                                           String beginTime, String endTime,
                                                           int pageNumber, int pageSize,
                                                           String sortType)
    {
        int startIndex = pageNumber * pageSize - pageSize;
        int endIndex = startIndex + pageSize;
        String insidesql = "(select acc.*,rownum rn from (select  a.*,nvl(b.period_balance,0) as periodBalance,nvl(c.previous_balance,0) as previousBalance,nvl(b.period_unavailable_balance,0) as periodUnavailableBalance,nvl(c.previous_unavailable_balance,0) as previousUnavailableBalance from ";
        String sqlSelect = "";
        String sqlWhere = "";
        String sqlGroupBy = " group by  ar1.account_id ";
        sqlSelect += " (select ar1.account_id as accountId";
        for (ReportProperty reportProperty : rpList)
        {
            if (EntityConstant.AccountReport.ACCOUNT_TYPE_NAME.equalsIgnoreCase(reportProperty.getReportPropertyFieldName()))
            {
                sqlSelect += " ,ar1.account_Type_Id as ACCOUNTTYPEID,ar1.account_Type_Name as accountTypeName";
                sqlGroupBy += ",ar1.account_Type_Id,ar1.account_Type_Name";
            }

            if (EntityConstant.AccountReport.IDENTITY_NAME.equalsIgnoreCase(reportProperty.getReportPropertyFieldName()))
            {
                sqlSelect += " ,ar1.identity_name as identityName";
                sqlGroupBy += ",ar1.identity_name";
            }

            if (EntityConstant.AccountReport.IDENTITY_TYPE_NAME.equalsIgnoreCase(reportProperty.getReportPropertyFieldName()))
            {
                sqlSelect += ",ar1.identity_type as identityType,ar1.identity_type_name as identityTypeName";
                sqlGroupBy += ",ar1.identity_type,ar1.identity_type_name";
            }
        }

        if (ReportTool.isNotNull(searchParams.get(EntityConstant.AccountReport.ACCOUNT_ID)))
        {
            sqlWhere += " and ar1.account_id='"
                        + searchParams.get(EntityConstant.AccountReport.ACCOUNT_ID) + "'";
        }

        if (ReportTool.isNotNull(searchParams.get(EntityConstant.AccountReport.ACCOUNT_TYPE_ID)))
        {
            sqlWhere += " and ar1.account_Type_Id='"
                        + searchParams.get(EntityConstant.AccountReport.ACCOUNT_TYPE_ID) + "'";

        }

        if (ReportTool.isNotNull(searchParams.get(EntityConstant.AccountReport.IDENTITY_NAME)))
        {
            sqlWhere += " and ar1.identity_name='"
                        + searchParams.get(EntityConstant.AccountReport.IDENTITY_NAME) + "'";
        }

        if (ReportTool.isNotNull(searchParams.get(EntityConstant.AccountReport.IDENTITY_TYPE)))
        {
            sqlWhere += " and ar1.identity_type='"
                        + searchParams.get(EntityConstant.AccountReport.IDENTITY_TYPE) + "'";
        }

        sqlSelect += ",nvl(sum(ar1.PERIOD_PLUS_SECTION),0) as PERIODPLUSSECTION,nvl(sum(ar1.CURRENT_EXPENDITURE),0) as CURRENTEXPENDITURE, nvl(sum(ar1.PERIOD_ADD_AMT),0) as PERIODADDAMT, "
                     + "to_char(min(ar1.begin_time),'yyyy-mm-dd') as BEGINTIME,to_char(max(ar1.begin_time),'yyyy-mm-dd') as ENDTIME from account_report  ar1  where 1=1 ";

        sqlWhere += " and ar1.begin_time >= to_date('" + beginTime
                    + "','yyyy-mm-dd HH24:mi:ss') and ar1.begin_time<=to_date('" + endTime
                    + "','yyyy-mm-dd HH24:mi:ss') ";

        sqlSelect += sqlWhere + sqlGroupBy + ") a left join ";
        sqlSelect += "(select distinct ar2.account_id as accountId,ar2.period_balance,ar2.period_unavailable_balance from account_report ar2 where ar2.begin_time=(select max(ard.begin_time) from account_report ard where ard.begin_time>= to_date('"
                     + beginTime
                     + "','yyyy-mm-dd HH24:mi:ss') and ard.begin_time<=to_date('"
                     + endTime
                     + "','yyyy-mm-dd HH24:mi:ss') and ard.account_id=ar2.account_id)) b on b.accountId=a.accountId left join ";
        sqlSelect += "(select distinct ar3.account_id as accountId,ar3.previous_balance,ar3.previous_unavailable_balance from account_report ar3 where ar3.begin_time=(select min(ard2.begin_time) from account_report ard2 where ard2.begin_time>= to_date('"
                     + beginTime
                     + "','yyyy-mm-dd HH24:mi:ss') and ard2.begin_time<=to_date('"
                     + endTime
                     + "','yyyy-mm-dd HH24:mi:ss') and ard2.account_id=ar3.account_id)) c  on c.accountId=a.accountId order by a.accountid)acc ";

        insidesql += sqlSelect;
        if (endIndex > 0)
        {
            insidesql = insidesql + " where rownum <= " + endIndex;
        }

        String pageTotal_sql = "select count(*) from (select acc.*,rownum rn from (select * from "
                               + sqlSelect + ")";
        YcPage<AccountReportPo> ycPage = new YcPage<AccountReportPo>();
        try
        {
            Query query = em.createNativeQuery(pageTotal_sql);
            BigDecimal total = (BigDecimal)query.getSingleResult();
            if (total == null)
            {
                total = new BigDecimal(0);
            }
            Double pageTotal = BigDecimalUtil.divide(total, new BigDecimal(pageSize),
                DecimalPlaces.ZERO.value(), BigDecimal.ROUND_CEILING).doubleValue();

            String sql = "select * from (" + insidesql + ")) where rn>" + startIndex + "";

            query = em.createNativeQuery(sql);
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

            List<AccountReportPo> atvlist = new ArrayList<AccountReportPo>();
            List<?> rows = query.getResultList();
            for (Object obj : rows)
            {
                Map<String, Object> row = (Map<String, Object>)obj;
                AccountReportPo accountReportVo = new AccountReportPo();
                BeanUtils.transMap2Bean(row, accountReportVo);
                atvlist.add(accountReportVo);
            }

            ycPage.setList(atvlist);
            ycPage.setCountTotal(total.intValue());
            ycPage.setPageTotal(pageTotal.intValue());
        }
        catch (Exception e)
        {
            LOGGER.error("[AccountReportSqlDao :queryAccountPageReports()][报错"
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
        return ycPage;
    }
}
