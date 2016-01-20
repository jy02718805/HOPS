package com.yuecheng.hops.mportal.web.account;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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

import com.yuecheng.hops.account.entity.AccountType;
import com.yuecheng.hops.account.entity.vo.AccountHistoryAssistVo;
import com.yuecheng.hops.account.entity.vo.TransactionHistoryAssistVo;
import com.yuecheng.hops.account.service.AccountTypeService;
import com.yuecheng.hops.account.service.CardAccountBalanceHistoryService;
import com.yuecheng.hops.account.service.CurrencyAccountBalanceHistoryService;
import com.yuecheng.hops.account.service.TransactionHistoryService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.DateUtil;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.sp.SP;
import com.yuecheng.hops.identity.service.sp.SpService;
import com.yuecheng.hops.mportal.vo.account.CurrencyAccountBalanceHistoryVo;
import com.yuecheng.hops.mportal.vo.account.TransactionHistoryVo;


@Controller
@RequestMapping(value = "/accountHistory")
public class AccountHistoryController
{

    @Autowired
    private TransactionHistoryService transactionHistoryService;

    @Autowired
    private CurrencyAccountBalanceHistoryService currencyAccountBalanceHistoryService;

    @Autowired
    private CardAccountBalanceHistoryService cardAccountBalanceHistoryService;

    @Autowired
    AccountTypeService accountTypeService;

