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
import com.yuecheng.hops.transaction.basic.entity.Notify;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.service.StatusManagementService;
import com.yuecheng.hops.transaction.service.action.ActionHandler;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.notify.NotifyService;
import com.yuecheng.hops.transaction.service.order.OrderManagement;

@Scope("prototype")
@Service("notifyStatusFailAction")
public class NotifyStatusFailAction extends ActionHandler
{
    private static Logger           logger = LoggerFactory.getLogger(NotifyStatusFailAction.class);

    @Autowired
    private OrderManagement         orderManagement;

    @Autowired
    @Qualifier("notifyStatusManagementServiceImpl")
    private StatusManagementService notifyStatusManagementServiceImpl;

    @Autowired
    private NotifyService           notifyService;

    @Override
    @Transactional
    public void handleRequest()
        throws HopsException
    {
        Order order = (Order)ActionContextUtil.getActionContextParam(ActionMapKey.ORDER);
        Notify notify = notifyService.findNotifyByOrderNo(order.getOrderNo());
        try
        {
        	logger.debug("通知失败，修改订单、通知状态 [开始]"+notify.getNotifyId());
        	if(Constant.NotifyStatus.NOTIFY_FAIL != notify.getNotifyStatus())
        	{
        		orderManagement.updateOrderNotifyStatus(order.getOrderNo(), Constant.NotifyStatus.NOTIFY_FAIL);
                notify = (Notify)notifyStatusManagementServiceImpl.updateStatus(notify.getNotifyId(),
                    notify.getNotifyStatus(), Constant.NotifyStatus.NOTIFY_FAIL);
                notify.setEndTime(new Date());
                notify = notifyService.save(notify);
        	}
            logger.debug("通知失败，修改订单、通知状态 [结束]"+notify.getNotifyId());
            
            ActionContextUtil.setActionContext(ActionMapKey.ORDER, order);
            ActionContextUtil.setActionContext(ActionMapKey.NOTIFY, notify);
            if(BeanUtils.isNotNull(this.successor)){
                this.successor.handleRequest();
            }
        }
        catch (Exception e)
        {
            logger.error("notifyStatusFailAction happen Exception caused by "+ ExceptionUtil.getStackTraceAsString(e));
            throw new ApplicationException("transaction002008", new String[]{ExceptionUtil.getStackTraceAsString(e)}, e);
        }
    }
}
