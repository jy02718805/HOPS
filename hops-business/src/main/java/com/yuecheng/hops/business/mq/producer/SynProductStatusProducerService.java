package com.yuecheng.hops.business.mq.producer;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.ScheduledMessage;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class SynProductStatusProducerService
{

	private JmsTemplate jmsTemplate;

	public void setJmsTemplate(JmsTemplate jmsTemplate)
	{
		this.jmsTemplate = jmsTemplate;
	}

	public void sendMessage(final Long merchantId, final String merchantProdCode, final String status, final long delay)
	{
		jmsTemplate.send(new MessageCreator()
		{
			public Message createMessage(Session session) throws JMSException
			{
				MapMessage message = session.createMapMessage();
				message.setLong("merchantId", merchantId);
				message.setString("merchantProdCode", merchantProdCode);
				message.setString("status", status);

				message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delay > 0 ? delay : 10000);
				return message;
			}
		});
	}
}
