/*
 * 文件名：ApacheSSLConnectionSocketFactory.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：yangyi
 * 修改时间：2014年10月16日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.gateway.communication.apache.http;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.yuecheng.hops.gateway.security.ssl.SSLContextFactory;

@Component("apacheSSLConnectionSocketFactory")
public class ApacheSSLConnectionSocketFactory
{
    @Autowired
    @Qualifier("sslContextFactory")
    private SSLContextFactory sslContextFactory;
    
    public SSLConnectionSocketFactory create()
    {
        SSLContext sslcontext = sslContextFactory.createSSLClientContext();
//      SSLContext sslcontext = SSLContexts.createSystemDefault();/*apache默认实现*/
        X509HostnameVerifier hostnameVerifier = new BrowserCompatHostnameVerifier();
        
        return new SSLConnectionSocketFactory(sslcontext, hostnameVerifier);
    }
}
