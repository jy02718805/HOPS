/*
 * 文件名：ReportDao.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator 修改时间：2014年10月15日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.report.repository;


import java.util.Date;

import com.yuecheng.hops.report.entity.RefundReportRecord;


public interface RefundReportRecordDao
{
    RefundReportRecord queryRefundReportRecord(String merchantType, Date imputationDate);

    RefundReportRecord queryRefundReportRecordByStatus(String merchantType, Date imputationDate,
                                                       String status1, String status2);
}
