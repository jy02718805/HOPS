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
import com.yuecheng.hops.transaction.service.action.query.QueryStatusNeedQueryAction;
import com.yuecheng.hops.transaction.service.process.SupplyAmtLowProcess;

@Service("supplyAmtLowProcess")
public class SupplyAmtLowProcessImpl implements SupplyAmtLowProcess
{
    @Autowired
    @Qualifier("queryStatusNeedQueryAction")
    private QueryStatusNeedQueryAction queryStatusNeedQueryAction;
    
    @Autowired
    @Qualifier("deliveryStatusSendAction")
    private AbstractEventAction deliveryStatusSendAction;
    
    @Override
    public void execute()
    {
        queryStatusNeedQueryAction.handleRequest();
        deliveryStatusSendAction.handleRequest();
        TransactionContextUtil.copyProperties(ActionContextUtil.getActionContextLocal());
    }

}
