package com.yuecheng.hops.transaction.execution.notify.impl;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.injection.service.MerchantRequestService;
import com.yuecheng.hops.transaction.TransactionContextUtil;
import com.yuecheng.hops.transaction.TransactionMapKey;
import com.yuecheng.hops.transaction.basic.entity.Notify;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.execution.notify.NotifyOrderService;
import com.yuecheng.hops.transaction.execution.notify.action.NotifyStatusManagementAction;
import com.yuecheng.hops.transaction.service.action.AbstractEventAction;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.order.OrderManagement;


@Service("notifyOrderService")
public class NotifyOrderServiceImpl implements NotifyOrderService
{
    private static Logger                logger = LoggerFactory.getLogger(NotifyOrderServiceImpl.class);

    @Autowired
    private NotifyStatusManagementAction notifyStatusManagementAction;

    @Autowired
    private OrderManagement              orderManagement;

    @Autowired
    private MerchantRequestService       merchantRequestService;

    
    @Autowired
    @Qualifier("notifyStatusWaitAction")
    private AbstractEventAction notifyStatusWaitAction;
    
    @Autowired
    @Qualifier("notifyStatusSuccessAction")
    private AbstractEventAction notifyStatusSuccessAction;
    
    @Autowired
    @Qualifier("notifyStatusFailAction")
    private AbstractEventAction notifyStatusFailAction;

    @Override
    @Transactional
    public void execute(Map<String, Object> responseMap, Order order, Notify notify)
    {
        try
        {
            String responseStr = (String)responseMap.get(TransactionMapKey.RESPONSE_STR);
            
            if (responseStr.contains(Constant.Common.SUCCESS) || Constant.Common.TRUE.equals(responseStr))
            {
                ActionContextUtil.init();
                ActionContextUtil.setActionContext(ActionMapKey.NOTIFY, notify);
                ActionContextUtil.setActionContext(ActionMapKey.ORDER, order);
//                actionChainDefiner.notifySuccess();
                notifyStatusSuccessAction.handleRequest();
            }
            else
            {
                logger.debug("订单通知流程：返回报文[" + responseStr + "]");
                ActionContextUtil.init();
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
                logger.debug("订单通知流程：通知失败！");
                logger.debug("订单通知流程：修改订单通知记录[" + notify.toString() + "]");
            }
        }
        catch (HopsException e)
        {
            logger.error("订单通知流程：[出现异常，回滚通知记录为等待通知！]错误信息：[" + ExceptionUtil.getStackTraceAsString(e) + "]");
            ActionContextUtil.setActionContext(ActionMapKey.ORDER, order);
            if(notify.getNotifyCntr().compareTo(notify.getLimitedCntr()) <= 0)
            {
//                actionChainDefiner.keepNotify();
                notifyStatusWaitAction.handleRequest();
            }
            else
            {
                notifyStatusFailAction.handleRequest();
            }
        }
    }
}
