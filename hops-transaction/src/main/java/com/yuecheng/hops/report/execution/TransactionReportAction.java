package com.yuecheng.hops.report.execution;


import java.util.Date;
import java.util.List;

import com.yuecheng.hops.report.entity.TransactionReportInfo;


/**
 * 交易量报表统计
 * 
 * @author Administrator
 * @version 2014年10月23日
 * @see TransactionReportAction
 * @since
 */
public interface TransactionReportAction
{
    /**
     * 统计代理商户各个产品交易量
     * 
     * @see
     */
    void censusAgentTransactionReport();

    /**
     * 统计供货商各个产品交易量
     * 
     * @see
     */
    void censusSupplyTransactionReport();

    /**
     * 统计交易报表
     * 
     * @param beginTime
     * @param endTime
     * @return
     * @see
     */
    List<TransactionReportInfo> censusTransactionReports(Date beginTime, Date endTime,
                                                         String merchantType);

    
    /**
     * 统计代理商户各个产品交易量测试
     * 
     * @see
     */
    void censusAgentTransactionReportTest(String beginTime,String endTime);
    void censusSupplyTransactionReportTest(String beginTime,String endTime);

}