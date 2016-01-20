/*
 * 文件名：NotifyStatusManagementServiceImpl.java 版权：Copyright by www.365haoyou.com 描述：
 * 修改人：Administrator 修改时间：2014年10月23日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.transaction.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.transaction.basic.entity.Notify;
import com.yuecheng.hops.transaction.basic.repository.NotifyJpaDao;
import com.yuecheng.hops.transaction.execution.status.NotifyStatusTransferService;
import com.yuecheng.hops.transaction.service.notify.NotifyService;

@Service("notifyStatusManagementServiceImpl")
public class NotifyStatusManagementServiceImpl implements StatusManagementService
{
    private static Logger logger = LoggerFactory.getLogger(NotifyStatusManagementServiceImpl.class);
    
    @Autowired
    private NotifyStatusTransferService NotifyStatusTransferService;
    
    @Autowired
    private NotifyService notifyService;
    
    @Autowired
    private NotifyJpaDao notifyDao;
    
    
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Notify updateStatus(Long id, Integer originalStatus, Integer targetStatus)
    {
        if (NotifyStatusTransferService.checkStatus(targetStatus, originalStatus))
        {
            Notify notify = notifyDao.findOne(id);
            if(originalStatus.compareTo(notify.getNotifyStatus()) == 0){
                notify.setNotifyStatus(targetStatus);
                return notify;
            }
            else
            {
                logger.error("修改通知状态失败 Long "+id+", Integer originalStatus"+originalStatus+", Integer targetStatus:"+targetStatus);
                throw new ApplicationException("transaction001036", new String[] {id.toString(), originalStatus.toString(), targetStatus.toString()});
            }
        }
        else
        {
            logger.error("修改通知状态异常 Long "+id+", Integer originalStatus"+originalStatus+", Integer targetStatus:"+targetStatus);
            throw new ApplicationException("transaction001036", new String[] {id.toString(), originalStatus.toString(), targetStatus.toString()});
        }
    }

}
