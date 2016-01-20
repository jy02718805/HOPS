/*
 * 文件名：AccountVoConvertorImpl.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月15日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.account.convertor.impl;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.account.convertor.TransactionHistoryVoConvertor;
import com.yuecheng.hops.account.entity.Account;
import com.yuecheng.hops.account.entity.AccountType;
import com.yuecheng.hops.account.entity.TransactionHistory;
import com.yuecheng.hops.account.entity.role.IdentityAccountRole;
import com.yuecheng.hops.account.entity.vo.TransactionHistoryVo;
import com.yuecheng.hops.account.repository.AccountDao;
import com.yuecheng.hops.account.service.AccountTypeService;
import com.yuecheng.hops.account.service.CCYAccountService;
import com.yuecheng.hops.account.service.IdentityAccountRoleService;
import com.yuecheng.hops.account.service.impl.AccountDaoFinder;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.entity.sp.SP;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.identity.service.sp.SpService;


/**
 * 交易历史记录VO转换工具类
 * 
 * @author Administrator
 * @version 2014年10月16日
 * @see TransactionHistoryVoConvertorImpl
 * @since
 */
@Service("transactionHistoryVoConvertor")
public class TransactionHistoryVoConvertorImpl implements TransactionHistoryVoConvertor
{
    private static Logger logger = LoggerFactory.getLogger(TransactionHistoryVoConvertorImpl.class);

    @Autowired
    private CCYAccountService ccyAccountService;

    @Autowired
    private IdentityAccountRoleService identityAccountRoleService;

    @Autowired
    private AccountTypeService accountTypeService;

    @Autowired
    private SpService spService;

    @Autowired
    private MerchantService merchantService;
    
    @Autowired
    private AccountDaoFinder accountDaoFinder;

    @Override
    public TransactionHistoryVo execute(TransactionHistory transactionHistory)
    {
        logger.debug("转换VO对象 [开始] TransactionHistory[" + transactionHistory + "]");
        TransactionHistoryVo transactionHistoryVo = new TransactionHistoryVo();
        try
        {
            BeanUtils.copyProperties(transactionHistoryVo, transactionHistory);
            transactionHistoryVo.setCreateDate(transactionHistory.getCreateDate());
        }
        catch (Exception e)
        {
            return transactionHistoryVo;
        }
        List<String> strList = new ArrayList<String>();
        // 收款
        Long payerAccountId = transactionHistoryVo.getPayerAccountId();
        strList = getCcyPayerName(payerAccountId, transactionHistory.getPayerAccountTypeId(), Long.valueOf(transactionHistory.getTransactionNo()));
        if (strList != null && strList.size() > 0)
        {
            transactionHistoryVo.setPayerName(strList.get(0));
            transactionHistoryVo.setPayerTypeName(strList.get(1));
            transactionHistoryVo.setPayerAccountType(transactionHistory.getPayerAccountTypeId().toString());
        }
        // 付款
        Long payeeAccountId = transactionHistoryVo.getPayeeAccountId();
        strList = getCcyPayerName(payeeAccountId, transactionHistory.getPayeeAccountTypeId(), Long.valueOf(transactionHistory.getTransactionNo()));
        if (strList != null && strList.size() > 0)
        {
            transactionHistoryVo.setPayeeName(strList.get(0));
            transactionHistoryVo.setPayeeTypeName(strList.get(1));
            transactionHistoryVo.setPayeeAccountType(transactionHistory.getPayeeAccountTypeId().toString());
        }

        logger.debug("转换VO对象 [开始] transactionHistoryVo[" + transactionHistoryVo + "]");
        return transactionHistoryVo;
    }

    /**
     * 根据账户ID，查询对应账户名称。
     * 
     * @param accountId
     * @return
     * @see
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public List<String> getCcyPayerName(Long accountId, Long accountTypeId, Long transactionNo)
    {
        List<String> strList = new ArrayList<String>();
        AccountType accountType = accountTypeService.queryAccountTypeById(accountTypeId);
        AccountDao accountDao = accountDaoFinder.getByModelType(accountType, transactionNo);
        Account account = (Account)accountDao.findOne(accountId);
        IdentityAccountRole identityAccountRole = identityAccountRoleService.queryIdentityAccountRoleByParames(
        		account.getAccountId(), Constant.Account.ACCOUNT_RELATION_TYPE_OWN);
        String identityType = identityAccountRole.getIdentityType();
        Long identityId = identityAccountRole.getIdentityId();
        if (identityType.equals(IdentityType.SP.toString()))
        {
            SP sp = spService.getSP();
            strList.add(sp.getSpName());
        }
        else if (identityType.equals(IdentityType.MERCHANT.toString()))
        {
            Merchant merchant = merchantService.queryMerchantById(identityId);
            strList.add(merchant.getMerchantName());
        }
        strList.add(accountType.getAccountTypeName());
        return strList;
    }
}
