/*
 * 文件名：TransactionReportControlServiceImpl.java 版权：Copyright by www.365haoyou.com 描述：
 * 修改人：Administrator 修改时间：2014年10月17日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.report.service.impl;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.report.entity.AccountReportRecord;
import com.yuecheng.hops.report.repository.AccountReportRecordDao;
import com.yuecheng.hops.report.repository.jpa.AccountReportRecordJpaDao;
import com.yuecheng.hops.report.service.AccountReportRecordService;

/**
 * 账户统计记录〉
 * 
 * @author Administrator
 * @version 2014年10月27日
 * @see AccountReportRecordServiceImpl
 * @since
 */
@Service("accountReportControlService")
public class AccountReportRecordServiceImpl implements AccountReportRecordService
{
    @Autowired
    private AccountReportRecordDao accountReportRecordDao;

    @Autowired
    private AccountReportRecordJpaDao accountReportRecordJpaDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public AccountReportRecord saveAccountReportRecord(AccountReportRecord accountReportRecord)
    {
        accountReportRecord = accountReportRecordJpaDao.save(accountReportRecord);
        return accountReportRecord;
    }

    @Override
    public List<AccountReportRecord> queryAccountReportControlList(String status)
    {
        List<AccountReportRecord> records = accountReportRecordJpaDao.queryAccountReportControlList(status);
        return records;
    }

    @Override
    public AccountReportRecord queryAccountReportRecord(Date recordDate)
    {
        // TODO Auto-generated method stub
        AccountReportRecord accountReportRecord = accountReportRecordDao.queryAccountReportRecord(recordDate);
        return accountReportRecord;
    }

    @Override
    public AccountReportRecord queryAccountReportRecordByStatus(Date recordDate)
    {
        // TODO Auto-generated method stub
        AccountReportRecord accountReportRecord = accountReportRecordDao.queryAccountReportRecordByStatus(recordDate);
        return accountReportRecord;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public AccountReportRecord updateAccountReportRecord(String recordStatus,
                                                         String recordDescribe,Date recordDate)
    {
        AccountReportRecord ard = accountReportRecordDao.queryAccountReportRecordByStatus(recordDate);
        ard.setUpdateDate(new Date());
        ard.setReportStatus(recordStatus);
        ard.setReportDescribe(recordDescribe);
        ard = accountReportRecordJpaDao.save(ard);
        return ard;
    }

}
