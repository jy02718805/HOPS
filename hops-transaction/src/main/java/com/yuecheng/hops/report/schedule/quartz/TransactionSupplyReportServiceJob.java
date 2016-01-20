package com.yuecheng.hops.report.schedule.quartz;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuecheng.hops.report.execution.TransactionReportAction;


/**
 * 交易量统计每日定时任务
 * 
 * @author Administrator
 * @version 2014年10月13日
 * @see TransactionSupplyReportServiceJob
 * @since
 */
public class TransactionSupplyReportServiceJob
{
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionSupplyReportServiceJob.class);

    private TransactionReportAction transactionReportAction;


    public TransactionReportAction getTransactionReportAction()
    {
        return transactionReportAction;
    }

    public void setTransactionReportAction(TransactionReportAction transactionReportAction)
    {
        this.transactionReportAction = transactionReportAction;
    }

    protected void execute()
    {
        LOGGER.info("开始执行定时任务:[TransactionSupplyReportServiceJob.execute()] [调用transactionReportsService中setMetaData()]");
        // 起线程
        transactionReportAction.censusSupplyTransactionReport();
        LOGGER.info("结束执行定时任务:[TransactionSupplyReportServiceJob.execute()] [调用transactionReportsService中setMetaData()]");
    }
}
