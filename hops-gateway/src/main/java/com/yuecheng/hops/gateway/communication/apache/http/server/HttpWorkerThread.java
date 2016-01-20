/*
 * 文件名：HttpWorkerThread.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：yangyi
 * 修改时间：2014年10月17日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.gateway.communication.apache.http.server;

import java.io.IOException;

import org.apache.http.ConnectionClosedException;
import org.apache.http.HttpException;
import org.apache.http.HttpServerConnection;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpWorkerThread extends Thread {

    private final HttpService httpservice;
    private final HttpServerConnection conn;
    private static final Logger logger = LoggerFactory.getLogger(HttpWorkerThread.class);
    
    public HttpWorkerThread(
            final HttpService httpservice,
            final HttpServerConnection conn) {
        super();
        this.httpservice = httpservice;
        this.conn = conn;
    }

    @Override
    public void run() {
        logger.info("New connection thread");
        HttpContext context = new BasicHttpContext(null);
        try {
            while (!Thread.interrupted() && this.conn.isOpen()) {
                this.httpservice.handleRequest(this.conn, context);
            }
        } catch (ConnectionClosedException ex) {
        	logger.error("Client closed connection");
        } catch (IOException ex) {
        	logger.error("I/O error: " + ex.getMessage());
        } catch (HttpException ex) {
        	logger.error("Unrecoverable HTTP protocol violation: " + ex.getMessage());
        } finally {
            try {
                this.conn.shutdown();
            } catch (IOException ignore) {
            	logger.error("shutdown error:"+ignore.getMessage());
            }
        }
    }

}
