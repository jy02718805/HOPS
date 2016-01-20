/*
 * 文件名：MessageReceiverBuilder.java 版权：Copyright by www.365haoyou.com 描述： 修改人：yangyi
 * 修改时间：2014年10月21日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.gateway.message;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuecheng.hops.gateway.service.MessageReceiverResolverService;


@Component("messageReceiverBuilder")
public class MessageReceiverBuilder
{
    @Autowired
    private MessageReceiverResolverService messageReceiverResolverService;

    public void setMessageReceiverResolverService(MessageReceiverResolverService messageReceiverResolverService)
    {
        this.messageReceiverResolverService = messageReceiverResolverService;
    }



    public MessageReceiver builder(AbstractMessage requestMessage)
    {
        return messageReceiverResolverService;
    }
}
