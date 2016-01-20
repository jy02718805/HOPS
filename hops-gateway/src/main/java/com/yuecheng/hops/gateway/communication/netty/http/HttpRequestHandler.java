package com.yuecheng.hops.gateway.communication.netty.http;

import static org.jboss.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.OK;
import static org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.DynamicChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuecheng.hops.common.utils.UtilBigDecimalConverter;

public class HttpRequestHandler extends SimpleChannelUpstreamHandler {
	private HttpRequest request;
    private boolean readingChunks;
    private static final Logger logger = LoggerFactory.getLogger(UtilBigDecimalConverter.class);
    
//    @Override
//    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
//        if (!readingChunks) {
//            HttpRequest request = this.request = (HttpRequest) e.getMessage();
//            String uri = request.getUri();
//            /**
//             * 100 Continue
//             * 是这样的一种情况：HTTP客户端程序有一个实体的主体部分要发送给服务器，但希望在发送之前查看下服务器是否会
//             * 接受这个实体，所以在发送实体之前先发送了一个携带100
//             * Continue的Expect请求首部的请求。服务器在收到这样的请求后，应该用 100 Continue或一条错误码来进行响应。
//             */
//            if (is100ContinueExpected(request)) {
//                send100Continue(e);
//            }
//            // 解析http头部
//            for (Entry<String, String> h : request.getHeaders()) {
//                System.out.println("HEADER: " + h.getKey() + " = " + h.getValue() + "\r\n");
//            }
//            // 解析请求参数
//            QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.getUri());
//            Map<String, List<String>> params = queryStringDecoder.getParameters();
//            if (!params.isEmpty()) {
//                for (Entry<String, List<String>> p : params.entrySet()) {
//                    String key = p.getKey();
//                    List<String> vals = p.getValue();
//                    for (String val : vals) {
//                        System.out.println("PARAM: " + key + " = " + val + "\r\n");
//                    }
//                }
//            }
//            if (request.isChunked()) {
//                readingChunks = true;
//            } else {
//                ChannelBuffer content = request.getContent();
//                if (content.readable()) {
//                    System.out.println(content.toString(CharsetUtil.UTF_8));
//                }
//                writeResponse(e, uri);
//            }
//        } else {// 为分块编码时
//            HttpChunk chunk = (HttpChunk) e.getMessage();
//            if (chunk.isLast()) {
//                readingChunks = false;
//                // END OF CONTENT\r\n"
//                HttpChunkTrailer trailer = (HttpChunkTrailer) chunk;
//                if (!trailer.getHeaderNames().isEmpty()) {
//                    for (String name : trailer.getHeaderNames()) {
//                        for (String value : trailer.getHeaders(name)) {
//                            System.out.println("TRAILING HEADER: " + name + " = " + value + "\r\n");
//                        }
//                    }
//                }
//                writeResponse(e, "/");
//            } else {
//                System.out.println("CHUNK: " + chunk.getContent().toString(CharsetUtil.UTF_8)
//                        + "\r\n");
//            }
//        }
//    }
 
    private boolean is100ContinueExpected(HttpRequest request2) {
		return false;
	}

    //http://wutaoo.iteye.com/blog/373640
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception{
		HttpRequest request = (HttpRequest) e.getMessage();
		String uri = request.getUri();
		logger.info("uri:"+uri);
		HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);
		
        // 解析请求参数
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.getUri());
        Map<String, List<String>> params = queryStringDecoder.getParameters();
        if (!params.isEmpty()) {
            for (Entry<String, List<String>> p : params.entrySet()) {
                String key = p.getKey();
                List<String> vals = p.getValue();
                for (String val : vals) {
                	logger.info("PARAM: " + key + " = " + val + "\r\n");
                }
            }
        }
		
        ChannelBuffer buffer=new DynamicChannelBuffer(2048);
		buffer.writeBytes("hello!! 你好".getBytes("UTF-8"));
		response.setContent(buffer);
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		response.setHeader("Content-Length", response.getContent().writerIndex());
		Channel ch = e.getChannel();
		// Write the initial line and the header.
		ch.write(response);
		ch.disconnect();
		ch.close();
	}
 
    private void send100Continue(MessageEvent e) {
        HttpResponse response = new DefaultHttpResponse(HTTP_1_1, CONTINUE);
        e.getChannel().write(response);
    }
 
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
    	logger.error("HttpRequestHandler:[exceptionCaught]["+e.getCause().getMessage()+"]");
        e.getCause().printStackTrace();
        e.getChannel().close();
    }
}
