package com.yuecheng.hops.transaction.execution.notify.action;


import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.transaction.basic.entity.Notify;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.basic.repository.NotifyJpaDao;
import com.yuecheng.hops.transaction.basic.repository.OrderJpaDao;


@Service("notifyStatusManagementAction")
public class NotifyStatusManagementAction
{
    @Autowired
    private NotifyJpaDao notifyDao;

    @Autowired
    private OrderJpaDao  orderJpaDao;
    
    private final static Integer SUCCESS = 1;
    
    private static Logger                logger = LoggerFactory.getLogger(NotifyStatusManagementAction.class);
    
    /**
     * 通知流程，更新通知记录状态变成通知中
     * 
     * @param notifys
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Long> updateNotifyStatusToNOTIFYING(List<Notify> notifys)
    {
        List<Long> orderNos = new ArrayList<Long>();
        for (Iterator<Notify> iterator = notifys.iterator(); iterator.hasNext();)
        {
            Notify notify = iterator.next();
            try
            {
                int result = notifyDao.updateNotifyStatus(notify.getNotifyId(), Constant.NotifyStatus.NOTIFYING, Constant.NotifyStatus.WAIT_NOTIFY);
                if(SUCCESS == result){
                    orderNos.add(notify.getOrderNo());
                }
            }
            catch (Exception e)
            {
                logger.error("fail to updateNotifyStatusToNOTIFYING with notifyId["+notify.getNotifyId()+"]" + ExceptionUtil.getStackTraceAsString(e));
            }
        }
        return orderNos;
    }

    /**
     * 通知流程，通知异常将通知记录还原
     * 
     * @param notify
     * @param order
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateNotifyStatusToWAITNOTIFY(Notify notify, Order order)
    {
        notify.setNotifyCntr(notify.getNotifyCntr() + 1);
        notify.setEndTime(new Date());
        if (notify.getNotifyCntr() > notify.getLimitedCntr())
        {
            notify.setNotifyStatus(Constant.NotifyStatus.NOTIFY_FAIL);
            notifyDao.save(notify);
            order.setNotifyStatus(Constant.NotifyStatus.NOTIFY_FAIL);
            orderJpaDao.save(order);
        }
        else
        {
            notify.setNotifyStatus(Constant.NotifyStatus.WAIT_NOTIFY);
            notifyDao.save(notify);
        }
    }
}
