package com.yuecheng.hops.report.schedule.quartz;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuecheng.hops.report.execution.ProfitReportAction;


/**
 * 利润统计每日定时任务
 * 
 * @author Administrator
 * @version 2014年10月13日
 * @see PofitReportAgentServiceJob
 * @since
 */
public class PofitReportAgentServiceJob
{
    private ProfitReportAction profitReportAction;

    private static final Logger LOGGER = LoggerFactory.getLogger(PofitReportAgentServiceJob.class);

    protected void execute()
    {
        LOGGER.info("开始执行定时任务:[PofitReportDataServiceJob.execute()] [调用profitReportsService中setMetaData()]");
        profitReportAction.censusProfitReportByAgent();
        LOGGER.info("结束执行定时任务:[PofitReportDataServiceJob.execute()] [调用profitReportsService中setMetaData()]");
    }

    public ProfitReportAction getProfitReportAction()
    {
        return profitReportAction;
    }

    public void setProfitReportAction(ProfitReportAction profitReportAction)
    {
        this.profitReportAction = profitReportAction;
    }
}
