/*
 * 文件名：ApacheHttpClientContext.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：yangyi
 * 修改时间：2014年10月16日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.gateway.communication.apache.http;

import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.protocol.HttpClientContext;
import org.springframework.stereotype.Component;

import com.yuecheng.hops.common.utils.BeanUtils;

@Component("apacheHttpClientContext")
public class ApacheHttpClientContext
{
    
    public HttpClientContext createContext(CookieStore cookieStore,CredentialsProvider credentialsProvider)
    {
        // Execution context can be customized locally.
        HttpClientContext localContext = HttpClientContext.create();
        // Contextual attributes set the local context level will take
        // precedence over those set at the client level.
        if (BeanUtils.isNotNull(cookieStore))
        {
            localContext.setCookieStore(cookieStore);
        }
        if (BeanUtils.isNotNull(credentialsProvider))
        {
            localContext.setCredentialsProvider(credentialsProvider);
        }
        return localContext;
    }
}
