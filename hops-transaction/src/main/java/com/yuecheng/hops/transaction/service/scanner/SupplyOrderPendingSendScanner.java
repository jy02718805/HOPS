package com.yuecheng.hops.transaction.service.scanner;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.event.HopsPublisher;
import com.yuecheng.hops.common.event.HopsRequestEvent;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.product.entity.airtimes.AirtimeProduct;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;
import com.yuecheng.hops.product.service.ProductService;
import com.yuecheng.hops.product.service.SupplyProductRelationService;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.event.SendOrderEvent;
import com.yuecheng.hops.transaction.execution.devliery.DeliveryStatusManagementAction;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;
import com.yuecheng.hops.transaction.service.delivery.DeliveryService;


@Service("supplyOrderPendingSendScanner")
public class SupplyOrderPendingSendScanner
{
    private static final Logger            logger = LoggerFactory.getLogger(SupplyOrderPendingSendScanner.class);

    @Autowired
    private DeliveryService                deliveryService;
    
    @Autowired
    private DeliveryManagement deliveryManagement;
    
    @Autowired
    private ProductService productService;

    @Autowired
    private SupplyProductRelationService  supplyProductRelationService;
    
    @Autowired
    private DeliveryStatusManagementAction deliveryStatusManagementAction;

    @Autowired
    private HopsPublisher                  publisher;

    public void sendOrders()
    {
        logger.debug("订单发送流程[开始]!");
        // 所有需要发货的订单
        List<Delivery> deliverys = deliveryService.findSendOrders();
        logger.debug("订单发送流程：修改通知记录状态为通知中！总数[" + deliverys.size() + "]条。[开始]");
        // 先修改状态
        logger.debug("订单发送流程：修改通知记录状态为通知中！总数[" + deliverys.size() + "]条。[结束]");
        HopsRequestEvent hre = new HopsRequestEvent(SupplyOrderPendingSendScanner.class);
        // 发单
        for (Delivery delivery : deliverys)
        {
              Long deliveryId = delivery.getDeliveryId();
              // 获取供货商信息(号段、金额)
              SupplyProductRelation upr = supplyProductRelationService.querySupplyProductRelationByParams(delivery.getProductId(), delivery.getMerchantId(), Constant.SupplyProductStatus.OPEN_STATUS);
              if (BeanUtils.isNotNull(upr))
              {
                  logger.debug("找到的供货商产品信息：["+String.valueOf(upr).toString()+"]");
                  AirtimeProduct airtimeProduct=productService.findOne(upr.getProductId());
                  logger.debug("查找系统产品信息：["+String.valueOf(airtimeProduct).toString()+"]");
                  hre = new SendOrderEvent(SupplyOrderPendingSendScanner.class, deliveryId,airtimeProduct.getBusinessType(),upr);
                  publisher.publishRequestEvent(hre);
              }
        }
//        for (Iterator<Long> iterator = deliveryIds.iterator(); iterator.hasNext();)
//        {
//            Long deliveryId = iterator.next();
//            hre = new SendOrderEvent(SupplyOrderPendingSendScanner.class, deliveryId);
//            publisher.publishRequestEvent(hre);
//        }
        logger.debug("订单发送流程[结束]!");
    }
}
