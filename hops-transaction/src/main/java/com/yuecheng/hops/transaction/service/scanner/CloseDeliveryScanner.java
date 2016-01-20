package com.yuecheng.hops.transaction.service.scanner;


import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;
import com.yuecheng.hops.transaction.service.delivery.DeliveryService;


@Service("closeDeliveryScanner")
public class CloseDeliveryScanner
{
    private static final Logger            logger = LoggerFactory.getLogger(CloseDeliveryScanner.class);

    @Autowired
    private DeliveryService                deliveryService;
    
    @Autowired
    private DeliveryManagement deliveryManagement;
    
    public void closeDelivery()
    {
        logger.debug("关闭发货记录[开始]!");
        // 所有需要发货的订单
        List<Long> deliveryIds = deliveryService.findCloseDeliverys();
        logger.info("关闭发货记录["+deliveryIds+"]");
        try
        {
            // 关闭发货记录
            for (Long deliveryId : deliveryIds)
            {
                deliveryManagement.closeDelivery(deliveryId);
            }
        }
        catch (Exception e)
        {
            logger.error("closeDelivery has error["+ExceptionUtil.getStackTraceAsString(e)+"]");
        }
        logger.debug("关闭发货记录[结束]!");
    }
}
