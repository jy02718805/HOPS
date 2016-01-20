package com.yuecheng.hops.mportal.web.identity;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.account.convertor.AccountVoConvertor;
import com.yuecheng.hops.account.entity.Account;
import com.yuecheng.hops.account.entity.AccountType;
import com.yuecheng.hops.account.entity.CCYAccount;
import com.yuecheng.hops.account.entity.role.IdentityAccountRole;
import com.yuecheng.hops.account.entity.vo.CCYAccountVo;
import com.yuecheng.hops.account.service.AccountTypeService;
import com.yuecheng.hops.account.service.CCYAccountService;
import com.yuecheng.hops.account.service.IdentityAccountRoleService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.Constant.IdentityConstants;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.enump.MerchantLevel;
import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.IdentityStatus;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.entity.merchant.MerchantCode;
import com.yuecheng.hops.identity.entity.mirror.Organization;
import com.yuecheng.hops.identity.service.IdentityService;
import com.yuecheng.hops.identity.service.merchant.MerchantPageQuery;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.identity.service.merchant.MerchantStatusManagement;
import com.yuecheng.hops.identity.service.merchant.OrganizationService;
import com.yuecheng.hops.injection.entity.InterfacePacketsDefinition;
import com.yuecheng.hops.injection.service.InterfaceService;
import com.yuecheng.hops.mportal.constant.PageConstant;
import com.yuecheng.hops.product.entity.relation.AgentProductRelation;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;
import com.yuecheng.hops.product.service.AgentProductRelationService;
import com.yuecheng.hops.product.service.SupplyProductRelationService;
import com.yuecheng.hops.rebate.entity.RebateRule;
import com.yuecheng.hops.rebate.entity.RebateTradingVolume;
import com.yuecheng.hops.rebate.entity.assist.RebateRuleAssist;
import com.yuecheng.hops.rebate.service.RebateRuleQueryManager;
import com.yuecheng.hops.rebate.service.RebateRuleService;
import com.yuecheng.hops.security.entity.SecurityCredential;
import com.yuecheng.hops.security.service.SecurityCredentialManagerService;
import com.yuecheng.hops.security.service.SecurityCredentialService;
import com.yuecheng.hops.security.service.SecurityTypeService;


/**
 * 商户账号管理 组织机构管理
 * 
 * @author xwj
 */
@Controller
@RequestMapping(value = "/Merchant")
public class MerchantController extends MerchantControllerService
{

    private static final String PAGE_SIZE = "10";

    @Autowired
    MerchantService merchantService ;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private CCYAccountService currencyAccountService;

    @Autowired
    private IdentityAccountRoleService identityAccountRoleService;

    @Autowired
    private SupplyProductRelationService upProductRelationService;

    @Autowired
    private AgentProductRelationService downProductRelationService;

    @Autowired
    private AccountTypeService accountTypeService;

    @Autowired
    private RebateRuleService rebateRuleService;

    @Autowired
    private RebateRuleQueryManager rebateRuleQueryManager;

    @Autowired
    private InterfaceService interfaceService;

    @Autowired
    private SecurityCredentialService securityCredentialService;

    @Autowired
    private SecurityCredentialManagerService securityCredentialManagerService;

    @Autowired
    private SecurityTypeService securityTypeService;

    @Autowired
    IdentityService identityService;

    @Autowired
    private MerchantStatusManagement merchantStatusManagement;

    @Autowired
    private AccountVoConvertor accountVoConvertor;

    @Autowired
    private MerchantPageQuery merchantPageQuery;

    private static Logger logger = LoggerFactory.getLogger(MerchantController.class);

