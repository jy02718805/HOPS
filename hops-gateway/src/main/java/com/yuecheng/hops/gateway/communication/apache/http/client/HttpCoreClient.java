/*
 * 文件名：HttpCoreClient.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：yangyi
 * 修改时间：2014年10月16日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.gateway.communication.apache.http.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.UnsupportedCharsetException;

import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultBHttpClientConnection;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpProcessorBuilder;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.RequestConnControl;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpCoreClient
{
    private static Logger logger = LoggerFactory.getLogger(HttpCoreClient.class);
    
    HttpRequestExecutor httpexecutor = new HttpRequestExecutor();
    
    DefaultBHttpClientConnection conn = new DefaultBHttpClientConnection(8 * 1024);
    ConnectionReuseStrategy connStrategy = DefaultConnectionReuseStrategy.INSTANCE;
    
    HttpProcessor httpproc = HttpProcessorBuilder.create()
        .add(new RequestContent())
        .add(new RequestTargetHost())
        .add(new RequestConnControl())
        .add(new RequestUserAgent("Test/1.1"))
        .add(new RequestExpectContinue(true)).build();
    
    public void sendHttpPost() throws UnsupportedCharsetException, UnknownHostException, IOException, HttpException
    {
        HttpCoreContext coreContext = HttpCoreContext.create();
        HttpHost host = new HttpHost("localhost", 8080);
        coreContext.setTargetHost(host);
        try {

            HttpEntity[] requestBodies = {
                    new StringEntity(
                            "This is the first test request",
                            ContentType.create(ContentType.TEXT_PLAIN.getMimeType(), Consts.UTF_8)),
                    new ByteArrayEntity(
                            "This is the second test request".getBytes("UTF-8"),
                            ContentType.APPLICATION_OCTET_STREAM),
                    new InputStreamEntity(
                            new ByteArrayInputStream(
                                    "This is the third test request (will be chunked)"
                                    .getBytes("UTF-8")),
                            ContentType.APPLICATION_OCTET_STREAM)
            };

            for (int i = 0; i < requestBodies.length; i++) {
                if (!conn.isOpen()) {
                    Socket socket = new Socket(host.getHostName(), host.getPort());
                    conn.bind(socket);
                }
                BasicHttpEntityEnclosingRequest request = new BasicHttpEntityEnclosingRequest("POST",
                        "/servlets-examples/servlet/RequestInfoExample");
                request.setEntity(requestBodies[i]);
                logger.debug(">> Request URI: " + request.getRequestLine().getUri());

                httpexecutor.preProcess(request, httpproc, coreContext);
                HttpResponse response = httpexecutor.execute(request, conn, coreContext);
                httpexecutor.postProcess(response, httpproc, coreContext);

                logger.debug("<< Response: " + response.getStatusLine());
                logger.debug(EntityUtils.toString(response.getEntity()));
                logger.debug("==============");
                if (!connStrategy.keepAlive(response, coreContext)) {
                    conn.close();
                } else {
                    logger.info("Connection kept alive...");
                }
            }
        } finally {
            conn.close();
        }
    }
    public void sendHttpGet() throws UnknownHostException, IOException, HttpException
    {
        
        HttpCoreContext coreContext = HttpCoreContext.create();
        HttpHost host = new HttpHost("localhost", 8080);
        coreContext.setTargetHost(host);
        
        try {

            String[] targets = {
                    "/",
                    "/servlets-examples/servlet/RequestInfoExample",
                    "/somewhere%20in%20pampa"};

            for (int i = 0; i < targets.length; i++) {
                if (!conn.isOpen()) {
                    Socket socket = new Socket(host.getHostName(), host.getPort());
                    conn.bind(socket);
                }
                BasicHttpRequest request = new BasicHttpRequest("GET", targets[i]);
                logger.debug(">> Request URI: " + request.getRequestLine().getUri());

                httpexecutor.preProcess(request, httpproc, coreContext);
                HttpResponse response = httpexecutor.execute(request, conn, coreContext);
                httpexecutor.postProcess(response, httpproc, coreContext);

                logger.debug("<< Response: " + response.getStatusLine());
                logger.debug(EntityUtils.toString(response.getEntity()));
                logger.debug("==============");
                if (!connStrategy.keepAlive(response, coreContext)) {
                    conn.close();
                } else {
                    logger.info("Connection kept alive...");
                }
            }
        } finally {
            conn.close();
        }
    }
}
