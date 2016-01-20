package com.yuecheng.hops.report.service;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.report.entity.AgentTransactionReportInfo;
import com.yuecheng.hops.report.entity.ReportProperty;
import com.yuecheng.hops.report.entity.bo.MerchantTransactionReportBo;
import com.yuecheng.hops.report.entity.po.AgentTransactionReportPo;


/**
 * 代理商户交易量报表
 * 
 * @author Administrator
 * @version 2014年10月23日
 * @see AgentTransactionReportService
 * @since
 */
public interface AgentTransactionReportService
{

    /**
     * 保存
     * 
     * @param agentTransactionReport
     * @return
     * @see
     */
    AgentTransactionReportInfo saveAgentTransactionReport(AgentTransactionReportInfo agentTransactionReport);


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
    BigDecimal getAgentReportTransaction(Long merchantId, Long rebateProductId, Date beginDate,
                                         Date endDate);

    List<AgentTransactionReportInfo> getAgentTransactionReports(Long merchantId, Long productId,
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
     * @param agentTransactionReport
     * @return
     * @see
     */
    List<AgentTransactionReportInfo> saveAgentTransactionReports(List<AgentTransactionReportInfo> agentTransactionReports);

    /**
     * 通过条件查询数据
     * 
     * @return
     */
    YcPage<AgentTransactionReportPo> queryAgentTransactionReports(Map<String, Object> searchParams,
                                                                  List<ReportProperty> rpList,
                                                                  String beginTime,
                                                                  String endTime, int pageNumber,
                                                                  int pageSize, String sortType);

    /**
     * 测试
     */
    void testReport(String begin, String end);
}
