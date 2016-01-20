package com.yuecheng.hops.transaction.execution.order.director;


import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.PrintUtil;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.injection.entity.MerchantResponse;
import com.yuecheng.hops.injection.service.MerchantResponseService;
import com.yuecheng.hops.transaction.TransactionContextUtil;
import com.yuecheng.hops.transaction.TransactionMapKey;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.execution.order.action.QueryDeliveryAction;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;
import com.yuecheng.hops.transaction.service.order.builder.support.SupplyOrderKeyInitialization;


@Service("notifyOrderRequestSelector")
public class NotifyOrderRequestSelectorImpl implements NotifyOrderRequestSelector
{
    private static final Logger logger = LoggerFactory.getLogger(NotifyOrderRequestSelectorImpl.class);
    
    @Autowired
    @Qualifier("queryDeliverySuccessAction")
    private QueryDeliveryAction queryDeliverySuccessAction;

    @Autowired
    @Qualifier("queryDeliveryFailAction")
    private QueryDeliveryAction queryDeliveryFailAction;

    @Autowired
    @Qualifier("queryDeliveryPartSuccessAction")
    private QueryDeliveryAction queryDeliveryPartSuccessAction;

    @Autowired
    @Qualifier("queryDeliveryUnKnowAction")
    private QueryDeliveryAction queryDeliveryUnKnowAction;
    
    @Autowired
    private SupplyOrderKeyInitialization supplyOrderKeyInitialization;
    
    @Autowired
    private MerchantResponseService merchantResponseService;
    
    @Autowired
    private DeliveryManagement deliveryManagement;

    @Override
    public QueryDeliveryAction select(Delivery delivery,Map<String, Object> responseMap)
    {
        QueryDeliveryAction action = null;
        try
        {
            Assert.notEmpty(responseMap);
            logger.debug("SupplyOrderQuerier_查询成功 responseMap["+PrintUtil.mapToString(responseMap)+"]");
            /* 3.后续处理 */
            String errorCode = (String)responseMap.get(EntityConstant.MerchantResponse.ERROR_CODE);
            String merchantStatus = (String)responseMap.get(EntityConstant.MerchantResponse.MERCHANT_STATUS);
            String deliveryResult = (String)responseMap.get(EntityConstant.MerchantResponse.DELIVERY_RESULT);
            
            String orderSuccessFeeStr = String.valueOf(responseMap.get(EntityConstant.MerchantResponse.ORDER_SUCCESS_FEE));
            String interfaceType = (String)responseMap.get(TransactionMapKey.INTERFACE_TYPE);
            BigDecimal orderSuccessFee;
            
            try
            {
                orderSuccessFee = StringUtil.isNullOrEmpty(orderSuccessFeeStr)?new BigDecimal(orderSuccessFeeStr):delivery.getProductFace();
            }
            catch (Exception e)
            {
                logger.error("供货商返回金额异常:["+String.valueOf(orderSuccessFeeStr).toString()+"]");
                orderSuccessFee = delivery.getProductFace();
            }
            ActionContextUtil.setActionContext(TransactionMapKey.ORDER_SUCCESS_FEE, orderSuccessFee);
            logger.debug("往供货商查询订单，供货商merchantId["+String.valueOf(delivery.getMerchantId()).toString()+"] errorCode:["+errorCode+"] merchantStatus:["+merchantStatus+"]");
            // 下单返回
            MerchantResponse merchantResponse = merchantResponseService.getMerchantResponseByParams(
                delivery.getMerchantId(), interfaceType, errorCode,
                merchantStatus);
            Integer merchantResponseStatus = null;
            if(BeanUtils.isNull(merchantResponse)){
                merchantResponseStatus = -1;
                deliveryManagement.updateQueryMsg(delivery.getDeliveryId(),EntityConstant.MerchantResponse.QUERY_MSG);
            }else{
                merchantResponseStatus = merchantResponse.getStatus();
                delivery.setQueryMsg(merchantResponse.getMerchantStatusInfo());
                deliveryManagement.updateQueryMsg(delivery.getDeliveryId(),delivery.getQueryMsg());
            }
            TransactionContextUtil.setTransactionContext(TransactionMapKey.QUERY_MSG, BeanUtils.isNotNull(merchantResponse)?merchantResponse.getMerchantStatusInfo():StringUtil.initString());
            TransactionContextUtil.setTransactionContext(TransactionMapKey.DELIVERY_RESULT, deliveryResult);
            TransactionContextUtil.setTransactionContext(TransactionMapKey.MERCHANT_RESPONSE, merchantResponse);
            TransactionContextUtil.setTransactionContext(TransactionMapKey.ORDER_SUCCESS_FEE, orderSuccessFee);
            
            if (merchantResponseStatus.compareTo(Constant.OrderStatus.SUCCESS) == 0)
            {
                supplyOrderKeyInitialization.saveSupplyOrderKey(delivery.getMerchantId(), delivery.getDeliveryId());
                // 成功
                action = queryDeliverySuccessAction;
            }
            else if (merchantResponseStatus.compareTo(Constant.OrderStatus.FAILURE_ALL) == 0)
            {
                supplyOrderKeyInitialization.saveSupplyOrderKey(delivery.getMerchantId(), delivery.getDeliveryId());
                // 失败
                action = queryDeliveryFailAction;
            }
            else if (merchantResponseStatus.compareTo(Constant.OrderStatus.SUCCESS_PART) == 0)
            {
                // 部分成功
                action = queryDeliveryPartSuccessAction;
            }
            else
            {
                // 其他
                action = queryDeliveryUnKnowAction;
            }
        }
        catch (ApplicationException e)
        {
            action = null;
        }
        catch (Exception e)
        {
            logger.error("fail to select,caused by:["+ExceptionUtil.getStackTraceAsString(e)+"]");
            action = queryDeliveryUnKnowAction;
        }
        return action;
    }
}
