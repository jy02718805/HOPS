package com.yuecheng.hops.transaction.execution.order.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.account.entity.role.IdentityAccountRole;
import com.yuecheng.hops.account.service.IdentityAccountRoleService;
import com.yuecheng.hops.account.service.TransactionHistoryService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.event.HopsPublisher;
import com.yuecheng.hops.common.event.HopsRequestEvent;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.NumberUtil;
import com.yuecheng.hops.common.utils.PrintUtil;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.operator.Operator;
import com.yuecheng.hops.numsection.entity.NumSection;
import com.yuecheng.hops.numsection.service.CheckNumSectionService;
import com.yuecheng.hops.product.entity.airtimes.AirtimeProduct;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;
import com.yuecheng.hops.product.service.ProductService;
import com.yuecheng.hops.product.service.SupplyProductRelationService;
import com.yuecheng.hops.transaction.TransactionContextUtil;
import com.yuecheng.hops.transaction.TransactionMapKey;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.basic.entity.OrderApplyOperateHistory;
import com.yuecheng.hops.transaction.event.SendOrderEvent;
import com.yuecheng.hops.transaction.execution.account.AirtimeAccountingTransaction;
import com.yuecheng.hops.transaction.execution.order.OrderTransaction;
import com.yuecheng.hops.transaction.execution.order.action.ForceOrderAction;
import com.yuecheng.hops.transaction.execution.product.SupplyProductTransaction;
import com.yuecheng.hops.transaction.execution.product.action.ScoreFilterAction;
import com.yuecheng.hops.transaction.service.StatusManagementService;
import com.yuecheng.hops.transaction.service.action.AbstractEventAction;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.builder.AgentOrderBinder;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;
import com.yuecheng.hops.transaction.service.notify.NotifyService;
import com.yuecheng.hops.transaction.service.order.OrderApplyOperateHistoryService;
import com.yuecheng.hops.transaction.service.order.OrderManagement;
import com.yuecheng.hops.transaction.service.process.DeliveryCreatorProcess;
import com.yuecheng.hops.transaction.service.process.ManualCloserOrderProcess;
import com.yuecheng.hops.transaction.service.process.ManualCloserSuccessOrderProcess;
import com.yuecheng.hops.transaction.service.process.OrderReBindProcess;
import com.yuecheng.hops.transaction.service.process.QuerySuccessProcess;
import com.yuecheng.hops.transaction.service.process.SupplyAmtLowProcess;


@Service("orderTransaction")
public class OrderTransactionImpl implements OrderTransaction
{
    private static final Logger          logger = LoggerFactory.getLogger(OrderTransactionImpl.class);

    @Autowired
    private OrderManagement              orderManagement;

    @Autowired
    private DeliveryManagement           deliveryManagement;

    @Autowired
    private NotifyService                notifyService;

    @Autowired
    private IdentityAccountRoleService       identityAccountRoleService;

    @Autowired
    private AirtimeAccountingTransaction airtimeAccountingTransaction;

    @Autowired
    private ProductService productService;
    
    @Autowired
    private CheckNumSectionService checkNumSectionService;
    
    @Autowired
    private SupplyProductRelationService supplyProductRelationService;
    
    @Autowired
    private ScoreFilterAction scoreFilterAction;
    
    @Autowired
    private TransactionHistoryService    transactionHistoryService;
    
    @Autowired
    private QuerySuccessProcess querySuccessProcess;
    
    @Autowired
    private OrderApplyOperateHistoryService orderApplyOperateHistoryService;
    
    @Autowired
    @Qualifier("orderStatusManagementServiceImpl")
    private StatusManagementService      orderStatusManagementServiceImpl;
    
    @Autowired
    @Qualifier("createDeliveryAction") 
    private AbstractEventAction createDeliveryAction;
    
    @Autowired
    @Qualifier("orderStatusRechargingAction")
    private AbstractEventAction orderStatusRechargingAction;
    
    @Autowired
    private ManualCloserSuccessOrderProcess manualCloserSuccessOrderProcess;
    
    @Autowired
    private ManualCloserOrderProcess manualCloserOrderProcess;
    
    @Autowired
    private DeliveryCreatorProcess deliveryCreatorProcess;
    
    @Autowired
    private ForceOrderAction forceOrderFailAction;
    
    @Autowired
    private OrderReBindProcess orderReBindProcess;
    
