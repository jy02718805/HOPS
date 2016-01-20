package com.yuecheng.hops.transaction.service.scanner;


import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.event.HopsPublisher;
import com.yuecheng.hops.common.event.HopsRequestEvent;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.event.BindAgentOrderEvent;
import com.yuecheng.hops.transaction.service.order.OrderService;
import com.yuecheng.hops.transaction.service.order.OrderStatusManagement;


@Service("agentOrderPengdingBindScanner")
public class AgentOrderPengdingBindScanner
{
    private static final Logger   logger = LoggerFactory.getLogger(AgentOrderPengdingBindScanner.class);

    @Autowired
    private OrderService          orderService;

    @Autowired
    private OrderStatusManagement orderStatusManagement;

    @Autowired
    private HopsPublisher         publisher;
    
    public void bindOrders()
    {
        logger.debug("订单绑定流程[开始]");
        List<Order> orders = orderService.findBindOrders();
        logger.debug("订单绑定流程:修改绑定订单状态为发货中！总数[" + orders.size() + "]条。[开始]");
        // 将所有订单状态修改成正在充值状态
        List<Long> orderNos = orderStatusManagement.updateOrderStatusToRECHARGING(orders);
        logger.debug("订单绑定流程:修改绑定订单状态为发货中！总数[" + orders.size() + "]条。[结束]");
        HopsRequestEvent hre = new HopsRequestEvent(AgentOrderPengdingBindScanner.class);
        for (Iterator<Long> iterator = orderNos.iterator(); iterator.hasNext();)
        {
            Long orderNo = iterator.next();
            hre = new BindAgentOrderEvent(AgentOrderPengdingBindScanner.class, orderNo);
            publisher.publishRequestEvent(hre);
        }
        logger.debug("订单绑定流程[结束]");
    }
}
