/*
 * 文件名：ApacheSocketConfig.java 版权：Copyright by www.365haoyou.com 描述： 修改人：yangyi 修改时间：2014年12月19日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.gateway.communication.apache.http;


import org.apache.http.config.SocketConfig;


public class ApacheSocketConfig
{
    public static final int SO_TIMEOUT = 30000;

    public static final boolean TCP_NO_DELAY = true;

    public static SocketConfig create(Integer soTimeout, Boolean tcpNoDelay)
    {
        SocketConfig.Builder builder = SocketConfig.custom();
        if (null == soTimeout || soTimeout <= 0)
        {
            soTimeout = SO_TIMEOUT;
        }
        if (null == tcpNoDelay)
        {
            tcpNoDelay = TCP_NO_DELAY;
        }
        builder.setSoTimeout(soTimeout);
        builder.setTcpNoDelay(tcpNoDelay);
        SocketConfig socketConfig = builder.build();
        return socketConfig;
    }

}
