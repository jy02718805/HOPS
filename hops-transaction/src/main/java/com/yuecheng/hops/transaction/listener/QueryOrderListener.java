package com.yuecheng.hops.transaction.listener;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.yuecheng.hops.transaction.DefaultTransactionRequestImpl;
import com.yuecheng.hops.transaction.TransactionCodeConstant;
import com.yuecheng.hops.transaction.TransactionRequest;
import com.yuecheng.hops.transaction.TransactionResponse;
import com.yuecheng.hops.transaction.TransactionService;
import com.yuecheng.hops.transaction.event.QueryOrderEvent;


@Component("queryOrderListener")
public class QueryOrderListener implements ApplicationListener<QueryOrderEvent>
{

    @Autowired
    @Qualifier("transactionService")
    private TransactionService service;

    @Override
    @Async
    public void onApplicationEvent(QueryOrderEvent event)
    {
        Long deliveryId = event.getDeliveryId();
        TransactionRequest request = new DefaultTransactionRequestImpl();
        request.setTransactionCode(TransactionCodeConstant.SUPPLY_QUERY_ORDER_CODE);
        request.setTransactionTime(new Date());
        request.setParameter("deliveryId", deliveryId);
        TransactionResponse response = service.doTransaction(request);
        // Map<String,Object> response_fields = response.getResponseMap();
    }
}
