package com.yuecheng.hops.transaction.service.builder;


import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.account.entity.MerchantDebitAccount;
import com.yuecheng.hops.account.service.IdentityAccountRoleService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.DecimalPlaces;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.BigDecimalUtil;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.transaction.DefaultTransactionResponseImpl;
import com.yuecheng.hops.transaction.TransactionContextUtil;
import com.yuecheng.hops.transaction.TransactionMapKey;
import com.yuecheng.hops.transaction.TransactionRequest;
import com.yuecheng.hops.transaction.TransactionResponse;
import com.yuecheng.hops.transaction.TransactionService;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.process.AgentQueryAccountCheckProcess;

@Service("agentAccountQuerier")
public class AgentAccountQuerier implements TransactionService
{
    private static Logger       logger = LoggerFactory.getLogger(AgentAccountQuerier.class);
    
    @Autowired
    private AgentQueryAccountCheckProcess        agentQueryAccountCheckProcess;

    @Autowired
    private MerchantService     merchantService;

    @Autowired
    private IdentityAccountRoleService    identityAccountRoleService;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public TransactionResponse doTransaction(TransactionRequest transactionRequest)
    {
        logger.debug("代理商查询账户，进入doTransaction方法。");
        TransactionContextUtil.copyPropertiesIfAbsent(transactionRequest);
        TransactionResponse response = new DefaultTransactionResponseImpl();
        
        try
        {
            logger.debug("开始执行检查流程！");
            ActionContextUtil.init();
            TransactionContextUtil.getTransactionContextLocal();
            String merchantCode = (String)TransactionContextUtil.getTransactionContextParam(TransactionMapKey.MERCHANT_CODE);
            Date tsp = (Date)TransactionContextUtil.getTransactionContextParam(TransactionMapKey.TSP);
            ActionContextUtil.setActionContext(ActionMapKey.MERCHANT_CODE, merchantCode);
            ActionContextUtil.setActionContext(ActionMapKey.TSP, tsp);
            // 1.订单检查
            agentQueryAccountCheckProcess.execute();
            logger.debug("检查通过！查询商户账户信息[" + merchantCode + "]");
            Merchant merchant = merchantService.queryMerchantByMerchantCode(merchantCode);
            MerchantDebitAccount account = (MerchantDebitAccount)identityAccountRoleService.getAccountByParams(Constant.AccountType.MERCHANT_DEBIT, merchant.getId(), IdentityType.MERCHANT.toString(), Constant.Account.ACCOUNT_RELATION_TYPE_OWN, null);
            
            BigDecimal money = new BigDecimal(0);
            if(BeanUtils.isNotNull(account)){
                money = BigDecimalUtil.add(account.getAvailableBalance(), account.getCreditableBanlance(), DecimalPlaces.THREE.value(), BigDecimal.ROUND_HALF_UP);
            }
            
            response.setErrorCode(Constant.ErrorCode.SUCCESS);
            response.setParameter(TransactionMapKey.MONEY, money);
            response.setParameter(TransactionMapKey.INTERFACE_MERCHANT_ID, merchant.getId());
            logger.debug("代理商查询账户，返回信息response:[" + response + "]");
            TransactionContextUtil.clear();
            return response;
        }
        catch (HopsException e)
        {
            logger.error("代理商查询账户，异常信息：[" + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw e;
        }
    }

}
