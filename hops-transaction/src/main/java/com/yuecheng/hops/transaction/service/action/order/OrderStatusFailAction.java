/*
 * 文件名：orderStatusFailAction.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
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

import com.yuecheng.hops.account.service.IdentityAccountRoleService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.injection.service.ErrorCodeService;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.execution.account.AirtimeAccountingTransaction;
import com.yuecheng.hops.transaction.service.StatusManagementService;
import com.yuecheng.hops.transaction.service.action.ActionHandler;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.order.OrderManagement;

@Scope("prototype")
@Service("orderStatusFailAction")
public class OrderStatusFailAction extends ActionHandler
{
    private static Logger                logger = LoggerFactory.getLogger(OrderStatusFailAction.class);

    @Autowired
    @Qualifier("orderStatusManagementServiceImpl")
    private StatusManagementService      orderStatusManagementServiceImpl;

    @Autowired
    private ErrorCodeService             errorCodeService;

    @Autowired
    private OrderManagement              orderManagement;

    @Autowired
    private AirtimeAccountingTransaction airtimeAccountingTransaction;
    
    @Autowired
    private IdentityAccountRoleService identityAccountRoleService;

    @Override
    @Transactional
    public void handleRequest()
        throws HopsException
    {
        Order order = (Order)ActionContextUtil.getActionContextParam(ActionMapKey.ORDER);
        String errorCode = (String)ActionContextUtil.getActionContextParam(ActionMapKey.ERROR_CODE);
        try
        {
            logger.debug("修改订单状态FAIL [开始]"+order.getOrderNo());
            order = (Order)orderStatusManagementServiceImpl.updateStatus(order.getOrderNo(),
                order.getOrderStatus(), Constant.OrderStatus.FAILURE_ALL);
            order.setErrorCode(errorCode);
            order.setCloseReason(errorCodeService.getErrorCode(errorCode));
            if(null == order.getOrderFinishTime())
            {
                order.setOrderFinishTime(new Date());
            }
            orderManagement.save(order);
            logger.debug("修改订单状态FAIL [结束]"+order.getOrderNo());
            
            // 退款
            airtimeAccountingTransaction.refundTransfer(order.getOrderNo(), order.getOrderRequestTime());
            
            ActionContextUtil.setActionContext(ActionMapKey.ORDER, order);
            if(BeanUtils.isNotNull(this.successor)){
                this.successor.handleRequest();
            }
        }
        catch (Exception e)
        {
            logger.error("orderStatusFailAction happen Exception caused by "+ ExceptionUtil.getStackTraceAsString(e));
            throw new ApplicationException("transaction002012", new String[]{ExceptionUtil.getStackTraceAsString(e)}, e);
        }
    }
}
