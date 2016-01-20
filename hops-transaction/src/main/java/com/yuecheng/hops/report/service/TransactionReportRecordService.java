/*
 * 修改时间：2014年10月17日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.report.service;


import java.util.Date;
import java.util.List;

import com.yuecheng.hops.report.entity.TransactionReportRecord;


/**
 * 交易量控制表服务
 * 
 * @author Administrator
 * @version 2014年10月23日
 * @see TransactionReportRecordService
 * @since
 */
public interface TransactionReportRecordService
{
    /**
     * 保存
     * 
     * @param transactionReportControl
     * @return
     * @see
     */
    TransactionReportRecord saveTransactionReportRecord(TransactionReportRecord transactionReportControl);

    /**
     * 查询交易量报表中不为成功的记录
     * 
     * @param status
     * @param type
     * @return
     * @see
     */
    List<TransactionReportRecord> queryTransactionReportRecordsList(String status, String type);
    
    TransactionReportRecord queryTransactionReportRecord(String merchantType, String reportType,
                                                         Date recordDate);
    TransactionReportRecord updateTransactionReportRecord(String reportType,String merchantType,String recordStatus, String recordDescribe,Date recordDate);

}
