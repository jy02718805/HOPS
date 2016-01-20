package com.yuecheng.hops.transaction.service.builder;


import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.account.entity.role.IdentityAccountRole;
import com.yuecheng.hops.account.service.IdentityAccountRoleService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.event.HopsPublisher;
import com.yuecheng.hops.common.event.HopsRequestEvent;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.PrintUtil;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.injection.service.MerchantResponseService;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;
import com.yuecheng.hops.transaction.DefaultTransactionResponseImpl;
import com.yuecheng.hops.transaction.TransactionContextUtil;
import com.yuecheng.hops.transaction.TransactionMapKey;
import com.yuecheng.hops.transaction.TransactionRequest;
import com.yuecheng.hops.transaction.TransactionResponse;
import com.yuecheng.hops.transaction.TransactionService;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.event.AsynSendDeliveryEvent;
import com.yuecheng.hops.transaction.execution.account.AirtimeAccountingTransaction;
import com.yuecheng.hops.transaction.service.action.AbstractEventAction;
import com.yuecheng.hops.transaction.service.action.context.ActionContext;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.action.context.DefaultActionContextImpl;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;
import com.yuecheng.hops.transaction.service.order.OrderManagement;
import com.yuecheng.hops.transaction.service.process.DeliveryPreparationProcess;
import com.yuecheng.hops.transaction.service.process.OrderReBindProcess;

@Service("supplyOrderSender")
public class SupplyOrderSender implements TransactionService
{
    private static Logger                logger = LoggerFactory.getLogger(SupplyOrderSender.class);

    @Autowired
    private DeliveryManagement           deliveryManagement;

    @Autowired
    private OrderManagement              orderManagement;

    @Autowired
    private AirtimeAccountingTransaction airtimeAccountingTransaction;

    @Autowired
    private IdentityAccountRoleService       identityAccountRoleService;

    @Autowired
    private MerchantResponseService      merchantResponseService;

    @Autowired
    private DeliveryPreparationProcess deliveryPreparationProcess;
    
    @Autowired
    private OrderReBindProcess orderReBindProcess;
    
    @Autowired
    private HopsPublisher publisher;

    @Autowired
    @Qualifier("deliveryStatusWaitAction")
    private AbstractEventAction deliveryStatusWaitAction;
    
