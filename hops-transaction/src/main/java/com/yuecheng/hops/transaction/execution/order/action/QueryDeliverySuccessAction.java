package com.yuecheng.hops.transaction.execution.order.action;


import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.PrintUtil;
import com.yuecheng.hops.transaction.TransactionContextUtil;
import com.yuecheng.hops.transaction.TransactionMapKey;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;
import com.yuecheng.hops.transaction.service.order.OrderManagement;
import com.yuecheng.hops.transaction.service.process.DeliverySuccessProcess;
import com.yuecheng.hops.transaction.service.process.OrderSuccessProcess;

@Scope("prototype")
@Service("queryDeliverySuccessAction")
public class QueryDeliverySuccessAction implements QueryDeliveryAction
{
    private static final Logger       logger = LoggerFactory.getLogger(QueryDeliverySuccessAction.class);

    @Autowired
    private DeliveryManagement deliveryManagement;
    
    @Autowired
    private OrderManagement orderManagement;

    @Autowired
    private DeliverySuccessProcess deliverySuccessProcess;
    
    @Autowired
    private OrderSuccessProcess orderSuccessProcess;
    
    @Override
    public void doAction(Delivery delivery, BigDecimal orderSuccessFee)
    {
        Order order = orderManagement.findOne(delivery.getOrderNo());
            try
            {
                logger.debug("查询供货商订单信息流程:查询订单成功!" + String.valueOf(delivery).toString() + "  orderSuccessFee["+String.valueOf(orderSuccessFee).toString()+"]");
                ActionContextUtil.init();
                ActionContextUtil.setActionContext(ActionMapKey.ORDER, order);
                ActionContextUtil.setActionContext(ActionMapKey.DELIVERY, delivery);
                ActionContextUtil.setActionContext(ActionMapKey.ORDER_SUCCESS_FEE, orderSuccessFee);
                ActionContextUtil.setActionContext(ActionMapKey.DELIVERY_RESULT, TransactionContextUtil.getTransactionContextParam(TransactionMapKey.DELIVERY_RESULT));
                ActionContextUtil.setActionContext(ActionMapKey.QUERY_MSG, TransactionContextUtil.getTransactionContextParam(TransactionMapKey.QUERY_MSG));
                logger.debug("QueryOrderSuccessAction_ActionContext["+PrintUtil.mapToString(ActionContextUtil.getActionContextLocal())+"]");
                //1.修改发货记录状态成功     2.解冻成功金额，并转款到上游账户     3.修改发货记录标示结束。
                deliverySuccessProcess.execute();
            }
            catch (HopsException e)
            {
                throw e;
            }
            catch (Exception e)
            {
                logger.error("queryOrderSuccessAction happen Exception caused by "+ ExceptionUtil.getStackTraceAsString(e));
                throw new ApplicationException("transaction002038", e);
            }
            
            try
            {
                ActionContextUtil.init();
                ActionContextUtil.setActionContext(ActionMapKey.ORDER, order);
                ActionContextUtil.setActionContext(ActionMapKey.DELIVERY, delivery);
                ActionContextUtil.setActionContext(ActionMapKey.ORDER_SUCCESS_FEE, orderSuccessFee);
                //比较金额是否与产品面值相等   相等：1.修改订单状态成功，2.将金额转款到利润账户 3.发起通知
                //                不相等： 1.修改订单状态部分成功。
                orderSuccessProcess.execute();
            }
            catch (HopsException e)
            {
                throw e;
            }
            catch (Exception e)
            {
                logger.error("queryOrderSuccessAction happen Exception caused by "+ ExceptionUtil.getStackTraceAsString(e));
                throw new ApplicationException("transaction002038", e);
            }
        }
}