    /**
     * 进入商户列表页面
     * 
     * @return
     */
    @RequestMapping(value = "/supplylist")
    public String listSupplyMerchant(@RequestParam(value = "merchantName", defaultValue = "")
    String merchantName, @RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PageConstant.LIST_PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = PageConstant.LIST_ORDER_SORT)
    String sortType, ModelMap model, ServletRequest request)
    {
        try
        {
            Map<String, Object> searchParams = new HashMap<String, Object>();
            if (merchantName != null && !merchantName.isEmpty())
            {
                searchParams.put(Operator.LIKE + "_" + EntityConstant.Merchant.MERCHANT_NAME,
                    merchantName);
            }
            YcPage<Merchant> page = merchantPageQuery.queryPageMerchant(searchParams, pageNumber,
                pageSize, sortType, MerchantType.SUPPLY, null);
            List<Merchant> list = page.getList();
            String pagetotal = page.getPageTotal() + "";
            String countTotal = page.getCountTotal() + "";
            setDefaultEnumModel(model, new Class[] {MerchantType.class});
            model.addAttribute("mlist", list);
            model.addAttribute("page", pageNumber);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("pagetotal", pagetotal);
            model.addAttribute("counttotal", countTotal);
            model.addAttribute("merchantName", merchantName);
            return PageConstant.PAGE_SUPPLY_MERCHANT_LIST;
        }
        catch (RpcException e)
        {
            logger.debug("[MerchantController:listSupplyMerchant()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }

    }

    /**
     * 进入商户列表页面
     * 
     * @return
     */
    @RequestMapping(value = "/agentlist")
    public String listAgentMerchant(@RequestParam(value = "merchantName", defaultValue = "")
    String merchantName, @RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PageConstant.LIST_PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = PageConstant.LIST_ORDER_SORT)
    String sortType, ModelMap model, ServletRequest request)
    {
        try
        {
            Map<String, Object> searchParams = new HashMap<String, Object>();
            if (merchantName != null && !merchantName.isEmpty())
            {
                searchParams.put(Operator.LIKE + "_" + EntityConstant.Merchant.MERCHANT_NAME,
                    merchantName);
            }
            List<Merchant> allMerchant = merchantService.queryAllMerchant(MerchantType.AGENT, null);
            YcPage<Merchant> page = merchantPageQuery.queryPageMerchant(searchParams, pageNumber,
                pageSize, sortType, MerchantType.AGENT, null);
            List<Merchant> list = page.getList();
            String pagetotal = page.getPageTotal() + "";
            String countTotal = page.getCountTotal() + "";
            setDefaultEnumModel(model, new Class[] {MerchantType.class});
            model.addAttribute("mlist", list);
            model.addAttribute("allMerchant", allMerchant);
            model.addAttribute("page", pageNumber);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("pagetotal", pagetotal);
            model.addAttribute("counttotal", countTotal);
            model.addAttribute("merchantName", merchantName);
            return PageConstant.PAGE_AGENT_MERCHANT_LIST;
        }
        catch (RpcException e)
        {
            logger.debug("[MerchantController:listAgentMerchant()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 进入新增商户页面
     * 
     * @return
     */
    @RequestMapping(value = "/addDownMerchant", method = RequestMethod.GET)
    public String addDownMerchant(ModelMap model)
    {
        try
        {
            YcPage<Organization> querResult = organizationService.queryOrganization(
                new HashMap<String, Object>(), 1, 100, null);
            List<Organization> organizationlist = querResult.getList();
            model.put("organizationlist", organizationlist);
            return PageConstant.PAGE_MERCHANT_DOWN_ADD;
        }
        catch (RpcException e)
        {
            logger.debug("[MerchantController:addDownMerchant()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 进入新增商户页面
     * 
     * @return
     */
    @RequestMapping(value = "/addUpMerchant", method = RequestMethod.GET)
    public String addUpMerchant(ModelMap model)
    {
        try
        {
            YcPage<Organization> querResult = organizationService.queryOrganization(
                new HashMap<String, Object>(), 1, 100, null);
            List<Organization> organizationlist = querResult.getList();
            model.put("organizationlist", organizationlist);
            return PageConstant.PAGE_MERCHANT_UP_ADD;
        }
        catch (RpcException e)
        {
            logger.debug("[MerchantController:addUpMerchant()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 新增商户页面
     * 
     * @return
     */
    @RequestMapping(value = "/addDownMerchant", method = RequestMethod.POST)
    public String addDownMerchant(@ModelAttribute("merchant")
    Merchant merchant, @RequestParam("organizationNO")
    String organizationNO, @RequestParam("type")
    String type, ModelMap model)
    {
        try
        {
            com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
            // 获取父级商户
            Merchant parentMerchant = (Merchant)identityService.findIdentityByIdentityId(
                merchant.getParentIdentityId(), IdentityType.MERCHANT);
            MerchantLevel ml = MerchantLevel.Zero;
            if (parentMerchant != null)
            {
                ml = parentMerchant.getMerchantLevel();
                ml = getChildMerchantLevel(ml);
            }

            // 获取所属组织机构
            Organization org = organizationService.queryOrganizationById(Long.parseLong(organizationNO));
            merchant.setOrganization(org);

            // 设置商户类型
            merchant.setMerchantType(MerchantType.valueOf(type));
            merchant.setMerchantLevel(ml);
            // 检查商户编号是否存在
            boolean merchantCodeFlag = checkMerchantCode(merchant);
            if (merchantCodeFlag)
            {
                model.put("message", "操作失败,代理商编号已存在");
                model.put("canback", false);
                model.put("next_url", "Merchant/agentlist");
                model.put("next_msg", "商户列表");
                return PageConstant.PAGE_COMMON_NOTIFY;
            }
            // 检查商户名称是否存在
            boolean merchantNameFlag = checkMerchantName(merchant);
            if (merchantNameFlag)
            {
                model.put("message", "操作失败,代理商名称已存在");
                model.put("canback", false);
                model.put("next_url", "Merchant/agentlist");
                model.put("next_msg", "商户列表");
                return PageConstant.PAGE_COMMON_NOTIFY;
            }
            merchant.setIsRebate(new Long(Constant.RebateStatus.DISABLE));
            merchant.setIdentityType(IdentityType.MERCHANT);
            merchant.setIdentityStatus(new IdentityStatus(IdentityConstants.MERCHANT_DISABLE));
            Merchant mer = merchantService.saveMerchant(merchant, loginPerson.getOperatorName());

            if (mer != null)
            {
                model.put("message", "操作成功");
                model.put("canback", false);
                model.put("next_url", "Merchant/agentlist");
                model.put("next_msg", "商户列表");
            }
            else
            {
                model.put("message", "操作失败");
                model.put("canback", false);
                model.put("next_url", "Merchant/agentlist");
                model.put("next_msg", "商户列表");
            }
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (RpcException e)
        {
            logger.debug("[MerchantController:addDownMerchant()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    public static MerchantLevel getChildMerchantLevel(MerchantLevel ml)
    {
        if (ml.equals(MerchantLevel.Zero))
        {
            ml = MerchantLevel.One;
        }
        else if (ml.equals(MerchantLevel.One))
        {
            ml = MerchantLevel.Two;
        }
        else
        {
            ml = MerchantLevel.Three;
        }
        return ml;
    }

    /**
     * 新增商户页面
     * 
     * @return
     */
    @RequestMapping(value = "/addUpMerchant", method = RequestMethod.POST)
    public String addUpMerchant(@ModelAttribute("merchant")
    Merchant merchant, @RequestParam("organizationNO")
    String organizationNO, @RequestParam("type")
    String type, ModelMap model)
    {
        try
        {
            com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
            // 获取所属组织机构
            Organization org = organizationService.queryOrganizationById(Long.parseLong(organizationNO));
            merchant.setOrganization(org);

            // 设置商户类型
            merchant.setMerchantType(MerchantType.valueOf(type));
            // 检查商户编号是否存在
            boolean merchantCodeFlag = checkMerchantCode(merchant);
            if (merchantCodeFlag)
            {
                model.put("message", "操作失败,供货商编号已存在");
                model.put("canback", true);
                model.put("next_url", "Merchant/supplylist");
                model.put("next_msg", "商户列表");
                return PageConstant.PAGE_COMMON_NOTIFY;
            }
            // 检查商户名称是否存在
            boolean merchantNameFlag = checkMerchantName(merchant);
            if (merchantNameFlag)
            {
                model.put("message", "操作失败,供货商名称已存在");
                model.put("canback", true);
                model.put("next_url", "Merchant/supplylist");
                model.put("next_msg", "商户列表");
                return PageConstant.PAGE_COMMON_NOTIFY;
            }

            merchant.setIsRebate(new Long(Constant.RebateStatus.DISABLE));
            merchant.setIdentityType(IdentityType.MERCHANT);
            merchant.setIdentityStatus(new IdentityStatus(IdentityConstants.MERCHANT_DISABLE));
            // Merchant mer = (Merchant)identityService.saveIdentity(merchant,
            // loginPerson.getUserName());
            Merchant mer = merchantService.saveMerchant(merchant, loginPerson.getOperatorName());

            if (mer != null)
            {
                model.put("message", "操作成功");
                model.put("canback", false);
                model.put("next_url", "Merchant/supplylist");
                model.put("next_msg", "商户列表");
            }
            else
            {
                model.put("message", "操作失败");
                model.put("canback", false);
                model.put("next_url", "Merchant/supplylist");
                model.put("next_msg", "商户列表");
            }
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (RpcException e)
        {
            logger.debug("[MerchantController:addUpMerchant()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@RequestParam("id")
    String id, ModelMap model)
    {
        try
        {
            YcPage<Organization> querResult = organizationService.queryOrganization(
                new HashMap<String, Object>(), 1, 100, null);
            List<Organization> organizationlist = querResult.getList();
            List<Merchant> list2 = merchantService.queryAllMerchant(MerchantType.AGENT,
                Constant.IdentityStatus.OPEN_STATUS);
            model.put("organizationlist", organizationlist);
            model.put("merchantList", list2);
            Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(new Long(id),
                IdentityType.MERCHANT);
            model.addAttribute("merchant", merchant);
            return PageConstant.PAGE_MERCHANT_EDIT;
        }
        catch (RpcException e)
        {
            logger.error("[MerchantController:edit()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String doEdit(@RequestParam(value = "merchantId", defaultValue = "")
    String merchantId, @RequestParam(value = "merchantCode", defaultValue = "")
    String merchantCode, @RequestParam(value = "merchantName", defaultValue = "")
    String merchantName, @RequestParam(value = "parentMerchantId", defaultValue = "")
    String parentMerchantId, ModelMap model)
    {
        try
        {
            com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();

            if (StringUtils.isBlank(merchantId) || StringUtils.isBlank(merchantCode)
                || StringUtils.isBlank(merchantName))
            {
                model.put("message", "操作失败,必填参数为空");
                model.put("canback", true);
                return PageConstant.PAGE_COMMON_NOTIFY;
            }
            Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(new Long(
                merchantId), IdentityType.MERCHANT);
            if (merchant != null)
            {
                if (!merchantName.equals(merchant.getMerchantName()))
                {
                    merchant.setMerchantName(merchantName);
                    // 检查商户名称是否存在
                    boolean merchantNameFlag = checkMerchantName(merchant);
                    if (merchantNameFlag)
                    {
                        model.put("message", "操作失败,商名称已存在");
                        model.put("canback", true);
                        if (merchant.getMerchantType().equals(MerchantType.AGENT))
                        {
                            model.put("next_url", "Merchant/agentlist");
                        }
                        if (merchant.getMerchantType().equals(MerchantType.SUPPLY))
                        {
                            model.put("next_url", "Merchant/supplylist");
                        }
                        model.put("next_msg", "商户列表");
                        return PageConstant.PAGE_COMMON_NOTIFY;
                    }
                }
                if (StringUtil.isNotBlank(parentMerchantId))
                {
                    Merchant parentMerchant = (Merchant)identityService.findIdentityByIdentityId(
                        new Long(parentMerchantId), IdentityType.MERCHANT);
                    MerchantLevel ml = MerchantLevel.Zero;
                    if (parentMerchant != null)
                    {
                        ml = parentMerchant.getMerchantLevel();
                    }
                    merchant.setMerchantLevel(ml);
                    merchant.setParentIdentityId(new Long(parentMerchantId));
                }
                merchant.setMerchantCode(new MerchantCode(merchantCode));

                merchant.setMerchantName(merchantName);
                merchant.setIdentityType(IdentityType.MERCHANT);
                Merchant mer = (Merchant)identityService.saveIdentity(merchant,
                    loginPerson.getOperatorName());

                // List<ProductRelation>
                if (mer != null)
                {

                    if (MerchantType.AGENT.toString().equalsIgnoreCase(
                        merchant.getMerchantType().toString()))
                    {
                        List<AgentProductRelation> productRelations = downProductRelationService.getProductRelationByIdentityId(merchant.getId());
                        for (AgentProductRelation agentProductRelation : productRelations)
                        {
                            agentProductRelation.setIdentityName(mer.getMerchantName());
                            downProductRelationService.editAgentProductRelation(agentProductRelation, loginPerson.getDisplayName());
                        }
                    }
                    else if (MerchantType.SUPPLY.toString().equalsIgnoreCase(
                        merchant.getMerchantType().toString()))
                    {
                        List<SupplyProductRelation> productRelations = upProductRelationService.getProductRelationByIdentityId(merchant.getId());
                        for (SupplyProductRelation supplyProductRelation : productRelations)
                        {
                            supplyProductRelation.setIdentityName(mer.getMerchantName());
                            upProductRelationService.editSupplyProductRelation(supplyProductRelation, loginPerson.getDisplayName());
                        }
                    }

                    model.put("message", "更新成功");
                    model.put("canback", false);
                    if (merchant.getMerchantType().equals(MerchantType.AGENT))
                    {
                        model.put("next_url", "Merchant/agentlist");
                    }
                    if (merchant.getMerchantType().equals(MerchantType.SUPPLY))
                    {
                        model.put("next_url", "Merchant/supplylist");
                    }
                    model.put("next_msg", "商户列表");

                }
                else
                {
                    model.put("message", "操作失败");
                    model.put("canback", false);
                }

            }
            else
            {
                model.put("message", "商户信息不存在");
                model.put("canback", false);
            }
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (RpcException e)
        {
            logger.debug("[MerchantController:doEdit()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/enable", method = RequestMethod.GET)
    @ResponseBody
    public Object enable(@RequestParam("id")
    String id)
    {
        Map<String, String> model = new HashMap<String, String>();
        IdentityStatus identityStatus = new IdentityStatus(Constant.MerchantStatus.ENABLE);
        Merchant merchant = (Merchant)identityService.updateIdentityStatus(identityStatus,
            new Long(id), IdentityType.MERCHANT);// merchantStatusManagement.enableMerchant(new
                                                 // Long(id));
        if (merchant != null)
        {
            model.put("message", "操作成功");
            model.put("flage", "true");
        }
        else
        {
            model.put("message", "操作失败");
            model.put("flage", "false");
        }
        return model;

    }

    @RequestMapping(value = "/disable", method = RequestMethod.GET)
    @ResponseBody
    public Object disable(@RequestParam("id")
    String id)
    {
        Map<String, String> model = new HashMap<String, String>();
        IdentityStatus identityStatus = new IdentityStatus(Constant.MerchantStatus.DISABLE);
        Merchant merchant = (Merchant)identityService.updateIdentityStatus(identityStatus,
            new Long(id), IdentityType.MERCHANT);// disableMerchant(new Long(id));
        if (merchant != null)
        {
            model.put("message", "操作成功");
            model.put("flage", "true");
        }
        else
        {
            model.put("message", "操作失败");
            model.put("flage", "false");
        }
        return model;
    }

    /**
     * 商户账户管理页面
     * 
     * @param merchant
     * @param model
     * @return
     */
    @RequestMapping(value = "/toEditMerchantAccount")
    public String toEditMerchantAccount(@ModelAttribute("id")
    Long id, ModelMap model)
    {
        try
        {
            Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(id,
                IdentityType.MERCHANT);
            merchant.setIdentityType(IdentityType.MERCHANT);
            List<Account> accounts = identityAccountRoleService.queryAccountByIdentity(merchant,
                null);
            if (accounts == null)
            {
                accounts = new ArrayList<Account>();
            }
            model.addAttribute("SYSTEM_DEBIT", Constant.AccountType.SYSTEM_DEBIT);
            model.addAttribute("SYSTEM_PROFIT", Constant.AccountType.SYSTEM_PROFIT);
            model.addAttribute("accounts", accounts);
            model.addAttribute("merchant", merchant);
            model.addAttribute("counttotal", accounts.size());
            return "/account/MerchantAccountList.ftl";
        }
        catch (RpcException e)
        {
            logger.debug("[MerchantController:toEditMerchantAccount()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 下游商户账户管理页面
     * 
     * @param merchant
     * @param model
     * @return
     */
    @RequestMapping(value = "/allAgentAccountList")
    public String allAgentCurrencyAccountList(@RequestParam(value = "page", defaultValue = "1")
                                              int pageNumber,
                                              @RequestParam(value = "pageSize", defaultValue = PageConstant.LIST_PAGE_SIZE)
                                              int pageSize,
                                              @RequestParam(value = "sortType", defaultValue = PageConstant.LIST_ORDER_SORT)
                                              String sortType,
                                              @RequestParam(value = "merchantName", defaultValue = "")
                                              String merchantName,
                                              @RequestParam(value = "accountTypeId", defaultValue = Constant.AccountType.MERCHANT_DEBIT
                                                                                                    + "")
                                              String accountTypeIdStr, ModelMap model,
                                              ServletRequest request)
    {
        Map<String, Object> searchParams = new HashMap<String, Object>();
        try
        {

            Long accountTypeId = new Long(0);
            if (StringUtil.isNotBlank(accountTypeIdStr))
            {
                accountTypeId = new Long(accountTypeIdStr);
            }
            if (StringUtil.isNotBlank(merchantName))
            {
                searchParams.put(EntityConstant.Merchant.MERCHANT_NAME, merchantName);
            }
            else
            {
                searchParams.put(EntityConstant.Merchant.MERCHANT_NAME, "");
            }

            if (StringUtil.isNotBlank(accountTypeIdStr))
            {
                searchParams.put(EntityConstant.IdentityAccountRole.ACCOUNT_TYPE, accountTypeIdStr);
            }
            else
            {
                searchParams.put(EntityConstant.IdentityAccountRole.ACCOUNT_TYPE, "");
            }
            searchParams.put(EntityConstant.IdentityAccountRole.IDENTITY_TYPE,
                IdentityType.MERCHANT.toString());
            searchParams.put(EntityConstant.IdentityAccountRole.RELATION,
                Constant.Account.ACCOUNT_RELATION_TYPE_OWN);
            searchParams.put(EntityConstant.Merchant.MERCHANT_TYPE, MerchantType.AGENT.toString());

            YcPage<CCYAccountVo> page_list = new YcPage<CCYAccountVo>();
            List<CCYAccountVo> currencyAccountVos = new ArrayList<CCYAccountVo>();
            YcPage<CCYAccount> currencyAccounts = identityAccountRoleService.queryCurrencyAccountByMerchant(
                searchParams, pageNumber, pageSize);
            List<CCYAccount> currencyAccountList = currencyAccounts.getList();
            for (CCYAccount currencyAccount : currencyAccountList)
            {
                IdentityAccountRole identityAccountRole = identityAccountRoleService.queryIdentityAccountRoleByParames(
                    currencyAccount.getAccountId(), Constant.Account.ACCOUNT_RELATION_TYPE_OWN);
                CCYAccountVo vo = accountVoConvertor.execute(currencyAccount, identityAccountRole);
                currencyAccountVos.add(vo);
            }
            page_list.setList(currencyAccountVos);
            page_list.setCountTotal(currencyAccounts.getCountTotal());
            page_list.setPageTotal(currencyAccounts.getPageTotal());

            List<Merchant> downMerchant = merchantService.queryAllMerchant(MerchantType.AGENT,
                null);
            model.addAttribute("SYSTEM_DEBIT", Constant.AccountType.SYSTEM_DEBIT);
            model.addAttribute("SYSTEM_PROFIT", Constant.AccountType.SYSTEM_PROFIT);
            List<AccountType> accountType = accountTypeService.getAccountTypeByIdentityType(IdentityType.MERCHANT);
            model.addAttribute("downMerchant", downMerchant);
            model.addAttribute("accountType", accountType);
            model.addAttribute("accountTypeId", accountTypeId);
            model.addAttribute("merchantName", merchantName);
            model.addAttribute("accountlist", page_list.getList());
            model.addAttribute("page", pageNumber);
            model.addAttribute("counttotal", page_list.getCountTotal() + "");
            model.addAttribute("pagetotal", page_list.getPageTotal() + "");
            model.addAttribute("pageSize", pageSize);
            return "/account/allAgentAccountList.ftl";
        }
        catch (RpcException e)
        {
            logger.debug("[MerchantController:allAgentAccountList()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 供货商账户管理页面
     * 
     * @param merchant
     * @param model
     * @return
     */
    @RequestMapping(value = "/allSupplyAccountList")
    public String allSupplyCurrencyAccountList(@RequestParam(value = "page", defaultValue = "1")
                                               int pageNumber,
                                               @RequestParam(value = "pageSize", defaultValue = PageConstant.LIST_PAGE_SIZE)
                                               int pageSize,
                                               @RequestParam(value = "sortType", defaultValue = PageConstant.LIST_ORDER_SORT)
                                               String sortType,
                                               @RequestParam(value = "merchantName", defaultValue = "")
                                               String merchantName,
                                               @RequestParam(value = "accountTypeId", defaultValue = Constant.AccountType.MERCHANT_CREDIT
                                                                                                     + "")
                                               String accountTypeIdStr, ModelMap model,
                                               ServletRequest request)
    {
        Map<String, Object> searchParams = new HashMap<String, Object>();
        try
        {
            Long accountTypeId = new Long(0);
            if (StringUtil.isNotBlank(accountTypeIdStr))
            {
                accountTypeId = new Long(accountTypeIdStr);
            }

            if (StringUtil.isNotBlank(merchantName))
            {
                searchParams.put(EntityConstant.Merchant.MERCHANT_NAME, merchantName);
            }
            else
            {
                searchParams.put(EntityConstant.Merchant.MERCHANT_NAME, "");
            }

            if (StringUtil.isNotBlank(accountTypeIdStr))
            {
                searchParams.put(EntityConstant.IdentityAccountRole.ACCOUNT_TYPE, accountTypeIdStr);
            }
            else
            {
                searchParams.put(EntityConstant.IdentityAccountRole.ACCOUNT_TYPE, "");
            }
            searchParams.put(EntityConstant.IdentityAccountRole.IDENTITY_TYPE,
                IdentityType.MERCHANT.toString());
            searchParams.put(EntityConstant.IdentityAccountRole.RELATION,
                Constant.Account.ACCOUNT_RELATION_TYPE_OWN);
            searchParams.put(EntityConstant.Merchant.MERCHANT_TYPE, MerchantType.SUPPLY.toString());

            YcPage<CCYAccountVo> page_list = new YcPage<CCYAccountVo>();
            List<CCYAccountVo> currencyAccountVos = new ArrayList<CCYAccountVo>();
            YcPage<CCYAccount> currencyAccounts = identityAccountRoleService.queryCurrencyAccountByMerchant(
                searchParams, pageNumber, pageSize);
            List<CCYAccount> currencyAccountList = currencyAccounts.getList();
            for (CCYAccount currencyAccount : currencyAccountList)
            {
                IdentityAccountRole identityAccountRole = identityAccountRoleService.queryIdentityAccountRoleByParames(
                    currencyAccount.getAccountId(), Constant.Account.ACCOUNT_RELATION_TYPE_OWN);
                CCYAccountVo vo = accountVoConvertor.execute(currencyAccount, identityAccountRole);
                currencyAccountVos.add(vo);
            }
            page_list.setList(currencyAccountVos);
            page_list.setCountTotal(currencyAccounts.getCountTotal());
            page_list.setPageTotal(currencyAccounts.getPageTotal());

            List<Merchant> downMerchant = merchantService.queryAllMerchant(MerchantType.SUPPLY,
                null);
            List<AccountType> accountType = accountTypeService.getAccountTypeByIdentityType(IdentityType.MERCHANT);
            model.addAttribute("downMerchant", downMerchant);
            model.addAttribute("accountType", accountType);
            model.addAttribute("accountTypeId", accountTypeId);
            model.addAttribute("downMerchant", downMerchant);
            model.addAttribute("merchantName", merchantName);
            model.addAttribute("accountlist", page_list.getList());
            model.addAttribute("page", pageNumber);
            model.addAttribute("counttotal", page_list.getCountTotal() + "");
            model.addAttribute("pagetotal", page_list.getPageTotal() + "");
            model.addAttribute("pageSize", pageSize);
            return "/account/allSupplyAccountList.ftl";
        }
        catch (RpcException e)
        {
            logger.debug("[MerchantController:allSupplyAccountList()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/agentProductRelationList")
    public String downProductRelationList(@RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = "auto")
    String sortType, @RequestParam(value = "merchantId")
    String merchantId, ModelMap model, ServletRequest request)
    {
        try
        {
            Map<String, Object> searchParams = new HashMap<String, Object>();
            if (!merchantId.isEmpty())
            {
                String identityType = IdentityType.MERCHANT.toString();
                searchParams.put(EntityConstant.Identity.IDENTITY_ID,
                    merchantId);
                searchParams.put(EntityConstant.Identity.IDENTITY_TYPE,
                    identityType);
            }
            BSort bsort = new BSort(BSort.Direct.DESC, EntityConstant.AgentProductRelation.ID);
            YcPage<AgentProductRelation> page_list = downProductRelationService.queryAgentProductRelation(
                searchParams, pageNumber, pageSize, bsort);

            model.addAttribute("merchantId", merchantId);
            model.addAttribute("mlist", page_list.getList());
            model.addAttribute("page", pageNumber);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("counttotal", page_list.getCountTotal() + "");
            model.addAttribute("pagetotal", page_list.getPageTotal() + "");
            return "product/agentProductRelationList";
        }
        catch (RpcException e)
        {
            logger.debug("[MerchantController:agentProductRelationList()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/supplyProductRelationList")
    public String supplyProductRelationList(@RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = "auto")
    String sortType, @RequestParam(value = "merchantId")
    String merchantId, ModelMap model, ServletRequest request)
    {

        try
        {
            Map<String, Object> searchParams = new HashMap<String, Object>();
            if (!merchantId.isEmpty())
            {
                String identityType = IdentityType.MERCHANT.toString();
                searchParams.put(EntityConstant.Identity.IDENTITY_ID,
                    merchantId);
                searchParams.put(EntityConstant.Identity.IDENTITY_TYPE,
                    identityType);
            }
            BSort bsort = new BSort(BSort.Direct.DESC, EntityConstant.SupplyProductRelation.ID);
            YcPage<SupplyProductRelation> page_list = upProductRelationService.querySupplyProductRelation(
                searchParams, pageNumber, pageSize, bsort);

            model.addAttribute("merchantId", merchantId);
            model.addAttribute("mlist", page_list.getList());
            model.addAttribute("page", pageNumber);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("counttotal", page_list.getCountTotal() + "");
            model.addAttribute("pagetotal", page_list.getPageTotal() + "");
            return "product/supplyProductRelationList";
        }
        catch (RpcException e)
        {
            logger.debug("[MerchantController:supplyProductRelationList()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/merchantInterfaceConfList")
    public String merchantInterfaceConfList(@RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "page.size", defaultValue = PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = "auto")
    String sortType, @RequestParam(value = "merchantId", defaultValue = "")
    String merchantId, @RequestParam(value = "encoding", defaultValue = "")
    String encoding, @RequestParam(value = "connectionType", defaultValue = "")
    String connectionType, @RequestParam(value = "interfaceType", defaultValue = "")
    String interfaceType, ModelMap model, ServletRequest request)
    {
        try
        {
            Map<String, Object> searchParams = new HashMap<String, Object>();
            if (StringUtil.isNotBlank(merchantId))
            {
                searchParams.put(Operator.EQ + "_"
                                 + EntityConstant.InterfacePacketsDefinition.MERCHANT_ID,
                    merchantId);
            }
            if (StringUtil.isNotBlank(encoding))
            {
                searchParams.put(Operator.EQ + "_"
                                 + EntityConstant.InterfacePacketsDefinition.ENCODING, encoding);
            }
            if (StringUtil.isNotBlank(connectionType))
            {
                searchParams.put(Operator.EQ + "_"
                                 + EntityConstant.InterfacePacketsDefinition.CONNECTION_TYPE,
                    connectionType);
            }
            if (StringUtil.isNotBlank(interfaceType))
            {
                searchParams.put(Operator.EQ + "_"
                                 + EntityConstant.InterfacePacketsDefinition.INTERFACE_TYPE,
                    interfaceType);
            }
            BSort bsort = new BSort(BSort.Direct.DESC,
                EntityConstant.InterfacePacketsDefinition.MERCHANT_ID);
            YcPage<InterfacePacketsDefinition> interfacePacketsDefinitions = interfaceService.queryInterfacePacketsDefinition(
                searchParams, pageNumber, pageSize, bsort);
            Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(new Long(
                merchantId), IdentityType.MERCHANT);
            model.addAttribute("merchant", merchant);
            model.addAttribute("interfacePacketsDefinitions",
                interfacePacketsDefinitions.getList());
            model.addAttribute("page", pageNumber);
            model.addAttribute("counttotal", interfacePacketsDefinitions.getCountTotal() + "");
            model.addAttribute("pagetotal", interfacePacketsDefinitions.getPageTotal() + "");
            return "interface/interfacePacketsDefinitionList";
        }
        catch (RpcException e)
        {
            logger.debug("[MerchantController:merchantInterfaceConfList()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 获取城市列表
     * 
     * @return
     */
    @RequestMapping(value = "/getParentMerchant", method = RequestMethod.POST)
    @ResponseBody
    public String getParentMerchant(@RequestParam("merchantName")
    String merchantName, ModelMap model)
    {
        try
        {
            merchantName = new String(merchantName.getBytes("ISO-8859-1"), "gbk");
            logger.info("[MerchantController:getParentMerchant(" + merchantName + ")]");
            List<Merchant> list2 = merchantService.queryMerchantByMerchantNameFuzzy(merchantName,
                MerchantType.AGENT, MerchantLevel.Two);

            int i = 0;
            String result = "";
            while (i < list2.size())
            {
                Merchant merchant = list2.get(i);
                result = result + merchant.getId() + "*" + merchant.getMerchantName() + "|";
                i++ ;
            }
            return result;
        }
        catch (RpcException e)
        {
            logger.debug("[MerchantController:getParentMerchant()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (Exception e)
        {
            logger.debug("[MerchantController:getParentMerchant()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 查看商户详情
     * 
     * @return
     */
    @RequestMapping(value = "/merchantview", method = RequestMethod.GET)
    public String merchantview(@RequestParam("merchantId")
    Long merchantId, ModelMap model)
    {
        try
        {
            logger.info("[MerchantController:merchantview(" + merchantId + ")]");
            List<Merchant> parentMerchantList = merchantService.queryParentMerchantByMerchantId(merchantId);

            parentMerchantList = changeListProt(parentMerchantList);
            int pMerchantSize = parentMerchantList.size();
            Merchant merchant0 = new Merchant();
            merchant0.setId(0l);
            Merchant merchant1 = new Merchant();
            merchant1.setId(0l);
            Merchant parentMerchant = new Merchant();
            if (pMerchantSize > 0)
            {
                if (pMerchantSize == 1)
                {
                    merchant0 = parentMerchantList.get(0);
                    parentMerchant = merchant0;
                }
                else if (pMerchantSize == 2)
                {
                    merchant0 = parentMerchantList.get(0);
                    merchant1 = parentMerchantList.get(1);
                    parentMerchant = merchant1;
                }
            }
            Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(merchantId,
                IdentityType.MERCHANT);
            // 获取商户秘钥
            SecurityCredential securityCredential = null;
            if (MerchantType.AGENT.equals(merchant.getMerchantType()))
            {
                securityCredential = securityCredentialService.querySecurityCredentialByParam(
                    merchant.getId(), IdentityType.MERCHANT,
                    Constant.SecurityCredentialType.AGENTMD5KEY, null);
            }
            else
            {
                securityCredential = securityCredentialService.querySecurityCredentialByParam(
                    merchant.getId(), IdentityType.MERCHANT,
                    Constant.SecurityCredentialType.SUPPLYMD5KEY, null);
            }

            String key = StringUtil.initString();
            if (securityCredential != null)
            {
                String desKey = securityCredential.getSecurityValue();
                key = securityCredentialManagerService.decrypt(desKey,
                    Constant.EncryptType.ENCRYPT_TYPE_3DES);
            }
            List<RebateRuleAssist> rebateRuleList = rebateRuleQueryManager.queryRebateRulesByParams(
                merchantId, Constant.RebateRuleMerchantType.MERCHANT_AND_REBATEMERCHANT);
            List<RebateRuleAssist> result = new ArrayList<RebateRuleAssist>();
            for (RebateRuleAssist rebateRuleAssist : rebateRuleList)
            {
                String ruleName = getTrdingVolumeStr(
                    rebateRuleAssist.getRebateRule().getRebateRuleId(),
                    rebateRuleAssist.getRebateTradingVolume());
                rebateRuleAssist.setRuleName(ruleName);
                result.add(rebateRuleAssist);
            }
            model = getMerchantList(model);
            model.addAttribute("merchant0", merchant0);
            model.addAttribute("merchant1", merchant1);
            model.addAttribute("merchant", merchant);
            model.addAttribute("parentMerchant", parentMerchant);
            model.addAttribute("rebateRuleList", rebateRuleList);
            model.addAttribute("key", key);
            if (MerchantType.AGENT.equals(merchant.getMerchantType()))
            {
                return PageConstant.PAGE_MERCHANT_AGENT_VIEW;
            }
            else
            {
                return PageConstant.PAGE_MERCHANT_SUPPLY_VIEW;
            }
        }
        catch (RpcException e)
        {
            logger.debug("[MerchantController:merchantview()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 组装在界面显示的交易区间格式
     * 
     * @param rebateRuleId
     * @param rebateTradingVolumeList
     * @return
     */
    public String getTrdingVolumeStr(Long rebateRuleId,
                                     List<RebateTradingVolume> rebateTradingVolumeList)
    {
        logger.debug("[RebateRuleController:getTrdingVolumeStr(" + rebateRuleId + ")]");
        String backStr = StringUtil.initString();
        if (rebateRuleId != null)
        {
            RebateRuleAssist rebateRuleAssist = rebateRuleService.queryRebateRuleById(rebateRuleId);
            RebateRule rebateRule = rebateRuleAssist.getRebateRule();
            for (RebateTradingVolume rebateTradingVolume : rebateTradingVolumeList)
            {
                String maxHigh = rebateTradingVolume.getTradingVolumeHigh().toString();
                if (rebateTradingVolume.getTradingVolumeHigh().equals(
                    Constant.RebateTransactionVolume.MAX_TRADING_VOLUME))
                {
                    maxHigh = "无穷";
                }
                backStr = backStr + "区间：<b>" + rebateTradingVolume.getTradingVolumeLow() + "~"
                          + maxHigh + "</b> 返佣比例：<b>"
                          + rebateTradingVolume.getDiscount().toString() + "</b>";
                if (rebateRule.getRebateType() == 0)
                {
                    backStr = backStr + "%</br>";
                }
                else
                {
                    backStr = backStr + "元</br>";
                }
            }
        }
        logger.debug("[RebateRuleController:getTrdingVolumeStr(" + backStr + ")][返回数据]");
        return backStr;
    }

    /**
     * 进入所有层级展示界面
     * 
     * @return
     */
    @RequestMapping(value = "/merchanttreelist", method = RequestMethod.GET)
    public String listMenu(ModelMap model)
    {
        try
        {
            // 树的代码集合
            StringBuffer sb = new StringBuffer();
            // 拼接树ZERO节点
            sb = getTreeStr();
            model.addAttribute("tree", sb.toString());
            return PageConstant.PAGE_MERCHANT_TREE;
        }
        catch (RpcException e)
        {
            logger.debug("[MerchantController:merchanttreelist()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    public StringBuffer getTreeStr()
    {
        StringBuffer sb = new StringBuffer();
        List<Merchant> merchantlist0 = merchantService.queryMerchantList(MerchantLevel.Zero,
            Constant.MerchantStatus.ENABLE);
        List<Merchant> merchantlist1 = merchantService.queryMerchantList(MerchantLevel.One,
            Constant.MerchantStatus.ENABLE);
        List<Merchant> merchantlist2 = merchantService.queryMerchantList(MerchantLevel.Two,
            Constant.MerchantStatus.ENABLE);
        List<Merchant> merchantlist3 = merchantService.queryMerchantList(MerchantLevel.Three,
            Constant.MerchantStatus.ENABLE);
        for (Merchant m0 : merchantlist0)
        {
            sb = getMenuStr(sb, m0.getId(), m0.getId(), m0.getMerchantName(),
                m0.getMerchantName(), m0.getMerchantName());
            for (Merchant m1 : merchantlist1)
            {
                if (m0.getId().equals(m1.getParentIdentityId()))
                {
                    sb = getMenuStr(sb, m1.getId(), m0.getId(), m1.getMerchantName(),
                        m0.getMerchantName(), m1.getMerchantName());
                    for (Merchant m2 : merchantlist2)
                    {
                        if (m1.getId().equals(m2.getParentIdentityId()))
                        {
                            sb = getMenuStr(sb, m2.getId(), m1.getId(), m2.getMerchantName(),
                                m1.getMerchantName(), m2.getMerchantName());
                            for (Merchant m3 : merchantlist3)
                            {
                                if (m2.getId().equals(m3.getParentIdentityId()))
                                {
                                    sb = getMenuStr(sb, m3.getId(), m2.getId(),
                                        m3.getMerchantName(), m2.getMerchantName(),
                                        m3.getMerchantName());
                                }
                            }
                        }
                    }
                }
            }
        }
        return sb;
    }

    public StringBuffer getMenuStr(StringBuffer sb, Long id, Long pid, String name, String pname,
                                   String portalName)
    {
        if (sb.length() > 0) sb.append(",");
        sb.append("{ key:" + id + ", pkey:" + pid + ", name:\"" + name + "\", title:\"" + pname
                  + "\"}");
        return sb;
    }

    @RequestMapping(value = "/queryMerchantsByName")
    @ResponseBody
    public List<Merchant> queryMerchantsByName(@RequestParam(value = "merchantName", defaultValue = "")
                                               String merchantName, Model model,
                                               ServletRequest request)
    {
        try
        {
            merchantName = new String(merchantName.getBytes("ISO-8859-1"), "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch block
            logger.error("转码异常:[" + e.getMessage() + "]");
        }
        List<Merchant> merchants = merchantService.queryMerchantsByName(merchantName);
        return merchants;
    }

    @RequestMapping(value = "/getAgentMerchant")
    @ResponseBody
    public String getAgentMerchant(@RequestParam(value = "merchantType", defaultValue = "")
    String merchantType, @RequestParam(value = "identityId", defaultValue = "")
    String identityId, Model model)
    {
        List<Merchant> merchants = merchantService.queryAllMerchant(
            MerchantType.valueOf(merchantType), null);
        StringBuffer merchantStr = new StringBuffer();
        merchantStr.append("<option value=\'\'>请选择</option>");
        if (merchants.size() > 0)
        {
            for (Iterator<Merchant> iterator2 = merchants.iterator(); iterator2.hasNext();)
            {
                Merchant merchant = iterator2.next();
                if (StringUtil.isNotBlank(identityId) && BeanUtils.isNotNull(merchant.getId())
                    && identityId.equals(merchant.getId().toString()))
                {
                    merchantStr.append("<option value=\'" + merchant.getId()
                                       + "\' selected=\'selected\'>" + merchant.getMerchantName()
                                       + "</option>");
                }
                else
                {
                    merchantStr.append("<option value=\'" + merchant.getId() + "\'>"
                                       + merchant.getMerchantName() + "</option>");
                }

            }
        }

        return merchantStr.toString();
    }

}