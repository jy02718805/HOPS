/*
 * 文件名：StdErrorExceptionLogger.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：yangyi
 * 修改时间：2014年12月25日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.gateway.communication.apache.http.server;

import java.net.SocketTimeoutException;

import org.apache.http.ConnectionClosedException;
import org.apache.http.ExceptionLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StdErrorExceptionLogger implements ExceptionLogger
{
	private static final Logger logger = LoggerFactory.getLogger(StdErrorExceptionLogger.class);
    
    @Override
    public void log(final Exception ex)
    {
        if (ex instanceof SocketTimeoutException)
        {
        	logger.error("Connection timed out");
        }
        else if (ex instanceof ConnectionClosedException)
        {
        	logger.error(ex.getMessage());
        }
        else
        {
        	logger.error("UtilBigDecimalConverter:[convert]["+ex.getMessage()+"]");
        }
    }
}
