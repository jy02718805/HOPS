package com.yuecheng.hops.transaction.execution.order.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;
import com.yuecheng.hops.transaction.service.order.OrderManagement;
import com.yuecheng.hops.transaction.service.process.OrderReBindProcess;

@Scope("prototype")
@Service("forceOrderFailAction")
public class ForceOrderFailAction implements ForceOrderAction
{
	private static final Logger logger = LoggerFactory.getLogger(ForceOrderFailAction.class);

	@Autowired
	private DeliveryManagement deliveryManagement;

	@Autowired
	private OrderManagement orderManagement;

	@Autowired
	private OrderReBindProcess orderReBindProcess;

	@Override
	public void doAction(Order order)
	{
		try
		{
			order.setManualFlag(Constant.OrderManualFlag.ORDER_MANUAL_FLAG_DONE);
			order.setLimitBindTimes(order.getBindTimes());
			orderManagement.save(order);
			List<Delivery> deliverys = deliveryManagement.findUnfinishedDeliveryList(order.getOrderNo());
			Delivery delivery = null;
			if (deliverys != null && deliverys.size() > 0)
			{
				delivery = deliverys.get(0);
			}
			ActionContextUtil.init();
			ActionContextUtil.setActionContext(ActionMapKey.ERROR_CODE, Constant.OrderStatus.ORDER_MANUAL_FAIL);
			ActionContextUtil.setActionContext(ActionMapKey.ORDER, order);
			ActionContextUtil.setActionContext(ActionMapKey.DELIVERY, delivery);
			ActionContextUtil.setActionContext(ActionMapKey.RESPONSE_STR, "强制审核失败");
			orderReBindProcess.execute();
		}
		catch (Exception e)
		{
			logger.error("queryOrderFailAction happen Exception caused by " + ExceptionUtil.getStackTraceAsString(e));
			throw new ApplicationException("transaction002040", e);
		}
	}
}
