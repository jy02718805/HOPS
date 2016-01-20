/*
 * 文件名：OrderStatusWaitAction.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月22日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.transaction.service.action.order;


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
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.DateUtil;
import com.yuecheng.hops.parameter.entity.ParameterConfiguration;
import com.yuecheng.hops.parameter.service.ParameterConfigurationService;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.service.StatusManagementService;
import com.yuecheng.hops.transaction.service.action.ActionHandler;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.order.OrderManagement;

@Scope("prototype")
@Service("orderStatusWaitAction")
public class OrderStatusWaitAction extends ActionHandler
{
    private static Logger           logger = LoggerFactory.getLogger(OrderStatusWaitAction.class);

    @Autowired
    @Qualifier("orderStatusManagementServiceImpl")
    private StatusManagementService orderStatusManagementServiceImpl;
    
    @Autowired
    private OrderManagement orderManagement;

    @Autowired
    private ParameterConfigurationService parameterConfigurationService;
    
    @Override
    @Transactional
    public void handleRequest()
        throws HopsException
    {
        Order order = (Order)ActionContextUtil.getActionContextParam(ActionMapKey.ORDER);
        try
        {
            logger.debug("付款完成，修改订单状态(WAIT_INIT->WAIT_RECHARGE) [开始]"+order.getOrderNo());
            order = (Order)orderStatusManagementServiceImpl.updateStatus(order.getOrderNo(), order.getOrderStatus(), Constant.OrderStatus.WAIT_RECHARGE);
            logger.debug("修改订单状态(WAIT_INIT->WAIT_RECHARGE) [结束]"+order.getOrderNo());
            
            ParameterConfiguration hc =parameterConfigurationService.getParameterConfigurationByKey(Constant.ParameterConfiguration.BIND_INTERVAL_TIME);
            Date preOrderBindTime = DateUtil.addTime(hc.getConstantUnitValue(), Integer.parseInt(hc.getConstantValue()));
            order.setPreOrderBindTime(preOrderBindTime);
            order = orderManagement.save(order);//!!! update
            
            ActionContextUtil.setActionContext(ActionMapKey.ORDER, order);
            if(BeanUtils.isNotNull(this.successor)){
                this.successor.handleRequest();
            }
        }
        catch (Exception e)
        {
            logger.error("orderStatusWaitAction happen Exception caused by "+ ExceptionUtil.getStackTraceAsString(e));
            throw new ApplicationException("transaction002015", new String[]{ExceptionUtil.getStackTraceAsString(e)}, e);//!!!
        }
    }
}
