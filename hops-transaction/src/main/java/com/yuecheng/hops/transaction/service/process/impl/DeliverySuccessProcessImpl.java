/*
 * 文件名：DeliveryCreator.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator 修改时间：2015年1月9日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.transaction.service.process.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.event.HopsPublisher;
import com.yuecheng.hops.common.event.HopsRequestEvent;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.transaction.TransactionContextUtil;
import com.yuecheng.hops.transaction.TransactionMapKey;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.event.QueryStatusEndEvent;
import com.yuecheng.hops.transaction.service.action.AbstractEventAction;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;
import com.yuecheng.hops.transaction.service.process.DeliverySuccessProcess;

@Service("deliverySuccessProcess")
public class DeliverySuccessProcessImpl implements DeliverySuccessProcess
{
    @Autowired
    @Qualifier("deliveryStatusSuccessAction") 
    private AbstractEventAction deliveryStatusSuccessAction;
    
    @Autowired
    private DeliveryManagement deliveryManagement;
    
    @Autowired
    private HopsPublisher publisher;
    
    @Override
    public void execute()
    {
        try
        {
            Delivery delivery = (Delivery)ActionContextUtil.getActionContextParam(ActionMapKey.DELIVERY);
            if((Constant.Delivery.DELIVERY_STATUS_FAIL != delivery.getDeliveryStatus() || Constant.Delivery.DELIVERY_STATUS_SUCCESS != delivery.getDeliveryStatus()) && Constant.Delivery.QUERY_FLAG_QUERY_END != delivery.getQueryFlag()){
                deliveryStatusSuccessAction.handleRequest();
            }
            delivery = (Delivery)ActionContextUtil.getActionContextParam(ActionMapKey.DELIVERY);
            delivery = deliveryManagement.findDeliveryById(delivery.getDeliveryId());
            HopsRequestEvent hre = new HopsRequestEvent(DeliverySuccessProcessImpl.class);
            hre = new QueryStatusEndEvent(DeliverySuccessProcessImpl.class, delivery);
            TransactionContextUtil.setTransactionContext(TransactionMapKey.HOPS_REQUEST_EVENT, hre);
            publisher.publishRequestEvent(hre);
//            queryStatusEndingAction.handleRequest();
            TransactionContextUtil.copyProperties(ActionContextUtil.getActionContextLocal());
        }
        catch (HopsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new ApplicationException("transaction002045", new String[]{ExceptionUtil.getStackTraceAsString(e)});
        }
    }

}