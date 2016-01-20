package com.yuecheng.hops.report.schedule.quartz;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuecheng.hops.report.execution.AccountReportAction;


/**
 * 账户统计每日定时任务
 * 
 * @author Administrator
 * @version 2014年10月13日
 * @see AccountReportServiceJob
 * @since
 */
public class AccountReportServiceJob
{
    private AccountReportAction accountReportAction;

    public AccountReportAction getAccountReportAction()
    {
        return accountReportAction;
    }

    public void setAccountReportAction(AccountReportAction accountReportAction)
    {
        this.accountReportAction = accountReportAction;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountReportServiceJob.class);

    public void execute()
    {
        LOGGER.info("开始执行定时任务:[AccountReportServiceJob.execute()] [调用accountReportsService中setMetaData()]");
    //    accountReportAction.censusCurrencyAccountReport();

        // accountReportAction.censusCardAccountReport();
        LOGGER.info("结束执行定时任务:[AccountReportServiceJob.execute()] [调用accountReportsService中setMetaData()]");
    }
}
