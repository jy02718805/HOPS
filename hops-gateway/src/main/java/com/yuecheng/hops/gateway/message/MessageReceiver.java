/*
 * 文件名：MessageReceiver.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：yangyi
 * 修改时间：2014年10月21日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.gateway.message;

public interface MessageReceiver
{
    public AbstractMessage recieve(AbstractMessage message);
}