    @Autowired
    SpService spService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountHistoryController.class);

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 查询账户交易日志列表页面
     * 
     * @return
     */
    @RequestMapping(value = "/accountTransactionLogList")
    public String accountTransactionLogList(TransactionHistoryVo transaction,
                                            @RequestParam(value = "page", defaultValue = "1")
                                            int pageNumber,
                                            @RequestParam(value = "pageSize", defaultValue = "10")
                                            int pageSize,
                                            @RequestParam(value = "sortType", defaultValue = "auto")
                                            String sortType, Model model, ServletRequest request)
    {
        LOGGER.debug("[AccountHistoryControllerr:accountTransactionLogList(TransactionHistoryVo:"
                     + transaction.toString() + ")] ");
        Map<String, Object> searchParams = new HashMap<String, Object>();
        List<AccountType> accountTypelist = accountTypeService.getAllAccountType();
        if (StringUtil.isNotBlank(transaction.getTransactionNo()))
        {
            String transactionNo = transaction.getTransactionNo().trim();
            searchParams.put(EntityConstant.TransactionHistory.TRANSACTION_NO, transactionNo);
        }
        else
        {
            searchParams.put(EntityConstant.TransactionHistory.TRANSACTION_NO, "");
        }

        if (StringUtil.isNotBlank(transaction.getTransactionId()))
        {
            searchParams.put(EntityConstant.TransactionHistory.TRANSACTION_ID,
                transaction.getTransactionId());
        }
        else
        {
            searchParams.put(EntityConstant.TransactionHistory.TRANSACTION_ID, "");
        }

        if (StringUtil.isNotBlank(transaction.getPayerIdentityName()))
        {
            searchParams.put(EntityConstant.TransactionHistory.PAYER_IDENTITY_NAME,
                transaction.getPayerIdentityName().trim());
        }
        else
        {
            searchParams.put(EntityConstant.TransactionHistory.PAYER_IDENTITY_NAME, "");
        }

        if (StringUtil.isNotBlank(transaction.getPayeeIdentityName()))
        {
            searchParams.put(EntityConstant.TransactionHistory.PAYEE_IDENTITY_NAME,
                transaction.getPayeeIdentityName().trim());
        }
        else
        {
            searchParams.put(EntityConstant.TransactionHistory.PAYEE_IDENTITY_NAME, "");
        }

        if (StringUtil.isNotBlank(transaction.getBeginDate()))
        {
            searchParams.put(EntityConstant.TransactionHistory.BEGIN_DATE,
                transaction.getBeginDate());
        }
        else
        {
            String beginTimeString = DateUtil.toDateMinute(new Date());
            searchParams.put(EntityConstant.TransactionHistory.BEGIN_DATE, beginTimeString);
            transaction.setBeginDate(beginTimeString);
        }

        if (StringUtil.isNotBlank(transaction.getEndDate()))
        {
            searchParams.put(EntityConstant.TransactionHistory.END_DATE, transaction.getEndDate());
        }
        else
        {
            String nowTime = DateUtil.formatDateTime(new Date());
            searchParams.put(EntityConstant.TransactionHistory.END_DATE, nowTime);
            transaction.setEndDate(nowTime);
        }

        if (StringUtil.isNotBlank(transaction.getPayerAccountType()))
        {
            searchParams.put(EntityConstant.TransactionHistory.PAYER_ACCOUNT_TYPE,
                transaction.getPayerAccountType());
        }
        else
        {
            searchParams.put(EntityConstant.TransactionHistory.PAYER_ACCOUNT_TYPE, "");
        }

        if (StringUtil.isNotBlank(transaction.getPayeeAccountType()))
        {
            searchParams.put(EntityConstant.TransactionHistory.PAYEE_ACCOUNT_TYPE,
                transaction.getPayeeAccountType());
        }
        else
        {
            searchParams.put(EntityConstant.TransactionHistory.PAYEE_ACCOUNT_TYPE, "");
        }

        searchParams.put(EntityConstant.TransactionHistory.TYPE, transaction.getType());
        YcPage<TransactionHistoryAssistVo> page_list = transactionHistoryService.queryTransactionHistoryList(
            searchParams, pageNumber, pageSize, EntityConstant.TransactionHistory.CREATE_DATE);

        page_list = BeanUtils.isNull(page_list) ? new YcPage<TransactionHistoryAssistVo>() : page_list;

        model.addAttribute("accountTypelist", accountTypelist);
        model.addAttribute("transaction", transaction);
        model.addAttribute("mlist", page_list.getList());
        model.addAttribute("page", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("counttotal", page_list.getCountTotal() + "");
        model.addAttribute("pagetotal", page_list.getPageTotal() + "");
        return "account/accountTransactionLogList";
    }

    /**
     * 账户日志
     * 
     * @param currencyAccountBalanceHistoryVo
     * @param pageNumber
     * @param pageSize
     * @param sortType
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/accountBalanceHistoryList")
    public String accountBalanceHistoryList(CurrencyAccountBalanceHistoryVo currencyAccountBalanceHistoryVo,
                                            @RequestParam(value = "page", defaultValue = "1")
                                            int pageNumber,
                                            @RequestParam(value = "pageSize", defaultValue = "10")
                                            int pageSize,
                                            @RequestParam(value = "sortType", defaultValue = "auto")
                                            String sortType, Model model, ServletRequest request)
    {
        LOGGER.debug("[AccountHistoryControllerr:accountBalanceHistoryList(currencyAccountBalanceHistoryVo:"
                     + currencyAccountBalanceHistoryVo.toString() + ")] ");
        List<AccountType> accountTypelist = accountTypeService.getAllAccountType();
        Map<String, Object> searchParams = new HashMap<String, Object>();
        if (StringUtil.isNotBlank(currencyAccountBalanceHistoryVo.getAccountId()))
        {
            searchParams.put(EntityConstant.CurrencyAccountBalanceHistory.ACCOUNT_ID,
                currencyAccountBalanceHistoryVo.getAccountId().trim());
        }

        if (StringUtil.isNotBlank(currencyAccountBalanceHistoryVo.getAccountTypeId()))
        {
            searchParams.put(EntityConstant.Account.ACCOUNT_TYPE,
                currencyAccountBalanceHistoryVo.getAccountTypeId());
        }
        else
        {
            searchParams.put(EntityConstant.Account.ACCOUNT_TYPE, "");
        }

        // if (StringUtil.isNotBlank(currencyAccountBalanceHistoryVo.getIdentityName()))
        // {
        // searchParams.put(EntityConstant.Account.IDENTITY_NAME,
        // currencyAccountBalanceHistoryVo.getIdentityName().trim());
        // }
        // else
        // {
        // searchParams.put(EntityConstant.Account.IDENTITY_NAME, "");
        // }

        if (StringUtil.isNotBlank(currencyAccountBalanceHistoryVo.getType()))
        {
            searchParams.put(EntityConstant.CurrencyAccountBalanceHistory.TYPE,
                currencyAccountBalanceHistoryVo.getType());
        }
        else
        {
            searchParams.put(EntityConstant.CurrencyAccountBalanceHistory.TYPE, "");
        }

        if (StringUtil.isNotBlank(currencyAccountBalanceHistoryVo.getTransactionId()))
        {
            searchParams.put(EntityConstant.CurrencyAccountBalanceHistory.TRANSACTION_ID,
                currencyAccountBalanceHistoryVo.getTransactionId());
        }
        else
        {
            searchParams.put(EntityConstant.CurrencyAccountBalanceHistory.TRANSACTION_ID, "");
        }

        if (StringUtil.isNotBlank(currencyAccountBalanceHistoryVo.getTransactionNo()))
        {
            searchParams.put(EntityConstant.CurrencyAccountBalanceHistory.TRANSACTION_NO,
                currencyAccountBalanceHistoryVo.getTransactionNo());
        }
        else
        {
            searchParams.put(EntityConstant.CurrencyAccountBalanceHistory.TRANSACTION_NO, "");
        }

        if (StringUtil.isNotBlank(currencyAccountBalanceHistoryVo.getBeginDate()))
        {
            searchParams.put(EntityConstant.CurrencyAccountBalanceHistory.BEGIN_DATE,
                currencyAccountBalanceHistoryVo.getBeginDate());
        }
        else
        {
            String beginTim = DateUtil.toDateMinute(new Date());
            searchParams.put(EntityConstant.CurrencyAccountBalanceHistory.BEGIN_DATE, beginTim);
            currencyAccountBalanceHistoryVo.setBeginDate(beginTim);
        }

        if (StringUtil.isNotBlank(currencyAccountBalanceHistoryVo.getEndDate()))
        {
            searchParams.put(EntityConstant.CurrencyAccountBalanceHistory.END_DATE,
                currencyAccountBalanceHistoryVo.getEndDate());
        }
        else
        {
            String nowTime = DateUtil.formatDateTime(new Date());
            searchParams.put(EntityConstant.CurrencyAccountBalanceHistory.END_DATE, nowTime);
            currencyAccountBalanceHistoryVo.setEndDate(nowTime);
        }

        BSort bsort = new BSort(BSort.Direct.DESC, Constant.Sort.ID);
        YcPage<AccountHistoryAssistVo> ccypage = new YcPage<AccountHistoryAssistVo>();

        try
        {
            ccypage = currencyAccountBalanceHistoryService.queryCurrencyAccountBalanceHistory(
                searchParams, pageNumber, pageSize, bsort);

            ccypage = BeanUtils.isNull(ccypage) ? new YcPage<AccountHistoryAssistVo>() : ccypage;
        }
        catch (Exception e)
        {
            LOGGER.error("[AccountHistoryControllerr:accountBalanceHistoryList()] [报错: "
                         + e.getMessage() + "] ");
        }

        SP sp = spService.getSP();
        model.addAttribute("sp", sp);
        model.addAttribute("accountTypelist", accountTypelist);
        model.addAttribute("currencyAccountBalanceHistoryVo", currencyAccountBalanceHistoryVo);
        model.addAttribute("ccylist", ccypage.getList());
        model.addAttribute("counttotal", ccypage.getCountTotal() + "");
        model.addAttribute("pagetotal", ccypage.getPageTotal() + "");
        model.addAttribute("page", pageNumber);
        model.addAttribute("pageSize", pageSize);
        return "account/accountBalanceHistoryList";
    }
    
    
}
