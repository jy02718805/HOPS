package com.yuecheng.hops.report.execution;


import java.util.Date;
import java.util.List;

import com.yuecheng.hops.account.entity.bo.AccountBalanceHistoryBo;
import com.yuecheng.hops.report.entity.AccountReportInfo;


/**
 * 账户报表统计 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author Administrator
 * @version 2014年10月23日
 * @see AccountReportAction
 * @since
 */
public interface AccountReportAction
{
    /**
     * 统计虚拟账户
     * 
     * @see
     */
    public void censusCurrencyAccountReport();

    /**
     * 统计卡账户
     * 
     * @see
     */
    public void censusCardAccountReport();

    /**
     * 统计记录账户信息
     * 
     * @param accountIdList
     * @return
     * @see
     */
    public List<AccountReportInfo> getAccountReports(List<AccountBalanceHistoryBo> abList,
                                                     Date beginDate, Date endDate);

    /**
     * 统计虚拟账户测试
     * 
     * @see
     */
    public void censusCurrencyAccountReportTest(String beginTime, String endTime);

}