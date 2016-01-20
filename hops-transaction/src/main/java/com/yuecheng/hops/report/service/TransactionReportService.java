package com.yuecheng.hops.report.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.report.entity.ReportProperty;
import com.yuecheng.hops.report.entity.TransactionReportInfo;
import com.yuecheng.hops.report.entity.bo.TransactionReportBo;
import com.yuecheng.hops.report.entity.po.TransactionReportPo;


/**
 * 交易量统计服务
 * 
 * @author Administrator
 * @version 2014年10月27日
 * @see TransactionReportService
 * @since
 */
public interface TransactionReportService
{

    /**
     * 保存
     * 
     * @param tr
     * @return
     * @see
     */
    TransactionReportInfo saveTransactionReport(TransactionReportInfo tr);

    /**
     * 统计每天交易量
     * 
     * @param beginTime
     * @param endTime
     * @return
     * @see
     */
    List<TransactionReportBo> censusTransactionInfos(Date beginTime, Date endTime,
                                                     String merchantType);

    /**
     * 批量保存
     * 
     * @param transactionReports
     * @return
     * @see
     */
    List<TransactionReportInfo> saveTransactionReports(List<TransactionReportInfo> transactionReports);

    

    /**
     * 通过条件查询数据
     * 
     * @return
     */
    YcPage<TransactionReportPo> queryTransactionReports(Map<String, Object> searchParams,
                                                        List<ReportProperty> rpList,
                                                        String beginTime, String endTime,
                                                        int pageNumber, int pageSize,
                                                        String sortType);

    /**
     * 测试
     */
    void testTransactionReport(String begin, String end);
}
