/*
 * 文件名：NHttpCoreServer.java 版权：Copyright by www.365haoyou.com 描述： 修改人：yangyi 修改时间：2014年10月16日 跟踪单号：
 * 修改单号： 修改内容：
 */

package com.yuecheng.hops.gateway.communication.apache.http.server;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.http.ExceptionLogger;
import org.apache.http.HttpRequest;
import org.apache.http.impl.nio.bootstrap.HttpServer;
import org.apache.http.impl.nio.bootstrap.ServerBootstrap;
import org.apache.http.nio.protocol.HttpAsyncRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuecheng.hops.gateway.communication.apache.http.ApacheIOReactorConfig;
import com.yuecheng.hops.gateway.security.ssl.SSLContextFactory;


public class NHttpCoreServer
{
    private static Logger logger = LoggerFactory.getLogger(NHttpCoreServer.class);

    private int connectionTimeout;
    
    private int port = 8080;

    private int soTimeout;

    private boolean sslEnable = false;

    private SSLContextFactory sslContextFactory;

    private Map<String, HttpAsyncRequestHandler<HttpRequest>> httpAsyncRequestHandlers = new HashMap<String, HttpAsyncRequestHandler<HttpRequest>>();

    private HttpServer server;

    public void setPort(int port)
    {
        this.port = port;
    }

    public void setSoTimeout(int soTimeout)
    {
        this.soTimeout = soTimeout;
    }
    
    public void setConnectionTimeout(int connectionTimeout)
    {
        this.connectionTimeout = connectionTimeout;
    }

    public void setSslEnable(boolean sslEnable)
    {
        this.sslEnable = sslEnable;
    }

    public void setSslContextFactory(SSLContextFactory sslContextFactory)
    {
        this.sslContextFactory = sslContextFactory;
    }

    public void setHttpAsyncRequestHandlers(Map<String, HttpAsyncRequestHandler<HttpRequest>> httpAsyncRequestHandlers)
    {
        this.httpAsyncRequestHandlers = httpAsyncRequestHandlers;
    }

    public void start()
        throws Exception
    {
        ServerBootstrap serverBootstrap = ServerBootstrap.bootstrap().setListenerPort(port).setServerInfo(
            "Test/1.1").setIOReactorConfig(
            ApacheIOReactorConfig.create(connectionTimeout, soTimeout));
        if (sslEnable)
        {
            serverBootstrap.setSslContext(sslContextFactory.createSSLServerContext());
        }
        serverBootstrap.setExceptionLogger(ExceptionLogger.STD_ERR);
        for (String handlerKey : httpAsyncRequestHandlers.keySet())
        {
            serverBootstrap.registerHandler(handlerKey, httpAsyncRequestHandlers.get(handlerKey));
        }
        server = serverBootstrap.create();
        server.start();
//        server.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        logger.info("The server on port:" + port + " has been started up!");

    }

    public void stop()
        throws Exception
    {
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                server.shutdown(5, TimeUnit.SECONDS);
                logger.info("The server on port:" + port + " has been shutted down!");
            }
        });
    }

}
