/*
 * 文件名：HttpCoreServer.java 版权：Copyright by www.365haoyou.com 描述： 修改人：yangyi 修改时间：2014年10月16日 跟踪单号：
 * 修改单号： 修改内容：
 */

package com.yuecheng.hops.gateway.communication.apache.http.server;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.http.impl.bootstrap.HttpServer;
import org.apache.http.impl.bootstrap.ServerBootstrap;
import org.apache.http.protocol.HttpRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuecheng.hops.gateway.communication.apache.http.ApacheSocketConfig;
import com.yuecheng.hops.gateway.security.ssl.SSLContextFactory;


public class HttpCoreServer
{
    private static Logger logger = LoggerFactory.getLogger(HttpCoreServer.class);

    private int port = 8080;

    private int soTimeout;

    private boolean tcpNoDelay = true;

    private boolean sslEnable = false;

    private SSLContextFactory sslContextFactory;

    private Map<String, HttpRequestHandler> httpRequestHandlers = new HashMap<String, HttpRequestHandler>();

    public void setPort(int port)
    {
        this.port = port;
    }

    public void setSoTimeout(int soTimeout)
    {
        this.soTimeout = soTimeout;
    }

    public void setTcpNoDelay(boolean tcpNoDelay)
    {
        this.tcpNoDelay = tcpNoDelay;
    }

    public void setSslEnable(boolean sslEnable)
    {
        this.sslEnable = sslEnable;
    }

    public void setSslContextFactory(SSLContextFactory sslContextFactory)
    {
        this.sslContextFactory = sslContextFactory;
    }

    public void setHttpRequestHandlers(Map<String, HttpRequestHandler> httpRequestHandlers)
    {
        this.httpRequestHandlers = httpRequestHandlers;
    }

    private HttpServer server;

    public void start()
        throws Exception
    {

        ServerBootstrap serverBootstrap = ServerBootstrap.bootstrap().setListenerPort(port).setServerInfo(
            "Test/1.1").setSocketConfig(ApacheSocketConfig.create(soTimeout, tcpNoDelay));
        if (sslEnable)
        {
            serverBootstrap.setSslContext(sslContextFactory.createSSLServerContext());
        }
        serverBootstrap.setExceptionLogger(new StdErrorExceptionLogger());
        for (String handlerKey : httpRequestHandlers.keySet())
        {
            serverBootstrap.registerHandler(handlerKey, httpRequestHandlers.get(handlerKey));
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
