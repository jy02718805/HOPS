/*
 * 文件名：CheckOrderAction.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月22日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.transaction.service.action.order;


import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.yuecheng.hops.account.entity.role.IdentityAccountRole;
import com.yuecheng.hops.account.service.IdentityAccountRoleService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.DecimalPlaces;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.BigDecimalUtil;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.execution.account.AirtimeAccountingTransaction;
import com.yuecheng.hops.transaction.service.StatusManagementService;
import com.yuecheng.hops.transaction.service.action.ActionHandler;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.order.OrderManagement;

@Scope("prototype")
@Service("orderStatusSuccessAction")
public class OrderStatusSuccessAction extends ActionHandler
{
    private static Logger                logger = LoggerFactory.getLogger(OrderStatusSuccessAction.class);

    @Autowired
    private OrderManagement              orderManagement;
    
    @Autowired
    @Qualifier("orderStatusManagementServiceImpl")
    private StatusManagementService      orderStatusManagementServiceImpl;

    @Autowired
    private AirtimeAccountingTransaction airtimeAccountingTransaction;

    @Autowired
    private IdentityAccountRoleService       identityAccountRoleService;

    @Override
    public void handleRequest()
        throws HopsException
    {
        Order order = (Order)ActionContextUtil.getActionContextParam(ActionMapKey.ORDER);
        Delivery delivery = (Delivery)ActionContextUtil.getActionContextParam(ActionMapKey.DELIVERY);
        BigDecimal orderSuccessFee = (BigDecimal)ActionContextUtil.getActionContextParam(ActionMapKey.ORDER_SUCCESS_FEE);
        Integer originalOrderStatus = order.getOrderStatus();
        try
        {
            logger.debug("修改订单状态(RECHARGING->SUCCESS) [开始]"+order.getOrderNo());
            order = (Order)orderStatusManagementServiceImpl.updateStatus(order.getOrderNo(),
                order.getOrderStatus(), Constant.OrderStatus.SUCCESS);
            
            orderManagement.updateOrderSuccess(order, originalOrderStatus);
            
            order.setOrderSuccessFee(orderSuccessFee);
            if(null == order.getOrderFinishTime())
            {
                order.setOrderFinishTime(new Date());//!!!操作提前
            }
            orderManagement.save(order);
            logger.debug("修改订单状态(RECHARGING->SUCCESS) [结束]"+order.getOrderNo());
            
            ActionContextUtil.setActionContext(ActionMapKey.ORDER, order);
        }
        catch (Exception e)
        {
            logger.error("orderStatusSuccessAction happen Exception caused by "+ ExceptionUtil.getStackTraceAsString(e));
        }
        
        try
        {
            // 成功 系统账户向上游账户转款 系统账户向系统利润账户转款
            // 系统账户
            IdentityAccountRole spDebitAccount = identityAccountRoleService.getIdentityAccountRoleByParams(
                Constant.AccountType.SYSTEM_DEBIT, order.getMerchantId(),
                IdentityType.MERCHANT.toString(), Constant.Account.ACCOUNT_RELATION_TYPE_USE, order.getOrderNo());
            // 系统利润账户
            IdentityAccountRole spProfitAccount = identityAccountRoleService.getIdentityAccountRoleByParams(
                Constant.AccountType.SYSTEM_PROFIT, order.getMerchantId(),
                IdentityType.MERCHANT.toString(), Constant.Account.ACCOUNT_RELATION_TYPE_USE, null);//!!!
            Assert.notNull(spDebitAccount, "spDebitAccount is null!");
            Assert.notNull(spProfitAccount, "spProfitAccount is null!");
            // 成功面额 * 折扣 ==成功金额
            // 利润 == 成功面额 - 成功金额
            BigDecimal saleSuccessAmt = BigDecimalUtil.multiply(orderSuccessFee,
                delivery.getProductSaleDiscount(), DecimalPlaces.THREE.value(), BigDecimal.ROUND_HALF_UP);
            BigDecimal costSuccessAmt = BigDecimalUtil.multiply(orderSuccessFee,
                delivery.getCostDiscount(), DecimalPlaces.THREE.value(), BigDecimal.ROUND_HALF_UP);
            BigDecimal profitAmt = BigDecimalUtil.sub(saleSuccessAmt, costSuccessAmt,
                DecimalPlaces.THREE.value(), BigDecimal.ROUND_HALF_UP);
            
            airtimeAccountingTransaction.transfer(spDebitAccount.getAccountId(), spDebitAccount.getAccountType(),
                spProfitAccount.getAccountId(), spProfitAccount.getAccountType(), profitAmt, "供货商返回：订单成功，订单号:[" + order.getOrderNo() + "], 系统借记账户["+spDebitAccount.getAccountId()+"]转款到利润账户[" + spProfitAccount.getAccountId() + "]  金额[" + profitAmt + "]",
                Constant.TransferType.TRANSFER_SYSTEM_AGENT_PROFIT, delivery.getOrderNo());
        }
        catch (Exception e)
        {
            orderManagement.orderAnalysts(order.getOrderNo(), originalOrderStatus);//!!! 发起事件处理
            logger.error("orderStatusSuccessAction transfer happen Exception caused by "+ ExceptionUtil.getStackTraceAsString(e));
        }
    }
}
