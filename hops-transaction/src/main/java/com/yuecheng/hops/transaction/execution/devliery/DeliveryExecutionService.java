/*
 * 文件名：DeliveryExecutionService.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2014年11月26日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.transaction.execution.devliery;

import com.yuecheng.hops.transaction.basic.entity.Delivery;

public interface DeliveryExecutionService
{
    /**
     * 往供货商查询订单信息时，查询次数+1
     * 
     * @param delivery
     * @return 
     * @see
     */
    public Delivery addQueryTime(Long deliveryId);
}
