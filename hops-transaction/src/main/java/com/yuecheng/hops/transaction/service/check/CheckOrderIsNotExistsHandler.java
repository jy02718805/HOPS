package com.yuecheng.hops.transaction.service.check;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.injection.service.ErrorCodeService;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.service.action.ActionHandler;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.order.OrderManagement;


@Service("checkOrderIsNotExistsHandler")
public class CheckOrderIsNotExistsHandler extends ActionHandler
{
    private static Logger logger = LoggerFactory.getLogger(CheckOrderIsNotExistsHandler.class);

    @Autowired
    private ErrorCodeService errorCodeService;

    @Autowired
    private OrderManagement orderManagement;

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
            logger.info("检查订单是否不存在！通过");
        }
        catch (HopsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            logger.error("检查订单是否不存在，失败！");
            throw new ApplicationException(Constant.ErrorCode.MANUAL);
        }
    }

    public void Verify()
        throws Exception
    {
        Order order = (Order)ActionContextUtil.getActionContextParam(ActionMapKey.ORDER);
        String merchantOrderNo = order.getMerchantOrderNo();
        Long merchantId = order.getMerchantId();
        try
        {
            order = orderManagement.findOrderByMerchantOrderNo(merchantId,
                order.getMerchantOrderNo());
        }
        catch(CannotCreateTransactionException e){
            throw e;
        }
        catch (RpcException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            logger.error("异常信息：" + ExceptionUtil.getStackTraceAsString(e));
//            throw new ApplicationException(Constant.ErrorCode.IS_NOT_EXIST);
            throw e;//!!!修正错误码
        }
        if (BeanUtils.isNull(order))
        {
            logger.error("订单不存在,订单编号:[" + merchantOrderNo + "] merchantId:["+merchantId+"]");
            // 订单不存在
            throw new ApplicationException(Constant.ErrorCode.IS_NOT_EXIST);
        }else{
            ActionContextUtil.setActionContext(ActionMapKey.ORDER, order);//!!!避免查单主程序进行2次查询
        }
    }

}
