package com.yuecheng.hops.transaction.listener;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.common.event.HopsPublisher;
import com.yuecheng.hops.common.event.HopsRequestEvent;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.transaction.basic.entity.Notify;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.event.BeginNotifyEvent;
import com.yuecheng.hops.transaction.event.NotifyAgentOrderEvent;
import com.yuecheng.hops.transaction.service.action.AbstractEventAction;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.notify.NotifyService;
import com.yuecheng.hops.transaction.service.order.OrderManagement;


@Component("beginNotifyListener")
public class BeginNotifyListener implements ApplicationListener<BeginNotifyEvent>
{
    @Autowired
    private NotifyService notifyService;
    
    @Autowired
    private OrderManagement orderManagement;
    
    @Autowired
    @Qualifier("notifyStatusWaitAction") 
    private AbstractEventAction notifyStatusWaitAction;
    
    @Autowired
    @Qualifier("notifyStatusNotifyingAction") 
    private AbstractEventAction notifyStatusNotifyingAction;
    
    @Autowired
    private HopsPublisher publisher;

    @Override
    @Async
    @Transactional
    public void onApplicationEvent(BeginNotifyEvent event)
    {
        Long orderNo = event.getOrderNo();
        Order order = orderManagement.findOne(orderNo);
        Notify notify = notifyService.findNotifyByOrderNo(orderNo);
        if(BeanUtils.isNotNull(notify)){
            ActionContextUtil.setActionContext(ActionMapKey.ORDER, order);
            notifyStatusWaitAction.handleRequest();
            notify = (Notify)ActionContextUtil.getActionContextParam(ActionMapKey.NOTIFY);
            ActionContextUtil.init();
            ActionContextUtil.setActionContext(ActionMapKey.NOTIFY, notify);
            notifyStatusNotifyingAction.handleRequest();
            
            HopsRequestEvent hopsRequestEvent = new NotifyAgentOrderEvent(BeginNotifyListener.class, orderNo);
            publisher.publishRequestEvent(hopsRequestEvent);
        }
    }
}