    @Autowired
    private SupplyProductTransaction supplyProductTransaction;
    
    @Autowired
    private SupplyAmtLowProcess supplyAmtLowProcess;
    
    @Autowired
    private HopsPublisher publisher;

    @Override
    public void closeOrder(String operatorName,Long orderNo,SupplyProductRelation upr, String userCode, String operaType)
    {
        logger.debug("begin OrderAction closeOrder!");
        String reason = StringUtil.initString();
        OrderApplyOperateHistory orderApplyOperateHistory = new OrderApplyOperateHistory();
        orderApplyOperateHistory.setCreateDate(new Date());
        orderApplyOperateHistory.setOperatorName(operatorName);
        orderApplyOperateHistory.setOrderNo(orderNo);
        
        
        Order order = orderManagement.findOne(orderNo);
        try
        {
            Integer originalManualFlag = order.getManualFlag();
            if(originalManualFlag.compareTo(Constant.OrderManualFlag.ORDER_MANUAL_FLAG_NO_NEED) == 0 || originalManualFlag.compareTo(Constant.OrderManualFlag.ORDER_MANUAL_FLAG_NO_CHECKSUPPLY) == 0){
                orderManagement.updateManualFlag(orderNo, Constant.OrderManualFlag.ORDER_MANUAL_FLAG_ING);
            }
            //排除重复处理
            orderManagement.updateManualFlag(orderNo, Constant.OrderManualFlag.ORDER_MANUAL_FLAG_ING, Constant.OrderManualFlag.ORDER_MANUAL_FLAG_DONE);
            
			if (Constant.OrderStatus.SUCCESS == order.getOrderStatus() && !Constant.AuditType.FORCE_FAIL_ORDER.equals(operaType) && !Constant.AuditType.RE_OPEN_ORDER.equals(operaType))
			{
				// 成功订单审核失败
				reason = Constant.AuditType.SUCCESS_ORDER;

				order.setOrderSuccessFee(new BigDecimal(0));
				order = orderManagement.save(order);
				ActionContextUtil.init();
				ActionContextUtil.setActionContext(ActionMapKey.ORDER, order);
				ActionContextUtil.setActionContext(ActionMapKey.ERROR_CODE, Constant.OrderStatus.ORDER_MANUAL_FAIL);
				manualCloserSuccessOrderProcess.execute();
			}
			else if (Constant.AuditType.FORCE_FAIL_ORDER.equals(operaType))
			{
				if(Constant.OrderStatus.SUCCESS == order.getOrderStatus()
					|| Constant.OrderStatus.FAILURE_ALL == order.getOrderStatus())
				{
					return;
				}
				// 强制审核失败
				reason = Constant.AuditType.FORCE_FAIL_ORDER;
				forceOrderFailAction.doAction(order);
			}
            else
            {
                //1.修改绑定次数 2.判断人工审核原因
                if(order.getBindTimes().compareTo(order.getLimitBindTimes()) >= 0){
                    //超绑
                    reason = Constant.AuditType.OUT_BIND_PRE_ORDER;
                    order.setLimitBindTimes(order.getLimitBindTimes() + 1);
                    order.setBindTimes(order.getBindTimes() + 1);
                }else{
                    if(order.getPreSuccessStatus() == Constant.OrderStatus.PRE_SUCCESS_STATUS_NO_NEED)
                    {
                        //普通订单超时
                        reason = Constant.AuditType.TIME_OUT_ORDER;
                    }
                    else
                    {
                        //预成功订单超时
                        reason = Constant.AuditType.TIME_OUT_PRE_ORDER;
                    }
                    order.setBindTimes(order.getBindTimes() + 1);
                }
                order.setCloseReason(reason);
                order.setErrorCode(Constant.OrderStatus.ORDER_MANUAL_FAIL);
                order = orderManagement.save(order);
                //2.修改delivery->fail order->wait
                ActionContextUtil.init();
                ActionContextUtil.setActionContext(ActionMapKey.SUPPLY_PRODUCT_RELATION, upr);
                ActionContextUtil.setActionContext(ActionMapKey.ORDER, order);
                ActionContextUtil.setActionContext(ActionMapKey.USER_CODE, userCode);
                manualCloserOrderProcess.execute();
                
                Delivery delivery = (Delivery)TransactionContextUtil.getTransactionContextParam(TransactionMapKey.DELIVERY);
                HopsRequestEvent hre = new HopsRequestEvent(AgentOrderBinder.class);
                
                String businessType = StringUtil.initString();
                if(NumberUtil.isFixedPhone(delivery.getUserCode()))
                {
                    businessType = Constant.BusinessType.BUSINESS_TYPE_FIXED;
                }
                else
                {
                    businessType = Constant.BusinessType.BUSINESS_TYPE_HF;
                }
                
                hre = new SendOrderEvent(AgentOrderBinder.class, delivery.getDeliveryId(), Integer.valueOf(businessType), upr);
                publisher.publishRequestEvent(hre);
                if(originalManualFlag.compareTo(Constant.OrderManualFlag.ORDER_MANUAL_FLAG_NO_NEED) == 0)
                {
                    orderManagement.updateManualFlag(orderNo, Constant.OrderManualFlag.ORDER_MANUAL_FLAG_NO_NEED);
                }
                else if (originalManualFlag.compareTo(Constant.OrderManualFlag.ORDER_MANUAL_FLAG_ING) == 0 || originalManualFlag.compareTo(Constant.OrderManualFlag.ORDER_MANUAL_FLAG_NO_CHECKSUPPLY) == 0)
                {
                    orderManagement.updateManualFlag(orderNo, Constant.OrderManualFlag.ORDER_MANUAL_FLAG_ING);
                }
            }
            
            logger.debug("end OrderAction closeOrder!");
        }
        catch (ApplicationException e)
        {
            if(!e.getCode().equalsIgnoreCase("transaction002033"))
            {
                orderManagement.updateManualFlag(orderNo, Constant.OrderManualFlag.ORDER_MANUAL_FLAG_ING);
            }
        }
        catch (Exception e)
        {
            orderManagement.updateManualFlag(orderNo, Constant.OrderManualFlag.ORDER_MANUAL_FLAG_ING);
        }
		finally
		{
		    reason = StringUtil.isNotBlank(operaType)?operaType:reason;
			orderApplyOperateHistory.setAction(reason);
			orderApplyOperateHistoryService.save(orderApplyOperateHistory);
		}
    }

