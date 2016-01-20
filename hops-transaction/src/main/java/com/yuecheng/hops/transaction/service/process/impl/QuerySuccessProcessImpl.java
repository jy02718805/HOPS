/*
 * 文件名：DeliveryCreator.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator 修改时间：2015年1月9日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.transaction.service.process.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.common.event.HopsPublisher;
import com.yuecheng.hops.common.event.HopsRequestEvent;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.transaction.TransactionContextUtil;
import com.yuecheng.hops.transaction.TransactionMapKey;
import com.yuecheng.hops.transaction.basic.entity.Notify;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.event.NotifyAgentOrderEvent;
import com.yuecheng.hops.transaction.service.action.AbstractEventAction;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.notify.NotifyService;
import com.yuecheng.hops.transaction.service.process.QuerySuccessProcess;

@Service("querySuccessProcess")
public class QuerySuccessProcessImpl implements QuerySuccessProcess
{
    @Autowired
    @Qualifier("deliveryStatusSuccessAction") 
    private AbstractEventAction deliveryStatusSuccessAction;
    
    @Autowired
    @Qualifier("orderStatusSuccessAction") 
    private AbstractEventAction orderStatusSuccessAction;
    
    @Autowired
    @Qualifier("queryStatusEndingAction") 
    private AbstractEventAction queryStatusEndingAction;
    
    @Autowired
    @Qualifier("notifyStatusWaitAction") 
    private AbstractEventAction notifyStatusWaitAction;
    
    @Autowired
    @Qualifier("notifyStatusNotifyingAction") 
    private AbstractEventAction notifyStatusNotifyingAction;
    
    @Autowired
    private HopsPublisher publisher;
    
    @Autowired
    private NotifyService notifyService;
    
    @Override
    @Transactional
    public void execute()
    {
        deliveryStatusSuccessAction.handleRequest();
        queryStatusEndingAction.handleRequest();
        Order order = (Order)ActionContextUtil.getActionContextParam(ActionMapKey.ORDER);
        orderStatusSuccessAction.handleRequest();
        Notify notify = notifyService.findNotifyByOrderNo(order.getOrderNo());
        if(BeanUtils.isNotNull(notify)){
            notifyStatusWaitAction.handleRequest();
            ActionContextUtil.setActionContext(ActionMapKey.NOTIFY, notify);
            notifyStatusNotifyingAction.handleRequest();
            
            HopsRequestEvent hopsRequestEvent = new HopsRequestEvent(QuerySuccessProcessImpl.class);
            hopsRequestEvent = new NotifyAgentOrderEvent(QuerySuccessProcessImpl.class, notify.getOrderNo());
            
            TransactionContextUtil.setTransactionContext(TransactionMapKey.HOPS_REQUEST_EVENT, hopsRequestEvent);
        }
        TransactionContextUtil.copyProperties(ActionContextUtil.getActionContextLocal());
    }

}