package com.yuecheng.hops.report.execution.impl;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.account.entity.AccountType;
import com.yuecheng.hops.account.entity.bo.AccountBalanceHistoryBo;
import com.yuecheng.hops.account.entity.role.IdentityAccountRole;
import com.yuecheng.hops.account.service.AccountTypeService;
import com.yuecheng.hops.account.service.CCYAccountService;
import com.yuecheng.hops.account.service.CurrencyAccountBalanceHistoryService;
import com.yuecheng.hops.account.service.IdentityAccountRoleService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.AccountDirectoryType;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.Collections3;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.customer.Customer;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.entity.sp.SP;
import com.yuecheng.hops.identity.service.IdentityService;
import com.yuecheng.hops.identity.service.merchant.MerchantStatusManagement;
import com.yuecheng.hops.report.entity.AccountReportInfo;
import com.yuecheng.hops.report.entity.AccountReportRecord;
import com.yuecheng.hops.report.execution.AccountReportAction;
import com.yuecheng.hops.report.service.AccountReportRecordService;
import com.yuecheng.hops.report.service.AccountReportService;
import com.yuecheng.hops.report.tool.ReportTool;


/**
 * 账户统计功能组件
 * 
 * @author Administrator
 * @version 2014年10月13日
 * @see AccountReportActionImpl
 * @since
 */
@Service("accountReportAction")
public class AccountReportActionImpl implements AccountReportAction
{

    @Autowired
    private IdentityAccountRoleService identityAccountRoleService;

    @Autowired
    private CCYAccountService ccyAccountService;

    @Autowired
    private CurrencyAccountBalanceHistoryService currencyAccountHistoryService;

    @Autowired
    private MerchantStatusManagement merchantService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private AccountReportService accountReportService;

    @Autowired
    private AccountReportRecordService accountReportControlService;

    @Autowired
    private AccountTypeService accountTypeService;

