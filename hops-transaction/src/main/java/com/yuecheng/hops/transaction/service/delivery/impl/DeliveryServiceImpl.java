package com.yuecheng.hops.transaction.service.delivery.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.account.entity.CurrencyAccountBalanceHistory;
import com.yuecheng.hops.account.service.CurrencyAccountBalanceHistoryService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.injection.service.MerchantRequestService;
import com.yuecheng.hops.parameter.service.ParameterConfigurationService;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.basic.repository.DeliveryDao;
import com.yuecheng.hops.transaction.basic.repository.DeliveryJpaDao;
import com.yuecheng.hops.transaction.service.delivery.DeliveryService;
import com.yuecheng.hops.transaction.service.order.OrderManagement;


@Service("deliveryService")
public class DeliveryServiceImpl implements DeliveryService
{

    private static final Logger logger = LoggerFactory.getLogger(DeliveryServiceImpl.class);

    @Autowired
    private OrderManagement orderManagement;

    @Autowired
    private MerchantRequestService merchantRequestService;

    @Autowired
    private ParameterConfigurationService parameterConfigurationService;
    
    @Autowired
    private CurrencyAccountBalanceHistoryService currencyAccountBalanceHistoryService;

    @Autowired
    private DeliveryJpaDao deliveryJpaDao;

    @Autowired
    private DeliveryDao deliveryDao;
    
    @Override
    public List<Long> findCloseDeliverys(){
        List<BigDecimal> deliveryIds = deliveryDao.findCloseDeliverys();
        List<Long> result = new ArrayList<Long>();
        for(BigDecimal deliveryId : deliveryIds){
            Delivery delivery = deliveryJpaDao.findOne(deliveryId.longValue());
            List<CurrencyAccountBalanceHistory> forzenList = currencyAccountBalanceHistoryService.getFrozenBalanceHistoryByOrderNo(delivery.getOrderNo(), Constant.AccountBalanceOperationType.ACT_BAL_OPR_FORZEN);
            List<CurrencyAccountBalanceHistory> unforzenList = currencyAccountBalanceHistoryService.getFrozenBalanceHistoryByOrderNo(delivery.getOrderNo(), Constant.AccountBalanceOperationType.ACT_BAL_OPR_UNFORZEN);
            Integer forzenSize = BeanUtils.isNotNull(forzenList)?forzenList.size():0;
            Integer unforzenSize = BeanUtils.isNotNull(unforzenList)?unforzenList.size():0;
            if(forzenSize.compareTo(unforzenSize) == 0){
                result.add(delivery.getDeliveryId());
            }
        }
        return result;
    }

    @Override
    public Date calcPreDeliveryTime(Long orderNo, Long merchantId)
    {
        try
        {
            Order order = orderManagement.findOne(orderNo);
            Date createDeliveryTime = order.getOrderRequestTime();
            Date now = new Date();
            Date preDeliveryTime = merchantRequestService.getNextQueryDate(createDeliveryTime, now, merchantId, Constant.Interface.INTERFACE_TYPE_SEND_ORDER);
            return preDeliveryTime;
        }
        catch (Exception e)
        {
            logger.debug("计算发货时间失败，失败原因：[" + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw new ApplicationException("transaction002036");
        }
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS)
    public List<Delivery> findSendOrders()
    {
        List<Delivery> deliverys = deliveryDao.findSendOrders(
            Constant.Delivery.DELIVERY_STATUS_WAIT);
        return deliverys;
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS)
    public List<Delivery> findQueryOrders()
    {
        // 所有需要发货的订单
        List<Delivery> deliverySended = deliveryDao.findQueryOrders(Constant.Delivery.DELIVERY_STATUS_SENDED, Constant.Delivery.QUERY_FLAG_WAIT_QUERY);
        return deliverySended;
    }

    /*
     * <p>Title: selectDeliveryByIntervalTime</p> <p>Description:根据状态和间隔时间查询发货记录 </p>
     * @param intervalTime
     * @param status
     * @return
     * @see
     * com.yuecheng.hops.transaction.service.delivery.DeliveryService#selectDeliveryByIntervalTime
     * (long, long)
     */
    @Override
    public List<Delivery> selectDeliveryByIntervalTime(long intervalTime, Integer status)
    {
        // TODO Auto-generated method stub
        return deliveryDao.selectDeliveryByIntervalTime(intervalTime, status);
    }
}
