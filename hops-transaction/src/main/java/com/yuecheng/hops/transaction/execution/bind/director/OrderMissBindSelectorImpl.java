package com.yuecheng.hops.transaction.execution.bind.director;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.execution.bind.action.OrderMissBindAction;


@Service("orderMissBindSelector")
public class OrderMissBindSelectorImpl implements OrderMissBindSelector
{
    @Autowired
    @Qualifier("manualReviewOrderAction")
    private OrderMissBindAction manualReviewOrderAction;

    @Autowired
    @Qualifier("bindOrderAction")
    private OrderMissBindAction bindOrderAction;

    @Autowired
    @Qualifier("orderBindFailAction")
    private OrderMissBindAction orderBindFailAction;
    
    @Override
    public OrderMissBindAction select(Order order)
    {
        OrderMissBindAction service = null;
        if (order.getBindTimes().compareTo(order.getLimitBindTimes()) >= 0)
        {
            if (order.getPreSuccessStatus().compareTo(
                Constant.OrderStatus.PRE_SUCCESS_STATUS_NO_NEED) == 0)
            {
                // 非预成功
                service = orderBindFailAction;// 绑定失败
            }
            else{
                if (order.getOrderPreSuccessTime().compareTo(new Date()) < 0)
                {
                    // 预成功
                    // 超过绑定次数
                    service = manualReviewOrderAction;// 人工审核
                }
                else
                {
                    service = orderBindFailAction;// 绑定失败
                }
            }
        }
        else
        {
            // 未超过绑定次数
            service = bindOrderAction;// 重绑
        }
        return service;
    }

}
