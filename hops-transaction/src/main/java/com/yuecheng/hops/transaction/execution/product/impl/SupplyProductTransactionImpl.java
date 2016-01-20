package com.yuecheng.hops.transaction.execution.product.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.parameter.entity.ParameterConfiguration;
import com.yuecheng.hops.parameter.service.ParameterConfigurationService;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;
import com.yuecheng.hops.transaction.TransactionContextUtil;
import com.yuecheng.hops.transaction.TransactionMapKey;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.execution.product.SupplyProductTransaction;
import com.yuecheng.hops.transaction.execution.product.action.AssignExcludeFilterAction;
import com.yuecheng.hops.transaction.execution.product.action.DeliveryFilterAction;
import com.yuecheng.hops.transaction.execution.product.action.MerchantIsConfInjectionFilterAction;
import com.yuecheng.hops.transaction.execution.product.action.MerchantStatusFilterAction;
import com.yuecheng.hops.transaction.execution.product.action.ScoreFilterAction;
import com.yuecheng.hops.transaction.execution.product.action.SupplyDupNumFilterAction;
import com.yuecheng.hops.transaction.execution.product.action.SupplyProductRelationMatchAction;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;
import com.yuecheng.hops.transaction.service.order.OrderManagement;


@Service("supplyProductTransaction")
public class SupplyProductTransactionImpl implements SupplyProductTransaction
{
    private static final Logger logger = LoggerFactory.getLogger(SupplyProductTransactionImpl.class);

    @Autowired
    private ParameterConfigurationService parameterConfigurationService;

    @Autowired
    private DeliveryManagement deliveryService;

    @Autowired
    private SupplyProductRelationMatchAction supplyProductRelationMatchAction;

    @Autowired
    private AssignExcludeFilterAction assignExcludeFilterAction;

    @Autowired
    private DeliveryFilterAction deliveryFilterAction;

    @Autowired
    private ScoreFilterAction scoreFilterAction;

    @Autowired
    private MerchantStatusFilterAction merchantStatusFilterAction;

    @Autowired
    private MerchantIsConfInjectionFilterAction merchantIsConfInjectionFilterAction;
    
    @Autowired
    private SupplyDupNumFilterAction supplyDupNumFilterAction;

    @Autowired
    private OrderManagement orderManagement;
    
    @Override
    public SupplyProductRelation getSupplyProductRelation(Order order)
    {
        try
        {
        	 // 如果超过次数限制，返回NULL
            if (order.getBindTimes().compareTo(order.getLimitBindTimes()) >= 0)
            {
                logger.error("订单绑定流程:根据订单信息找寻供货商！绑定次数超过限制数" + order.getLimitBindTimes()
                             + ",返回空(null)！");
                return null;
            }
            // 添加绑定次数
            order.setBindTimes(order.getBindTimes() + 1);
            order = orderManagement.save(order);
            
            TransactionContextUtil.setTransactionContext(TransactionMapKey.ORDER, order);
            logger.debug("订单绑定流程:根据订单信息找寻供货商！[开始] order:[" + order.toString() + "]");
            List<SupplyProductRelation> uprs = new ArrayList<SupplyProductRelation>();
            ParameterConfiguration hc = parameterConfigurationService.getParameterConfigurationByKey(Constant.ParameterConfiguration.RANDOM_BINDING_TIMES_CONSTANT);
            int randomBindTimes = 0;
            if(BeanUtils.isNotNull(hc))
            {
                randomBindTimes = Integer.parseInt(hc.getConstantValue());
            }
            else
            {
                randomBindTimes = 10;
            }

            int randomTimes = 0;
            boolean isNotExit = true;
            while (isNotExit)
            {
                if (randomTimes >= randomBindTimes)
                {
                    break;
                }
                uprs = supplyProductRelationMatchAction.execute(order);
                if (CollectionUtils.isEmpty(uprs))
                {
                    isNotExit = true;
                }
                else
                {
                    isNotExit = false;
                }
                randomTimes++ ;
            }
            if (uprs.size() > 0)
            {
                // 过滤指定排除的商户
                uprs = assignExcludeFilterAction.filter(order, uprs);
                if(order.getManualFlag().compareTo(Constant.OrderManualFlag.ORDER_MANUAL_FLAG_NO_CHECKSUPPLY)!=0)
                {
                    // 如果之前有绑定记录，需要将已经绑定过的上游排除
                    uprs = deliveryFilterAction.filter(order, uprs);
                }
                // 过滤商户状态
                uprs = merchantStatusFilterAction.filter(uprs);
                // 过滤没有接口配置的商户
                if(Constant.BusinessType.BUSINESS_TYPE_FLOW.equals(order.getBusinessType().toString()))
                {
                    uprs = merchantIsConfInjectionFilterAction.filterFlow(uprs);
                }else{
                    uprs = merchantIsConfInjectionFilterAction.filter(uprs);
                }
                uprs = supplyDupNumFilterAction.filter(uprs, order.getUserCode());
                // 排序，获取得分最高的。
                SupplyProductRelation upr = scoreFilterAction.filter(uprs, order);
                return upr;
            }
            else
            {
                return null;
            }
        }
        catch (Exception e)
        {
            logger.error("绑定供货商发生异常，信息[" + ExceptionUtil.getStackTraceAsString(e) + "]");
            return null;
            
        }
    }

}
