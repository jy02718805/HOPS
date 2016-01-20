package com.yuecheng.hops.mportal.web.account;


import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.web.Servlets;

import com.alibaba.dubbo.rpc.RpcException;
import com.google.common.collect.Maps;
import com.yuecheng.hops.account.convertor.AccountVoConvertor;
import com.yuecheng.hops.account.entity.Account;
import com.yuecheng.hops.account.entity.AccountType;
import com.yuecheng.hops.account.entity.CCYAccount;
import com.yuecheng.hops.account.entity.CardAccount;
import com.yuecheng.hops.account.entity.CurrencyAccount;
import com.yuecheng.hops.account.entity.CurrencyAccountAddCashRecord;
import com.yuecheng.hops.account.entity.role.IdentityAccountRole;
import com.yuecheng.hops.account.entity.vo.CCYAccountVo;
import com.yuecheng.hops.account.entity.vo.CurrencyAccountAddCashRecordVo;
import com.yuecheng.hops.account.entity.vo.SpAccountVo;
import com.yuecheng.hops.account.service.AccountServiceFinder;
import com.yuecheng.hops.account.service.AccountStatusManagement;
import com.yuecheng.hops.account.service.AccountTransferService;
import com.yuecheng.hops.account.service.AccountTypeService;
import com.yuecheng.hops.account.service.CCYAccountService;
import com.yuecheng.hops.account.service.CardAccountService;
import com.yuecheng.hops.account.service.CurrencyAccountAddCashRecordService;
import com.yuecheng.hops.account.service.IdentityAccountRoleService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.AccountDirectoryType;
import com.yuecheng.hops.common.enump.AccountModelType;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.DateUtil;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.entity.operator.Operator;
import com.yuecheng.hops.identity.entity.sp.SP;
import com.yuecheng.hops.identity.service.IdentityService;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.identity.service.merchant.MerchantStatusManagement;
import com.yuecheng.hops.identity.service.sp.SpService;
import com.yuecheng.hops.mportal.constant.PageConstant;
import com.yuecheng.hops.mportal.web.BaseControl;


@Controller
@RequestMapping(value = "/account")
public class AccountController extends BaseControl
{

    @Autowired
    private AccountServiceFinder accountServiceFinder;

    @Autowired
    private AccountStatusManagement accountStatusManagement;

    @Autowired
    private AccountTypeService accountTypeService;

    @Autowired
    private CCYAccountService ccyAccountService;

    @Autowired
    private CardAccountService cardAccountService;

    @Autowired
    private MerchantStatusManagement merchantStatusManagement;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private IdentityAccountRoleService identityAccountRoleService;

    @Autowired
    private AccountVoConvertor accountVoConvertor;

    @Autowired
    private HttpSession session;

    @Autowired
    private CurrencyAccountAddCashRecordService currencyAccountAddCashRecordService;

    @Autowired
    private SpService spService;

    @Autowired
    private AccountTransferService accountTransferService;

    @Autowired
    private MerchantService merchantService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    private SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");

    /**
     * 进入账户添加页面
     * 
     * @return
     */
    @RequestMapping(value = "/addAccount")
    public String addAccount(@RequestParam("identityId")
    String identityId, @RequestParam("identityName")
    String identityName, Model model)
    {
        List<AccountType> accountTypeList = accountTypeService.getAccountTypeByIdentityType(IdentityType.MERCHANT);
        model.addAttribute("accountTypeList", accountTypeList);
        model.addAttribute("identityId", identityId);
        model.addAttribute("identityName", identityName);
        return "account/accountRegister";
    }

    @RequestMapping(value = "/toEditCardAccount")
    public String toEditCardAccount(@RequestParam(value = "accountId")
    Long accountId, @RequestParam(value = "status")
    String status, Model model)
    {
        CardAccount cardAccount = cardAccountService.queryCardAccountById(accountId);
        model.addAttribute("cardAccount", cardAccount);
        return "account/toEditCardAccount";
    }

    @RequestMapping(value = "/doEditCardAccount")
    public String doEditCardAccount(@RequestParam(value = "accountId")
    Long accountId, @RequestParam(value = "status")
    String status, Model model)
    {
        Operator loginPerson = getLoginUser();
        CardAccount cardAccount = cardAccountService.queryCardAccountById(accountId);
        accountStatusManagement.updateAccountStatus(cardAccount.getAccountId(),
            cardAccount.getAccountType(), status, loginPerson.getOperatorName());
        return "redirect:/account/listCardAccount";
    }

    private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
    static
    {
        sortTypes.put("auto", "自动");
        sortTypes.put("accountId", "标题");
    }

