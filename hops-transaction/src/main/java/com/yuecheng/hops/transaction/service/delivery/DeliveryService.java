package com.yuecheng.hops.transaction.service.delivery;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.yuecheng.hops.transaction.basic.entity.Delivery;


public interface DeliveryService
{

    /**
     * 通过订单编号以及商户ID，查询预发货时间
     * 
     * @param orderNo
     * @param merchantId
     * @return
     */
    public Date calcPreDeliveryTime(Long orderNo, Long merchantId);

    /**
     * 发货流程，查询出需要发货的发货记录。
     * 
     * @return
     */
    public List<Delivery> findSendOrders();

    /**
     * 查询需要去供货商查询订单详情的订单信息。
     * 
     * @return
     */
    public List<Delivery> findQueryOrders();

    /**
     * 根据状态和间隔时间查询发货记录
     * 
     * @Title: selectDeliveryByIntervalTime
     * @Description: TODO
     * @param @param intervalTime
     * @param @param status
     * @param @return 设定文件
     * @return List<Delivery> 返回类型
     * @throws
     */
    public List<Delivery> selectDeliveryByIntervalTime(long intervalTime, Integer status);
    
    /**
     * 查询异常发货记录
     * 
     * @return 
     * @see
     */
    public List<Long> findCloseDeliverys();
}
