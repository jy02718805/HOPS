package com.yuecheng.hops.transaction.execution.order.action;

import com.yuecheng.hops.transaction.basic.entity.Order;

public interface ForceOrderAction
{
	public void doAction(Order order);
}
