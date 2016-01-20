package com.yuecheng.hops.gateway.communication.netty;

/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuecheng.hops.gateway.communication.protocal.SocketServer;

/**
 * Simple SSL chat server modified from {@link TelnetServer}.
 */
public class NettySocketServer implements SocketServer{

	private int port;
	private SSLSupportedPipelineFactory pipelineFactory;
	private boolean tcpNoDelay = true;
	private boolean keepAlive = true;
	private static final Logger logger = LoggerFactory.getLogger(NettySocketServer.class);
    
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public SSLSupportedPipelineFactory getPipelineFactory() {
		return pipelineFactory;
	}

	public void setPipelineFactory(SSLSupportedPipelineFactory pipelineFactory) {
		this.pipelineFactory = pipelineFactory;
	}

	public void setTcpNoDelay(boolean tcpNoDelay) {
		this.tcpNoDelay = tcpNoDelay;
	}

	public void setKeepAlive(boolean keepAlive) {
		this.keepAlive = keepAlive;
	}

	private ServerBootstrap bootstrap;

	public void start() {
		// Configure the server.
		this.bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool()));
		
		// Configure the pipeline factory.
		this.bootstrap.setPipelineFactory(this.pipelineFactory);
		this.bootstrap.setOption("child.tcpNoDelay", this.tcpNoDelay);
		this.bootstrap.setOption("child.keepAlive", this.keepAlive);
		this.bootstrap.bind(new InetSocketAddress(this.port));

		logger.info("success to start the server on port:" + this.port);
	}

	public void stop() {
		this.bootstrap.releaseExternalResources();
	}
}