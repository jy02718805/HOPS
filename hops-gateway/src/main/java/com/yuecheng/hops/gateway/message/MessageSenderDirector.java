/*
 * 文件名：MessageSenderDirector.java
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

@Component("messageSenderDirector")
public class MessageSenderDirector
{
    @Autowired
    private MessageSenderBuilder messageSenderBuilder;

    public void setMessageSenderBuilder(MessageSenderBuilder messageSenderBuilder)
    {
        this.messageSenderBuilder = messageSenderBuilder;
    }

    public MessageSender create(AbstractMessage requestMessage)
    {
        return messageSenderBuilder.builder(requestMessage);
    }

}
