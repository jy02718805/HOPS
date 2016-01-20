package com.yuecheng.hops.transaction.service.notify;


import java.util.List;

import com.yuecheng.hops.transaction.basic.entity.Notify;


/**
 * 订单相关操作服务层
 * 
 * @author Jinger 2014-03-07
 */
public interface NotifyService
{
    /**
     * 保存通知记录
     * 
     * @param notify
     * @return
     */
    public Notify save(Notify notify);

    /**
     * 通过订单号查询通知记录
     * 
     * @param orderNo
     * @return
     */
    public Notify findNotifyByOrderNo(Long orderNo);

    /**
     * 获取需要通知代理商的所有通知记录
     * 
     * @return
     */
    public List<Notify> findNotifyOrders();

    /**
     * 根据ID获取notify记录
     * 
     * @param id
     * @return
     */
    public Notify findNotifyById(Long id);
    
    /**
     * 重新通知
     * @param status
     * @param orderNo
     * @return 
     * @see
     */
    Integer reNotify(Integer status, Long orderNo);
}
