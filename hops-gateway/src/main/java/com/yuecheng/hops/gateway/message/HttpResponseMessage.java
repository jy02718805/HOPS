package com.yuecheng.hops.gateway.message;


import java.io.Serializable;

import com.yuecheng.hops.gateway.GatewayConstant;


public class HttpResponseMessage extends AbstractMessage implements Serializable
{

    public HttpResponseMessage()
    {
        this.protocalType = GatewayConstant.SECHME_HTTP;
    }

    public HttpResponseMessage(String encoding, String message, String contentType)
    {
        this.protocalType = GatewayConstant.SECHME_HTTP;
        this.encoding = encoding;
        this.message = message;
        this.contentType = contentType;
    }

    private static final long serialVersionUID = 8039119973278978395L;

    private String encoding;

    private String message;

    private String contentType;

    private String protocolVersion;

    private int statusCode;

    private String statusDesc;

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

    public String getProtocolVersion()
    {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion)
    {
        this.protocolVersion = protocolVersion;
    }

    public int getStatusCode()
    {
        return statusCode;
    }

    public void setStatusCode(int statusCode)
    {
        this.statusCode = statusCode;
    }

    public String getStatusDesc()
    {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc)
    {
        this.statusDesc = statusDesc;
    }

    @Override
    public String toString()
    {
        return "HttpResponseMessage [statusCode=" + statusCode + ", statusDesc=" + statusDesc
               + ", protocolVersion=" + protocolVersion + ", encoding=" + encoding
               + ", contentType=" + contentType + ", message=" + message + "]";
    }
}
