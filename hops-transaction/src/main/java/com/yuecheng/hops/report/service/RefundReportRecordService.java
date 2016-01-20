/*
 * 文件名：TransactionReportControlService.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月17日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.report.service;


import java.util.Date;

import com.yuecheng.hops.report.entity.RefundReportRecord;


/**
 * 利润报表控制服务
 * 
 * @author Administrator
 * @version 2014年10月23日
 * @see RefundReportRecordService
 * @since
 */
public interface RefundReportRecordService
{
    /**
     * 保存控制
     * 
     * @param RefundReportControl
     * @return
     * @see
     */
    RefundReportRecord saveRefundReportRecord(RefundReportRecord refundReportControl);

    /**
     * 查询利润报表中不为成功的记录
     * 
     * @param status
     * @return
     * @see
     */

    RefundReportRecord queryRefundReportRecord(String merchantType, Date recordDate);

    RefundReportRecord queryRefundReportRecordByStatus(String merchantType, Date recordDate,
                                                       String status1, String status2);

    RefundReportRecord updateRefundReportRecord(String merchantType, String status,
                                                String describe, Date recordDate);
}
