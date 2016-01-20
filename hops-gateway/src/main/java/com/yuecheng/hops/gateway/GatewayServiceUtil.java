package com.yuecheng.hops.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.yuecheng.hops.common.utils.SpringUtils;
import com.yuecheng.hops.gateway.communication.netty.NettySocketClient;
import com.yuecheng.hops.gateway.communication.netty.http.NettyHttpClient;
import com.yuecheng.hops.gateway.communication.protocal.SocketClient;
import com.yuecheng.hops.gateway.communication.protocal.http.HttpClient;

@Component
public class GatewayServiceUtil {
	@Autowired
	private NettyHttpClient nettyHttpClient;
	
	@Autowired
	private HttpClient httpClient;
	
	@Autowired
	private NettySocketClient nettySocketClient;
	
	@Autowired
	private SocketClient socketClient;

	public NettyHttpClient getHttpclient() {
		ApplicationContext ctx = SpringUtils.getApplicationContext();
		nettyHttpClient = (NettyHttpClient)ctx.getBean("nettyHttpClient");
		return nettyHttpClient;
	}

	public HttpClient getHttpsClient() {
		ApplicationContext ctx = SpringUtils.getApplicationContext();
		httpClient = (HttpClient)ctx.getBean("httpClient");
		return httpClient;
	}

	public NettySocketClient getSocketClient() {
		ApplicationContext ctx = SpringUtils.getApplicationContext();
		nettySocketClient = (NettySocketClient)ctx.getBean("nettySocketClient");
		return nettySocketClient;
	}

	public SocketClient getSslSocketClient() {
		ApplicationContext ctx = SpringUtils.getApplicationContext();
		socketClient = (SocketClient)ctx.getBean("socketClient");
		return socketClient;
	}
}
