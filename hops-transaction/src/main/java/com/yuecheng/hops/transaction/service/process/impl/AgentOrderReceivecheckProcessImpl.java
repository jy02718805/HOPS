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
import com.yuecheng.hops.transaction.service.check.CheckOrderByBlacklistHander;
import com.yuecheng.hops.transaction.service.process.AgentOrderReceivecheckProcess;

@Service("agentOrderReceivecheckProcess")
public class AgentOrderReceivecheckProcessImpl implements AgentOrderReceivecheckProcess
{
    @Autowired@Qualifier("checkOrderParamsHandler")
    private AbstractEventAction checkOrderParamsHandler;
    
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
    @Qualifier("checkOrderByProductIsExistsHandler")
    private AbstractEventAction checkOrderByProductIsExistsHandler;
    
    @Autowired
    @Qualifier("checkOrderByBlacklistHander")
    private CheckOrderByBlacklistHander checkOrderByBlacklistHander;
    
    
    @Autowired
    @Qualifier("checkOrderByMerchantAccountHandler")
    private AbstractEventAction checkOrderByMerchantAccountHandler;
    
    @Override
    public void execute()
    {
        checkOrderParamsHandler.handleRequest();
        checkOrderByMerchantStatusHandler.handleRequest();
        checkOrderIsExistsHandler.handleRequest();
        checkOrderByNumSectionHandler.handleRequest();
        checkOrderByBlacklistHander.handleRequest();
        checkOrderByProductIsExistsHandler.handleRequest();
        checkOrderByMerchantAccountHandler.handleRequest();
        TransactionContextUtil.copyProperties(ActionContextUtil.getActionContextLocal());
    }

}
