package com.yuecheng.hops.report.service.impl;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.account.service.CCYAccountService;
import com.yuecheng.hops.account.service.CurrencyAccountBalanceHistoryService;
import com.yuecheng.hops.account.service.IdentityAccountRoleService;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.report.entity.AccountReportInfo;
import com.yuecheng.hops.report.entity.ReportProperty;
import com.yuecheng.hops.report.entity.po.AccountReportPo;
import com.yuecheng.hops.report.execution.AccountReportAction;
import com.yuecheng.hops.report.repository.AccountReportDao;
import com.yuecheng.hops.report.repository.jpa.AccountReportJpaDao;
import com.yuecheng.hops.report.service.AccountReportService;


/**
 * 账户统计服务
 * 
 * @author Administrator
 * @version 2014年10月27日
 * @see AccountReportServiceImpl
 * @since
 */
@Service("accountReportService")
public class AccountReportServiceImpl implements AccountReportService
{
    @Autowired
    private IdentityAccountRoleService identityAccountRoleService;

    @Autowired
    private CCYAccountService ccyAccountService;

    @Autowired
    private AccountReportAction accountReportAction;

    @Autowired
    private CurrencyAccountBalanceHistoryService currencyAccountBalanceHistoryService;

    @Autowired
    private AccountReportJpaDao accountReportJpaDao;

    @Autowired
    private AccountReportDao accountReportDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountReportServiceImpl.class);


    @Override
    public AccountReportInfo saveAccountReport(AccountReportInfo ar)
    {
        LOGGER.info("进入 保存账户报表[AccountReportsServiceImpl.saveAccountReport(AccountReport:"
                    + ar.toString() + ")]");
        AccountReportInfo arr = accountReportJpaDao.save(ar);
        LOGGER.info("结束 保存账户报表[AccountReportsServiceImpl.saveAccountReport(AccountReport:"
                    + arr.toString() + ")]");
        return arr;
    }

    @Override
    public List<AccountReportInfo> saveAccountReports(List<AccountReportInfo> accountReports)
    {
        accountReports = (List<AccountReportInfo>)accountReportJpaDao.save(accountReports);
        return accountReports;
    }

    @Override
    public void testReport(String begin, String end)
    {
        // TODO Auto-generated method stub
        accountReportAction.censusCurrencyAccountReportTest(begin, end);
    }

    @Override
    public YcPage<AccountReportPo> queryAccountReports(Map<String, Object> searchParams,
                                                       List<ReportProperty> rpList,
                                                       String beginTime, String endTime,
                                                       int pageNumber, int pageSize,
                                                       String sortType)
    {
        // TODO Auto-generated method stub
        LOGGER.debug("[AccountReportsServiceImpl:queryAccountReports()] ");
        YcPage<AccountReportPo> ycPage = accountReportDao.queryAccountPageReports(searchParams,
            rpList, beginTime, endTime, pageNumber, pageSize, sortType);
        return ycPage;
    }

}
