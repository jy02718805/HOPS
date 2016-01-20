/*
 * 文件名：ApachePoolingHttpClientConnectionManager.java 版权：Copyright by www.365haoyou.com 描述：
 * 修改人：yangyi 修改时间：2014年10月16日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.gateway.communication.apache.http;


import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.conn.DefaultHttpResponseParser;
import org.apache.http.impl.conn.DefaultHttpResponseParserFactory;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.http.io.HttpMessageParser;
import org.apache.http.io.HttpMessageParserFactory;
import org.apache.http.io.HttpMessageWriterFactory;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicLineParser;
import org.apache.http.message.LineParser;
import org.apache.http.util.CharArrayBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.gateway.entity.HttpConifguration;


@Component("apachePoolingHttpClientConnectionManager")
public class ApachePoolingHttpClientConnectionManager
{

    HttpMessageParserFactory<HttpResponse> responseParserFactory = new DefaultHttpResponseParserFactory()
    {

        @Override
        public HttpMessageParser<HttpResponse> create(SessionInputBuffer buffer,
                                                      MessageConstraints constraints)
        {
            LineParser lineParser = new BasicLineParser()
            {

                @Override
                public Header parseHeader(final CharArrayBuffer buffer)
                {
                    try
                    {
                        return super.parseHeader(buffer);
                    }
                    catch (ParseException ex)
                    {
                        return new BasicHeader(buffer.toString(), null);
                    }
                }

            };
            return new DefaultHttpResponseParser(buffer, lineParser,
                DefaultHttpResponseFactory.INSTANCE, constraints)
            {

                @Override
                protected boolean reject(final CharArrayBuffer line, int count)
                {
                    // try to ignore all garbage preceding a status line infinitely
                    return false;
                }

            };
        }

    };

    HttpMessageWriterFactory<HttpRequest> requestWriterFactory = new DefaultHttpRequestWriterFactory();

    HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory = new ManagedHttpClientConnectionFactory(
        requestWriterFactory, responseParserFactory);

    @Autowired
    ApacheConnectionConfig apacheConnectionConfig;

    
    // Create socket configuration
    SocketConfig defaultSocketConfig = SocketConfig.custom().setTcpNoDelay(true).build();

    @Autowired
    ApacheSSLConnectionSocketFactory apacheSSLConnectionSocketFactory;

    public PoolingHttpClientConnectionManager createConnMananger(HttpConifguration httpConfiguration)
    {
        RegistryBuilder<ConnectionSocketFactory> builder = RegistryBuilder.<ConnectionSocketFactory> create();
        builder.register(HttpHost.DEFAULT_SCHEME_NAME, PlainConnectionSocketFactory.INSTANCE);
        if (BeanUtils.isNotNull(apacheSSLConnectionSocketFactory))
        {
            SSLConnectionSocketFactory sslConnectionSocketFactory = apacheSSLConnectionSocketFactory.create();
            builder.register("https", sslConnectionSocketFactory);
        }
        Registry<ConnectionSocketFactory> socketFactoryRegistry = builder.build();
        
        //Registry<ConnectionSocketFactory> socketFactoryRegistry2 = ApacheRegistry.create(PlainConnectionSocketFactory.INSTANCE, sslConnectionSocketFactory);
            
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
            socketFactoryRegistry, connFactory, ApacheDnsResolver.dnsResolver);
        connManager.setDefaultSocketConfig(defaultSocketConfig);
        connManager.setDefaultConnectionConfig(apacheConnectionConfig.create());
        if (httpConfiguration.isSocketHostSettable())
        {
            connManager.setSocketConfig(httpConfiguration.getSocketHost(), defaultSocketConfig);
            connManager.setConnectionConfig(httpConfiguration.getSocketHost(), ConnectionConfig.DEFAULT);
            connManager.setMaxPerRoute(new HttpRoute(httpConfiguration.getSocketHost()), httpConfiguration.getMaxConnTotal());
        }
        connManager.setMaxTotal(httpConfiguration.getMaxConnTotal());
        connManager.setDefaultMaxPerRoute(httpConfiguration.getMaxConnTotal());
        return connManager;
    }

}
