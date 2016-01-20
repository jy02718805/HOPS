/*
 * 文件名：ApacheRequestConfig.java 版权：Copyright by www.365haoyou.com 描述： 修改人：yangyi 修改时间：2014年10月16日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.gateway.communication.apache.http;


import java.util.Arrays;

import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;

import com.yuecheng.hops.gateway.entity.HttpConifguration;


public class ApacheRequestConfig
{
    public ApacheRequestConfig(HttpConifguration httpConfiguration)
    {
        RequestConfig.Builder builder = RequestConfig.custom();
        if (httpConfiguration.isSocketTimeoutSettable())
        {
            builder = builder.setSocketTimeout(httpConfiguration.getSocketTimeout());
        }
        if (httpConfiguration.isConnectTimeoutSettable())
        {
            builder = builder.setConnectTimeout(httpConfiguration.getConnectTimeout());
        }
        if (httpConfiguration.isConnectionRequestTimeoutSettable())
        {
            builder = builder.setConnectionRequestTimeout(httpConfiguration.getConnectionRequestTimeout());
        }
        if (httpConfiguration.isProxyEnable())
        {
            builder = builder.setProxy(httpConfiguration.getProxyHost());
        }
        builder.setCookieSpec(CookieSpecs.BEST_MATCH);
        builder.setExpectContinueEnabled(true);
        builder.setStaleConnectionCheckEnabled(true);
        builder.setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST));
        builder.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC));
        requestConfig = builder.build();
    }

    private RequestConfig requestConfig = defaultRequestConfig;

    public static RequestConfig defaultRequestConfig = RequestConfig.custom().setCookieSpec(
        CookieSpecs.BEST_MATCH).setExpectContinueEnabled(true).setStaleConnectionCheckEnabled(true).setTargetPreferredAuthSchemes(
        Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST)).setProxyPreferredAuthSchemes(
        Arrays.asList(AuthSchemes.BASIC)).build();

    public RequestConfig getRequestConfig()
    {
        return requestConfig;
    }
}
