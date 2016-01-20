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
import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.BigDecimalUtil;
import com.yuecheng.hops.report.entity.ReportProperty;
import com.yuecheng.hops.report.entity.bo.ProfitReportBo;
import com.yuecheng.hops.report.entity.po.ProfitReportPo;
import com.yuecheng.hops.report.repository.ProfitReportDao;
import com.yuecheng.hops.report.tool.ReportTool;


@Service
public class ProfitReportSqlDao implements ProfitReportDao
{
    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfitReportSqlDao.class);

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public List<ProfitReportBo> getProfitReportBos(Date beginDate, Date endDate, String merchatType)
    {
        LOGGER.debug("[取得当天订单中产品属性组合][TransactionReportSqlDao: getProfitReportBos()]");
        String condition = "";
        if (MerchantType.AGENT.toString().equals(merchatType))
        {
            condition += "o.merchantId";
        }
        else
        {
            condition += "d.merchantId";
        }
        String sql = "select new com.yuecheng.hops.report.entity.bo.ProfitReportBo("
                     + condition
                     + ",o.ext1,o.ext2,o.ext3,o.displayValue,o.businessType,"
                     + "sum(o.orderSalesFee),sum(o.displayValue),sum(d.costFee),"
                     + "sum(o.orderSalesFee-d.costFee), sum(o.displayValue),count(*),o.orderStatus) from Order o,Delivery d where 1=1 "
                     + "and  (o.orderNo=d.orderNo or d.orderNo is null) and d.deliveryStatus='"
                     + Constant.Delivery.DELIVERY_STATUS_SUCCESS + "' and o.orderStatus='"
                     + Constant.OrderStatus.SUCCESS + "' and o.orderFinishTime>=to_date('"
                     + format.format(beginDate)
                     + "','yyyy-mm-dd hh24:mi:ss') and o.orderFinishTime<=to_date('"
                     + format.format(endDate) + "','yyyy-mm-dd hh24:mi:ss') group by " + condition
                     + ",o.ext1,o.ext2,o.ext3,o.productFace,o.orderStatus,o.businessType";

        List<ProfitReportBo> pList = new ArrayList<ProfitReportBo>();

        try
        {
            Query query = em.createQuery(sql, ProfitReportBo.class);// createNativeQuery(sql);
            pList = query.getResultList();
        }
        catch (Exception e)
        {
            LOGGER.error("[ProfitReportSqlDao :getProfitReportBos()][报错" + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
        LOGGER.debug("[结束取得当天订单中产品属性组合][TransactionReportSqlDao: getProfitReportBos()]");
        return pList;
    }

    public YcPage<ProfitReportPo> queryProfitReports(Map<String, Object> searchParams,
                                                     List<ReportProperty> rpList,
                                                     String beginTime, String endTime,
                                                     int pageNumber, int pageSize, String sortType)
    {

        int startIndex = pageNumber * pageSize - pageSize;
        int endIndex = startIndex + pageSize;

        String insidesql = "select  profit.*,rownum rn from ( ";
        String sqlSelect = " select p.Merchant_Id as merchantId,p.Merchant_Name as merchantName";
        String sqlWhere = "";
        String sqlGroupBy = " group by p.Merchant_Id,p.Merchant_Name";

        for (ReportProperty reportProperty : rpList)
        {
            if (EntityConstant.ProfitReports.MERCHANT_TYPE_NAME.equalsIgnoreCase(reportProperty.getReportPropertyFieldName()))
            {
                sqlSelect += ",p.merchant_Type as merchantType,p.merchant_Type_Name as merchantTypeName";
                sqlGroupBy += ",p.merchant_Type,p.merchant_Type_Name";
            }

            if (EntityConstant.ProfitReports.PROVINCE_NAME.equalsIgnoreCase(reportProperty.getReportPropertyFieldName()))
            {
                sqlSelect += ",p.province as province,p.province_Name as provinceName";
                sqlGroupBy += ",p.province,p.province_Name";
            }

            if (EntityConstant.ProfitReports.CITY_NAME.equalsIgnoreCase(reportProperty.getReportPropertyFieldName()))
            {
                sqlSelect += ",p.city as city,p.city_Name as cityName";
                sqlGroupBy += ",p.city,p.city_Name";
            }

            if (EntityConstant.ProfitReports.CARRIER_NAME.equalsIgnoreCase(reportProperty.getReportPropertyFieldName()))
            {
                sqlSelect += ",p.carrier_No as carrierNo,p.CARRIER_NAME as CARRIERNAME";
                sqlGroupBy += ",p.carrier_No,p.CARRIER_NAME";
            }

            if (EntityConstant.ProfitReports.PAR_VALUE.equalsIgnoreCase(reportProperty.getReportPropertyFieldName()))
            {
                sqlSelect += ",p.Par_Value as parValue";
                sqlGroupBy += ",p.Par_Value";
            }
            if (EntityConstant.TransactionReport.BUSINESS_TYPE.equalsIgnoreCase(reportProperty.getReportPropertyFieldName()))
            {
                sqlSelect += ",p.BUSINESS_TYPE as businessType";
                sqlGroupBy += ",p.BUSINESS_TYPE";
            }

        }

        if (ReportTool.isNotNull(searchParams.get(EntityConstant.TransactionReport.MERCHANT_ID)))
        {
            sqlWhere += " and p.merchant_id='"
                        + searchParams.get(EntityConstant.TransactionReport.MERCHANT_ID) + "'";
        }

        if (ReportTool.isNotNull(searchParams.get(EntityConstant.TransactionReport.MERCHANT_NAME)))
        {
            sqlWhere += " and p.Merchant_Name='"
                        + searchParams.get(EntityConstant.TransactionReport.MERCHANT_NAME) + "'";
        }

        if (ReportTool.isNotNull(searchParams.get(EntityConstant.TransactionReport.MERCHANT_TYPE)))
        {
            sqlWhere += " and p.merchant_Type='"
                        + searchParams.get(EntityConstant.TransactionReport.MERCHANT_TYPE) + "'";
        }

        if (ReportTool.isNotNull(searchParams.get(EntityConstant.TransactionReport.PROVINCE)))
        {
            sqlWhere += " and p.province='"
                        + searchParams.get(EntityConstant.TransactionReport.PROVINCE) + "'";
        }

        if (ReportTool.isNotNull(searchParams.get(EntityConstant.TransactionReport.CITY)))
        {
            sqlWhere += " and p.city='" + searchParams.get(EntityConstant.TransactionReport.CITY)
                        + "'";
        }

        if (ReportTool.isNotNull(searchParams.get(EntityConstant.TransactionReport.CARRIER_NO)))
        {
            sqlWhere += " and p.carrier_No='"
                        + searchParams.get(EntityConstant.TransactionReport.CARRIER_NO) + "'";
        }

        if (ReportTool.isNotNull(searchParams.get(EntityConstant.TransactionReport.PAR_VALUE)))
        {
            sqlWhere += " and p.Par_Value='"
                        + searchParams.get(EntityConstant.TransactionReport.PAR_VALUE) + "'";
        }
        if (ReportTool.isNotNull(searchParams.get(EntityConstant.TransactionReport.BUSINESS_TYPE)))
        {
            sqlWhere += " and p.BUSINESS_TYPE='"
                        + searchParams.get("businessType") + "'";
        }
        
        sqlSelect += ",sum(p.Cost_Fee) as costFee,sum(p.Profit) as Profit,sum(p.Success_Face) as successFace,sum(p.order_sales_fee)as ordersalesfee,sum(p.total_Par_Value)as totalParValue,sum(p.Profit_Num) as profitNum,"
                     + "to_char(min(p.begin_time),'yyyy-mm-dd')  as BEGINTIME,to_char(max(p.begin_time),'yyyy-mm-dd')  as ENDTIME from profit_Report p where 1=1 ";

        sqlWhere += " and p.begin_time >= to_date('" + beginTime
                    + "','yyyy-mm-dd HH24:mi:ss') and p.begin_time<=to_date('" + endTime
                    + "','yyyy-mm-dd HH24:mi:ss')";

        insidesql += sqlSelect + sqlWhere + sqlGroupBy + ") profit where 1=1 ";
        if (endIndex > 0)
        {
            insidesql = insidesql + " and rownum <= " + endIndex;
        }

        String pageTotal_sql = "select count(*) from (" + sqlSelect + sqlWhere + sqlGroupBy
                               + ") profit where 1=1 ";
        YcPage<ProfitReportPo> ycPage = new YcPage<ProfitReportPo>();
        try
        {
            Query query = em.createNativeQuery(pageTotal_sql);
            BigDecimal total = (BigDecimal)query.getSingleResult();

            Double pageTotal = BigDecimalUtil.divide(total, new BigDecimal(pageSize),
                DecimalPlaces.ZERO.value(), BigDecimal.ROUND_CEILING).doubleValue();

            String sql = "select * from (" + insidesql + ") where rn>" + startIndex + "";

            query = em.createNativeQuery(sql);
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

            List<ProfitReportPo> prvList = new ArrayList<ProfitReportPo>();
            List rows = query.getResultList();
            for (Object obj : rows)
            {
                @SuppressWarnings("unchecked")
                Map<String, Object> row = (Map<String, Object>)obj;

                ProfitReportPo profitReportVo = new ProfitReportPo();
                BeanUtils.transMap2Bean(row, profitReportVo);
                prvList.add(profitReportVo);
            }

            ycPage.setList(prvList);
            ycPage.setCountTotal(total.intValue());
            ycPage.setPageTotal(pageTotal.intValue());

        }
        catch (Exception e)
        {
            LOGGER.error("[ProfitReportSqlDao :queryProfitReports()][报错" + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
        return ycPage;
    }
}
