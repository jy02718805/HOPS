package com.yuecheng.hops.transaction.service.scanner;


import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.event.HopsPublisher;
import com.yuecheng.hops.common.event.HopsRequestEvent;
import com.yuecheng.hops.transaction.basic.entity.Notify;
import com.yuecheng.hops.transaction.event.NotifyAgentOrderEvent;
import com.yuecheng.hops.transaction.execution.notify.action.NotifyStatusManagementAction;
import com.yuecheng.hops.transaction.service.notify.NotifyService;
import com.yuecheng.hops.transaction.service.order.impl.OrderServiceImpl;


@Service("agentOrderPendingNotifyScanner")
public class AgentOrderPendingNotifyScanner
{
    private static final Logger          logger = LoggerFactory.getLogger(AgentOrderPendingNotifyScanner.class);

    @Autowired
    private NotifyService                notifyService;

    @Autowired
    private NotifyStatusManagementAction notifyStatusManagementAction;

    @Autowired
    private HopsPublisher                publisher;

    public void sendNotifys()
    {
        logger.debug("订单通知流程[开始]!");
        // 所有需要发货的订单
        List<Notify> notifys = notifyService.findNotifyOrders();
        logger.debug("订单通知流程：修改通知记录状态为通知中！总数[" + notifys.size() + "]条。[开始]");
        List<Long> orderNos = notifyStatusManagementAction.updateNotifyStatusToNOTIFYING(notifys);
        logger.debug("订单通知流程：修改通知记录状态为通知中！总数[" + notifys.size() + "]条。[结束]");

        HopsRequestEvent hopsRequestEvent = new HopsRequestEvent(AgentOrderPendingNotifyScanner.class);

        
        for (Iterator<Long> iterator = orderNos.iterator(); iterator.hasNext();)
        {
            Long orderNo = iterator.next();
            hopsRequestEvent = new NotifyAgentOrderEvent(OrderServiceImpl.class, orderNo);
            publisher.publishRequestEvent(hopsRequestEvent);
        }
        logger.debug("订单通知流程[结束]!");
    }
}
