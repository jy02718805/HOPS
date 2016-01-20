package com.yuecheng.hops.transaction.service.check;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.service.action.ActionHandler;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.order.OrderService;


@Service("checkQueryOrderParamsHandler")
public class CheckQueryOrderParamsHandler extends ActionHandler
{
    private static Logger           logger = LoggerFactory.getLogger(CheckQueryOrderParamsHandler.class);

    @Autowired
    private OrderService     orderService;


    /**
     * 处理方法，调用此方法处理请求
     */
    @Override
    public void handleRequest()
        throws HopsException
    {
        try
        {
            Verify();
            logger.info("检查查询订单必输项！ 通过");
        }
        catch (HopsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            logger.error("检查查询订单必输项，失败！");
            throw new ApplicationException(Constant.ErrorCode.MANUAL);
        }
    }

    public void Verify()
        throws Exception
    {
        Order order = (Order)ActionContextUtil.getActionContextParam(ActionMapKey.ORDER);
        String sign = (String)ActionContextUtil.getActionContextParam(ActionMapKey.SIGN);
        try
        {
            Assert.notNull(order.getMerchantId());
//            Assert.notNull(order.getMerchantOrderNo());//!!!修改校验方式
            Assert.isTrue(StringUtil.isNotBlank(order.getMerchantOrderNo()));
//            Assert.notNull(sign);
//            Assert.isTrue(!sign.isEmpty());
            Assert.isTrue(StringUtil.isNotBlank(sign));//!!!合并
        }
        catch (Exception e)
        {
            logger.error("必须参数为空");
            throw new ApplicationException(Constant.ErrorCode.PARAM_IS_ERROR);
        }
    }
}
