package com.yuecheng.hops.transaction.execution.order.action;


import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.account.service.IdentityAccountRoleService;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.transaction.TransactionContextUtil;
import com.yuecheng.hops.transaction.TransactionMapKey;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.execution.account.AirtimeAccountingTransaction;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;
import com.yuecheng.hops.transaction.service.order.OrderManagement;
import com.yuecheng.hops.transaction.service.process.OrderReBindProcess;


@Scope("prototype")
@Service("queryDeliveryFailAction")
public class QueryDeliveryFailAction implements QueryDeliveryAction
{
    private static final Logger logger = LoggerFactory.getLogger(QueryDeliveryFailAction.class);

    @Autowired
    private AirtimeAccountingTransaction airtimeAccountingTransaction;

    @Autowired
    private IdentityAccountRoleService identityAccountRoleService;

    @Autowired
    private DeliveryManagement deliveryManagement;

    @Autowired
    private OrderManagement orderManagement;

    @Autowired
    private OrderReBindProcess orderReBindProcess;

    @Override
    public void doAction(Delivery delivery, BigDecimal orderSuccessFee)
    {
            try
            {
                logger.debug("查询供货商订单信息流程:查询订单返回失败!" + String.valueOf(delivery).toString());
                Order order = orderManagement.findOne(delivery.getOrderNo());
                
                ActionContextUtil.init();
                ActionContextUtil.setActionContext(ActionMapKey.ORDER, order);
                ActionContextUtil.setActionContext(ActionMapKey.QUERY_MSG, TransactionContextUtil.getTransactionContextParam(TransactionMapKey.QUERY_MSG));
                ActionContextUtil.setActionContext(ActionMapKey.DELIVERY, delivery);
                ActionContextUtil.setActionContext(
                    ActionMapKey.DELIVERY_RESULT,
                    TransactionContextUtil.getTransactionContextLocal().get(
                        TransactionMapKey.DELIVERY_RESULT));
                orderReBindProcess.execute();
            }
            catch (Exception e)
            {//!!!异常优化
                logger.error("queryOrderFailAction happen Exception caused by " + ExceptionUtil.getStackTraceAsString(e));
                throw new ApplicationException("transaction002040", e);
            }
        }

}
