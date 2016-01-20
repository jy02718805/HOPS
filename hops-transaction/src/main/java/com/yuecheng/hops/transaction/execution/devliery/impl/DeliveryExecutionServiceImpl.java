/*
 * 文件名：DeliveryExecutionServiceImpl.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2014年11月26日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.transaction.execution.devliery.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.execution.devliery.DeliveryExecutionService;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;

@Service("deliveryExecutionService")
public class DeliveryExecutionServiceImpl implements DeliveryExecutionService
{
    @Autowired
    private DeliveryManagement deliveryManagement;

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public Delivery addQueryTime(Long deliveryId)
    {
        Delivery delivery = deliveryManagement.findDeliveryById(deliveryId);
        Assert.notNull(delivery,"delivery is not found! id:"+deliveryId);
        delivery.setQueryTimes(delivery.getQueryTimes() + 1);
        deliveryManagement.addQueryTimes(delivery.getQueryTimes(), delivery.getDeliveryId());
        return delivery;
    }

}
