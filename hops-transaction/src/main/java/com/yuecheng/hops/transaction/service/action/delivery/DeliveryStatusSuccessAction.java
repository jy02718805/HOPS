/*
 * 文件名：CheckOrderAction.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月22日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.transaction.service.action.delivery;


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
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.BigDecimalUtil;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.transaction.TransactionMapKey;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.execution.account.AirtimeAccountingTransaction;
import com.yuecheng.hops.transaction.service.StatusManagementService;
import com.yuecheng.hops.transaction.service.action.ActionHandler;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;

@Scope("prototype")
@Service("deliveryStatusSuccessAction")
public class DeliveryStatusSuccessAction extends ActionHandler
{
    private static Logger           logger = LoggerFactory.getLogger(DeliveryStatusSuccessAction.class);

    @Autowired
    @Qualifier("deliveryStatusManagementServiceImpl")
    private StatusManagementService deliveryStatusManagementServiceImpl;
    
    @Autowired
    private DeliveryManagement      deliveryManagement;
    
    @Autowired
    private AirtimeAccountingTransaction airtimeAccountingTransaction;
    
    @Autowired
    private IdentityAccountRoleService       identityAccountRoleService;

    @Override
    public void handleRequest()
        throws HopsException
    {
        //修改状态
        Delivery delivery = (Delivery)ActionContextUtil.getActionContextParam(ActionMapKey.DELIVERY);
        Order order = (Order)ActionContextUtil.getActionContextParam(ActionMapKey.ORDER);
        String deliveryResult = (String)ActionContextUtil.getActionContextParam(TransactionMapKey.DELIVERY_RESULT);
        String queryMsg = (String)ActionContextUtil.getActionContextParam(TransactionMapKey.QUERY_MSG);
        BigDecimal orderSuccessFee = (BigDecimal)ActionContextUtil.getActionContextParam(ActionMapKey.ORDER_SUCCESS_FEE);
        Integer deliveryStatus = delivery.getDeliveryStatus();
        try
        {
            logger.debug("修改发货状态(DELIVERY_STATUS_SENDED->DELIVERY_STATUS_SUCCESS) [开始]"+delivery.getDeliveryId());
            delivery = (Delivery)deliveryStatusManagementServiceImpl.updateStatus(delivery.getDeliveryId(),
                delivery.getDeliveryStatus(), Constant.Delivery.DELIVERY_STATUS_SUCCESS);
            logger.debug("修改发货状态(DELIVERY_STATUS_SENDING->DELIVERY_STATUS_SUCCESS) [结束]"+delivery.getDeliveryId());
            delivery.setDeliveryResult(deliveryResult);
            delivery.setDeliveryFinishTime(new Date());
            delivery.setSuccessFee(orderSuccessFee);
            delivery.setQueryMsg(queryMsg);
            deliveryManagement.updateDeliveryStatus(delivery, deliveryStatus);
            
            ActionContextUtil.setActionContext(ActionMapKey.DELIVERY, delivery);
        }
        catch (Exception e)
        {
            logger.error("deliveryStatusSuccessAction happen Exception caused by "+ ExceptionUtil.getStackTraceAsString(e));
            throw new ApplicationException("deliverySuccess1", new String[]{ExceptionUtil.getStackTraceAsString(e)}, e);
        }
        
        //账户操作 1.解冻系统借记账户 2.系统借记账户转款到供货商账户
        try
        {
          //系统借记账户
            IdentityAccountRole spDebitAccount = identityAccountRoleService.getIdentityAccountRoleByParams(
                Constant.AccountType.SYSTEM_DEBIT, order.getMerchantId(),
                IdentityType.MERCHANT.toString(), Constant.Account.ACCOUNT_RELATION_TYPE_USE, order.getOrderNo());
            
            //上游账户
            IdentityAccountRole upMerchantAccount = identityAccountRoleService.getIdentityAccountRoleByParams(
                Constant.AccountType.MERCHANT_CREDIT, delivery.getMerchantId(),
                IdentityType.MERCHANT.toString(), Constant.Account.ACCOUNT_RELATION_TYPE_OWN, null);//!!!null 重构下
            
            Assert.notNull(spDebitAccount, "spDebitAccount is null!");
            Assert.notNull(upMerchantAccount, "upMerchantAccount is null!");
            
            BigDecimal discount = null;
            if(order.getProductSaleDiscount().compareTo(delivery.getCostDiscount()) >= 0)
            {
                //正常交易(代理商折扣数值大于等于供货商折扣数值)
                discount = order.getProductSaleDiscount();
            }
            else
            {
                //亏本交易
                discount = delivery.getCostDiscount();
            }
            
            BigDecimal unforzenAmt = BigDecimalUtil.multiply(orderSuccessFee, discount, DecimalPlaces.THREE.value(), BigDecimal.ROUND_HALF_UP);
            BigDecimal costSuccessAmt = BigDecimalUtil.multiply(orderSuccessFee,
                delivery.getCostDiscount(), DecimalPlaces.THREE.value(), BigDecimal.ROUND_HALF_UP);
            
            airtimeAccountingTransaction.deliverySuccessTransfer(order.getOrderNo(), 
                spDebitAccount.getAccountId(), spDebitAccount.getAccountType(), unforzenAmt, 
                upMerchantAccount.getAccountId(), upMerchantAccount.getAccountType(), costSuccessAmt, "供货商返回：订单成功，订单号:[" + order.getOrderNo() + "], 解冻系统借记账户["+spDebitAccount.getAccountId()+"],金额"+ unforzenAmt +" 并转款到供货商账户[" + upMerchantAccount.getAccountId() + "],金额[" + costSuccessAmt + "]");
        }
        catch (Exception e)
        {
//            delivery.setDeliveryResult(StringUtil.initString());
//            delivery.setDeliveryFinishTime(null);
//            delivery.setSuccessFee(null);
//            delivery.setDeliveryStatus(deliveryStatus);
//            delivery = deliveryManagement.save(delivery);
            ActionContextUtil.setActionContext(ActionMapKey.DELIVERY, delivery);
            logger.error("deliveryStatusSuccessAction transfer happen Exception caused by "+ ExceptionUtil.getStackTraceAsString(e));
            throw new ApplicationException("transaction002045", new String[]{ExceptionUtil.getStackTraceAsString(e)}, e);//!!!
        }
    }
}
