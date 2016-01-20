/*
 * 文件名：CheckOrderAction.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月22日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.transaction.service.action.notify;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.injection.service.MerchantRequestService;
import com.yuecheng.hops.transaction.basic.entity.Notify;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.mq.producer.OrderNotifyProducerService;
import com.yuecheng.hops.transaction.service.StatusManagementService;
import com.yuecheng.hops.transaction.service.action.ActionHandler;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.notify.NotifyService;
import com.yuecheng.hops.transaction.service.order.OrderManagement;

@Scope("prototype")
@Service("notifyStatusWaitAction")
public class NotifyStatusWaitAction extends ActionHandler
{
    private static Logger           logger = LoggerFactory.getLogger(NotifyStatusWaitAction.class);

    @Autowired
    private NotifyService           notifyService;
    
    @Autowired
    private OrderManagement orderManagement;

    @Autowired
    private MerchantRequestService  merchantRequestService;

    @Autowired
    @Qualifier("notifyStatusManagementServiceImpl")
    private StatusManagementService notifyStatusManagementServiceImpl;
    
    @Autowired
    private OrderNotifyProducerService orderNotifyProducerService;

    @Override
    @Transactional
    public void handleRequest()
        throws HopsException
    {
        Order order = (Order)ActionContextUtil.getActionContextParam(ActionMapKey.ORDER);
        try
        {
            Notify notify = notifyService.findNotifyByOrderNo(order.getOrderNo());
            if(BeanUtils.isNotNull(notify))
            {
                if(Constant.NotifyStatus.NO_NEED_NOTIFY == notify.getNotifyStatus() || Constant.NotifyStatus.NOTIFYING == notify.getNotifyStatus())
                {
                    //普通订单
                    logger.debug("修改发货状态(NO_NEED_NOTIFY->WAIT_NOTIFY) [开始]"+order.getOrderNo());
                    orderManagement.updateOrderNotifyStatus(notify.getOrderNo(), Constant.NotifyStatus.WAIT_NOTIFY);
                    notify = (Notify)notifyStatusManagementServiceImpl.updateStatus(notify.getNotifyId(), notify.getNotifyStatus(), Constant.NotifyStatus.WAIT_NOTIFY);
                    Date deliveryTime = new Date();
                    Date now = new Date();
                    Date startTime = merchantRequestService.getNextQueryDate(deliveryTime, now, order.getMerchantId(), Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_ORDER);
                    notify.setStartTime(startTime);
                    notify = notifyService.save(notify);
                    logger.debug("修改发货状态(NO_NEED_NOTIFY->WAIT_NOTIFY) [结束]"+order.getOrderNo());
                }
            }
            ActionContextUtil.setActionContext(ActionMapKey.NOTIFY, notify);
            if(BeanUtils.isNotNull(this.successor)){
                this.successor.handleRequest();
            }
        }
        catch (Exception e)
        {
            logger.error("notifyStatusWaitAction happen Exception caused by "+ ExceptionUtil.getStackTraceAsString(e));
            throw new ApplicationException("transaction002011", new String[]{ExceptionUtil.getStackTraceAsString(e)}, e);
        }
    }
}
