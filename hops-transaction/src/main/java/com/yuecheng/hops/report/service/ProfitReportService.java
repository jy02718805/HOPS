package com.yuecheng.hops.report.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.report.entity.ProfitReportInfo;
import com.yuecheng.hops.report.entity.ReportProperty;
import com.yuecheng.hops.report.entity.bo.ProfitReportBo;
import com.yuecheng.hops.report.entity.po.ProfitReportPo;


/**
 * 利润报表统计服务 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author Administrator
 * @version 2014年10月23日
 * @see ProfitReportService
 * @since
 */
public interface ProfitReportService
{

    /**
     * 保存利润报表
     * 
     * @param pr
     * @return
     * @see
     */
    ProfitReportInfo saveProfitReport(ProfitReportInfo pr);

    /**
     * 统计每天利润
     * 
     * @param beginTime
     * @param endTime
     * @return
     * @see
     */
    List<ProfitReportBo> censusProfitInfos(Date beginTime, Date endTime, String merchatType);

    /**
     * 批量保存
     * 
     * @param profitReports
     * @return
     * @see
     */
    List<ProfitReportInfo> saveProfitReports(List<ProfitReportInfo> profitReports);

    /**
     * 通过条件查询数据
     * 
     * @return
     */
    public YcPage<ProfitReportPo> queryProfitReports(Map<String, Object> searchParams,
                                                     List<ReportProperty> rpList,
                                                     String beginTime, String endTime,
                                                     int pageNumber, int pageSize, String sortType);

    /**
     * 测试
     */
    void testReport(String begin, String end);
}
