/*
 * 文件名：ApacheListeningIOReactor.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：yangyi
 * 修改时间：2014年12月17日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.gateway.communication.apache.http;

import org.apache.http.impl.nio.reactor.DefaultListeningIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.nio.reactor.ListeningIOReactor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuecheng.hops.common.exception.ExceptionUtil;

public class ApacheListeningIOReactor
{
    private static Logger logger = LoggerFactory.getLogger(ApacheListeningIOReactor.class);
    
    public ListeningIOReactor create(IOReactorConfig ioReactorConfig)
    {
        ListeningIOReactor ioReactor = null;
        try
        {
            ioReactor = new DefaultListeningIOReactor(null==ioReactorConfig?ApacheIOReactorConfig.create(null,null):ioReactorConfig);
        }
        catch (IOReactorException e)
        {
            logger.error(ExceptionUtil.getStackTraceAsString(e));
        }
        return ioReactor;
    }
}
