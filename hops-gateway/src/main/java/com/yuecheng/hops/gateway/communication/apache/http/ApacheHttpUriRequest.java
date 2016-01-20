/*
 * 文件名：ApacheHttpGet.java 版权：Copyright by www.365haoyou.com 描述： 修改人：yangyi 修改时间：2014年10月16日 跟踪单号：
 * 修改单号： 修改内容：
 */

package com.yuecheng.hops.gateway.communication.apache.http;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.gateway.entity.HttpConifguration;
import com.yuecheng.hops.gateway.message.HttpRequestMessage;


@Component("apacheHttpUriRequest")
public class ApacheHttpUriRequest
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ApacheHttpUriRequest.class);

    private HttpConifguration httpConfiguration;

    public void setHttpConfiguration(HttpConifguration httpConfiguration)
    {
        this.httpConfiguration = httpConfiguration;
    }

    public HttpUriRequest createHttpUriRequest(HttpRequestMessage message)
    {
        HttpUriRequest request = null;
        if (HttpMethod.GET == message.getMethod())
        {
            request = getGetMethodRequest(message);
        }
        else
        {
            request = getPostMethodRequest(message);
        }
        return request;
    }

    @SuppressWarnings("deprecation")
    public HttpUriRequest getPostMethodRequest(HttpRequestMessage message)
    {
        HttpPost httpPost = new HttpPost(message.getUri());
        AbstractHttpEntity myEntity = null;
        if (Constant.Interface.PACKET_TYPE_TEXT.equalsIgnoreCase(message.getContentType())
            || Constant.Interface.PACKET_TYPE_FORM.equalsIgnoreCase(message.getContentType()))
        {
            String url = message.getUri() + "?" + message.getMessage().replaceAll(" ", "%20");
            httpPost = new HttpPost(url);
            myEntity = new StringEntity(message.getMessage(), message.getEncoding());
        }
        else if (Constant.Interface.PACKET_TYPE_XML.equalsIgnoreCase(message.getContentType())
                 || Constant.Interface.PACKET_TYPE_JSON.equalsIgnoreCase(message.getContentType()))
        {
            myEntity = new StringEntity(message.getMessage(), message.getEncoding());
            myEntity.setContentType(message.getContentType());

        }
        if (StringUtil.isNotBlank(message.getEncoding()))
        {
            myEntity.setContentEncoding(message.getEncoding());
            httpPost.setHeader(Constant.Common.CONTENT_ENCODING, message.getEncoding());
        }
        else
        {
            myEntity.setContentEncoding(HTTP.UTF_8);
            httpPost.setHeader(Constant.Common.CONTENT_ENCODING, HTTP.UTF_8);
        }

        httpPost.setHeader("Content-Type", message.getContentType());
        httpPost.setEntity(myEntity);
        if (BeanUtils.isNull(httpConfiguration))
        {
            httpPost.setConfig(ApacheRequestConfig.defaultRequestConfig);
        }
        else
        {
            ApacheRequestConfig apacheRequestConfig = new ApacheRequestConfig(httpConfiguration);
            httpPost.setConfig(apacheRequestConfig.getRequestConfig());
        }
        return httpPost;
    }

    public HttpUriRequest getGetMethodRequest(HttpRequestMessage message)
    {
        HttpGet httpGet = new HttpGet(message.getUri());
        if (Constant.Interface.PACKET_TYPE_TEXT.equalsIgnoreCase(message.getContentType()))
        {
            httpGet = new HttpGet(message.getUri() + "?" + message.getMessage());
        }
        else if (Constant.Interface.PACKET_TYPE_JSON.equalsIgnoreCase(message.getContentType()))
        {
            String msg = "";
            try
            {
                msg = URLEncoder.encode(message.getMessage(), message.getEncoding());
            }
            catch (UnsupportedEncodingException e)
            {
                LOGGER.error("URLEncoder.encode(message) 报错: [" + message.getMessage() + "]");
                e.printStackTrace();
            }
            httpGet = new HttpGet(message.getUri() + "?" + msg);
        }
        httpGet.setHeader("Content-Type", message.getContentType());
        if (BeanUtils.isNull(httpConfiguration))
        {
            httpGet.setConfig(ApacheRequestConfig.defaultRequestConfig);
        }
        else
        {
            ApacheRequestConfig apacheRequestConfig = new ApacheRequestConfig(httpConfiguration);
            httpGet.setConfig(apacheRequestConfig.getRequestConfig());
        }
        return httpGet;
    }
}