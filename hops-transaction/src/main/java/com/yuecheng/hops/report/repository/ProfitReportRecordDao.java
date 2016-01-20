/*
 * 文件名：ReportDao.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator 修改时间：2014年10月15日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.report.repository;


import java.util.Date;

import com.yuecheng.hops.report.entity.ProfitReportRecord;


public interface ProfitReportRecordDao
{
    ProfitReportRecord queryProfitReportRecord(String merchantType, Date imputationDate);

    ProfitReportRecord queryProfitReportRecordByStatus(String merchantType, Date imputationDate);
}
