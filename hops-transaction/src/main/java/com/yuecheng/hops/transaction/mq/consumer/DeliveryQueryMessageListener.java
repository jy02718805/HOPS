/*
 * 文件名：DeliveryQueryMessageListener.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：yangyi
 * 修改时间：2015年1月15日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.transaction.mq.consumer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuecheng.hops.common.event.HopsPublisher;
import com.yuecheng.hops.common.event.HopsRequestEvent;
import com.yuecheng.hops.transaction.event.QueryOrderEvent;

@Component
public class DeliveryQueryMessageListener extends AbstractListener
{
    @Autowired
    private HopsPublisher publisher;
    
    private static Logger logger = LoggerFactory.getLogger(DeliveryQueryMessageListener.class);
    
    @Override
    public void onMessage(Message message, Session session)
        throws JMSException
    {
        logger.debug("receive delivery query msg!");
        TextMessage deliveryIdMessage =  (TextMessage) message;
        Long deliveryId = Long.valueOf(deliveryIdMessage.getText());
        HopsRequestEvent hre = new HopsRequestEvent(DeliveryQueryMessageListener.class);
        hre = new QueryOrderEvent(DeliveryQueryMessageListener.class, deliveryId);
        publisher.publishRequestEvent(hre);
    }
}
