package com.yuecheng.hops.transaction.service.scanner;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.DateUtil;
import com.yuecheng.hops.transaction.config.entify.profitImputation.ProfitImputationInfo;
import com.yuecheng.hops.transaction.config.entify.profitImputation.ProfitImputationRecord;
import com.yuecheng.hops.transaction.execution.imputation.ProfitImputationService;
import com.yuecheng.hops.transaction.service.profitImputation.ProfitImputationInfoService;
import com.yuecheng.hops.transaction.service.profitImputation.ProfitImputationRecordService;


@Service("profitImputationScanner")
public class ProfitImputationScanner
{
    @Autowired
    private ProfitImputationInfoService profitImputationInfoService;

    @Autowired
    private ProfitImputationRecordService profitImputationRecordService;

    @Autowired
    private ProfitImputationService profitImputationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfitImputationScanner.class);

    public void taskProfitImputation()
    {
        LOGGER.debug("[ProfitImputationScanner: taskProfitImputation] [进入每日利润归集定时任务]");

        ProfitImputationRecord profitImputationRecord = new ProfitImputationRecord();
        try
        {
            profitImputationRecord = profitImputationRecordService.queryProfitImputationRecord(getSubTime(getImputationDate()));
            if (BeanUtils.isNull(profitImputationRecord))
            {
                profitImputationRecord = new ProfitImputationRecord();
                // 1.初始化归集记录
                profitImputationRecord = profitImputationRecordService.saveProfitImputationRecord(
                    profitImputationRecord, Constant.RecordStatus.INITIALIZATION, "初始化");
            }

            if (!Constant.RecordStatus.SUCCESS.equals(profitImputationRecord.getRecordStatus())
                && !Constant.RecordStatus.PENDING.equals(profitImputationRecord.getRecordStatus()))
            {

                profitImputationRecord = profitImputationRecordService.updateProfitImputationRecord(
                    Constant.RecordStatus.PENDING, "处理中");
                if (BeanUtils.isNull(profitImputationRecord))
                {
                    throw new Exception("利润归集状态修改重复!");
                }
                // 2.归集利润到中间利润账户
                List<ProfitImputationInfo> profitImputations = profitImputationService.imputationProfit(
                    getSubTime(DateUtil.getYesterdayBeginTime()),
                    getSubTime(DateUtil.getYesterdayEndTime()));

                // 3 保存到归集控制表
                profitImputationInfoService.saveProfitImputations(profitImputations);
                // 4.记录归集成功
                profitImputationRecordService.saveProfitImputationRecord(profitImputationRecord,
                    Constant.RecordStatus.SUCCESS, "账户利润归集完成");
            }
        }
        catch (HopsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            LOGGER.error("[ProfitImputationServiceImpl: taskProfitImputation] [利润归集定时任务异常] "
                         + ExceptionUtil.getStackTraceAsString(e));
            profitImputationRecordService.saveProfitImputationRecord(profitImputationRecord,
                Constant.RecordStatus.FAILURE, "账户利润归集失败 ");
            throw new ApplicationException("imputation0000001",
                new String[] {ExceptionUtil.getStackTraceAsString(e)}, e);
        }
        LOGGER.debug("[ProfitImputationServiceImpl: taskProfitImputation] [结束每日利润归集定时任务]");
    }

    public Date getImputationDate()
    {
        Calendar currentDate = new GregorianCalendar();
        currentDate.add(Calendar.DATE, -1);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        return (Date)currentDate.getTime().clone();
    }

    public Date getSubTime(Date dd)
    {
        Calendar currentDate = new GregorianCalendar();
        currentDate.setTime(dd);
        // currentDate.add(Calendar.DATE, -3);
        currentDate.add(Calendar.HOUR_OF_DAY, -72);
        return (Date)currentDate.getTime().clone();
    }
}
