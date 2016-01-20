/*
 * 修改时间：2014年10月17日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.report.service;


import java.util.Date;
import java.util.List;

import com.yuecheng.hops.report.entity.AccountReportRecord;


/**
 * 账户控制服务
 * 
 * @author Administrator
 * @version 2014年10月23日
 * @see AccountReportRecordService
 * @since
 */
public interface AccountReportRecordService
{
    /**
     * 保存
     * 
     * @param accountReportControl
     * @return
     * @see
     */
    AccountReportRecord saveAccountReportRecord(AccountReportRecord accountReportControl);

    /**
     * 查询出状态不为成功的记录
     * 
     * @param status
     * @return
     * @see
     */
    List<AccountReportRecord> queryAccountReportControlList(String status);

    AccountReportRecord queryAccountReportRecord(Date recordDate);

    AccountReportRecord queryAccountReportRecordByStatus(Date recordDate);

    AccountReportRecord updateAccountReportRecord(String recordStatus, String recordDescribe,Date recordDate);
}
