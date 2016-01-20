package com.yuecheng.hops.report.schedule.quartz;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuecheng.hops.report.execution.ReportControlAction;


/**
 * 交易量统计每日定时任务
 * 
 * @author Administrator
 * @version 2014年10月13日
 * @see ReportRecordServiceJob
 * @since
 */
public class ReportRecordServiceJob
{
    private ReportControlAction reportControlAction;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportRecordServiceJob.class);

    public ReportControlAction getReportControlAction()
    {
        return reportControlAction;
    }

    public void setReportControlAction(ReportControlAction reportControlAction)
    {
        this.reportControlAction = reportControlAction;
    }

    protected void execute()
    {
        // TODO Auto-generated method stub
        // 起线程
//        LOGGER.debug("[进入生成交易量报表中]");
//        reportControlAction.reCensusTransactionReport();
//        LOGGER.debug("[结束生成交易量报表]");
//        LOGGER.debug("[进入生成代理商户交易量报表中]");
//        reportControlAction.reCensusAgentTransactionReport();
//        LOGGER.debug("[结束生成代理商户交易量报表]");
//        LOGGER.debug("[进入生成供货商户交易量报表中]");
//        reportControlAction.reCensusSupplyTransactionReport();;
//        LOGGER.debug("[结束生成供货商户交易量报表]");
//        LOGGER.debug("[进入生成利润报表中]");
//        reportControlAction.reCensusProfitReport();
//        LOGGER.debug("[结束生成利润报表]");
//        LOGGER.debug("[进入生成账户报表中]");
//        reportControlAction.reCensusAccountReport();
//        LOGGER.debug("[结束生成账户报表中]");
    }
}
