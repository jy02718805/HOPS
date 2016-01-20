package com.yuecheng.hops.report.service;


import java.util.List;

import com.yuecheng.hops.report.entity.ReportProperty;


/**
 * 报表属性服务
 * 
 * @version 2014年10月23日
 * @see ReportPropertyService
 * @since
 */
public interface ReportPropertyService
{

    /**
     * 通过条件查询数据
     * 
     * @return
     */
    List<ReportProperty> queryReportPropertysByTypeId(Long reportTypeId);

    /**
     * 更新
     * 
     * @return
     */
    ReportProperty saveReportProperty(ReportProperty reportProperty);

    /**
     * 删除
     * 
     * @return
     */
    void delReportProperty(ReportProperty reportProperty);
}
