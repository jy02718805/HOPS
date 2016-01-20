/*
 * 文件名：MessageReceiverDirector.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：yangyi
 * 修改时间：2014年10月22日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.gateway.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("messageReceiverDirector")
public class MessageReceiverDirector
{
    @Autowired
    private MessageReceiverBuilder messageReceiverBuilder;

    public void setMessageReceiverBuilder(MessageReceiverBuilder messageReceiverBuilder)
    {
        this.messageReceiverBuilder = messageReceiverBuilder;
    }

    public MessageReceiver create(AbstractMessage requestMessage)
    {
        return messageReceiverBuilder.builder(requestMessage);
    }
}
