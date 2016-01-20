/*
 * 文件名：ApacheIOReactorConfig.java 版权：Copyright by www.365haoyou.com 描述： 修改人：yangyi 修改时间：2014年10月16日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.gateway.communication.apache.http;


import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.IOReactorException;
import org.springframework.stereotype.Component;

@Component
public class ApacheIOReactorConfig
{
    public static final Integer CONNECT_TIMEOUT = 30000;
    public static final Integer SO_TIMEOUT = 30000;
    public static final Integer IO_WORK_NUM = 200;
    public static IOReactorConfig create(Integer connectTimeout,Integer soTimeOut)
        throws IOReactorException
    {
        // Create I/O reactor configuration
        IOReactorConfig.Builder  builder = IOReactorConfig.custom();
        builder.setIoThreadCount(IO_WORK_NUM);
        builder.setSoReuseAddress(true);
        if (null == connectTimeout || connectTimeout <= 0)
        {
            connectTimeout = CONNECT_TIMEOUT;
        }
        if (null == soTimeOut || soTimeOut <= 0)
        {
            soTimeOut = SO_TIMEOUT;
        }
        builder.setConnectTimeout(connectTimeout);
        builder.setSoTimeout(soTimeOut);
        IOReactorConfig ioReactorConfig = builder.build();
        return ioReactorConfig;
    }
}
