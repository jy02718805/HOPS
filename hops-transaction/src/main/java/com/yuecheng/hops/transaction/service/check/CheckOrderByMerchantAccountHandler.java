package com.yuecheng.hops.transaction.service.check;


import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.account.entity.MerchantDebitAccount;
import com.yuecheng.hops.account.service.IdentityAccountRoleService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.injection.service.ErrorCodeService;
import com.yuecheng.hops.product.entity.relation.AgentProductRelation;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.service.action.ActionHandler;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.order.OrderService;


@Service("checkOrderByMerchantAccountHandler")
public class CheckOrderByMerchantAccountHandler extends ActionHandler
{
    private static Logger           logger = LoggerFactory.getLogger(CheckOrderByMerchantAccountHandler.class);

    @Autowired
    private ErrorCodeService errorCodeService;

    @Autowired
    private OrderService     orderService;

    @Autowired
    private IdentityAccountRoleService            identityAccountRoleService;

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
            logger.debug("检查商户余额！通过");
        }
        catch (HopsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            logger.error("检查商户余额，失败！");
            throw new ApplicationException(Constant.ErrorCode.MANUAL);
        }
    }

    public void Verify()
        throws Exception
    {
        Order order = (Order)ActionContextUtil.getActionContextParam(ActionMapKey.ORDER);
        AgentProductRelation agentProduct = (AgentProductRelation)ActionContextUtil.getActionContextParam(ActionMapKey.AGENT_PRODUCT);
        MerchantDebitAccount payerAccount = null;
        try{
            payerAccount= (MerchantDebitAccount)identityAccountRoleService.getAccountByParams(
                Constant.AccountType.MERCHANT_DEBIT, order.getMerchantId(),
                IdentityType.MERCHANT.toString(), Constant.Account.ACCOUNT_RELATION_TYPE_OWN, null);
        }
        catch(CannotCreateTransactionException e){
            throw e;
        }
        catch(RpcException e){
            throw e;
        }
        catch(Exception e)
        {
            logger.error("查询商户账户信息异常,商户编号："+ String.valueOf(order.getMerchantId()).toString()+"异常信息："+ExceptionUtil.getStackTraceAsString(e));
//            payerAccount = null;
            throw new ApplicationException(Constant.ErrorCode.MANUAL);//!!!
        }
        if (payerAccount == null)
        {
            logger.error("账户不存在,商户编号："+order.getMerchantId());
        	throw new ApplicationException(Constant.ErrorCode.BALANCE_NOT_ENOUGH);
        }
        else
        {
            // 检查商户账户状态
        	if(Constant.Account.ACCOUNT_STATUS_LOCKED.equals(payerAccount.getStatus()))
			{
        		logger.debug("账户未开启,账户ID："+String.valueOf(payerAccount.getAccountId()).toString());
                // 商家状态未开启
                throw new ApplicationException(Constant.ErrorCode.ACCOUNT_LOCKED);
			}
        	BigDecimal saleFee=order.getOrderFee().multiply(agentProduct.getDiscount());
        	// 检查商户余额
            boolean balanceFlag = checkBalance(payerAccount, saleFee);
            if (balanceFlag)
            {
                logger.error("账户余额不足,商户编号："+String.valueOf(order.getMerchantId()).toString());
                // 商家扣款失败
                throw new ApplicationException(Constant.ErrorCode.BALANCE_NOT_ENOUGH);
            }
            else
            {
                ActionContextUtil.setActionContext(ActionMapKey.PAYER_ACCOUNT, payerAccount);
                // 检查完订单必输字段后，初始化下单交易数据
                orderService.OrderDataInitialization();
            }
        }
    }

    public boolean checkBalance(MerchantDebitAccount payerAccount, BigDecimal orderFee)
    {
        if (payerAccount != null)
        {
            BigDecimal avilableBalance = payerAccount.getAvailableBalance(); // 可用余额
            BigDecimal creditableBanlance = payerAccount.getCreditableBanlance(); // 授信余额
            BigDecimal merchantBalance = avilableBalance.add(creditableBanlance); // 商户总余额（包括授信余额）
            if (merchantBalance.compareTo(orderFee) < 0)
            {
                // 商家扣款失败
                return true;
            }
        }
        else
        {
            return true;
        }
        return false;
    }
}
