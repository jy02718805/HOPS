/*
 * 文件名：CheckOrderAction.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月22日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.transaction.service.action.delivery;


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
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.service.StatusManagementService;
import com.yuecheng.hops.transaction.service.action.ActionHandler;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;
import com.yuecheng.hops.transaction.service.order.OrderManagement;

@Scope("prototype")
@Service("deliveryStatusSendAction")
public class DeliveryStatusSendAction extends ActionHandler
{
    private static Logger           logger = LoggerFactory.getLogger(DeliveryStatusSendAction.class);

    @Autowired
    @Qualifier("deliveryStatusManagementServiceImpl")
    private StatusManagementService deliveryStatusManagementServiceImpl;

    @Autowired
    private OrderManagement orderManagement;
    
    @Autowired
    private DeliveryManagement deliveryManagement;

    @Override
    @Transactional
    public void handleRequest()
        throws HopsException
    {
        Delivery delivery = (Delivery)ActionContextUtil.getActionContextParam(ActionMapKey.DELIVERY);
        try
        {
            Integer deliveryStatus = delivery.getDeliveryStatus();
            logger.debug("修改发货状态(DELIVERY_STATUS_SENDING->DELIVERY_STATUS_SENDED) [开始]"+delivery.getDeliveryId());
            delivery = (Delivery)deliveryStatusManagementServiceImpl.updateStatus(delivery.getDeliveryId(),
                delivery.getDeliveryStatus(), Constant.Delivery.DELIVERY_STATUS_SENDED);
            deliveryManagement.updateDeliveryStatus(delivery.getDeliveryStatus(), deliveryStatus, delivery.getDeliveryId());
//            delivery = deliveryManagement.save(delivery);
            logger.debug("修改发货状态(DELIVERY_STATUS_SENDING->DELIVERY_STATUS_SENDED) [结束]"+delivery.getDeliveryId());
            
            ActionContextUtil.setActionContext(ActionMapKey.DELIVERY, delivery);
            if(BeanUtils.isNotNull(this.successor)){
                this.successor.handleRequest();
            }
        }
        catch (Exception e)
        {
            logger.error("deliveryStatusSendAction happen Exception caused by "+ ExceptionUtil.getStackTraceAsString(e));
            throw new ApplicationException("transaction002005", new String[]{ExceptionUtil.getStackTraceAsString(e)}, e);
        }
    }
}
