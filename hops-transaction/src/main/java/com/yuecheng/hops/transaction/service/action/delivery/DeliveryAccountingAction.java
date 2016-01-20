/*
 * 文件名：CheckOrderAction.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月22日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.transaction.service.action.delivery;


import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.yuecheng.hops.account.entity.role.IdentityAccountRole;
import com.yuecheng.hops.account.service.IdentityAccountRoleService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.AccountModelType;
import com.yuecheng.hops.common.enump.DecimalPlaces;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.BigDecimalUtil;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.execution.account.AirtimeAccountingTransaction;
import com.yuecheng.hops.transaction.service.action.ActionHandler;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;
import com.yuecheng.hops.transaction.service.order.OrderManagement;

@Scope("prototype")
@Service("deliveryAccountingAction")
public class DeliveryAccountingAction extends ActionHandler
{
    private static Logger          logger = LoggerFactory.getLogger(DeliveryAccountingAction.class);

    @Autowired
    private IdentityAccountRoleService identityAccountRoleService;
    
    @Autowired
    private AirtimeAccountingTransaction airtimeAccountingTransaction;
    
    @Autowired
    private DeliveryManagement deliveryManagement;
    
    @Autowired
    private OrderManagement orderManagement;
    
    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void handleRequest()
        throws HopsException
    {
        Long deliveryId = (Long)ActionContextUtil.getActionContextParam(ActionMapKey.DELIVERY_ID);
        Assert.notNull(deliveryId);
        logger.debug("deliveryId["+String.valueOf(deliveryId).toString()+"]");
        Delivery delivery = deliveryManagement.findDeliveryById(deliveryId);
        Order order = orderManagement.findOne(delivery.getOrderNo());
        ActionContextUtil.setActionContext(ActionMapKey.ORDER, order);
        ActionContextUtil.setActionContext(ActionMapKey.DELIVERY, delivery);
        
        BigDecimal discount = order.getProductSaleDiscount();
        BigDecimal frozenFee=  BigDecimalUtil.sub(order.getDisplayValue(), BeanUtils.isNotNull(order.getOrderSuccessFee())?order.getOrderSuccessFee():new BigDecimal("0"), DecimalPlaces.THREE.value(), BigDecimal.ROUND_HALF_UP);
        BigDecimal frozenAmt = BigDecimalUtil.multiply(frozenFee, discount, DecimalPlaces.THREE.value(), BigDecimal.ROUND_HALF_UP);
        IdentityAccountRole payee = identityAccountRoleService.getIdentityAccountRoleByParams(Constant.AccountType.SYSTEM_DEBIT, order.getMerchantId(), IdentityType.MERCHANT.toString(), Constant.Account.ACCOUNT_RELATION_TYPE_USE, order.getOrderNo());
        try
        {
            logger.debug("订单发送流程:冻结账户[" + String.valueOf(payee).toString() + "] frozenAmt[" + frozenAmt + "]");
            /* 1.冻结 */
            airtimeAccountingTransaction.frozenAccount(payee.getAccountId(), payee.getAccountType(), AccountModelType.FUNDS, frozenAmt, delivery.getOrderNo());
            
            ActionContextUtil.setActionContext(ActionMapKey.FROZEN_AMT, frozenAmt);
            ActionContextUtil.setActionContext(ActionMapKey.SYSTEM_DEBIT_ACCOUNT, payee);
            logger.debug("订单发送流程：发送发货记录");
        }
        catch (Exception e)
        {
            logger.debug("订单发送流程：发生异常,解冻账户[" + String.valueOf(payee).toString() + "] frozenAmt[" + String.valueOf(frozenAmt).toString() + "]" + ExceptionUtil.getStackTraceAsString(e));
//            airtimeAccountingTransaction.unfrozenAccount(payee.getAccountId(), payee.getAccountType(), AccountModelType.FUNDS, frozenAmt, delivery.getOrderNo());
            throw new ApplicationException("transaction002003", new String[]{ExceptionUtil.getStackTraceAsString(e)}, e);
        }
    }
}
