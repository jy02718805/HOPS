package com.yuecheng.hops.transaction.listener;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.transaction.DefaultTransactionRequestImpl;
import com.yuecheng.hops.transaction.TransactionCodeConstant;
import com.yuecheng.hops.transaction.TransactionRequest;
import com.yuecheng.hops.transaction.TransactionResponse;
import com.yuecheng.hops.transaction.TransactionService;
import com.yuecheng.hops.transaction.event.BindAgentOrderEvent;


@Component("bindAgentOrderListener")
public class BindAgentOrderListener implements ApplicationListener<BindAgentOrderEvent>
{

    @Autowired
    @Qualifier("transactionService")
    private TransactionService service;
    
    @Override
    @Async
    public void onApplicationEvent(BindAgentOrderEvent event)
    {
        Long orderNo = event.getOrderNo();
        TransactionRequest request = new DefaultTransactionRequestImpl();
        request.setTransactionCode(TransactionCodeConstant.BIND_ORDER_CODE);
        request.setTransactionTime(new Date());
        request.setParameter(EntityConstant.Order.ORDER_NO, orderNo);
        TransactionResponse response = service.doTransaction(request);
        request.clear();
    }
}
