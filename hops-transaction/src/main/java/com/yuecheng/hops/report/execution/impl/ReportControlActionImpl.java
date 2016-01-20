/*
 * 文件名：ReportControlActionImpl.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月21日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.report.execution.impl;


import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.account.entity.bo.AccountBalanceHistoryBo;
import com.yuecheng.hops.account.service.CurrencyAccountBalanceHistoryService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.report.entity.AccountReportInfo;
import com.yuecheng.hops.report.entity.AccountReportRecord;
import com.yuecheng.hops.report.entity.AgentTransactionReportInfo;
import com.yuecheng.hops.report.entity.ProfitReportInfo;
import com.yuecheng.hops.report.entity.ProfitReportRecord;
import com.yuecheng.hops.report.entity.SupplyTransactionReportInfo;
import com.yuecheng.hops.report.entity.TransactionReportInfo;
import com.yuecheng.hops.report.entity.TransactionReportRecord;
import com.yuecheng.hops.report.execution.AccountReportAction;
import com.yuecheng.hops.report.execution.MerchantTransactionReportAcion;
import com.yuecheng.hops.report.execution.ProfitReportAction;
import com.yuecheng.hops.report.execution.ReportControlAction;
import com.yuecheng.hops.report.execution.TransactionReportAction;
import com.yuecheng.hops.report.service.AccountReportRecordService;
import com.yuecheng.hops.report.service.AccountReportService;
import com.yuecheng.hops.report.service.AgentTransactionReportService;
import com.yuecheng.hops.report.service.ProfitReportRecordService;
import com.yuecheng.hops.report.service.ProfitReportService;
import com.yuecheng.hops.report.service.SupplyTransactionReportService;
import com.yuecheng.hops.report.service.TransactionReportRecordService;
import com.yuecheng.hops.report.service.TransactionReportService;
import com.yuecheng.hops.report.tool.ReportTool;


/**
 * 报表控制表
 * 
 * @author Administrator
 * @version 2014年10月23日
 * @see ReportControlActionImpl
 * @since
 */
@Service("reportControlAction")
public class ReportControlActionImpl implements ReportControlAction
{

    @Autowired
    private ProfitReportRecordService profitReportControlService;

    @Autowired
    private TransactionReportRecordService transactionReportControlService;

    @Autowired
    private ProfitReportAction profitReportAction;

    @Autowired
    private TransactionReportAction transactionReportAction;

    @Autowired
    private MerchantTransactionReportAcion merchantTransactionReportAcion;

    @Autowired
    private ProfitReportService profitReportsService;

    @Autowired
    private TransactionReportService transactionReportService;

    @Autowired
    private SupplyTransactionReportService supplyTransactionReportService;

    @Autowired
    private AgentTransactionReportService agentTransactionReportService;

    @Autowired
    private AccountReportRecordService accountReportControlService;

    @Autowired
    private AccountReportAction accountReportAction;

    @Autowired
    private CurrencyAccountBalanceHistoryService currencyAccountHistoryService;

