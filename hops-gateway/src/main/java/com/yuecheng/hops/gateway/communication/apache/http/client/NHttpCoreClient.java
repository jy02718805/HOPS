/*
 * 文件名：NHttpCoreClient.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：yangyi
 * 修改时间：2014年10月16日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.gateway.communication.apache.http.client;


import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.impl.nio.DefaultHttpClientIODispatch;
import org.apache.http.impl.nio.pool.BasicNIOConnPool;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.nio.protocol.BasicAsyncRequestProducer;
import org.apache.http.nio.protocol.BasicAsyncResponseConsumer;
import org.apache.http.nio.protocol.HttpAsyncRequestExecutor;
import org.apache.http.nio.protocol.HttpAsyncRequester;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOEventDispatch;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpProcessorBuilder;
import org.apache.http.protocol.RequestConnControl;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NHttpCoreClient
{

    private static Logger logger = LoggerFactory.getLogger(NHttpCoreClient.class);
    
    HttpProcessor httpproc = HttpProcessorBuilder.create()
            .add(new RequestContent())
            .add(new RequestTargetHost())
            .add(new RequestConnControl())
            .add(new RequestUserAgent("Test/1.1"))
            .add(new RequestExpectContinue(true)).build();
    
    public void test(String[] args) throws Exception {
        
        // Create client-side HTTP protocol handler
        HttpAsyncRequestExecutor protocolHandler = new HttpAsyncRequestExecutor();
        // Create client-side I/O event dispatch
        final IOEventDispatch ioEventDispatch = new DefaultHttpClientIODispatch(protocolHandler,
                ConnectionConfig.DEFAULT);
        // Create client-side I/O reactor
        final ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor();
        // Create HTTP connection pool
        BasicNIOConnPool pool = new BasicNIOConnPool(ioReactor, ConnectionConfig.DEFAULT);
        // Limit total number of connections to just two
        pool.setDefaultMaxPerRoute(2);
        pool.setMaxTotal(2);
        // Run the I/O reactor in a separate thread
        Thread t = new Thread(new Runnable() {

            public void run() {
                try {
                    // Ready to go!
                    ioReactor.execute(ioEventDispatch);
                } catch (InterruptedIOException ex) {
                    logger.error("Interrupted");
                } catch (IOException e) {
                    logger.error("I/O error: " + e.getMessage());
                }
                logger.info("Shutdown");
            }

        });
        // Start the client thread
        t.start();
        // Create HTTP requester
        HttpAsyncRequester requester = new HttpAsyncRequester(httpproc);
        // Execute HTTP GETs to the following hosts and
        HttpHost[] targets = new HttpHost[] {
                new HttpHost("www.apache.org", 80, "http"),
                new HttpHost("www.verisign.com", 443, "https"),
                new HttpHost("www.google.com", 80, "http")
        };
        final CountDownLatch latch = new CountDownLatch(targets.length);
        for (int i = 0; i < 10000; i++ )
        {
          
            for (final HttpHost target: targets) {
                BasicHttpRequest request = new BasicHttpRequest("GET", "/");
                HttpCoreContext coreContext = HttpCoreContext.create();
                  
                
                Future<HttpResponse> future = requester.execute(
                        new BasicAsyncRequestProducer(target, request),
                        new BasicAsyncResponseConsumer(),
                        pool,
                        coreContext,
                        // Handle HTTP response from a callback
                        new FutureCallback<HttpResponse>() {
    
                    public void completed(final HttpResponse response) {
                        latch.countDown();
                        logger.debug(target + "->" + response.getStatusLine());
                    }
    
                    public void failed(final Exception ex) {
                        latch.countDown();
                        logger.debug(target + "->" + ex);
                    }
    
                    public void cancelled() {
                        latch.countDown();
                        logger.debug(target + " cancelled");
                    }
    
                });
                HttpResponse response = future.get();
                
            }
        
        System.out.println(i);
        }
        latch.await();
        logger.info("Shutting down I/O reactor");
        ioReactor.shutdown();
        logger.info("Done");
    }
}
