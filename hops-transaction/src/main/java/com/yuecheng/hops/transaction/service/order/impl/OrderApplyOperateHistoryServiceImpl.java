/*
 * 文件名：OrderApplyOperateHistoryServiceImpl.java 版权：Copyright by www.365haoyou.com 描述：
 * 修改人：Administrator 修改时间：2014年11月24日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.transaction.service.order.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.transaction.basic.entity.OrderApplyOperateHistory;
import com.yuecheng.hops.transaction.basic.repository.OrderApplyOperateHistoryDao;
import com.yuecheng.hops.transaction.service.order.OrderApplyOperateHistoryService;


@Service("orderApplyOperateHistoryService")
public class OrderApplyOperateHistoryServiceImpl implements OrderApplyOperateHistoryService
{
    @Autowired
    private OrderApplyOperateHistoryDao orderApplyOperateHistoryDao;

    @Override
    public OrderApplyOperateHistory save(OrderApplyOperateHistory orderApplyOperateHistory)
    {
        orderApplyOperateHistory = orderApplyOperateHistoryDao.save(orderApplyOperateHistory);
        return orderApplyOperateHistory;
    }

    @Override
    public List<OrderApplyOperateHistory> queryApplyOperateHistoryListByOrderNo(Long orderNo)
    {
        List<OrderApplyOperateHistory> oaohList = orderApplyOperateHistoryDao.queryApplyOperateHistoryListByOrderNo(orderNo);
        return oaohList;
    }

}
