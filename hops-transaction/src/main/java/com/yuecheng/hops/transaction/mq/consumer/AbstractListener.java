/*
 * 文件名：AbstractListener.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2015年1月18日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.transaction.mq.consumer;


import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.jms.support.converter.MessageConverter;


public abstract class AbstractListener implements MessageListener, SessionAwareMessageListener
{

    private MessageConverter messageConverter;

    public void onMessage(Message message)
    {
        try
        {
            onMessage(message, null);
        }
        catch (Throwable ex)
        {
            ex.printStackTrace();
        }
    }

    public abstract void onMessage(Message message, Session session)
        throws JMSException;

    public MessageConverter getMessageConverter()
    {
        return messageConverter;
    }

    public void setMessageConverter(MessageConverter messageConverter)
    {
        this.messageConverter = messageConverter;
    }

}