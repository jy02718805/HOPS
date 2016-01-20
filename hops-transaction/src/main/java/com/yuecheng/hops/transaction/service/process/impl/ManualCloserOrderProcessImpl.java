/*
 * 文件名：OrderCloserProcessImpl.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年1月9日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.transaction.service.process.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.event.HopsPublisher;
import com.yuecheng.hops.transaction.TransactionContextUtil;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.service.action.AbstractEventAction;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.process.ManualCloserOrderProcess;

@Service("manualCloserOrderProcess")
public class ManualCloserOrderProcessImpl implements ManualCloserOrderProcess
{
    @Autowired
    @Qualifier("deliveryStatusFailAction")
    private AbstractEventAction deliveryStatusFailAction;
    
    @Autowired
    @Qualifier("queryStatusEndingAction")
    private AbstractEventAction queryStatusEndingAction;
    
    @Autowired
    @Qualifier("orderStatusWaitAction")
    private AbstractEventAction orderStatusWaitAction;
    
    @Autowired
    @Qualifier("notifyStatusNoneedAction")
    private AbstractEventAction notifyStatusNoneedAction;
    
    @Autowired
    @Qualifier("orderStatusRechargingAction")
    private AbstractEventAction orderStatusRechargingAction;
    
    @Autowired
    @Qualifier("createDeliveryAction") 
    private AbstractEventAction createDeliveryAction;
    
    @Autowired
    @Qualifier("deliveryStatusSendingAction") 
    private AbstractEventAction deliveryStatusSendingAction;
    
    @Autowired
    private HopsPublisher publisher;
    
    @Override
    public void execute()
    {
        deliveryStatusFailAction.handleRequest();
        queryStatusEndingAction.handleRequest();
        Order order = (Order)ActionContextUtil.getActionContextParam(ActionMapKey.ORDER);
        if(order.getOrderStatus() != Constant.OrderStatus.WAIT_RECHARGE){
            orderStatusWaitAction.handleRequest();
        }
        orderStatusRechargingAction.handleRequest();
        notifyStatusNoneedAction.handleRequest();
        createDeliveryAction.handleRequest();
        deliveryStatusSendingAction.handleRequest();
        TransactionContextUtil.copyProperties(ActionContextUtil.getActionContextLocal());
    }

}