    @Override
    public void successOrder(Operator operator,Long orderNo,BigDecimal orderSuccessFee, SupplyProductRelation upr, String userCode)
    {
        logger.debug("begin OrderTransaction successOrder!");
        String reason = "手工处理成功";
        OrderApplyOperateHistory orderApplyOperateHistory = new OrderApplyOperateHistory();
        orderApplyOperateHistory.setCreateDate(new Date());
        orderApplyOperateHistory.setOperatorName(operator.getDisplayName());
        orderApplyOperateHistory.setOrderNo(orderNo);
//        orderApplyOperateHistory.setAction(reason);
//        orderApplyOperateHistoryService.save(orderApplyOperateHistory);
        Order order = orderManagement.findOne(orderNo);
        Delivery delivery = null;
        try
        {
            Integer originalManualFlag = order.getManualFlag();
            if(originalManualFlag.compareTo(Constant.OrderManualFlag.ORDER_MANUAL_FLAG_NO_NEED) == 0 || originalManualFlag.compareTo(Constant.OrderManualFlag.ORDER_MANUAL_FLAG_NO_CHECKSUPPLY) == 0){
                orderManagement.updateManualFlag(orderNo, Constant.OrderManualFlag.ORDER_MANUAL_FLAG_ING);
            }
            //排除重复处理
            orderManagement.updateManualFlag(orderNo, Constant.OrderManualFlag.ORDER_MANUAL_FLAG_ING, Constant.OrderManualFlag.ORDER_MANUAL_FLAG_DONE);
            if(order.getBindTimes().compareTo(order.getLimitBindTimes()) >= 0){
                order.setLimitBindTimes(order.getLimitBindTimes() + 1);
                order.setBindTimes(order.getBindTimes() + 1);
            }else{
                order.setBindTimes(order.getBindTimes() + 1);
            }
            order.setCloseReason(reason);
            order.setErrorCode(Constant.OrderStatus.ORDER_MANUAL_SUCCESS);
            order = orderManagement.save(order);
            List<Delivery> deliveryList = deliveryManagement.findUnfinishedDeliveryList(orderNo);
            if(deliveryList.size() > 0){
                //有未完成delivery
                reason = Constant.AuditType.DELIVERY_UNFINISHED_ORDER;
                //正常成功流程
                delivery = deliveryList.get(0);
                ActionContextUtil.init();
                ActionContextUtil.setActionContext(ActionMapKey.ORDER, order);
                ActionContextUtil.setActionContext(ActionMapKey.DELIVERY, delivery);
                ActionContextUtil.setActionContext(ActionMapKey.ORDER_SUCCESS_FEE, orderSuccessFee);
                ActionContextUtil.setActionContext(ActionMapKey.RESPONSE_STR, StringUtil.initString());
                logger.debug("QueryOrderSuccessAction_ActionContext["+PrintUtil.mapToString(ActionContextUtil.getActionContextLocal())+"]");
                try
                {
                    querySuccessProcess.execute();
                    HopsRequestEvent hopsRequestEvent = (HopsRequestEvent)TransactionContextUtil.getTransactionContextParam(TransactionMapKey.HOPS_REQUEST_EVENT);
                    if(BeanUtils.isNotNull(hopsRequestEvent))
                    {
                        publisher.publishRequestEvent(hopsRequestEvent);
                    }
                }
                catch (ApplicationException e){
                    if(e.getCode().equalsIgnoreCase("transaction002045")){
                        ActionContextUtil.setActionContext(ActionMapKey.DELIVERY, delivery);
                        supplyAmtLowProcess.execute();
                    }
                    throw ExceptionUtil.throwException(new ApplicationException("transaction002029", e));
                }
                catch (Exception e)
                {
                    throw ExceptionUtil.throwException(new ApplicationException("transaction002029", e));
                }
            }
            else
            {
                //无未完成delivery
                reason = Constant.AuditType.DELIVERY_FINISHED_ORDER;
                // 添加Delivery记录
                try
                {
                    ActionContextUtil.init();
                    ActionContextUtil.setActionContext(ActionMapKey.SUPPLY_PRODUCT_RELATION, upr);
                    ActionContextUtil.setActionContext(ActionMapKey.ORDER, order);
                    ActionContextUtil.setActionContext(ActionMapKey.USER_CODE, userCode);
                    manualCloserOrderProcess.execute();
                    
                    delivery = (Delivery)TransactionContextUtil.getTransactionContextParam(TransactionMapKey.DELIVERY);
                    
                    String businessType = StringUtil.initString();
                    if(NumberUtil.isFixedPhone(delivery.getUserCode()))
                    {
                        businessType = Constant.BusinessType.BUSINESS_TYPE_FIXED;
                    }
                    else
                    {
                        businessType = Constant.BusinessType.BUSINESS_TYPE_HF;
                    }
                    
                    HopsRequestEvent hre = new HopsRequestEvent(AgentOrderBinder.class);
                    hre = new SendOrderEvent(AgentOrderBinder.class, delivery.getDeliveryId(), Integer.valueOf(businessType), upr);
                    publisher.publishRequestEvent(hre);
                }
                catch (Exception e)
                {
                    throw ExceptionUtil.throwException(new ApplicationException("transaction002029",e));
                }
            }
            
            if(originalManualFlag.compareTo(Constant.OrderManualFlag.ORDER_MANUAL_FLAG_NO_NEED) == 0)
            {
                orderManagement.updateManualFlag(orderNo, Constant.OrderManualFlag.ORDER_MANUAL_FLAG_NO_NEED);
            }
            else if (originalManualFlag.compareTo(Constant.OrderManualFlag.ORDER_MANUAL_FLAG_ING) == 0 || originalManualFlag.compareTo(Constant.OrderManualFlag.ORDER_MANUAL_FLAG_NO_CHECKSUPPLY) == 0)
            {
                orderManagement.updateManualFlag(orderNo, Constant.OrderManualFlag.ORDER_MANUAL_FLAG_ING);
            }
            
            logger.debug("end OrderTransaction successOrder!");
        }
        catch (ApplicationException e)
        {
            if(!e.getCode().equalsIgnoreCase("transaction002033"))
            {
                orderManagement.updateManualFlag(orderNo, Constant.OrderManualFlag.ORDER_MANUAL_FLAG_ING);
            }
        }
        catch (Exception e)
        {
            orderManagement.updateManualFlag(orderNo, Constant.OrderManualFlag.ORDER_MANUAL_FLAG_ING);
        }
        finally
		{
			orderApplyOperateHistory.setAction(reason);
			orderApplyOperateHistoryService.save(orderApplyOperateHistory);
		}
    }

