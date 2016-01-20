package com.yuecheng.hops.transaction.service.order;


import java.util.List;

import com.yuecheng.hops.transaction.basic.entity.Order;


/**
 * 订单相关操作服务层
 * 
 * @author Jinger 2014-03-05
 */
public interface OrderService
{
    /**
     * 进入发货流程，查询所有符合条件的订单
     * 
     * @return
     */
    public List<Order> findBindOrders();

    /**
     * 预成功订单流程，将所有预成功订单的通知记录修改为成功状态
     * 
     * @return
     */
    public List<Order> findFakeOrders();

    /**
     * 检索部分成功订单
     * 
     * @return
     */
    public List<Order> findPartSuccessOrders();

    /**
     * 检查完订单必输字段后，初始化下单交易数据
     */
    public void OrderDataInitialization();
}
