package com.yuecheng.hops.gateway.transaction;

import java.util.Map;

public interface TransactionRemoteService {
	public Map<String,Object> execute(Map<String,Object> requestFields) throws Exception ;
}
