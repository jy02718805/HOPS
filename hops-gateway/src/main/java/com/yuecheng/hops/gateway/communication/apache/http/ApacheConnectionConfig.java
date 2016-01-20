/*
 * 文件名：ApacheConnectionConfig.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：yangyi
 * 修改时间：2014年10月16日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.gateway.communication.apache.http;

import java.nio.charset.CodingErrorAction;

import org.apache.http.Consts;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.springframework.stereotype.Component;

@Component("apacheConnectionConfig")
public class ApacheConnectionConfig
{
    
    static MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(200).setMaxLineLength(2000).build();
    
    public static ConnectionConfig create()
    {
        ConnectionConfig connectionConfig = ConnectionConfig.custom().setMalformedInputAction(
            CodingErrorAction.IGNORE).setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(
            Consts.UTF_8).setMessageConstraints(messageConstraints).build();
        return connectionConfig;
    }
    
}
