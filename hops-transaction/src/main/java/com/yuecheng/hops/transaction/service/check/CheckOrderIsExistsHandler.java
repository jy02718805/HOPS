package com.yuecheng.hops.transaction.service.check;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.util.Assert;

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


@Service("checkOrderIsExistsHandler")
public class CheckOrderIsExistsHandler extends ActionHandler
{
    private static Logger           logger = LoggerFactory.getLogger(CheckOrderIsExistsHandler.class);

    @Autowired
    private OrderManagement  orderManagement;

    @Autowired
    private ErrorCodeService errorCodeService;


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
            logger.debug("检查订单是否已经存在！通过");
        }
        catch (HopsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            logger.error("检查订单是否已经存在，失败！");
            throw new ApplicationException(Constant.ErrorCode.MANUAL);
        }
    }

    public void Verify()
        throws Exception
    {
        Order order = (Order)ActionContextUtil.getActionContextParam(ActionMapKey.ORDER);
    	try{
            // 检查下游商户订单号是否存在
    	    String merchantOrderNo = order.getMerchantOrderNo();
    	    Long merchantId = order.getMerchantId();
            order = orderManagement.findOrderByMerchantOrderNo(merchantId, merchantOrderNo);
            Assert.isTrue(orderManagement.checkIsExit(merchantId, merchantOrderNo));
    	}
    	catch(CannotCreateTransactionException e){
    	    throw e;
    	}
    	catch(RpcException e){
            throw e;
        }
    	catch(Exception e)
    	{
    		ActionContextUtil.setActionContext(ActionMapKey.ORDER_NO, order.getOrderNo());
    		// 订单已存在
            logger.error("异常信息："+ExceptionUtil.getStackTraceAsString(e));
            String errorCode = getOrderStateMappingCode(order);
            throw new ApplicationException(errorCode);
    	}
        if (BeanUtils.isNotNull(order)&&BeanUtils.isNotNull(order.getMerchantOrderNo()))
        {
        	ActionContextUtil.setActionContext(ActionMapKey.ORDER_NO, order.getOrderNo());
            logger.error("订单已存在,订单编号："+order.getMerchantOrderNo());
            
            String errorCode = getOrderStateMappingCode(order);
            
            // 订单已存在
            throw new ApplicationException(errorCode);
        }
    }

	private String getOrderStateMappingCode(Order order)//!!!gateway映射解决
	{
		String errorCode = Constant.ErrorCode.IS_EXIST;
		
		//拍拍下单接口如果订单存在需要返回订单状态
		if(order.getSpecialDown() == Constant.SpecialDown.PAIPAI)
		{
			//映射
			if(order.getOrderStatus() == Constant.OrderStatus.SUCCESS)
			{
				errorCode = Constant.ErrorCode.RECHARGE_SUCCESS;
			}
			else if(order.getOrderStatus() == Constant.OrderStatus.FAILURE_ALL)
			{
				if(order.getNotifyStatus() != Constant.NotifyStatus.NOTIFY_SUCCESS)
				{
					errorCode = Constant.ErrorCode.REFUND_PROCESS;
				}
				else 
				{
					errorCode = Constant.ErrorCode.RECHARGE_FAIL;
				}
			} 
			else
			{
				errorCode = Constant.ErrorCode.WAITING_RECHARGE;
			}
		}
		return errorCode;
	}

}
