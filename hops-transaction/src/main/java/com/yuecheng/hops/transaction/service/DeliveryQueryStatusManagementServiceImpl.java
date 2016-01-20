/*
 * 文件名：DeliveryQueryStatusManagementServiceImpl.java 版权：Copyright by www.365haoyou.com 描述：
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
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.basic.repository.DeliveryJpaDao;
import com.yuecheng.hops.transaction.execution.status.QueryStatusTransferService;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;

@Service("deliveryQueryStatusManagementServiceImpl")
public class DeliveryQueryStatusManagementServiceImpl implements StatusManagementService
{
    private static Logger logger = LoggerFactory.getLogger(DeliveryQueryStatusManagementServiceImpl.class);

    @Autowired
    private QueryStatusTransferService queryStatusTransferService;
    
    @Autowired
    private DeliveryManagement deliveryManagement;
    
    @Autowired
    private DeliveryJpaDao deliveryJpaDao;
    
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Delivery updateStatus(Long id, Integer originalStatus, Integer targetStatus)
    {
        if (queryStatusTransferService.checkStatus(targetStatus, originalStatus))
        {
            Delivery delivery = deliveryManagement.findDeliveryById(id);
            if(originalStatus.compareTo(delivery.getQueryFlag()) == 0){
                delivery.setQueryFlag(targetStatus);
                return delivery;
            }
            else
            {
                logger.error("修改查询状态失败 Long "+id+", Integer originalStatus"+originalStatus+", Integer targetStatus:"+targetStatus);
                throw new ApplicationException("transaction001035", new String[] {id.toString(), originalStatus.toString(), targetStatus.toString()});
            }
        }
        else
        {
            logger.error("修改查询状态异常 Long "+id+", Integer originalStatus"+originalStatus+", Integer targetStatus:"+targetStatus);
            throw new ApplicationException("transaction001035", new String[] {id.toString(), originalStatus.toString(), targetStatus.toString()});
        }
    }
}
