package com.yuecheng.hops.gateway.communication.netty;


import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuecheng.hops.gateway.communication.netty.http.HttpResponseHandler;
import com.yuecheng.hops.gateway.communication.protocal.SocketClient;
import com.yuecheng.hops.gateway.entity.ChannelLocalEntity;
import com.yuecheng.hops.gateway.message.AbstractMessage;
import com.yuecheng.hops.gateway.message.AbstractMessageSender;


public class NettySocketClient extends AbstractMessageSender implements SocketClient
{
	private static final Logger logger = LoggerFactory.getLogger(NettySocketClient.class);
    private String                      host;

    private int                         port;

    private SSLSupportedPipelineFactory pipelineFactory;

    private boolean                     tcpNoDelay        = false;

    private boolean                     keepAlive         = false;

    private int                         receiveBufferSize = 1048576;

    private ChannelFuture               channelFuture;

    private Channel                     channel;

    private boolean                     sslEnable;

    private boolean                     needClientAuth;

    public boolean isSslEnable()
    {
        return sslEnable;
    }

    public void setSslEnable(boolean sslEnable)
    {
        this.sslEnable = sslEnable;
    }

    public boolean isNeedClientAuth()
    {
        return needClientAuth;
    }

    public void setNeedClientAuth(boolean needClientAuth)
    {
        this.needClientAuth = needClientAuth;
    }

    public NettySocketClient()
    {

    }

    public String getHost()
    {
        return host;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public SSLSupportedPipelineFactory getPipelineFactory()
    {
        return pipelineFactory;
    }

    public void setPipelineFactory(SSLSupportedPipelineFactory pipelineFactory)
    {
        this.pipelineFactory = pipelineFactory;
    }

    public boolean isTcpNoDelay()
    {
        return tcpNoDelay;
    }

    public void setTcpNoDelay(boolean tcpNoDelay)
    {
        this.tcpNoDelay = tcpNoDelay;
    }

    public boolean isKeepAlive()
    {
        return keepAlive;
    }

    public void setKeepAlive(boolean keepAlive)
    {
        this.keepAlive = keepAlive;
    }

    public int getReceiveBufferSize()
    {
        return receiveBufferSize;
    }

    public void setReceiveBufferSize(int receiveBufferSize)
    {
        this.receiveBufferSize = receiveBufferSize;
    }

    public Channel getChannel()
    {
        return channelFuture.awaitUninterruptibly().getChannel();
    }

    ClientBootstrap client = new ClientBootstrap();

    public void send(String msg, String host, int port, ChannelLocalEntity channelLocalEntity)
    {
        try
        {

            if (!this.channel.isConnected())
            {
                client.setOption("remoteAddress", new InetSocketAddress(host, port));
                this.channelFuture = client.connect();
                this.channel = this.channelFuture.awaitUninterruptibly().getChannel();
            }

            ChannelHandlerContext chc = this.channelFuture.getChannel().getPipeline().getContext(
                HttpResponseHandler.class);
            chc.setAttachment(channelLocalEntity);

            ChannelFuture channelFuture = this.channel.write(msg + '\n');
            if (channelFuture.isDone())
            {
            	logger.info("success to send message:" + msg);
                channelFuture.awaitUninterruptibly();
                channelFuture.getChannel().getCloseFuture().awaitUninterruptibly();
            }
        }
        catch (Exception e)
        {
        	logger.error("NettySocketClient:[send]["+e.getMessage()+"]");
        }
    }

    public void start()
    {
        ChannelFactory channelFactory = new NioClientSocketChannelFactory(
            Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
        pipelineFactory.setSslEnable(sslEnable);
        pipelineFactory.setNeedClientAuth(needClientAuth);
        client.setFactory(channelFactory);
        client.setPipelineFactory(pipelineFactory);
        client.setOption("tcpNoDelay", tcpNoDelay);
        client.setOption("keepAlive", keepAlive);
        client.setOption("receiveBufferSize", receiveBufferSize);
    }

    public void stop()
    {
        client.releaseExternalResources();
    }

    @Override
    public AbstractMessage send(AbstractMessage message)
    {
       
        return null;
    }
}
