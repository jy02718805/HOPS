/*
 * 文件名：AccountStatusManagement.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月15日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.account.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.account.entity.Account;
import com.yuecheng.hops.account.entity.AccountOperationHistory;
import com.yuecheng.hops.account.entity.AccountStatusTransfer;
import com.yuecheng.hops.account.entity.AccountType;
import com.yuecheng.hops.account.entity.CCYAccount;
import com.yuecheng.hops.account.entity.CardAccount;
import com.yuecheng.hops.account.repository.AccountDao;
import com.yuecheng.hops.account.repository.AccountOperationHistoryDao;
import com.yuecheng.hops.account.repository.AccountStatusTransferDao;
import com.yuecheng.hops.account.service.AccountStatusManagement;
import com.yuecheng.hops.account.utils.CardAccountSignUtil;
import com.yuecheng.hops.account.utils.CurrencyAccountSignUtil;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.AccountModelType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;


/**
 * 账户状态管理
 * 
 * @author Administrator
 * @version 2014年10月16日
 * @see AccountStatusManagementImpl
 * @since
 */
@Service("accountStatusManagement")
public class AccountStatusManagementImpl implements AccountStatusManagement
{
    private static Logger logger = LoggerFactory.getLogger(AccountStatusManagementImpl.class);

    @Autowired
    protected AccountDaoFinder accountDaoFinder;

    @Autowired
    private AccountOperationHistoryDao accountOperationHistoryDao;

    @Autowired
    private AccountStatusTransferDao accountStatusDefendersDao;

    /**
     * 修改账户状态
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
    public void updateAccountStatus(Long accountId, AccountType accountType,
                                    String status, String updateUser)
    {
        try
        {
            AccountDao accountDao = accountDaoFinder.getByModelType(accountType, null);

            Account init_account = (Account)accountDao.findOne(accountId);
            Assert.notNull(init_account, "修改账户状态失败：根据ID未匹配到对应account.");
            logger.debug("查询账户 accountId[" + accountId + "]  accountModelType["
                         + accountType.getTypeModel().toString() + "]");
            if (updateAccountStatuCheck(accountType.getTypeModel(), status, init_account.getStatus()))
            {
                AccountOperationHistory aoh = new AccountOperationHistory();
                aoh.setAccountId(accountId);
                aoh.setOperatorDate(new Date());
                aoh.setOperator(updateUser);
                aoh.setDescStr("账户状态从" + init_account.getStatus() + "变成" + status);
                aoh.setOldStatus(init_account.getStatus());
                aoh.setNewStatus(status);
                aoh.setTypeModel(accountType.getTypeModel().toString());
                accountOperationHistoryDao.save(aoh);
                logger.debug("记录账户操作记录");
                String signStr = StringUtil.initString();
                init_account.setStatus(status);
                if (AccountModelType.FUNDS.equals(accountType.getTypeModel()))
                {
                    CCYAccount ccyAccount = (CCYAccount)init_account;
                    signStr = CurrencyAccountSignUtil.getSign(ccyAccount);
                }
                else
                {
                    CardAccount cardAccount = (CardAccount)init_account;
                    signStr = CardAccountSignUtil.getSign(cardAccount);
                }
                logger.debug("account 签名前sign值:[" + init_account.getSign() + "]");
                init_account.setSign(signStr);
                accountDao.save(init_account);
                logger.debug("account 签名后sign值:[" + init_account.getSign() + "]");
            }
            else
            {
                ApplicationException ae = new ApplicationException("identity101055");
                throw ExceptionUtil.throwException(ae);
            }
        }catch(RpcException e){
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            ApplicationException ae = new ApplicationException("identity101057",
                new String[] {ExceptionUtil.getStackTraceAsString(e)}, e);
            throw ExceptionUtil.throwException(ae);
        }
    }

    /**
     * 在修改账户状态时调用，用来验证账户状态变更是否合理
     * 
     * @param account
     * @param originalAccountStatus
     * @return
     * @see
     */
    public boolean updateAccountStatuCheck(AccountModelType accountModelType, String status,
                                           String originalAccountStatus)
    {
        logger.debug("账户状态检查 开始，accountModelType:[" + accountModelType.toString() + "] status:["
                     + status + "] originalAccountStatus[" + originalAccountStatus + "]");
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.AccountStatusDefenders.ORIGINAL_ACCOUNT_STATUS,
            new SearchFilter(EntityConstant.AccountStatusDefenders.ORIGINAL_ACCOUNT_STATUS,
                Operator.EQ, originalAccountStatus));
        filters.put(EntityConstant.AccountStatusDefenders.TARGET_ACCOUNT_STATUS, new SearchFilter(
            EntityConstant.AccountStatusDefenders.TARGET_ACCOUNT_STATUS, Operator.EQ, status));
        filters.put(EntityConstant.AccountStatusDefenders.TYPE_MODEL,
            new SearchFilter(EntityConstant.AccountStatusDefenders.TYPE_MODEL, Operator.EQ,
                accountModelType.toString()));
        Specification<AccountStatusTransfer> spec = DynamicSpecifications.bySearchFilter(
            filters.values(), AccountStatusTransfer.class);
        AccountStatusTransfer orderStatusDefenders = accountStatusDefendersDao.findOne(spec);
        if (BeanUtils.isNotNull(orderStatusDefenders))
        {
            logger.debug("账户状态检查 结束 true");
            return true;
        }
        else
        {
            logger.debug("账户状态检查 结束 false");
            return false;
        }
    }
}