package com.yuecheng.hops.transaction.execution.devliery;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;


@Service("deliveryFailAction")
public class DeliveryFailAction
{
    private static final Logger logger = LoggerFactory.getLogger(DeliveryFailAction.class);

    @Autowired
    private DeliveryManagement  deliveryManagement;

    @Transactional
    public void execute(Delivery delivery, String responseStr)
    {
        logger.debug("发货记录失败 [开始]");
        Assert.notNull(delivery);
        // 修改绑定记录
        delivery.setDeliveryResult(responseStr);
        delivery.setQueryFlag(Constant.Delivery.QUERY_FLAG_QUERY_END);
        delivery.setDeliveryFinishTime(new Date());
        delivery.setDeliveryStatus(Constant.Delivery.DELIVERY_STATUS_FAIL);
        deliveryManagement.save(delivery);
        logger.debug("deliveryManagement.save(delivery) [" + delivery.toString() + "]");
        logger.debug("发货记录失败 [结束]");
    }
}
