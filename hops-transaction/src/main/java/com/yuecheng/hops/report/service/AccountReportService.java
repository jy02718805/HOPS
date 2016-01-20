package com.yuecheng.hops.report.service;


import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.report.entity.AccountReportInfo;
import com.yuecheng.hops.report.entity.ReportProperty;
import com.yuecheng.hops.report.entity.po.AccountReportPo;


/**
 * 账户统计 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author Administrator
 * @version 2014年10月23日
 * @see AccountReportService
 * @since
 */
public interface AccountReportService
{
    /**
     * 通过条件查询数据
     * 
     * @return
     */
    YcPage<AccountReportPo> queryAccountReports(Map<String, Object> searchParams,
                                                List<ReportProperty> rpList, String beginTime,
                                                String endTime, int pageNumber, int pageSize,
                                                String sortType);

    /**
     * 保存报表
     * 
     * @return
     */
    AccountReportInfo saveAccountReport(AccountReportInfo ar);

    /**
     * 批量保存
     * 
     * @return
     */
    List<AccountReportInfo> saveAccountReports(List<AccountReportInfo> accountReports);

    /**
     * 测试
     */
    void testReport(String begin, String end);
}
