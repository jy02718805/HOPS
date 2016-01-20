/*
 * 文件名：CheckOrderAction.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月22日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.transaction.service.action.delivery;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.event.HopsPublisher;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.injection.service.MerchantRequestService;
import com.yuecheng.hops.parameter.entity.ParameterConfiguration;
import com.yuecheng.hops.parameter.service.ParameterConfigurationService;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;
import com.yuecheng.hops.transaction.TransactionMapKey;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.execution.devliery.DeliveryCreatorAction;
import com.yuecheng.hops.transaction.service.action.AbstractEventAction;
import com.yuecheng.hops.transaction.service.action.ActionHandler;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;
import com.yuecheng.hops.transaction.service.delivery.DeliveryService;
import com.yuecheng.hops.transaction.service.order.OrderManagement;

@Scope("prototype")
@Service("createDeliveryAction")
public class CreateDeliveryAction extends ActionHandler
{
    private static Logger          logger = LoggerFactory.getLogger(CreateDeliveryAction.class);

    @Autowired
    private DeliveryCreatorAction  deliveryCreatorAction;

    @Autowired
    private MerchantRequestService merchantRequestService;

    @Autowired
    private DeliveryService        deliveryService;
    
    @Autowired
    private OrderManagement orderManagement;

    @Autowired
    private DeliveryManagement     deliveryManagement;
    
	@Autowired
	private ParameterConfigurationService parameterConfigurationService;

    @Autowired
    private HopsPublisher          publisher;

    @Autowired
    @Qualifier("deliveryStatusSendingAction")
    private AbstractEventAction    deliveryStatusSendingAction;

    @Override
    @Transactional
    public void handleRequest()
        throws HopsException
    {
        Order order = (Order)ActionContextUtil.getActionContextParam(ActionMapKey.ORDER);
        SupplyProductRelation upr = (SupplyProductRelation)ActionContextUtil.getActionContextParam(ActionMapKey.SUPPLY_PRODUCT_RELATION);
        String userCode = (String)ActionContextUtil.getActionContextParam(ActionMapKey.USER_CODE);
        try
        {
            logger.debug("创建发货记录 [开始]");
            // 绑定
            Date preDeliveryTime = deliveryService.calcPreDeliveryTime(order.getOrderNo(), upr.getIdentityId());
            logger.debug("orderNo:["+ String.valueOf(order.getOrderNo()).toString() +"] preDeliveryTime:[" + preDeliveryTime + "]");
            // 如果发货时间未找到，需要将发货记录直接置为失败。
            Date createDeliveryTime = new Date();
            Date now = new Date();
            Date queryTime = merchantRequestService.getNextQueryDate(createDeliveryTime, now, upr.getIdentityId(), Constant.Interface.INTERFACE_TYPE_QUERY_ORDER);
            logger.debug("orderNo:["+ String.valueOf(order.getOrderNo()).toString() +"] queryTime:[" + queryTime + "]");
            Delivery delivery = deliveryCreatorAction.createDelivery(order, upr, preDeliveryTime, queryTime, userCode);
          
            logger.debug("创建发货记录");
            delivery = deliveryManagement.save(delivery);
            
            Assert.notNull(delivery);
          //如果是供货商联通流量，根据其规则组装流水号
        	getUnicomTranId(upr, delivery);
            logger.debug("订单绑定流程:创建发货记录![" + delivery.getDeliveryId() + "]");

            ActionContextUtil.setActionContext(TransactionMapKey.DELIVERY, delivery);
        }
        catch (Exception e)
        {
            logger.error("createDeliveryAction happen Exception caused by "+ ExceptionUtil.getStackTraceAsString(e));
            throw new ApplicationException("transaction002003", new String[]{ExceptionUtil.getStackTraceAsString(e)}, e);
        }
    }

	private void getUnicomTranId(SupplyProductRelation upr, Delivery delivery)
	{
		
		
		ParameterConfiguration hc = parameterConfigurationService
				.getParameterConfigurationByKey(Constant.ParameterConfiguration.UNICOM_FLOW_IDENTITY_ID);
		
		if (null != hc && String.valueOf(upr.getIdentityId()).equals(hc.getConstantValue()))
		{
			logger.debug("湖南联通id===============>" + hc.getConstantValue());
			String startStr = "UNI010731";
			String midStr =String.valueOf(System.currentTimeMillis()/1000);
			String endStr = String.valueOf(delivery.getDeliveryId());
			if(endStr.length()<=8)
			{
				endStr = String.format("%08d",delivery.getDeliveryId());
			}
			else
			{
				endStr = endStr.substring(endStr.length() - 8);
			}
			String supplyMerchantOrderNo = startStr + midStr + endStr;
			logger.debug("湖南联通supplyMerchantOrderNo===============>" + supplyMerchantOrderNo);
			delivery.setSupplyMerchantOrderNo(supplyMerchantOrderNo);
			deliveryManagement.updateSupplyMerchantOrderNo(supplyMerchantOrderNo, delivery.getDeliveryId());
			logger.debug("湖南联通提交tranid成功===============>" + supplyMerchantOrderNo);
		}
	}
	
}
