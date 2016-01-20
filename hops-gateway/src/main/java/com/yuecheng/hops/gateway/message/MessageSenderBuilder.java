/*
 * 文件名：MessageSenderBuilder.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：yangyi
 * 修改时间：2014年10月21日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.gateway.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuecheng.hops.gateway.GatewayConstant;
import com.yuecheng.hops.gateway.communication.apache.http.client.ApacheCloseableHttpAsyncClient;
import com.yuecheng.hops.gateway.communication.apache.http.client.ApacheCloseableHttpClient;

@Component("messageSenderBuilder")
public class MessageSenderBuilder
{
    @Autowired
    private ApacheCloseableHttpClient apacheCloseableHttpClient;
    
    @Autowired
    private ApacheCloseableHttpAsyncClient apacheCloseableHttpAsyncClient;
    
    public MessageSender builder(AbstractMessage requestMessage)
    {
        if (GatewayConstant.SECHME_HTTP.equalsIgnoreCase(requestMessage.getProtocalType()))
        {
             return apacheCloseableHttpClient;
        }
        return null;
    }
}
