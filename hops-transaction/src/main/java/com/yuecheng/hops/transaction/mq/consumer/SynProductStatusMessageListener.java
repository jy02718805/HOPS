package com.yuecheng.hops.transaction.mq.consumer;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuecheng.hops.common.event.HopsPublisher;
import com.yuecheng.hops.common.event.HopsRequestEvent;
import com.yuecheng.hops.transaction.event.SynProductStatusEvent;

@Component
public class SynProductStatusMessageListener extends AbstractListener
{
	@Autowired
	private HopsPublisher publisher;

	private static Logger logger = LoggerFactory.getLogger(SynProductStatusMessageListener.class);

	@SuppressWarnings("static-access")
	@Override
	public void onMessage(Message message, Session session) throws JMSException
	{
		logger.debug("receive Syn Product Status msg!");
		MapMessage mapMessage = (MapMessage) message;
		Long merchantId = mapMessage.getLong("merchantId");
		String merchantProdCode = mapMessage.getString("merchantProdCode");
		String status = mapMessage.getString("status");

		HopsRequestEvent hre = new HopsRequestEvent(SynProductStatusMessageListener.class);
		hre = new SynProductStatusEvent(SynProductStatusMessageListener.class, merchantId, merchantProdCode, status);
		publisher.publishRequestEvent(hre);
	}
}
