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

import com.yuecheng.hops.common.event.HopsPublisher;
import com.yuecheng.hops.transaction.TransactionContextUtil;
import com.yuecheng.hops.transaction.service.action.AbstractEventAction;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;
import com.yuecheng.hops.transaction.service.process.OrderReBindProcess;

@Service("orderReBindProcess")
public class OrderReBindProcessImpl implements OrderReBindProcess
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
    private DeliveryManagement deliveryManagement;
    
    @Autowired
    private HopsPublisher publisher;
    
    @Override
    public void execute()
    {
        deliveryStatusFailAction.handleRequest();
        queryStatusEndingAction.handleRequest();//!!!发起事件处理
        orderStatusWaitAction.handleRequest();
        notifyStatusNoneedAction.handleRequest();//!!!逻辑优化
        TransactionContextUtil.copyProperties(ActionContextUtil.getActionContextLocal());
    }

}
