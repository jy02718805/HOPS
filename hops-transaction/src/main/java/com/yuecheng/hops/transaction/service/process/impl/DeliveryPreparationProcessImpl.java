/*
 * 文件名：DeliveryPreparationProcessImpl.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年1月10日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.transaction.service.process.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.transaction.TransactionContextUtil;
import com.yuecheng.hops.transaction.service.action.AbstractEventAction;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.process.DeliveryPreparationProcess;

@Service("deliveryPreparationProcess")
public class DeliveryPreparationProcessImpl implements DeliveryPreparationProcess
{
    @Autowired@Qualifier("deliveryAccountingAction")
    private AbstractEventAction deliveryAccountingAction;
    
    @Autowired
    @Qualifier("deliveryStatusSendAction")
    private AbstractEventAction deliveryStatusSendAction;

    @Override
    public void execute()
    {
        deliveryAccountingAction.handleRequest();
        deliveryStatusSendAction.handleRequest();
        TransactionContextUtil.copyProperties(ActionContextUtil.getActionContextLocal());
    }

}
