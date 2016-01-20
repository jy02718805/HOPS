package com.yuecheng.hops.report.repository;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.report.entity.ReportProperty;
import com.yuecheng.hops.report.entity.bo.MerchantTransactionReportBo;
import com.yuecheng.hops.report.entity.po.AgentTransactionReportPo;


public interface AgentTransactionReportDao
{
    List<MerchantTransactionReportBo> getMerchantTransactionInfos(Date beginTime, Date endTime);

    YcPage<AgentTransactionReportPo> queryAgentTransactionReports(Map<String, Object> searchParams,
                                                                  List<ReportProperty> rpList,
                                                                  String beginTime,
                                                                  String endTime, int pageNumber,
                                                                  int pageSize, String sortType);
    BigDecimal getSumAgentTransaction(Long merchantId, Long productId, Date beginDate,
                                      Date endDate);
}
