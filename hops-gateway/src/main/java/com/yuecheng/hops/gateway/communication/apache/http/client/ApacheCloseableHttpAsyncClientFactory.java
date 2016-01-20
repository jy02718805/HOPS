/*
 * 文件名：ApacheCloseableHttpAsyncClientFactory.java 版权：Copyright by www.365haoyou.com 描述： 修改人：yangyi
 * 修改时间：2014年10月16日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.gateway.communication.apache.http.client;


import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.nio.reactor.IOReactorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.gateway.communication.apache.http.ApachePoolingNHttpClientConnectionManager;
import com.yuecheng.hops.gateway.communication.apache.http.ApacheRequestConfig;
import com.yuecheng.hops.gateway.communication.apache.http.ApacheSSLIOSessionStrategy;
import com.yuecheng.hops.gateway.entity.HttpConifguration;

@Component("apacheCloseableHttpAsyncClientFactory")
public class ApacheCloseableHttpAsyncClientFactory
{

    @Autowired
    ApachePoolingNHttpClientConnectionManager apachePoolingNHttpClientConnectionManager;

    @Autowired
    ApacheSSLIOSessionStrategy apacheSSLIOSessionStrategy;
    
    public CloseableHttpAsyncClient createClient(CookieStore cookieStore,
                                                 CredentialsProvider credentialsProvider,PoolingNHttpClientConnectionManager connManager )
        throws IOReactorException
    {
        if (BeanUtils.isNull(connManager))
        {
            connManager = apachePoolingNHttpClientConnectionManager.createConnectionManager(new HttpConifguration());
        }
        
        // Create an HttpClient with the given custom dependencies and configuration.
        HttpAsyncClientBuilder builder = HttpAsyncClients.custom();
        builder = builder.setConnectionManager(connManager);
        if (BeanUtils.isNotNull(cookieStore))
        {
            builder = builder.setDefaultCookieStore(cookieStore);
        }
        if (BeanUtils.isNotNull(credentialsProvider))
        {
            builder = builder.setDefaultCredentialsProvider(credentialsProvider);
        }
//        builder = builder.setProxy(new HttpHost("myproxy", 8080));
        builder = builder.setDefaultRequestConfig(ApacheRequestConfig.defaultRequestConfig);
        CloseableHttpAsyncClient httpclient = builder.build();

        return httpclient;
    }

}