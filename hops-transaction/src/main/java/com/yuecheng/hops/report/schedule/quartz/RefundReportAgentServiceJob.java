package com.yuecheng.hops.report.schedule.quartz;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuecheng.hops.report.execution.RefundReportAction;


/**
 * 利润统计每日定时任务
 * 
 * @author Administrator
 * @version 2014年10月13日
 * @see RefundReportAgentServiceJob
 * @since
 */
public class RefundReportAgentServiceJob
{
    private RefundReportAction refundReportAction;

    private static final Logger LOGGER = LoggerFactory.getLogger(RefundReportAgentServiceJob.class);

    protected void execute()
    {
        LOGGER.info("开始执行定时任务:[PofitReportDataServiceJob.execute()] [调用RefundReportsService中setMetaData()]");
        refundReportAction.censusRefundReportByAgent();
        LOGGER.info("结束执行定时任务:[PofitReportDataServiceJob.execute()] [调用RefundReportsService中setMetaData()]");
    }

    public RefundReportAction getRefundReportAction()
    {
        return refundReportAction;
    }

    public void setRefundReportAction(RefundReportAction refundReportAction)
    {
        this.refundReportAction = refundReportAction;
    }

    
}
