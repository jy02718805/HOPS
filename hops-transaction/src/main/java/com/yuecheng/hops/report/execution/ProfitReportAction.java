package com.yuecheng.hops.report.execution;


import java.util.Date;
import java.util.List;

import com.yuecheng.hops.report.entity.ProfitReportInfo;


/**
 * 利润报表统计 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author Administrator
 * @version 2014年10月23日
 * @see ProfitReportAction
 * @since
 */
public interface ProfitReportAction
{
    /**
     * 统计代理商户各个产品利润
     * 
     * @see
     */
    public void censusProfitReportByAgent();

    /**
     * 统计供货商户各个产品利润
     * 
     * @see
     */
    public void censusProfitReportBySupply();

    /**
     * 统计利润
     * 
     * @param beginTime
     * @param endTime
     * @return
     * @see
     */
    public List<ProfitReportInfo> censusProfitReport(Date beginTime, Date endTime,String merchatType);

    
    /**
     * 测试
     * @see
     */
    public void censusProfitReportByAgentTest(String beginTime, String endTime);
   // public void censusProfitReportBySupplyTest(String beginTime, String endTime);

}