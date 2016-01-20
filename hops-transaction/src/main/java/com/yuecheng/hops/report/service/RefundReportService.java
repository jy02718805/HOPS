package com.yuecheng.hops.report.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.report.entity.RefundReportInfo;
import com.yuecheng.hops.report.entity.ReportProperty;
import com.yuecheng.hops.report.entity.bo.RefundReportBo;
import com.yuecheng.hops.report.entity.po.RefundReportPo;


/**
 * 利润报表统计服务 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author Administrator
 * @version 2014年10月23日
 * @see RefundReportService
 * @since
 */
public interface RefundReportService
{

    /**
     * 保存利润报表
     * 
     * @param pr
     * @return
     * @see
     */
    RefundReportInfo saveRefundReport(RefundReportInfo rr);

    /**
     * 统计每天利润
     * 
     * @param beginTime
     * @param endTime
     * @return
     * @see
     */
    List<RefundReportBo> censusRefundInfos(Date beginTime, Date endTime, String merchatType);

    /**
     * 批量保存
     * 
     * @param RefundReports
     * @return
     * @see
     */
    List<RefundReportInfo> saveRefundReports(List<RefundReportInfo> refundReportInfo);

    /**
     * 通过条件查询数据
     * 
     * @return
     */
    public YcPage<RefundReportPo> queryRefundReports(Map<String, Object> searchParams,
                                                     List<ReportProperty> rpList,
                                                     String beginTime, String endTime,
                                                     int pageNumber, int pageSize, String sortType);

    /**
     * 测试
     */
    void testReport(String begin, String end);
}
