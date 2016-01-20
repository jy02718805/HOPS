package com.yuecheng.hops.gateway.communication.netty.http;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuecheng.hops.gateway.entity.ChannelLocalEntity;
import com.yuecheng.hops.injection.entity.InterfacePacketsDefinitionBo;
import com.yuecheng.hops.injection.service.InterfaceService;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;

@Component
public class HttpResponseHandler extends SimpleChannelUpstreamHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpResponseHandler.class);
	@Autowired
	private InterfaceService interfaceService;
	@Autowired
	private DeliveryManagement deliveryManagement;
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		e.getCause().printStackTrace();
		Channel ch = e.getChannel();
		ch.close();
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
                LOGGER.debug("channelId:["+e.getChannel().getId()+"] responseStr:["+responseStr+"]");
                try {
//                	map = parseResponseService.parseResponse(interfaceType, recordId, responseStr);
//                	ResponseService service = interfaceResponseSelector.select(interfaceType);
//                	service.execute(merchantId, interfaceType, recordId, responseStr, map);
                } catch (Exception e2) {
                	LOGGER.error("HttpResponseHandler:[messageReceived]["+e2.getMessage()+"]");
				}
            }
		} catch (Exception e2) {
        	LOGGER.error("HttpResponseHandler:[messageReceived-Exception]["+e2.getMessage()+"]");
		} finally{
			e.getChannel().close();
		}
	}
	
	/**   
     * 追加文件：使用FileWriter   
     *    
     * @param fileName   
     * @param content   
     */    
    public static void method(String fileName, String content) {   
        FileWriter writer = null;  
        try {     
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件     
            writer = new FileWriter(fileName, true);     
            writer.write(content);  
            writer.flush();
        } catch (IOException e) {  
        	LOGGER.error("HttpResponseHandler:[method-IOException]["+e.getMessage()+"]");  
        } finally {     
            try {     
                if(writer != null){  
                    writer.close();     
                }  
            } catch (IOException e) { 
            	LOGGER.error("HttpResponseHandler:[method-finally-IOException]["+e.getMessage()+"]");    
            }     
        }   
    }
}
