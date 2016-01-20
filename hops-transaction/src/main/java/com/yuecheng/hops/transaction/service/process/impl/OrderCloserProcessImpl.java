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
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.transaction.TransactionContextUtil;
import com.yuecheng.hops.transaction.service.action.AbstractEventAction;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.process.OrderCloserProcess;

@Service("orderCloserProcess")
public class OrderCloserProcessImpl implements OrderCloserProcess
{
    @Autowired
    @Qualifier("deliveryStatusFailAction")
    private AbstractEventAction deliveryStatusFailAction;
    
    @Autowired
    @Qualifier("queryStatusEndingAction")
    private AbstractEventAction queryStatusEndingAction;
    
    @Autowired
    @Qualifier("orderStatusFailAction")
    private AbstractEventAction orderStatusFailAction;
    
    @Autowired
    @Qualifier("notifyStatusWaitAction")
    private AbstractEventAction notifyStatusWaitAction;

    @Override
    @Transactional
    public void execute()
    {
        deliveryStatusFailAction.handleRequest();
        queryStatusEndingAction.handleRequest();
        orderStatusFailAction.handleRequest();
        notifyStatusWaitAction.handleRequest();
        TransactionContextUtil.copyProperties(ActionContextUtil.getActionContextLocal());
    }

}
