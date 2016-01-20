package com.yuecheng.hops.transaction.service.order;


import java.util.List;

import com.yuecheng.hops.transaction.basic.entity.Order;


public interface OrderStatusManagement
{

    /**
     * 进入发货流程。将订单修改成正在发货状态
     * 
     * @param orders
     */
    public List<Long> updateOrderStatusToRECHARGING(List<Order> orders);

    /**
     * 部分成功绑定流程，将部分成功的订单修改成发货中状态。
     * 
     * @param orders
     */
    public List<Long> updateOrderStatusToSuccessPartRECHARGING(List<Order> orders);
}