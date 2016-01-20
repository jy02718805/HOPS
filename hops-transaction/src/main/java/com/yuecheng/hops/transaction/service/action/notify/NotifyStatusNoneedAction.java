/*
 * 文件名：CheckOrderAction.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月22日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.transaction.service.action.notify;


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

@Scope("prototype")
@Service("notifyStatusNoneedAction")
public class NotifyStatusNoneedAction extends ActionHandler
{
    private static Logger           logger = LoggerFactory.getLogger(NotifyStatusNoneedAction.class);

    @Autowired
    private NotifyService           notifyService;

    @Autowired
    @Qualifier("notifyStatusManagementServiceImpl")
    private StatusManagementService notifyStatusManagementServiceImpl;

    @Override
    @Transactional
    public void handleRequest()
        throws HopsException
    {
        Order order = (Order)ActionContextUtil.getActionContextParam(ActionMapKey.ORDER);
        Notify notify = notifyService.findNotifyByOrderNo(order.getOrderNo());
        try
        {
            logger.debug("修改通知状态(NoNeed) [开始]"+String.valueOf(notify).toString());
            if(BeanUtils.isNotNull(notify))
            {
                if(Constant.NotifyStatus.WAIT_NOTIFY == notify.getNotifyStatus() || Constant.NotifyStatus.NOTIFYING == notify.getNotifyStatus()){
                    notify = (Notify)notifyStatusManagementServiceImpl.updateStatus(notify.getNotifyId(),
                        notify.getNotifyStatus(), Constant.NotifyStatus.NO_NEED_NOTIFY);
                    notify = notifyService.save(notify);
                }
            }
            logger.debug("修改通知状态(NoNeed) [结束]"+String.valueOf(notify).toString());
            
            if(BeanUtils.isNotNull(this.successor)){
                this.successor.handleRequest();
            }
        }
        catch (Exception e)
        {
            logger.error("notifyStatusNoneedAction happen Exception caused by "+ ExceptionUtil.getStackTraceAsString(e));
            throw new ApplicationException("transaction002010", new String[]{ExceptionUtil.getStackTraceAsString(e)}, e);
        }
    }
}
