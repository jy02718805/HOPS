/*
 * + * 文件名：OrderSqlDao.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年11月24日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.transaction.basic.repository.impl.sql;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.DecimalPlaces;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.BigDecimalUtil;
import com.yuecheng.hops.common.utils.DateUtil;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.parameter.entity.ParameterConfiguration;
import com.yuecheng.hops.parameter.service.ParameterConfigurationService;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderExportVo;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderNativeVo;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderParameterVo;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderStatisticsVo;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderVo;
import com.yuecheng.hops.transaction.basic.repository.DeliveryJpaDao;
import com.yuecheng.hops.transaction.basic.repository.OracleSql;
import com.yuecheng.hops.transaction.basic.repository.OrderDao;


@Service
public class OrderSqlDao implements OrderDao
{
    @PersistenceContext
    private EntityManager em;

    @Autowired
    MerchantService merchantService;

    @Autowired
    private DeliveryJpaDao deliveryJpaDao;

    @Autowired
    ParameterConfigurationService parameterConfigurationService;

    @Value("#{configProperties['order.business']}")
    private String business;

    @Value("#{configProperties['order.identity']}")
    private String identity;

    private static Logger logger = LoggerFactory.getLogger(OrderSqlDao.class);

    @SuppressWarnings("unchecked")
    @Override
    public YcPage<OrderNativeVo> queryOrders(int pageNumber, int pageSize,
                                             OrderParameterVo orderParameter)
    {
        try
        {
            int startIndex = pageNumber * pageSize - pageSize;
            int endIndex = startIndex + pageSize;

            String insidesql = "select od.*,rownum rn from (select * from  yc_order o where 1=1";

            String condition = StringUtil.initString();
            if (BeanUtils.isNotNull(orderParameter.getOrderStatus()))
            {
                condition += "   and o.order_status =" + orderParameter.getOrderStatus();

            }

            if (BeanUtils.isNotNull(orderParameter.getNotifyStatus()))
            {
                condition += "   and o.notify_Status =" + orderParameter.getNotifyStatus();

            }

            if (StringUtil.isNotBlank(orderParameter.getMerchantOrderNo()))
            {
                condition += "   and o.merchant_Order_No ='" + orderParameter.getMerchantOrderNo()
                             + "' ";

            }

            if (StringUtil.isNotBlank(orderParameter.getOrderNo()))
            {
                condition += "   and o.order_No =" + orderParameter.getOrderNo();

            }

            if (StringUtil.isNotBlank(orderParameter.getUsercode()))
            {
                condition += "   and o.user_code ='" + orderParameter.getUsercode() + "'";

            }

            if (StringUtil.isNotBlank(orderParameter.getPreSuccessStatus()))
            {

                if (orderParameter.getPreSuccessStatus().equals(
                    Constant.OrderStatus.PRE_SUCCESS_STATUS_WAIT + ""))
                {
                    condition = condition + " and o.pre_success_status != '"
                                + Constant.OrderStatus.PRE_SUCCESS_STATUS_DONE + "'";
                }
                else
                {
                    condition += "   and o.pre_success_status ="
                                 + orderParameter.getPreSuccessStatus();
                }

            }

            if (StringUtil.isNotBlank(orderParameter.getMerchantId()))
            {
                condition += "   and o.merchant_Id =" + orderParameter.getMerchantId();

            }

            if (StringUtil.isNotBlank(orderParameter.getParValue()))
            {
                condition += "   and o.product_Face ='" + orderParameter.getParValue() + "' ";

            }

            if (StringUtil.isNotBlank(orderParameter.getCarrierInfo()))
            {
                condition += "   and o.ext1 ='" + orderParameter.getCarrierInfo() + "' ";

            }

            if (StringUtil.isNotBlank(orderParameter.getProvince()))
            {
                condition += "   and o.ext2 ='" + orderParameter.getProvince() + "' ";

            }

            if (StringUtil.isNotBlank(orderParameter.getBusinessType()))
            {
                condition += "   and o.business_Type ='" + orderParameter.getBusinessType() + "' ";
            }

            if (StringUtil.isNotBlank(orderParameter.getSupplyMerchant()))
            {
                condition += "   and d.merchant_id =" + orderParameter.getSupplyMerchant();
            }

            if (StringUtil.isNotBlank(orderParameter.getManualFlag()))
            {
                condition += "   and o.manual_Flag =" + orderParameter.getManualFlag();
            }

            condition += "     and o.order_request_time >=                                        "
                         + "         to_date('"
                         + orderParameter.getBeginDate()
                         + "', 'yyyy-mm-dd hh24:mi:ss')      "
                         + "     and o.order_request_time <=                                        "
                         + "         to_date('"
                         + orderParameter.getEndDate()
                         + "', 'yyyy-mm-dd hh24:mi:ss')      ";

            String pageTotal_sql = "";
            String sql = "";
            if (StringUtil.isNotBlank(orderParameter.getSupplyMerchant()))
            {
                pageTotal_sql = "select count(*)  from yc_order o left join ( select yd.order_no,yd.merchant_id,ROW_NUMBER() OVER(PARTITION BY order_no ORDER BY yd.delivery_start_time DESC) overrn   from yc_delivery yd) d on o.order_no=d.order_no  where 1=1 "
                                + "   and (overrn =1 or overrn is null)                                  "
                                + condition;

                sql = " select *                                                                                                      "
                      + " from (select od.*, rownum rn                                                                                "
                      + " from (select  to_char(o.order_request_time, 'YYYY/mm/dd HH24:mi:ss') as orderRequestTime,o.order_no as orderNo,o.merchant_name agentMerchant,m.merchant_name supplyMerchant,  "
                      + " o.business_type as businessType,o.ext1,o.ext2,o.ext3,o.ext4,o.user_code as userCode,o.product_face as productFace,o.display_value as displayValue,o.order_success_fee as orderSuccessFee, "
                      + " o.order_status as orderStatus,o.notify_status as notifyStatus,o.manual_flag as manualFlag,to_char(o.order_finish_time, 'YYYY/mm/dd HH24:mi:ss') as orderFinishTime,(o.order_fee-o.order_success_fee) as orderWaitFee ,o.pre_success_status as preSuccessStatus  "
                      + ",o.order_Reason as orderReason from yc_order o left join (select yd.order_no,yd.merchant_id,ROW_NUMBER() OVER(PARTITION BY order_no ORDER BY yd.delivery_start_time DESC) overrn   from yc_delivery yd)  d on o.order_no=d.order_no left join  "
                      + identity
                      + ".merchant m  on d.merchant_id=m.identity_id where 1 = 1 "
                      + "  and (overrn =1 or overrn is null)                            ";

            }
            else
            {
                pageTotal_sql = "select count(*) from yc_order o where 1=1 " + condition;
                sql = " select *                                                                                                      "
                      + " from (select od.*, rownum rn                                                                                "
                      + " from (select  to_char(o.order_request_time, 'YYYY/mm/dd HH24:mi:ss') as orderRequestTime,o.order_no as orderNo,o.merchant_name agentMerchant,  "
                      + " o.business_type as businessType,o.ext1,o.ext2,o.ext3,o.ext4,o.user_code as userCode,o.product_face as productFace,o.display_value as displayValue,o.order_success_fee as orderSuccessFee, "
                      + " o.order_status as orderStatus,o.notify_status as notifyStatus,o.manual_flag as manualFlag,to_char(o.order_finish_time, 'YYYY/mm/dd HH24:mi:ss') as orderFinishTime,(o.order_fee-o.order_success_fee) as orderWaitFee                                    "
                      + ",o.pre_success_status as preSuccessStatus,o.order_Reason as orderReason from yc_order o where 1 = 1 ";
            }

            Query query = em.createNativeQuery(pageTotal_sql);
            BigDecimal total = (BigDecimal)query.getSingleResult();

            Double pageTotal = BigDecimalUtil.divide(total, new BigDecimal(pageSize),
                DecimalPlaces.ZERO.value(), BigDecimal.ROUND_CEILING).doubleValue();
            insidesql = insidesql + condition;

            sql += condition + "order by o.order_request_time desc, o.order_no desc) od "
                   + "where 1 = 1 and rownum <= " + endIndex + ")                  "
                   + "where rn > " + startIndex + "                                ";
            query = em.createNativeQuery(sql);
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List<OrderNativeVo> result = new ArrayList<OrderNativeVo>();
            List<?> rows = query.getResultList();
            for (Object obj : rows)
            {
                Map<String, Object> row = (Map<String, Object>)obj;
                OrderNativeVo order = new OrderNativeVo();
                BeanUtils.transMap2Bean(row, order);
                result.add(order);
            }

            YcPage<OrderNativeVo> ycPage = new YcPage<OrderNativeVo>();
            result = converDeliveryToOrderNativeVos(result);
            ycPage.setList(result);
            ycPage.setCountTotal(total.intValue());
            ycPage.setPageTotal(pageTotal.intValue());
            return ycPage;
        }
        catch (Exception e)
        {
            logger.error("queryOrders exception info[" + ExceptionUtil.getStackTraceAsString(e)
                         + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public YcPage<OrderVo> queryPartSuccessOrders(OrderParameterVo orderParameter, int pageNumber,
                                                  int pageSize)
    {
        try
        {
            int startIndex = pageNumber * pageSize - pageSize;
            int endIndex = startIndex + pageSize;

            if (StringUtil.isBlank(orderParameter.getSortType())
                || orderParameter.getSortType().equalsIgnoreCase("auto"))
            {
                orderParameter.setSortType("order_request_time");
            }
            String insidesql = "select o.*,rownum rn from (select * from yc_order order by "
                               + orderParameter.getSortType() + " desc) o where 1=1";

            if (endIndex > 0)
            {
                insidesql = insidesql + " and rownum <= " + endIndex;
            }
            String condition = StringUtil.initString();
            if (StringUtil.isNotBlank(orderParameter.getCarrierInfo()))
            {
                condition = condition + " and ext1 = '" + orderParameter.getCarrierInfo() + "'";
            }
            if (StringUtil.isNotBlank(orderParameter.getProvince()))
            {
                condition = condition + " and ext2 = '" + orderParameter.getProvince() + "'";
            }
            if (StringUtil.isNotBlank(orderParameter.getParValue()))
            {
                condition = condition + " and order_fee = '" + orderParameter.getParValue() + "'";
            }
            if (StringUtil.isNotBlank(orderParameter.getAgentMerchant()))
            {
                condition = condition + " and merchant_id = '" + orderParameter.getAgentMerchant()
                            + "'";
            }
            if (orderParameter.getOrderStatus() != null)
            {
                condition = condition + " and order_status = '" + orderParameter.getOrderStatus()
                            + "'";
            }
            if (StringUtil.isNotBlank(orderParameter.getBeginDate()))
            {// to_date('"+dateStr+"','yyyy-mm-dd
             // hh24:mi:ss')";
                condition = condition + " and order_request_time >= to_date('"
                            + orderParameter.getBeginDate() + "','yyyy-mm-dd hh24:mi:ss')";
            }
            if (StringUtil.isNotBlank(orderParameter.getEndDate()))
            {
                condition = condition + " and order_request_time <= to_date('"
                            + orderParameter.getEndDate() + "','yyyy-mm-dd hh24:mi:ss')";
            }
            if (orderParameter.getNotifyStatus() != null)
            {
                condition = condition + " and notify_status = '"
                            + orderParameter.getNotifyStatus() + "'";
            }

            if (StringUtil.isNotBlank(orderParameter.getPreSuccessStatus()))
            {
                condition = condition + " and pre_success_status = '"
                            + orderParameter.getPreSuccessStatus() + "'";
            }

            if (StringUtil.isNotBlank(orderParameter.getUsercode()))
            {
                condition = condition + " and user_code = '" + orderParameter.getUsercode() + "'";
            }

            if (StringUtil.isNotBlank(orderParameter.getOrderNo()))
            {
                condition = condition + " and order_no = '" + orderParameter.getOrderNo() + "'";
            }
            if (StringUtil.isNotBlank(orderParameter.getMerchantOrderNo()))
            {
                condition = condition + " and merchant_order_no = '"
                            + orderParameter.getMerchantOrderNo() + "'";
            }

            if (StringUtil.isNotBlank(orderParameter.getBusinessType()))
            {
                condition = condition + " and business_type = '"
                            + orderParameter.getBusinessType() + "'";
            }

            String pageTotal_sql = "select count(*) from yc_order where 1=1" + condition;
            Query query = em.createNativeQuery(pageTotal_sql);
            BigDecimal total = (BigDecimal)query.getSingleResult();

            Double pageTotal = BigDecimalUtil.divide(total, new BigDecimal(pageSize),
                DecimalPlaces.ZERO.value(), BigDecimal.ROUND_CEILING).doubleValue();

            insidesql = insidesql + condition;
            String sql = "select * from (" + insidesql + ") where rn>" + startIndex;
            query = em.createNativeQuery(sql, Order.class);
            List<Order> orderList = query.getResultList();
            YcPage<OrderVo> ycPage = new YcPage<OrderVo>();
            List<OrderVo> result = converDeliveryInfoToOrders(orderList);
            ycPage.setList(result);
            ycPage.setCountTotal(total.intValue());
            ycPage.setPageTotal(pageTotal.intValue());
            return ycPage;
        }
        catch (Exception e)
        {
            logger.error("queryPartSuccessOrders exception info["
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public YcPage<OrderNativeVo> queryTimeOutOrders(OrderParameterVo orderParameter,
                                                    int pageNumber, int pageSize)
    {
        try
        {
            ParameterConfiguration hc = parameterConfigurationService.getParameterConfigurationByKey(Constant.ParameterConfiguration.TIMEOUT_CONSTANT);
            int time = Integer.parseInt(hc.getConstantValue());
            String unit = hc.getConstantUnitValue();
            // 单位
            time = DateUtil.formatUnit(unit, time);
            int startIndex = pageNumber * pageSize - pageSize;
            int endIndex = startIndex + pageSize;

            String insidesql = "select od.*,rownum rn from (select * from  yc_order o where 1=1";

            String condition = StringUtil.initString();
            if (BeanUtils.isNotNull(orderParameter.getOrderStatus()))
            {
                condition += "   and o.order_status =" + orderParameter.getOrderStatus();

            }

            if (BeanUtils.isNotNull(orderParameter.getNotifyStatus()))
            {
                condition += "   and o.notify_Status =" + orderParameter.getNotifyStatus();

            }

            if (StringUtil.isNotBlank(orderParameter.getMerchantOrderNo()))
            {
                condition += "   and o.merchant_Order_No ='" + orderParameter.getMerchantOrderNo()
                             + "' ";

            }

            if (StringUtil.isNotBlank(orderParameter.getOrderNo()))
            {
                condition += "   and o.order_No =" + orderParameter.getOrderNo();

            }

            if (StringUtil.isNotBlank(orderParameter.getUsercode()))
            {
                condition += "   and o.user_code ='" + orderParameter.getUsercode() + "'";

            }

            if (StringUtil.isNotBlank(orderParameter.getMerchantId()))
            {
                condition += "   and o.merchant_Id =" + orderParameter.getMerchantId();

            }

            if (StringUtil.isNotBlank(orderParameter.getParValue()))
            {
                condition += "   and o.product_Face ='" + orderParameter.getParValue() + "' ";

            }

            if (StringUtil.isNotBlank(orderParameter.getCarrierInfo()))
            {
                condition += "   and o.ext1 ='" + orderParameter.getCarrierInfo() + "' ";

            }

            if (StringUtil.isNotBlank(orderParameter.getProvince()))
            {
                condition += "   and o.ext2 ='" + orderParameter.getProvince() + "' ";

            }

            if (StringUtil.isNotBlank(orderParameter.getBusinessType()))
            {
                condition += "   and o.business_Type ='" + orderParameter.getBusinessType() + "' ";
            }

            if (StringUtil.isNotBlank(orderParameter.getSupplyMerchant()))
            {
                condition += "   and d.merchant_id =" + orderParameter.getSupplyMerchant();
            }

            if (StringUtil.isNotBlank(orderParameter.getManualFlag()))
            {
                condition += "   and o.manual_Flag =" + orderParameter.getManualFlag();
            }

            condition += "and ceil(((sysdate - o.order_request_time)) * 24 * 60 * 60) >" + time
                         + "   and order_status != 3                                         "
                         + "   and order_status != 4                                         ";
            if (BeanUtils.isNotNull(orderParameter.getPreSuccessStatus())
                && orderParameter.getPreSuccessStatus().equalsIgnoreCase(
                    String.valueOf(Constant.OrderStatus.PRE_SUCCESS_STATUS_NO_NEED)))
            {
                // 普通订单
                // + "   and pre_success_status = 0                                    "
                condition += " and pre_success_status = 0";
            }
            else
            {
                condition += " and pre_success_status != 0";
            }
            condition += "   and (overrn =1 or overrn is null)                                  "
                         + "     and order_request_time >=                                        "
                         + "         to_date('"
                         + orderParameter.getBeginDate()
                         + "', 'yyyy-mm-dd hh24:mi:ss')      "
                         + "     and order_request_time <=                                        "
                         + "         to_date('" + orderParameter.getEndDate()
                         + "', 'yyyy-mm-dd hh24:mi:ss')      ";

            String pageTotal_sql = "select count(*)  from yc_order o left join ( select yd.order_no,yd.merchant_id,ROW_NUMBER() OVER(PARTITION BY order_no ORDER BY yd.delivery_start_time DESC) overrn   from yc_delivery yd) d on o.order_no=d.order_no  where 1=1 "
                                   + condition;

            Query query = em.createNativeQuery(pageTotal_sql);
            BigDecimal total = (BigDecimal)query.getSingleResult();

            Double pageTotal = BigDecimalUtil.divide(total, new BigDecimal(pageSize),
                DecimalPlaces.ZERO.value(), BigDecimal.ROUND_CEILING).doubleValue();
            insidesql = insidesql + condition;

            String sql = " select *                                                                                                      "
                         + " from (select od.*, rownum rn                                                                                "
                         + " from (select  to_char(o.order_request_time, 'YYYY/mm/dd HH24:mi:ss') as orderRequestTime,o.order_no as orderNo,o.merchant_name agentMerchant,m.merchant_name supplyMerchant,  "
                         + " o.business_type as businessType,o.ext1,o.ext2,o.ext3,o.ext4,o.user_code as userCode,o.product_face as productFace,o.display_value as displayValue,o.order_success_fee as orderSuccessFee, "
                         + " o.order_status as orderStatus,o.notify_status as notifyStatus,o.manual_flag as manualFlag,to_char(o.order_finish_time, 'YYYY/mm/dd HH24:mi:ss') as orderFinishTime,(o.order_fee-o.order_success_fee) as orderWaitFee                                    "
                         + " from yc_order o left join (select yd.order_no,yd.merchant_id,ROW_NUMBER() OVER(PARTITION BY order_no ORDER BY yd.delivery_start_time DESC) overrn   from yc_delivery yd)  d on o.order_no=d.order_no left join  "
                         + " merchant m  on d.merchant_id=m.identity_id where 1 = 1 ";

            sql += condition + "order by o.order_request_time desc, o.order_no desc) od "
                   + "where 1 = 1 and rownum <= " + endIndex + ")                  "
                   + "where rn > " + startIndex + "                                ";
            query = em.createNativeQuery(sql);
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List<OrderNativeVo> result = new ArrayList<OrderNativeVo>();
            List<?> rows = query.getResultList();
            for (Object obj : rows)
            {
                Map<String, Object> row = (Map<String, Object>)obj;
                OrderNativeVo order = new OrderNativeVo();
                BeanUtils.transMap2Bean(row, order);
                result.add(order);
            }

            YcPage<OrderNativeVo> ycPage = new YcPage<OrderNativeVo>();
            ycPage.setList(result);
            ycPage.setCountTotal(total.intValue());
            ycPage.setPageTotal(pageTotal.intValue());
            return ycPage;
        }
        catch (Exception e)
        {
            logger.error("queryTimeOutOrders exception info["
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

    private void setParameterOfTimeOrder(OrderParameterVo orderParameter, Integer time,
                                         Integer startIndex, Integer endIndex, Query query)
    {
        query.setParameter("time", time);
        query.setParameter(EntityConstant.Order.ORDER_STATUS,
            null == orderParameter.getOrderStatus() ? "" : orderParameter.getOrderStatus());
        query.setParameter(EntityConstant.Order.EXT1,
            null == orderParameter.getCarrierInfo() ? "" : orderParameter.getCarrierInfo());
        query.setParameter(EntityConstant.Order.EXT2,
            null == orderParameter.getProvince() ? "" : orderParameter.getProvince());
        query.setParameter(EntityConstant.Order.ORDER_FEE,
            null == orderParameter.getParValue() ? "" : orderParameter.getParValue());
        query.setParameter(EntityConstant.Order.MERCHANT_ID,
            null == orderParameter.getMerchantId() ? "" : orderParameter.getMerchantId());

        query.setParameter(EntityConstant.Order.NOTIFY_STATUS,
            null == orderParameter.getNotifyStatus() ? "" : orderParameter.getNotifyStatus());
        query.setParameter(
            EntityConstant.Order.PRE_SUCCESS_STATUS,
            null == orderParameter.getPreSuccessStatus() ? "" : orderParameter.getPreSuccessStatus());
        query.setParameter(EntityConstant.Order.USER_CODE,
            null == orderParameter.getUsercode() ? "" : orderParameter.getUsercode());
        query.setParameter(EntityConstant.Order.ORDER_NO,
            null == orderParameter.getOrderNo() ? "" : orderParameter.getOrderNo());
        query.setParameter(EntityConstant.Order.MERCHANT_ORDER_NO,
            null == orderParameter.getMerchantOrderNo() ? "" : orderParameter.getMerchantOrderNo());
        query.setParameter(EntityConstant.Order.BUSINESS_TYPE,
            null == orderParameter.getBusinessType() ? "" : orderParameter.getBusinessType());
        query.setParameter("supplyMerchant",
            null == orderParameter.getSupplyMerchant() ? "" : orderParameter.getSupplyMerchant());
        query.setParameter(EntityConstant.Order.BEGIN_DATE, orderParameter.getBeginDate());
        query.setParameter(EntityConstant.Order.END_DATE, orderParameter.getEndDate());
        if (startIndex == null)
        {
            query.setParameter("startIndex", "");
            query.setParameter("rownum", "");
        }
        else
        {
            query.setParameter("startIndex", startIndex);
            query.setParameter("rownum", endIndex);
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public YcPage<OrderVo> queryManualAuditOrders(OrderParameterVo orderParameter, int pageNumber,
                                                  int pageSize)
    {
        try
        {
            int startIndex = pageNumber * pageSize - pageSize;
            int endIndex = startIndex + pageSize;

            if (StringUtil.isBlank(orderParameter.getSortType())
                || orderParameter.getSortType().equalsIgnoreCase("auto"))
            {
                orderParameter.setSortType("order_request_time");
            }
            String insidesql = "select o.*,rownum rn from (select * from yc_order order by "
                               + orderParameter.getSortType() + " desc) o where 1=1";

            if (endIndex > 0)
            {
                insidesql = insidesql + " and rownum <= " + endIndex;
            }
            String condition = StringUtil.initString();
            condition = condition + " and  order_status!='" + Constant.OrderStatus.SUCCESS
                        + "' and order_status!='" + Constant.OrderStatus.FAILURE_ALL
                        + "' and manual_flag='" + Constant.OrderManualFlag.ORDER_MANUAL_FLAG_ING
                        + "' and pre_success_status!=0 ";

            if (orderParameter.getOrderStatus() != null)
            {
                condition = condition + " and (order_status = '" + orderParameter.getOrderStatus()
                            + "' or '" + orderParameter.getOrderStatus() + "' is null)";
            }

            if (StringUtil.isNotBlank(orderParameter.getCarrierInfo()))
            {
                condition = condition + " and ext1 = '" + orderParameter.getCarrierInfo() + "'";
            }
            if (StringUtil.isNotBlank(orderParameter.getProvince()))
            {
                condition = condition + " and ext2 = '" + orderParameter.getProvince() + "'";
            }
            if (StringUtil.isNotBlank(orderParameter.getParValue()))
            {
                condition = condition + " and order_fee = '" + orderParameter.getParValue() + "'";
            }
            if (StringUtil.isNotBlank(orderParameter.getAgentMerchant()))
            {
                condition = condition + " and merchant_id = '" + orderParameter.getAgentMerchant()
                            + "'";
            }

            if (StringUtil.isNotBlank(orderParameter.getBeginDate()))
            {
                condition = condition + " and order_request_time >= to_date('"
                            + orderParameter.getBeginDate() + "','yyyy-mm-dd hh24:mi:ss')";
            }
            if (StringUtil.isNotBlank(orderParameter.getEndDate()))
            {
                condition = condition + " and order_request_time <= to_date('"
                            + orderParameter.getEndDate() + "','yyyy-mm-dd hh24:mi:ss')";
            }

            if (BeanUtils.isNotNull(orderParameter.getNotifyStatus()))
            {
                condition = condition + " and notify_status = '"
                            + orderParameter.getNotifyStatus() + "'";
            }

            if (StringUtil.isNotBlank(orderParameter.getPreSuccessStatus()))
            {
                condition = condition + " and pre_success_status = '"
                            + orderParameter.getPreSuccessStatus() + "'";
            }

            if (StringUtil.isNotBlank(orderParameter.getUsercode()))
            {
                condition = condition + " and user_code = '" + orderParameter.getUsercode() + "'";
            }

            if (StringUtil.isNotBlank(orderParameter.getOrderNo()))
            {
                condition = condition + " and order_no = '" + orderParameter.getOrderNo() + "'";
            }
            if (StringUtil.isNotBlank(orderParameter.getMerchantOrderNo()))
            {
                condition = condition + " and merchant_order_no = '"
                            + orderParameter.getMerchantOrderNo() + "'";
            }

            if (!StringUtil.isNullOrEmpty(orderParameter.getBusinessType()))
            {
                condition = condition + " and business_type = '"
                            + orderParameter.getBusinessType() + "'";
            }
            if (!StringUtil.isNullOrEmpty(orderParameter.getReBindType()))
            {
                condition = condition
                            + " and bind_times = limit_bind_times and exists (select yd.order_no from yc_delivery yd where yd.order_no = order_no and yd.query_flag = '3')";
            }

            if (StringUtil.isNotBlank(orderParameter.getSortType()))
            {
                condition = condition + " order by " + orderParameter.getSortType() + " desc ";
            }
            else
            {
                condition = condition + " order by ORDER_REQUEST_TIME desc";
            }

            String pageTotal_sql = "select count(*) from yc_order where 1=1" + condition;

            Query query = em.createNativeQuery(pageTotal_sql);
            BigDecimal total = (BigDecimal)query.getSingleResult();

            Double pageTotal = BigDecimalUtil.divide(total, new BigDecimal(pageSize),
                DecimalPlaces.ZERO.value(), BigDecimal.ROUND_CEILING).doubleValue();

            insidesql = insidesql + condition;
            String sql = "select * from (" + insidesql + ") where rn>" + startIndex;
            query = em.createNativeQuery(sql, Order.class);
            List<Order> orderList = query.getResultList();
            YcPage<OrderVo> ycPage = new YcPage<OrderVo>();
            List<OrderVo> result = converDeliveryInfoToOrders(orderList);
            ycPage.setList(result);
            ycPage.setCountTotal(total.intValue());
            ycPage.setPageTotal(pageTotal.intValue());
            return ycPage;
        }
        catch (Exception e)
        {
            logger.error("queryManualAuditOrders exception info["
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

    public List<OrderVo> converDeliveryInfoToOrders(List<Order> orderList)
    {
        try
        {
            Map<String, SearchFilter> filters = null;
            List<OrderVo> result = new ArrayList<OrderVo>();
            for (Order order : orderList)
            {
                OrderVo orderVo = new OrderVo();
                try
                {
                    BeanUtils.copyProperties(orderVo, order);
                }
                catch (Exception e)
                {
                    continue;
                }
                BigDecimal orderSuccessFee = null;
                if (orderVo.getOrderSuccessFee() != null)
                {
                    orderSuccessFee = order.getOrderSuccessFee();
                }
                else
                {
                    orderSuccessFee = new BigDecimal(0);
                }
                orderVo.setOrderRequestTime(order.getOrderRequestTime());
                orderVo.setOrderFinishTime(order.getOrderFinishTime());
                BigDecimal orderWaitFee = orderVo.getOrderFee().subtract(orderSuccessFee);
                orderVo.setOrderWaitFee(orderWaitFee);
                filters = new HashMap<String, SearchFilter>();
                filters.put(EntityConstant.Delivery.ORDER_NO, new SearchFilter(
                    EntityConstant.Delivery.ORDER_NO, Operator.EQ, orderVo.getOrderNo()));
                Specification<Delivery> spec_Delivery = DynamicSpecifications.bySearchFilter(
                    filters.values(), Delivery.class);
                List<Delivery> deliverys = deliveryJpaDao.findAll(spec_Delivery, new Sort(
                    Direction.DESC, EntityConstant.Delivery.DELIVERY_ID));
                if (deliverys.size() > 0)
                {
                    Merchant merchant = merchantService.queryMerchantById(deliverys.get(0).getMerchantId());
                    if (BeanUtils.isNotNull(merchant))
                    {
                        orderVo.setSupplierName(merchant.getMerchantName());
                    }
                }
                result.add(orderVo);
            }
            return result;
        }
        catch (Exception e)
        {
            logger.error("converDeliveryInfoToOrders exception info["
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Order> findOrdersByParams(Integer orderStatus)
    {
        try
        {
            ParameterConfiguration parameterConfiguration = parameterConfigurationService.getParameterConfigurationByKey(Constant.ParameterConfiguration.SCANNER_BIND_NUM);
            int scannerBindNum = 0;
            if (BeanUtils.isNull(parameterConfiguration))
            {
                scannerBindNum = 15;
            }
            else
            {
                scannerBindNum = Integer.valueOf(parameterConfiguration.getConstantValue());
            }
            String sql = "select o.* from yc_order o where o.order_request_time<sysdate and o.order_request_time>sysdate-2 and o.order_Status = :orderStatus"
                         + " and o.pre_order_bind_time <= sysdate and o.bind_times <= o.limit_bind_times";
            Query query = em.createNativeQuery(sql, Order.class).setMaxResults(scannerBindNum);
            query.setParameter("orderStatus", orderStatus);
            List<Order> orderList = query.getResultList();
            return orderList;
        }
        catch (Exception e)
        {
            logger.error("findOrdersByParams exception info["
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public YcPage<Order> queryFakeOrders(Map<String, Object> searchParams, int pageNumber,
                                         int pageSize)
    {
        YcPage<Order> ycPage = new YcPage<Order>();
        try
        {
            int startIndex = pageNumber * pageSize - pageSize;
            int endIndex = startIndex + pageSize;
            String pageTotal_sql = "select count(*) from (" + OracleSql.Order.QUERYFAKEORDERS_SQL
                                   + ")";
            Query query = em.createNativeQuery(pageTotal_sql);
            query = OracleSql.setParameter(searchParams, query, 0, 0);
            BigDecimal total = (BigDecimal)query.getSingleResult();

            Double pageTotal = BigDecimalUtil.divide(total, new BigDecimal(pageSize),
                DecimalPlaces.ZERO.value(), BigDecimal.ROUND_CEILING).doubleValue();

            String sql = "select * from (" + OracleSql.Order.QUERYFAKEORDERS_SQL
                         + ") where rn>:startIndex";
            query = em.createNativeQuery(sql, Order.class);
            query = OracleSql.setParameter(searchParams, query, startIndex, endIndex);
            List<Order> result = query.getResultList();
            ycPage.setList(result);
            ycPage.setCountTotal(total.intValue());
            ycPage.setPageTotal(pageTotal.intValue());
        }
        catch (Exception e)
        {
            logger.error("[OrderSqlDao :queryFakeOrders()][异常:" + e.getMessage() + "]");
        }
        return ycPage;
    }

    @Override
    public OrderStatisticsVo statisticsOrderInfo(OrderParameterVo orderParameter, String type)
    {
        // TODO Auto-generated method stub
        String value = "";
        if (!StringUtil.isNullOrEmpty(orderParameter.getBusinessType())
            && Constant.BusinessType.BUSINESS_TYPE_HF.equals(orderParameter.getBusinessType()))
        {
            value = "o.product_face";
        }
        else if (!StringUtil.isNullOrEmpty(orderParameter.getBusinessType())
                 && Constant.BusinessType.BUSINESS_TYPE_FLOW.equals(orderParameter.getBusinessType()))
        {
            value = "o.display_value";
        }
        String sql = "select o.productFace,o.orderSuccessFee,o.orderSalesFee,o2.orderWaitFee,o3.orderfailfee from "
                     + "(select sum("
                     + value
                     + ") as productFace,sum(o.order_success_fee) as orderSuccessFee,sum(o.order_sales_fee) as orderSalesFee from yc_order o "
                     + " where 1=1 and ((o.order_status=:orderStatus and :orderStatus is not null) or :orderStatus is null) and ((o.notify_status=:notifyStatus and :notifyStatus is not null) or :notifyStatus is null)"
                     + " and ((o.merchant_order_no=:merchantOrderNo and :merchantOrderNo is not null) or :merchantOrderNo is null) and ((o.order_no=:orderNo and :orderNo is not null) or :orderNo is null)"
                     + " and ((o.user_code=:userCode and :userCode is not null) or :userCode is null) and ((o.pre_success_status=:preSuccessStatus and :preSuccessStatus is not null) or :preSuccessStatus is null) "
                     + " and ((o.merchant_id=:merchantId and :merchantId is not null) or :merchantId is null) and ((o.product_face=:productFace and :productFace is not null) or :productFace is null) "
                     + " and ((o.ext1=:ext1 and :ext1 is not null) or :ext1 is null) and ((o.ext2=:ext2 and :ext2 is not null) or :ext2 is null)"
                     + " and ((o.business_type=:businessType and :businessType is not null) or :businessType is null) "
                     + "   and ((o.manual_Flag = :manualFlag and                         "
                     + "       :manualFlag is not null) or :manualFlag is null)          ";

        // ManualAuditOrders
        sql = statisticsOrderInfoOfSql(type, sql);

        sql += " and o.order_request_time >=to_date(:beginDate,'yyyy-mm-dd hh24:mi:ss') and o.order_request_time <=to_date(:endDate,'yyyy-mm-dd hh24:mi:ss'))o,"
               + "(select (sum("
               + value
               + ") -sum(o.order_success_fee)) as orderWaitFee  from yc_order o"
               + " where 1=1 and ((o.order_status=:orderStatus and :orderStatus is not null) or :orderStatus is null) "
               + " and ((o.notify_status=:notifyStatus and :notifyStatus is not null) or :notifyStatus is null)"
               + " and ((o.merchant_order_no=:merchantOrderNo and :merchantOrderNo is not null) or :merchantOrderNo is null) and ((o.order_no=:orderNo and :orderNo is not null) or :orderNo is null)"
               + " and ((o.user_code=:userCode and :userCode is not null) or :userCode is null) and ((o.pre_success_status=:preSuccessStatus and :preSuccessStatus is not null) or :preSuccessStatus is null) "
               + " and ((o.merchant_id=:merchantId and :merchantId is not null) or :merchantId is null) and ((o.product_face=:productFace and :productFace is not null) or :productFace is null) "
               + " and ((o.ext1=:ext1 and :ext1 is not null) or :ext1 is null) and ((o.ext2=:ext2 and :ext2 is not null) or :ext2 is null) "
               + " and o.order_status!='"
               + Constant.OrderStatus.SUCCESS
               + "' and o.order_status!='"
               + Constant.OrderStatus.FAILURE_ALL
               + "' "
               + " and ((o.business_type=:businessType and :businessType is not null) or :businessType is null) "
               + "   and ((o.manual_Flag = :manualFlag and                         "
               + "       :manualFlag is not null) or :manualFlag is null)         ";

        // ManualAuditOrders
        sql = statisticsOrderInfoOfSql(type, sql);

        sql += " and o.order_request_time >=to_date(:beginDate,'yyyy-mm-dd hh24:mi:ss') and o.order_request_time <=to_date(:endDate,'yyyy-mm-dd hh24:mi:ss'))o2 "
               + " ,(select sum("
               + value
               + ")as orderfailfee from yc_order o "
               + " where 1=1 and ((o.order_status=:orderStatus and :orderStatus is not null) or :orderStatus is null) and ((o.notify_status=:notifyStatus and :notifyStatus is not null) or :notifyStatus is null)"
               + " and ((o.merchant_order_no=:merchantOrderNo and :merchantOrderNo is not null) or :merchantOrderNo is null) and ((o.order_no=:orderNo and :orderNo is not null) or :orderNo is null)"
               + " and ((o.user_code=:userCode and :userCode is not null) or :userCode is null) and ((o.pre_success_status=:preSuccessStatus and :preSuccessStatus is not null) or :preSuccessStatus is null) "
               + " and ((o.merchant_id=:merchantId and :merchantId is not null) or :merchantId is null) and ((o.product_face=:productFace and :productFace is not null) or :productFace is null) "
               + " and ((o.ext1=:ext1 and :ext1 is not null) or :ext1 is null) and ((o.ext2=:ext2 and :ext2 is not null) or :ext2 is null) "
               + " and o.order_status='"
               + Constant.OrderStatus.FAILURE_ALL
               + "' "
               + " and ((o.business_type=:businessType and :businessType is not null) or :businessType is null) "
               + "   and ((o.manual_Flag = :manualFlag and                         "
               + "       :manualFlag is not null) or :manualFlag is null)         ";

        if (StringUtil.isNotBlank(orderParameter.getReBindType()))
        {
            sql += " and bind_times = limit_bind_times and exists (select yd.order_no from yc_delivery yd where yd.order_no = order_no and yd.query_flag = '3')";

        }
        sql = statisticsOrderInfoOfSql(type, sql);
        sql += " and o.order_request_time >=to_date(:beginDate,'yyyy-mm-dd hh24:mi:ss') and o.order_request_time <=to_date(:endDate,'yyyy-mm-dd hh24:mi:ss')) o3";
        Query query = em.createNativeQuery(sql);
        setParamterForOrder(orderParameter, query);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        OrderStatisticsVo orderStatisticsVo = new OrderStatisticsVo();
        Map<String, Object> row = (Map<String, Object>)query.getSingleResult();
        BeanUtils.transMap2Bean(row, orderStatisticsVo);
        return orderStatisticsVo;
    }

    private void setParamterForOrder(OrderParameterVo orderParameter, Query query)
    {
        query.setParameter("orderStatus",
            orderParameter.getOrderStatus() == null ? "" : orderParameter.getOrderStatus());
        query.setParameter("notifyStatus",
            orderParameter.getNotifyStatus() == null ? "" : orderParameter.getNotifyStatus());
        query.setParameter("merchantOrderNo",
            orderParameter.getMerchantOrderNo() == null ? "" : orderParameter.getMerchantOrderNo());
        query.setParameter("orderNo",
            orderParameter.getOrderNo() == null ? "" : orderParameter.getOrderNo());
        query.setParameter("userCode",
            orderParameter.getUsercode() == null ? "" : orderParameter.getUsercode());
        query.setParameter(
            "preSuccessStatus",
            orderParameter.getPreSuccessStatus() == null ? "" : orderParameter.getPreSuccessStatus());
        query.setParameter("merchantId",
            orderParameter.getMerchantId() == null ? "" : orderParameter.getMerchantId());
        query.setParameter("productFace",
            orderParameter.getParValue() == null ? "" : orderParameter.getParValue());
        query.setParameter("ext1",
            orderParameter.getCarrierInfo() == null ? "" : orderParameter.getCarrierInfo());
        query.setParameter("ext2",
            orderParameter.getProvince() == null ? "" : orderParameter.getProvince());
        query.setParameter("beginDate",
            orderParameter.getBeginDate() == null ? "" : orderParameter.getBeginDate());
        query.setParameter("endDate",
            orderParameter.getEndDate() == null ? "" : orderParameter.getEndDate());

        query.setParameter("businessType",
            orderParameter.getBusinessType() == null ? "" : orderParameter.getBusinessType());

        query.setParameter("manualFlag",
            orderParameter.getManualFlag() == null ? "" : orderParameter.getManualFlag());

    }

    private String statisticsOrderInfoOfSql(String type, String sql)
    {
        if (Constant.OrderStatistics.MANUAL_AUDIT_ORDER.equals(type))
        {
            sql += " and o.order_status!='" + Constant.OrderStatus.SUCCESS
                   + "' and order_status!='" + Constant.OrderStatus.FAILURE_ALL + "' ";
            sql += " and o.manual_flag='" + Constant.OrderManualFlag.ORDER_MANUAL_FLAG_ING + "' "
                   + " and o.pre_success_status!=0 ";
        }
        else if (Constant.OrderStatistics.TIME_OUT_ORDER.equals(type))
        {
            sql += " and o.order_status!='" + Constant.OrderStatus.SUCCESS
                   + "' and o.order_status!='" + Constant.OrderStatus.FAILURE_ALL + "' ";
            ParameterConfiguration hc = parameterConfigurationService.getParameterConfigurationByKey(Constant.ParameterConfiguration.TIMEOUT_CONSTANT);
            int time = Integer.parseInt(hc.getConstantValue());
            String unit = hc.getConstantUnitValue();
            // 单位
            time = DateUtil.formatUnit(unit, time);

            sql += " and ceil(((sysdate - o.order_request_time)) * 24 * 60 * 60)>" + time;
            sql += " and o.pre_success_status=0 ";
        }
        else if (Constant.OrderStatistics.PRE_SUCCESS_ORDER.equals(type))
        {
            sql += " and o.pre_success_status!='0'";
        }
        else if (Constant.OrderStatistics.PRE_TIME_OUT_ORDER.equals(type))
        {
            sql += " and o.order_status!='" + Constant.OrderStatus.SUCCESS
                   + "' and o.order_status!='" + Constant.OrderStatus.FAILURE_ALL + "' ";
            ParameterConfiguration hc = parameterConfigurationService.getParameterConfigurationByKey(Constant.ParameterConfiguration.TIMEOUT_CONSTANT);
            int time = Integer.parseInt(hc.getConstantValue());
            String unit = hc.getConstantUnitValue();
            // 单位
            time = DateUtil.formatUnit(unit, time);

            sql += " and ceil(((sysdate - o.order_request_time)) * 24 * 60 * 60)>" + time;
            sql += " and o.pre_success_status!=0 ";
        }
        return sql;
    }

    @Override
    public BigDecimal getOrderAmtSumByIdentityId(Long identityId, int orderStatus,
                                                 int deliveryStatus, Date beginTime, Date endTime)
    {
        // TODO Auto-generated method stub
        try
        {
            SimpleDateFormat fomat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String beginDate = fomat.format(beginTime);
            String endDate = fomat.format(endTime);
            String sql = "select sum(o.order_sales_fee)-sum(d.cost_fee) from yc_order o,yc_delivery d where 1=1 "
                         + "and o.order_status=:orderStatus and d.delivery_status=:deliveryStatus and  (o.order_no=d.order_no or d.order_no is null) "
                         + "and o.merchant_id=:merchantId "
                         + "and o.order_finish_time>=to_date(:beginDate,'yyyy/mm/dd hh24:mi:ss') "
                         + "and o.order_finish_time <=to_date(:endDate,'yyyy/mm/dd hh24:mi:ss')";

            Query query = em.createNativeQuery(sql);
            query.setParameter("merchantId", identityId);
            query.setParameter("orderStatus", orderStatus);
            query.setParameter("deliveryStatus", deliveryStatus);
            query.setParameter("beginDate", beginDate);
            query.setParameter("endDate", endDate);
            BigDecimal sumAmtStr = (BigDecimal)query.getSingleResult();
            return sumAmtStr;
        }
        catch (Exception e)
        {
            logger.error("getTransactionHistoryOfAmtSum exception error["
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

    @Override
    public YcPage<OrderExportVo> queryOrderForExport(Map<String, Object> searchParams,
                                                     int pageNumber, int pageSize,
                                                     String carrierInfo, String province,
                                                     String parValue, String downMerchant,
                                                     Integer status, String sortType,
                                                     String beginDate, String endDate,
                                                     Integer notifyStatus,
                                                     String preSuccessStatus, String usercode,
                                                     String orderNo, String merchantOrderNo,
                                                     String businessType, boolean isFake)
    {
        try
        {
            int startIndex = pageNumber * pageSize - pageSize;
            int endIndex = startIndex + pageSize;
            // select * from yc_order order by "
            // + sortType + " desc

            String insidesql = "select o.*,rownum rn from (select o.order_request_time as orderRequestTime,o.order_no as orderno,o.merchant_order_no as merchantorderno,"
                               + "o.user_code as usercode,o.product_face as productface,o.order_fee as orderFee,o.order_sales_fee as ordersalesfee,p.product_name as productname,o.order_status as orderstatus,o.pre_success_status as preSuccessStatus from yc_order o,"
                               + " airtime_product p where 1=1 ";

            String condition = StringUtil.initString();
            if (carrierInfo != null && !carrierInfo.isEmpty())
            {
                condition = condition + " and o.ext1 = '" + carrierInfo + "'";
            }
            if (province != null && !province.isEmpty())
            {
                condition = condition + " and o.ext2 = '" + province + "'";
            }
            if (parValue != null && !parValue.isEmpty())
            {
                condition = condition + " and o.product_face = '" + parValue + "'";
            }
            if (downMerchant != null && !downMerchant.isEmpty())
            {
                condition = condition + " and o.merchant_id = '" + downMerchant + "'";
            }
            if (status != null)
            {
                condition = condition + " and o.order_status = '" + status + "'";
            }
            if (beginDate != null && !beginDate.isEmpty())
            {
                condition = condition + " and o.order_request_time >= to_date('" + beginDate
                            + "','yyyy-mm-dd hh24:mi:ss')";
            }
            if (endDate != null && !endDate.isEmpty())
            {
                condition = condition + " and o.order_request_time <= to_date('" + endDate
                            + "','yyyy-mm-dd hh24:mi:ss')";
            }
            if (notifyStatus != null)
            {
                condition = condition + " and o.notify_status = '" + notifyStatus + "'";
            }

            if (preSuccessStatus != null && !preSuccessStatus.isEmpty())
            {
                if (preSuccessStatus.equals(Constant.OrderStatus.PRE_SUCCESS_STATUS_WAIT + ""))
                {
                    condition = condition + " and o.pre_success_status != '"
                                + Constant.OrderStatus.PRE_SUCCESS_STATUS_DONE + "'";
                }
                else
                {
                    condition = condition + " and o.pre_success_status = '" + preSuccessStatus
                                + "'";
                }

            }

            if (usercode != null && !usercode.isEmpty())
            {
                condition = condition + " and o.user_code = '" + usercode + "'";
            }

            if (orderNo != null && !orderNo.isEmpty())
            {
                condition = condition + " and o.order_no = '" + orderNo + "'";
            }
            if (merchantOrderNo != null && !merchantOrderNo.isEmpty())
            {
                condition = condition + " and o.merchant_order_no = '" + merchantOrderNo + "'";
            }

            if (!StringUtil.isNullOrEmpty(businessType))
            {
                condition = condition + " and o.business_type = '" + businessType + "'";
            }
            // condition+=")o )";
            // String pageTotal_sql = "select count(*) from yc_order where 1=1 " + condition;
            // Query query = em.createNativeQuery(pageTotal_sql);
            insidesql = insidesql + condition;

            if (endIndex > 0)
            {
                insidesql = insidesql
                            + " and  o.product_id=p.product_id  order by  o.order_request_time desc,o.order_no desc)o where 1=1 and rownum <= "
                            + endIndex;
            }
            String sql = "select * from (" + insidesql + ") where rn>" + startIndex;
            Query query = em.createNativeQuery(sql);
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List<OrderExportVo> result = new ArrayList<OrderExportVo>();
            List<?> rows = query.getResultList();
            for (Object obj : rows)
            {
                @SuppressWarnings("unchecked")
                Map<String, Object> row = (Map<String, Object>)obj;
                OrderExportVo order = new OrderExportVo();
                BeanUtils.transMap2Bean(row, order);
                result.add(order);
            }
            YcPage<OrderExportVo> ycPage = new YcPage<OrderExportVo>();
            ycPage.setList(result);
            return ycPage;
        }
        catch (Exception e)
        {
            logger.error("queryOrderForExport exception info["
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

    List<OrderNativeVo> converDeliveryToOrderNativeVos(List<OrderNativeVo> orderList)
    {
        try
        {
            Map<String, SearchFilter> filters = null;
            List<OrderNativeVo> result = new ArrayList<OrderNativeVo>();
            for (OrderNativeVo order : orderList)
            {

                filters = new HashMap<String, SearchFilter>();
                filters.put(EntityConstant.Delivery.ORDER_NO, new SearchFilter(
                    EntityConstant.Delivery.ORDER_NO, Operator.EQ, order.getORDERNO()));
                Specification<Delivery> spec_Delivery = DynamicSpecifications.bySearchFilter(
                    filters.values(), Delivery.class);
                List<Delivery> deliverys = deliveryJpaDao.findAll(spec_Delivery, new Sort(
                    Direction.DESC, EntityConstant.Delivery.DELIVERY_ID));
                if (deliverys.size() > 0)
                {
                    order.setDELIVERYID(deliverys.get(0).getDeliveryId() + "");
                    Merchant merchant = merchantService.queryMerchantById(deliverys.get(0).getMerchantId());
                    if (BeanUtils.isNotNull(merchant))
                    {
                        order.setSUPPLYMERCHANT(merchant.getMerchantName());
                    }
                }
                result.add(order);
            }
            return result;
        }
        catch (Exception e)
        {
            logger.error("converDeliveryInfoToOrders exception info["
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

}
