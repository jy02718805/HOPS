package com.yuecheng.hops.transaction.listener;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.event.QueryStatusEndEvent;
import com.yuecheng.hops.transaction.service.action.AbstractEventAction;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;
import com.yuecheng.hops.transaction.service.process.QueryStartUpProcess;


@Component("queryStatusEndListener")
public class QueryStatusEndListener implements ApplicationListener<QueryStatusEndEvent>
{
    @Autowired
    @Qualifier("queryStatusEndingAction") 
    private AbstractEventAction queryStatusEndingAction;
    
    @Autowired
    private DeliveryManagement deliveryManagement;
    
    @Autowired
    private QueryStartUpProcess queryStartUpProcess;

    @Override
    @Async
    public void onApplicationEvent(QueryStatusEndEvent event)
    {
        Delivery delivery = event.getDelivery();
        try
        {
            ActionContextUtil.init();
            ActionContextUtil.setActionContext(ActionMapKey.DELIVERY, delivery);
            if((Constant.Delivery.DELIVERY_STATUS_FAIL == delivery.getDeliveryStatus() || Constant.Delivery.DELIVERY_STATUS_SUCCESS == delivery.getDeliveryStatus()) && Constant.Delivery.QUERY_FLAG_QUERY_END != delivery.getQueryFlag())
            {
                queryStatusEndingAction.handleRequest();
            }
        }
        catch (Exception e)
        {
            delivery = deliveryManagement.findDeliveryById(delivery.getDeliveryId());
            if(delivery.getDeliveryStatus() != Constant.Delivery.DELIVERY_STATUS_FAIL || delivery.getDeliveryStatus() != Constant.Delivery.DELIVERY_STATUS_SUCCESS){
                deliveryManagement.beginQuery(delivery);
            }
        }
    }
}
