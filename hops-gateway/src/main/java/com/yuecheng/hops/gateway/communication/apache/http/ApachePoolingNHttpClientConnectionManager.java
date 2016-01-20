/*
 * 文件名：ApachePoolingNHttpClientConnectionManager.java 版权：Copyright by www.365haoyou.com 描述：
 * 修改人：yangyi 修改时间：2014年10月16日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.gateway.communication.apache.http;


import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.nio.codecs.DefaultHttpRequestWriterFactory;
import org.apache.http.impl.nio.codecs.DefaultHttpResponseParser;
import org.apache.http.impl.nio.codecs.DefaultHttpResponseParserFactory;
import org.apache.http.impl.nio.conn.ManagedNHttpClientConnectionFactory;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicLineParser;
import org.apache.http.message.LineParser;
import org.apache.http.nio.NHttpMessageParser;
import org.apache.http.nio.NHttpMessageParserFactory;
import org.apache.http.nio.NHttpMessageWriterFactory;
import org.apache.http.nio.conn.ManagedNHttpClientConnection;
import org.apache.http.nio.conn.NHttpConnectionFactory;
import org.apache.http.nio.conn.NoopIOSessionStrategy;
import org.apache.http.nio.conn.SchemeIOSessionStrategy;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.nio.reactor.SessionInputBuffer;
import org.apache.http.nio.util.HeapByteBufferAllocator;
import org.apache.http.util.CharArrayBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuecheng.hops.gateway.entity.HttpConifguration;


@Component("apachePoolingNHttpClientConnectionManager")
public class ApachePoolingNHttpClientConnectionManager
{
    NHttpMessageParserFactory<HttpResponse> responseParserFactory = new DefaultHttpResponseParserFactory()
    {

        @Override
        public NHttpMessageParser<HttpResponse> create(final SessionInputBuffer buffer,
                                                       final MessageConstraints constraints)
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
                DefaultHttpResponseFactory.INSTANCE, constraints);
        }

    };

    NHttpMessageWriterFactory<HttpRequest> requestWriterFactory = new DefaultHttpRequestWriterFactory();

    @Autowired
    ApacheConnectionConfig apacheConnectionConfig;

    @Autowired
    ApacheIOReactorConfig apacheIOReactorConfig;

    @Autowired
    ApacheSSLIOSessionStrategy apacheSSLIOSessionStrategy;

    private static Integer MAX_TOTAL_CONN = 10000;

    private static Integer MaxPerRoute = 20;

    public PoolingNHttpClientConnectionManager createConnectionManager(HttpConifguration httpConfiguration)
        throws IOReactorException
    {
        RegistryBuilder<SchemeIOSessionStrategy> builder = RegistryBuilder.<SchemeIOSessionStrategy> create();
        builder.register(HttpHost.DEFAULT_SCHEME_NAME, NoopIOSessionStrategy.INSTANCE);
//        if (BeanUtils.isNotNull(sSLIOSessionStrategy))
//        {
            builder.register("https", apacheSSLIOSessionStrategy.create());
//        }
        Registry<SchemeIOSessionStrategy> sessionStrategyRegistry = builder.build();

        // Registry<SchemeIOSessionStrategy> sessionStrategyRegistry2 =
        // ApacheRegistry.create(NoopIOSessionStrategy.INSTANCE, sSLIOSessionStrategy);
        ConnectingIOReactor connectingIOReactor = new DefaultConnectingIOReactor(
            apacheIOReactorConfig.create(null, null));
        NHttpConnectionFactory<ManagedNHttpClientConnection> connFactory = new ManagedNHttpClientConnectionFactory(
            requestWriterFactory, responseParserFactory, HeapByteBufferAllocator.INSTANCE);
        PoolingNHttpClientConnectionManager connManager = new PoolingNHttpClientConnectionManager(
            connectingIOReactor, connFactory, sessionStrategyRegistry,
            ApacheDnsResolver.dnsResolver);
        connManager.setDefaultConnectionConfig(apacheConnectionConfig.create());
        if (httpConfiguration.isSocketHostSettable())
        {
            connManager.setConnectionConfig(httpConfiguration.getSocketHost(),  ApacheConnectionConfig.create());
            connManager.setMaxPerRoute(new HttpRoute(httpConfiguration.getSocketHost()), httpConfiguration.getMaxConnTotal());
        }
        connManager.setMaxTotal(httpConfiguration.getMaxConnTotal());
        connManager.setDefaultMaxPerRoute(httpConfiguration.getMaxConnTotal());
        return connManager;
    }
}
