/*
 * 文件名：ApacheCloseableHttpAsyncClient.java 版权：Copyright by www.365haoyou.com 描述： 修改人：yangyi
 * 修改时间：2014年10月16日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.gateway.communication.apache.http.client;


import java.io.IOException;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.gateway.communication.apache.http.ApacheHttpClientContext;
import com.yuecheng.hops.gateway.communication.apache.http.ApacheHttpUriRequest;
import com.yuecheng.hops.gateway.communication.apache.http.ApachePoolingNHttpClientConnectionManager;
import com.yuecheng.hops.gateway.communication.apache.http.ApacheRequestConfig;
import com.yuecheng.hops.gateway.communication.protocal.http.HttpClient;
import com.yuecheng.hops.gateway.entity.HttpConifguration;
import com.yuecheng.hops.gateway.message.AbstractMessage;
import com.yuecheng.hops.gateway.message.AbstractMessageSender;
import com.yuecheng.hops.gateway.message.HttpRequestMessage;
import com.yuecheng.hops.gateway.message.HttpResponseMessage;


@Component("apacheCloseableHttpAsyncClient")
public class ApacheCloseableHttpAsyncClient extends AbstractMessageSender  implements HttpClient
{
    private static final Logger logger = LoggerFactory.getLogger(ApacheCloseableHttpAsyncClient.class);
    
    @Autowired
    ApacheHttpUriRequest apacheHttpUriRequest;

    @Autowired
    ApacheHttpClientContext apacheHttpClientContext;
    
    CloseableHttpAsyncClient httpclient = null;
    
    @Autowired
    ApacheCloseableHttpAsyncClientFactory apacheCloseableHttpAsyncClientFactory;

    // Use custom cookie store if necessary.
    CookieStore cookieStore = new BasicCookieStore();

    // Use custom credentials provider if necessary.
    CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    
    @Autowired
    ApachePoolingNHttpClientConnectionManager apachePoolingNHttpClientConnectionManager;
    
    @PostConstruct
    public void start()
    {
        try
        {
//            httpclient =  apacheCloseableHttpAsyncClientFactory.createClient(null, null, apachePoolingNHttpClientConnectionManager.createConnectionManager(null,null));
            httpclient = HttpAsyncClients.custom()
            .setConnectionManager(apachePoolingNHttpClientConnectionManager.createConnectionManager(new HttpConifguration()))
            .setDefaultCookieStore(cookieStore)
            .setDefaultCredentialsProvider(credentialsProvider)
            .setConnectionManagerShared(true)
            .setConnectionReuseStrategy(new DefaultConnectionReuseStrategy())
            .setDefaultRequestConfig(ApacheRequestConfig.defaultRequestConfig)
            .build();
            httpclient.start();
        }
        catch (IOReactorException e)
        {
            logger.error("failed to start the client caused by:"+ ExceptionUtil.getStackTraceAsString(e));
        }
        
    }

    @Override
    public AbstractMessage send(AbstractMessage sendMessage)
    {
        try
        {
            HttpRequestMessage requestMessage = (HttpRequestMessage) sendMessage;
            HttpUriRequest httpUriRequest = apacheHttpUriRequest.createHttpUriRequest(requestMessage);
            logger.info("sendMessage:["+sendMessage+"]");
//            HttpAsyncRequestProducer asyncRequestProducer = HttpAsyncMethods.create(httpUriRequest);
            Future<HttpResponse> future = httpclient.execute(httpUriRequest,null);
            HttpResponse response = future.get();
            AbstractMessage httpResponse =  handleResponse(response);
            logger.info("responseBody:["+httpResponse+"]");
            return httpResponse;
        }
        catch (Exception e){
            logger.error(ExceptionUtil.getStackTraceAsString(e));
            throw new ApplicationException("gateway00007", new String[]{ExceptionUtil.getStackTraceAsString(e)});
        }
    }
    
    public HttpResponseMessage handleResponse(HttpResponse response)
        throws ClientProtocolException, IOException
    {
        int status = response.getStatusLine().getStatusCode();
        if (status >= 200 && status < 300)
        {
            HttpEntity entity = response.getEntity();
            if(BeanUtils.isNull(entity))
            {
                throw new ClientProtocolException("Unexpected response entity: " + EntityUtils.toString(entity));
            }
            HttpResponseMessage responseMessage = new HttpResponseMessage();
            for (Header header : response.getAllHeaders())
            {
                logger.debug("HeadName:["+header.getName()+"]  HeadValue:["+header.getValue()+"]");
                if ("Content-Type".equalsIgnoreCase(header.getName()))
                {
                    if (!header.getValue().contains(";"))
                    {
                        String[] contentType = StringUtil.split(header.getValue(), ";");
                        responseMessage.setContentType(contentType[0]);
                        responseMessage.setEncoding("utf-8");
                    }
                    else {
                        String[] contentType = StringUtil.split(header.getValue(), ";");
                        responseMessage.setContentType(contentType[0]);
                        String[] encoding = StringUtil.split(contentType[1], "=");
                        responseMessage.setEncoding(encoding[1]);
                    }
                }
            }
            responseMessage.setProtocolVersion(response.getProtocolVersion().toString());
            responseMessage.setStatusCode(response.getStatusLine().getStatusCode());
            responseMessage.setStatusDesc(response.getStatusLine().getReasonPhrase());
            responseMessage.setMessage(EntityUtils.toString(entity));
            return responseMessage;
        }
        else
        {
            throw new ClientProtocolException("Unexpected response status: " + status);
        }
    }
    
    @PreDestroy
    public void stop()
    {
        try
        {
            httpclient.close();
        }
        catch (Exception e)
        {
            logger.error("failed to close the http client caused by:"+ExceptionUtil.getStackTraceAsString(e));
            throw new ApplicationException("000000");
        }
    }
}
