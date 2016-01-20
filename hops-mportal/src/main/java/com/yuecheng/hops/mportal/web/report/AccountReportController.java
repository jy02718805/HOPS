package com.yuecheng.hops.mportal.web.report;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuecheng.hops.account.entity.AccountType;
import com.yuecheng.hops.account.service.AccountTypeService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.persistence.SelectLabel;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.AbstractIdentity;
import com.yuecheng.hops.identity.entity.customer.Customer;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.entity.operator.Operator;
import com.yuecheng.hops.identity.entity.sp.SP;
import com.yuecheng.hops.identity.service.IdentityService;
import com.yuecheng.hops.mportal.vo.report.AccountReportVo;
import com.yuecheng.hops.report.entity.AccountReportInfo;
import com.yuecheng.hops.report.entity.ReportProperty;
import com.yuecheng.hops.report.entity.ReportType;
import com.yuecheng.hops.report.entity.po.AccountReportPo;
import com.yuecheng.hops.report.service.AccountReportService;
import com.yuecheng.hops.report.service.ReportPropertyService;
import com.yuecheng.hops.report.service.ReportTypeService;


/**
 * 账户统计
 * 
 * @author Administrator
 * @version 2014年10月12日
 * @see AccountReportController
 * @since
 */
@Controller
@RequestMapping(value = "/report")
public class AccountReportController
{
    @Autowired
    private AccountReportService accountReportsService;

    @Autowired
    private ReportTypeService reportTypeService;

    @Autowired
    private ReportPropertyService reportPropertyService;

    @Autowired
    private AccountTypeService accountTypeService;
    
