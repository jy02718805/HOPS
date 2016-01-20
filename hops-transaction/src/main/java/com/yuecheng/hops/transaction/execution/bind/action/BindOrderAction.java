package com.yuecheng.hops.transaction.execution.bind.action;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.service.action.AbstractEventAction;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;


@Service("bindOrderAction")
public class BindOrderAction implements OrderMissBindAction
{
    private static final Logger       logger = LoggerFactory.getLogger(BindOrderAction.class);
    
    @Autowired
    @Qualifier("orderStatusWaitAction")
    private AbstractEventAction orderStatusWaitAction;
    
    
    @Transactional
    public void execute(Order order)
    {
        try
        {
            logger.debug("开始重新绑定[开始]");
            ActionContextUtil.init();
            ActionContextUtil.setActionContext(ActionMapKey.ORDER, order);
            orderStatusWaitAction.handleRequest();
            logger.debug("开始重新绑定[结束]");
        }
        catch (Exception e)
        {
            logger.debug("BindOrderAction 重新绑定发生异常! 异常信息["+ExceptionUtil.getStackTraceAsString(e)+"]");
            throw new ApplicationException("transaction001031", e);
        }
    }
}
