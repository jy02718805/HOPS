package com.yuecheng.hops.gateway.communication.netty.http;


import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuecheng.hops.gateway.communication.netty.SSLSupportedPipelineFactory;
import com.yuecheng.hops.gateway.communication.protocal.http.HttpServer;


public class NettyHttpServer implements HttpServer
{
    private static final Logger logger = LoggerFactory.getLogger(NettyHttpServer.class);

    private int port;

    private SSLSupportedPipelineFactory pipelineFactory;

    private boolean tcpNoDelay = true;

    private boolean keepAlive = true;

    private Executor workThreadExecutor = Executors.newCachedThreadPool();

    private int ioWorkNumber = 100;

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

    public void setTcpNoDelay(boolean tcpNoDelay)
    {
        this.tcpNoDelay = tcpNoDelay;
    }

    public void setKeepAlive(boolean keepAlive)
    {
        this.keepAlive = keepAlive;
    }

    public void setWorkThreadExecutor(Executor workThreadExecutor)
    {
        this.workThreadExecutor = workThreadExecutor;
    }

    public void setIoWorkNumber(int ioWorkNumber)
    {
        this.ioWorkNumber = ioWorkNumber;
    }

    private ServerBootstrap bootstrap;

    public void start()
    {
        this.bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
            Executors.newCachedThreadPool(), workThreadExecutor, ioWorkNumber));

        this.bootstrap.setPipelineFactory(this.pipelineFactory);
        this.bootstrap.setOption("child.tcpNoDelay", this.tcpNoDelay);
        this.bootstrap.setOption("child.keepAlive", this.keepAlive);
        this.bootstrap.setOption("reuseAddress", true);
        this.bootstrap.bind(new InetSocketAddress(this.port));
        logger.info("success to start the server on port:" + this.port);

    }

    public void stop()
    {
        this.bootstrap.releaseExternalResources();
        logger.info("success to stop the server on port:" + this.port);

    }
}
