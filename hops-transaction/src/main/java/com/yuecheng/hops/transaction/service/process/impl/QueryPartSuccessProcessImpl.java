/*
 * 文件名：DeliveryCreator.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator 修改时间：2015年1月9日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.transaction.service.process.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.transaction.TransactionContextUtil;
import com.yuecheng.hops.transaction.service.action.AbstractEventAction;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.process.QueryPartSuccessProcess;

@Service("queryPartSuccessProcess")
public class QueryPartSuccessProcessImpl implements QueryPartSuccessProcess
{
    @Autowired
    @Qualifier("deliveryStatusSuccessAction") 
    private AbstractEventAction deliveryStatusSuccessAction;
    
    @Autowired
    @Qualifier("orderStatusPartSuccessAction") 
    private AbstractEventAction orderStatusPartSuccessAction;
    
    @Override
    @Transactional
    public void execute()
    {
//        deliveryStatusSuccessAction.handleRequest();
        orderStatusPartSuccessAction.handleRequest();
        TransactionContextUtil.copyProperties(ActionContextUtil.getActionContextLocal());
    }

}