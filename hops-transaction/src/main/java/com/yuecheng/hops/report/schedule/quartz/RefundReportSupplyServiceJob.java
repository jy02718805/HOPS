package com.yuecheng.hops.report.schedule.quartz;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuecheng.hops.report.execution.RefundReportAction;


/**
 * 利润统计每日定时任务
 * 
 * @author Administrator
 * @version 2014年10月13日
 * @see RefundReportSupplyServiceJob
 * @since
 */
public class RefundReportSupplyServiceJob
{
    private static final Logger LOGGER = LoggerFactory.getLogger(RefundReportSupplyServiceJob.class);
    private RefundReportAction refundReportAction;
    
    public RefundReportAction getRefundReportAction()
    {
        return refundReportAction;
    }

    public void setRefundReportAction(RefundReportAction RefundReportAction)
    {
        this.refundReportAction = RefundReportAction;
    }

    protected void execute()
    {

        LOGGER.info("开始执行定时任务:[PofitReportSupplyServiceJob.execute()]");
        refundReportAction.censusRefundReportBySupply();
        LOGGER.info("结束执行定时任务:[PofitReportSupplyServiceJob.execute()]");
    }
}
