package com.yuecheng.hops.transaction.service.delivery.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.DecimalPlaces;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.BigDecimalUtil;
import com.yuecheng.hops.common.utils.DateUtil;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.parameter.entity.SupplyDupNumRule;
import com.yuecheng.hops.transaction.TransactionContextUtil;
import com.yuecheng.hops.transaction.TransactionMapKey;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.basic.entity.vo.DeliveryStatisticsVo;
import com.yuecheng.hops.transaction.basic.repository.DeliveryJpaDao;
import com.yuecheng.hops.transaction.basic.repository.impl.sql.DeliverySqlDao;
import com.yuecheng.hops.transaction.mq.producer.DeliveryQueryProducerService;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;
import com.yuecheng.hops.transaction.service.process.QueryStartUpProcess;


@Service("deliveryManagement")
public class DeliveryManagementImpl implements DeliveryManagement
{
    private static Logger logger = LoggerFactory.getLogger(DeliveryManagementImpl.class);

    @Autowired
    private DeliveryJpaDao deliveryJpaDao;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private DeliverySqlDao deliverySqlDao;

    @Autowired
    private QueryStartUpProcess queryStartUpProcess;

    @Autowired
    private DeliveryQueryProducerService deliveryQueryProducerService;

    @PersistenceContext
    private EntityManager em;

    private final static Integer SUCCESS = 1;

