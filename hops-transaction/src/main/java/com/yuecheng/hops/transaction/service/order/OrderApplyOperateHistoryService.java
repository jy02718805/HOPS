/*
 * 文件名：OrderApplyOperateHistoryService.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2014年11月24日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.transaction.service.order;

import java.util.List;

import com.yuecheng.hops.transaction.basic.entity.OrderApplyOperateHistory;

public interface OrderApplyOperateHistoryService
{
    public OrderApplyOperateHistory save(OrderApplyOperateHistory orderApplyOperateHistory);
    
    public List<OrderApplyOperateHistory> queryApplyOperateHistoryListByOrderNo(Long orderNo);
}
