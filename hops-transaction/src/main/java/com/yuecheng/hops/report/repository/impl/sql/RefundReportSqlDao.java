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
import com.yuecheng.hops.report.entity.bo.RefundReportBo;
import com.yuecheng.hops.report.entity.po.RefundReportPo;
import com.yuecheng.hops.report.repository.RefundReportDao;
import com.yuecheng.hops.report.tool.ReportTool;


@Service
public class RefundReportSqlDao implements RefundReportDao
{
    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(RefundReportSqlDao.class);

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public List<RefundReportBo> getRefundReportBos(Date beginDate, Date endDate, String merchatType)
    {
        LOGGER.debug("[取得当天订单中产品属性组合][TransactionReportSqlDao: getRefundReportBos()]");
        String condition = "";
        if (MerchantType.AGENT.toString().equals(merchatType))
        {
            condition += "o.merchantId";
        }
        else
        {
            condition += "d.merchantId";
        }
        String sql = "select new com.yuecheng.hops.report.entity.bo.RefundReportBo("
                     + condition
                     + ",o.ext1,o.ext2,o.ext3,o.productFace,o.orderSalesFee,o.productFace,d.costFee,"
                     + "o.orderSalesFee-d.costFee, o.productFace,o.orderFinishTime,o.orderFinishTime,o.orderNo,h.createDate,o.businessType) from Order o,Delivery d,OrderApplyOperateHistory h where 1=1 "
                     + "and o.orderNo=d.orderNo and o.orderNo=h.orderNo and d.deliveryStatus='"
                     + Constant.Delivery.DELIVERY_STATUS_FAIL
                     + "' and o.orderStatus='"
                     + Constant.OrderStatus.FAILURE_ALL
                     + "' "
                     + " and h.action='4'  " // 人工审核动作标识为订单成功后退款
                     + " and d.deliveryStartTime=(select max(d.deliveryStartTime) from Delivery d where d.orderNo=o.orderNo)"
                     + " and h.createDate>=to_date('" + format.format(beginDate)
                     + "','yyyy-mm-dd hh24:mi:ss') and h.createDate<=to_date('"
                     + format.format(endDate) + "','yyyy-mm-dd hh24:mi:ss') ";

        List<RefundReportBo> pList = new ArrayList<RefundReportBo>();

        try
        {
            Query query = em.createQuery(sql, RefundReportBo.class);// createNativeQuery(sql);
            pList = query.getResultList();
        }
        catch (Exception e)
        {
            LOGGER.error("[RefundReportSqlDao :getRefundReportBos()][报错"
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
        LOGGER.debug("[结束取得当天订单中产品属性组合][TransactionReportSqlDao: getRefundReportBos()]");
        return pList;
    }

    public YcPage<RefundReportPo> queryRefundReports(Map<String, Object> searchParams,
                                                     List<ReportProperty> rpList,
                                                     String beginTime, String endTime,
                                                     int pageNumber, int pageSize, String sortType)
    {

        int startIndex = pageNumber * pageSize - pageSize;
        int endIndex = startIndex + pageSize;

        String insidesql = "select  refund.*,rownum rn from ( ";
        String sqlSelect = " select p.Merchant_Id as merchantId,p.Merchant_Name as merchantName";
        String sqlWhere = "";
        String sqlGroupBy = " group by p.Merchant_Id,p.Merchant_Name";

        for (ReportProperty reportProperty : rpList)
        {

            if (EntityConstant.RefundReport.REFUND_ORDER_NO.equalsIgnoreCase(reportProperty.getReportPropertyFieldName()))
            {
                sqlSelect += ",p.refund_order_no as refundOrderNO";
                sqlGroupBy += ",p.refund_order_no";
            }

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

            if (EntityConstant.RefundReport.MANUAL_AUDIT_DATE.equalsIgnoreCase(reportProperty.getReportPropertyFieldName()))
            {
                sqlSelect += ", to_char(p.manual_audit_date,'yyyy-mm-dd') as MANUALAUDITDATE";
                sqlGroupBy += ",p.manual_audit_date";
            }

            if (EntityConstant.TransactionReport.BUSINESS_TYPE.equalsIgnoreCase(reportProperty.getReportPropertyFieldName()))
            {
                sqlSelect += ",p.business_type as businessType";
                sqlGroupBy += ",p.business_type";
            }

            // if
            // (EntityConstant.ProfitReports.PAR_VALUE.equalsIgnoreCase(reportProperty.getReportPropertyFieldName()))
            // {
            // sqlSelect += ",p.refund_order_no as refundOrderNo ";
            // sqlGroupBy += ",p.refund_order_no";
            // }

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

        if (ReportTool.isNotNull(searchParams.get(EntityConstant.RefundReport.REFUND_ORDER_NO)))
        {
            sqlWhere += " and p.refund_order_no='"
                        + searchParams.get(EntityConstant.RefundReport.REFUND_ORDER_NO) + "'";
        }

        if (ReportTool.isNotNull(searchParams.get(EntityConstant.TransactionReport.BUSINESS_TYPE)))
        {
            sqlWhere += " and p.business_type='"
                        + searchParams.get(EntityConstant.TransactionReport.BUSINESS_TYPE) + "'";
        }
        // if (ReportTool.isNotNull(searchParams.get(EntityConstant.TransactionReport.PAR_VALUE)))
        // {
        // sqlWhere += " and p.refund_order_no='"
        // + searchParams.get(EntityConstant.TransactionReport.PAR_VALUE) + "'";
        // }

        sqlSelect += ",sum(p.Cost_Fee) as costFee,sum(p.refund_profit) as refundProfit,sum(p.refund_face) as refundFace,sum(p.order_sales_fee)as ordersalesfee,sum(p.total_Par_Value)as totalParValue,sum(p.refund_Num) as refundNum,"
                     + "to_char(min(p.begin_time),'yyyy-mm-dd')  as BEGINTIME from refund_Report p where 1=1 ";

        sqlWhere += " and p.begin_time >= to_date('" + beginTime
                    + "','yyyy-mm-dd HH24:mi:ss') and p.begin_time<=to_date('" + endTime
                    + "','yyyy-mm-dd HH24:mi:ss')";

        insidesql += sqlSelect + sqlWhere + sqlGroupBy + ") refund where 1=1 ";
        if (endIndex > 0)
        {
            insidesql = insidesql + " and rownum <= " + endIndex;
        }

        String pageTotal_sql = "select count(*) from (" + sqlSelect + sqlWhere + sqlGroupBy
                               + ") refund where 1=1 ";
        YcPage<RefundReportPo> ycPage = new YcPage<RefundReportPo>();
        try
        {
            Query query = em.createNativeQuery(pageTotal_sql);
            BigDecimal total = (BigDecimal)query.getSingleResult();

            Double pageTotal = BigDecimalUtil.divide(total, new BigDecimal(pageSize),
                DecimalPlaces.ZERO.value(), BigDecimal.ROUND_CEILING).doubleValue();

            String sql = "select * from (" + insidesql + ") where rn>" + startIndex + "";

            query = em.createNativeQuery(sql);
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

            List<RefundReportPo> prvList = new ArrayList<RefundReportPo>();
            List rows = query.getResultList();
            for (Object obj : rows)
            {
                @SuppressWarnings("unchecked")
                Map<String, Object> row = (Map<String, Object>)obj;

                RefundReportPo refundReportPo = new RefundReportPo();
                BeanUtils.transMap2Bean(row, refundReportPo);
                prvList.add(refundReportPo);
            }

            ycPage.setList(prvList);
            ycPage.setCountTotal(total.intValue());
            ycPage.setPageTotal(pageTotal.intValue());

        }
        catch (Exception e)
        {
            LOGGER.error("[RefundReportSqlDao :queryRefundReports()][报错"
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
        return ycPage;
    }

}
