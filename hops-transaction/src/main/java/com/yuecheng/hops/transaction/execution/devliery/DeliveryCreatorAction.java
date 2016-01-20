package com.yuecheng.hops.transaction.execution.devliery;


import java.math.BigDecimal;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.DecimalPlaces;
import com.yuecheng.hops.common.utils.BigDecimalUtil;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.basic.entity.Order;


@Service("deliveryCreatorAction")
public class DeliveryCreatorAction
{
    /**
     * 创建发货数据
     * 
     * @param ycOrder
     * @param upr
     * @param dpr
     * @return
     */
    @Transactional
    public Delivery createDelivery(Order ycOrder, SupplyProductRelation upr, Date preDeliveryTime,
                                   Date nextQueryTime,String userCode)
    {
        BigDecimal orderSuccessFee = ycOrder.getOrderSuccessFee();
        if (orderSuccessFee == null)
        {
            orderSuccessFee = new BigDecimal(0);
        }
        BigDecimal waitDeliveryFee  = BigDecimalUtil.sub(ycOrder.getDisplayValue(), orderSuccessFee,
                DecimalPlaces.THREE.value(), BigDecimal.ROUND_HALF_UP);

        Delivery delivery = new Delivery();
        delivery.setUserCode(userCode);
        delivery.setOrderNo(ycOrder.getOrderNo());
        delivery.setMerchantId(upr.getIdentityId());
        delivery.setMerchantOrderNo(ycOrder.getMerchantOrderNo());
        delivery.setDeliveryStartTime(new Date());
        delivery.setPreDeliveryTime(preDeliveryTime);
        delivery.setDeliveryStatus(Constant.Delivery.DELIVERY_STATUS_WAIT);// 等待发货状态。需要写入常量
        delivery.setProductId(upr.getProductId());
        delivery.setProductFace(waitDeliveryFee);
        delivery.setDisplayValue(waitDeliveryFee);
        delivery.setParValue(ycOrder.getProductFace());
        delivery.setProductSaleDiscount(ycOrder.getProductSaleDiscount());
        delivery.setCostDiscount(upr.getDiscount());
        delivery.setCostFee(BigDecimalUtil.multiply(waitDeliveryFee, upr.getDiscount(),
            DecimalPlaces.THREE.value(), BigDecimal.ROUND_HALF_UP));
//        delivery.setNextQueryTime(nextQueryTime);
        delivery.setQueryFlag(Constant.Delivery.QUERY_FLAG_NO_NEED);
        delivery.setQueryTimes(0l);
        return delivery;
    }
}