    @Autowired
    private AccountReportRecordService accountReportRecordService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountReportActionImpl.class);

    /**
     * 虚拟账户统计
     */
    @Override
    public void censusCurrencyAccountReport()
    {
        LOGGER.debug("[进入定时扫描到账户报表元数据][AccountReportActionImpl:setAccountReport()]");
        AccountReportRecord accountReportRecord = new AccountReportRecord();

        LOGGER.debug("[定时扫描到账户报表元数据][AccountReportActionImpl: censusCurrencyAccountReport()] [得到所有账户:currencyAccountService.queryAllCurrencyAccount(),currencyAccounts数量:"
                     + "]");
        try
        {
            accountReportRecord = accountReportRecordService.queryAccountReportRecord(ReportTool.getBeginTime());
            if (BeanUtils.isNull(accountReportRecord))
            {
                accountReportRecord = new AccountReportRecord();
                accountReportRecord = saveAccountReportRecord(accountReportRecord,
                    ReportTool.getBeginTime(), ReportTool.getEndTime(),
                    Constant.RecordStatus.INITIALIZATION, "初始化");
            }

            if (!Constant.RecordStatus.SUCCESS.equals(accountReportRecord.getReportStatus())
                && !Constant.RecordStatus.PENDING.equals(accountReportRecord.getReportStatus()))
            {
                accountReportRecord = accountReportRecordService.updateAccountReportRecord(
                    Constant.RecordStatus.PENDING, "处理中", ReportTool.getEndTime());
                if (BeanUtils.isNotNull(accountReportRecord))
                {
                    List<AccountBalanceHistoryBo> abList = currencyAccountHistoryService.queryAccountReportBos(
                        ReportTool.getBeginTime(), ReportTool.getEndTime());

                    List<AccountReportInfo> accountReports = getAccountReports(abList,
                        ReportTool.getBeginTime(), ReportTool.getEndTime());

                    accountReportService.saveAccountReports(accountReports);
                    saveAccountReportRecord(accountReportRecord, ReportTool.getBeginTime(),
                        ReportTool.getEndTime(), Constant.RecordStatus.SUCCESS, "账户报表元数据生成成功");
                }
            }

        }
        catch (Exception e)
        {
            saveAccountReportRecord(accountReportRecord, ReportTool.getBeginTime(),
                ReportTool.getEndTime(), Constant.RecordStatus.FAILURE, "保存账户报表到数据库失败");
            throw new ApplicationContextException(
                "[账户报表元数据保存报错]" + "[AccountReportActionImpl: censusCurrencyAccountReport()]"
                    + ExceptionUtil.getStackTraceAsString(e), e);
        }
        LOGGER.debug("[结束生成账户报表元数据方法][AccountReportActionImpl: censusCurrencyAccountReport()]");
    }

    @Override
    public List<AccountReportInfo> getAccountReports(List<AccountBalanceHistoryBo> abList,
                                                     Date beginDate, Date endDate)
    {
        LOGGER.debug("[获得统计账户报表方法][AccountReportActionImpl: getAccountReports(accountIdList:"
                     + abList != null ? Collections3.convertToString(abList, ",") : StringUtil.initString()
                                                                                    + ",beginDate:"
                                                                                    + beginDate
                                                                                    + ",endDate:"
                                                                                    + endDate
                                                                                    + ")]");
        List<AccountReportInfo> accountReports = new ArrayList<AccountReportInfo>();
        try
        {
            for (AccountBalanceHistoryBo ab : abList)
            {
                AccountReportInfo accountReport = new AccountReportInfo();

                accountReport.setAccountId(ab.getACCOUNTID());
                accountReport.setAccountTypeId(ab.getACCOUNTTYPEID());
                AccountType accountType = accountTypeService.queryAccountTypeById(ab.getACCOUNTTYPEID());
                accountReport.setAccountTypeName(accountType.getAccountTypeName());
                // 保存关于账户的
                saveAccountReportOfIdentity(accountReport);
                // 保存上期余额,本期余额,本期加款,本期支出,本期入账
                AccountBalanceHistoryBo accountBalanceHistoryBo = currencyAccountHistoryService.getAccountReportBo(
                    ab.getACCOUNTID(), beginDate, endDate);
                accountReport.setPreviousBalance(accountBalanceHistoryBo.getPREVIOUSBALANCE());
                accountReport.setPeriodBalance(accountBalanceHistoryBo.getPERIODBALANCE());

                accountReport.setPeriodUnavailableBalance(accountBalanceHistoryBo.getPERIODUNAVAILABLEBALANCE());
                accountReport.setPreviousUnavailableBalance(accountBalanceHistoryBo.getPREVIOUSUNAVAILABLEBALANCE());

                if(ab.getACCOUNTTYPEID().compareTo(Constant.AccountType.MERCHANT_CREDIT)==0)
                {
                    accountReport.setPeriodAddAmt(accountBalanceHistoryBo.getPERIODADDAMTPAYER());
                }else if(ab.getACCOUNTTYPEID().compareTo(Constant.AccountType.MERCHANT_DEBIT)==0)
                {
                    accountReport.setPeriodAddAmt(accountBalanceHistoryBo.getPERIODADDAMTPAYEE());
                }
                
                accountBalanceHistoryBo.setPERIODPLUSSECTION(BeanUtils.isNull(accountBalanceHistoryBo.getPERIODPLUSSECTION()) ? new BigDecimal(
                    0) : accountBalanceHistoryBo.getPERIODPLUSSECTION());

                accountBalanceHistoryBo.setCURRENTREVENUE(BeanUtils.isNull(accountBalanceHistoryBo.getCURRENTREVENUE()) ? new BigDecimal(
                    0) : accountBalanceHistoryBo.getCURRENTREVENUE());

                if (accountType.getDirectory().toString().equals(
                    AccountDirectoryType.CREDIT.toString()))
                {
                    accountReport.setPeriodPlusSection(accountBalanceHistoryBo.getPERIODPLUSSECTION());
                    accountReport.setCurrentExpenditure(accountBalanceHistoryBo.getCURRENTREVENUE());
                }

                else
                {

                    BigDecimal plusAmt = accountBalanceHistoryBo.getPERIODPLUSSECTION().add(
                        accountBalanceHistoryBo.getCURRENTREVENUE());
                    accountReport.setPeriodPlusSection(plusAmt);
                    accountReport.setCurrentExpenditure(accountBalanceHistoryBo.getCURRENTEXPENDITURE());
                }

                accountReport.setBeginTime(beginDate);
                accountReport.setEndTime(endDate);
                accountReports.add(accountReport);
            }
        }
        catch (Exception e)
        {
            throw new ApplicationContextException(
                "利润报表查询统计报错: [AccountReportActionImpl :getAccountReports(" + abList != null ? Collections3.convertToString(
                    abList, Constant.Common.SEPARATOR) : StringUtil.initString(), e);
        }

        return accountReports;
    }

    // 保存账户报表中商户
    private void saveAccountReportOfIdentity(AccountReportInfo accountReport)
    {
        LOGGER.debug(" [进入保存元数据账户的商户方法][AccountReportActionImpl: saveAccountReportMerchant()]");
        try
        {
            IdentityAccountRole iar = identityAccountRoleService.queryIdentityAccountRoleByParames(
                accountReport.getAccountId(), Constant.Account.ACCOUNT_RELATION_TYPE_OWN);

            if (IdentityType.MERCHANT.toString().equalsIgnoreCase(iar.getIdentityType()))
            {
                Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(
                    iar.getIdentityId(), IdentityType.MERCHANT);
                accountReport.setIdentityId(merchant.getId());
                accountReport.setIdentityName(merchant.getMerchantName());
                accountReport.setIdentityType(IdentityType.MERCHANT.toString());
                if (merchant.getMerchantType().toString().equals(
                    Constant.ReportType.MERCHANTTYPE_SUPPLY))
                {
                    accountReport.setIdentityTypeName(Constant.ReportType.MERCHANTTYPE_SUPPLY_ZH_CN);
                }
                else if (merchant.getMerchantType().toString().equals(
                    Constant.ReportType.MERCHANTTYPE_AGENT))
                {
                    accountReport.setIdentityTypeName(Constant.ReportType.MERCHANTTYPE_AGENT_ZH_CN);
                }
            }

            if (IdentityType.SP.toString().equalsIgnoreCase(iar.getIdentityType()))
            {
                SP sp = (SP)identityService.findIdentityByIdentityId(iar.getIdentityId(),
                    IdentityType.SP);
                accountReport.setIdentityId(sp.getId());
                accountReport.setIdentityName(sp.getSpName());
                accountReport.setIdentityType(IdentityType.SP.toString());
                accountReport.setIdentityTypeName(Constant.ReportType.SPTYPE_ZH_CN);
            }

            if (IdentityType.CUSTOMER.toString().equalsIgnoreCase(iar.getIdentityType()))
            {
                Customer customer = (Customer)identityService.findIdentityByIdentityId(
                    iar.getIdentityId(), IdentityType.CUSTOMER);
                accountReport.setIdentityId(customer.getId());
                accountReport.setIdentityName(customer.getCustomerName());
                accountReport.setIdentityType(IdentityType.CUSTOMER.toString());
                accountReport.setIdentityTypeName(Constant.ReportType.CUSTOMERTYPE_ZH_CN);
            }
        }
        catch (Exception e)
        {
            LOGGER.error("[保存元数据账户的商户方法报错][AccountReportActionImpl:saveAccountReportMerchant()]"
                         + ExceptionUtil.getStackTraceAsString(e));
        }

        LOGGER.debug(" [结束保存元数据账户的商户方法][AccountReportActionImpl: saveAccountReportMerchant()]");
    }

    /**
     * 卡账户统计
     */
    @Override
    public void censusCardAccountReport()
    {

    }

    public AccountReportRecord saveAccountReportRecord(AccountReportRecord accountReportRecord,
                                                       Date beginDate, Date endDate,
                                                       String status, String describe)
    {
        accountReportRecord.setBeginDate(beginDate);
        accountReportRecord.setEndDate(endDate);
        accountReportRecord.setUpdateDate(new Date());
        accountReportRecord.setReportStatus(status);
        accountReportRecord.setReportDescribe(describe);
        accountReportRecord = accountReportControlService.saveAccountReportRecord(accountReportRecord);
        return accountReportRecord;
    }

    public void censusCurrencyAccountReportTest(String beginTime, String endTime)
    {
        LOGGER.debug("[进入定时扫描到账户报表元数据][AccountReportActionImpl:setAccountReport()]");
        AccountReportRecord accountReportRecord = new AccountReportRecord();
        Date beginTimeDate = null;
        Date endTimeDate = null;
        LOGGER.debug("[定时扫描到账户报表元数据][AccountReportActionImpl: censusCurrencyAccountReport()] [得到所有账户:currencyAccountService.queryAllCurrencyAccount(),currencyAccounts数量:"
                     + "]");
        try
        {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            if (StringUtil.isBlank(beginTime) && StringUtil.isBlank(endTime))
            {
                beginTimeDate = ReportTool.getBeginTime();
                endTimeDate = ReportTool.getEndTime();
            }
            else
            {
                beginTimeDate = format.parse(beginTime);
                endTimeDate = format.parse(endTime);
            }
            accountReportRecord = saveAccountReportRecord(accountReportRecord, beginTimeDate,
                endTimeDate, Constant.RecordStatus.INITIALIZATION, "初始化");

            List<AccountBalanceHistoryBo> abList = currencyAccountHistoryService.queryAccountReportBos(
                beginTimeDate, endTimeDate);

            List<AccountReportInfo> accountReports = getAccountReports(abList, beginTimeDate,
                endTimeDate);

            accountReportService.saveAccountReports(accountReports);
            saveAccountReportRecord(accountReportRecord, beginTimeDate, endTimeDate,
                Constant.RecordStatus.SUCCESS, "保存账户报表到数据库成功");
        }
        catch (Exception e)
        {
            LOGGER.error("[账户报表元数据保存报错]"
                         + "[AccountReportActionImpl: censusCurrencyAccountReport()]"
                         + ExceptionUtil.getStackTraceAsString(e));
            saveAccountReportRecord(accountReportRecord, beginTimeDate, endTimeDate,
                Constant.RecordStatus.FAILURE, "保存账户报表到数据库失败");
        }
        LOGGER.debug("[结束生成账户报表元数据方法][AccountReportActionImpl: censusCurrencyAccountReport()]");
    }

}