package com.yuecheng.hops.gateway.message;


import java.io.Serializable;

import org.jboss.netty.handler.codec.http.HttpMethod;

import com.yuecheng.hops.gateway.GatewayConstant;


public class HttpRequestMessage extends AbstractMessage implements Serializable
{

    private static final long serialVersionUID = 8039119973278978395L;

    private String uri;

    private String encoding;

    private String message;

    private String contentType;
    
    private transient HttpMethod method;

    public HttpRequestMessage()
    {
        this.protocalType = GatewayConstant.SECHME_HTTP;
    }

    public HttpRequestMessage(String uri, String encoding, String message, String contentType)
    {
        this.protocalType = GatewayConstant.SECHME_HTTP;
        this.uri = uri;
        this.encoding = encoding;
        this.message = message;
        this.contentType = contentType;
    }

	public HttpMethod getMethod()
	{
		return method;
	}

	public void setMethod(HttpMethod method)
	{
		this.method = method;
	}

	public String getUri()
    {
        return uri;
    }

    public void setUri(String uri)
    {
        this.uri = uri;
    }

    public String getEncoding()
    {
        return encoding;
    }

    public void setEncoding(String encoding)
    {
        this.encoding = encoding;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getContentType()
    {
        return contentType;
    }

    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }
}
