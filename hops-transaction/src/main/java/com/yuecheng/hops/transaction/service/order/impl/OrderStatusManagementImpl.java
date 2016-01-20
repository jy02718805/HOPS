package com.yuecheng.hops.transaction.service.order.impl;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.basic.repository.OrderJpaDao;
import com.yuecheng.hops.transaction.service.StatusManagementService;
import com.yuecheng.hops.transaction.service.order.OrderManagement;
import com.yuecheng.hops.transaction.service.order.OrderStatusManagement;


@Service("orderStatusManagement")
public class OrderStatusManagementImpl implements OrderStatusManagement
{
    @Autowired
    @Qualifier("orderStatusManagementServiceImpl")
    private StatusManagementService orderStatusManagementServiceImpl;
    
    @Autowired
    private OrderJpaDao orderJpaDao;

    @Autowired
    private OrderManagement orderManagement;
    
    private final static Integer SUCCESS = 1;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderStatusManagementImpl.class);

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Long> updateOrderStatusToRECHARGING(List<Order> orders)
    {
        List<Long> orderNos = new ArrayList<Long>();
        for (Iterator<Order> iterator = orders.iterator(); iterator.hasNext();)
        {
            Order order = iterator.next();
            try
            {
                int result = orderJpaDao.updateOrderStatus(order.getOrderNo(), Constant.OrderStatus.RECHARGING, order.getOrderStatus());
                if(SUCCESS == result){
                    orderNos.add(order.getOrderNo());
                }
            }
            catch (Exception e)
            {
                LOGGER.error("[OrderStatusManagementImpl : updateOrderStatusToRECHARGING()] [异常: "  + e.getMessage() + "]");
            }
        }
        return orderNos;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Long> updateOrderStatusToSuccessPartRECHARGING(List<Order> orders)
    {
        List<Long> orderNos = new ArrayList<Long>();
        for (Iterator<Order> iterator = orders.iterator(); iterator.hasNext();)
        {
            Order order = iterator.next();
            try
            {
                int result = orderJpaDao.updateOrderStatus(order.getOrderNo(), Constant.OrderStatus.SUCCESS_PART_RECHARGING, order.getOrderStatus());
                if(SUCCESS == result){
                    orderNos.add(order.getOrderNo());
                }
            }
            catch (Exception e)
            {
                LOGGER.error("[OrderStatusManagementImpl : updateOrderStatusToSuccessPartRECHARGING()] [异常: "
                    + e.getMessage() + "]");
            }
        }
        return orderNos;
    }
}
