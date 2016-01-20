package com.yuecheng.hops.report.schedule.quartz;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuecheng.hops.report.execution.TransactionReportAction;


/**
 * 交易量统计每日定时任务
 * 
 * @author Administrator
 * @version 2014年10月13日
 * @see TransactionAgentReportServiceJob
 * @since
 */
public class TransactionAgentReportServiceJob
{
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionAgentReportServiceJob.class);

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
        // TODO Auto-generated method stub
        LOGGER.info("开始执行定时任务:[TransactionAgentReportServiceJob.execute()]");
        // 起线程
        transactionReportAction.censusAgentTransactionReport();
        LOGGER.info("结束执行定时任务:[TransactionAgentReportServiceJob.execute()]");
    }
}
