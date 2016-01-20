/*
 * 文件名：ApacheCloseableHttpClientFactory.java 版权：Copyright by www.365haoyou.com 描述： 修改人：yangyi
 * 修改时间：2014年10月16日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.gateway.communication.apache.http.client;


import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.gateway.communication.apache.http.ApachePoolingHttpClientConnectionManager;
import com.yuecheng.hops.gateway.communication.apache.http.ApacheRequestConfig;
import com.yuecheng.hops.gateway.entity.HttpConifguration;


@Component("apacheCloseableHttpClientFactory")
public class ApacheCloseableHttpClientFactory extends BasePooledObjectFactory<CloseableHttpClient>
{
    @Autowired
    ApachePoolingHttpClientConnectionManager apachePoolingHttpClientConnectionManager;

    CookieStore baiscCookieStore = new BasicCookieStore();

    CredentialsProvider basicCredentialsProvider = new BasicCredentialsProvider();
    
    private HttpConifguration httpConifguration = new HttpConifguration();

    public void setHttpConifguration(HttpConifguration httpConifguration)
    {
        this.httpConifguration = httpConifguration;
    }

    public CloseableHttpClient createClient(HttpConifguration httpConfiguration,CookieStore cookieStore,CredentialsProvider credentialsProvider)
    {
        PoolingHttpClientConnectionManager connManager = apachePoolingHttpClientConnectionManager.createConnMananger(httpConfiguration);
        HttpClientBuilder builder = HttpClients.custom();
        builder = builder.setConnectionManager(connManager);
        if (BeanUtils.isNull(cookieStore))
        {
            builder = builder.setDefaultCookieStore(baiscCookieStore);
        }
        else 
        {
            builder = builder.setDefaultCookieStore(cookieStore);
        }
        
        if (BeanUtils.isNull(credentialsProvider))
        {
            builder = builder.setDefaultCredentialsProvider(basicCredentialsProvider);
        }
        else
        {
            builder = builder.setDefaultCredentialsProvider(credentialsProvider);
        }
        if (httpConfiguration.isProxyEnable())
        {
            builder = builder.setProxy(httpConfiguration.getProxyHost());
        }
        
        builder = builder.setDefaultRequestConfig(ApacheRequestConfig.defaultRequestConfig);
        CloseableHttpClient httpclient = builder.build();
        return httpclient;
    }

    @Override
    public CloseableHttpClient create()
        throws Exception
    {
        PoolingHttpClientConnectionManager connManager = apachePoolingHttpClientConnectionManager.createConnMananger(httpConifguration);
        HttpClientBuilder builder = HttpClients.custom();
        builder = builder.setConnectionManager(connManager);
        builder = builder.setDefaultCookieStore(baiscCookieStore);
        builder = builder.setDefaultCredentialsProvider(basicCredentialsProvider);
        builder = builder.setDefaultRequestConfig(ApacheRequestConfig.defaultRequestConfig);
        CloseableHttpClient httpclient = builder.build();
        return httpclient;
    }

    @Override
    public PooledObject<CloseableHttpClient> wrap(CloseableHttpClient client)
    {
        return new DefaultPooledObject<CloseableHttpClient>(client);    
    }
}
