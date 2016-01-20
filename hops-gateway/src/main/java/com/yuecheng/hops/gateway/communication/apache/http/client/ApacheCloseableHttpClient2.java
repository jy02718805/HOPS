/*
 * 文件名：ApacheCloseableHttpClient.java 版权：Copyright by www.365haoyou.com 描述： 修改人：yangyi 修改时间：2014年10月16日 跟踪单号：
 * 修改单号： 修改内容：
 */

package com.yuecheng.hops.gateway.communication.apache.http.client;


import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.gateway.communication.apache.http.ApacheHttpClientContext;
import com.yuecheng.hops.gateway.communication.apache.http.ApacheHttpUriRequest;
import com.yuecheng.hops.gateway.communication.protocal.http.HttpClient;
import com.yuecheng.hops.gateway.entity.HttpConifguration;
import com.yuecheng.hops.gateway.message.AbstractMessage;
import com.yuecheng.hops.gateway.message.AbstractMessageSender;
import com.yuecheng.hops.gateway.message.HttpRequestMessage;
import com.yuecheng.hops.gateway.message.HttpResponseMessage;


@Component("apacheCloseableHttpClient2")
public class ApacheCloseableHttpClient2 extends AbstractMessageSender implements HttpClient
{
    private static Logger logger = LoggerFactory.getLogger(ApacheCloseableHttpClient2.class);

    @Autowired
    ApacheHttpUriRequest apacheHttpUriRequest;
    
    @Autowired
    ApacheHttpClientContext apacheHttpClientContext;

    @Autowired
    ApacheCloseableHttpClientFactory apacheCloseableHttpClientFactory;
    
    CookieStore cookieStore = new BasicCookieStore();

    CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

    public void sendWithContext()
        throws IOException
    {
        HttpConifguration httpConifguration = new HttpConifguration();
        CloseableHttpClient httpclient = apacheCloseableHttpClientFactory.createClient(httpConifguration,cookieStore, credentialsProvider);
        //CloseableHttpClient httpclient = HttpClients.createDefault();
        try
        {
            HttpRequestMessage requestMessage = new HttpRequestMessage();
            HttpUriRequest httpUriRequest = apacheHttpUriRequest.createHttpUriRequest(requestMessage);
            HttpClientContext context = apacheHttpClientContext.createContext(cookieStore,
                credentialsProvider);

            logger.info("executing request " + httpUriRequest.getURI());
            CloseableHttpResponse response = httpclient.execute(httpUriRequest, context);

            try
            {
                HttpEntity entity = response.getEntity();

                // Once the request has been executed the local context can
                // be used to examine updated state and various objects affected
                // by the request execution.

                // Last executed request
                context.getRequest();
                // Execution route
                context.getHttpRoute();
                // Target auth state
                context.getTargetAuthState();
                // Proxy auth state
                context.getTargetAuthState();
                // Cookie origin
                context.getCookieOrigin();
                // Cookie spec used
                context.getCookieSpec();
                // User security token
                context.getUserToken();

            }
            finally
            {
                response.close();
            }
        }
        finally
        {
            httpclient.close();
        }
    }
    
    // Create a custom response handler
    private ResponseHandler<HttpResponseMessage> responseHandler = new ResponseHandler<HttpResponseMessage>() {

        @Override
        public HttpResponseMessage handleResponse(HttpResponse response)
            throws ClientProtocolException, IOException
        {
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                
                String message = entity != null ? EntityUtils.toString(entity) : null;
                Header encodingHeader = response.getFirstHeader("Encoding");
                Header contentType = response.getFirstHeader("Content-Type");
                return new HttpResponseMessage(encodingHeader.getValue(), message, contentType.getValue());
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        }

    };

    @Override
    public AbstractMessage send(AbstractMessage sendMessage)
    {
        HttpConifguration httpConifguration = new HttpConifguration();
        CloseableHttpClient httpclient = apacheCloseableHttpClientFactory.createClient(httpConifguration, cookieStore, credentialsProvider);
        //CloseableHttpClient httpclient = HttpClients.createDefault();
        
        HttpResponseMessage responseBody = null;
        try
        {
            HttpConifguration httpConfiguration = new  HttpConifguration();
            apacheHttpUriRequest.setHttpConfiguration(httpConfiguration);
            HttpUriRequest httpUriRequest = apacheHttpUriRequest.createHttpUriRequest((HttpRequestMessage)sendMessage);
            
            
            try
            {
                responseBody = httpclient.execute(httpUriRequest, responseHandler);
            }
            catch (ClientProtocolException e)
            {
                logger.error(ExceptionUtil.getStackTraceAsString(e));
            }
            catch (IOException e)
            {
                logger.error(ExceptionUtil.getStackTraceAsString(e));
            }
            logger.info(String.valueOf(responseBody).toString());
        } finally {
            try
            {
                httpclient.close();
            }
            catch (IOException e)
            {
                logger.error(ExceptionUtil.getStackTraceAsString(e));
            }
        }
        return responseBody;
    }
}
