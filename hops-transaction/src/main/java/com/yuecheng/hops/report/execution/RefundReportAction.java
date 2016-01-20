package com.yuecheng.hops.report.execution;


import java.util.Date;
import java.util.List;

import com.yuecheng.hops.report.entity.RefundReportInfo;


/**
 * 利润报表统计 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author Administrator
 * @version 2014年10月23日
 * @see RefundReportAction
 * @since
 */
public interface RefundReportAction
{
    /**
     * 统计代理商户各个产品利润
     * 
     * @see
     */
    public void censusRefundReportByAgent();

    /**
     * 统计供货商户各个产品利润
     * 
     * @see
     */
    public void censusRefundReportBySupply();

    /**
     * 统计利润
     * 
     * @param beginTime
     * @param endTime
     * @return
     * @see
     */
    public List<RefundReportInfo> censusRefundReport(Date beginTime, Date endTime,
                                                     String merchatType);

    /**
     * 测试
     * 
     * @see
     */
    public void censusRefundReportByAgentTest(String beginTime, String endTime);
    // public void censusRefundReportBySupplyTest(String beginTime, String endTime);
    public void censusRefundReportBySupplyTest(String beginTime, String endTime);
}