    @Autowired
    private IdentityService identityService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountReportController.class);

    private static final String PAGE_SIZE = "10";

    /**
     * 统计列表
     * 
     * @param merchantName
     * @param accountId
     * @param merchantType
     * @param accountTypeId
     * @param reportType
     * @param beginTime
     * @param endTime
     * @param pageNumber
     * @param pageSize
     * @param model
     * @param request
     * @return
     * @see
     */
    @RequestMapping(value = "/accountReports")
    public String accountReports(AccountReportVo accountReportVo,
                                 @RequestParam(value = "page", defaultValue = "1")
                                 int pageNumber,
                                 @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE)
                                 int pageSize, Model model, ServletRequest request)
    {
        LOGGER.debug("[进入查询账户报表方法][AccountReportsController:accountReports(accountReportVo:"
                     + accountReportVo.toString() + ")] ");

        if (StringUtil.isBlank(accountReportVo.getBeginTime())
            && StringUtil.isBlank(accountReportVo.getEndTime()))
        {
            accountReportVo.setBeginTime(ReportTool.getDefaultDate());
            accountReportVo.setEndTime(ReportTool.getDefaultDate());
        }
        YcPage<AccountReportPo> pagelist = new YcPage<AccountReportPo>();
        ReportType rtype = new ReportType();
        StringBuffer html = new StringBuffer();
        List<AccountType> atList = new ArrayList<AccountType>();
        try
        {
            LOGGER.debug("[查询出账户报表方法][AccountReportsController: accountReports()] [调用accountReportsService中queryAccountReports()方法，获得报表数据]");

            if (StringUtil.isNotBlank(accountReportVo.getReportType()))
            {
                rtype = reportTypeService.getReportType(Long.valueOf(accountReportVo.getReportType()));
                List<ReportProperty> rpList = reportPropertyService.queryReportPropertysByTypeId(Long.valueOf(accountReportVo.getReportType()));
                Map<String, Object> searchParams = BeanUtils.transBean2Map(accountReportVo);
                 pagelist = accountReportsService.queryAccountReports(searchParams,rpList,
                     accountReportVo.getBeginTime(), accountReportVo.getEndTime(), pageNumber, pageSize,
                     EntityConstant.AccountReport.BEGIN_TIME);
                 if(rpList.size()>0)
                 {
                     html = ReportTool.getReportTypeHtml(html, rpList);
                     html = ReportTool.getReportPropertyHtml(html,pageNumber,pageSize, rpList, pagelist.getList());
                 }else
                 {
                     html=html.append("<tr><td>您还没有配置属性，请先配置。</td></tr>");
                 }
            }
            atList = accountTypeService.getAllAccountType();
        }
        catch (Exception e)
        {
            LOGGER.error("[查询账户报表方法报错][AccountReportsController:accountReports()] "
                         + e.getMessage());
        }

        model.addAttribute("reportTermList",
            ReportTool.getReportTermList(reportTypeService, rtype));
        model.addAttribute("reportTypeList",
            ReportTool.getReportTypeList(reportTypeService, Constant.ReportType.ACCOUNT_REPORTS));
        model.addAttribute("accountReportHtml", html);
        model.addAttribute("atList", atList);
        model.addAttribute("accountReportVo", accountReportVo);
        model.addAttribute("accountReports", pagelist.getList());
        model.addAttribute("page", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("counttotal", pagelist.getCountTotal() + "");
        model.addAttribute("pagetotal", pagelist.getPageTotal() + "");

        LOGGER.debug("[结束查询账户报表方法][AccountReportsController:accountReports()] ");
        return "/report/accountReports.ftl";
    }

    /**
     * 设置响应 测试后
     * 
     * @param merchantName
     * @param accountId
     * @param merchantType
     * @param accountTypeId
     * @param reportType
     * @param beginTime
     * @param endTime
     * @param pageNumber
     * @param model
     * @param page_list
     * @param rtype
     * @param html
     * @param atList
     * @see
     */
    protected void setModelToAccountReports(String merchantName, String accountId,
                                            String merchantType, String accountTypeId,
                                            String reportType, String beginTime, String endTime,
                                            int pageNumber, Model model,
                                            YcPage<AccountReportInfo> page_list, ReportType rtype,
                                            StringBuffer html, List<AccountType> atList)
    {
        LOGGER.debug("[设置查询账户报表响应数据][AccountReportsController:accountReports()] ");

        model.addAttribute("reportTermList",
            ReportTool.getReportTermList(reportTypeService, rtype));
        model.addAttribute("reportTypeList",
            ReportTool.getReportTypeList(reportTypeService, Constant.ReportType.ACCOUNT_REPORTS));
        model.addAttribute("accountReportHtml", html);
        model.addAttribute("reportType", reportType);

        model.addAttribute("atList", atList);
        model.addAttribute("merchantName", merchantName);
        model.addAttribute("merchantType", merchantType);
        model.addAttribute("accountId", accountId);
        model.addAttribute("accountTypeId", accountTypeId);
        model.addAttribute("beginTime", beginTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("accountReports", page_list.getList());
        model.addAttribute("page", pageNumber);
        model.addAttribute("counttotal", page_list.getCountTotal() + "");
        model.addAttribute("pagetotal", page_list.getPageTotal() + "");
    }

    
	@RequestMapping(value = "/getUsers")
	@ResponseBody
	public List<SelectLabel> getUsers(@RequestParam(value = "identityType", defaultValue = "") String identityType,
			Model model, ServletRequest request)
	{
		List<SelectLabel> userLabels = new ArrayList<SelectLabel>();
		List<AbstractIdentity> identities1 = new ArrayList<AbstractIdentity>();
		List<AbstractIdentity> identities2 = new ArrayList<AbstractIdentity>();
		List<AbstractIdentity> identities3 = new ArrayList<AbstractIdentity>();
		List<AbstractIdentity> identities4 = new ArrayList<AbstractIdentity>();
		if (StringUtil.isBlank(identityType) || IdentityType.MERCHANT.name().equals(identityType))
		{
			identities1 = identityService.getAllIdentityList(IdentityType.MERCHANT);
		}
		if (StringUtil.isBlank(identityType) || IdentityType.CUSTOMER.name().equals(identityType))
		{
			identities2 = identityService.getAllIdentityList(IdentityType.CUSTOMER);
		}

		if (StringUtil.isBlank(identityType) || IdentityType.SP.name().equals(identityType))
		{
			identities3 = identityService.getAllIdentityList(IdentityType.SP);
		}
		if (StringUtil.isBlank(identityType) || IdentityType.OPERATOR.name().equals(identityType))
		{
			identities4 = identityService.getAllIdentityList(IdentityType.OPERATOR);
		}

		for (AbstractIdentity abstractIdentity : identities1)
		{
			Merchant merchant = (Merchant) abstractIdentity;
			SelectLabel sl = new SelectLabel(merchant.getMerchantName(), merchant.getMerchantName());
			userLabels.add(sl);
		}
		for (AbstractIdentity abstractIdentity : identities2)
		{
			Customer customer = (Customer) abstractIdentity;
			SelectLabel sl = new SelectLabel(customer.getCustomerName(), customer.getCustomerName());
			userLabels.add(sl);
		}
		for (AbstractIdentity abstractIdentity : identities3)
		{
			SP sp = (SP) abstractIdentity;
			SelectLabel sl = new SelectLabel(sp.getSpName(), sp.getSpName());
			userLabels.add(sl);
		}
		for (AbstractIdentity abstractIdentity : identities4)
		{
			Operator operator = (Operator) abstractIdentity;
			SelectLabel sl = new SelectLabel(operator.getOperatorName(), operator.getOperatorName());
			userLabels.add(sl);
		}
		return userLabels;
	}
    
    
    
    
    /**
     * 测试
     */
    @RequestMapping(value = "/testAccount")
    public String testAccount(@RequestParam(value = "beginTime", defaultValue = "")
    String beginTime, @RequestParam(value = "endTime", defaultValue = "")
    String endTime, @RequestParam(value = "page.size", defaultValue = PAGE_SIZE)
    int pageSize, Model model, ServletRequest request)
    {
        accountReportsService.testReport(beginTime+" 00:00:00", endTime+" 23:59:59");
        return null;
    }
}
