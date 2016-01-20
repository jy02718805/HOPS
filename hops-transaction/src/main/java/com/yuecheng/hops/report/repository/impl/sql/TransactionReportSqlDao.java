package com.yuecheng.hops.report.repository.impl.sql;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.BigDecimalUtil;
import com.yuecheng.hops.common.utils.DateUtil;
import com.yuecheng.hops.report.entity.ReportProperty;
import com.yuecheng.hops.report.entity.bo.TransactionReportBo;
import com.yuecheng.hops.report.entity.po.TransactionReportPo;
import com.yuecheng.hops.report.repository.OracleSql;
import com.yuecheng.hops.report.repository.TransactionReportDao;
import com.yuecheng.hops.report.tool.ReportTool;


@Service
public class TransactionReportSqlDao implements TransactionReportDao
{
    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionReportSqlDao.class);

    @Override
    public List<TransactionReportBo> getTransactionReportBos(Date beginTime, Date endTime,
                                                             String merchantType)
    {
        try
        {
            String sql = "";
            if (MerchantType.AGENT.toString().equals(merchantType))
            {
                sql = OracleSql.TransactionReport.GETTRANSACTIONREPORTBOS_AGENT_SQL;
            }
            else
            {
                sql = OracleSql.TransactionReport.GETTRANSACTIONREPORTBOS_SUPPLY_SQL;
            }

            Query query = em.createQuery(sql, TransactionReportBo.class);
            query.setParameter("beginTime", DateUtil.formatDateTime(beginTime));
            query.setParameter("endTime", DateUtil.formatDateTime(endTime));
            @SuppressWarnings("unchecked")
            List<TransactionReportBo> tList = query.getResultList();
            LOGGER.debug("[结束取得当天订单中产品属性组合][TransactionReportSqlDao: getTransactionReportBos()]");
            return tList;
        }
        catch (Exception e)
        {
            LOGGER.error("[TransactionReportSqlDao :getTransactionReportBos(beginTime,endTime,merchantType:"
                         + merchantType + ") [报错:" + ExceptionUtil.getStackTraceAsString(e) + "]]");
            throw ExceptionUtil.throwException(e);
        }
    }

    public YcPage<TransactionReportPo> queryTransactionReports(Map<String, Object> searchParams,
                                                               List<ReportProperty> rpList,
                                                               String beginTime, String endTime,
                                                               int pageNumber, int pageSize,
                                                               String sortType)
    {
        int startIndex = pageNumber * pageSize - pageSize;
        int endIndex = startIndex + pageSize;

        String insidesql = "select  transaction.*,rownum rn from ( ";
        String sqlSelect = " select t.merchant_id as merchantid,t.Merchant_Name as merchantName";
        String sqlWhere = "";
        String sqlGroupBy = " group by t.merchant_id,t.Merchant_Name";
        String sqlOrderBy = "order by t.merchant_id, t.user_code ,t.merchant_order_no desc";
        boolean flag = false;
        for (ReportProperty reportProperty : rpList)
        {
            if (EntityConstant.TransactionReport.MERCHANT_TYPE_NAME.equalsIgnoreCase(reportProperty.getReportPropertyFieldName()))
            {
                sqlSelect += ",t.merchant_Type as merchantType,t.merchant_Type_Name as merchantTypeName";
                sqlGroupBy += ",t.merchant_Type,t.merchant_Type_Name";
            }

            if (EntityConstant.TransactionReport.PROVINCE_NAME.equalsIgnoreCase(reportProperty.getReportPropertyFieldName()))
            {
                sqlSelect += ",t.province as province,t.province_Name as provinceName";
                sqlGroupBy += ",t.province,t.province_Name";
            }

            if (EntityConstant.TransactionReport.CITY_NAME.equalsIgnoreCase(reportProperty.getReportPropertyFieldName()))
            {
                sqlSelect += ",t.city as city,t.city_Name as cityName";
                sqlGroupBy += ",t.city,t.city_Name";
            }

            if (EntityConstant.TransactionReport.CARRIER_NAME.equalsIgnoreCase(reportProperty.getReportPropertyFieldName()))
            {
                sqlSelect += ",t.carrier_No as carrierNo,t.CARRIER_NAME as CARRIERNAME";
                sqlGroupBy += ",t.carrier_No,t.CARRIER_NAME";
            }

            if (EntityConstant.TransactionReport.PAR_VALUE.equalsIgnoreCase(reportProperty.getReportPropertyFieldName()))
            {
                sqlSelect += ",t.Par_Value as parValue";
                sqlGroupBy += ",t.Par_Value";
            }

            if (EntityConstant.TransactionReport.REPORTS_STATUS_NAME.equalsIgnoreCase(reportProperty.getReportPropertyFieldName()))
            {
                sqlSelect += ",t.reports_status as reportsStatus,t.reports_status_name as reportsstatusname";
                sqlGroupBy += ",t.reports_status,t.reports_status_name";
            }
            if (EntityConstant.TransactionReport.BUSINESS_TYPE.equalsIgnoreCase(reportProperty.getReportPropertyFieldName()))
            {
                sqlSelect += ",t.business_type as businessType";
                sqlGroupBy += ",t.business_type";
            }
            
            if (EntityConstant.TransactionReport.MERCHANT_ORDER_NO.equalsIgnoreCase(reportProperty.getReportPropertyFieldName()))
            {
                sqlSelect += ",t.merchant_order_no as merchantOrderNo";
                sqlGroupBy += ",t.merchant_order_no";
            }
            
            if (EntityConstant.TransactionReport.USER_CODE.equalsIgnoreCase(reportProperty.getReportPropertyFieldName()))
            {
                sqlSelect += ",t.user_code as userCode";
                flag = true;
            }

        }

        if (ReportTool.isNotNull(searchParams.get(EntityConstant.TransactionReport.MERCHANT_NAME)))
        {
            sqlWhere += " and t.Merchant_Name='"
                        + searchParams.get(EntityConstant.TransactionReport.MERCHANT_NAME) + "' ";
        }

        if (ReportTool.isNotNull(searchParams.get(EntityConstant.TransactionReport.MERCHANT_TYPE)))
        {
            sqlWhere += " and t.merchant_Type='"
                        + searchParams.get(EntityConstant.TransactionReport.MERCHANT_TYPE) + "' ";

        }

        if (ReportTool.isNotNull(searchParams.get(EntityConstant.TransactionReport.PROVINCE)))
        {
            sqlWhere += " and t.province='"
                        + searchParams.get(EntityConstant.TransactionReport.PROVINCE) + "'";
        }

        if (ReportTool.isNotNull(searchParams.get(EntityConstant.TransactionReport.CITY)))
        {
            sqlWhere += " and t.city='" + searchParams.get(EntityConstant.TransactionReport.CITY)
                        + "'";
        }

        if (ReportTool.isNotNull(searchParams.get(EntityConstant.TransactionReport.CARRIER_NO)))
        {
            sqlWhere += " and t.carrier_No='"
                        + searchParams.get(EntityConstant.TransactionReport.CARRIER_NO) + "'";

        }

        if (ReportTool.isNotNull(searchParams.get(EntityConstant.TransactionReport.REPORTS_STATUS)))
        {
            sqlWhere += " and t.reports_status="
                        + searchParams.get(EntityConstant.TransactionReport.REPORTS_STATUS);
        }

        if (ReportTool.isNotNull(searchParams.get(EntityConstant.TransactionReport.PAR_VALUE)))
        {
            sqlWhere += " and t.Par_Value='"
                        + searchParams.get(EntityConstant.TransactionReport.PAR_VALUE) + "'";

        }
        if (ReportTool.isNotNull(searchParams.get(EntityConstant.TransactionReport.BUSINESS_TYPE)))
        {
            sqlWhere += " and t.BUSINESS_TYPE='"
                        + searchParams.get(EntityConstant.TransactionReport.BUSINESS_TYPE) + "'";

        }
        
        if (ReportTool.isNotNull(searchParams.get(EntityConstant.TransactionReport.MERCHANT_ORDER_NO)))
        {
            sqlWhere += " and t.merchant_order_no='"
                        + searchParams.get(EntityConstant.TransactionReport.MERCHANT_ORDER_NO) + "'";

        }

        if (ReportTool.isNotNull(searchParams.get(EntityConstant.SupplyTransactionReport.PRODUCT_ID)))
        {
            sqlWhere += " and t.product_name like '%"
                        + searchParams.get(EntityConstant.SupplyTransactionReport.PRODUCT_ID)
                        + "%'";
        }

        
        sqlWhere += " and t.begin_time >= to_date('" + beginTime
                    + "','yyyy-mm-dd HH24:mi:ss') and t.begin_time<=to_date('" + endTime
                    + "','yyyy-mm-dd HH24:mi:ss')";

        if (flag)
        {
        	sqlGroupBy = "";
        	sqlSelect += ",t.total_sales_fee as totalSalesFee,t.Total_Par_Value as totalParValue,t.transaction_num as transactionNum,"
                    + "'"+beginTime+"' as BEGINTIME,'"+endTime+"' as ENDTIME  from transaction_report t where 1=1 ";
        }
        else
        {
        	sqlOrderBy = "";
        	sqlSelect += ",sum(t.total_sales_fee)as totalSalesFee,sum(t.Total_Par_Value)as totalParValue,sum(t.transaction_num) as transactionNum,"
                    + "to_char(min(t.begin_time),'yyyy-mm-dd') as BEGINTIME,to_char(max(t.begin_time),'yyyy-mm-dd') as ENDTIME  from transaction_report t where 1=1 ";
        }
        insidesql += sqlSelect + sqlWhere + sqlGroupBy + sqlOrderBy + ") transaction where 1=1 ";
        if (endIndex > 0)
        {
            insidesql = insidesql + " and rownum <= " + endIndex;
        }

        String pageTotal_sql = "select count(*) from (" + sqlSelect + sqlWhere + sqlGroupBy
                               + ") transaction where 1=1 ";

        YcPage<TransactionReportPo> ycPage = new YcPage<TransactionReportPo>();
        try
        {
            Query query = em.createNativeQuery(pageTotal_sql);
            BigDecimal total = (BigDecimal)query.getSingleResult();
            Double pageTotal = BigDecimalUtil.divide(total, new BigDecimal(pageSize),
                DecimalPlaces.ZERO.value(), BigDecimal.ROUND_CEILING).doubleValue();

            String sql = "select * from (" + insidesql + ") where rn>" + startIndex + "";

            query = em.createNativeQuery(sql);
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

            List<TransactionReportPo> tplistList = new ArrayList<TransactionReportPo>();
            List<?> rows = query.getResultList();
            for (Object obj : rows)
            {
                @SuppressWarnings("unchecked")
                Map<String, Object> row = (Map<String, Object>)obj;
                TransactionReportPo transactionReportPo = new TransactionReportPo();
                BeanUtils.transMap2Bean(row, transactionReportPo);
                tplistList.add(transactionReportPo);
            }

            ycPage.setList(tplistList);
            ycPage.setCountTotal(total.intValue());
            ycPage.setPageTotal(pageTotal.intValue());
        }
        catch (Exception e)
        {
            LOGGER.error("[TransactionReportSqlDao :queryTransactionReports()][报错"
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
        return ycPage;
    }

}
