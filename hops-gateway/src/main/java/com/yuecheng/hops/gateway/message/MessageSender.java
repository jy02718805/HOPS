/*
 * 文件名：MessageSender.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2014年10月11日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.gateway.message;

import java.util.concurrent.Callable;



public interface MessageSender extends Callable<AbstractMessage>
{
    public void setRequestMessage(AbstractMessage request);
    public AbstractMessage send(AbstractMessage message);
}
