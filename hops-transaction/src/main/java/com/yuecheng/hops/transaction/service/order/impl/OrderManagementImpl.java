package com.yuecheng.hops.transaction.service.order.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.product.entity.airtimes.AirtimeProduct;
import com.yuecheng.hops.product.service.ProductPageQuery;
import com.yuecheng.hops.transaction.basic.entity.AgentOrderKey;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.basic.entity.vo.DeliveryVo;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderVo;
import com.yuecheng.hops.transaction.basic.repository.AgentOrderKeyDao;
import com.yuecheng.hops.transaction.basic.repository.AgentOrderKeyJpaDao;
import com.yuecheng.hops.transaction.basic.repository.OrderJpaDao;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;
import com.yuecheng.hops.transaction.service.order.OrderManagement;


@Service("orderManagement")
public class OrderManagementImpl implements OrderManagement
{
    static Logger logger = LoggerFactory.getLogger(OrderManagementImpl.class);

    @Autowired
    private OrderJpaDao orderJpaDao;

    @Autowired
    private ProductPageQuery productPageQuery;

    @Autowired
    private DeliveryManagement deliveryManagement;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private AgentOrderKeyDao agentOrderKeyDao;

    @Autowired
    private AgentOrderKeyJpaDao agentOrderKeyJpaDao;

    private final static Integer SUCCESS = 1;

    @Override
    @Transactional
    public Order save(Order order)
    {
        return orderJpaDao.save(order);
    }

    @Override
    public OrderVo getOrderVoById(Long orderNo)
    {
        Order order = orderJpaDao.findOne(orderNo);
        OrderVo orderVo = new OrderVo();
        if (order != null)
        {
            try
            {
                BeanUtils.copyProperties(orderVo, order);
                orderVo.setOrderRequestTime(order.getOrderRequestTime());
                orderVo.setOrderFinishTime(order.getOrderFinishTime());
            }
            catch (Exception e)
            {
                throw ExceptionUtil.throwException(new ApplicationException("transaction001027",
                    new String[] {e.toString()}));
            }
            List<Delivery> deliverys = deliveryManagement.findDeliveryByOrderNo(orderNo);
            List<DeliveryVo> deliveryList = new ArrayList<DeliveryVo>();
            for (Delivery delivery : deliverys)
            {
                DeliveryVo deliveryVo = new DeliveryVo();
                AirtimeProduct ap = productPageQuery.queryAirtimeProductById(delivery.getProductId());
                if (BeanUtils.isNull(ap))
                {
                    delivery.setProductName("[" + order.getExt1() + "][" + order.getExt2() + "]["
                                            + order.getExt3() + "][" + order.getExt4() + "]");
                }
                else
                {
                    delivery.setProductName(ap.getProductName());
                }
                try
                {
                    BeanUtils.copyProperties(deliveryVo, delivery);
                    deliveryVo.setDeliveryStartTime(delivery.getDeliveryStartTime());
                    deliveryVo.setDeliveryResult(delivery.getDeliveryResult());
                    deliveryVo.setNextQueryTime(delivery.getNextQueryTime());
                }
                catch (Exception e)
                {
                    throw ExceptionUtil.throwException(new ApplicationException(
                        "transaction002031", e));
                }
                Merchant supplyMerchant = merchantService.queryMerchantById(delivery.getMerchantId());
                deliveryVo.setMerchantName(supplyMerchant.getMerchantName());
                deliveryList.add(deliveryVo);
            }
            orderVo.setDeliverys(deliveryList);

            BigDecimal orderSuccessFee = null;
            if (order.getOrderSuccessFee() != null)
            {
                orderSuccessFee = order.getOrderSuccessFee();
            }
            else
            {
                orderSuccessFee = new BigDecimal(0);
            }
            BigDecimal orderWaitFee = order.getOrderFee().subtract(orderSuccessFee);
            orderVo.setOrderWaitFee(orderWaitFee);
        }
        return orderVo;
    }

    @Override
    @Transactional
    public Order findOne(Long orderNo)
    {
        Order order = orderJpaDao.findOne(orderNo);
        return order;
    }

    @Override
    public Order findOneNoTransactional(Long orderNo)
    {
        Order order = orderJpaDao.findOne(orderNo);
        return order;
    }

    @Override
    @Transactional
    public Order findOrderByMerchantOrderNo(Long merchantId, String merchantOrderNo)
    {
        Order order = orderJpaDao.getOrderByMerchantOrderNo(merchantId, merchantOrderNo);
        return order;
    }

    @Override
    public Boolean checkIsExit(Long merchantId, String merchantOrderNo)
    {
        List<AgentOrderKey> keys = agentOrderKeyJpaDao.findAgentOrderKeyByParams(merchantId,
            merchantOrderNo);
        if (keys.size() <= 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public void saveAgentOrderKey(Long merchantId, String merchantOrderNo)//!!!区分具体Exception
        throws Exception
    {
        try
        {
            agentOrderKeyDao.saveKey(merchantId, merchantOrderNo);
        }
        catch (Exception e)
        {
            throw e;
        }

    }

    @Override
    public void updateManualFlag(Long orderNo, Integer originalStatus, Integer targerStatus)
        throws Exception
    {
        Integer result = orderJpaDao.updateOrderManualFlag(orderNo, targerStatus, originalStatus);
        if (SUCCESS != result)
        {
            throw new ApplicationException("transaction002033", new String[] {orderNo.toString(),
                targerStatus.toString(), originalStatus.toString()});
        }
    }

    @Override
    public void updateManualFlag(Long orderNo, Integer targerStatus)
    {
        orderJpaDao.updateOrderManualFlag(orderNo, targerStatus);
    }

    @Override
    @Transactional
    public void updateOrderNotifyStatus(Long orderNo, int notifyStatus)
    {
        orderJpaDao.updateOrderNotifyStatus(notifyStatus, orderNo);
    }

    @Override
    public void orderAnalysts(Long orderNo, Integer originalOrderStatus)
    {
        Order order = orderJpaDao.findOne(orderNo);
        order.setOrderSuccessFee(null);
        order.setOrderFinishTime(null);
        order.setOrderStatus(originalOrderStatus);
        order = orderJpaDao.save(order);
        List<Delivery> deliverys = deliveryManagement.findUnfinishedDeliveryList(orderNo);
        for (Delivery delivery : deliverys)
        {
            delivery.setQueryFlag(Constant.Delivery.QUERY_FLAG_QUERYING);
            deliveryManagement.beginQuery(delivery);
        }
    }

    @Override
    public void updateOrderSuccess(Order order, Integer originalStatus)
        throws Exception
    {
        Integer result = orderJpaDao.updateOrderSuccess(order.getOrderNo(),
            order.getOrderStatus(), originalStatus, order.getOrderSuccessFee());
        if (SUCCESS != result)
        {
            throw new ApplicationException("transaction001034", new String[] {
                order.getOrderNo().toString(), originalStatus.toString(),
                order.getOrderStatus().toString()});
        }
    }

    @Override
    public Integer updateOrderReason(Integer preSuccessStatus,String orderReason,Long orderNo)
    {
        Integer result = orderJpaDao.updateOrderReason(preSuccessStatus, orderReason, orderNo);
        return result;
    }

}