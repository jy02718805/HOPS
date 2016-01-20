/*
 * 文件名：DeliveryCreator.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator 修改时间：2015年1月9日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.transaction.service.process.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.transaction.TransactionContextUtil;
import com.yuecheng.hops.transaction.service.action.AbstractEventAction;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.process.DeliveryCreatorProcess;

@Service("deliveryCreatorProcess")
public class DeliveryCreatorProcessImpl implements DeliveryCreatorProcess
{
    @Autowired
    @Qualifier("createDeliveryAction") 
    private AbstractEventAction createDeliveryAction;
    
    @Autowired
    @Qualifier("deliveryStatusSendingAction") 
    private AbstractEventAction deliveryStatusSendingAction;
    
    @Override
    @Transactional
    public void execute()
    {
        createDeliveryAction.handleRequest();
        deliveryStatusSendingAction.handleRequest();
        TransactionContextUtil.copyProperties(ActionContextUtil.getActionContextLocal());
    }

}