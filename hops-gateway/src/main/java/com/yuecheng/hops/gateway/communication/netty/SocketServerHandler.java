package com.yuecheng.hops.gateway.communication.netty;
//package com.yuecheng.hops.gateway.netty.socket.server.example;
//
///*
// * Copyright 2012 The Netty Project
// *
// * The Netty Project licenses this file to you under the Apache License,
// * version 2.0 (the "License"); you may not use this file except in compliance
// * with the License. You may obtain a copy of the License at:
// *
// *   http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
// * License for the specific language governing permissions and limitations
// * under the License.
// */
//
//import java.io.IOException;
//import java.util.logging.Logger;
//
//import org.jboss.netty.channel.ChannelHandlerContext;
//import org.jboss.netty.channel.ChannelStateEvent;
//import org.jboss.netty.channel.ExceptionEvent;
//import org.jboss.netty.channel.MessageEvent;
//import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
//import org.jboss.netty.channel.group.ChannelGroup;
//import org.jboss.netty.channel.group.DefaultChannelGroup;
//import org.jboss.netty.handler.ssl.NotSslRecordException;
//
//import com.yuecheng.hops.gateway.shjf.up.BusinessProcess;
//
///**
// * Handles a server-side channel.
// */
//public class SocketServerHandler extends SimpleChannelUpstreamHandler {
//
//	private static final Logger logger = Logger
//			.getLogger(SocketServerHandler.class.getName());
//
//	private static final ChannelGroup channels = new DefaultChannelGroup();
//
//	private BusinessProcess businessProcess;
//
//	public static ChannelGroup getChannels() {
//		return channels;
//	}
//
//	public void setBusinessProcess(BusinessProcess businessProcess) {
//		this.businessProcess = businessProcess;
//	}
//
//	@Override
//	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
//			throws Exception {
//		System.out.println("Thread:"+Thread.currentThread().getId()+" success to to connect to:"+e.getChannel().getId()+"!");
//		super.channelConnected(ctx, e);
//	}
//
//	@Override
//	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
//		String request = (String) e.getMessage();
//		System.out.println("Thread:"+Thread.currentThread().getId()+" receive request:" + request + " on channel:"+e.getChannel().getId()+"!");
//		String response = this.businessProcess.businessProcess(request);
//		System.out.println("Thread:"+Thread.currentThread().getId()+" send response:" + response + " on channel:"+e.getChannel().getId()+"!");
//		e.getChannel().write(response + '\n');
//		try {
//			super.messageReceived(ctx, e);
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//	}
//	
//	@Override
//	public void channelDisconnected(ChannelHandlerContext ctx,
//			ChannelStateEvent e) throws Exception {
//		System.out.println("Thread:"+Thread.currentThread().getId()+" success to disconnect for channel:"+e.getChannel().getId()+"!");
//		super.channelDisconnected(ctx, e);
//	}
//
//	@Override
//	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
//			throws Exception {
//		if (e.getCause() instanceof NotSslRecordException) {
//			System.out.println("Thread:"+Thread.currentThread().getId()+"!"+e.getCause().getMessage()+" on channle: "+e.getChannel().getId()+"!");
//			e.getChannel().close();
//		} else if (e.getCause() instanceof IOException) {
//			System.out.println("Thread:"+Thread.currentThread().getId()+"!"+e.getCause().getMessage()+" on channle: "+e.getChannel().getId()+"!");
//		} else {
//			super.exceptionCaught(ctx, e);
//		}
//	}
//}