package com.yuecheng.hops.aportal.web.transaction;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuecheng.hops.account.entity.vo.AccountHistoryAssistVo;
import com.yuecheng.hops.account.service.CurrencyAccountBalanceHistoryService;
import com.yuecheng.hops.account.service.TransactionHistoryService;
import com.yuecheng.hops.aportal.web.BaseControl;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.DateUtil;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.operator.Operator;
import com.yuecheng.hops.product.service.ProductPageQuery;
import com.yuecheng.hops.report.service.ReportTypeService;
import com.yuecheng.hops.transaction.service.order.OrderManagement;


@Controller
@RequestMapping(value = "/transaction")
public class FundsChangeController extends BaseControl
{
    @Autowired
    private CurrencyAccountBalanceHistoryService currencyAccountBalanceHistoryService;

    @Autowired
    private OrderManagement orderManagement;

    @Autowired
    private TransactionHistoryService transactionHistoryService;

    @Autowired
    private ReportTypeService reportTypeService;

    @Autowired
    private ProductPageQuery productPageQuery;

    private static final Logger LOGGER = LoggerFactory.getLogger(FundsChangeController.class);

    @Autowired
    HttpSession session;

    private static final String PAGE_SIZE = "10";

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    // 变动时间、日期、变动类型、订单编号
    @RequestMapping(value = "/fundchangelist")
    public String fundchangelist(@RequestParam(value = "logType", defaultValue = "1")
    String logType, @RequestParam(value = "accountChangeType", defaultValue = "")
    String accountChangeType, @RequestParam(value = "transactionChangeType", defaultValue = "")
    String transactionChangeType, @RequestParam(value = "changeDate", defaultValue = "")
    String changeDate, @RequestParam(value = "orderNo", defaultValue = "")
    String orderNo, @RequestParam(value = "beginDate", defaultValue = "")
    String beginDate, @RequestParam(value = "endDate", defaultValue = "")
    String endDate, @RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = "auto")
    String sortType, Model model, ServletRequest request)
    {
        LOGGER.debug("[FundChangeController:fundchangelist()]");
        Map<String, Object> searchParams = new HashMap<String, Object>();

        if (Constant.AccountFundChange.LOGTYPE_TRANSACTION.equals(logType))
        {

            if (StringUtil.isBlank(transactionChangeType))
            {
                searchParams.put(EntityConstant.AccountFundChange.TRANSACTION_TYPE, null);
            }
            else
            {
                searchParams.put(EntityConstant.AccountFundChange.TRANSACTION_TYPE,
                    transactionChangeType);
            }

            if (StringUtil.isBlank(orderNo))
            {
                searchParams.put(EntityConstant.AccountFundChange.TRANSACTION_NO, null);
            }
            else
            {
                searchParams.put(EntityConstant.AccountFundChange.TRANSACTION_NO, orderNo.trim());
            }
        }
        else
        {
            searchParams.put(EntityConstant.AccountFundChange.TRANSACTION_TYPE, accountChangeType);

        }

        if (StringUtil.isNullOrEmpty(changeDate))
        {
            changeDate = format.format(new Date());
            if (StringUtil.isBlank(beginDate))
            {
                beginDate = DateUtil.formatDateTime(DateUtil.getDateStart(new Date()));
            }
            if (StringUtil.isBlank(endDate))
            {
                endDate = DateUtil.formatDateTime(new Date());
            }
        }

        // else
        // {
        // if (StringUtil.isBlank(beginDate))
        // {
        // beginDate = DateUtil.formatDateTime(DateUtil.getDateStart(getLastMonth(new Date())));
        // }
        // if (StringUtil.isBlank(endDate))
        // {
        // endDate = DateUtil.formatDateTime(getLastMonth(new Date()));
        // }
        // }

        searchParams.put(EntityConstant.AccountFundChange.BEGIN_DATE, beginDate);
        searchParams.put(EntityConstant.AccountFundChange.END_DATE, endDate);

        YcPage<AccountHistoryAssistVo> pageList = new YcPage<AccountHistoryAssistVo>();
        try
        {
            Operator loginPerson = getLoginUser();
            Long identityId = null;
            if (loginPerson != null)
            {
                identityId = loginPerson.getOwnerIdentityId();
            }
            if (identityId != null)
            {
                searchParams.put(EntityConstant.AccountFundChange.LOGTYPE, logType);
                searchParams.put(EntityConstant.AccountFundChange.IDENTITY_ID, identityId);
                searchParams.put(EntityConstant.AccountFundChange.ACCOUNT_TYPE,
                    Constant.AccountType.MERCHANT_DEBIT);
                searchParams.put(EntityConstant.AccountFundChange.RELATION,
                    Constant.Account.ACCOUNT_RELATION_TYPE_OWN);
                searchParams.put(EntityConstant.AccountFundChange.IDENTITY_ID, identityId);
                searchParams.put(EntityConstant.Account.ACCOUNT_ID,
                    session.getAttribute(EntityConstant.Account.ACCOUNT_ID));

                pageList = currencyAccountBalanceHistoryService.queryAccountFundsChange(
                    searchParams, pageNumber, pageSize);

            }
            else
            {
                LOGGER.error("[FundChangeController:fundchangelist()] [identityId:" + identityId
                             + "]");
            }

            // getModelToFundChange(accountChangeType, transactionChangeType, changeDate, orderNo,
            // beginDate, endDate, pageNumber, model, pagevo_list, pageSize);
        }
        catch (Exception e)
        {
            LOGGER.error("[FundChangeController:fundchangelist()] " + e.getMessage());

        }
        if (BeanUtils.isNull(pageList))
        {
            pageList = new YcPage<AccountHistoryAssistVo>();
        }
        model.addAttribute("logType", logType);
        model.addAttribute("orderNo", orderNo);
        model.addAttribute("changeDate", changeDate);
        model.addAttribute("accountChangeType", accountChangeType);
        model.addAttribute("transactionChangeType", transactionChangeType);
        model.addAttribute("beginDate", beginDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("mlist", pageList.getList());
        model.addAttribute("page", pageNumber);
        model.addAttribute("counttotal", pageList.getCountTotal() + "");
        model.addAttribute("pagetotal", pageList.getPageTotal() + "");
        return "transaction/fundchangeList";
    }

    public Date getLastMonth(Date date)
    {
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(date);
        currentDate.add(Calendar.HOUR_OF_DAY, 1);
        return (Date)currentDate.getTime().clone();
    }

}
