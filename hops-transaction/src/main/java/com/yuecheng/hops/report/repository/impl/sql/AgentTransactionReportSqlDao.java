package com.yuecheng.hops.report.repository.impl.sql;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.DecimalPlaces;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.BigDecimalUtil;
import com.yuecheng.hops.common.utils.DateUtil;
import com.yuecheng.hops.report.entity.ReportProperty;
import com.yuecheng.hops.report.entity.bo.MerchantTransactionReportBo;
import com.yuecheng.hops.report.entity.po.AgentTransactionReportPo;
import com.yuecheng.hops.report.repository.AgentTransactionReportDao;
import com.yuecheng.hops.report.repository.OracleSql;
import com.yuecheng.hops.report.tool.ReportTool;


@Service
public class AgentTransactionReportSqlDao implements AgentTransactionReportDao
{
    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(AgentTransactionReportSqlDao.class);

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<MerchantTransactionReportBo> getMerchantTransactionInfos(Date beginTime,
                                                                         Date endTime)
    {
        LOGGER.debug("[开始取得当天订单中产品属性组合][TransactionReportSqlDao: getMerchantTransactionInfos(beginTime"
                     + format.format(beginTime) + ",endTime" + format.format(endTime) + ")]");
        try
        {
            Query query = em.createQuery(
                OracleSql.AgentTransactionReport.GETMERCHANTTRANSACTIONINFO_SQL,
                MerchantTransactionReportBo.class);
            query.setParameter("beginTime", DateUtil.formatDateTime(beginTime));
            query.setParameter("endTime", DateUtil.formatDateTime(endTime));
            @SuppressWarnings("unchecked")
            List<MerchantTransactionReportBo> tList = query.getResultList();
            LOGGER.debug("[结束取得当天订单中产品属性组合][TransactionReportSqlDao: getMerchantTransactionInfos()]");
            return tList;
        }
        catch (Exception e)
        {
            LOGGER.error("[AgentTransactionReportSqlDao :getMerchantTransactionInfos(beginTime,endTime) [报错:"
                         + ExceptionUtil.getStackTraceAsString(e) + "]]");
            throw ExceptionUtil.throwException(e);
        }
    }

    public YcPage<AgentTransactionReportPo> queryAgentTransactionReports(Map<String, Object> searchParams,
                                                                         List<ReportProperty> rpList,
                                                                         String beginTime,
                                                                         String endTime,
                                                                         int pageNumber,
                                                                         int pageSize,
                                                                         String sortType)
    {

        int startIndex = pageNumber * pageSize - pageSize;
        int endIndex = startIndex + pageSize;

        String insidesql = "select  transaction.*,rownum rn from ( ";
        String sqlSelect = " select t.Merchant_Id as merchantId,t.Merchant_Name as merchantName";
        String sqlWhere = "";
        String sqlGroupBy = " group by t.Merchant_Id,t.Merchant_Name";

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

            if (EntityConstant.TransactionReport.PROVINCE_NAME.equalsIgnoreCase(reportProperty.getReportPropertyFieldName()))
            {
                sqlSelect += ",t.product_id as productid,t.product_name as productname";
                sqlGroupBy += ",t.product_id,t.product_name";
            }
            
            if (EntityConstant.TransactionReport.BUSINESS_TYPE.equalsIgnoreCase(reportProperty.getReportPropertyFieldName()))
            {
                sqlSelect += ",t.BUSINESS_TYPE as businessType";
                sqlGroupBy += ",t.BUSINESS_TYPE";
            }
        }

        if (ReportTool.isNotNull(searchParams.get(EntityConstant.SupplyTransactionReport.MERCHANT_ID)))
        {
            sqlWhere += " and t.merchant_id='"
                        + searchParams.get(EntityConstant.SupplyTransactionReport.MERCHANT_ID)
                        + "'";
        }

        if (ReportTool.isNotNull(searchParams.get(EntityConstant.SupplyTransactionReport.MERCHANT_NAME)))
        {
            sqlWhere += " and t.Merchant_Name='"
                        + searchParams.get(EntityConstant.SupplyTransactionReport.MERCHANT_NAME)
                        + "'";
        }

        if (ReportTool.isNotNull(searchParams.get(EntityConstant.SupplyTransactionReport.MERCHANT_TYPE)))
        {
            sqlWhere += " and t.merchant_Type='"
                        + searchParams.get(EntityConstant.SupplyTransactionReport.MERCHANT_TYPE)
                        + "'";
        }

        if (ReportTool.isNotNull(searchParams.get(EntityConstant.SupplyTransactionReport.PROVINCE)))
        {
            sqlWhere += " and t.province='"
                        + searchParams.get(EntityConstant.SupplyTransactionReport.PROVINCE) + "'";
        }

        if (ReportTool.isNotNull(searchParams.get(EntityConstant.SupplyTransactionReport.CITY)))
        {
            sqlWhere += " and t.city='"
                        + searchParams.get(EntityConstant.SupplyTransactionReport.CITY) + "'";
        }

