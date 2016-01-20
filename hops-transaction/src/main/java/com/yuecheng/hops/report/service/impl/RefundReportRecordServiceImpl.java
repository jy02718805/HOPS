/*
 * 文件名：TransactionReportControlServiceImpl.java 版权：Copyright by www.365haoyou.com 描述：
 * 修改人：Administrator 修改时间：2014年10月17日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.report.service.impl;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.report.entity.RefundReportRecord;
import com.yuecheng.hops.report.repository.RefundReportRecordDao;
import com.yuecheng.hops.report.repository.jpa.RefundReportRecordJpaDao;
import com.yuecheng.hops.report.service.RefundReportRecordService;
import com.yuecheng.hops.report.tool.ReportTool;


/**
 * 利润记录服务
 * 
 * @author Administrator
 * @version 2014年10月23日
 * @see RefundReportRecordServiceImpl
 * @since
 */
@Service("refundReportControlService")
public class RefundReportRecordServiceImpl implements RefundReportRecordService
{
    @Autowired
    private RefundReportRecordJpaDao refundReportRecordJpaDao;

    @Autowired
    private RefundReportRecordDao RefundReportRecordDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public RefundReportRecord saveRefundReportRecord(RefundReportRecord refundReportRecord)
    {
        refundReportRecord = refundReportRecordJpaDao.save(refundReportRecord);
        return refundReportRecord;
    }


    @Override
    public RefundReportRecord queryRefundReportRecord(String merchantType, Date recordDate)
    {
        // TODO Auto-generated method stub
        RefundReportRecord RefundReportRecord = RefundReportRecordDao.queryRefundReportRecord(
            merchantType, recordDate);
        return RefundReportRecord;
    }

    @Override
    public RefundReportRecord queryRefundReportRecordByStatus(String merchantType,
                                                              Date recordDate, String status1,
                                                              String status2)
    {
        // TODO Auto-generated method stub
        RefundReportRecord RefundReportRecord = RefundReportRecordDao.queryRefundReportRecordByStatus(
            merchantType, recordDate, status1, status2);
        return RefundReportRecord;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public RefundReportRecord updateRefundReportRecord(String merchantType, String status,
                                                       String describe, Date recordDate)
    {
        RefundReportRecord RefundReportRecord = RefundReportRecordDao.queryRefundReportRecordByStatus(
            merchantType, recordDate, Constant.RecordStatus.INITIALIZATION,
            Constant.RecordStatus.FAILURE);
        RefundReportRecord.setBeginDate(ReportTool.getBeginTime());
        RefundReportRecord.setEndDate(ReportTool.getEndTime());
        RefundReportRecord.setMerchantType(merchantType);
        RefundReportRecord.setUpdateDate(new Date());
        RefundReportRecord.setReportStatus(status);
        RefundReportRecord.setReportDescribe(describe);
        RefundReportRecord = refundReportRecordJpaDao.save(RefundReportRecord);
        return RefundReportRecord;
    }
}
