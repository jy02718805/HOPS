package com.yuecheng.hops.report.repository;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.report.entity.ReportProperty;
import com.yuecheng.hops.report.entity.bo.TransactionReportBo;
import com.yuecheng.hops.report.entity.po.TransactionReportPo;


/**
 * 交易量报表Dao 〈功能详细描述〉
 * 
 * @author Administrator
 * @version 2014年11月4日
 * @see TransactionReportDao
 * @since
 */
public interface TransactionReportDao
{
    /**
     * 获得每日统计的数据
     * 
     * @param beginTime
     * @param endTime
     * @param merchantType
     * @return
     * @see
     */
    public List<TransactionReportBo> getTransactionReportBos(Date beginTime, Date endTime,
                                                             String merchantType);

    /**
     * 分页统计
     * 
     * @param searchParams
     * @param beginTime
     * @param endTime
     * @param pageNumber
     * @param pageSize
     * @param sortType
     * @return
     * @see
     */
    YcPage<TransactionReportPo> queryTransactionReports(Map<String, Object> searchParams,
                                                        List<ReportProperty> rpList,
                                                        String beginTime, String endTime,
                                                        int pageNumber, int pageSize,
                                                        String sortType);
}
