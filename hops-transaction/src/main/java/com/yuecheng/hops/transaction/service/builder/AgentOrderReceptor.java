package com.yuecheng.hops.transaction.service.builder;


import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.yuecheng.hops.account.entity.CCYAccount;
import com.yuecheng.hops.account.entity.role.IdentityAccountRole;
import com.yuecheng.hops.account.service.IdentityAccountRoleService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.event.HopsPublisher;
import com.yuecheng.hops.common.event.HopsRequestEvent;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.NumberUtil;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.injection.service.ErrorCodeService;
import com.yuecheng.hops.injection.service.InterfaceService;
import com.yuecheng.hops.numsection.entity.NumSection;
import com.yuecheng.hops.product.entity.relation.AgentProductRelation;
import com.yuecheng.hops.transaction.DefaultTransactionResponseImpl;
import com.yuecheng.hops.transaction.TransactionContextUtil;
import com.yuecheng.hops.transaction.TransactionMapKey;
import com.yuecheng.hops.transaction.TransactionRequest;
import com.yuecheng.hops.transaction.TransactionResponse;
import com.yuecheng.hops.transaction.TransactionService;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.event.BindAgentOrderEvent;
import com.yuecheng.hops.transaction.execution.account.AirtimeAccountingTransaction;
import com.yuecheng.hops.transaction.service.action.AbstractEventAction;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.order.OrderManagement;
import com.yuecheng.hops.transaction.service.order.builder.support.NotifyBuilder;
import com.yuecheng.hops.transaction.service.order.builder.support.OrderInitialization;
import com.yuecheng.hops.transaction.service.process.AgentOrderReceivecheckProcess;

@org.springframework.core.annotation.Order(1)
@Service("agentOrderReceptor")
public class AgentOrderReceptor implements TransactionService
{
    private static Logger                logger = LoggerFactory.getLogger(AgentOrderReceptor.class);
    
    @Autowired
    private AgentOrderReceivecheckProcess          agentOrderReceivecheckProcess;

    @Autowired
    private OrderInitialization          orderInitialization;

    @Autowired
    private NotifyBuilder                notifyBuilder;

    @Autowired
    private AirtimeAccountingTransaction airtimeAccountingTransaction;

    @Autowired
    private ErrorCodeService             errorCodeService;

    @Autowired
    private OrderManagement              orderManagement;

    @Autowired
    private HopsPublisher                publisher;

    @Autowired
    @Qualifier("orderStatusWaitAction")
    private AbstractEventAction orderStatusWaitAction;
    
    @Autowired
    @Qualifier("orderStatusFailAction")
    private AbstractEventAction orderStatusFailAction;
    
    @Autowired
    @Qualifier("orderStatusRechargingAction")
    private AbstractEventAction orderStatusRechargingAction;
    
    @Autowired
    private IdentityAccountRoleService identityAccountRoleService;
    
