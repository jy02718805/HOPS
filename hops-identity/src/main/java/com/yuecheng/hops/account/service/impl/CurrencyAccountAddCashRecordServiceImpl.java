/*
 * 文件名：CurrencyAccountAddCashRecordServiceImpl.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2014年11月7日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.account.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.account.entity.CCYAccount;
import com.yuecheng.hops.account.entity.CurrencyAccountAddCashRecord;
import com.yuecheng.hops.account.entity.vo.CurrencyAccountAddCashRecordVo;
import com.yuecheng.hops.account.repository.CurrencyAccountAddCashRecordJpaDao;
import com.yuecheng.hops.account.repository.CurrencyAccountAddCashRecordSqlDao;
import com.yuecheng.hops.account.service.AccountTransferService;
import com.yuecheng.hops.account.service.CCYAccountService;
import com.yuecheng.hops.account.service.CurrencyAccountAddCashRecordService;
import com.yuecheng.hops.account.service.IdentityAccountRoleService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.AccountDirectoryType;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.identity.entity.sp.SP;
import com.yuecheng.hops.identity.service.sp.SpService;

@Service("currencyAccountAddCashRecordService")
public class CurrencyAccountAddCashRecordServiceImpl implements CurrencyAccountAddCashRecordService
{
    @Autowired
    private CurrencyAccountAddCashRecordJpaDao currencyAccountAddCashRecordJpaDao;
    
    @Autowired
    private CurrencyAccountAddCashRecordSqlDao currencyAccountAddCashRecordSqlDao;
    
    @Autowired
    private CCYAccountService ccyAccountService;
    
    @Autowired
    private AccountTransferService accountTransferService;
    
    @Autowired
    private IdentityAccountRoleService identityAccountRoleService;
    
    @Autowired
    private SpService spService;
    
    @Override
    public CurrencyAccountAddCashRecord saveCurrencyAccountAddCashRecord(CurrencyAccountAddCashRecord caar)
    {
        caar = currencyAccountAddCashRecordJpaDao.save(caar);
        return caar;
    }

    @Override
    @Transactional
    public void verify(Long id, Integer status, BigDecimal amt)
    {
        try
        {
            CurrencyAccountAddCashRecord caar = currencyAccountAddCashRecordJpaDao.findOne(id);
            caar.setVerifyTime(new Date());
            caar.setVerifyStatus(status);
            caar.setSuccessAmt(amt);
            currencyAccountAddCashRecordJpaDao.save(caar);
            CCYAccount currencyAccount = (CCYAccount)identityAccountRoleService.getAccountByParams(Constant.AccountType.MERCHANT_DEBIT, caar.getMerchantId(), IdentityType.MERCHANT.toString(), Constant.Account.ACCOUNT_RELATION_TYPE_OWN, null);
            
            SP sp = spService.getSP();
            //外部账户
            CCYAccount externalAccount = (CCYAccount)identityAccountRoleService.getAccountNoCache(
                Constant.AccountType.EXTERNAL_ACCOUNT, sp.getId(), IdentityType.SP.toString(),
                Constant.Account.ACCOUNT_RELATION_TYPE_OWN, null);
            
            String desc = "审核     账户：" + currencyAccount.getAccountId() + "进行加款，金额为[" + amt + "]。备注:[加款审核]";
            Long transactionNo = new Date().getTime();
            
            if(Constant.AddCashStatus.VERIFY_SUCCESS == status)
            {
                //审核成功 加款
                if (AccountDirectoryType.CREDIT.equals(currencyAccount.getAccountType().getDirectory()))
                {
                    accountTransferService.doTransfer(currencyAccount.getAccountId(), currencyAccount.getAccountType().getAccountTypeId(), externalAccount.getAccountId(), externalAccount.getAccountType().getAccountTypeId(), amt, desc, Constant.TransferType.TRANSFER_ADD_CASH, transactionNo);
                }
                else if (AccountDirectoryType.DEBIT.equals(currencyAccount.getAccountType().getDirectory()))
                {
                    accountTransferService.doTransfer(externalAccount.getAccountId(), externalAccount.getAccountType().getAccountTypeId(), currencyAccount.getAccountId(), currencyAccount.getAccountType().getAccountTypeId(), amt, desc, Constant.TransferType.TRANSFER_ADD_CASH, transactionNo);
                }
            }
        }catch(RpcException e){
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            throw ExceptionUtil.throwException(new ApplicationException("", e));
        }
    }

    @Override
    public CurrencyAccountAddCashRecord findOne(Long id)
    {
        CurrencyAccountAddCashRecord caar = currencyAccountAddCashRecordJpaDao.findOne(id);
        return caar;
    }

    @Override
    public YcPage<CurrencyAccountAddCashRecord> findCurrencyAccountAddCashRecordByParams(CurrencyAccountAddCashRecordVo currencyAccountAddCashRecordVo,
                                                                                         int pageNumber,
                                                                                         int pageSize,
                                                                                         String sortType)
    {
        YcPage<CurrencyAccountAddCashRecord> ycPage = currencyAccountAddCashRecordSqlDao.findCurrencyAccountAddCashRecordByParams(currencyAccountAddCashRecordVo, pageNumber, pageSize, sortType);
//        YcPage<CurrencyAccountAddCashRecord> ycPage = PageUtil.queryYcPage(currencyAccountAddCashRecordJpaDao, filters, pageNumber,
//            pageSize, new Sort(Direction.DESC, sortType),
//            CurrencyAccountAddCashRecord.class);
        return ycPage;
    }
}
