package com.yuecheng.hops.transaction.listener;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;
import com.yuecheng.hops.transaction.DefaultTransactionRequestImpl;
import com.yuecheng.hops.transaction.TransactionCodeConstant;
import com.yuecheng.hops.transaction.TransactionMapKey;
import com.yuecheng.hops.transaction.TransactionRequest;
import com.yuecheng.hops.transaction.TransactionResponse;
import com.yuecheng.hops.transaction.TransactionService;
import com.yuecheng.hops.transaction.event.SendOrderEvent;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;


@Component("sendOrderListener")
public class SendOrderListener implements ApplicationListener<SendOrderEvent>
{

    @Autowired
    @Qualifier("transactionService")
    private TransactionService service;

    @Autowired
    private DeliveryManagement deliveryManagement;
    
    @Override
    @Async
    public void onApplicationEvent(SendOrderEvent event)
    {
        Long deliveryId = event.getDeliveryId();
        int businessType = event.getBusinessType();
        SupplyProductRelation supplyProductRelation = event.getSupplyProductRelation();
        
        TransactionRequest request = new DefaultTransactionRequestImpl();
        request.setTransactionCode(TransactionCodeConstant.SEND_ORDER_CODE);
        request.setTransactionTime(new Date());
        request.setParameter(TransactionMapKey.DELIVERY_ID, deliveryId);
        request.setParameter(TransactionMapKey.BUSINESS_TYPE, businessType);
        request.setParameter(TransactionMapKey.SUPPLY_PRODUCT_RELATION, supplyProductRelation);
        TransactionResponse response = service.doTransaction(request);
        request.clear();
        // Map<String,Object> response_fields = response.getResponseMap();
    }
}