    @Override
    public void successPartSuccessOrder(Long orderNo)
    {
        logger.debug("begin OrderAction successPartSuccessOrder!");

        Order order = orderManagement.findOne(orderNo);
        Integer manualFlag = order.getManualFlag();
        List<Delivery> deliveryList = deliveryManagement.findDeliveryByOrderNo(orderNo);
        Integer deliveryStatus = deliveryList.get(0).getDeliveryStatus();
        for (Delivery delivery : deliveryList)
        {
            delivery.setDeliveryStatus(Constant.Delivery.DELIVERY_STATUS_SUCCESS);
            deliveryManagement.save(delivery);
        }

        String reason = "手工处理成功";
        order = (Order)orderStatusManagementServiceImpl.updateStatus(order.getOrderNo(),
            order.getOrderStatus(), Constant.OrderStatus.SUCCESS);
        order.setCloseReason(reason);
        order.setOrderFinishTime(new Date());
        order.setErrorCode(Constant.OrderStatus.ORDER_MANUAL_SUCCESS);
        order = orderManagement.save(order);

        // 系统户
        IdentityAccountRole spDebitAccount = identityAccountRoleService.getIdentityAccountRoleByParams(
            Constant.AccountType.SYSTEM_DEBIT, order.getMerchantId(),
            IdentityType.MERCHANT.toString(), Constant.Account.ACCOUNT_RELATION_TYPE_USE,orderNo);
        // 系统利润账户
        IdentityAccountRole spProfitAccount = identityAccountRoleService.getIdentityAccountRoleByParams(
            Constant.AccountType.SYSTEM_PROFIT, order.getMerchantId(),
            IdentityType.MERCHANT.toString(), Constant.Account.ACCOUNT_RELATION_TYPE_USE,orderNo);
        // 上游账户
        IdentityAccountRole upMerchantAccount = identityAccountRoleService.getIdentityAccountRoleByParams(
            Constant.AccountType.MERCHANT_CREDIT, deliveryList.get(0).getMerchantId(),
            IdentityType.MERCHANT.toString(), Constant.Account.ACCOUNT_RELATION_TYPE_OWN,orderNo);

        if (!manualFlag.equals(Constant.OrderManualFlag.ORDER_MANUAL_FLAG_ING))
        {
            if (deliveryList.size() > 0)
            {
                if (deliveryStatus.equals(Constant.Delivery.DELIVERY_STATUS_SUCCESS))
                {
                    Delivery delivery = deliveryList.get(0);
                    BigDecimal unfrozenAmt = delivery.getProductFace().subtract(
                        delivery.getSuccessFee()).multiply(order.getProductSaleDiscount());
                    BigDecimal wait_successAmt = delivery.getProductFace().subtract(
                        delivery.getSuccessFee()).multiply(delivery.getCostDiscount());
                    BigDecimal profit_Amt = delivery.getProductFace().subtract(
                        delivery.getSuccessFee()).subtract(wait_successAmt);
                    airtimeAccountingTransaction.orderSuccessTransfer(delivery.getOrderNo(),
                        spDebitAccount.getAccountId(),
                        spDebitAccount.getAccountType(), unfrozenAmt,
                        upMerchantAccount.getAccountId(),
                        upMerchantAccount.getAccountType(), wait_successAmt,
                        spProfitAccount.getAccountId(),
                        spProfitAccount.getAccountType(), profit_Amt,
                        "人工强制部分成功交易，订单号:[" + order.getOrderNo() + "]");
                }
            }
            else
            {
                // 如果是部分成功状态，但是没有发货记录。那肯定有异常
            }
        }
        logger.debug("end OrderAction successPartSuccessOrder!");
    }
    
