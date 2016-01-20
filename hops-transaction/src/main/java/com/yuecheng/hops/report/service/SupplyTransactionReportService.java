package com.yuecheng.hops.report.service;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.report.entity.ReportProperty;
import com.yuecheng.hops.report.entity.SupplyTransactionReportInfo;
import com.yuecheng.hops.report.entity.bo.MerchantTransactionReportBo;
import com.yuecheng.hops.report.entity.po.SupplyTransactionReportPo;


/**
 * 供货商报表服务 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author Administrator
 * @version 2014年10月23日
 * @see SupplyTransactionReportService
 * @since
 */
public interface SupplyTransactionReportService
{

    /**
     * 保存
     * 
     * @return
     */
    SupplyTransactionReportInfo saveSupplyTransactionReport(SupplyTransactionReportInfo supplyTransactionReport);

    /**
     * 一个商户所有子商户的所有产品在某一段时间内的交易量(总面值和)
     * 
     * @param merchantId
     * @param downMerchantIdList
     * @param productIdList
     * @param beginDate
     * @param endDate
     * @return
     */
    BigDecimal getSupplyReportTransaction(Long merchantId, Long productId, Date beginDate,
                                          Date endDate);

    List<SupplyTransactionReportInfo> getSupplyTransactionReports(Long merchantId, Long productId,
                                                                  Date beginDate, Date endDate);

    /**
     * 统计每天交易量
     * 
     * @param beginTime
     * @param endTime
     * @return
     * @see
     */
    List<MerchantTransactionReportBo> censusMerchantTransactionInfo(Date beginTime, Date endTime);

    /**
     * 批量保存
     * 
     * @param supplyTransactionReports
     * @return
     * @see
     */
    List<SupplyTransactionReportInfo> saveSupplyTransactionReports(List<SupplyTransactionReportInfo> supplyTransactionReports);

    
    
    /**
     * 通过条件查询数据
     * 
     * @return
     */
    YcPage<SupplyTransactionReportPo> querySupplyTransactionReports(Map<String, Object> searchParams,
                                                                    List<ReportProperty> rpList,
                                                                    String beginTime,
                                                                    String endTime,
                                                                    int pageNumber, int pageSize,
                                                                    String sortType);

    /**
     * 测试
     */
    void testReport(String begin, String end);

}
