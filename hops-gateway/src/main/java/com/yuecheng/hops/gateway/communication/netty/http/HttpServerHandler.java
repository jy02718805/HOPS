package com.yuecheng.hops.gateway.communication.netty.http;


import static org.jboss.netty.handler.codec.http.HttpHeaders.is100ContinueExpected;
import static org.jboss.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.OK;
import static org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.protocol.HTTP;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpHeaders.Values;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.multipart.Attribute;
import org.jboss.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import org.jboss.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import org.jboss.netty.handler.codec.http.multipart.InterfaceHttpData;
import org.jboss.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;
import org.jboss.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.gateway.GatewayConstant;
import com.yuecheng.hops.gateway.GatewayContextUtil;
import com.yuecheng.hops.gateway.message.HttpRequestMessage;
import com.yuecheng.hops.gateway.message.HttpResponseMessage;
import com.yuecheng.hops.gateway.message.MessageReceiver;
import com.yuecheng.hops.gateway.message.MessageReceiverDirector;


public class HttpServerHandler extends SimpleChannelUpstreamHandler
{
    private static Logger logger = LoggerFactory.getLogger(HttpServerHandler.class);

    private static final String URI_BLANK_STR = "?";

    private static final String URI_SPLITE_CHAR = "\\?";

    @Autowired
    private MessageReceiverDirector messageReceiverDirector;

    @SuppressWarnings("deprecation")
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
        throws Exception
    {
        HttpRequestMessage requestMessage = new HttpRequestMessage();
        HttpRequest request = (HttpRequest)e.getMessage();
        String contentType = request.getHeader(Constant.Common.CONTENT_TYPE);
        Channel ch = e.getChannel();
        if (is100ContinueExpected(request))
        {
            Channels.write(ctx, Channels.future(ch), new DefaultHttpResponse(HTTP_1_1, CONTINUE));
        }
        boolean keepAlive = isKeepAlive(request);

        String requestString = request.getUri();
        logger.info("received  from uri requestString [" + requestString + "]");
        SocketAddress remoteAddress = ctx.getChannel().getRemoteAddress();
        String requestUri = null;
        String requestMessageBody = null;

        if (StringUtil.isNotBlank(requestString)
            && StringUtil.contains(requestString, URI_BLANK_STR))
        {
            String[] requestFields = requestString.split(URI_SPLITE_CHAR);
            if (requestFields.length >= 2)
            {
                requestUri = requestFields[0];
                requestMessageBody = requestFields[1];
            }

        }
        else if (request.getMethod() == HttpMethod.POST)
        {
            String contentEncode = request.getHeader(Constant.Common.CONTENT_ENCODING);
            requestUri = requestString;
            if (Constant.Interface.PACKET_TYPE_JSON.equalsIgnoreCase(contentType))
            {
                if (StringUtil.isNotBlank(contentEncode))
                {
                    requestMessageBody = new String(request.getContent().array(), contentEncode);
                }
                else
                {
                    requestMessageBody = new String(request.getContent().array(), HTTP.UTF_8);
                }

            }
            else
            {
                HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(
                    new DefaultHttpDataFactory(false), request);
                // 读取从客户端传过来的参数
                InterfaceHttpData postData = decoder.getBodyHttpData("req");
                if (BeanUtils.isNotNull(postData))
                {
                    if (postData.getHttpDataType() == HttpDataType.Attribute)
                    {
                        Attribute attribute = (Attribute)postData;
                        requestMessageBody = attribute.getValue();

                    }
                }
                else
                {
                    List<InterfaceHttpData> list = decoder.getBodyHttpDatas();
                    requestMessageBody = keyValueToString(list);
                }
            }

        }

        if (StringUtil.isBlank(request.getHeader("charset")))
        {
            requestMessage.setEncoding(request.getHeader(Constant.Common.CONTENT_ENCODING));
        }
        else
        {
            requestMessage.setEncoding(request.getHeader("charset"));
        }

        logger.info("received  from channel" + e.getChannel() + " with http request:"
                    + String.valueOf(requestMessageBody).toString());
        GatewayContextUtil.setParameter(GatewayConstant.GATEWAT_CONTEXT_KEY_URI, requestUri);
        requestMessage.setContentType(request.getHeader(CONTENT_TYPE));
        requestMessage.setMessage(requestMessageBody);
        requestMessage.setUri(requestUri);

        logger.debug("Get  message Receiver" + e.getChannel() + " with http request:"
                     + String.valueOf(requestMessage).toString());
        MessageReceiver messageReceiver = messageReceiverDirector.create(requestMessage);
        logger.debug("begin to  message process " + e.getChannel() + " with http request:"
                     + String.valueOf(requestMessage).toString());
        HttpResponseMessage resultMessage = (HttpResponseMessage)messageReceiver.recieve(requestMessage);

        logger.debug("resultMessage:" + String.valueOf(resultMessage).toString());
        HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);
        Charset charset = null == resultMessage.getEncoding() ? CharsetUtil.UTF_8 : Charset.forName(resultMessage.getEncoding());
        response.setContent(ChannelBuffers.copiedBuffer(
            null == resultMessage.getMessage() ? "null" : resultMessage.getMessage(), charset));
        response.setHeader(HttpHeaders.Names.CONTENT_TYPE, resultMessage.getContentType()
                                                           + "; charset=" + charset);
        response.setHeader(HttpHeaders.Names.CONTENT_LENGTH, response.getContent().readableBytes());

        if (!keepAlive)
        {
            ChannelFuture f = Channels.future(ch);
            f.addListener(ChannelFutureListener.CLOSE);
            Channels.write(ctx, f, response);
        }
        else
        {
            response.headers().set(CONNECTION, Values.KEEP_ALIVE);
            Channels.write(ctx, Channels.future(ch), response);
        }

        logger.info("write reponse to channel" + e.getChannel() + " with http reponse:"
                    + String.valueOf(response).toString());

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
        throws Exception
    {
        logger.error(Thread.currentThread().getName() + ":channel id:" + e.getChannel()
                     + ":exception:"
                     + ExceptionUtil.getStackTraceAsString((Exception)e.getCause()));
        e.getChannel().close();
    }

    public static String keyValueToString(List<InterfaceHttpData> param)
        throws IOException
    {
        String sret = "";
        for (InterfaceHttpData postData : param)
        {
            String key = "";
            String value = "";
            if (postData.getHttpDataType() == HttpDataType.Attribute)
            {
                Attribute attribute = (Attribute)postData;
                key = postData.getName();
                value = attribute.getValue();
            }
            if (!StringUtil.isNullOrEmpty(key))
            {
                if (sret.length() <= 0)
                {
                    sret += key + "=" + value;
                }
                else
                {
                    sret += "&" + key + "=" + value;
                }
            }
        }
        return sret;
    }

}