    /**
     * 实体卡账户列表页面
     * 
     * @return
     */
    @RequestMapping(value = "/listCardAccount")
    public String listCardAccount(@RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "page.size", defaultValue = PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = "auto")
    String sortType, Model model, ServletRequest request)
    {
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request,
            Constant.Common.SEARCH + "_");
        YcPage<CardAccount> page_list = cardAccountService.queryCardAccount(searchParams,
            pageNumber, pageSize, "");

        model.addAttribute("mlist", page_list.getList());
        model.addAttribute("page", pageNumber);
        model.addAttribute("counttotal", page_list.getCountTotal() + "");
        model.addAttribute("pagetotal", page_list.getPageTotal() + "");
        return "account/cardAccountList";
    }

    /**
     * 虚拟卡账户列表页面
     * 
     * @return
     */
    @RequestMapping(value = "/listCurrencyAccount")
    public String listCurrencyAccount(@RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = "auto")
    String sortType, Model model, ServletRequest request)
    {
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request,
            Constant.Common.SEARCH + "_");
        YcPage<CurrencyAccount> page_list = ccyAccountService.queryCurrencyAccountList(
            searchParams, pageNumber, pageSize, "");

        model.addAttribute("mlist", page_list.getList());
        model.addAttribute("page", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("counttotal", page_list.getCountTotal() + "");
        model.addAttribute("pagetotal", page_list.getPageTotal() + "");
        return "account/currencyAccountList";
    }

    /**
     * 进入虚拟账户补款页面
     * 
     * @param accountId
     * @param model
     * @return
     */
    @RequestMapping(value = "/toCurrencyAccountDebit")
    public String toCurrencyAccountDebit(@RequestParam(value = "pageType", defaultValue = "")
    String pageType, @RequestParam(value = "accountId")
    Long accountId, @RequestParam(value = "identityId")
    String identityId, @RequestParam(value = "identityName")
    String identityName, @RequestParam(value = "accountTypeId")
    String accountTypeId, Model model)
    {
        CCYAccount ccyAccount = (CCYAccount)ccyAccountService.getAccountByParams(accountId,
            Long.valueOf(accountTypeId), null);
        model.addAttribute("currencyAccount", ccyAccount);
        model.addAttribute("identityId", identityId);
        SP sp = spService.getSP();
        List<CCYAccount> accounts = ccyAccountService.queryCCYAccounts(Constant.AccountType.EXTERNAL_ACCOUNT, sp.getId(), Constant.Account.ACCOUNT_RELATION_TYPE_OWN);
        try {
			identityName = new String(identityName.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        model.addAttribute("accounts", accounts);
        model.addAttribute("identityName", identityName);
        model.addAttribute("accountTypeId", accountTypeId);
        model.addAttribute("pageType", pageType);
        return "account/currencyAccountDebit";
    }

    /**
     * 进入虚拟账户减款页面
     * 
     * @param accountId
     * @param model
     * @return
     */
    @RequestMapping(value = "/toCurrencyAccountCredit")
    public String toCurrencyAccountCredit(@RequestParam(value = "pageType", defaultValue = "")
    String pageType, @RequestParam(value = "accountId")
    Long accountId, @RequestParam(value = "identityId")
    String identityId, @RequestParam(value = "identityName")
    String identityName, @RequestParam(value = "accountTypeId")
    String accountTypeId, Model model)
    {
        CCYAccount ccyAccount = (CCYAccount)ccyAccountService.getAccountByParams(
            Long.valueOf(accountId), Long.valueOf(accountTypeId), null);
        model.addAttribute("currencyAccount", ccyAccount);
        model.addAttribute("identityId", identityId);
        SP sp = spService.getSP();
        List<CCYAccount> accounts = ccyAccountService.queryCCYAccounts(Constant.AccountType.EXTERNAL_ACCOUNT, sp.getId(), Constant.Account.ACCOUNT_RELATION_TYPE_OWN);
        try{
        	identityName = new String(identityName.getBytes("ISO-8859-1"), "UTF-8");
        }catch(Exception e){
        	e.printStackTrace();
        }
        model.addAttribute("accounts", accounts);
        model.addAttribute("identityName", identityName);
        model.addAttribute("accountTypeId", accountTypeId);
        model.addAttribute("pageType", pageType);
        return "account/currencyAccountCredit";
    }

    /**
     * 加款
     * 
     * @param ca
     * @return
     */
    @RequestMapping(value = "/doCurrencyAccountDebit")
    public String doCurrencyAccountDebit(@RequestParam(value = "pageType", defaultValue = "")String pageType, 
                                         CurrencyAccount ca, 
                                         @RequestParam(value = "identityId")String identityId, 
                                         @RequestParam(value = "accountTypeId")String accountTypeId,
                                         @RequestParam(value = "externalAccountId")String externalAccountId,
                                         @RequestParam(value = "remark", 
                                         defaultValue = "")
    String remark, ModelMap model)
    {
        BigDecimal amt = ca.getAvailableBalance();
        try
        {
            CCYAccount ccyAccount = (CCYAccount)ccyAccountService.getAccountByParams(
                ca.getAccountId(), Long.valueOf(accountTypeId), null);
            
            SP sp = spService.getSP();
            Account externalAccount = ccyAccountService.getAccountByParams(Long.valueOf(externalAccountId), Constant.AccountType.EXTERNAL_ACCOUNT, null);
            //外部账户
//            CCYAccount externalAccount = (CCYAccount)identityAccountRoleService.getAccountNoCache(
//                Constant.AccountType.EXTERNAL_ACCOUNT, sp.getId(), IdentityType.SP.toString(),
//                Constant.Account.ACCOUNT_RELATION_TYPE_OWN, null);
            
            Operator operator = getLoginUser();
            String desc = "操作员：" + operator.getDisplayName() + " 对账户：" + ccyAccount.getAccountId() + "进行加款，金额为[" + amt + "]。备注:["+remark+"]";
            Long transactionNo = new Date().getTime();
            
            if (AccountDirectoryType.CREDIT.equals(ccyAccount.getAccountType().getDirectory()))
            {
                accountTransferService.doTransfer(ccyAccount.getAccountId(), ccyAccount.getAccountType().getAccountTypeId(), externalAccount.getAccountId(), externalAccount.getAccountType().getAccountTypeId(), amt, desc, Constant.TransferType.TRANSFER_ADD_CASH, transactionNo);
            }
            else if (AccountDirectoryType.DEBIT.equals(ccyAccount.getAccountType().getDirectory()))
            {
                accountTransferService.doTransfer(externalAccount.getAccountId(), externalAccount.getAccountType().getAccountTypeId(), ccyAccount.getAccountId(), ccyAccount.getAccountType().getAccountTypeId(), amt, desc, Constant.TransferType.TRANSFER_ADD_CASH, transactionNo);
            }
            else
            {
                model.put("message", "操作失败[账户方向(directory)未知]");
                model.put("canback", true);
            }
            if (pageType != null && pageType.equals(Constant.Common.LIST) && identityId != null
                && !identityId.isEmpty())
            {
                Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(new Long(
                    identityId), IdentityType.MERCHANT);
                if (merchant.getMerchantType().name().equals(MerchantType.AGENT.toString()))
                {
                    model.put("next_url", "Merchant/allAgentAccountList");
                    model.put("next_msg", "代理商账户列表");
                }
                else
                {
                    model.put("next_url", "Merchant/allSupplyAccountList");
                    model.put("next_msg", "供货商账户列表");
                }
            }
            else
            {
                model.put("next_url", "Merchant/toEditMerchantAccount?id=" + identityId);
                model.put("next_msg", "账户管理");
            }
            model.put("message", "操作成功");
            model.put("canback", false);
        }
        catch (RpcException e)
        {
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        catch (Exception e)
        {
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    /**
     * 减款
     * 
     * @param ca
     * @return
     */
    @RequestMapping(value = "/doCurrencyAccountCredit")
    public String doCurrencyAccountCredit(@RequestParam(value = "pageType", defaultValue = "")String pageType, 
                                          CurrencyAccount ca, 
                                          @RequestParam(value = "identityId")String identityId, 
                                          @RequestParam(value = "accountTypeId")String accountTypeId,
                                          @RequestParam(value = "externalAccountId")String externalAccountId,
                                          @RequestParam(value = "remark", 
                                          defaultValue = "")
    String remark, ModelMap model)
    {
        BigDecimal amt = ca.getAvailableBalance();
        try
        {
            CCYAccount ccyAccount = (CCYAccount)ccyAccountService.getAccountByParams(
                ca.getAccountId(), Long.valueOf(accountTypeId), null);
            
            SP sp = spService.getSP();
            //外部账户
            Account externalAccount = ccyAccountService.getAccountByParams(Long.valueOf(externalAccountId), Constant.AccountType.EXTERNAL_ACCOUNT, null);
            
            Operator operator = getLoginUser();
            String desc = "操作员：" + operator.getDisplayName() + " 对账户：" + ccyAccount.getAccountId() + "进行减款，金额为[" + amt + "]。备注:["+remark+"]";
            Long transactionNo = new Date().getTime();
            
            if (AccountDirectoryType.CREDIT.equals(ccyAccount.getAccountType().getDirectory()))
            {
                accountTransferService.doTransfer(externalAccount.getAccountId(), externalAccount.getAccountType().getAccountTypeId(), ccyAccount.getAccountId(), ccyAccount.getAccountType().getAccountTypeId(), amt, desc, Constant.TransferType.TRANSFER_SUB_CASH, transactionNo);
            }
            else if (AccountDirectoryType.DEBIT.equals(ccyAccount.getAccountType().getDirectory()))
            {
                accountTransferService.doTransfer(ccyAccount.getAccountId(), ccyAccount.getAccountType().getAccountTypeId(), externalAccount.getAccountId(), externalAccount.getAccountType().getAccountTypeId(), amt, desc, Constant.TransferType.TRANSFER_SUB_CASH, transactionNo);
            }
            else
            {
                model.put("message", "操作失败[账户方向(directory)未知]");
                model.put("canback", true);
            }
            if (pageType != null && pageType.equals(Constant.Common.LIST) && identityId != null
                && !identityId.isEmpty())
            {
                Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(new Long(
                    identityId), IdentityType.MERCHANT);
                if (merchant.getMerchantType().name().equals(MerchantType.AGENT.toString()))
                {
                    model.put("next_url", "Merchant/allAgentAccountList");
                    model.put("next_msg", "代理商账户列表");
                }
                else
                {
                    model.put("next_url", "Merchant/allSupplyAccountList");
                    model.put("next_msg", "供货商列表");
                }
            }
            else
            {
                model.put("next_url", "Merchant/toEditMerchantAccount?id=" + identityId);
                model.put("next_msg", "账户列表");
            }
            model.put("message", "操作成功");
            model.put("canback", false);
        }
        catch (RpcException e)
        {
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        catch (Exception e)
        {
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/toShowAccount")
    public String toShowAccount(@RequestParam(value = "pageType", defaultValue = "")
    String pageType, @RequestParam(value = "accountId")
    Long accountId, @RequestParam(value = "typeModel")
    String typeModel, @RequestParam(value = "identityId")
    String identityId, @RequestParam(value = "identityName")
    String identityName, @RequestParam(value = "accountTypeId")
    String accountTypeId, ModelMap model)
    {
        try
        {

            identityName = new String(identityName.getBytes("ISO-8859-1"), "UTF-8");
            model.addAttribute("identityId", identityId);
            model.addAttribute("identityName", identityName);

            if (AccountModelType.FUNDS.toString().equalsIgnoreCase(typeModel))
            {
                CCYAccount ccyAccount = (CCYAccount)ccyAccountService.getAccountByParams(
                    accountId, Long.valueOf(accountTypeId), null);
                IdentityAccountRole identityAccountRole = identityAccountRoleService.queryIdentityAccountRoleByParames(
                    ccyAccount.getAccountId(), Constant.Account.ACCOUNT_RELATION_TYPE_OWN);
                CCYAccountVo vo = accountVoConvertor.execute(ccyAccount, identityAccountRole);

                model.addAttribute("ca", vo);
                return "account/showCurrencyAccount.ftl";
            }
            else if (AccountModelType.CARD.toString().equalsIgnoreCase(typeModel))
            {
                Account cardAccount = cardAccountService.getAccountByParams(accountId,
                    Long.valueOf(accountTypeId), null);

                model.addAttribute("ca", cardAccount);
                return "account/showCardAccount.ftl";
            }
            else
            {
                Account ccyAccount = ccyAccountService.getAccountByParams(accountId,
                    Long.valueOf(accountTypeId), null);
                model.addAttribute("ca", ccyAccount);
                return "account/showCurrencyAccount.ftl";
            }
        }
        catch (Exception ae)
        {
            model.addAttribute("message", "操作失败[" + ae.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/updateAccountStatus")
    public String updateAccountStatus(@RequestParam(value = "pageType", defaultValue = "")
    String pageType, @RequestParam(value = "accountId")
    Long accountId, @RequestParam(value = "typeModel")
    String typeModel, @RequestParam(value = "identityId")
    String identityId, @RequestParam(value = "identityName")
    String identityName, @RequestParam(value = "accountTypeId")
    String accountTypeId, Model model)
    {
        Operator loginPerson = getLoginUser();

        if (AccountModelType.FUNDS.toString().equalsIgnoreCase(typeModel))
        {
            Account account = ccyAccountService.getAccountByParams(accountId,
                Long.valueOf(accountTypeId), null);

            if (account.getStatus().equalsIgnoreCase(Constant.Account.ACCOUNT_STATUS_LOCKED))
            {
                account.setStatus(Constant.Account.ACCOUNT_STATUS_UNLOCK);
            }
            else
            {
                account.setStatus(Constant.Account.ACCOUNT_STATUS_LOCKED);
            }
            accountStatusManagement.updateAccountStatus(account.getAccountId(),
                account.getAccountType(), account.getStatus(), loginPerson.getOperatorName());
        }
        else if (AccountModelType.CARD.toString().equalsIgnoreCase(typeModel))
        {
            Account account = cardAccountService.getAccountByParams(accountId,
                Long.valueOf(accountTypeId), null);
            if (account.getStatus().equalsIgnoreCase(Constant.Account.ACCOUNT_STATUS_LOCKED))
            {
                account.setStatus(Constant.Account.ACCOUNT_STATUS_UNLOCK);
            }
            else
            {
                account.setStatus(Constant.Account.ACCOUNT_STATUS_LOCKED);
            }
            accountStatusManagement.updateAccountStatus(account.getAccountId(),
                account.getAccountType(), account.getStatus(), loginPerson.getOperatorName());
        }
        if (pageType != null && pageType.equals(Constant.Common.LIST) && identityId != null
            && !identityId.isEmpty())
        {
            Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(new Long(
                identityId), IdentityType.MERCHANT);
            if (merchant.getMerchantType().name().equals(MerchantType.AGENT.toString()))
            {
                return "redirect:/Merchant/allAgentAccountList";
            }
            else
            {
                return "redirect:/Merchant/allSupplyAccountList";
            }
        }
        else
        {
            return "redirect:/Merchant/toEditMerchantAccount?id=" + identityId;
        }
    }

    @RequestMapping(value = "/toAddCreditableBanlance")
    public String toAddCreditableBanlance(@RequestParam(value = "pageType", defaultValue = "")
    String pageType, @RequestParam(value = "accountId")
    Long accountId, @RequestParam(value = "typeModel")
    String typeModel, @RequestParam(value = "identityId")
    String identityId, @RequestParam(value = "identityName")
    String identityName, @RequestParam(value = "accountTypeId")
    String accountTypeId, Model model)
    {
        Account account = null;
        if (AccountModelType.FUNDS.toString().equalsIgnoreCase(typeModel))
        {

            account = ccyAccountService.getAccountByParams(accountId, Long.valueOf(accountTypeId),
                null);
        }
        else if (AccountModelType.CARD.toString().equalsIgnoreCase(typeModel))
        {
            account = cardAccountService.getAccountByParams(accountId,
                Long.valueOf(accountTypeId), null);
        }

        model.addAttribute("currencyAccount", account);
        model.addAttribute("identityId", identityId);
        try{
        	identityName = new String(identityName.getBytes("ISO-8859-1"),"utf-8");
        }catch(Exception e){
        	e.printStackTrace();
        }
        model.addAttribute("identityName", identityName);
        model.addAttribute("accountTypeId", accountTypeId);
        model.addAttribute("pageType", pageType);
        return "account/addCreditableBanlance";
    }

    @RequestMapping(value = "/toSubCreditableBanlance")
    public String toSubCreditableBanlance(@RequestParam(value = "pageType", defaultValue = "")
    String pageType, @RequestParam(value = "accountId")
    Long accountId, @RequestParam(value = "typeModel")
    String typeModel, @RequestParam(value = "identityId")
    String identityId, @RequestParam(value = "identityName")
    String identityName, @RequestParam(value = "accountTypeId")
    String accountTypeId, Model model)
    {
        Account account = null;
        if (AccountModelType.FUNDS.toString().equalsIgnoreCase(typeModel))
        {

            account = ccyAccountService.getAccountByParams(accountId, Long.valueOf(accountTypeId),
                null);
        }
        else if (AccountModelType.CARD.toString().equalsIgnoreCase(typeModel))
        {
            account = cardAccountService.getAccountByParams(accountId,
                Long.valueOf(accountTypeId), null);
        }
        model.addAttribute("currencyAccount", account);
        model.addAttribute("identityId", identityId);
        try{
        	identityName = new String(identityName.getBytes("ISO-8859-1"),"UTF-8");
        }catch(Exception e){
        	e.printStackTrace();
        }
        model.addAttribute("identityName", identityName);
        model.addAttribute("accountTypeId", accountTypeId);
        model.addAttribute("pageType", pageType);
        return "account/subCreditableBanlance";
    }

    @RequestMapping(value = "/doAddCreditableBanlance")
    public String doAddCreditableBanlance(@RequestParam(value = "pageType", defaultValue = "")
    String pageType, CurrencyAccount ca, @RequestParam(value = "identityId")
    String identityId, @RequestParam(value = "remark", defaultValue = "")
    String remark,ModelMap model)
    {
        try
        {
            BigDecimal amt = ca.getCreditableBanlance();
            ccyAccountService.addCreditableBanlanceAction(ca.getAccountId(),
                ca.getAccountType().getAccountTypeId(), amt,remark);
            model.put("message", "操作成功");
            model.put("canback", false);
            if (pageType != null && pageType.equals(Constant.Common.LIST) && identityId != null
                && !identityId.isEmpty())
            {
                Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(new Long(
                    identityId), IdentityType.MERCHANT);
                if (merchant.getMerchantType().name().equals(MerchantType.AGENT.toString()))
                {
                    model.put("next_url", "/Merchant/allAgentAccountList");
                    model.put("next_msg", "代理商账户列表");
                }
                else
                {
                    model.put("next_url", "/Merchant/allSupplyAccountList");
                    model.put("next_msg", "供货商账户列表");
                }
            }
            else
            {
                model.put("next_url", "Merchant/toEditMerchantAccount?id=" + identityId);
                model.put("next_msg", "商户账户列表");
            }
        }
        catch (RpcException e)
        {
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/doSubCreditableBanlance")
    public String doSubCreditableBanlance(@RequestParam(value = "pageType", defaultValue = "")
    String pageType, CurrencyAccount ca, @RequestParam(value = "identityId")
    String identityId, @RequestParam(value = "remark", defaultValue = "")
    String remark,ModelMap model)
    {
        try
        {
            BigDecimal amt = ca.getCreditableBanlance();
            ccyAccountService.subCreditableBanlanceAction(ca.getAccountId(),
                ca.getAccountType().getAccountTypeId(), amt,remark);
            model.put("message", "操作成功");
            model.put("canback", false);
            if (pageType != null && pageType.equals(Constant.Common.LIST) && identityId != null
                && !identityId.isEmpty())
            {
                Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(new Long(
                    identityId), IdentityType.MERCHANT);
                if (merchant.getMerchantType().name().equals(MerchantType.AGENT.toString()))
                {
                    model.put("next_url", "/Merchant/allAgentAccountList");
                    model.put("next_msg", "代理商账户列表");
                }
                else
                {
                    model.put("next_url", "/Merchant/allSupplyAccountList");
                    model.put("next_msg", "供货商账户列表");
                }
            }
            else
            {
                model.put("next_url", "Merchant/toEditMerchantAccount?id=" + identityId);
                model.put("next_msg", "商户账户列表");
            }
        }
        catch (RpcException e)
        {
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/showAccount")
    public String showAccount(@RequestParam(value = "accountId")
    Long accountId, @RequestParam(value = "accountTypeId")
    Long accountTypeId, @RequestParam(value = "transactionNo")
    Long transactionNo, Model model, ModelMap map)
    {
        AccountType accountType = accountTypeService.queryAccountTypeById(accountTypeId);

        if (BeanUtils.isNotNull(accountType)
            && AccountModelType.FUNDS.equals(accountType.getTypeModel()))
        {
            Account account = ccyAccountService.getAccountByParams(accountId,
                Long.valueOf(accountTypeId), transactionNo);
            IdentityAccountRole identityAccountRole = identityAccountRoleService.queryIdentityAccountRoleByParames(
                account.getAccountId(), Constant.Account.ACCOUNT_RELATION_TYPE_OWN);
            CCYAccountVo vo = accountVoConvertor.execute((CCYAccount)account, identityAccountRole);
            model.addAttribute("ca", vo);
            if (vo.getMerchant() != null)
            {
                model.addAttribute("identityName", vo.getMerchant().getMerchantName());
            }
            else if (vo.getSp() != null)
            {
                model.addAttribute("identityName", vo.getSp().getSpName());
            }
            else
            {
                model.addAttribute("identityName", StringUtil.initString());
            }
            return "account/showCurrencyAccount.ftl";
        }
        else if (BeanUtils.isNotNull(accountType)
                 && AccountModelType.CARD.equals(accountType.getTypeModel()))
        {
            Account account = cardAccountService.getAccountByParams(accountId,
                Long.valueOf(accountTypeId), null);
            model.addAttribute("ca", account);
            return "account/showCardAccount.ftl";
        }
        else
        {
            map.put("message", "操作失败[无此账户类型]");
            map.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 系统类型帐号查询
     * 
     * @return
     */
    @RequestMapping(value = "/showSPCrrencyAccountList")
    public String showSPCrrencyAccountList(@RequestParam(value = "accountTypeId", defaultValue = "")
                                           String accountTypeId,
                                           @RequestParam(value = "typeModel", defaultValue = "FUNDS")
                                           String typeModel,
                                           @RequestParam(value = "merchantName", defaultValue = "")
                                           String merchantName,
                                           @RequestParam(value = "relation", defaultValue = "")
                                           String relation,
                                           @RequestParam(value = "page", defaultValue = "1")
                                           int pageNumber,
                                           @RequestParam(value = "pageSize", defaultValue = "10")
                                           int pageSize,
                                           @RequestParam(value = "sortType", defaultValue = "auto")
                                           String sortType, Model model, ServletRequest request)
    {
        @SuppressWarnings("unchecked")
        Map<String, Object> searchParams = new HashedMap();
        searchParams.put(EntityConstant.SPAccount.ACCOUNT_TYPE, accountTypeId);
        searchParams.put(EntityConstant.SPAccount.MERCHANT_NAME, merchantName.trim());
        searchParams.put(EntityConstant.SPAccount.SP_NAME, merchantName.trim());
        searchParams.put(EntityConstant.SPAccount.RELATION, relation);
        searchParams.put(EntityConstant.SPAccount.IDENTITY_TYPE, IdentityType.SP.toString());

        YcPage<SpAccountVo> accountList = new YcPage<SpAccountVo>();
        if (AccountModelType.FUNDS.toString().equals(typeModel))
        {
            searchParams.put(EntityConstant.SPAccount.TYPE_MODEL, typeModel);
            accountList = identityAccountRoleService.queryCurrencyAccountBySp(searchParams,
                pageNumber, pageSize);
        }
        else
        {
            // 实体卡查询
        }

        SP sp = spService.getSP();
        List<AccountType> atLIst = accountTypeService.getAccountTypeByIdentityType(IdentityType.SP);
        model.addAttribute("sp", sp);
        model.addAttribute("atLIst", atLIst);
        model.addAttribute("accountTypeId", accountTypeId);
        model.addAttribute("typeModel", typeModel);
        model.addAttribute("merchantName", merchantName);
        model.addAttribute("relation", relation);
        model.addAttribute("mlist", accountList.getList());
        model.addAttribute("page", pageNumber);
        model.addAttribute("counttotal", accountList.getCountTotal() + "");
        model.addAttribute("pagetotal", accountList.getPageTotal() + "");
        model.addAttribute("pageSize", pageSize);
        return "account/showSPCrrencyAccountList.ftl";
    }

    // 账户详情
    @RequestMapping(value = "/toShowSpAccount")
    public String showAccountSp(@RequestParam(value = "accountId", defaultValue = "")
    String accountId, @RequestParam(value = "relation", defaultValue = "")
    String relation, @RequestParam(value = "accountTypeId", defaultValue = "")
    String accountTypeId, Model model, ServletRequest request)
    {
        CCYAccountVo vo = new CCYAccountVo();
        try
        {
            if (StringUtil.isNotBlank(accountId))
            {
                CCYAccount ccyAccount = ccyAccountService.getCcyAccountById(Long.valueOf(accountId));
                IdentityAccountRole identityAccountRole = identityAccountRoleService.queryIdentityAccountRoleByParames(
                    ccyAccount.getAccountId(), relation);
                BeanUtils.copyProperties(vo, ccyAccount);
                Merchant mt = merchantService.queryMerchantById(identityAccountRole.getIdentityId());
                if (BeanUtils.isNotNull(mt))
                {
                    vo.setMerchant(mt);
                }
                else
                {
                    SP sp = spService.getSP();
                    vo.setSp(sp);
                }

            }
        }
        catch (Exception e)
        {
            LOGGER.error("[AccountController :showAccountSp(accountId:" + accountId + ",relation:"
                         + relation + ")] [异常：" + e.getMessage() + "]");
        }

        model.addAttribute("ca", vo);
        model.addAttribute("availableBalance",String.valueOf(vo.getAvailableBalance()));
        model.addAttribute("creditableBanlance",String.valueOf(vo.getCreditableBanlance()));
        model.addAttribute("unavailableBanlance",String.valueOf(vo.getUnavailableBanlance()));
        return "account/showSPAccount.ftl";
    }

    @RequestMapping(value = "/accountAddCashRecordList")
    public String accountAddCashRecordList(@RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = "10")
    int pageSize, @RequestParam(value = "sortType", defaultValue = "auto")
    String sortType, @RequestParam(value = "merchantName", defaultValue = "")
    String merchantName, @RequestParam(value = "beginApplyTime", defaultValue = "")
    String beginApplyTime, @RequestParam(value = "endApplyTime", defaultValue = "")
    String endApplyTime, @RequestParam(value = "verifyStatus", defaultValue = "")
    Integer verifyStatus, ModelMap model, ServletRequest request)
    {
        if (StringUtil.isBlank(beginApplyTime))
        {
            beginApplyTime = DateUtil.formatDateTime(DateUtil.getDateStart(new Date()));

        }
        if (StringUtil.isBlank(endApplyTime))
        {
            endApplyTime = DateUtil.formatDateTime(new Date());
        }
        CurrencyAccountAddCashRecordVo caarVo = new CurrencyAccountAddCashRecordVo();
        caarVo.setMerchantName(merchantName);
        caarVo.setVerifyStatus(verifyStatus);
        caarVo.setBeginApplyTime(beginApplyTime);
        caarVo.setEndApplyTime(endApplyTime);

        sortType = EntityConstant.CurrencyAccountAddCashRecord.APPLY_TIME_SQL;
        YcPage<CurrencyAccountAddCashRecord> page = currencyAccountAddCashRecordService.findCurrencyAccountAddCashRecordByParams(
            caarVo, pageNumber, pageSize, sortType);
        model.addAttribute("mlist", page.getList());
        model.addAttribute("page", pageNumber);
        model.addAttribute("counttotal", page.getCountTotal() + "");
        model.addAttribute("pagetotal", page.getPageTotal() + "");

        model.addAttribute("merchantName", merchantName);
        model.addAttribute("verifyStatus", verifyStatus);
        model.addAttribute("beginApplyTime", beginApplyTime);
        model.addAttribute("endApplyTime", endApplyTime);
        model.addAttribute("pageSize", pageSize);

        return "account/accountAddCashRecordList";
    }

    @RequestMapping(value = "/toAddCashVerify")
    public String toAddCashVerify(@RequestParam(value = "id")
    Long id, ModelMap model, ServletRequest request)
    {
        CurrencyAccountAddCashRecord caar = currencyAccountAddCashRecordService.findOne(id);
        
        SP sp = spService.getSP();
        List<CCYAccount> accounts = ccyAccountService.queryCCYAccounts(Constant.AccountType.EXTERNAL_ACCOUNT, sp.getId(), Constant.Account.ACCOUNT_RELATION_TYPE_OWN);
        CCYAccount ccyAccount = ccyAccountService.getCcyAccountById(caar.getAccountId());
        
        model.addAttribute("accounts", accounts);
        model.addAttribute("caar", caar);
        model.addAttribute("id", id);
        model.addAttribute("ca", ccyAccount);
        return "account/toAddCashVerify.ftl";
    }

    @RequestMapping(value = "/doAddCashVerify")
    public String doAddCashVerify(@RequestParam(value = "id")
    Long id, @RequestParam(value = "verifyStatus")
    Integer verifyStatus, @RequestParam(value = "amt")
    String amt, ModelMap model, ServletRequest request)
    {
        try
        {
            currencyAccountAddCashRecordService.verify(id, verifyStatus, new BigDecimal(amt));
            model.put("next_url", "account/accountAddCashRecordList");
            model.put("next_msg", "账户列表");
            model.put("message", "操作成功");
            model.put("canback", false);
        }
        catch (Exception e)
        {
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/toConfirmProfit")
    public String toConfirmProfit(ModelMap model, ServletRequest request)
    {
        SP sp = spService.getSP();
        // 最终利润户
        CCYAccount spProfitOwnAccount = (CCYAccount)identityAccountRoleService.getAccountNoCache(
            Constant.AccountType.SYSTEM_PROFIT_OWN, sp.getId(), IdentityType.SP.toString(),
            Constant.Account.ACCOUNT_RELATION_TYPE_OWN, null);
        // 系统利润中间户
        CCYAccount spMiddleProfitAccount = (CCYAccount)identityAccountRoleService.getAccountNoCache(
            Constant.AccountType.SYSTEM_MIDDLE_PROFIT, sp.getId(), IdentityType.SP.toString(),
            Constant.Account.ACCOUNT_RELATION_TYPE_OWN, null);
        model.addAttribute("spProfitOwnAccount", spProfitOwnAccount);
        model.addAttribute("spMiddleProfitAccount", spMiddleProfitAccount);
        return "account/toConfirmProfit.ftl";
    }

    @RequestMapping(value = "/doConfirmProfit")
    public String doConfirmProfit(@RequestParam(value = "spProfitOwnAccountId")
    Long spProfitOwnAccountId, @RequestParam(value = "spMiddleProfitAccountId")
    Long spMiddleProfitAccountId, @RequestParam(value = "amt")
    String amt, ModelMap model, ServletRequest request)
    {
        try
        {
            SP sp = spService.getSP();
            CCYAccount spProfitOwnAccount = (CCYAccount)ccyAccountService.getAccountByParams(
                spProfitOwnAccountId, Constant.AccountType.SYSTEM_PROFIT_OWN, null);
            CCYAccount spMiddleProfitAccount = (CCYAccount)ccyAccountService.getAccountByParams(
                spMiddleProfitAccountId, Constant.AccountType.SYSTEM_MIDDLE_PROFIT, null);
            // IdentityAccountRole spProfitOwnAccount =
            // identityAccountRoleService.getIdentityAccountRoleByParams(Constant.AccountType.SYSTEM_PROFIT_OWN,
            // sp.getId(), IdentityType.SP.toString(), Constant.Account.ACCOUNT_RELATION_TYPE_USE,
            // null);
            // IdentityAccountRole spMiddleProfitAccount =
            // identityAccountRoleService.getIdentityAccountRoleByParams(Constant.AccountType.SYSTEM_MIDDLE_PROFIT,
            // sp.getId(), IdentityType.SP.toString(), Constant.Account.ACCOUNT_RELATION_TYPE_USE,
            // null);

            BigDecimal profitAmt = null;
            try
            {
                profitAmt = new BigDecimal(amt);
                Assert.isTrue(profitAmt.compareTo(new BigDecimal("0")) > 0);
            }
            catch (Exception e)
            {
                model.put("message", "操作失败[金额有误]");
                model.put("canback", true);
            }
            try
            {
                String desc = "确认利润  [" + profitAmt + "元]";
                // Long transactionNo = System.currentTimeMillis() + new Random().nextLong();
                Random r = new Random();
                Long tiansactionNo = Long.valueOf(format.format(new Date()) + r.nextInt(100000));
                accountTransferService.doTransfer(spMiddleProfitAccount.getAccountId(),
                    spMiddleProfitAccount.getAccountType().getAccountTypeId(),
                    spProfitOwnAccount.getAccountId(),
                    spProfitOwnAccount.getAccountType().getAccountTypeId(), profitAmt, desc,
                    Constant.TransferType.TRANSFER_CONFIRM_PROFIT, tiansactionNo);
                model.put("next_url", "account/toConfirmProfit");
                model.put("next_msg", "确认利润");
                model.put("message", "操作成功");
                model.put("canback", false);
            }
            catch (Exception e)
            {
                model.put("message", "操作失败[金额有误]");
                model.put("canback", true);
            }
        }
        catch (Exception e)
        {
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/queryCCYAccounts")
    @ResponseBody
    public List<CCYAccount> queryCCYAccounts(@RequestParam(value = "accountTypeId", defaultValue = "")
    String accountTypeId, @RequestParam(value = "identityId", defaultValue = "")
    String identityId, @RequestParam(value = "relation", defaultValue = "")
    String relation, @RequestParam(value = "accountId", defaultValue = "")
    String accountId, Model model, ServletRequest request)
    {
//        List<CCYAccount> accounts = new ArrayList<CCYAccount>();
//        StringBuffer accountStr = new StringBuffer();
//        if (StringUtil.isNotBlank(accountTypeId) && StringUtil.isNotBlank(identityId)
//            && StringUtil.isNotBlank(relation))
//        {
//            accounts = ccyAccountService.queryCCYAccounts(Long.valueOf(accountTypeId),
//                Long.valueOf(identityId), relation);
//
//            accountStr.append("<option value=\'\'>请选择</option>");
//            if (accounts.size() > 0)
//            {
//                for (Iterator<CCYAccount> iterator2 = accounts.iterator(); iterator2.hasNext();)
//                {
//                    CCYAccount account = iterator2.next();
//                    if (StringUtil.isNotBlank(accountId) && BeanUtils.isNotNull(account)
//                        && accountId.equals(account.getAccountId().toString()))
//                    {
//                        accountStr.append("<option value=\'" + account.getAccountId()
//                                          + "\' selected=\'selected\'>" + account.getAccountId()
//                                          + "</option>");
//                    }
//                    else
//                    {
//                        accountStr.append("<option value=\'" + account.getAccountId() + "\'>"
//                                          + account.getAccountId() + "</option>");
//                    }
//
//                }
//            }
//        }
//        return accountStr.toString();
        List<CCYAccount> accounts = new ArrayList<CCYAccount>();
        if (StringUtil.isNotBlank(accountTypeId) && StringUtil.isNotBlank(identityId))
        {
            accounts = ccyAccountService.queryCCYAccounts(Long.valueOf(accountTypeId),
                Long.valueOf(identityId), relation);
        }
        return accounts;
    }
    
    @RequestMapping(value = "/toCreateExternalAccount")
    public String toCreateExternalAccount(Model model)
    {
        return "account/toCreateExternalAccount";
    }
    
    @RequestMapping(value = "/doCreateExternalAccount")
    @ResponseBody
    public String doCreateExternalAccount(@RequestParam(value = "rmk", defaultValue = "")String rmk,ModelMap model)
    {
        try
        {
            if(BeanUtils.isNull(rmk))
            {
                model.put("message", "操作失败[提交的银行和卡号为空！]");
                model.put("canback", true);
            }
            rmk = new String(rmk.getBytes(), "UTF-8");
            SP sp = spService.getSP();
            AccountType accountType = accountTypeService.queryAccountTypeById(Constant.AccountType.EXTERNAL_ACCOUNT);
            Account account = identityAccountRoleService.saveAccount(sp, accountType, Constant.Account.ACCOUNT_STATUS_UNLOCK, rmk, Constant.Account.ACCOUNT_RELATION_TYPE_OWN, null);
            if(BeanUtils.isNotNull(account))
            {
                return PageConstant.TRUE;
            }
            else
            {
                return PageConstant.FALSE;
            }
        }
        catch (Exception e)
        {
            return PageConstant.FALSE;
        }
    }
}
