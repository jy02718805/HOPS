package com.yuecheng.hops.transaction.listener;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.PrintUtil;
import com.yuecheng.hops.transaction.TransactionContextUtil;
import com.yuecheng.hops.transaction.basic.entity.Notify;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.event.AsynNotifyDeliveryEvent;
import com.yuecheng.hops.transaction.execution.notify.NotifyOrderService;
import com.yuecheng.hops.transaction.service.action.AbstractEventAction;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;


@Component("asynNotifyDeliveryListener")
public class AsynNotifyDeliveryListener implements ApplicationListener<AsynNotifyDeliveryEvent>
{
    private static Logger                logger = LoggerFactory.getLogger(AsynNotifyDeliveryListener.class);

    @Autowired@Qualifier("gatewayAction")
    private AbstractEventAction gatewayAction;
    
    @Autowired
    @Qualifier("notifyStatusWaitAction")
    private AbstractEventAction notifyStatusWaitAction;
    
    @Autowired
    @Qualifier("notifyStatusFailAction")
    private AbstractEventAction notifyStatusFailAction;
    
    @Autowired
    private NotifyOrderService notifyOrderService;

    @Override
    @Async
    public void onApplicationEvent(AsynNotifyDeliveryEvent event)
    {
        Map<String,Object> fileds = event.getRequestMap();
        Map<String,Object> responseMap = null;
        Order order = event.getOrder();
        Notify notify = event.getNotify();
        try
        {
            logger.debug("begin to notify order["+String.valueOf(order).toString()+"]");
            ActionContextUtil.init();
            ActionContextUtil.setActionContext(ActionMapKey.REQUEST_MAP, fileds);
            gatewayAction.handleRequest();
            responseMap = (Map<String,Object>)ActionContextUtil.getActionContextParam(ActionMapKey.RESPONSE_MAP);
            /* 4.后续处理 */
            notifyOrderService.execute(responseMap, order, notify);
        }
        catch (HopsException e)
        {
            logger.error("AsynNotifyDeliveryListener_ActionContext["+PrintUtil.mapToString(ActionContextUtil.getActionContextLocal())+"]");
        }
        catch (Exception e)
        {
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
//                actionChainDefiner.notifyFail();
                notifyStatusFailAction.handleRequest();
                TransactionContextUtil.copyProperties(ActionContextUtil.getActionContextLocal());
            }
        }
    }
}
