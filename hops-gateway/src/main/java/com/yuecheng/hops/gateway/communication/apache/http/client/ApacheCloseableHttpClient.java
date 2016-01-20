/*
 * 文件名：ApacheCloseableHttpClient.java 版权：Copyright by www.365haoyou.com 描述： 修改人：yangyi
 * 修改时间：2014年10月16日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.gateway.communication.apache.http.client;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
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
import com.yuecheng.hops.gateway.communication.apache.http.ApacheHttpUriRequest;
import com.yuecheng.hops.gateway.communication.apache.http.ApachePoolingHttpClientConnectionManager;
import com.yuecheng.hops.gateway.communication.protocal.http.HttpClient;
import com.yuecheng.hops.gateway.entity.HttpConifguration;
import com.yuecheng.hops.gateway.message.AbstractMessage;
import com.yuecheng.hops.gateway.message.AbstractMessageSender;
import com.yuecheng.hops.gateway.message.HttpRequestMessage;
import com.yuecheng.hops.gateway.message.HttpResponseMessage;


@Component("apacheCloseableHttpClient")
public class ApacheCloseableHttpClient extends AbstractMessageSender implements HttpClient
{
    private static Logger logger = LoggerFactory.getLogger(ApacheCloseableHttpClient.class);

    @Autowired
    ApacheHttpUriRequest apacheHttpUriRequest;

    @Autowired
    ApacheCloseableHttpClientFactory apacheCloseableHttpClientFactory;

    @Autowired
    ApachePoolingHttpClientConnectionManager apachePoolingHttpClientConnectionManager;

    private CloseableHttpClient httpclient;
    
    private Map<Integer, CloseableHttpClient> httpClientMap = new HashMap<Integer, CloseableHttpClient>();

    @PostConstruct
    public void start()
        throws IOReactorException
    {
        // httpclient = apacheCloseableHttpAsyncClientFactory.createClient(null, null,
        // apachePoolingNHttpClientConnectionManager.createConnectionManager(null,null));
        HttpClientBuilder builder = HttpClients.custom();
        builder.setConnectionManager(apachePoolingHttpClientConnectionManager.createConnMananger(new HttpConifguration()));
        builder.setConnectionReuseStrategy(DefaultConnectionReuseStrategy.INSTANCE);
//        builder.setConnectionReuseStrategy(NoConnectionReuseStrategy.INSTANCE);
        builder.setConnectionManagerShared(true);
        httpclient = builder.build();
    }

    // Create a custom response handler
    private ResponseHandler<HttpResponseMessage> responseHandler = new ResponseHandler<HttpResponseMessage>()
    {

        @Override
        public HttpResponseMessage handleResponse(HttpResponse response)
            throws ClientProtocolException, IOException
        {
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300)
            {
                HttpEntity entity = response.getEntity();
                if (BeanUtils.isNull(entity))
                {
                    throw new ClientProtocolException("Unexpected response entity: "
                                                      + EntityUtils.toString(entity));
                }
                HttpResponseMessage responseMessage = new HttpResponseMessage();
                for (Header header : response.getAllHeaders())
                {
                    logger.debug("HeadName:[" + header.getName() + "]  HeadValue:["
                                 + header.getValue() + "]");
                    if ("Content-Type".equalsIgnoreCase(header.getName()))
                    {
                        if (!header.getValue().contains(";"))
                        {
                            String[] contentType = StringUtil.split(header.getValue(), ";");
                            responseMessage.setContentType(contentType[0]);
                            responseMessage.setEncoding("utf-8");
                        }
                        else
                        {
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

    };

    @Override
    public AbstractMessage send(AbstractMessage sendMessage)
    {
        HttpResponseMessage responseBody = null;
        try
        {
            HttpConifguration httpConfiguration = new HttpConifguration();
            apacheHttpUriRequest.setHttpConfiguration(httpConfiguration);
            HttpUriRequest request = apacheHttpUriRequest.createHttpUriRequest((HttpRequestMessage)sendMessage);

            try
            {
                logger.info("sendMessage:[" + sendMessage + "]");
                responseBody = httpclient.execute(request, responseHandler);
                logger.info("responseBody:[" + responseBody + "]");
            }
            catch (Exception e)
            {
                logger.error(ExceptionUtil.getStackTraceAsString(e));
            }
        }
        catch (Exception e1)
        {
            logger.error(ExceptionUtil.getStackTraceAsString(e1));
        }
        return responseBody;
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
