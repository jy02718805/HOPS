/*
 * 文件名：MessageSenderFulfillment.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：yangyi
 * 修改时间：2014年10月25日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.gateway.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("messageSenderFulfillment")
public class MessageSenderFulfillment
{
    private static Logger logger = LoggerFactory.getLogger(MessageSenderFulfillment.class);
    
    @Autowired
    private MessageSenderDirector messageSenderDirector;
    
    
    public AbstractMessage doSend(AbstractMessage request)
    {
        MessageSender sender = messageSenderDirector.create(request);
        AbstractMessage response = sender.send(request);
        return response;
    }
}
