package com.yuecheng.hops.transaction.service.builder;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.event.HopsPublisher;
import com.yuecheng.hops.common.event.HopsRequestEvent;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.injection.service.MerchantResponseService;
import com.yuecheng.hops.transaction.DefaultTransactionResponseImpl;
import com.yuecheng.hops.transaction.TransactionContextUtil;
import com.yuecheng.hops.transaction.TransactionMapKey;
import com.yuecheng.hops.transaction.TransactionRequest;
import com.yuecheng.hops.transaction.TransactionResponse;
import com.yuecheng.hops.transaction.TransactionService;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.execution.order.action.QueryDeliveryAction;
import com.yuecheng.hops.transaction.execution.order.director.NotifyOrderRequestSelector;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;
import com.yuecheng.hops.transaction.service.order.OrderManagement;
import com.yuecheng.hops.transaction.service.order.builder.support.SupplyOrderKeyInitialization;
import com.yuecheng.hops.transaction.service.process.SupplyAmtLowProcess;

@Service("supplyOrderResulter")
public class SupplyOrderResulter implements TransactionService
{
    private static Logger              logger = LoggerFactory.getLogger(SupplyOrderResulter.class);

    @Autowired
    private MerchantResponseService    merchantResponseService;

    @Autowired
    private DeliveryManagement         deliveryManagement;

    @Autowired
    private OrderManagement            orderManagement;

    @Autowired
    private NotifyOrderRequestSelector notifyOrderRequestSelector;
    
    @Autowired
    private SupplyOrderKeyInitialization supplyOrderKeyInitialization;
    
    @Autowired
    private SupplyAmtLowProcess supplyAmtLowProcess;
    
    @Autowired
    private HopsPublisher publisher;

    @Override
    public TransactionResponse doTransaction(TransactionRequest transactionRequest)
    {
        
        TransactionContextUtil.copyPropertiesIfAbsent(transactionRequest);
        TransactionResponse response = new DefaultTransactionResponseImpl();
        Delivery delivery = null;
        logger.debug("供货商订单通知流程：开始");
        try
        {
            Map<String, Object> responseMap = transactionRequest;
            Long merchantId = (Long)TransactionContextUtil.getTransactionContextParam("interfaceMerchantId");
            
            String supplyMerchantOrderNo = (String)TransactionContextUtil.getTransactionContextParam(EntityConstant.MerchantResponse.SUPPLY_MERCHANT_ORDER_NO);
            if(BeanUtils.isNotNull(supplyMerchantOrderNo)){
                delivery = deliveryManagement.findDeliveryBySupplyOrderNo(merchantId, supplyMerchantOrderNo);
            }
            Long deliveryId = Long.valueOf(TransactionContextUtil.getTransactionContextParam(EntityConstant.Delivery.DELIVERY_ID).toString());
            if(BeanUtils.isNotNull(deliveryId)){
                delivery = deliveryManagement.findDeliveryByIdNoTransaction(deliveryId);
            }
            Assert.notNull(delivery," delivery is null!");
            if(BeanUtils.isNotNull(supplyMerchantOrderNo)){
            	if (BeanUtils.isNull(delivery.getSupplyMerchantOrderNo()))
            	{
            		delivery.setSupplyMerchantOrderNo(supplyMerchantOrderNo);
                    deliveryManagement.updateSupplyMerchantOrderNo(supplyMerchantOrderNo, delivery.getDeliveryId());
            	}
            }
            Order order = orderManagement.findOneNoTransactional(delivery.getOrderNo());
            if((Constant.Delivery.DELIVERY_STATUS_FAIL != delivery.getDeliveryStatus() && Constant.Delivery.DELIVERY_STATUS_SUCCESS != delivery.getDeliveryStatus()) && Constant.Delivery.QUERY_FLAG_QUERY_END != delivery.getQueryFlag()){
                delivery.setQueryFlag(Constant.Delivery.QUERY_FLAG_QUERYING);
                responseMap = BeanUtils.isNotNull(responseMap)?responseMap:new HashMap<String, Object>();
                //通过businessType判断查询的接口类型
                String interfaceType = Constant.Interface.INTERFACE_TYPE_SUPPLY_NOTIFY_ORDER;
                if(Constant.BusinessType.BUSINESS_TYPE_FLOW.equals(String.valueOf(order.getBusinessType())))
                {
                    interfaceType = Constant.Interface.INTERFACE_TYPE_SUPPLY_NOTIFY_ORDER_FLOW;
                }
                responseMap.put(TransactionMapKey.INTERFACE_TYPE, interfaceType);
                QueryDeliveryAction action = notifyOrderRequestSelector.select(delivery,responseMap);
                BigDecimal orderSuccessFee = (BigDecimal)ActionContextUtil.getActionContextParam(TransactionMapKey.ORDER_SUCCESS_FEE);
                if(BeanUtils.isNotNull(action)){
                    action.doAction(delivery, orderSuccessFee);
                }
                logger.debug("供货商订单通知流程,通知成功");
            }
            response.setParameter(Constant.TransactionCode.RESULT, Constant.Common.SUCCESS);
            for (Map.Entry<String, Object> entry : TransactionContextUtil.getTransactionContextLocal().entrySet())
            {
            	if(!(entry.getValue() instanceof HopsRequestEvent)){
            		response.setParameter(entry.getKey(), entry.getValue());
            	}
            }
            response.setParameter(TransactionMapKey.INTERFACE_MERCHANT_ID, order.getMerchantId());
        }
        catch (HopsException e)
        {
            delivery = deliveryManagement.findDeliveryById(delivery.getDeliveryId());
//            deliveryManagement.beginQuery(delivery);
            if(e.getCode().equalsIgnoreCase("transaction002045")){
                ActionContextUtil.setActionContext(ActionMapKey.DELIVERY, delivery);
                supplyAmtLowProcess.execute();
            }
            supplyOrderKeyInitialization.deleteSupplyOrderKey(delivery.getMerchantId(), delivery.getDeliveryId());
            response.setParameter(Constant.TransactionCode.RESULT, Constant.Common.FAIL);
            logger.error("供货商订单通知流程,通知失败，Exception:[" + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw e;
        }
        catch (Exception e)
        {
            supplyOrderKeyInitialization.deleteSupplyOrderKey(delivery.getMerchantId(), delivery.getDeliveryId());
        }
        HopsRequestEvent hre = (HopsRequestEvent)TransactionContextUtil.getTransactionContextParam(TransactionMapKey.HOPS_REQUEST_EVENT);
        TransactionContextUtil.clear();
        TransactionContextUtil.setTransactionContext(TransactionMapKey.HOPS_REQUEST_EVENT, hre);
        return response;
    }

}
