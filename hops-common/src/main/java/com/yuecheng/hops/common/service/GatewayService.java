package com.yuecheng.hops.common.service;

import java.util.Map;

import com.yuecheng.hops.common.exception.HopsException;

public interface GatewayService {
	/**
	 * 发送请求
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> request(Map<String,Object> fields) throws HopsException;
}
