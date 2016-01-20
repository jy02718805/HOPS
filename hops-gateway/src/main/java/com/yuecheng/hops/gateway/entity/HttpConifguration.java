/*
 * 文件名：HttpConifguration.java 版权：Copyright by www.365haoyou.com 描述： 修改人：yangyi 修改时间：2014年10月16日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.gateway.entity;


import java.io.Serializable;

import org.apache.http.HttpHost;


public class HttpConifguration implements Serializable
{
    private static final long serialVersionUID = -9074118922464070929L;

    public static final HttpHost DEFAULT_PROXY_HOST = new HttpHost("myotherproxy", 8080);

    public static final int DEFAULT_SOCKET_TIME_OUT = 5000;

    public static final int DEFAULT_CONNECT_TIME_OUT = 5000;

    public static final int DEFAULT_CONNECTION_REQUEST_TIME_OUT = 5000;
    
    public static final Integer DEFAULT_MAX_CONN_TOTAL = 10000;

    private boolean sslEnable = false;

    private String url;
    
    private Integer maxConnTotal = DEFAULT_MAX_CONN_TOTAL;

    private boolean proxyEnable = false;

    private HttpHost proxyHost = DEFAULT_PROXY_HOST;

    private boolean socketHostSettable = false;

    private HttpHost socketHost;
    
    private boolean requestConfigSettable = false;

    private boolean socketTimeoutSettable = false;

    private int socketTimeout = DEFAULT_SOCKET_TIME_OUT;

    private boolean connectTimeoutSettable = false;

    private int connectTimeout = DEFAULT_CONNECT_TIME_OUT;

    private boolean connectionRequestTimeoutSettable = false;

    private int connectionRequestTimeout = DEFAULT_CONNECT_TIME_OUT;

    public boolean isSslEnable()
    {
        return sslEnable;
    }

    public void setSslEnable(boolean sslEnable)
    {
        this.sslEnable = sslEnable;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
    
    public Integer getMaxConnTotal()
    {
        return maxConnTotal;
    }

    public void setMaxConnTotal(Integer maxConnTotal)
    {
        this.maxConnTotal = maxConnTotal;
    }

    public boolean isProxyEnable()
    {
        return proxyEnable;
    }

    public void setProxyEnable(boolean proxyEnable)
    {
        this.proxyEnable = proxyEnable;
    }

    public HttpHost getProxyHost()
    {
        return proxyHost;
    }

    public void setProxyHost(HttpHost proxyHost)
    {
        this.proxyHost = proxyHost;
        this.proxyEnable = true;
    }

    public boolean isSocketTimeoutSettable()
    {
        return socketTimeoutSettable;
    }

    public void setSocketTimeoutSettable(boolean socketTimeoutSettable)
    {
        this.socketTimeoutSettable = socketTimeoutSettable;
    }

    public int getSocketTimeout()
    {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout)
    {
        this.socketTimeout = socketTimeout;
        this.socketTimeoutSettable = true;
    }

    public boolean isConnectTimeoutSettable()
    {
        return connectTimeoutSettable;
    }

    public void setConnectTimeoutSettable(boolean connectTimeoutSettable)
    {
        this.connectTimeoutSettable = connectTimeoutSettable;
    }

    public int getConnectTimeout()
    {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout)
    {
        this.connectTimeout = connectTimeout;
        this.connectTimeoutSettable = true;
    }

    public boolean isConnectionRequestTimeoutSettable()
    {
        return connectionRequestTimeoutSettable;
    }

    public void setConnectionRequestTimeoutSettable(boolean connectionRequestTimeoutSettable)
    {
        this.connectionRequestTimeoutSettable = connectionRequestTimeoutSettable;
    }

    public int getConnectionRequestTimeout()
    {
        return connectionRequestTimeout;
    }

    public void setConnectionRequestTimeout(int connectionRequestTimeout)
    {
        this.connectionRequestTimeout = connectionRequestTimeout;
        this.connectionRequestTimeoutSettable = true;
    }

    public boolean isSocketHostSettable()
    {
        return socketHostSettable;
    }

    public void setSocketHostSettable(boolean socketHostSettable)
    {
        this.socketHostSettable = socketHostSettable;
    }

    public HttpHost getSocketHost()
    {
        return socketHost;
    }

    public void setSocketHost(HttpHost socketHost)
    {
        this.socketHost = socketHost;
        this.socketHostSettable = true;
    }

    public boolean isRequestConfigSettable()
    {
        return requestConfigSettable;
    }

    public void setRequestConfigSettable(boolean requestConfigSettable)
    {
        this.requestConfigSettable = requestConfigSettable;
    }
}
