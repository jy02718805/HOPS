package com.yuecheng.hops.transaction.service.scanner;


import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.event.HopsPublisher;
import com.yuecheng.hops.common.event.HopsRequestEvent;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.event.QueryOrderEvent;
import com.yuecheng.hops.transaction.execution.devliery.DeliveryStatusManagementAction;
import com.yuecheng.hops.transaction.service.delivery.DeliveryService;


@Service("supplyOrderPendingQueryScanner")
public class SupplyOrderPendingQueryScanner
{
    private static Logger                  logger = LoggerFactory.getLogger(SupplyOrderPendingQueryScanner.class);

    @Autowired
    private DeliveryStatusManagementAction deliveryStatusManagementAction;

    @Autowired
    private DeliveryService                deliveryService;

    @Autowired
    private HopsPublisher                  publisher;

    public void querySupplyOrders()
    {
        logger.debug("查询供货商订单信息流程[开始]!");
        // 查询需要发货的订单
        List<Delivery> deliverySended = deliveryService.findQueryOrders();
        logger.debug("查询供货商订单信息流程：修改发货记录查询状态为查询中！deliverySended[" + deliverySended + "]。[开始]");
        List<Long> deliveryIds = deliveryStatusManagementAction.updateDeliveryQueryFlagToQUERYING(deliverySended);
        logger.debug("查询供货商订单信息流程：修改发货记录查询状态为查询中！deliverySended[" + deliverySended + "]。[结束]");;
        HopsRequestEvent hre = new HopsRequestEvent(SupplyOrderPendingSendScanner.class);
        for (Iterator<Long> iterator = deliveryIds.iterator(); iterator.hasNext();)
        {
            Long deliveryId = iterator.next();
            hre = new QueryOrderEvent(SupplyOrderPendingSendScanner.class, deliveryId);
            publisher.publishRequestEvent(hre);
        }
        logger.debug("查询供货商订单信息流程[结束]!");
    }
}
