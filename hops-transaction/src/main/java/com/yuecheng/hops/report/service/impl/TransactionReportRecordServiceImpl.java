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

import com.yuecheng.hops.report.entity.TransactionReportRecord;
import com.yuecheng.hops.report.repository.TransactionReportRecordDao;
import com.yuecheng.hops.report.repository.jpa.TransactionReportRecordJpaDao;
import com.yuecheng.hops.report.service.TransactionReportRecordService;
import com.yuecheng.hops.report.tool.ReportTool;


/**
 * 交易量统计记录
 * 
 * @author Administrator
 * @version 2014年10月27日
 * @see TransactionReportRecordServiceImpl
 * @since
 */
@Service("transactionReportRecordService")
public class TransactionReportRecordServiceImpl implements TransactionReportRecordService
{
    @Autowired
    private TransactionReportRecordJpaDao transactionReportRecordJpaDao;

    @Autowired
    private TransactionReportRecordDao transactionReportRecordDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public TransactionReportRecord saveTransactionReportRecord(TransactionReportRecord transactionReportRecord)
    {
        transactionReportRecord = transactionReportRecordJpaDao.save(transactionReportRecord);
        return transactionReportRecord;
    }

    @Override
    public List<TransactionReportRecord> queryTransactionReportRecordsList(String status,
                                                                           String type)
    {

        List<TransactionReportRecord> records = transactionReportRecordJpaDao.queryTransactionReportRecordList(
            status, type);
        return records;
    }

    @Override
    public TransactionReportRecord queryTransactionReportRecord(String merchantType,
                                                                String reportType, Date recordDate)
    {
        TransactionReportRecord transactionReportRecord = transactionReportRecordDao.queryTransactionReportRecord(
            merchantType, reportType, recordDate);
        return transactionReportRecord;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public TransactionReportRecord updateTransactionReportRecord(String reportType,
                                                                 String merchantType,
                                                                 String recordStatus,
                                                                 String recordDescribe,
                                                                 Date recordDate)
    {
        TransactionReportRecord trr = transactionReportRecordDao.queryTransactionReportRecordByStatus(
            merchantType, reportType, recordDate);
        trr.setBeginDate(ReportTool.getBeginTime());
        trr.setEndDate(ReportTool.getEndTime());
        trr.setUpdateDate(new Date());
        trr.setReportStatus(recordStatus);
        trr.setMerchantType(merchantType);
        trr.setReportDescribe(recordDescribe);
        trr.setReportType(reportType);
        trr = transactionReportRecordJpaDao.save(trr);
        return trr;
    }

}
