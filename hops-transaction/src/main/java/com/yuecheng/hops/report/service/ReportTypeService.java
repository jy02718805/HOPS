package com.yuecheng.hops.report.service;


import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.report.entity.ReportMetadata;
import com.yuecheng.hops.report.entity.ReportTerm;
import com.yuecheng.hops.report.entity.ReportType;


/**
 * 报表类型服务
 * 
 * @author Administrator
 * @version 2014年10月23日
 * @see ReportTypeService
 * @since
 */
public interface ReportTypeService
{

    /**
     * 通过条件查询数据
     * 
     * @return
     */
    YcPage<ReportType> queryReportTypes(Map<String, Object> searchParams, int pageNumber,
                                        int pageSize, String sortType);

    /**
     * 获得报表类型
     * 
     * @return
     */
    ReportType getReportType(Long reportTypeId);

    List<ReportType> getReportTypeByType(String reportMetadataType);

    /**
     * 更新ReportType
     * 
     * @return
     */
    ReportType saveReportType(ReportType rt);

    /**
     * 获得查询条件
     */
    List<ReportMetadata> getmetadataByType(String type);

    List<ReportMetadata> getAllmetadataByType(String type);

    /**
     * 获得已设置查询条件
     */
    List<ReportTerm> getReportTermsByReportTypeId(Long reportType);

    /**
     * 获得查询条件
     */
    ReportTerm getReportTermsByMetadataId(Long reportMetadata, Long reportTypeId);

    /**
     * 更新ReportType
     * 
     * @return
     */
    ReportTerm saveReportTerms(ReportTerm rt);

    /**
     * 获得查询信息
     */
    ReportMetadata getmetadataById(Long id);

    /**
     * 验证报表名称
     */
    boolean reportNameIsRepeat(String reportFileName, String reportMetadataType);

}