    @Override
    public boolean closeOrderNoUpr(String operatorName,Long orderNo, String userCode)
    {
        logger.debug("begin OrderAction closeOrder!");
        String reason = StringUtil.initString();
        OrderApplyOperateHistory orderApplyOperateHistory = new OrderApplyOperateHistory();
        orderApplyOperateHistory.setCreateDate(new Date());
        orderApplyOperateHistory.setOperatorName(operatorName);
        orderApplyOperateHistory.setOrderNo(orderNo);
        
        
        Order order = orderManagement.findOne(orderNo);
        try
        {
            Integer originalManualFlag = order.getManualFlag();
            if(originalManualFlag.compareTo(Constant.OrderManualFlag.ORDER_MANUAL_FLAG_NO_NEED) == 0 || originalManualFlag.compareTo(Constant.OrderManualFlag.ORDER_MANUAL_FLAG_NO_CHECKSUPPLY) == 0){
                orderManagement.updateManualFlag(orderNo, Constant.OrderManualFlag.ORDER_MANUAL_FLAG_ING);
            }
            //排除重复处理
            orderManagement.updateManualFlag(orderNo, Constant.OrderManualFlag.ORDER_MANUAL_FLAG_ING, Constant.OrderManualFlag.ORDER_MANUAL_FLAG_DONE);
            
            //1.修改绑定次数 2.判断人工审核原因
            if(order.getBindTimes().compareTo(order.getLimitBindTimes()) >= 0){
                //超绑
                reason = Constant.AuditType.OUT_BIND_PRE_ORDER;
                order.setLimitBindTimes(order.getLimitBindTimes() + 1);
            }else{
                if(order.getPreSuccessStatus() == Constant.OrderStatus.PRE_SUCCESS_STATUS_NO_NEED)
                {
                    //普通订单超时
                    reason = Constant.AuditType.TIME_OUT_ORDER;
                }
                else
                {
                    //预成功订单超时
                    reason = Constant.AuditType.TIME_OUT_PRE_ORDER;
                }
                order.setBindTimes(order.getBindTimes() + 1);
            }
            order.setCloseReason(reason);
            order.setErrorCode(Constant.OrderStatus.ORDER_MANUAL_FAIL);
            order = orderManagement.save(order);
            //设定状态过滤已绑定的供货商方法
            int manualFlag=order.getManualFlag();
            order.setManualFlag(Constant.OrderManualFlag.ORDER_MANUAL_FLAG_NO_CHECKSUPPLY);
            
            SupplyProductRelation upr=supplyProductTransaction.getSupplyProductRelation(order);
            //状态还原
            order.setManualFlag(manualFlag);
            if(BeanUtils.isNull(upr))
            {
                orderManagement.updateManualFlag(orderNo, Constant.OrderManualFlag.ORDER_MANUAL_FLAG_ING);
                return false;
            }
            //2.修改delivery->fail order->wait
            ActionContextUtil.init();
            ActionContextUtil.setActionContext(ActionMapKey.SUPPLY_PRODUCT_RELATION, upr);
            ActionContextUtil.setActionContext(ActionMapKey.ORDER, order);
            ActionContextUtil.setActionContext(ActionMapKey.USER_CODE, userCode);
            manualCloserOrderProcess.execute();
            
            Delivery delivery = (Delivery)TransactionContextUtil.getTransactionContextParam(TransactionMapKey.DELIVERY);
            
            String businessType = StringUtil.initString();
            if(NumberUtil.isFixedPhone(delivery.getUserCode()))
            {
                businessType = Constant.BusinessType.BUSINESS_TYPE_FIXED;
            }
            else
            {
                businessType = Constant.BusinessType.BUSINESS_TYPE_HF;
            }
            
            HopsRequestEvent hre = new HopsRequestEvent(AgentOrderBinder.class);
            hre = new SendOrderEvent(AgentOrderBinder.class, delivery.getDeliveryId(), Integer.valueOf(businessType), upr);
            publisher.publishRequestEvent(hre);
            
            if(originalManualFlag.compareTo(Constant.OrderManualFlag.ORDER_MANUAL_FLAG_NO_NEED) == 0)
            {
                orderManagement.updateManualFlag(orderNo, Constant.OrderManualFlag.ORDER_MANUAL_FLAG_NO_NEED);
            }
            else if (originalManualFlag.compareTo(Constant.OrderManualFlag.ORDER_MANUAL_FLAG_ING) == 0 || originalManualFlag.compareTo(Constant.OrderManualFlag.ORDER_MANUAL_FLAG_NO_CHECKSUPPLY) == 0)
            {
                orderManagement.updateManualFlag(orderNo, Constant.OrderManualFlag.ORDER_MANUAL_FLAG_ING);
            }
            
            orderApplyOperateHistory.setAction(reason);
            orderApplyOperateHistoryService.save(orderApplyOperateHistory);
            
            logger.debug("end OrderAction closeOrder!");
            return true;
        }
        catch (ApplicationException e)
        {
            if(!e.getCode().equalsIgnoreCase("transaction002033"))
            {
                orderManagement.updateManualFlag(orderNo, Constant.OrderManualFlag.ORDER_MANUAL_FLAG_ING);
            }
            return false;
        }
        catch (Exception e)
        {
            orderManagement.updateManualFlag(orderNo, Constant.OrderManualFlag.ORDER_MANUAL_FLAG_ING);
            return false;
        }
    }
    
