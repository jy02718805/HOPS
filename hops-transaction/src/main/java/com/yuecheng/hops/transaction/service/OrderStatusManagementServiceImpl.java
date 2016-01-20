/*
 * 文件名：OrderStatusManagementService.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月23日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.transaction.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.basic.repository.OrderJpaDao;
import com.yuecheng.hops.transaction.execution.status.OrderStatusTransferService;
import com.yuecheng.hops.transaction.service.order.OrderManagement;

@Service("orderStatusManagementServiceImpl")
public class OrderStatusManagementServiceImpl implements StatusManagementService
{
    private static Logger logger = LoggerFactory.getLogger(OrderStatusManagementServiceImpl.class);
    
    @Autowired
    private OrderStatusTransferService orderStatusTransferService;
    
    @Autowired
    private OrderManagement orderManagement;
    
    @Autowired
    private OrderJpaDao orderJpaDao;
    
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Order updateStatus(Long id, Integer originalStatus, Integer targetStatus)
    {
        if (orderStatusTransferService.checkStatus(targetStatus, originalStatus))
        {
            Order order = orderManagement.findOne(id);
            if(originalStatus.compareTo(order.getOrderStatus()) == 0){
                order.setOrderStatus(targetStatus);
                return order;
            }
            else
            {
                logger.error("修改订单状态失败 Long "+id+", Integer originalStatus"+originalStatus+", Integer targetStatus:"+targetStatus);
                throw new ApplicationException("transaction001034", new String[] {id.toString(), originalStatus.toString(), targetStatus.toString()});
            }
        }
        else
        {
            logger.error("修改订单状态异常 Long "+id+", Integer originalStatus"+originalStatus+", Integer targetStatus:"+targetStatus);
            throw new ApplicationException("transaction001034", new String[] {id.toString(), originalStatus.toString(), targetStatus.toString()});
        }
    }

}
