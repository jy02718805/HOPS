/*
 * 文件名：OrderNotifyMessageListener.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：yangyi
 * 修改时间：2015年1月16日
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
import com.yuecheng.hops.transaction.execution.fakeRule.FakeRuleService;
import com.yuecheng.hops.transaction.service.order.OrderManagement;

@Component
public class OrderNotifyMessageListener extends AbstractListener
{
    @Autowired
    private HopsPublisher publisher;
    
    @Autowired
    private FakeRuleService fakeRuleService;
    
    @Autowired
    private OrderManagement orderManagement;
    
    private static Logger logger = LoggerFactory.getLogger(OrderNotifyMessageListener.class);

    @Override
    public void onMessage(Message message, Session session)
        throws JMSException
    {
        logger.debug("receive order notify msg!");
        TextMessage notifyIdMessage =  (TextMessage) message;
        Long orderNo = Long.valueOf(notifyIdMessage.getText());
        fakeRuleService.updateOrderNotifyStatus(orderNo);
    }

}
