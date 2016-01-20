package com.yuecheng.hops.report.repository;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.report.entity.ReportProperty;
import com.yuecheng.hops.report.entity.bo.RefundReportBo;
import com.yuecheng.hops.report.entity.po.RefundReportPo;


public interface RefundReportDao
{
    // 统计每天的商户 产品属性 利润
    public List<RefundReportBo> getRefundReportBos(Date beginDate, Date endDate,
                                                    String merchatType);

    /**
     * 分页查询
     * 
     * @param searchParams
     * @param beginTime
     * @param endTime
     * @param reportsStatus
     * @param pageNumber
     * @param pageSize
     * @param sortType
     * @return
     * @see
     */
    YcPage<RefundReportPo> queryRefundReports(Map<String, Object> searchParams,
                                               List<ReportProperty> rpList, String beginTime,
                                               String endTime, int pageNumber, int pageSize,
                                               String sortType);
}
