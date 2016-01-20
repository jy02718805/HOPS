package com.yuecheng.hops.account.verification;


import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuecheng.hops.account.entity.Account;
import com.yuecheng.hops.account.entity.CardAccount;
import com.yuecheng.hops.account.entity.CurrencyAccount;
import com.yuecheng.hops.account.service.CCYAccountService;
import com.yuecheng.hops.account.service.IdentityAccountRoleService;
import com.yuecheng.hops.account.utils.CardAccountSignUtil;
import com.yuecheng.hops.account.utils.CurrencyAccountSignUtil;
import com.yuecheng.hops.common.enump.AccountModelType;
import com.yuecheng.hops.common.utils.StringUtil;

//做利润归集时需要添加sign，平时交易不用sign
public class AccountVerification implements MethodBeforeAdvice
{
    private static Logger logger = LoggerFactory.getLogger(AccountVerification.class);

    @Autowired
    private CCYAccountService ccyAccountService;
    
    @Autowired
    private IdentityAccountRoleService identityAccountRoleService;

    @Override
    public void before(Method method, Object[] args, Object target)
        throws Throwable
    {
        // 修改账户金额之前需要验证sign
        if (method.getName().startsWith("update"))
        {
            logger.debug("开始检查账户签名 methodName:" + method.getName());
            // 现在只处理现金账户，以后规定第二个参数一定要为accountType.（用来区分不同类型的account）
            Long accountId = Long.valueOf(String.valueOf(args[0]));
        }
    }

    public boolean verificationAccount(Account account)
    {
        String signStr = StringUtil.initString();
        if (AccountModelType.FUNDS.equals(account.getAccountType().getTypeModel()))
        {
            CurrencyAccount currencyAccount = (CurrencyAccount)account;
            signStr = CurrencyAccountSignUtil.getSign(currencyAccount);
        }
        else
        {
            CardAccount cardAccount = (CardAccount)account;
            signStr = CardAccountSignUtil.getSign(cardAccount);
        }
        logger.debug("检查账户    新签名串 [" + signStr + "] 旧签名串[" + account.getSign() + "]");
        if (account.getSign().equalsIgnoreCase(signStr))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
