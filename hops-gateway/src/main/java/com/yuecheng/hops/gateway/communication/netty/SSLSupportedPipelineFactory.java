package com.yuecheng.hops.gateway.communication.netty;

import static org.jboss.netty.channel.Channels.pipeline;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.net.ssl.SSLEngine;

import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.codec.http.HttpChunkAggregator;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;
import org.jboss.netty.handler.execution.ExecutionHandler;
import org.jboss.netty.handler.ssl.SslHandler;
import org.jboss.netty.handler.stream.ChunkedWriteHandler;

import com.yuecheng.hops.common.utils.SpringUtils;
import com.yuecheng.hops.gateway.communication.netty.http.HttpServerHandler;
import com.yuecheng.hops.gateway.security.ssl.SSLContextFactory;

/**
 * Creates a newly configured {@link ChannelPipeline} for a new channel.
 */
public class SSLSupportedPipelineFactory implements ChannelPipelineFactory {
	
	private Map<String, ChannelHandler> channelHandlerMap = new LinkedHashMap<String, ChannelHandler>(10);

	public Map<String, ChannelHandler> getChannelHandlerMap() {
		return channelHandlerMap;
	}

	public void setChannelHandlerMap(
			Map<String, ChannelHandler> channelHandlerMap) {
		this.channelHandlerMap = channelHandlerMap;
	}
	
	private SSLContextFactory sslContextFactory;

	private boolean sslEnable;

	private boolean sslUserClientMode;

	private boolean needClientAuth;

	public boolean isSslUserClientMode() {
		return sslUserClientMode;
	}

	public void setSslUserClientMode(boolean sslUserClientMode) {
		this.sslUserClientMode = sslUserClientMode;
	}

	public boolean isNeedClientAuth() {
		return needClientAuth;
	}

	public void setNeedClientAuth(boolean needClientAuth) {
		this.needClientAuth = needClientAuth;
	}

	public boolean isSslEnable() {
		return sslEnable;
	}

	public void setSslEnable(boolean sslEnable) {
		this.sslEnable = sslEnable;
	}

	public SSLContextFactory getSslContextFactory() {
		return sslContextFactory;
	}

	public void setSslContextFactory(SSLContextFactory sslContextFactory) {
		this.sslContextFactory = sslContextFactory;
	}

	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = pipeline();
		if (this.sslEnable) {
			sslContextFactory.init();
			SSLEngine engine = this.sslContextFactory.createSSLServerContext()
					.createSSLEngine();
			engine.setUseClientMode(this.sslUserClientMode);
			engine.setWantClientAuth(this.needClientAuth);
			pipeline.addLast("ssl", new SslHandler(engine));
//			SSLEngine engine = HttpSslContextFactory.getServerContext().createSSLEngine();
//			engine.setUseClientMode(this.sslUserClientMode); //非客户端模式
//			engine.setWantClientAuth(this.needClientAuth);
//			pipeline.addLast("ssl", new SslHandler(engine));
		}
		pipeline.addLast("httpRequestDecoder", new HttpRequestDecoder());
		pipeline.addLast("aggregator", new HttpChunkAggregator(1048576));
        pipeline.addLast("httpResponseEncoder", new HttpResponseEncoder());
        pipeline.addLast("chunkedWriteHandler", new ChunkedWriteHandler());
        pipeline.addLast("executionHandler", (ExecutionHandler)SpringUtils.getBean("executionHandler"));
        pipeline.addLast("httpServerHandler", (HttpServerHandler)SpringUtils.getBean("httpServerHandler"));
        
		
//		for (Entry<String, ChannelHandler> entry : channelHandlerMap.entrySet()) {
//			pipeline.addLast(entry.getKey(),
//					(org.jboss.netty.channel.ChannelHandler) entry.getValue());
//		}

		return pipeline;
	}
}