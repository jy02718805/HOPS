/*
 * 文件名：HttpRequestListenerThread.java
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
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ssl.SSLServerSocketFactory;

import org.apache.http.HttpConnectionFactory;
import org.apache.http.HttpServerConnection;
import org.apache.http.impl.DefaultBHttpServerConnection;
import org.apache.http.impl.DefaultBHttpServerConnectionFactory;
import org.apache.http.protocol.HttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestListenerThread extends Thread {

    private final HttpConnectionFactory<DefaultBHttpServerConnection> connFactory;
    private final ServerSocket serversocket;
    private final HttpService httpService;
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestListenerThread.class);
    
    public HttpRequestListenerThread(
            final int port,
            final HttpService httpService,
            final SSLServerSocketFactory sf) throws IOException {
        this.connFactory = DefaultBHttpServerConnectionFactory.INSTANCE;
        this.serversocket = sf != null ? sf.createServerSocket(port) : new ServerSocket(port);
        this.httpService = httpService;
    }

    @Override
    public void run() {
        logger.info("Listening on port " + this.serversocket.getLocalPort());
        while (!Thread.interrupted()) {
            try {
                // Set up HTTP connection
                Socket socket = this.serversocket.accept();
                logger.info("Incoming connection from " + socket.getInetAddress());
                HttpServerConnection conn = this.connFactory.createConnection(socket);

                // Start worker thread
                Thread t = new HttpWorkerThread(this.httpService, conn);
                t.setDaemon(true);//设为守护线程 子进程
                t.start();
            } catch (InterruptedIOException ex) {
            	logger.error("InterruptedIOException thread: "
                        + ex.getMessage());
                break;
            } catch (IOException e) {
            	logger.error("I/O error initialising connection thread: "
                        + e.getMessage());
                break;
            }
        }
    }
}
