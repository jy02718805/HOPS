package com.yuecheng.hops.gateway.transaction;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.gateway.GatewayConstant;
import com.yuecheng.hops.gateway.GatewayContextUtil;
import com.yuecheng.hops.transaction.DefaultTransactionRequestImpl;
import com.yuecheng.hops.transaction.TransactionRequest;
import com.yuecheng.hops.transaction.TransactionResponse;
import com.yuecheng.hops.transaction.TransactionService;

@Service("transactionRemoteServiceExecutor")
public class TransactionRemoteServiceExecutor implements TransactionRemoteService {
	
	@Autowired
	private TransactionService transactionService;

	@Override
	public Map<String,Object> execute(Map<String,Object> request_fields) throws Exception {
		TransactionRequest request = new DefaultTransactionRequestImpl();
		request.setTransactionCode((String)GatewayContextUtil.getParameter(GatewayConstant.TRANSACTION_CODE));
		request.setTransactionTime(new Date());
		for(Map.Entry<String, Object> entry: request_fields.entrySet()) {
			request.setParameter(entry.getKey(),entry.getValue());
		}
		TransactionResponse response = transactionService.doTransaction(request);
		return response;
	}

}