    @SuppressWarnings({"unchecked", "unused", "rawtypes"})
    public YcPage<Map<String, Object>> queryDeliveryList(String beginDate, String endDate,
                                                         String deliverySatus, String carrierNo,
                                                         String provinceNO, String supplyMerchant,
                                                         String agentMerchant,
                                                         String supply_order_no,
                                                         String agent_order_no, String order_no,
                                                         String parValue, String usercode,
                                                         int pageNumber, int pageSize,
                                                         String queryFlag, String businessType)
    {
        YcPage<Map<String, Object>> ycPage = new YcPage<Map<String, Object>>();
        try
        {
            int startIndex = pageNumber * pageSize - pageSize;
            int endIndex = startIndex + pageSize;

            String querySQLString = "select /*+full(yo)*/yd.delivery_id,"
                                    + "yd.order_no,"
                                    + "yd.par_value par_value,"
                                    + "yo.order_request_time,"
                                    + "yd.merchant_id suppy_id,"
                                    + "yd.merchant_order_no supply_order_no,"
                                    + "yo.merchant_id agent_id,"
                                    + "yo.business_type business_type,"
                                    + "yo.merchant_order_no agent_order_no,"
                                    + "yo.user_code,"
                                    + "yo.ext1 carrierno,"
                                    + "yo.ext2 provinceno,"
                                    + "yo.ext3,"
                                    + "yo.order_fee,"
                                    + "yo.order_sales_fee,"
                                    + "yd.cost_discount,"
                                    + "yd.cost_fee,"
                                    + "yd.delivery_start_time,"
                                    + "yd.delivery_finish_time,"
                                    + "yd.delivery_status,"
                                    + "yd.delivery_result,"
                                    + "yd.query_Flag,"
                                    + "yd.query_msg,"
                                    + "yo.merchant_name agent_name,yd.PRODUCT_FACE,yd.SUCCESS_FEE from yc_delivery yd, yc_order yo"
                                    + " where yd.order_no = yo.order_no ";
            String condition = StringUtil.initString();

            if (!StringUtils.isBlank(deliverySatus))
            {

                condition = condition + " and yd.delivery_status = '" + deliverySatus + "' ";

            }
            if (!StringUtils.isBlank(carrierNo))
            {
                condition = condition + " and yo.ext1 = '" + carrierNo + "'";
            }
            if (!StringUtils.isBlank(provinceNO))
            {
                condition = condition + " and yo.ext2 = '" + provinceNO + "'";
            }

            if (!StringUtils.isBlank(supplyMerchant))
            {
                condition = condition + " and yd.merchant_id = '" + supplyMerchant + "'";
            }

            if (!StringUtils.isBlank(agentMerchant))
            {
                condition = condition + " and yo.merchant_id = '" + agentMerchant + "'";
            }

            if (!StringUtils.isBlank(supply_order_no))
            {
                condition = condition + " and yd.supply_Merchant_Order_No = '" + supply_order_no
                            + "'";
            }

            if (!StringUtils.isBlank(parValue))
            {
                condition = condition + " and yd.product_face = '" + parValue + "'";
            }

            if (!StringUtils.isBlank(usercode))
            {
                condition = condition + " and yd.user_code = '" + usercode + "'";
            }

            if (!StringUtils.isBlank(agent_order_no))
            {
                condition = condition + " and yo.MERCHANT_ORDER_NO = '" + agent_order_no + "'";
            }

            if (!StringUtils.isBlank(businessType))
            {
                condition = condition + " and yo.business_type = '" + businessType + "'";
            }

            if (!StringUtils.isBlank(order_no))
            {
                condition = condition + " and yo.ORDER_NO = '" + order_no + "'";
            }
            
            if (!StringUtils.isBlank(queryFlag))
            {
                condition = condition + " and yd.query_Flag = '" + queryFlag + "'";
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

            condition = condition + " order by yd.delivery_start_time desc,yd.delivery_id desc";

            String pageTotal_sql = "select count(*) from yc_delivery yd, yc_order yo"
                                   + " where yd.order_no = yo.order_no " + condition;
            Query query = em.createNativeQuery(pageTotal_sql);
            BigDecimal total = (BigDecimal)query.getSingleResult();

            Double pageTotal = BigDecimalUtil.divide(total, new BigDecimal(pageSize),
                DecimalPlaces.ZERO.value(), BigDecimal.ROUND_CEILING).doubleValue();

            querySQLString = querySQLString + condition;
            if (endIndex > 0)
            {
                querySQLString = "select t.*,rownum rn from (" + querySQLString
                                 + " )t where 1 = 1 and rownum <= " + endIndex;
            }

            String sql = "select * from (" + querySQLString + ") where rn>" + startIndex;
            query = em.createNativeQuery(sql);
            List<Map<String, Object>> deliveryList = new ArrayList<Map<String, Object>>();
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

            List list = query.getResultList();
            for (Object item : list)
            {
                Map<String, Object> iteMap = converDeliveryInfo(((Map<String, Object>)item));
                deliveryList.add((Map<String, Object>)item);

            }
            ycPage.setList(deliveryList);
            ycPage.setCountTotal(total.intValue());
            ycPage.setPageTotal(pageTotal.intValue());
            return ycPage;
        }
        catch (Exception e)
        {
            logger.error("queryDeliveryList exception info[" + e + "]");
            throw ExceptionUtil.throwException(e);
        }

    }

    @Override
    public List<Delivery> findDeliveryByOrderNo(Long orderNo)
    {
        List<Delivery> historyDeliveryList = deliveryJpaDao.findDeliveryByOrderNo(orderNo);
        return historyDeliveryList;
    }

    @Override
    public List<Delivery> findDeliveryByParams(Long orderNo, Long merchantId,
                                               Integer deliveryStatus)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.Delivery.ORDER_NO, new SearchFilter(
            EntityConstant.Delivery.ORDER_NO, Operator.EQ, orderNo));
        if (BeanUtils.isNotNull(merchantId))
        {
            filters.put(EntityConstant.Delivery.MERCHANT_ID, new SearchFilter(
                EntityConstant.Delivery.MERCHANT_ID, Operator.EQ, merchantId));
        }
        filters.put(EntityConstant.Delivery.DELIVERY_STATUS, new SearchFilter(
            EntityConstant.Delivery.DELIVERY_STATUS, Operator.EQ, deliveryStatus));
        Specification<Delivery> spec_Delivery = DynamicSpecifications.bySearchFilter(
            filters.values(), Delivery.class);
        List<Delivery> deliverys = deliveryJpaDao.findAll(spec_Delivery);
        return deliverys;
    }

    @Override
    @Transactional
    public Delivery save(Delivery delivery)
    {
        delivery = deliveryJpaDao.save(delivery);
        return delivery;
    }

    @Override
    @Transactional
    public Delivery findDeliveryById(Long deliveryId)
    {
        Delivery delivery = deliveryJpaDao.findOne(deliveryId);
        return delivery;
    }

    @Override
    public Delivery findDeliveryByIdNoTransaction(Long deliveryId)
    {
        Delivery delivery = deliveryJpaDao.findOne(deliveryId);
        return delivery;
    }

    public Map<String, Object> converDeliveryInfo(Map<String, Object> item)
    {
        if (BeanUtils.isNotNull(item.get("SUPPY_ID"))
            && StringUtil.isNotBlank(item.get("SUPPY_ID").toString()))
        {
            Merchant merchant = merchantService.queryMerchantById(Long.valueOf(item.get("SUPPY_ID").toString()));
            if (BeanUtils.isNotNull(merchant))
            {
                item.put("SUPPY_NAME", merchant.getMerchantName());
            }
            else
            {
                item.put("SUPPY_NAME", "");
            }
        }
        return item;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Delivery> findUnfinishedDeliveryList(Long orderNo)
    {
        List<Delivery> deliverys = deliverySqlDao.findUnfinishedDeliveryList(orderNo);
        return deliverys;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<BigDecimal> getDeliveryMerchantIdByOrderNo(Long orderNo)
    {
        List<BigDecimal> result = deliverySqlDao.getDeliveryMerchantIdByOrderNo(orderNo);
        return result;
    }
    
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer countUnFailDeliveryNum(Long orderNo)
    {
        Integer deliverySize = deliverySqlDao.countUnFailDeliveryNum(orderNo);
        return deliverySize;
    }

    @Override
    @Transactional
    public void save(List<Delivery> deliverys)
    {
        deliveryJpaDao.save(deliverys);
    }

    @Override
    @Transactional
    public Integer updateDeliveryNextQuery(Long deliveryId, Integer queryflag, Date queryTime)
    {
        Integer result = deliveryJpaDao.updateDeliveryNextQuery(deliveryId, queryflag, queryTime);
        return result;
    }

    @Override
    public Delivery findDeliveryBySupplyOrderNo(Long merchantId, String supplyMerchantOrderNo)
    {
        Delivery delivery = deliverySqlDao.findDeliveryBySupplyOrderNo(merchantId,
            supplyMerchantOrderNo);
        return delivery;
    }

    @Override
    public DeliveryStatisticsVo statisticsDeliveryInfo(String beginDate, String endDate,
                                                       String deliveryStatus, String carrierInfo,
                                                       String province, String supplymerchant,
                                                       String agentmerchant, String supply_no,
                                                       String agent_no, String parValue,
                                                       String usercode, String orderNo,
                                                       String businessType)
    {
        DeliveryStatisticsVo deliveryStatisticsVo = deliverySqlDao.statisticsDeliveryInfo(
            beginDate, endDate, deliveryStatus, carrierInfo, province, supplymerchant,
            agentmerchant, supply_no, agent_no, parValue, usercode, orderNo, businessType);
        return deliveryStatisticsVo;
    }

    @Override
    public void updateDeliveryStatus(Delivery delivery, Integer originalStatus)
        throws Exception
    {
        Integer result = deliveryJpaDao.updateDeliveryStatus(delivery.getDeliveryStatus(),
            delivery.getDeliveryResult(), delivery.getSuccessFee(), delivery.getQueryMsg(),
            delivery.getDeliveryId(), originalStatus);
        if (SUCCESS != result)
        {
            throw new ApplicationException("transaction001033", new String[] {
                delivery.getDeliveryId().toString(), originalStatus.toString(),
                delivery.getDeliveryStatus().toString()});
        }
    }

    @Override
    public void updateDeliveryStatus(Integer targetStatus, Integer originalStatus, Long deliveryId)
        throws Exception
    {
        Integer result = deliveryJpaDao.updateDeliveryStatus(deliveryId, targetStatus,
            originalStatus);
        if (SUCCESS != result)
        {
            throw new ApplicationException("transaction001033", new String[] {
                targetStatus.toString(), originalStatus.toString(), deliveryId.toString()});
        }
    }

    @Override
    public void updateQueryFlag(Delivery delivery, Integer originalStatus)
        throws Exception
    {
        Integer result = deliveryJpaDao.updateQueryFlag(delivery.getQueryFlag(),
            delivery.getDeliveryId(), originalStatus);
        if (SUCCESS != result)
        {
            throw new ApplicationException("transaction001035", new String[] {
                delivery.getDeliveryId().toString(), originalStatus.toString(),
                delivery.getDeliveryStatus().toString()});
        }
    }

    @Override
    public void updateQueryFlag(Delivery delivery, Integer originalStatus, Date queryTime)
        throws Exception
    {
        Integer result = deliveryJpaDao.updateQueryFlag(delivery.getQueryFlag(),
            delivery.getDeliveryId(), originalStatus, queryTime, delivery.getDeliveryResult());
        if (SUCCESS != result)
        {
            throw new ApplicationException("transaction001035", new String[] {
                delivery.getDeliveryId().toString(), originalStatus.toString(),
                delivery.getDeliveryStatus().toString()});
        }
    }

    @Override
    public void updateSupplyMerchantOrderNo(String supplyMerchantOrderNo, Long deliveryId)
    {
        deliveryJpaDao.updateSupplyMerchantOrderNo(supplyMerchantOrderNo, deliveryId);
    }

    @Override
    public void beginQuery(Delivery delivery)
    {
        if (Constant.Delivery.DELIVERY_STATUS_FAIL != delivery.getDeliveryStatus()
            && Constant.Delivery.DELIVERY_STATUS_SUCCESS != delivery.getDeliveryStatus())
        {
            ActionContextUtil.init();
            ActionContextUtil.setActionContext(ActionMapKey.DELIVERY, delivery);
            queryStartUpProcess.execute();
            delivery = (Delivery)TransactionContextUtil.getTransactionContextParam(TransactionMapKey.DELIVERY);
            Calendar startDate = Calendar.getInstance();
            Calendar nowDate = Calendar.getInstance();
            startDate.setTime(delivery.getNextQueryTime());
            nowDate.setTime(new Date());
            long delay = startDate.getTimeInMillis() - nowDate.getTimeInMillis();
            logger.debug("延迟 " + delay + "毫秒 发起查询事件！");
            deliveryQueryProducerService.sendMessage(delivery.getDeliveryId(), delay);
        }
    }

    @Override
    public void closeDelivery(Long deliveryId)
    {
        try
        {
            deliveryJpaDao.closeDelivery(Constant.Delivery.QUERY_FLAG_QUERY_END,
                Constant.Delivery.DELIVERY_STATUS_FAIL, deliveryId);
        }
        catch (Exception e)
        {
            logger.error("closeDelivery has error[" + ExceptionUtil.getStackTraceAsString(e) + "]");
        }

    }

    @Override
    public void updateParams(String supplyMerchantOrderNo, String deliveryResult, Long deliveryId,
                             String queryMsg)
    {
        deliveryJpaDao.updateParams(supplyMerchantOrderNo, deliveryResult, deliveryId, queryMsg);
    }

    @Override
    public void addQueryTimes(Long queryTimes, Long deliveryId)
    {
        deliveryJpaDao.addQueryTimes(queryTimes, deliveryId);
    }

    @Override
    public void updateQueryMsg(Long devliveryId, String queryMsg)
    {
        deliveryJpaDao.updateQueryMsg(devliveryId, queryMsg);
    }

    @Override
    public boolean checkSupplyDupNumRule(String userCode, SupplyDupNumRule supplyDupNumRule)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.Delivery.MERCHANT_ID, new SearchFilter(
            EntityConstant.Delivery.MERCHANT_ID, Operator.EQ, supplyDupNumRule.getMerchantId()));
        filters.put(EntityConstant.Delivery.USER_CODE, new SearchFilter(
            EntityConstant.Delivery.USER_CODE, Operator.EQ, userCode));

        Date searchDate = DateUtil.subTime(supplyDupNumRule.getDateUnit(),
            supplyDupNumRule.getDateInterval());
        filters.put(EntityConstant.Delivery.DELIVERY_START_TIME, new SearchFilter(
            EntityConstant.Delivery.DELIVERY_START_TIME, Operator.GTE, searchDate));
        Specification<Delivery> spec_Delivery = DynamicSpecifications.bySearchFilter(
            filters.values(), Delivery.class);
        List<Delivery> deliverys = deliveryJpaDao.findAll(spec_Delivery);
        if (BeanUtils.isNotNull(deliverys))
        {
            if (supplyDupNumRule.getSendTimes() <= deliverys.size())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return true;
        }
    }

    @Override
    public List<Long> batchUpdateQueryFlag(String deliveryIds, Integer queryFlag)
    {
        // TODO Auto-generated method stub
        String[] deliveryids = deliveryIds.split(Constant.StringSplitUtil.DECODE);
        List<Long> result = new ArrayList<Long>();
        for (int i = 0; i < deliveryids.length; i++ )
        {
            try
            {
                deliveryJpaDao.updateQueryFlag(Long.valueOf(deliveryids[i]), queryFlag);
                result.add(Long.valueOf(deliveryids[i]));
            }
            catch (Exception e)
            {
                logger.error("batchUpdateQueryFlag is fail! deliveryId" + deliveryids[i]);
            }
        }
        return result;
    }
}