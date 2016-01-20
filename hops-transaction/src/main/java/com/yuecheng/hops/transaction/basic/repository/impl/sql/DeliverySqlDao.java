package com.yuecheng.hops.transaction.basic.repository.impl.sql;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.parameter.entity.ParameterConfiguration;
import com.yuecheng.hops.parameter.service.ParameterConfigurationService;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.basic.entity.vo.DeliveryStatisticsVo;
import com.yuecheng.hops.transaction.basic.repository.DeliveryDao;
import com.yuecheng.hops.transaction.basic.repository.OracleSql;


@Service
public class DeliverySqlDao implements DeliveryDao
{
    private static Logger logger = LoggerFactory.getLogger(DeliverySqlDao.class);

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ParameterConfigurationService parameterConfigurationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(DeliverySqlDao.class);

    @Override
    public List<Delivery> selectDeliveryByIntervalTime(long intervalTime, Integer status)
    {
        try
        {
            String sql = "select yd.* from yc_delivery yd,yc_order yo where yd.order_no=yo.order_no and yo.order_status not in (3,4,5) and yd.delivery_status="
                         + status
                         + " and yd.DELIVERY_START_TIME<(sysdate - interval '"
                         + intervalTime + "' MINUTE)";
            Query query = em.createNativeQuery(sql, Delivery.class);
            List<Delivery> deliveryList = query.getResultList();
            if (!deliveryList.isEmpty())
            {
                LOGGER.info("查找yc_delivery状态为0并且yc_order中状态不等于3、4、5 ； DeliveryJPADaoImpl---selectDeliveryByIntervalTime---deliveryList.size()="
                            + deliveryList.size());
            }
            else
            {
                LOGGER.info("没有符合发货状态为待付款，订单状态等于3（成功）、4（失败）、5（部分成功）");
            }
            return deliveryList;
        }
        catch (Exception e)
        {
            logger.error("selectDeliveryByIntervalTime exception info[" + e + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

    @Override
    public List<Delivery> findSendOrders(Integer deliveryStatus)
    {
        ParameterConfiguration parameterConfiguration = parameterConfigurationService.getParameterConfigurationByKey(Constant.ParameterConfiguration.SCANNER_SEND_NUM);
        int scannerSendNum = 0;
        if (BeanUtils.isNull(parameterConfiguration))
        {
            scannerSendNum = 15;
        }
        else
        {
            scannerSendNum = Integer.valueOf(parameterConfiguration.getConstantValue());
        }
        try
        {
            String sql = "select d.* from yc_delivery d where d.delivery_Status = :delivery_Status"
                         + " and d.pre_delivery_time<= sysdate and rownum <= :scannerSendNum";
            Query query = em.createNativeQuery(sql, Delivery.class);
            query.setParameter("delivery_Status", deliveryStatus);
            query.setParameter("scannerSendNum", scannerSendNum);
            List<Delivery> deliveryList = query.getResultList();
            return deliveryList;
        }
        catch (Exception e)
        {
            logger.error("findSendOrders exception info[" + e + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

    @Override
    public List<Delivery> findQueryOrders(Integer deliveryStatus, Integer queryFlag)
    {
        ParameterConfiguration parameterConfiguration = parameterConfigurationService.getParameterConfigurationByKey(Constant.ParameterConfiguration.SCANNER_QUERY_NUM);
        int scannerQueryNum = 0;
        if (BeanUtils.isNull(parameterConfiguration))
        {
            scannerQueryNum = 15;
        }
        else
        {
            scannerQueryNum = Integer.valueOf(parameterConfiguration.getConstantValue());
        }
        try
        {
            String sql = "select d.* from yc_delivery d where d.delivery_Status = :deliveryStatus"
                         + " and d.query_flag = :queryFlag and d.next_query_time <= sysdate and rownum <= :scannerQueryNum";
            Query query = em.createNativeQuery(sql, Delivery.class);
            query.setParameter("deliveryStatus", deliveryStatus);
            query.setParameter("queryFlag", queryFlag);
            query.setParameter("scannerQueryNum", scannerQueryNum);
            List<Delivery> deliveryList = query.getResultList();
            return deliveryList;
        }
        catch (Exception e)
        {
            logger.error("findQueryOrders exception info[" + e + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

    public List<Delivery> findUnfinishedDeliveryList(Long orderNo)
    {
        try
        {
            String sql = "select d.* from yc_delivery d where d.order_no = :orderNo and d.delivery_status <> :deliverySuccessStatus and d.delivery_status <> :deliveryFailStatus";
            Query query = em.createNativeQuery(sql, Delivery.class).setMaxResults(30);
            query.setParameter("orderNo", orderNo);
            query.setParameter("deliverySuccessStatus", Constant.Delivery.DELIVERY_STATUS_SUCCESS);
            query.setParameter("deliveryFailStatus", Constant.Delivery.DELIVERY_STATUS_FAIL);
            List<Delivery> deliveryList = query.getResultList();
            return deliveryList;
        }
        catch (Exception e)
        {
            logger.error("findUnfinishedDeliveryList exception info[" + e + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

    @Override
    public List<BigDecimal> getDeliveryMerchantIdByOrderNo(Long orderNo)
    {
        try
        {
            String sql = "select yd.merchant_id from yc_delivery yd where yd.order_no=:orderNo";
            Query query = em.createNativeQuery(sql);
            query.setParameter("orderNo", orderNo);
            List<BigDecimal> deliveryList = query.getResultList();
            return deliveryList;
        }
        catch (Exception e)
        {
            logger.error("selectDeliveryByIntervalTime exception info[" + e + "]");
            throw ExceptionUtil.throwException(e);
        }
    }
    
    @Override
    public Delivery findDeliveryBySupplyOrderNo(Long merchantId, String supplyMerchantOrderNo)
    {
        try
        {
            String sql = "select yd.* from yc_delivery yd where yd.merchant_id=:merchantId and yd.supply_Merchant_Order_No = :supplyMerchantOrderNo ";
            Query query = em.createNativeQuery(sql,Delivery.class);
            query.setParameter("supplyMerchantOrderNo", supplyMerchantOrderNo);
            query.setParameter("merchantId", merchantId);
            List<Delivery> deliverys = query.getResultList();
            if(deliverys.size() > 0)
            {
                return deliverys.get(0);
            }
            else
            {
                return null;
            }
        }
        catch (Exception e)
        {
            logger.error("findDeliveryBySupplyOrderNo exception info[" + e + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public DeliveryStatisticsVo statisticsDeliveryInfo(String beginDate,
    		String endDate, String deliveryStatus, String carrierInfo, String province, String supplymerchant, String agentmerchant,
    		String supply_no, String agent_no,  String parValue, String usercode, String orderNo, String businessType)
    {
        try
        {
        	 String condition = StringUtil.initString();

             if (!StringUtils.isBlank(deliveryStatus))
             {

                 condition = condition + " and yd.delivery_status = '" + deliveryStatus + "' ";

             }
             if (!StringUtils.isBlank(orderNo))
             {

                 condition = condition + " and yd.order_no = '" + orderNo + "' ";

             }
             if (!StringUtils.isBlank(carrierInfo))
             {
                 condition = condition + " and yo.ext1 = '" + carrierInfo + "'";
             }
             if (!StringUtils.isBlank(province))
             {
                 condition = condition + " and yo.ext2 = '" + province + "'";
             }

             if (!StringUtils.isBlank(supplymerchant))
             {
                 condition = condition + " and yd.merchant_id = '" + supplymerchant + "'";
             }

             if (!StringUtils.isBlank(agentmerchant))
             {
                 condition = condition + " and yo.merchant_id = '" + agentmerchant + "'";
             }

             if (!StringUtils.isBlank(supply_no))
             {
                 condition = condition + " and yd.supply_Merchant_Order_No = '" + supply_no
                             + "'";
             }
             
             if (!StringUtils.isBlank(parValue))
             {
                 condition = condition + " and yd.product_face = '" + parValue
                             + "'";
             }
             
             if (!StringUtils.isBlank(usercode))
             {
                 condition = condition + " and yd.user_code = '" + usercode
                             + "'";
             }

             if (!StringUtils.isBlank(agent_no))
             {
                 condition = condition + " and yo.MERCHANT_ORDER_NO = '" + agent_no + "'";
             }
             
             if (!StringUtils.isBlank(businessType))
             {
                 condition = condition + " and yo.business_type = '" + businessType + "'";
             }

             if (beginDate != null && !beginDate.isEmpty())
             {
                 // to_date('"+dateStr+"','yyyy-mm-dd hh24:mi:ss')";
                 condition = condition + " and yd.delivery_start_time >= to_date('" + beginDate
                             + "','yyyy-mm-dd hh24:mi:ss')";
             }
             if (endDate != null && !endDate.isEmpty())
             {
                 condition = condition + " and yd.delivery_start_time <= to_date('" + endDate
                             + "','yyyy-mm-dd hh24:mi:ss')";
             }
             
             Query queryTotal  =  em.createNativeQuery(OracleSql.Delivery.STATISTICSDELIVERY_TOTAL_SQL+condition);
             queryTotal.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
             Map<String, Object> queryTotalRow = ((Map<String, Object>)queryTotal.getSingleResult());
             
            Query query = em.createNativeQuery(OracleSql.Delivery.STATISTICSDELIVERY_SQL+condition);
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            DeliveryStatisticsVo deliveryStatisticsVo = new DeliveryStatisticsVo();
            Map<String, Object> row = ((Map<String, Object>)query.getSingleResult());
            row.put("PRODUCTFACE", queryTotalRow.get("PRODUCTFACE"));
            BeanUtils.transMap2Bean(row, deliveryStatisticsVo);
            return deliveryStatisticsVo;
        }
        catch (Exception e)
        {
            logger.error("statisticsDeliveryInfo exception info[" + e + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

    @Override
    public void updateDeliveryStatus(Delivery delivery, Integer originalStatus) throws Exception
    {
        try
        {
            String sql = "update yc_delivery d set d.delivery_status = ?,d.delivery_finish_time= sysdate,d.delivery_result= ?,d.success_fee= ? where d.delivery_id = ? and d.delivery_status= ?";
            em.createNativeQuery(sql).setParameter(1, delivery.getDeliveryStatus()).setParameter(2, delivery.getDeliveryResult()).setParameter(3, delivery.getSuccessFee()).setParameter(4, delivery.getDeliveryId()).setParameter(5, originalStatus).executeUpdate();
        }
        catch(PersistenceException e)
        {
            logger.error("updateDeliveryStatus exception info[" + ExceptionUtil.throwException(e) + "]");
            throw new ApplicationException("transaction002007");
        }
    }
    
    @Override
    public void updateQueryFlag(Delivery delivery, Integer originalStatus) throws Exception
    {
        try
        {
            String sql = "update yc_delivery d set d.query_flag = ? where d.delivery_id = ? and d.query_flag= ?";
            em.createNativeQuery(sql).setParameter(1, delivery.getQueryFlag()).setParameter(2, delivery.getDeliveryId()).setParameter(3, originalStatus).executeUpdate();
        }
        catch(PersistenceException e)
        {
            logger.error("updateQueryFlag exception info[" + ExceptionUtil.throwException(e) + "]");
            throw new ApplicationException("transaction002007");
        }
    }
    
    @Override
    public List<BigDecimal> findCloseDeliverys(){
        List<BigDecimal> deliveryIds = new ArrayList<BigDecimal>();
        try
        {
            String sql = "select d.DELIVERY_ID from yc_order o,yc_delivery d where o.order_status = :orderStatus and o.order_no = d.order_no and d.delivery_status=:deliveryStatus";
            Query query = em.createNativeQuery(sql);
            query.setParameter("orderStatus", Constant.OrderStatus.WAIT_RECHARGE);
            query.setParameter("deliveryStatus", Constant.Delivery.DELIVERY_STATUS_SENDED);
            deliveryIds = query.getResultList();
        }
        catch (Exception e)
        {
            logger.error("findCloseDeliverys exception info[" + ExceptionUtil.throwException(e) + "]");
        }
        return deliveryIds;
    }
    
    @Override
    public Integer countUnFailDeliveryNum(Long orderNo){
        try
        {
            String sql = "select count(0) from yc_delivery d where d.order_no = :orderNo and d.delivery_status != " + Constant.Delivery.DELIVERY_STATUS_FAIL;
            Query query = em.createNativeQuery(sql);
            query.setParameter("orderNo", orderNo);
            BigDecimal unFailDeliveryNum = (BigDecimal)query.getSingleResult();
            return unFailDeliveryNum.intValue();
        }
        catch (Exception e)
        {
            logger.error("findUnfinishedDeliveryList exception info[" + e + "]");
            throw ExceptionUtil.throwException(e);
        }
    }
}
