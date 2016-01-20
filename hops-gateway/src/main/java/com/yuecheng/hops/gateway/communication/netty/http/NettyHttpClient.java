package com.yuecheng.hops.gateway.communication.netty.http;

import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.http.CookieEncoder;
import org.jboss.netty.handler.codec.http.DefaultHttpRequest;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuecheng.hops.gateway.communication.netty.SSLSupportedPipelineFactory;
import com.yuecheng.hops.gateway.communication.protocal.http.HttpClient;
import com.yuecheng.hops.gateway.entity.ChannelLocalEntity;
import com.yuecheng.hops.gateway.message.AbstractMessage;
import com.yuecheng.hops.gateway.message.AbstractMessageSender;

public class NettyHttpClient extends AbstractMessageSender implements HttpClient{
	private static final Logger LOGGER = LoggerFactory
			.getLogger(NettyHttpClient.class);
	
	private SSLSupportedPipelineFactory pipelineFactory;
	private boolean tcpNoDelay = false;
	private boolean keepAlive = false;
	private int receiveBufferSize = 1048576;
	private ChannelFuture channelFuture;
	private Channel  channel;
	private boolean sslEnable;
	private boolean needClientAuth;
	private ChannelLocalEntity channelLocalEntity;
	
	public boolean isSslEnable() {
		return sslEnable;
	}

	public void setSslEnable(boolean sslEnable) {
		this.sslEnable = sslEnable;
	}

	public boolean isNeedClientAuth() {
		return needClientAuth;
	}

	public void setNeedClientAuth(boolean needClientAuth) {
		this.needClientAuth = needClientAuth;
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

	public void setReceiveBufferSize(int receiveBufferSize) {
		this.receiveBufferSize = receiveBufferSize;
	}

	ClientBootstrap client = new ClientBootstrap();
	ChannelGroup allChannels = null;
	        
    public void retrieve(String method, String url) throws Exception{
        retrieve(method, url, null, null);
    }
    
    public void retrieve(String method, String url ,ChannelLocalEntity channelLocalEntity) throws Exception{
    	this.channelLocalEntity = channelLocalEntity;
        retrieve(method, url, null, null);
    }
    
    public void retrieve(String method, String url, Map<String, Object> data ) throws Exception{
        retrieve(method, url, data, null);
    }     
    
    public void retrieve(String method, String url, Map<String, Object>data, Map<String, String> cookie) throws Exception{
        if(url == null) throw new Exception("url is null") ;
        URI uri = new URI(url);                                                 
        String scheme = uri.getScheme() == null? "http" : uri.getScheme();
        String host = uri.getHost() == null? "localhost" : uri.getHost();
                                                                                    
        HttpRequest request = new DefaultHttpRequest(                               
                HttpVersion.HTTP_1_1, HttpMethod.valueOf(method), uri.toASCIIString()); 
                        
        request.setHeader(HttpHeaders.Names.HOST, host);                            
        request.setHeader(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        if(cookie != null){
                CookieEncoder httpCookieEncoder = new CookieEncoder(false);  
                for (Map.Entry<String, String> m : cookie.entrySet()) {
                        httpCookieEncoder.addCookie(m.getKey(), m.getValue());
                        request.setHeader(HttpHeaders.Names.COOKIE, httpCookieEncoder.encode());    
                }
        }
        retrieve(request);
    }
    
    public void start()
    {
    	ChannelFactory channelFactory = new NioClientSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());
		allChannels =new DefaultChannelGroup();
		pipelineFactory.setSslEnable(sslEnable);
		pipelineFactory.setNeedClientAuth(needClientAuth);
		client.setFactory(channelFactory);
		client.setPipelineFactory(pipelineFactory);
		client.setOption("tcpNoDelay", tcpNoDelay);
		client.setOption("keepAlive", keepAlive);
		client.setOption("receiveBufferSize", receiveBufferSize);
    }
    
    public void retrieve(HttpRequest request)throws Exception{
    	try {
	        URI uri = new URI(request.getUri());
	        int port = uri.getPort() == -1? 80 : uri.getPort(); 
			client.setOption("remoteAddress", new InetSocketAddress(request.getHeader(HttpHeaders.Names.HOST) , port));
			this.channelFuture = client.connect();
			this.channel = this.channelFuture.getChannel();
			this.channelFuture.await(90, TimeUnit.SECONDS);
	        allChannels.add(this.channel);
	        
	        if (!this.channel.isConnected()) {
				this.channelFuture = client.connect();
				this.channelFuture.await(90, TimeUnit.SECONDS);
				this.channel = this.channelFuture.getChannel();
			}
	        
	        ChannelHandlerContext chc = this.channelFuture.getChannel().getPipeline().getContext(HttpResponseHandler.class);
	        chc.setAttachment(channelLocalEntity);
	        
			ChannelFuture channelFuture = this.channel.write(request);
			if (channelFuture.isDone()) {
				LOGGER.info("success to send request:" + request.toString());
//				channelFuture.addListener(ChannelFutureListener.CLOSE);
				channelFuture.awaitUninterruptibly();
				channelFuture.getChannel().getCloseFuture().awaitUninterruptibly();
			}
    	} catch (Exception e) {
        	LOGGER.error("NettyHttpClient:[retrieve]["+e.getMessage()+"]");
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
        	LOGGER.error("NettyHttpClient:[method-IOException]["+e.getMessage()+"]");  
        } finally {     
            try {     
                if(writer != null){  
                    writer.close();     
                }  
            } catch (IOException e) { 
            	LOGGER.error("NettyHttpClient:[method-finally-IOException]["+e.getMessage()+"]");    
            }     
        }   
    }
    
    public void close(){
    	this.channel.close().awaitUninterruptibly();
        client.releaseExternalResources();
    }

    @Override
    public AbstractMessage send(AbstractMessage sendMessage)
    {
        return null;
    }
}
