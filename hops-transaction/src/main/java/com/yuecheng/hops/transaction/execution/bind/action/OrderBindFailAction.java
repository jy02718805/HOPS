package com.yuecheng.hops.transaction.execution.bind.action;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.execution.account.AirtimeAccountingTransaction;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;
import com.yuecheng.hops.transaction.service.notify.NotifyService;
import com.yuecheng.hops.transaction.service.order.OrderManagement;
import com.yuecheng.hops.transaction.service.process.OrderCloserProcess;


@Service("orderBindFailAction")
public class OrderBindFailAction implements OrderMissBindAction
{
    private static final Logger       logger = LoggerFactory.getLogger(OrderBindFailAction.class);

    
    @Autowired
    private DeliveryManagement deliveryManagement;
    
    @Autowired
    private NotifyService notifyService;
    
    @Autowired
    private OrderManagement orderManagement;
    
    @Autowired
    private AirtimeAccountingTransaction airtimeAccountingTransaction;

    @Autowired
    private OrderCloserProcess orderCloserProcess;
    
    @Override
    @Transactional
    public void execute(Order order)
    {
        try
        {
            logger.debug("绑定失败！退绑流程  [开始]");
            // 没有上游合适，订单失败。
            List<Delivery> deliverys = deliveryManagement.findUnfinishedDeliveryList(order.getOrderNo());
            Delivery delivery = null;
            if(BeanUtils.isNotNull(deliverys) && deliverys.size() > 0)
            {
                delivery = deliverys.get(0);
            }
            else
            {
                delivery = null;
            }
            ActionContextUtil.init();
            ActionContextUtil.setActionContext(ActionMapKey.ORDER, order);
            ActionContextUtil.setActionContext(ActionMapKey.DELIVERY, delivery);
            ActionContextUtil.setActionContext(ActionMapKey.ERROR_CODE, Constant.ErrorCode.BIND_ORDER_FAIL);
            orderCloserProcess.execute();
//            actionChainDefiner.closeOrder();
            logger.debug("绑定失败！退绑流程  [结束]");
        }
        catch (Exception e)
        {
            logger.debug("绑定失败！退绑流程发生异常! 异常信息["+ExceptionUtil.getStackTraceAsString(e)+"]");
            throw new ApplicationException("transaction001030", e);
        }

    }
}
