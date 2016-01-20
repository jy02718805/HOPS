/*
 * 文件名：DeliveryQueryProducerService.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：yangyi
 * 修改时间：2015年1月16日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.transaction.mq.producer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.ScheduledMessage;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.transaction.basic.entity.Delivery;

@Service("deliveryQueryProducerService")
public class DeliveryQueryProducerService implements ProducerService<Delivery>
{
    private static JmsTemplate jmsTemplate;
    
    public JmsTemplate getJmsTemplate()
    {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate)
    {
        this.jmsTemplate = jmsTemplate;
    }
    
    @Override
    public void sendMessage(final Long deliveryId,final long delay)
    {
        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {  
                 Message message =  session.createTextMessage(deliveryId.toString());
                 message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delay > 0?delay:10000);
                 return message ;
            }
        });
    }
}
