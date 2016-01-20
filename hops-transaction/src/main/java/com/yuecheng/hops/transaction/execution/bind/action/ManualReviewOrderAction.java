package com.yuecheng.hops.transaction.execution.bind.action;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.transaction.TransactionContextUtil;
import com.yuecheng.hops.transaction.TransactionMapKey;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.basic.repository.OrderJpaDao;


@Service("manualReviewOrderAction")
public class ManualReviewOrderAction implements OrderMissBindAction
{
    private static Logger logger = LoggerFactory.getLogger(ManualReviewOrderAction.class);

    @Autowired
    private OrderJpaDao orderJpaDao;

    /**
     * 将订单处理成人工审核状态
     * 
     * @param order
     */
    @Transactional
    public void execute(Order order)
    {
        try
        {
            logger.debug("订单[" + order.toString() + "] 置为人工审核！开始");
            orderJpaDao.updateOrderManualFlag(Constant.OrderManualFlag.ORDER_MANUAL_FLAG_ING, order.getOrderNo());
            TransactionContextUtil.setTransactionContext(TransactionMapKey.ORDER, order);
            logger.debug("订单[" + order.toString() + "] 置为人工审核！结束");
        }
        catch (Exception e)
        {
            logger.debug("订单[" + String.valueOf(order).toString() + "] 置为人工审核发生异常! 异常信息["+ExceptionUtil.getStackTraceAsString(e)+"]");
            throw new ApplicationException("transaction002033", e);
        }
       
    }
}
