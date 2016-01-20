package com.yuecheng.hops.report.schedule.quartz;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuecheng.hops.report.execution.MerchantTransactionReportAcion;


/**
 * 代理商交易统计每日定时任务
 * 
 * @author Administrator
 * @version 2014年10月13日
 * @see AgentTransactionReportServiceJob
 * @since
 */
public class AgentTransactionReportServiceJob
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AgentTransactionReportServiceJob.class);

    private MerchantTransactionReportAcion merchantTransactionReportAcion;

    public MerchantTransactionReportAcion getMerchantTransactionReportAcion()
    {
        return merchantTransactionReportAcion;
    }

    public void setMerchantTransactionReportAcion(MerchantTransactionReportAcion merchantTransactionReportAcion)
    {
        this.merchantTransactionReportAcion = merchantTransactionReportAcion;
    }

    protected void execute()
    {
        // TODO Auto-generated method stub
        LOGGER.info("开始执行定时任务:[AgentTransactionReportServiceJob.execute()] [调用agentTransactionReportsService中setMetaData()]");
        merchantTransactionReportAcion.censusAgentTransactionReport();
        LOGGER.info("结束执行定时任务:[AgentTransactionReportServiceJob.execute()] [调用agentTransactionReportsService中setMetaData()]");
    }
}