    @Autowired
    private AccountReportService accountReportService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportControlActionImpl.class);

    @Override
    public void reCensusProfitReport()
    {

        LOGGER.debug("进入利润报表控制表重新统计方法: [ReportControlActionImpl: censusProfitReportControl()] ");
        // 重新统计 查询出记录状态不为成功的
        List<ProfitReportRecord> trcList = profitReportControlService.queryProfitReportRecordsList(Constant.RecordStatus.SUCCESS);

        try
        {
            for (ProfitReportRecord profitReportControl : trcList)
            {

                List<ProfitReportInfo> profitReports = profitReportAction.censusProfitReport(
                    profitReportControl.getBeginDate(), profitReportControl.getEndDate(),
                    profitReportControl.getMerchantType());
                profitReportsService.saveProfitReports(profitReports);
                saveProfitReportControl(profitReportControl);
            }
        }
        catch (Exception e)
        {
            throw new ApplicationContextException(
                "利润报表控制表重新统计方法: [ReportControlActionImpl: censusProfitReportControl() ] 异常: "
                    + ExceptionUtil.getStackTraceAsString(e), e);
        }
        LOGGER.debug("结束利润报表控制表重新统计方法: [ReportControlActionImpl: censusProfitReportControl()] ");
    }

    @Override
    public void reCensusTransactionReport()
    {
        // TODO Auto-generated method stub
        LOGGER.debug("进入交易量报表控制表重新统计方法: [ReportControlActionImpl: censusTransactionReportControl()] ");
        // 重新统计
        List<TransactionReportRecord> trcList = transactionReportControlService.queryTransactionReportRecordsList(
            Constant.RecordStatus.SUCCESS, Constant.TransactionReportRecord.TYPE_TRANSACTION);
        try
        {
            for (TransactionReportRecord transactionReportControl : trcList)
            {
                List<TransactionReportInfo> transactionReports = transactionReportAction.censusTransactionReports(
                    transactionReportControl.getBeginDate(),
                    transactionReportControl.getEndDate(),
                    transactionReportControl.getMerchantType());
                transactionReportService.saveTransactionReports(transactionReports);
                transactionReportControlService.saveTransactionReportRecord(transactionReportControl);
            }
        }
        catch (Exception e)
        {
            throw new ApplicationContextException(
                "交易量报表控制表重新统计方法: [ReportControlActionImpl: censusTransactionReportControl() ] 异常: "
                    + ExceptionUtil.getStackTraceAsString(e), e);
        }
        LOGGER.debug("结束交易量报表控制表重新统计方法: [ReportControlActionImpl: censusTransactionReportControl()] ");
    }

    @Override
    public void reCensusSupplyTransactionReport()
    {
        LOGGER.debug("进入供货商报表控制表重新统计方法: [ReportControlActionImpl: censusSupplyTransactionReportControl()] ");

        // 重新统计 查询出记录状态不为成功的
        List<TransactionReportRecord> trcList = transactionReportControlService.queryTransactionReportRecordsList(
            Constant.RecordStatus.SUCCESS, Constant.TransactionReportRecord.TYPE_SUPPLY);
        try
        {
            for (TransactionReportRecord transactionReportControl : trcList)
            {
                @SuppressWarnings("unchecked")
                List<SupplyTransactionReportInfo> supplyTransactionReports = (List<SupplyTransactionReportInfo>)merchantTransactionReportAcion.censusTransactionReports(
                    transactionReportControl.getBeginDate(),
                    transactionReportControl.getEndDate(),
                    transactionReportControl.getMerchantType());

                supplyTransactionReportService.saveSupplyTransactionReports(supplyTransactionReports);
                transactionReportControlService.saveTransactionReportRecord(transactionReportControl);
            }
        }
        catch (Exception e)
        {
            throw new ApplicationContextException(
                "供货商报表控制表重新统计方法: [ReportControlActionImpl: censusSupplyTransactionReportControl() ] 异常: "
                    + ExceptionUtil.getStackTraceAsString(e), e);
        }
        LOGGER.debug("结束供货商报表控制表重新统计方法: [ReportControlActionImpl: censusSupplyTransactionReportControl()] ");
    }

    @Override
    public void reCensusAgentTransactionReport()
    {
        LOGGER.debug("进入代理商报表控制表重新统计方法: [ReportControlActionImpl: censusAgentTransactionReportControl()] ");
        // 重新统计
        List<TransactionReportRecord> trcList = transactionReportControlService.queryTransactionReportRecordsList(
            Constant.RecordStatus.SUCCESS, Constant.TransactionReportRecord.TYPE_AGENT);
        try
        {
            for (TransactionReportRecord transactionReportControl : trcList)
            {
                @SuppressWarnings("unchecked")
                List<AgentTransactionReportInfo> agentTransactionReports = (List<AgentTransactionReportInfo>)merchantTransactionReportAcion.censusTransactionReports(
                    transactionReportControl.getBeginDate(),
                    transactionReportControl.getEndDate(),
                    transactionReportControl.getMerchantType());

                agentTransactionReportService.saveAgentTransactionReports(agentTransactionReports);
                transactionReportControlService.saveTransactionReportRecord(transactionReportControl);
            }
        }
        catch (Exception e)
        {
            throw new ApplicationContextException(
                "代理商报表控制表重新统计方法: [ReportControlActionImpl: censusAgentTransactionReportControl() ] 异常: "
                    + ExceptionUtil.getStackTraceAsString(e), e);
        }
        LOGGER.debug("结束代理商报表控制表重新统计方法: [ReportControlActionImpl: censusAgentTransactionReportControl()] ");
    }

    @Override
    public void reCensusAccountReport()
    {
        LOGGER.debug("进入账户报表控制表重新统计方法: [ReportControlActionImpl: censusAgentTransactionReportControl()] ");
        // 重新统计
        List<AccountReportRecord> arcList = accountReportControlService.queryAccountReportControlList(Constant.RecordStatus.SUCCESS);
        try
        {
            for (AccountReportRecord accountReportRecord : arcList)
            {

                List<AccountBalanceHistoryBo> abList = currencyAccountHistoryService.queryAccountReportBos(
                    ReportTool.getBeginTime(), ReportTool.getEndTime());
                List<AccountReportInfo> accountReports = accountReportAction.getAccountReports(
                    abList, accountReportRecord.getBeginDate(), accountReportRecord.getEndDate());
                accountReportService.saveAccountReports(accountReports);
                saveAccountReportControl(accountReportRecord);
            }
        }
        catch (Exception e)
        {
            throw new ApplicationContextException(
                "账户报表控制表重新统计方法: [ReportControlActionImpl: censusAgentTransactionReportControl() ] 异常: "
                    + ExceptionUtil.getStackTraceAsString(e), e);
        }
        LOGGER.debug("结束账户报表控制表重新统计方法: [ReportControlActionImpl: censusAgentTransactionReportControl()] ");

    }

    public void saveProfitReportControl(ProfitReportRecord profitReportControl)
    {
        profitReportControl.setUpdateDate(new Date());
        profitReportControl.setReportStatus(Constant.RecordStatus.SUCCESS);
        profitReportControlService.saveProfitReportRecord(profitReportControl);
    }

    public void saveTransactionReportControl(TransactionReportRecord transactionReportControl)
    {
        transactionReportControl.setUpdateDate(new Date());
        transactionReportControl.setReportStatus(Constant.RecordStatus.SUCCESS);
        transactionReportControlService.saveTransactionReportRecord(transactionReportControl);
    }

    public void saveAccountReportControl(AccountReportRecord accountReportControl)
    {
        accountReportControl.setUpdateDate(new Date());
        accountReportControl.setReportStatus(Constant.RecordStatus.SUCCESS);
        accountReportControlService.saveAccountReportRecord(accountReportControl);
    }

}
