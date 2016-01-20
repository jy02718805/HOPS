/*
 * 文件名：AgentPrepareOrderCheckProcessImpl.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：yuanbiao
 * 修改时间：2015年4月13日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.transaction.service.process.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.transaction.TransactionContextUtil;
import com.yuecheng.hops.transaction.service.action.AbstractEventAction;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.process.AgentPrepareOrderCheckProcess;

@Service("agentPrepareOrderCheckProcess")
public class AgentPrepareOrderCheckProcessImpl implements
		AgentPrepareOrderCheckProcess {

	@Autowired
	@Qualifier("checkProductForPrepareOrderHandler")
	private AbstractEventAction checkProductForPrepareOrderHandler;

	@Autowired
	@Qualifier("checkOrderByMerchantStatusHandler")
	private AbstractEventAction checkOrderByMerchantStatusHandler;

	@Autowired
	@Qualifier("checkOrderIsExistsHandler")
	private AbstractEventAction checkOrderIsExistsHandler;

	@Autowired
	@Qualifier("checkOrderByNumSectionHandler")
	private AbstractEventAction checkOrderByNumSectionHandler;

	@Autowired
	@Qualifier("checkProductIsExistsForPrepare")
	private AbstractEventAction checkProductIsExistsForPrepare;

	@Autowired
	@Qualifier("checkOrderByMerchantAccountHandler")
	private AbstractEventAction checkOrderByMerchantAccountHandler;

	@Override
	public void execute() {
		checkProductForPrepareOrderHandler.handleRequest();
		checkOrderByMerchantStatusHandler.handleRequest();
		checkOrderByNumSectionHandler.handleRequest();
		checkProductIsExistsForPrepare.handleRequest();
		checkOrderByMerchantAccountHandler.handleRequest();
		TransactionContextUtil.copyProperties(ActionContextUtil
				.getActionContextLocal());
	}

}
