/*
 * 文件名：ReportControlAction.java 版权： www.365haoyou.com 描述： 修改人：Administrator 修改时间：2014年10月18日 跟踪单号：
 * 修改单号： 修改内容：
 */

package com.yuecheng.hops.report.execution;

/**
 * 报表控制表操作
 * 
 * @author Administrator
 * @version 2014年10月23日
 * @see ReportControlAction
 * @since
 */
public interface ReportControlAction
{
    /**
     * 定时任务：重新统计利润报表
     * 
     * @see
     */
    void reCensusProfitReport();

    /**
     * 定时任务：重新统计交易量报表
     * 
     * @see
     */
    void reCensusTransactionReport();

    /**
     * 定时任务：重新统计供货商报表
     * 
     * @see
     */
    void reCensusSupplyTransactionReport();

    /**
     * 定时任务：重新统计代理商报表
     * 
     * @see
     */
    void reCensusAgentTransactionReport();

    /**
     * 定时任务：重新统计账户报表
     * 
     * @see
     */
    void reCensusAccountReport();
}
