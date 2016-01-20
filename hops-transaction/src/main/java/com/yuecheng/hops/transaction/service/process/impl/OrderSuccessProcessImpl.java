/*
 * 文件名：DeliveryCreator.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator 修改时间：2015年1月9日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.transaction.service.process.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.event.HopsPublisher;
import com.yuecheng.hops.common.event.HopsRequestEvent;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.transaction.TransactionContextUtil;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.event.BeginNotifyEvent;
import com.yuecheng.hops.transaction.service.action.AbstractEventAction;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.process.OrderSuccessProcess;

@Service("orderSuccessProcess")
public class OrderSuccessProcessImpl implements OrderSuccessProcess
{
    
    @Autowired
    @Qualifier("orderStatusSuccessAction") 
    private AbstractEventAction orderStatusSuccessAction;
    
    @Autowired
    private HopsPublisher publisher;
    
    @Override
    public void execute()
    {
        try
        {
            Order order = (Order)ActionContextUtil.getActionContextParam(ActionMapKey.ORDER);
            orderStatusSuccessAction.handleRequest();
            HopsRequestEvent hre = new HopsRequestEvent(OrderSuccessProcessImpl.class);
            hre = new BeginNotifyEvent(OrderSuccessProcessImpl.class, order.getOrderNo());
            publisher.publishRequestEvent(hre);
            TransactionContextUtil.copyProperties(ActionContextUtil.getActionContextLocal());
        }
        catch (HopsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new ApplicationException("transaction002046", new String[]{ExceptionUtil.getStackTraceAsString(e)});
        }
    }

}