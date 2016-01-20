/*
 * 文件名：DeliveryPreparationProcessImpl.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年1月10日
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
import com.yuecheng.hops.transaction.service.process.AgentQueryOrderCheckProcess;

@Service("agentQueryOrderCheckProcess")
public class AgentQueryOrderCheckProcessImpl implements AgentQueryOrderCheckProcess
{
    @Autowired@Qualifier("checkOrderByMerchantStatusHandler")
    private AbstractEventAction checkOrderByMerchantStatusHandler;
    
    @Autowired
    @Qualifier("checkQueryOrderParamsHandler")
    private AbstractEventAction checkQueryOrderParamsHandler;

    @Autowired
    @Qualifier("checkOrderIsNotExistsHandler")
    private AbstractEventAction checkOrderIsNotExistsHandler;
    
    @Override
    public void execute()
    {
        checkQueryOrderParamsHandler.handleRequest();
        checkOrderByMerchantStatusHandler.handleRequest();
        checkOrderIsNotExistsHandler.handleRequest();
        TransactionContextUtil.copyProperties(ActionContextUtil.getActionContextLocal());
    }

}
