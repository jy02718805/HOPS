/*
 * 文件名：AgentPrepareOrderReceptor.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：yuanbiao
 * 修改时间：2015年4月8日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.transaction.service.builder;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.product.entity.airtimes.AirtimeProduct;
import com.yuecheng.hops.transaction.DefaultTransactionResponseImpl;
import com.yuecheng.hops.transaction.TransactionContextUtil;
import com.yuecheng.hops.transaction.TransactionMapKey;
import com.yuecheng.hops.transaction.TransactionRequest;
import com.yuecheng.hops.transaction.TransactionResponse;
import com.yuecheng.hops.transaction.TransactionService;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.process.AgentPrepareOrderCheckProcess;

@org.springframework.core.annotation.Order(1)
@Service("agentPrepareOrderReceptor")
public class AgentPrepareOrderReceptor implements TransactionService{

	private static Logger logger = LoggerFactory.getLogger(AgentPrepareOrderReceptor.class);
	
	//预下单查询结果常量
	private static final String SUCCESS_STATUS = "1";
	
	private static final String FAIL_STATUS = "2";
	
	
	@Autowired
    private AgentPrepareOrderCheckProcess          agentPrepareOrderCheckProcess;
	@Override
	@Transactional
	public TransactionResponse doTransaction(TransactionRequest transactionRequest) {
		logger.debug("代理商预下单，进入doTransaction方法。"+String.valueOf(transactionRequest).toString());
        TransactionContextUtil.copyPropertiesIfAbsent(transactionRequest);
        TransactionResponse response = new DefaultTransactionResponseImpl();
        String ext1 = (String)TransactionContextUtil.getTransactionContextParam(EntityConstant.Order.EXT1);
        Order order = new Order();
        try
        {
	        BeanUtils.populate(order, TransactionContextUtil.getTransactionContextLocal());
	        String orderDesc = StringUtil.replaceNullToBlank(ext1);
	        order.setOrderDesc(orderDesc);
	        Object objDate=TransactionContextUtil.getTransactionContextParam(EntityConstant.Order.ORDER_REQUEST_TIME);
	        Date dateTime=BeanUtils.isNotNull(objDate)?(Date)objDate:null;
	        order.setOrderRequestTime(dateTime);
	        
	        logger.debug("代理商下单，order详情：[" + String.valueOf(order).toString() + "]");
            TransactionContextUtil.setTransactionContext(TransactionMapKey.ORDER, order);
            
            ActionContextUtil.init();
            ActionContextUtil.setActionContext(ActionMapKey.ORDER, TransactionContextUtil.getTransactionContextParam(TransactionMapKey.ORDER));
            ActionContextUtil.setActionContext(ActionMapKey.SIGN, TransactionContextUtil.getTransactionContextParam(TransactionMapKey.SIGN));
            ActionContextUtil.setActionContext(ActionMapKey.SIGNTYPE, TransactionContextUtil.getTransactionContextParam(TransactionMapKey.SIGNTYPE));
//            ActionContextUtil.setActionContext(ActionMapKey.VERSION, TransactionContextUtil.getTransactionContextParam("version"));
//            ActionContextUtil.setActionContext(ActionMapKey.ENCODING, TransactionContextUtil.getTransactionContextParam("encoding"));
            // 1.订单检查
            agentPrepareOrderCheckProcess.execute();
            AirtimeProduct product = (AirtimeProduct)TransactionContextUtil.getTransactionContextParam("product");
            //预下单成功
            Map<String, Object> orderMap = BeanUtils.transBean2Map(order);
            response.copyPropertiesIfAbsent(orderMap);
            response.setErrorCode(Constant.ErrorCode.SUCCESS);
            response.setParameter("status", SUCCESS_STATUS);
            //AirtimeProduct [province=HUN, parValue=10, carrierName=YD, city=null, parentProductId=1131005, productNo=HFYDHUN****00001000, businessType=0]
            response.setParameter("province", product.getProvince());
            response.setParameter("carrierName", product.getCarrierName());
        }
        catch(HopsException e)
        {
        	logger.error("failed to process the transaction caused by" + ExceptionUtil.getStackTraceAsString(e));
        	Map<String, Object> orderMap = BeanUtils.transBean2Map(order);
        	response.copyPropertiesIfAbsent(orderMap);
            response.setErrorCode(Constant.ErrorCode.FAIL);
            response.setParameter(Constant.TransactionCode.ERROR_CODE,e.getCode());// e.getCode());
            response.setParameter("status", FAIL_STATUS);
            return response;
        }
        catch (Exception e)
        {
            logger.error("failed to process the transaction caused by" + ExceptionUtil.getStackTraceAsString(e));
            Map<String, Object> orderMap = BeanUtils.transBean2Map(order);
        	response.copyPropertiesIfAbsent(orderMap);
            response.setErrorCode(Constant.ErrorCode.FAIL);
            response.setParameter(Constant.TransactionCode.ERROR_CODE,Constant.ConInterface.SYSTEM_ERROR);// e.getCode());
            response.setParameter("status", FAIL_STATUS);
            return response;
        }
		return response;
	}

}
