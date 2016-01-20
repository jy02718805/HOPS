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

import com.yuecheng.hops.report.entity.ProfitReportRecord;
import com.yuecheng.hops.report.repository.ProfitReportRecordDao;
import com.yuecheng.hops.report.repository.jpa.ProfitReportRecordJpaDao;
import com.yuecheng.hops.report.service.ProfitReportRecordService;
import com.yuecheng.hops.report.tool.ReportTool;


/**
 * 利润记录服务
 * 
 * @author Administrator
 * @version 2014年10月23日
 * @see ProfitReportRecordServiceImpl
 * @since
 */
@Service("profitReportControlService")
public class ProfitReportRecordServiceImpl implements ProfitReportRecordService
{
    @Autowired
    private ProfitReportRecordJpaDao profitReportRecordJpaDao;

    @Autowired
    private ProfitReportRecordDao profitReportRecordDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ProfitReportRecord saveProfitReportRecord(ProfitReportRecord profitReportRecord)
    {
        profitReportRecord = profitReportRecordJpaDao.save(profitReportRecord);
        return profitReportRecord;
    }

    @Override
    public List<ProfitReportRecord> queryProfitReportRecordsList(String status)
    {
        // TODO Auto-generated method stub
        List<ProfitReportRecord> prcList = profitReportRecordJpaDao.queryProfitReportRecordList(status);
        return prcList;
    }

    @Override
    public ProfitReportRecord queryProfitReportRecord(String merchantType, Date recordDate)
    {
        // TODO Auto-generated method stub
        ProfitReportRecord profitReportRecord = profitReportRecordDao.queryProfitReportRecord(
            merchantType, recordDate);
        return profitReportRecord;
    }

    @Override
    public ProfitReportRecord queryProfitReportRecordByStatus(String merchantType, Date recordDate)
    {
        // TODO Auto-generated method stub
        ProfitReportRecord profitReportRecord = profitReportRecordDao.queryProfitReportRecordByStatus(
            merchantType, recordDate);
        return profitReportRecord;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ProfitReportRecord updateProfitReportRecord(String merchantType, String status,
                                                       String describe, Date recordDate)
    {
        ProfitReportRecord profitReportRecord = profitReportRecordDao.queryProfitReportRecordByStatus(
            merchantType, recordDate);
        profitReportRecord.setBeginDate(ReportTool.getBeginTime());
        profitReportRecord.setEndDate(ReportTool.getEndTime());
        profitReportRecord.setMerchantType(merchantType);
        profitReportRecord.setUpdateDate(new Date());
        profitReportRecord.setReportStatus(status);
        profitReportRecord.setReportDescribe(describe);
        profitReportRecord = profitReportRecordJpaDao.save(profitReportRecord);
        return profitReportRecord;
    }

}
