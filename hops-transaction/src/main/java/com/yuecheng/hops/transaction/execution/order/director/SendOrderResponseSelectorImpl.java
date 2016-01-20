package com.yuecheng.hops.transaction.execution.order.director;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.PrintUtil;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.injection.entity.MerchantResponse;
import com.yuecheng.hops.injection.service.MerchantResponseService;
import com.yuecheng.hops.transaction.TransactionMapKey;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.execution.order.action.SendOrderAction;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;


@Service("sendOrderResponseSelector")
public class SendOrderResponseSelectorImpl implements SendOrderResponseSelector
{
    private static Logger                logger = LoggerFactory.getLogger(SendOrderResponseSelectorImpl.class);
    
    @Autowired
    @Qualifier("sendOrderCloseAction")
    private SendOrderAction sendOrderCloseAction;

    @Autowired
    @Qualifier("sendOrderFailAction")
    private SendOrderAction sendOrderFailAction;

    @Autowired
    @Qualifier("sendOrderReBindAction")
    private SendOrderAction sendOrderReBindAction;

    @Autowired
    @Qualifier("sendOrderReTryAction")
    private SendOrderAction sendOrderReTryAction;

    @Autowired
    @Qualifier("sendOrderSuccessAction")
    private SendOrderAction sendOrderSuccessAction;

    @Autowired
    @Qualifier("sendOrderUnKnowAction")
    private SendOrderAction sendOrderUnKnowAction;
    
    @Autowired
    private DeliveryManagement deliveryManagement;
    
    @Autowired
    private MerchantResponseService merchantResponseService;

    @Override
    public SendOrderAction select(Delivery delivery,Map<String, Object> map)
    {
        SendOrderAction action = null;
        try
        {
            Assert.notEmpty(map);
            logger.debug("下单返回 responseMap["+PrintUtil.mapToString(map)+"]");
            /* 3.后续处理 */
            String errorCode = (String)map.get(EntityConstant.MerchantResponse.ERROR_CODE);
            String merchantStatus = (String)map.get(EntityConstant.MerchantResponse.MERCHANT_STATUS);
            String deliveryResult = (String)map.get(TransactionMapKey.DELIVERY_RESULT);
            String interfaceType = (String)map.get(TransactionMapKey.INTERFACE_TYPE);
            logger.debug("返回信息！errorCode[" + errorCode + "] merchantStatus[" + merchantStatus + "]");
            MerchantResponse merchantResponse = merchantResponseService.getMerchantResponseByParams(delivery.getMerchantId(), interfaceType, errorCode, merchantStatus);
            if (BeanUtils.isNotNull(merchantResponse))
            {
            	delivery.setQueryMsg(merchantResponse.getMerchantStatusInfo());
            }
            delivery.setDeliveryResult(deliveryResult);
            deliveryManagement.updateParams(BeanUtils.isNotNull(delivery.getSupplyMerchantOrderNo())?delivery.getSupplyMerchantOrderNo():StringUtil.initString(), deliveryResult, delivery.getDeliveryId(),delivery.getQueryMsg());
            ActionContextUtil.setActionContext(TransactionMapKey.DELIVERY, delivery);
            
            
            Integer merchantResponseStatus =  BeanUtils.isNotNull(merchantResponse)?merchantResponse.getStatus():-1;
            
            if (merchantResponseStatus.compareTo(Constant.MerchantResponseStatus.RETRY_STATUS) == 0)
            {
                // 重试
                action = sendOrderReTryAction;
            }
            else if (merchantResponseStatus.compareTo(Constant.MerchantResponseStatus.RE_BIND_STATUS) == 0)
            {
                // 重绑
                action = sendOrderReBindAction;
            }
            else if (merchantResponseStatus.compareTo(Constant.MerchantResponseStatus.CLOSE_STATUS) == 0)
            {
                // 强制关闭
                action = sendOrderCloseAction;
            }
            else if (merchantResponseStatus.compareTo(Constant.MerchantResponseStatus.SUCCESS_STATUS) == 0)
            {
                // 正常成功
                action = sendOrderSuccessAction;
            }
            else if (merchantResponseStatus.compareTo(Constant.MerchantResponseStatus.FAIL_STATUS) == 0)
            {
                // 正常失败
                action = sendOrderFailAction;
            }
            else
            {
                // 未配置
                action = sendOrderUnKnowAction;
            }
        }
        catch (Exception e)
        {
            logger.error("fail to select,caused by["+ExceptionUtil.getStackTraceAsString(e)+"]");
            action = sendOrderUnKnowAction;
        }
        return action;
    }
}
