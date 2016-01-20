package com.yuecheng.hops.transaction.service.scanner;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.execution.fakeRule.FakeRuleService;
import com.yuecheng.hops.transaction.service.order.OrderManagement;
import com.yuecheng.hops.transaction.service.order.OrderService;


@Service("pendingFakeOrderScanner")
public class PendingFakeOrderScanner
{
    private static final Logger logger = LoggerFactory.getLogger(PendingFakeOrderScanner.class);

    @Autowired
    private FakeRuleService     fakeRuleService;

    @Autowired
    private OrderService        orderService;

    @Autowired
    private OrderManagement     orderManagement;

    public void updateOrderNotifyStatus()
    {
        logger.warn("预成功订单流程：[开始]!");
        List<Order> orders = orderService.findFakeOrders();
        for (Order order : orders)
        {
            fakeRuleService.updateOrderNotifyStatus(order.getOrderNo());
        }
        logger.warn("预成功订单流程：[结束]!");
    }
}