    @Override
    public TransactionResponse doTransaction(TransactionRequest transactionRequest)
    {
        TransactionContextUtil.copyPropertiesIfAbsent(transactionRequest);
        TransactionResponse response = new DefaultTransactionResponseImpl();
        
        ActionContext context = new DefaultActionContextImpl();
        context.setContext(TransactionContextUtil.getTransactionContextLocal());
        final Long deliveryId = (Long)TransactionContextUtil.getTransactionContextParam(TransactionMapKey.DELIVERY_ID);
        final String businessType = String.valueOf(TransactionContextUtil.getTransactionContextParam(TransactionMapKey.BUSINESS_TYPE));
        logger.debug("BusinessType："+businessType);
        final SupplyProductRelation supplyProductRelation = (SupplyProductRelation)TransactionContextUtil.getTransactionContextParam(TransactionMapKey.SUPPLY_PRODUCT_RELATION);
        logger.debug("供货产品信息："+supplyProductRelation.toString());
        if(StringUtils.isNotBlank(businessType))
        {
                logger.debug("业务编号(businessType):"+businessType+",供货商产品编号(SupplyProdId):"+supplyProductRelation.getSupplyProdId());
                //1.冻结、变动deliveryStatus  -> sended
                logger.debug("订单发送流程：进入单笔发货流程！");
                ActionContextUtil.setActionContext(ActionMapKey.DELIVERY_ID, deliveryId);
                try{
                    deliveryPreparationProcess.execute();
                }
                catch (HopsException e)
                {
                    logger.error("执行发货前准备流程失败!(冻结金额、修改发货状态sended)！"+ExceptionUtil.getStackTraceAsString(e));
                    Delivery delivery = (Delivery)TransactionContextUtil.getTransactionContextParam(TransactionMapKey.DELIVERY);
                    if(Constant.Delivery.DELIVERY_STATUS_SENDING == delivery.getDeliveryStatus()){
                        ActionContextUtil.init();
                        ActionContextUtil.setActionContext(ActionMapKey.DELIVERY, delivery);
                        deliveryStatusWaitAction.handleRequest();
                    }
                }
                
                //2.发起gateway服务，并继续后续处理
                BigDecimal frozenAmt = (BigDecimal)TransactionContextUtil.getTransactionContextParam(TransactionMapKey.FROZEN_AMT);
                IdentityAccountRole payee = (IdentityAccountRole)TransactionContextUtil.getTransactionContextParam(TransactionMapKey.SYSTEM_DEBIT_ACCOUNT);
                Delivery delivery = (Delivery)TransactionContextUtil.getTransactionContextParam(TransactionMapKey.DELIVERY);
                Order order = (Order)TransactionContextUtil.getTransactionContextParam(TransactionMapKey.ORDER);
                String userCode = delivery.getUserCode();
                Map<String, Object> fileds = BeanUtils.transBean2Map(order);
                fileds.put(EntityConstant.Delivery.USER_CODE, userCode);
                fileds.put(TransactionMapKey.DELIVERY_ID, deliveryId);
                fileds.put(TransactionMapKey.INTERFACE_MERCHANT_ID, delivery.getMerchantId());
                
                
                if(Constant.BusinessType.BUSINESS_TYPE_HF.equals(businessType))
                {
                    fileds.put(TransactionMapKey.PRODUCT_TYPE, 1);
                    fileds.put(TransactionMapKey.CP, order.getExt1().equalsIgnoreCase(Constant.MobileType.YD)?"01":order.getExt1().equalsIgnoreCase(Constant.MobileType.DX)?"02":"03");
                    fileds.put(TransactionMapKey.CITY_CODE, order.getExt3());
                    fileds.put(TransactionMapKey.INTERFACE_TYPE, Constant.Interface.INTERFACE_TYPE_SEND_ORDER);
                }else if(Constant.BusinessType.BUSINESS_TYPE_FLOW.equals(businessType)){

                	logger.debug("流量供货商外部编码=================>"+supplyProductRelation.getSupplyProdId());
                    fileds.put(TransactionMapKey.INTERFACE_TYPE, Constant.Interface.INTERFACE_TYPE_SUPPLY_SEND_ORDER_FLOW);
                    fileds.put(TransactionMapKey.SUPPLY_PRODUCT_ID, supplyProductRelation.getSupplyProdId());      
                    fileds.put(TransactionMapKey.COST_FEE, delivery.costFee);
                    if(StringUtil.isNotBlank(delivery.getSupplyMerchantOrderNo()))
                    {
                    	fileds.put(EntityConstant.MerchantResponse.SUPPLY_MERCHANT_ORDER_NO, delivery.getSupplyMerchantOrderNo());  
                    }
                }else if (Constant.BusinessType.BUSINESS_TYPE_FIXED.equals(businessType)){
                    fileds.put(TransactionMapKey.PRODUCT_TYPE, 3);
                    fileds.put(TransactionMapKey.CP, order.getExt1().equalsIgnoreCase(Constant.MobileType.YD)?"01":order.getExt1().equalsIgnoreCase(Constant.MobileType.DX)?"02":"03");
                    fileds.put(TransactionMapKey.CITY_CODE, order.getExt3());
                    fileds.put(TransactionMapKey.INTERFACE_TYPE, Constant.Interface.INTERFACE_TYPE_SEND_ORDER);
                }
                
                logger.debug("begin to asynSendDeliveryEvent  delivery"+String.valueOf(delivery).toString()+" fileds:"+ PrintUtil.mapToString(fileds));
                HopsRequestEvent hre = new HopsRequestEvent(SupplyOrderSender.class);
                hre = new AsynSendDeliveryEvent(SupplyOrderSender.class, fileds, frozenAmt, payee, delivery);
                publisher.publishRequestEvent(hre);
        }
        return response;
    }
}