        if (ReportTool.isNotNull(searchParams.get(EntityConstant.SupplyTransactionReport.CARRIER_NO)))
        {
            sqlWhere += " and t.carrier_No='"
                        + searchParams.get(EntityConstant.SupplyTransactionReport.CARRIER_NO)
                        + "'";
        }

        if (ReportTool.isNotNull(searchParams.get(EntityConstant.SupplyTransactionReport.REPORTS_STATUS)))
        {
            sqlWhere += " and t.reports_status='"
                        + searchParams.get(EntityConstant.SupplyTransactionReport.REPORTS_STATUS)
                        + "'";
            sqlGroupBy += ",t.reports_status,t.reports_status_name";
        }

        if (ReportTool.isNotNull(searchParams.get(EntityConstant.SupplyTransactionReport.PAR_VALUE)))
        {
            sqlWhere += " and t.Par_Value='"
                        + searchParams.get(EntityConstant.SupplyTransactionReport.PAR_VALUE) + "'";
        }
        
        if (ReportTool.isNotNull(searchParams.get(EntityConstant.TransactionReport.BUSINESS_TYPE)))
        {
            sqlWhere += " and t.BUSINESS_TYPE='"
                        + searchParams.get("businessType") + "'";
        }

        if (ReportTool.isNotNull(searchParams.get(EntityConstant.SupplyTransactionReport.PRODUCT_ID)))
        {
            sqlWhere += " and t.product_name like '%"
                        + searchParams.get(EntityConstant.SupplyTransactionReport.PRODUCT_ID)
                        + "%'";
        }
        
        

        sqlSelect += ",sum(t.total_sales_fee)as totalSalesFee,sum(t.total_par_value)as totalParValue,sum(t.agent_transaction_num) as transactionNum,"
                     + "to_char(min(t.begin_time),'yyyy-mm-dd')  as BEGINTIME,to_char(max(t.begin_time),'yyyy-mm-dd') as ENDTIME from agent_transaction_report t where 1=1 ";

        sqlWhere += " and t.begin_time >= to_date('" + beginTime
                    + "','yyyy-mm-dd HH24:mi:ss') and t.begin_time<=to_date('" + endTime
                    + "','yyyy-mm-dd HH24:mi:ss') ";

        insidesql += sqlSelect + sqlWhere + sqlGroupBy + ") transaction where 1=1 ";
        if (endIndex > 0)
        {
            insidesql = insidesql + " and rownum <= " + endIndex;
        }

        String pageTotal_sql = "select count(*) from (" + sqlSelect + sqlWhere + sqlGroupBy
                               + ") transaction where 1=1 ";

        YcPage<AgentTransactionReportPo> ycPage = new YcPage<AgentTransactionReportPo>();
        try
        {
            Query query = em.createNativeQuery(pageTotal_sql);
            BigDecimal total = (BigDecimal)query.getSingleResult();
            Double pageTotal = BigDecimalUtil.divide(total, new BigDecimal(pageSize),
                DecimalPlaces.ZERO.value(), BigDecimal.ROUND_CEILING).doubleValue();

            String sql = "select * from (" + insidesql + ") where rn>" + startIndex + "";
            query = em.createNativeQuery(sql);
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

            List<AgentTransactionReportPo> atplist = new ArrayList<AgentTransactionReportPo>();
            List<?> rows = query.getResultList();
            for (Object obj : rows)
            {
                @SuppressWarnings("unchecked")
                Map<String, Object> row = (Map<String, Object>)obj;

                AgentTransactionReportPo transactionReportVo = new AgentTransactionReportPo();
                BeanUtils.transMap2Bean(row, transactionReportVo);
                atplist.add(transactionReportVo);
            }

            ycPage.setList(atplist);
            ycPage.setCountTotal(total.intValue());
            ycPage.setPageTotal(pageTotal.intValue());
        }
        catch (Exception e)
        {
            LOGGER.error("[AgentTransactionReportSqlDao :queryAgentTransactionReports()][报错"
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
        return ycPage;
    }

    public BigDecimal getSumAgentTransaction(Long merchantId, Long productId, Date beginDate,
                                             Date endDate)
    {
        SimpleDateFormat fomat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String beginTime = fomat.format(beginDate);
        String endTime = fomat.format(endDate);
        String sql = "select sum(a.total_par_value) from agent_transaction_report a where a.merchant_id ='"
                     + merchantId
                     + "' and a.product_id='"
                     + productId
                     + "' and a.reports_status='"
                     + Constant.OrderStatus.SUCCESS
                     + "' and a.begin_time>= to_date('"
                     + beginTime
                     + "','yyyy-MM-dd hh24:mi:ss') and a.end_time<=to_date('"
                     + endTime
                     + "','yyyy-MM-dd hh24:mi:ss')";

        BigDecimal sumTransaction = new BigDecimal(0);
        try
        {
            Query query = em.createNativeQuery(sql);
            sumTransaction = (BigDecimal)query.getSingleResult();

        }
        catch (Exception e)
        {
            LOGGER.error("[AgentTransactionReportSqlDao :getSumAgentTransaction()][报错"
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
        return sumTransaction;
    }
}
