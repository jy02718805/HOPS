package com.yuecheng.hops.report.repository;


import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.report.entity.ReportProperty;
import com.yuecheng.hops.report.entity.po.AccountReportPo;


public interface AccountReportDao
{
    public YcPage<AccountReportPo> queryAccountPageReports(Map<String, Object> searchParams,
                                                           List<ReportProperty> rpList,
                                                           String beginTime, String endTime,
                                                           int pageNumber, int pageSize,
                                                           String sortType);
}
