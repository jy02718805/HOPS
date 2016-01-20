package com.yuecheng.hops.transaction.service.action;

import com.yuecheng.hops.common.exception.HopsException;

public abstract class ActionHandler implements AbstractEventAction
{

    /**
     * 持有后继的责任对象
     */
    protected AbstractEventAction successor;

    /**
     * 示意处理请求的方法，虽然这个示意方法是没有传入参数的 但实际是可以传入参数的，根据具体需要来选择是否传递参数
     */
    public abstract void handleRequest()
        throws HopsException;

    /**
     * 取值方法
     */
    public AbstractEventAction getSuccessor()
    {
        return successor;
    }

    /**
     * 赋值方法，设置后继的责任对象
     */
    public void setNextAction(AbstractEventAction successor)
    {
        this.successor = successor;
    }

}