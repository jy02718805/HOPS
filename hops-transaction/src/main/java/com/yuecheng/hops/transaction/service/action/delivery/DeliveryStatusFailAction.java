/*
 * 文件名：CheckOrderAction.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月22日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.transaction.service.action.delivery;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

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
import com.yuecheng.hops.transaction.service.delivery.DeliveryService;

@Scope("prototype")
@Service("deliveryStatusFailAction")
public class DeliveryStatusFailAction extends ActionHandler
{
    private static Logger           logger = LoggerFactory.getLogger(DeliveryStatusFailAction.class);

    @Autowired
    private DeliveryService         deliveryService;

    @Autowired
    private DeliveryManagement      deliveryManagement;
    
    @Autowired
    private IdentityAccountRoleService identityAccountRoleService;
    
    @Autowired
    private AirtimeAccountingTransaction airtimeAccountingTransaction;

    @Autowired
    @Qualifier("deliveryStatusManagementServiceImpl")
    private StatusManagementService deliveryStatusManagementServiceImpl;

    @Override
    public void handleRequest()
        throws HopsException
    {
        Order order = (Order)ActionContextUtil.getActionContextParam(ActionMapKey.ORDER);
        String deliveryResult = (String)ActionContextUtil.getActionContextParam(ActionMapKey.DELIVERY_RESULT);
        String queryMsg = (String)ActionContextUtil.getActionContextParam(TransactionMapKey.QUERY_MSG);
        try
        {
            List<Delivery> deliverys = deliveryManagement.findDeliveryByOrderNo(order.getOrderNo());
            // 进入重绑流程，需要将之前绑定记录置为失败。
            
            Integer deliveryStatus = -1;//!!! 改为 null
            for (Delivery delivery : deliverys)
            {
                deliveryStatus = delivery.getDeliveryStatus();
                logger.debug("修改发货记录状态失败 [开始]"+delivery.getDeliveryId());
                if(Constant.Delivery.DELIVERY_STATUS_FAIL != delivery.getDeliveryStatus()){
                    delivery = (Delivery)deliveryStatusManagementServiceImpl.updateStatus(delivery.getDeliveryId(), delivery.getDeliveryStatus(), Constant.Delivery.DELIVERY_STATUS_FAIL);
                    // 修改绑定记录
                    if(StringUtil.isNotBlank(deliveryResult)){
                        delivery.setDeliveryResult(deliveryResult);
                    }
                    delivery.setDeliveryFinishTime(new Date());
                    delivery.setSuccessFee(new BigDecimal(0));
                    if(StringUtil.isNotBlank(queryMsg)){
                        delivery.setQueryMsg(queryMsg);
                    }
                    deliveryManagement.updateDeliveryStatus(delivery, deliveryStatus);
                    ActionContextUtil.setActionContext(ActionMapKey.DELIVERY, delivery);
                    
                    if(Constant.Delivery.DELIVERY_STATUS_SUCCESS != deliveryStatus){
                        // 解冻发货时冻结的金额。
                        IdentityAccountRole payee = identityAccountRoleService.getIdentityAccountRoleByParams(
                            Constant.AccountType.SYSTEM_DEBIT, order.getMerchantId(),
                            IdentityType.MERCHANT.toString(), Constant.Account.ACCOUNT_RELATION_TYPE_USE,
                            order.getOrderNo());
                        BigDecimal discount = null;
                        if (order.getProductSaleDiscount().compareTo(delivery.getCostDiscount()) >= 0)
                        {
                            // 正常交易(代理商折扣数值大于等于供货商折扣数值)
                            discount = order.getProductSaleDiscount();
                        }
                        else
                        {
                            // 亏本交易
                            discount = delivery.getCostDiscount();
                        }
        
                        BigDecimal  frozenFee = BigDecimalUtil.sub(order.getDisplayValue(),
                                order.getOrderSuccessFee(), DecimalPlaces.THREE.value(), BigDecimal.ROUND_HALF_UP);
                        
                        BigDecimal frozenAmt = BigDecimalUtil.multiply(frozenFee, discount,
                            DecimalPlaces.THREE.value(), BigDecimal.ROUND_HALF_UP);
                        
                        airtimeAccountingTransaction.unfrozenAccount(payee.getAccountId(),
                            payee.getAccountType(), AccountModelType.FUNDS, frozenAmt,
                            order.getOrderNo());
                    }
                }
                logger.debug("修改发货记录状态失败 [结束]"+delivery.getDeliveryId());
            }
            ActionContextUtil.setActionContext(ActionMapKey.ORDER, order);
            ActionContextUtil.setActionContext(ActionMapKey.RESPONSE_STR, deliveryResult);
            if(BeanUtils.isNotNull(this.successor)){
                this.successor.handleRequest();
            }
        }
        catch (Exception e)
        {
            logger.error("deliveryStatusFailAction happen Exception caused by "+ ExceptionUtil.getStackTraceAsString(e));
            throw new ApplicationException("transaction002004", new String[]{ExceptionUtil.getStackTraceAsString(e)}, e);//!!!
        }
    }
}
