package com.yuecheng.hops.transaction.execution.order.action;


import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.account.entity.role.IdentityAccountRole;
import com.yuecheng.hops.account.service.IdentityAccountRoleService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.AccountModelType;
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
import com.yuecheng.hops.transaction.service.process.OrderCloserProcess;


@Service("sendOrderCloseAction")
public class SendOrderCloseAction implements SendOrderAction
{
    private static final Logger logger = LoggerFactory.getLogger(SendOrderCloseAction.class);

    @Autowired
    private OrderCloserProcess orderCloserProcess;

    @Autowired
    private AirtimeAccountingTransaction airtimeAccountingTransaction;

    @Autowired
    private IdentityAccountRoleService identityAccountRoleService;

    @Autowired
    private OrderManagement orderManagement;

    @Autowired
    private DeliveryManagement deliveryManagement;

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void doAction(Delivery delivery, Map<String, Object> map)
    {
        logger.debug("订单发送流程:接收状态转义[Close]!");
        Order order = orderManagement.findOne(delivery.getOrderNo());
        // 关闭订单
        BigDecimal frozenAmt = (BigDecimal)TransactionContextUtil.getTransactionContextParam(TransactionMapKey.FROZEN_AMT);
        IdentityAccountRole payee = (IdentityAccountRole)TransactionContextUtil.getTransactionContextParam(TransactionMapKey.SYSTEM_DEBIT_ACCOUNT);
        try
        {
            airtimeAccountingTransaction.unfrozenAccount(payee.getAccountId(), payee.getAccountType(), AccountModelType.FUNDS, frozenAmt, order.getOrderNo());
            ActionContextUtil.init();
            ActionContextUtil.setActionContext(ActionMapKey.ORDER, order);
            ActionContextUtil.setActionContext(ActionMapKey.DELIVERY, delivery);
            ActionContextUtil.setActionContext(ActionMapKey.ERROR_CODE, Constant.ErrorCode.SUPPLY_MERCHANT_RESPONSE_ORDER_FAIL);
            orderCloserProcess.execute();
        }
        catch (Exception e)
        {
            logger.error("sendOrderCloseAction happen Exception caused by "+ ExceptionUtil.getStackTraceAsString(e) + "  params[" + String.valueOf(frozenAmt).toString() + "  " + String.valueOf(payee).toString() + "  " + String.valueOf(order).toString()+"]");
            throw new ApplicationException("transaction002022", e);
        }
    }

}
