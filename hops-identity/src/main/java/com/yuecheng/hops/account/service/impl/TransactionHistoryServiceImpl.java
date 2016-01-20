package com.yuecheng.hops.account.service.impl;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.yuecheng.hops.account.convertor.TransactionHistoryVoConvertor;
import com.yuecheng.hops.account.entity.TransactionHistory;
import com.yuecheng.hops.account.entity.vo.TransactionHistoryAssistVo;
import com.yuecheng.hops.account.entity.vo.TransactionHistoryVo;
import com.yuecheng.hops.account.repository.TransactionHistoryDao;
import com.yuecheng.hops.account.repository.TransactionHistoryJpaDao;
import com.yuecheng.hops.account.service.IdentityAccountRoleService;
import com.yuecheng.hops.account.service.TransactionHistoryService;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.YcPage;


@Service("transactionHistoryService")
public class TransactionHistoryServiceImpl implements TransactionHistoryService, Serializable
{
    private static final long serialVersionUID = -1868105124576863686L;

    private static Logger logger = LoggerFactory.getLogger(TransactionHistoryServiceImpl.class);

    @Autowired
    private TransactionHistoryDao transactionHistoryDao;

    @Autowired
    private TransactionHistoryJpaDao transactionHistoryJpaDao;

    @Autowired
    private TransactionHistoryVoConvertor transactionHistoryVoConvertor;

    @Autowired
    private IdentityAccountRoleService identityAccountRoleService;

    @Override
    public List<TransactionHistoryVo> getTransactionHistoryByTransactionNo(String transactionNo)
    {
        List<TransactionHistory> transactionHistorylist = transactionHistoryDao.queryTransactionHistoryByTransactionNo(transactionNo);
        List<TransactionHistoryVo> result = new ArrayList<TransactionHistoryVo>();
        for (TransactionHistory transactionHistory : transactionHistorylist)
        {
            result.add(transactionHistoryVoConvertor.execute(transactionHistory));
        }
        return result;
    }

    @Override
    public YcPage<TransactionHistoryAssistVo> queryTransactionHistoryList(Map<String, Object> searchParams,
                                                                          int pageNumber,
                                                                          int pageSize,
                                                                          String sortType)
    {
        YcPage<TransactionHistoryAssistVo> ycPage = transactionHistoryDao.queryTransactionHistoryList(
            searchParams, pageNumber, pageSize);
        return ycPage;
    }

    @Override
    @Transactional
    public TransactionHistory saveTransactionHistory(TransactionHistory th)
    {
        try
        {
            logger.debug("保存账户交易记录  [开始] TransactionHistory" + th);
            // 获得付款人、收款人名称
            String payerIdentityName = identityAccountRoleService.queryIdentityNameByAccountId(th.getPayerAccountId());
            String payeeIdentityName = identityAccountRoleService.queryIdentityNameByAccountId(th.getPayeeAccountId());

            th.setPayerIdentityName(payerIdentityName);
            th.setPayeeIdentityName(payeeIdentityName);

            th = transactionHistoryJpaDao.save(th);
            logger.debug("保存账户交易记录  [结束]");
            return th;
        }
        catch (Exception e)
        {
            throw ExceptionUtil.throwException(new ApplicationException("identity101067",
                new String[] {ExceptionUtil.getStackTraceAsString(e)}));
        }
    }

    @Override
    @Transactional
    public TransactionHistory updateIsRefundByParams(TransactionHistory th, Long isRefund)
    {
        try
        {
            logger.debug("编辑交易记录是否退款标示  [开始] TransactionHistory" + String.valueOf(th).toString());
            Assert.notNull(th);
            th.setIsRefund(isRefund);
            th = transactionHistoryJpaDao.save(th);
            logger.debug("编辑交易记录是否退款标示  [结束]");
            return th;
        }
        catch (Exception e)
        {
            throw ExceptionUtil.throwException(new ApplicationException("identity101082",
                new String[] {ExceptionUtil.getStackTraceAsString(e)}));
        }
    }

    @Override
    public TransactionHistory getTransactionHistoryById(Long transactionId)
    {
        TransactionHistory th = transactionHistoryJpaDao.findOne(transactionId);
        return th;
    }

    @Override
    @Transactional
    public List<TransactionHistory> queryNoRefundTransactionHistoryByTransactionNo(String transactionNo)
    {
        List<TransactionHistory> transactionHistorylist = transactionHistoryDao.queryNoRefundTransactionHistoryByTransactionNo(transactionNo);
        return transactionHistorylist;
    }
    
    @Override
    @Transactional
    public Boolean queryTransactionHistoryByParams(String transactionNo,String type)
    {
        List<TransactionHistory> transactionHistorylist = transactionHistoryDao.queryTransactionHistoryByParams(transactionNo, type);
        if(transactionHistorylist.size() > 0){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public BigDecimal getTransactionHistoryOfAmtSum(Long accoutId, String logType, Date beginTime,
                                                    Date endTime)
    {
        BigDecimal amtSum = transactionHistoryDao.getTransactionHistoryOfAmtSum(accoutId, logType,
            beginTime, endTime);
        return amtSum;
    }

}
