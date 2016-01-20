package com.yuecheng.hops.transaction.service.builder;


import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.event.HopsPublisher;
import com.yuecheng.hops.common.event.HopsRequestEvent;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.parameter.entity.ParameterConfiguration;
import com.yuecheng.hops.parameter.service.ParameterConfigurationService;
import com.yuecheng.hops.transaction.DefaultTransactionResponseImpl;
import com.yuecheng.hops.transaction.TransactionContextUtil;
import com.yuecheng.hops.transaction.TransactionMapKey;
import com.yuecheng.hops.transaction.TransactionRequest;
import com.yuecheng.hops.transaction.TransactionResponse;
import com.yuecheng.hops.transaction.TransactionService;
import com.yuecheng.hops.transaction.basic.entity.Notify;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.event.AsynNotifyDeliveryEvent;
import com.yuecheng.hops.transaction.execution.notify.NotifyOrderService;
import com.yuecheng.hops.transaction.mq.producer.OrderNotifyProducerService;
import com.yuecheng.hops.transaction.service.action.AbstractEventAction;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.notify.NotifyService;
import com.yuecheng.hops.transaction.service.order.OrderManagement;

@Service("agentOrderNotificator")
public class AgentOrderNotificator implements TransactionService
{
    private static Logger                logger = LoggerFactory.getLogger(AgentOrderNotificator.class);

    @Autowired
    private OrderManagement              orderManagement;

    @Autowired
    private NotifyOrderService           notifyOrderService;

    @Autowired
    private NotifyService                notifyService;
    
    @Autowired
    @Qualifier("notifyStatusWaitAction")
    private AbstractEventAction notifyStatusWaitAction;
    
    @Autowired
    @Qualifier("notifyStatusFailAction")
    private AbstractEventAction notifyStatusFailAction;
    
    @Autowired
    private OrderNotifyProducerService orderNotifyProducerService;
    
    @Autowired
    private ParameterConfigurationService parameterConfigurationService;
    
    @Autowired
    private HopsPublisher publisher;

    @Override
    public TransactionResponse doTransaction(TransactionRequest transactionRequest)
    {
        TransactionContextUtil.copyPropertiesIfAbsent(transactionRequest);
        TransactionResponse response = new DefaultTransactionResponseImpl();
        Long orderNo = Long.parseLong(String.valueOf((TransactionContextUtil.getTransactionContextParam(EntityConstant.Order.ORDER_NO))));
        Notify notify = notifyService.findNotifyByOrderNo(orderNo);
        if(BeanUtils.isNotNull(notify))
        {
            notify.setNotifyCntr(notify.getNotifyCntr() + 1);
            notify = notifyService.save(notify);
            TransactionContextUtil.setTransactionContext(TransactionMapKey.NOTIFY, notify);
            Order order = orderManagement.findOne(notify.getOrderNo());
            TransactionContextUtil.setTransactionContext(TransactionMapKey.ORDER, order);
            try
            {
                Assert.isTrue(order.getOrderStatus() == Constant.OrderStatus.SUCCESS || order.getOrderStatus() == Constant.OrderStatus.FAILURE_ALL ||
                		order.getPreSuccessStatus() == Constant.OrderStatus.PRE_SUCCESS_STATUS_WAIT || order.getPreSuccessStatus() == Constant.OrderStatus.PRE_SUCCESS_STATUS_DONE);
                logger.debug("订单通知流程：订单信息[" + order.toString() + "]");
                // 1.发送通知
                Map<String, Object> fileds = BeanUtils.transBean2Map(order);
                
                if(!(order.getOrderStatus() == Constant.OrderStatus.FAILURE_ALL || order.getOrderStatus() == Constant.OrderStatus.SUCCESS)){
                    //时间到了
                    if(BeanUtils.isNotNull(order.getPreSuccessStatus())
                        &&( order.getPreSuccessStatus().compareTo(Constant.OrderStatus.PRE_SUCCESS_STATUS_DONE) == 0
                        ||order.getPreSuccessStatus().compareTo(Constant.OrderStatus.PRE_SUCCESS_STATUS_WAIT) == 0 ) && order.getOrderPreSuccessTime().compareTo(new Date()) < 0)
                    {
                        fileds.put(EntityConstant.Order.ORDER_STATUS, Constant.OrderStatus.SUCCESS);
                    }
                    else
                    {
                        //在还未过预成功时间发起通知的话，我们往后延期通知
                        ParameterConfiguration hc = parameterConfigurationService.getParameterConfigurationByKey(Constant.ParameterConfiguration.NOTIFY_INTERVAL_TIME);
                        Long delayUnit = BeanUtils.isNotNull(hc)?Long.valueOf(hc.getConstantUnitValue()):10;
                        Long delayNotifyTime = delayUnit * 1000;
                        orderNotifyProducerService.sendMessage(order.getOrderNo(), delayNotifyTime);
                        return response;
                    }
                }
                
                
                String notifyUrl = notify.getNotifyUrl();
                if (notify.getOrderType() == 1 || notify.getOrderType() == 1L)
                {
                	fileds.put(TransactionMapKey.INTERFACE_TYPE, Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_TBORDER);
                }
				else 
				{
					if (order.getSpecialDown() == Constant.SpecialDown.PAIPAI)
					{
						if (order.getOrderStatus() == Constant.OrderStatus.SUCCESS)
						{
							fileds.put(TransactionMapKey.INTERFACE_TYPE,
									Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_ORDER_SUCCESS_PAIPAI);
						}
						else
						{
							fileds.put(TransactionMapKey.INTERFACE_TYPE,
									Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_ORDER_FAIL_PAIPAI);
							notifyUrl = notify.getFailedUrl();
						}
					}
					else
					{
						fileds.put(TransactionMapKey.INTERFACE_TYPE,
								Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_ORDER);
					}
				}
                
                fileds.put(TransactionMapKey.INTERFACE_MERCHANT_ID, order.getMerchantId());
                fileds.put(EntityConstant.Notify.NOTIFY_URL, notifyUrl);
                
                HopsRequestEvent hre = new HopsRequestEvent(AgentOrderNotificator.class);
                hre = new AsynNotifyDeliveryEvent(AsynNotifyDeliveryEvent.class, fileds, order, notify);
                publisher.publishRequestEvent(hre);
            }
            catch (HopsException e)
            {
                logger.error("订单通知流程：[出现异常，回滚通知记录为等待通知！]错误信息：[" + ExceptionUtil.getStackTraceAsString(e) + "]");
                notify = (Notify)TransactionContextUtil.getTransactionContextLocal().get(TransactionMapKey.NOTIFY);
                order = (Order)TransactionContextUtil.getTransactionContextLocal().get(TransactionMapKey.ORDER);
                ActionContextUtil.init();
                ActionContextUtil.setActionContext(ActionMapKey.NOTIFY, notify);
                ActionContextUtil.setActionContext(ActionMapKey.ORDER, order);
                if(notify.getNotifyCntr().compareTo(notify.getLimitedCntr()) <= 0)
                {
                    notifyStatusWaitAction.handleRequest();
                    TransactionContextUtil.copyProperties(ActionContextUtil.getActionContextLocal());
                }
                else
                {
//                    actionChainDefiner.notifyFail();
                    notifyStatusFailAction.handleRequest();
                    TransactionContextUtil.copyProperties(ActionContextUtil.getActionContextLocal());
                }
            }
        }
        TransactionContextUtil.clear();
        return response;
    }
}
