package com.yuecheng.hops.gateway.communication.netty;

import java.nio.charset.Charset;
import java.util.Map;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.util.internal.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuecheng.hops.gateway.entity.ChannelLocalEntity;
import com.yuecheng.hops.injection.entity.InterfacePacketsDefinitionBo;
import com.yuecheng.hops.injection.service.InterfaceService;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;

public class SocketHandler extends SimpleChannelHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(SocketHandler.class);
	
	@Autowired
	private InterfaceService interfaceService;
	@Autowired
	private DeliveryManagement deliveryManagement;
	private static final Logger logger = LoggerFactory.getLogger(SocketHandler.class);
    
	private static ConcurrentHashMap<Integer,Object> hashMap = new ConcurrentHashMap<Integer,Object>();
	
	public static void put(Integer channelId,Object object)
	{
		hashMap.put(channelId, object);
	}
	
	public static void remove(Integer channelId){
		hashMap.remove(channelId);
	}
	
	@Override
	public void connectRequested(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Thread:"+Thread.currentThread().getId()+" success to to connect to:"+e.getChannel().getId()+"!");
		}
		super.connectRequested(ctx, e);
	}

	@Override
	public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) {
		try {
			String request = (String) e.getMessage();
			LOGGER.info("Thread:"+Thread.currentThread().getId()+" send request:" + request + " on channel:"+e.getChannel().getId()+"!");
			super.writeRequested(ctx, e);
		} catch (Exception e2) {
			logger.error("SocketHandler:[writeRequested]["+e2.getMessage()+"]");
		} finally{
			hashMap.remove(ctx.getChannel().getId());
		}
	}
	
	@Override
	public void disconnectRequested(ChannelHandlerContext ctx,
			ChannelStateEvent e) throws Exception {
		LOGGER.info("Thread:"+Thread.currentThread().getId()+" success to disconnect for channel:"+e.getChannel().getId()+"!");
		super.connectRequested(ctx, e);
	}
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		try {
			ChannelLocalEntity ce = (ChannelLocalEntity)ctx.getAttachment();
            HttpResponse response = (HttpResponse) e.getMessage();
            Long merchantId = ce.getMerchantId();
            String interfaceType = ce.getInterfaceType();
            Long recordId = ce.getRecordId();
            
            ChannelBuffer content = response.getContent();
            if (content.readable()) {
            	InterfacePacketsDefinitionBo ipd = interfaceService.getInterfacePacketsDefinitionByParams(merchantId, interfaceType);
            	Map<String,Object> map = null;
                String responseStr = content.toString(Charset.forName(ipd.getEncoding()));
                try {
//                	map = parseResponseService.parseResponse(interfaceType, recordId, responseStr);
//                	ResponseService service = interfaceResponseSelector.select(interfaceType);
//                	service.execute(merchantId, interfaceType, recordId, responseStr, map);
                } catch (Exception e2) {
        			logger.error("SocketHandler:[messageReceived]["+e2.getMessage()+"]");
				}
            }
		} catch (Exception e2) {
			logger.error("SocketHandler:[messageReceived-Exception]["+e2.getMessage()+"]");
		} 
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		super.exceptionCaught(ctx, e);
	}
}
