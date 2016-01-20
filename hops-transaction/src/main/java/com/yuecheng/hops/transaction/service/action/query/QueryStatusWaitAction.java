/*
 * 文件名：CheckOrderAction.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月22日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.transaction.service.action.query;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.injection.service.MerchantRequestService;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.service.StatusManagementService;
import com.yuecheng.hops.transaction.service.action.ActionHandler;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;
import com.yuecheng.hops.transaction.service.order.OrderManagement;

@Scope("prototype")
@Service("queryStatusWaitAction")
public class QueryStatusWaitAction extends ActionHandler
{
    private static Logger           logger = LoggerFactory.getLogger(QueryStatusWaitAction.class);

    @Autowired
    @Qualifier("deliveryQueryStatusManagementServiceImpl")
    private StatusManagementService deliveryQueryStatusManagementServiceImpl;
    
    @Autowired
    private MerchantRequestService merchantRequestService;
    
    @Autowired
    private DeliveryManagement deliveryManagement;
    
    @Autowired
    private OrderManagement orderManagement;

    @Override
    public void handleRequest()
        throws HopsException
    {
        Delivery delivery = (Delivery)ActionContextUtil.getActionContextParam(ActionMapKey.DELIVERY);
        String deliveryResult = (String)ActionContextUtil.getActionContextParam(ActionMapKey.DELIVERY_RESULT);
        logger.debug("QueryStatusWaitAction_delivery["+delivery.toString()+"]");
        try
        {
            Order order = orderManagement.findOne(delivery.getOrderNo());
            logger.debug("修改发货记录查询标示(NO_NEED->QUERY_FLAG_WAIT_QUERY) [开始]"+delivery.getDeliveryId());
            if(Constant.OrderStatus.SUCCESS != order.getOrderStatus() && Constant.OrderStatus.FAILURE_ALL != order.getOrderStatus()){
                Integer originalStatus = delivery.getQueryFlag();
                Date createDeliveryTime = delivery.getDeliveryStartTime();
                Date now = new Date();
                String interface_type = Constant.Interface.INTERFACE_TYPE_QUERY_ORDER;
                if(order.getBusinessType() == Long.valueOf(Constant.BusinessType.BUSINESS_TYPE_FLOW))
                {
                    interface_type = Constant.Interface.INTERFACE_TYPE_SUPPLY_QUERY_ORDER_FLOW;
                }
                Date queryTime = merchantRequestService.getNextQueryDate(createDeliveryTime, now,
                    delivery.getMerchantId(), interface_type);
                
                
                delivery = (Delivery)deliveryQueryStatusManagementServiceImpl.updateStatus(
                    delivery.getDeliveryId(), delivery.getQueryFlag(),
                    Constant.Delivery.QUERY_FLAG_WAIT_QUERY);
                
                if(StringUtil.isNotBlank(deliveryResult)){
                    delivery.setDeliveryResult(deliveryResult);
                }
                
                deliveryManagement.updateQueryFlag(delivery, originalStatus, queryTime);
                delivery.setNextQueryTime(queryTime);
            }
            logger.debug("修改发货记录查询标示(NO_NEED->QUERY_FLAG_WAIT_QUERY) [结束]"+delivery.getDeliveryId());
            ActionContextUtil.setActionContext(ActionMapKey.DELIVERY,delivery);
        }
        catch (Exception e)
        {
            logger.error("queryStatusWaitAction happen Exception caused by "+ ExceptionUtil.getStackTraceAsString(e));
            throw new ApplicationException("transaction002018", new String[]{ExceptionUtil.getStackTraceAsString(e)}, e);
        }
    }
}
