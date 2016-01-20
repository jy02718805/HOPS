package com.yuecheng.hops.transaction.service.scanner;


import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.event.HopsPublisher;
import com.yuecheng.hops.common.event.HopsRequestEvent;
import com.yuecheng.hops.parameter.entity.ParameterConfiguration;
import com.yuecheng.hops.parameter.service.ParameterConfigurationService;
import com.yuecheng.hops.product.service.AgentProductRelationService;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.event.BindAgentOrderEvent;
import com.yuecheng.hops.transaction.execution.devliery.DeliveryCreatorAction;
import com.yuecheng.hops.transaction.execution.product.SupplyProductTransaction;
import com.yuecheng.hops.transaction.service.builder.AgentOrderReceptor;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;
import com.yuecheng.hops.transaction.service.delivery.DeliveryService;
import com.yuecheng.hops.transaction.service.order.OrderManagement;
import com.yuecheng.hops.transaction.service.order.OrderService;
import com.yuecheng.hops.transaction.service.order.OrderStatusManagement;


@Service("partSuccessOrderScanner")
public class PartSuccessOrderScanner
{
    private static final Logger           logger = LoggerFactory.getLogger(PartSuccessOrderScanner.class);

    @Autowired
    private ParameterConfigurationService parameterConfigurationService;

    @Autowired
    private OrderService                  orderService;

    @Autowired
    private OrderManagement               orderManagement;

    @Autowired
    private OrderStatusManagement         orderStatusManagement;

    @Autowired
    private DeliveryService               deliveryService;

    @Autowired
    private DeliveryManagement            deliveryManagement;

    @Autowired
    private AgentProductRelationService   agentProductRelationService;

    @Autowired
    private SupplyProductTransaction      supplyProductTransaction;

    @Autowired
    private DeliveryCreatorAction         deliveryCreatorAction;

    @Autowired
    private HopsPublisher                 publisher;

    public void bindPartSuccessOrders()
    {
        logger.debug("in PartSuccessDeliveryExecutive!");
        ParameterConfiguration hc = parameterConfigurationService.getParameterConfigurationByKey(Constant.ParameterConfiguration.DELIVERY_TIMES_CONSTANT);
        int deliveryTimes = Integer.parseInt(hc.getConstantValue());
        List<Order> orders = orderService.findPartSuccessOrders();
        logger.debug("in PartSuccessDeliveryExecutive:begin update orderStatus to SuccessPartRECHARGING!");
        // 将所有订单状态修改成正在充值状态
        List<Long> orderNos = orderStatusManagement.updateOrderStatusToSuccessPartRECHARGING(orders);
        logger.debug("in PartSuccessDeliveryExecutive:end update orderStatus to SuccessPartRECHARGING!");

        for (Iterator<Long> iterator = orderNos.iterator(); iterator.hasNext();)
        {
            Long orderNo = iterator.next();
            List<Delivery> deliverys = deliveryManagement.findDeliveryByOrderNo(orderNo);
            if (deliverys.size() <= deliveryTimes)
            {
                HopsRequestEvent hre = new HopsRequestEvent(AgentOrderReceptor.class);
                hre = new BindAgentOrderEvent(AgentOrderReceptor.class, orderNo);
                publisher.publishRequestEvent(hre);
            }
            else
            {
                // 超过次数，将部分成功绑定假上游。进入人工补单流程
                // bindService.bindFakeSupplyMerchant(order);
            }
        }

        logger.debug("out PartSuccessDeliveryExecutive:");
    }

}
