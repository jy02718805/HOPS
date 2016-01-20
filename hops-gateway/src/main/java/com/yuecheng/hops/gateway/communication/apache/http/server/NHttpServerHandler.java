/*
 * 文件名：NHttpServerHandler.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：yangyi
 * 修改时间：2014年12月17日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.gateway.communication.apache.http.server;


import java.io.IOException;
import java.util.Locale;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.nio.protocol.BasicAsyncRequestConsumer;
import org.apache.http.nio.protocol.BasicAsyncResponseProducer;
import org.apache.http.nio.protocol.HttpAsyncExchange;
import org.apache.http.nio.protocol.HttpAsyncRequestConsumer;
import org.apache.http.nio.protocol.HttpAsyncRequestHandler;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuecheng.hops.gateway.message.HttpRequestMessage;
import com.yuecheng.hops.gateway.message.HttpResponseMessage;
import com.yuecheng.hops.gateway.message.MessageReceiver;
import com.yuecheng.hops.gateway.message.MessageReceiverDirector;

@Component("nHttpServerHandler")
public class NHttpServerHandler implements HttpAsyncRequestHandler<HttpRequest>
{
    private static Logger logger = LoggerFactory.getLogger(NHttpServerHandler.class);
    
    @Autowired
    private MessageReceiverDirector messageReceiverDirector;

    
    @Override
    public HttpAsyncRequestConsumer<HttpRequest> processRequest(HttpRequest request,
                                                                HttpContext context)
        throws HttpException, IOException
    {
        return new BasicAsyncRequestConsumer();
    }

    @Override
    public void handle(HttpRequest request, HttpAsyncExchange httpExchange, HttpContext context)
        throws HttpException, IOException
    {
        HttpResponse response = httpExchange.getResponse();
        handleInternal(request, response, context);
        httpExchange.submitResponse(new BasicAsyncResponseProducer(response));
    }
    
    private void handleInternal(final HttpRequest request,
                                final HttpResponse response,
                                final HttpContext context) throws HttpException, IOException {
        HttpCoreContext coreContext = HttpCoreContext.adapt(context);

        String method = request.getRequestLine().getMethod().toUpperCase(Locale.ENGLISH);
        if (!method.equals("GET") && !method.equals("HEAD") && !method.equals("POST")) {
            throw new MethodNotSupportedException(method + " method not supported");
        }
        String target = request.getRequestLine().getUri();
        
        HttpRequestMessage requestMessage = new HttpRequestMessage();
        String contentType = request.getHeaders("ContentType")[0].getValue();
        requestMessage.setContentType(contentType);
        String charset = request.getHeaders("charset")[0].getValue();
        requestMessage.setEncoding(charset);
        requestMessage.setMessage(request.getRequestLine().getMethod().getBytes(charset).toString());
        requestMessage.setUri(target);

        MessageReceiver messageReceiver = messageReceiverDirector.create(requestMessage);
        logger.debug("begin to  message process  with http request:"+ String.valueOf(requestMessage).toString());
        HttpResponseMessage resultMessage = (HttpResponseMessage)messageReceiver.recieve(requestMessage);
        logger.debug("resultMessage:" + String.valueOf(resultMessage).toString());
        
        AbstractHttpEntity entity = new NStringEntity(resultMessage.getMessage(), resultMessage.getEncoding());
        response.setStatusCode(HttpStatus.SC_OK);
        response.setEntity(entity);
    }
}
