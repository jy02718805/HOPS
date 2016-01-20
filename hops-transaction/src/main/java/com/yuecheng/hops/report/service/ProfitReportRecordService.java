/*
 * 文件名：TransactionReportControlService.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月17日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.report.service;


import java.util.Date;
import java.util.List;

import com.yuecheng.hops.report.entity.ProfitReportRecord;


/**
 * 利润报表控制服务
 * 
 * @author Administrator
 * @version 2014年10月23日
 * @see ProfitReportRecordService
 * @since
 */
public interface ProfitReportRecordService
{
    /**
     * 保存控制
     * 
     * @param profitReportControl
     * @return
     * @see
     */
    ProfitReportRecord saveProfitReportRecord(ProfitReportRecord profitReportControl);

    /**
     * 查询利润报表中不为成功的记录
     * 
     * @param status
     * @return
     * @see
     */
    List<ProfitReportRecord> queryProfitReportRecordsList(String status);

    ProfitReportRecord queryProfitReportRecord(String merchantType, Date recordDate);

    ProfitReportRecord queryProfitReportRecordByStatus(String merchantType, Date recordDate);

    ProfitReportRecord updateProfitReportRecord(String merchantType, String status, String describe,Date recordDate);
}