    @Override
    public void batchCheckOrders(List<Long> orderNos, Long supplyMerchantId, String operatorName)
    {
        for (int i = 0; i < orderNos.size(); i++ )
        {
            Long orderNo = orderNos.get(i); 
            Order order = orderManagement.findOne(orderNo);
            try
            {
                List<Delivery> deliverys = deliveryManagement.findUnfinishedDeliveryList(order.getOrderNo());
                if(deliverys.size() <= 0){
                    if(BeanUtils.isNotNull(supplyMerchantId))
                    {
                        //订单按照指定供货商绑定
                        NumSection numSection = checkNumSectionService.checkNum(order.getUserCode());
                        
                        String businessType = StringUtil.initString();
                        if(NumberUtil.isFixedPhone(order.getUserCode()))
                        {
                            businessType = Constant.BusinessType.BUSINESS_TYPE_FIXED;
                        }
                        else
                        {
                            businessType = Constant.BusinessType.BUSINESS_TYPE_HF;
                        }
                        
                        List<AirtimeProduct> apList = productService.getProductTree(
                            numSection.getProvince().getProvinceId(), order.getOrderFee().toString(),
                            numSection.getCarrierInfo().getCarrierNo(), numSection.getCity().getCityId(),
                            Integer.valueOf(businessType));
        
                        List<SupplyProductRelation> supplyProducts = supplyProductRelationService.querySupplyProductByProductId(apList, supplyMerchantId);
                        SupplyProductRelation upr = scoreFilterAction.filter(supplyProducts, order);
                        if(BeanUtils.isNotNull(upr)){
                            closeOrder(operatorName, orderNo, upr, order.getUserCode(), null);
                        }
                    }
                    else
                    {
                        //订单按照系统逻辑绑定
                        closeOrderNoUpr(operatorName, orderNo, order.getUserCode());
                    }
                }
            }
            catch (Exception e)
            {
                logger.error("batchCheckOrders is fail! orderNo["+orderNo+"] cause by "+ExceptionUtil.getStackTraceAsString(e));
            }
        }
    }

