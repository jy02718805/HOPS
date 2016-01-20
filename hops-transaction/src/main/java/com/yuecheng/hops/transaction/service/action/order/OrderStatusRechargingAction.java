/*
 * 文件名：CheckOrderAction.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月22日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.transaction.service.action.order;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.event.HopsPublisher;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.service.StatusManagementService;
import com.yuecheng.hops.transaction.service.action.ActionHandler;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.order.OrderManagement;

@Scope("prototype")
@Service("orderStatusRechargingAction")
public class OrderStatusRechargingAction extends ActionHandler
{
    private static Logger           logger = LoggerFactory.getLogger(OrderStatusRechargingAction.class);

    @Autowired
    private HopsPublisher           publisher;
    
    @Autowired
    private OrderManagement orderManagement;

    @Autowired
    @Qualifier("orderStatusManagementServiceImpl")
    private StatusManagementService orderStatusManagementServiceImpl;

    @Override
    @Transactional
    public void handleRequest()
        throws HopsException
    {
        Order order = (Order)ActionContextUtil.getActionContextParam(ActionMapKey.ORDER);
        try
        {
            logger.debug("修改订单状态RECHARGING"+order.getOrderNo());
            
            order = (Order)orderStatusManagementServiceImpl.updateStatus(order.getOrderNo(),
                order.getOrderStatus(), Constant.OrderStatus.RECHARGING);
            order = orderManagement.save(order);
            logger.debug("[下单]成功,启动订单绑定事件!"+order.getOrderNo());
            
            if(BeanUtils.isNotNull(this.successor)){
                this.successor.handleRequest();
            }
        }
        catch (Exception e)
        {
            logger.error("orderStatusRechargingAction happen Exception caused by "+ ExceptionUtil.getStackTraceAsString(e));
            throw new ApplicationException("transaction002013", new String[]{ExceptionUtil.getStackTraceAsString(e)}, e);
        }
    }
}
