/*
 * 文件名：ProfitImputationRecordServiceImpl.java 版权：Copyright by www.365haoyou.com 描述：
 * 修改人：Administrator 修改时间：2014年10月24日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.transaction.service.profitImputation.impl;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.transaction.config.entify.profitImputation.ProfitImputationRecord;
import com.yuecheng.hops.transaction.config.repository.ProfitImputationRecordDao;
import com.yuecheng.hops.transaction.config.repository.jpa.ProfitImputationRecordJpaDao;
import com.yuecheng.hops.transaction.service.profitImputation.ProfitImputationRecordService;


@Service("profitImputationRecordService")
public class ProfitImputationRecordServiceImpl implements ProfitImputationRecordService
{
    @Autowired
    private ProfitImputationRecordJpaDao profitImputationRecordJpaDao;

    @Autowired
    private ProfitImputationRecordDao profitImputationRecordDao;

    @Override
    public List<ProfitImputationRecord> queryProfitImputationRecordList(String recordStatus)
    {
        return profitImputationRecordJpaDao.queryProfitImputationRecordList(recordStatus);
    }

    @Override
    public ProfitImputationRecord saveProfitImputationRecord(ProfitImputationRecord profitImputationRecord)
    {
        try
        {
            profitImputationRecord = profitImputationRecordJpaDao.save(profitImputationRecord);
        }
        catch (Exception e)
        {
            throw new ApplicationContextException(
                "[利润归集控制保存失败!][ProfitImputationRecordServiceImpl.saveProfitImputationRecord(profitImputationRecord))]"
                    + ExceptionUtil.getStackTraceAsString(e), e);
        }
        return profitImputationRecord;
    }

    @Override
    public ProfitImputationRecord queryProfitImputationRecord(Date imputationDate)
    {
        return profitImputationRecordDao.queryProfitImputationRecord(imputationDate);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ProfitImputationRecord saveProfitImputationRecord(ProfitImputationRecord profitImputationRecord,
                                                             String recordStatus,
                                                             String recordDescribe)
    {

        profitImputationRecord.setRecordStatus(recordStatus);
        profitImputationRecord.setRecordBeginDateate(getBeginTime());
        profitImputationRecord.setRecordEndDateate(getEndTime());
        profitImputationRecord.setRecordUpdateDate(new Date());
        profitImputationRecord.setRecordDescribe(recordDescribe);
        profitImputationRecord = saveProfitImputationRecord(profitImputationRecord);
        return profitImputationRecord;
    }

    /**
     * 开始时间
     * 
     * @return
     * @see
     */
    public static Date getBeginTime()
    {
        Calendar currentDate = new GregorianCalendar();
        currentDate.add(Calendar.DATE, -4);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        return (Date)currentDate.getTime().clone();
    }

    /**
     * 结束时间
     * 
     * @return
     * @see
     */
    public static Date getEndTime()
    {
        Calendar currentDate = new GregorianCalendar();
        currentDate.add(Calendar.DATE, -4);
        currentDate.set(Calendar.HOUR_OF_DAY, 23);
        currentDate.set(Calendar.MINUTE, 59);
        currentDate.set(Calendar.SECOND, 59);
        return (Date)currentDate.getTime().clone();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ProfitImputationRecord updateProfitImputationRecord(String recordStatus,
                                                               String recordDescribe)
    {

        ProfitImputationRecord profitImputationRecord = profitImputationRecordDao.queryProfitImputationRecordByStatus(getBeginTime());
        profitImputationRecord.setRecordStatus(recordStatus);
        profitImputationRecord.setRecordBeginDateate(getBeginTime());
        profitImputationRecord.setRecordEndDateate(getEndTime());
        profitImputationRecord.setRecordUpdateDate(new Date());
        profitImputationRecord.setRecordDescribe(recordDescribe);
        profitImputationRecord = saveProfitImputationRecord(profitImputationRecord);
        return profitImputationRecord;
    }
}