    @Override
    public List<Long> batchUpdateOrderManualFlag(String orderNos, Integer orderManualFlag)
    {
        String[] orders = orderNos.split(Constant.StringSplitUtil.DECODE);
        List<Long> result = new ArrayList<Long>();
        for (int i = 0; i < orders.length; i++ )
        {
            try
            {
                orderManagement.updateManualFlag(Long.valueOf(orders[i]), orderManualFlag);
                result.add(Long.valueOf(orders[i]));
            }
            catch (Exception e)
            {
                logger.error("batchUpdateOrderManualFlag is fail! orderNo"+orders[i]);
            }
        }
        return result;
    }
    
    @Override
    public void reOpenOrder(Long orderNo, Long userCode, SupplyProductRelation upr, String operatorName, String type){
        Order order = orderManagement.findOne(orderNo);
        try
        {
            airtimeAccountingTransaction.refundDeliveryTransfer(orderNo);
            order.setOrderSuccessFee(new BigDecimal(0));
            orderManagement.save(order);
            List<Delivery> deliverys = deliveryManagement.findUnfinishedDeliveryList(order.getOrderNo());
            if(deliverys.size() <= 0){
                if(BeanUtils.isNotNull(upr))
                {
                    closeOrder(operatorName, orderNo, upr, userCode.toString(), Constant.AuditType.RE_OPEN_ORDER);
                }
                else
                {
                    //订单按照系统逻辑绑定
                    closeOrderNoUpr(operatorName, orderNo, userCode.toString());
                }
            }
        }
        catch (Exception e)
        {
            logger.error("reOpenOrder is fail! orderNo["+orderNo+"] cause by "+ExceptionUtil.getStackTraceAsString(e));
        }
    }
}
