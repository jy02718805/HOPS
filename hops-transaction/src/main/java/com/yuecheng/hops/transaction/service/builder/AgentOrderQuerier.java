package com.yuecheng.hops.transaction.service.builder;


import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.injection.service.ErrorCodeService;
import com.yuecheng.hops.transaction.DefaultTransactionResponseImpl;
import com.yuecheng.hops.transaction.TransactionContextUtil;
import com.yuecheng.hops.transaction.TransactionMapKey;
import com.yuecheng.hops.transaction.TransactionRequest;
import com.yuecheng.hops.transaction.TransactionResponse;
import com.yuecheng.hops.transaction.TransactionService;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.basic.entity.bo.OrderBo;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.order.OrderManagement;
import com.yuecheng.hops.transaction.service.order.OrderService;
import com.yuecheng.hops.transaction.service.process.AgentQueryOrderCheckProcess;

@Service("agentOrderQuerier")
public class AgentOrderQuerier implements TransactionService
{
    private static Logger       logger = LoggerFactory.getLogger(AgentOrderQuerier.class);
    
    @Autowired
    private OrderService        orderService;

    @Autowired
    private AgentQueryOrderCheckProcess agentQueryOrderCheckProcess;

    @Autowired
    private OrderManagement     orderManagement;

    @Autowired
    private MerchantService     merchantService;

    @Autowired
    private ErrorCodeService    errorCodeService;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)//!!!应该删除
    public TransactionResponse doTransaction(TransactionRequest transactionRequest)
    {
        logger.debug("代理商查单，进入doTransaction方法。");
        TransactionContextUtil.copyPropertiesIfAbsent(transactionRequest);
        TransactionResponse response = new DefaultTransactionResponseImpl();
        
        OrderBo orderBo = new OrderBo();
        Order order = new Order();
        BeanUtils.populate(order, TransactionContextUtil.getTransactionContextLocal());
        TransactionContextUtil.setTransactionContext(TransactionMapKey.ORDER, order);

        logger.debug("代理商查单，order详情：[" + order.toString() + "]");
        try
        {
            logger.debug("代理商查单，开始检查入参");
            ActionContextUtil.init();
            ActionContextUtil.setActionContext(ActionMapKey.ORDER, TransactionContextUtil.getTransactionContextParam(TransactionMapKey.ORDER));
            ActionContextUtil.setActionContext(ActionMapKey.SIGN, TransactionContextUtil.getTransactionContextParam(TransactionMapKey.SIGN));
            agentQueryOrderCheckProcess.execute();
            logger.debug("代理商查单，检查入参结束，开始查询订单并组装订单对象");
            order = (Order)TransactionContextUtil.getTransactionContextParam(TransactionMapKey.ORDER);
            Merchant merchant = (Merchant)TransactionContextUtil.getTransactionContextParam(TransactionMapKey.AGENT_MERCHANT);
//            order = orderManagement.findOrderByMerchantOrderNo(order.getMerchantId(), order.getMerchantOrderNo());
            logger.debug("查询订单  [结束]");

            BeanUtils.copyProperties(orderBo, order);
            orderBo.setOrderFinishTime(order.getOrderFinishTime());
            orderBo.setOrderRequestTime(order.getOrderRequestTime());
            
//            Merchant merchant = merchantService.queryMerchantById(order.getMerchantId()); //!!!在检查时已经查询
            orderBo.setMerchantCode(merchant.getMerchantCode().getCode());
            if (StringUtil.isNullOrEmpty(order.getOrderDesc()) || order.getOrderDesc().length() == 2)//!!!抽出方法
            {
                orderBo.setInfo1(StringUtil.initString());
                orderBo.setInfo2(StringUtil.initString());
                orderBo.setInfo3(StringUtil.initString());
            }
            else
            {
                String[] infos = order.getOrderDesc().split(Constant.StringSplitUtil.DECODE);
                if (infos.length == 1)
                {
                	orderBo.setInfo1(infos[0]);
                }
                else if (infos.length == 2)
                {
                	orderBo.setInfo1(infos[0]);
                	orderBo.setInfo2(infos[1]);
                }
                else if(infos.length == 3)
                {
                	orderBo.setInfo1(infos[0]);
                	orderBo.setInfo2(infos[1]);
                	orderBo.setInfo3(infos[2]);
                }
                else
                {
                    orderBo.setInfo1(StringUtil.initString());
                    orderBo.setInfo2(StringUtil.initString());
                    orderBo.setInfo3(StringUtil.initString());
                }
            }
            
            if(!(order.getOrderStatus() == Constant.OrderStatus.FAILURE_ALL || order.getOrderStatus() == Constant.OrderStatus.SUCCESS)){
                //时间到了
                if(BeanUtils.isNotNull(order.getPreSuccessStatus()) 
                    &&( order.getPreSuccessStatus().compareTo(Constant.OrderStatus.PRE_SUCCESS_STATUS_DONE) == 0
                    ||order.getPreSuccessStatus().compareTo(Constant.OrderStatus.PRE_SUCCESS_STATUS_WAIT) == 0 ) && order.getOrderPreSuccessTime().compareTo(new Date()) < 0)
                {
                    orderBo.setOrderStatus(Constant.OrderStatus.SUCCESS);
                }
            }
            
            Map<String, Object> orderMap = BeanUtils.transBean2Map(orderBo);
            response.copyPropertiesIfAbsent(orderMap);
            
            if(orderBo.getOrderStatus().equals(Constant.OrderStatus.SUCCESS))//!!!常量前置
            {
	            String errorCode = Constant.ErrorCode.RECHARGE_SUCCESS;
	            TransactionContextUtil.setResult(Constant.TrueOrFalse.TRUE);
	            TransactionContextUtil.setErrorCode(errorCode);
            }else if(orderBo.getOrderStatus().equals(Constant.OrderStatus.FAILURE_ALL)){
            	String errorCode = Constant.ErrorCode.RECHARGE_FAIL;
				if (orderBo.getSpecialDown() == Constant.SpecialDown.PAIPAI
						&& orderBo.getNotifyStatus() != Constant.NotifyStatus.NOTIFY_SUCCESS)
				{
					errorCode = Constant.ErrorCode.REFUND_PROCESS;
				}
	            TransactionContextUtil.setResult(Constant.TrueOrFalse.TRUE);
	            TransactionContextUtil.setErrorCode(errorCode);
            }else{
            	String errorCode = Constant.ErrorCode.WAITING_RECHARGE;
	            TransactionContextUtil.setResult(Constant.TrueOrFalse.TRUE);
	            TransactionContextUtil.setErrorCode(errorCode);
            }
            
            response.setParameter(Constant.TransactionCode.RESULT, TransactionContextUtil.getResult());
            response.setParameter(Constant.TransactionCode.ERROR_CODE, TransactionContextUtil.getErrorCode());
            response.setParameter(TransactionMapKey.INTERFACE_MERCHANT_ID, order.getMerchantId());
            logger.debug("代理商查单，返回信息response:[" + response + "]");
            TransactionContextUtil.clear();
            return response;
        }
        catch (HopsException e)
        {
            logger.error("代理商查单，异常信息：transactionRequest[" + transactionRequest + "]");
            throw e;
        }
        catch (InvocationTargetException e)
        {
            logger.error("orderBo copyProperties，异常信息：[" + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw new ApplicationException(Constant.ErrorCode.MANUAL);
        }
        catch (IllegalAccessException e)
        {
            logger.error("orderBo copyProperties，异常信息：[" + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw new ApplicationException(Constant.ErrorCode.MANUAL);
        }//!!!异常捕捉
    }

}