    @Autowired
    private InterfaceService interfaceService;
    
    
    @Override
    public TransactionResponse doTransaction(TransactionRequest transactionRequest)
    {
        logger.debug("代理商下单，进入doTransaction方法。"+String.valueOf(transactionRequest).toString());
        TransactionContextUtil.copyPropertiesIfAbsent(transactionRequest);
        TransactionResponse response = new DefaultTransactionResponseImpl();
        Order order = new Order();
        //状态标示，当是淘宝下单的时候，如果productNum为空或者非数字时，标示为true；
        Boolean flag = false;
        
        //淘宝订单跟普通订单标示：默认-淘宝订单
        Boolean isTBorder = true;
        try
        {
	        BeanUtils.populate(order, TransactionContextUtil.getTransactionContextLocal());
	        try{//assert
	        	order.setProductNum(Long.valueOf((String)TransactionContextUtil.getTransactionContextParam(EntityConstant.Order.PORDUCT_NUM)));
	        }catch(Exception e)
	        {
	        	//如果是淘宝订单，当输入购买数量为字母时，抛出255异常
	        	if (null != order.getProductNo())
	        	{
	        		flag = true;
	        	}
	        }
	        Object buziType = TransactionContextUtil.getTransactionContextParam(EntityConstant.Order.BUSINESS_TYPE);
	        
			// 如果未传值默认为话费业务 !!优化一下写法
			if (null == buziType)
			{
			    String businessType = StringUtil.initString();
                if(NumberUtil.isFixedPhone(order.getUserCode()))
                {
                    businessType = Constant.BusinessType.BUSINESS_TYPE_FIXED;
                }
                else
                {
                    businessType = Constant.BusinessType.BUSINESS_TYPE_HF;
                }
				order.setBusinessType(Long.valueOf(businessType));
			}
			else
			{
				order.setBusinessType(Long.valueOf(buziType.toString()));
			}
	        
	        String info1 = (String)TransactionContextUtil.getTransactionContextParam(EntityConstant.Order.INFO1);
	        String info2 = (String)TransactionContextUtil.getTransactionContextParam(EntityConstant.Order.INFO2);
	        String info3 = (String)TransactionContextUtil.getTransactionContextParam(EntityConstant.Order.INFO3);
	        String orderDesc = StringUtil.replaceNullToBlank(info1) + "|" + StringUtil.replaceNullToBlank(info2) + "|" + StringUtil.replaceNullToBlank(info3);
	        //普通订单 null == order.getProductNo()
	        if (null == order.getProductNo())//!!! geteway传值区分
	        {
	        	order.setOrderDesc(orderDesc);
	        	isTBorder = false;
	        }
	        
	        Object objDate=TransactionContextUtil.getTransactionContextParam(EntityConstant.Order.ORDER_REQUEST_TIME);
	        Date dateTime=BeanUtils.isNotNull(objDate)?(Date)objDate:null;
	        order.setOrderRequestTime(dateTime);
	        
	        logger.debug("代理商下单，order详情：[" + String.valueOf(order).toString() + "]");
            TransactionContextUtil.setTransactionContext(TransactionMapKey.ORDER, order);
            
            ActionContextUtil.init();
            ActionContextUtil.setActionContext(ActionMapKey.ORDER, TransactionContextUtil.getTransactionContextParam(TransactionMapKey.ORDER));
            ActionContextUtil.setActionContext(ActionMapKey.NOTIFY_URL, TransactionContextUtil.getTransactionContextParam(EntityConstant.Notify.NOTIFY_URL));
            ActionContextUtil.setActionContext(ActionMapKey.SIGN, TransactionContextUtil.getTransactionContextParam(TransactionMapKey.SIGN));
            ActionContextUtil.setActionContext(ActionMapKey.PORDUCT_NUM,flag);
            // 1.订单检查
            agentOrderReceivecheckProcess.execute();
        }
        catch(HopsException e)
        {
        	//transactionRequest.put(ActionMapKey.ORDER_NO, ActionContextUtil.getActionContextParam(ActionMapKey.ORDER_NO));
            throw e;
        }
        
        try{
            order = (Order)TransactionContextUtil.getTransactionContextParam(TransactionMapKey.ORDER);
            Merchant agentMerchant = (Merchant)TransactionContextUtil.getTransactionContextParam(TransactionMapKey.AGENT_MERCHANT);
            AgentProductRelation agentProduct = (AgentProductRelation)TransactionContextUtil.getTransactionContextParam(TransactionMapKey.AGENT_PRODUCT);
            NumSection numSection = (NumSection)TransactionContextUtil.getTransactionContextParam(TransactionMapKey.NUM_SECTION);
            // 2.登记订单
            logger.debug("登记订单  [开始] 号码：["+order.getUserCode()+"]");
            order = orderInitialization.initOrder(order, agentMerchant, agentProduct, numSection);
            TransactionContextUtil.setTransactionContext(TransactionMapKey.ORDER, order);
            logger.debug("登记订单  [结束] 号码：["+order.getUserCode()+"]" + " 订单编号:["+order.getOrderNo()+"]");
            // 3.检查是否有通知接口配置，如果没有不生成通知记录，如果有就生成通知记录
            Boolean checkFlag = false;
            //0:普通订单，1：淘宝订单
            Long notify_type = 0L;
            if (Constant.SpecialDown.PAIPAI == order.getSpecialDown())
			{
				checkFlag = interfaceService.checkInterfaceIsExist(order.getMerchantId(),
						Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_ORDER_SUCCESS_PAIPAI);
			}
            else if (isTBorder)
            {
            	checkFlag = interfaceService.checkInterfaceIsExist(order.getMerchantId(), Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_TBORDER);
            	notify_type = 1L;
            	logger.debug("是否生成淘宝通知记录"+checkFlag);
            }
            else
            {
            	checkFlag = interfaceService.checkInterfaceIsExist(order.getMerchantId(), Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_ORDER);
            	logger.debug("是否生成普通通知记录"+checkFlag);
            }
			if (checkFlag)
			{
				logger.debug("生成通知记录");
				String notifyUrl = (String) TransactionContextUtil
						.getTransactionContextParam(EntityConstant.Notify.NOTIFY_URL);
				String failedNotifyUrl = null;
				if (Constant.SpecialDown.PAIPAI == order.getSpecialDown())
				{
					notifyUrl = interfaceService.getInterfacePacketsDefinitionByParams(agentMerchant.getId(),
							Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_ORDER_SUCCESS_PAIPAI).getRequestUrl();
					failedNotifyUrl = interfaceService.getInterfacePacketsDefinitionByParams(agentMerchant.getId(),
							Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_ORDER_FAIL_PAIPAI).getRequestUrl();
				}
				notifyBuilder.createNotify(order.getOrderNo(), agentMerchant.getId(), notifyUrl, notify_type,
						failedNotifyUrl);
				//!!!发起事件,可以在最后进行登记操作
			}
            // 4.扣款
            CCYAccount payerAccount = (CCYAccount)TransactionContextUtil.getTransactionContextParam(ActionMapKey.PAYER_ACCOUNT);
            IdentityAccountRole payeeAccount = identityAccountRoleService.getIdentityAccountRoleByParams(
                Constant.AccountType.SYSTEM_DEBIT, order.getMerchantId(),
                IdentityType.MERCHANT.toString(), Constant.Account.ACCOUNT_RELATION_TYPE_USE, order.getOrderNo());
            Assert.notNull(payeeAccount,"payeeAccount is null!");
            Assert.notNull(payerAccount,"payerAccount is null!");
            Long payeeAccountId = payeeAccount.getAccountId();
            Long payerAccountId = payerAccount.getAccountId();
        
            BigDecimal amt = order.getOrderSalesFee();
            String desc = "代理商下单，收款方:[" + payeeAccountId + "]、付款方:[" + payerAccountId
                          + "] 交易金额为:[" + amt + "]";
            logger.debug("代理商下单，收款方:[" + payeeAccountId + "]、付款方:[" + payerAccountId + "] 交易金额为:["
                         + amt + "]");
            airtimeAccountingTransaction.transfer(payerAccountId,
                payerAccount.getAccountType().getAccountTypeId(), payeeAccountId,
                payeeAccount.getAccountType(), amt, desc,
                Constant.TransferType.TRANSFER_AGENT_ORDERED, order.getOrderNo());//!!!可以返回账户余额信息
            logger.debug("完成扣款动作");
        
            /* 发起收单职责链 */
            ActionContextUtil.init();
            ActionContextUtil.setActionContext(ActionMapKey.ORDER, TransactionContextUtil.getTransactionContextParam(TransactionMapKey.ORDER));
            orderStatusWaitAction.handleRequest();
            TransactionContextUtil.copyProperties(ActionContextUtil.getActionContextLocal());
            
            order = (Order)TransactionContextUtil.getTransactionContextParam(TransactionMapKey.ORDER);
            ActionContextUtil.init();
            ActionContextUtil.setActionContext(ActionMapKey.ORDER, order);
            orderStatusRechargingAction.handleRequest();//!!!需要讨论
            
            HopsRequestEvent hre = new HopsRequestEvent(AgentOrderReceptor.class);
            hre = new BindAgentOrderEvent(AgentOrderReceptor.class, order.getOrderNo());
            TransactionContextUtil.setTransactionContext(TransactionMapKey.HOPS_REQUEST_EVENT, hre);
            // 5.修改订单状态 结束
            logger.info("[下单]" + order.toString() + "成功");
            Map<String, Object> orderMap = BeanUtils.transBean2Map(order);
            response.copyPropertiesIfAbsent(orderMap);
            response.setErrorCode(Constant.ErrorCode.SUCCESS);
            response.setParameter(TransactionMapKey.INTERFACE_MERCHANT_ID, order.getMerchantId());
            logger.debug("代理商下单，返回信息response_fields:[" + response + "]");
            return response;
        }
        catch (HopsException e)//!!!
        {
            if(e.getCode().equalsIgnoreCase(Constant.ErrorCode.IS_EXIST))
            {
                throw e;
            }
            else
            {
                logger.error("扣款失败 "+ExceptionUtil.getStackTraceAsString(e));
                ActionContextUtil.init();
                ActionContextUtil.setActionContext(ActionMapKey.ORDER, TransactionContextUtil.getTransactionContextParam(TransactionMapKey.ORDER));
                orderStatusFailAction.handleRequest();
                throw e;
            }
        }

    }

}
