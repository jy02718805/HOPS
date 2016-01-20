package com.yuecheng.hops.aportal.web.identity;


import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.account.entity.CCYAccount;
import com.yuecheng.hops.account.entity.role.IdentityAccountRole;
import com.yuecheng.hops.account.service.CCYAccountService;
import com.yuecheng.hops.account.service.IdentityAccountRoleService;
import com.yuecheng.hops.aportal.constant.PageConstant;
import com.yuecheng.hops.aportal.web.BaseControl;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.Constant.IdentityConstants;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.enump.OperatorType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.security.RSAUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.IdentityStatus;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.entity.mirror.Person;
import com.yuecheng.hops.identity.entity.operator.Operator;
import com.yuecheng.hops.identity.service.IdentityService;
import com.yuecheng.hops.identity.service.customer.PersonService;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.identity.service.operator.OperatorPasswordManagement;
import com.yuecheng.hops.identity.service.operator.OperatorService;
import com.yuecheng.hops.identity.service.sp.SpService;
import com.yuecheng.hops.privilege.entity.IdentityRoleSelect;
import com.yuecheng.hops.privilege.service.IdentityRoleQueryService;
import com.yuecheng.hops.privilege.service.IdentityRoleService;
import com.yuecheng.hops.security.entity.SecurityCredential;
import com.yuecheng.hops.security.entity.SecurityCredentialType;
import com.yuecheng.hops.security.service.LoginService;
import com.yuecheng.hops.security.service.SecurityCredentialManagerService;
import com.yuecheng.hops.security.service.SecurityCredentialService;
import com.yuecheng.hops.security.service.SecurityKeystoreService;
import com.yuecheng.hops.security.service.SecurityTypeService;


@Controller
@RequestMapping(value = "/Operator")
public class OperatorController extends BaseControl
{

    private static final Logger LOGGER = LoggerFactory.getLogger(OperatorController.class);

    @Autowired
    private OperatorService operatorService;

    @Autowired
    private OperatorPasswordManagement operatorPasswordManagement;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private PersonService personService;

    @Autowired
    private IdentityRoleService identityRoleService;

    @Autowired
    private IdentityRoleQueryService iRoleService;

    @Autowired
    private SpService spService;

    @Autowired
    private SecurityCredentialService securityCredentialService;

    @Autowired
    IdentityAccountRoleService identityAccountRoleService;

    @Autowired
    IdentityService identityService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private SecurityCredentialManagerService securityCredentialManagerService;

    @Autowired
    private SecurityTypeService securityTypeService;

    @Autowired
    private SecurityKeystoreService securityKeystoreService;

    @Autowired
    private CCYAccountService ccyAccountService;

    private static Logger logger = LoggerFactory.getLogger(OperatorController.class);

    private org.springside.modules.persistence.SearchFilter.Operator springSideOperator;

    @Autowired
    HttpSession session;

    @RequestMapping(value = "/addMerchantOperator", method = RequestMethod.GET)
    public String addMerchantOperatorView(ModelMap model)
    {
        com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
        Merchant merchant = null;
        if (loginPerson != null)
        {
            merchant = merchantService.queryMerchantById(loginPerson.getOwnerIdentityId());
        }
        Map<String, Object> keyMap = securityKeystoreService.getKeyObjectToMap(Constant.SecurityCredential.RSA_KEY);
        RSAPublicKey publicKey = RSAUtils.getKeyByString(keyMap.get(Constant.RSACacheKey.RSA_PUBLICKEY)
                                                         + "");

        model.addAttribute("modulus", RSAUtils.getModulus(publicKey));
        model.addAttribute("exponent", RSAUtils.getPublicExponent(publicKey));
        model.addAttribute("merchant", merchant);
        return PageConstant.PAGE_MERCHANT_OPERATOR_ADD;
    }

    @RequestMapping(value = "/addMerchantOperator", method = RequestMethod.POST)
    public String addMerchantOperatorDo(@ModelAttribute("operator")
    Operator operator, @RequestParam(value = "type", defaultValue = "")
    String type, ModelMap model)
    {
        Operator operatorIs = operatorService.queryOperatorByOperatorName(operator.getOperatorName());
        if (operatorIs != null)
        {
            model.put("message", "操作失败[操作员名称已存在]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        try
        {
            securityCredentialManagerService.checkSecurity(
                Constant.SecurityCredentialType.PASSWORD, operator.getLoginPassword());
            com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
            Person person = operator.getPerson();
            operator.setOwnerIdentityType(IdentityType.MERCHANT);
            // 设置操作员基本信息
            person.setUpdateName(loginPerson.getOperatorName());
            person.setUpdateTime(new Date());
            person.setCreateTime(new Date());
            person = personService.savePerson(person);
            operator.setPerson(person);
            operator.setIdentityStatus(new IdentityStatus(IdentityConstants.OPERATOR_DISABLE));
            // 设置密钥信息
            SecurityCredential securityCredential = new SecurityCredential();
            securityCredential.setUpdateUser(loginPerson.getOperatorName());
            securityCredential.setUpdateDate(new Date());
            securityCredential.setStatus(Constant.SecurityCredentialStatus.DISABLE_STATUS);
            if (!StringUtils.isBlank(type))
            {
                operator.setOperatorType(OperatorType.valueOf(type));
            }
            operator.setIdentityType(IdentityType.OPERATOR);
            Operator persister = (Operator)operatorService.regist(operator,
                operator.getLoginPassword(), null, loginPerson.getOperatorName());

            if (persister != null)
            {
                model.put("message", "添加操作员成功");
                model.put("canback", false);
                model.put("next_url", "Operator/merchantOperatorList");
                model.put("next_msg", "操作员列表");
            }
            else
            {
                model.put("message", "操作失败");
                model.put("canback", true);
            }
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (RpcException e)
        {
            logger.debug("[OperatorController:addMerchantOperator()]" + e.getMessage());
            model.put("message", "操作失败 :[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (Exception e)
        {
            logger.debug("[OperatorController:addMerchantOperator()]" + e.getMessage());
            model.put("message", "操作失败 :[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editView(@RequestParam("oid")
    String id, ModelMap model)
    {
        try
        {
            Operator operator = (Operator)identityService.findIdentityByIdentityId(new Long(id),
                IdentityType.OPERATOR);
            model.put("operator", operator);
            return PageConstant.PAGE_OPERATOR_EDIT;
        }
        catch (RpcException e)
        {
            logger.debug("[OperatorController:editView()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/tosetrole", method = RequestMethod.GET)
    public String toSetOpertorRole(@RequestParam("oid")
    Long oid, ModelMap model)
    {
        try
        {
            Operator operator = (Operator)identityService.findIdentityByIdentityId(oid,
                IdentityType.OPERATOR);
            List<IdentityRoleSelect> irSelectlist = null;
            if (operator != null && operator.getOperatorType().equals(OperatorType.SP_OPERATOR))
            {
                irSelectlist = iRoleService.queryIRSelectByRoleType(oid, IdentityType.OPERATOR,
                    Constant.RoleType.ROLE_TYPE_SP);
            }
            else if (operator != null
                     && operator.getOperatorType().equals(OperatorType.MERCHANT_OPERATOR))
            {
                irSelectlist = iRoleService.queryIRSelectByRoleType(oid, IdentityType.OPERATOR,
                    Constant.RoleType.ROLE_TYPE_MERCHANT);
            }
            model.put("rolelist", irSelectlist);
            model.put("operator", operator);
            return PageConstant.PAGE_OPERATOR_SETROLE;
        }
        catch (RpcException e)
        {
            logger.debug("[OperatorController:toSetOpertorRole()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (Exception e)
        {
            logger.debug("[OperatorController:toSetOpertorRole()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/setRoleDo", method = RequestMethod.GET)
    @ResponseBody
    public Object setRoleDo(@RequestParam("oid")
    String oid, @RequestParam("roleid")
    String roleid)
    {
        Map<String, String> model = new HashMap<String, String>();
        if (!StringUtils.isBlank(oid) && !StringUtils.isBlank(roleid))
        {
            String[] roleidlist = roleid.split(Constant.StringSplitUtil.DECODE);
            String result = identityRoleService.saveIdentityRoleList(roleidlist, new Long(oid),
                IdentityType.OPERATOR, "system");
            if (result != null)
            {
                model.put("message", "操作成功");

            }
            else
            {
                model.put("message", "操作失败");

            }
        }
        else
        {
            model.put("message", "权限列表不能为空");

        }
        return model;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editPost(@ModelAttribute("operator")
    Operator operator, ModelMap model)
    {
        try
        {
            if (operator.getId() == null)
            {
                model.put("message", "操作失败，操作员ID为空");
                model.put("canback", true);
                return PageConstant.PAGE_COMMON_NOTIFY;
            }
            Operator result = operatorService.editOperatorInfo(operator);
            if (result == null)
            {
                model.put("message", "操作失败");
                model.put("canback", true);
            }
            else
            {
                model.put("message", "修改成功");
                model.put("canback", false);
                model.put("next_url", "Operator/merchantOperatorList");
                model.put("next_msg", "操作员列表");
            }

            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (RpcException e)
        {
            logger.debug("[OperatorController:editPost()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/enable", method = RequestMethod.GET)
    @ResponseBody
    public Object enbale(@RequestParam(value = "id", defaultValue = "")
    String id)
    {
        Map<String, String> result = new HashMap<String, String>();

        if (StringUtils.isBlank(id))
        {
            result.put("flage", "false");
            result.put("msg", "id不能为空");
            return result;
        }
        Operator operator = (Operator)identityService.findIdentityByIdentityId(new Long(id),
            IdentityType.OPERATOR);
        if (operator == null)
        {
            result.put("flage", "false");
            result.put("msg", "操作员不存在");
            return result;
        }
        IdentityStatus identityStatus = new IdentityStatus(
            Constant.IdentityConstants.OPERATOR_ENABLE);
        Operator newOperator = (Operator)identityService.updateIdentityStatus(identityStatus,
            new Long(id), IdentityType.OPERATOR);
        // Operator newOperator = operatorStatusManagement.updateStatus(operator,
        // Constant.IdentityConstants.OPERATOR_ENABLE);
        if (newOperator == null)
        {
            result.put("flage", "false");
            result.put("msg", "操作员不存在");
        }
        else
        {
            result.put("flage", "true");
            result.put("msg", "操作成功");
        }
        return result;

    }

    @RequestMapping(value = "/disable", method = RequestMethod.GET)
    @ResponseBody
    public Object disable(@RequestParam(value = "id", defaultValue = "")
    String id)
    {
        Map<String, String> result = new HashMap<String, String>();
        if (StringUtils.isBlank(id))
        {
            result.put("flage", "false");
            result.put("msg", "id不能为空");
            return result;
        }
        Operator operator = (Operator)identityService.findIdentityByIdentityId(new Long(id),
            IdentityType.OPERATOR);

        if (operator == null)
        {
            result.put("flage", "false");
            result.put("msg", "操作员不存在");
            return result;
        }
        IdentityStatus identityStatus = new IdentityStatus(
            Constant.IdentityConstants.OPERATOR_DISABLE);
        Operator newOperator = (Operator)identityService.updateIdentityStatus(identityStatus,
            new Long(id), IdentityType.OPERATOR);
        // Operator newOperator =
        // operatorStatusManagement.updateStatus(operator,Constant.IdentityConstants.OPERATOR_DISABLE);
        if (newOperator == null)
        {
            result.put("flage", "false");
            result.put("msg", "操作员不存在");
        }
        else
        {
            result.put("flage", "true");
            result.put("msg", "操作成功");
        }
        return result;
    }

    @RequestMapping(value = "/toModifyPassword", method = RequestMethod.GET)
    public Object toModifyPassword(@RequestParam(value = "id")
    String id, ModelMap model)
    {
        Map<String, Object> keyMap = securityKeystoreService.getKeyObjectToMap(Constant.SecurityCredential.RSA_KEY);
        RSAPublicKey publicKey = RSAUtils.getKeyByString(keyMap.get(Constant.RSACacheKey.RSA_PUBLICKEY)
                                                         + "");
        Operator op = (Operator)identityService.findIdentityByIdentityId(new Long(id),
            IdentityType.OPERATOR);
        model.addAttribute("modulus", RSAUtils.getModulus(publicKey));
        model.addAttribute("exponent", RSAUtils.getPublicExponent(publicKey));
        model.addAttribute("operator", op);
        return PageConstant.PAGE_OPERATOR_RESETPASSWORD;
    }

    @RequestMapping(value = "/viewinfo", method = RequestMethod.GET)
    public Object viewinfo(@RequestParam(value = "id")
    String id, ModelMap model)
    {

        Operator op = (Operator)identityService.findIdentityByIdentityId(new Long(id),
            IdentityType.OPERATOR);
        Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(
            op.getOwnerIdentityId(), IdentityType.MERCHANT);
        List<IdentityAccountRole> merchantAccountRoleList = identityAccountRoleService.queryIdentityAccountRoleByParams(
            merchant.getId(), IdentityType.MERCHANT.toString(),
            Constant.AccountType.MERCHANT_DEBIT);
        CCYAccount merchantAccount = new CCYAccount();
        if (merchantAccountRoleList.size() > 0 && merchantAccountRoleList.size() == 1)
        {
            IdentityAccountRole merchantAccountRole = merchantAccountRoleList.get(0);
            merchantAccount = (CCYAccount)ccyAccountService.getAccountByParams(
                merchantAccountRole.getAccountId(), merchantAccountRole.getAccountType(), null);

        }
        model.addAttribute("merchantAccount", merchantAccount);
        model.addAttribute("operator", op);
        model.addAttribute("merchant", merchant);
        return PageConstant.PAGE_OPERATOR_VIEWINFO;
    }

    @RequestMapping(value = "/modifyPasswordDO", method = RequestMethod.POST)
    public String modifyPassordDo(@RequestParam(value = "id")
    String id, @RequestParam(value = "rsaOldpwd")
    String rsaOldpwd, @RequestParam(value = "rsaNewpwd")
    String rsaNewpwd, ModelMap model)
    {
        if (StringUtils.isBlank(id) || StringUtils.isBlank(rsaNewpwd)
            || StringUtils.isBlank(rsaOldpwd))
        {
            model.put("message", "数据不完整，操作失败");
            model.put("canback", true);
        }
        
        
        try
        {
            securityCredentialManagerService.checkSecurity(
                Constant.SecurityCredentialType.PASSWORD, rsaNewpwd);
        }
        catch (Exception e)
        {
            logger.debug("[OperatorController:modifyPasswordDO()]" + e.getMessage());
            model.put("message", "操作失败 :[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        
        try
        {
            com.yuecheng.hops.identity.entity.operator.Operator person = getLoginUser();
            
            Long result = operatorPasswordManagement.resetPassword(new Long(id), rsaOldpwd,
                rsaNewpwd, person.getOperatorName());

            if (result != null)
            {
                model.put("message", "操作成功");
                model.put("canback", true);
                model.put("next_url", "Operator/merchantOperatorList");
                model.put("next_msg", "返回操作员列表");
            }
            else
            {
                model.put("message", "操作失败");
                model.put("canback", true);
            }
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (RpcException e)
        {
            logger.debug("[OperatorController:modifyPasswordDO()]" + e.getMessage());
            model.put("message", "操作失败 :[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (Exception e)
        {
            logger.debug("[OperatorController:modifyPasswordDO()]" + e.getMessage());
            model.put("message", "操作失败 :[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public Object resetPassword(@RequestParam(value = "id")
    String id)
    {
        com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
        LOGGER.info("进入重置密码方法");
        Map<String, String> result = new HashMap<String, String>();
        if (StringUtils.isBlank(id))
        {
            result.put("flage", "false");
            result.put("msg", "操作员编号不能为空");
            return result;
        }

        Operator op = operatorPasswordManagement.requestResetPasswordEmail(new Long(id),
            loginPerson.getOperatorName());
        if (op != null)
        {
            // 发送邮件
            SecurityCredentialType securityTyp = securityTypeService.querySecurityTypeByTypeName(Constant.SecurityCredentialType.PASSWORD);
            SecurityCredential securityCredential = securityCredentialService.querySecurityCredentialByParams(
                op.getId(), IdentityType.OPERATOR,
                securityTyp != null ? securityTyp.getSecurityTypeId() : null,
                Constant.SecurityCredentialStatus.ENABLE_STATUS);
            String tempPassword = securityCredential.getSecurityValue();
            logger.debug("portal:" + tempPassword);
            String msg = "重置密码成功";
            // boolean sendResult =
            // emailService.SendEmail("密码重置通知邮件","您的临时登录密码为:"+tempPassword+",请您及时修改密码",
            // op.getPerson().getEmail());
            //
            // if(sendResult){
            // msg += ",已发送通知邮件";
            // }else{
            // msg += ",邮件发送失败";
            // }
            result.put("flage", "true");
            result.put("msg", msg);
        }
        else
        {
            result.put("flage", "false");
            result.put("msg", "重置密码失败");
        }
        return result;

    }

    @SuppressWarnings("static-access")
    @RequestMapping(value = "/merchantOperatorList", method = RequestMethod.GET)
    public String merchantOperatorList(@RequestParam(value = "ownerIdentityId", defaultValue = "0")
                                       Long ownerIdentityId,
                                       @RequestParam(value = "username", defaultValue = "")
                                       String userName,
                                       @RequestParam(value = "page", defaultValue = "1")
                                       int pageNumber,
                                       @RequestParam(value = "pageSize", defaultValue = PageConstant.LIST_PAGE_SIZE)
                                       int pageSize,
                                       @RequestParam(value = "sortType", defaultValue = PageConstant.LIST_ORDER_SORT)
                                       String sortType, ModelMap model, ServletRequest request)
    {
        try
        {
            com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
            if (loginPerson != null)
            {
                ownerIdentityId = loginPerson.getOwnerIdentityId();
            }
            Map<String, Object> searchParams = new HashMap<String, Object>();
            userName = new String(userName.getBytes("ISO-8859-1"), "UTF-8");
            if (!StringUtil.isNullOrEmpty(userName))
            {
                searchParams.put(springSideOperator.LIKE + "_"
                                 + EntityConstant.Operator.OPERATOR_NAME, userName);
            }
            if (ownerIdentityId != 0)
            {
                searchParams.put(springSideOperator.EQ + "_"
                                 + EntityConstant.Identity.OWNER_IDENTITY_ID,
                    ownerIdentityId.toString());
            }

            YcPage<Operator> page = operatorService.queryOperator(searchParams, pageNumber,
                pageSize, null, IdentityType.MERCHANT);
            List<Operator> list = page.getList();
            String pagetotal = page.getPageTotal() + "";
            String countTotal = page.getCountTotal() + "";

            model.addAttribute("username", userName);
            model.addAttribute("ownerIdentityId", ownerIdentityId);
            model.addAttribute("mlist", list);
            model.addAttribute("page", pageNumber);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("pagetotal", pagetotal);
            model.addAttribute("counttotal", countTotal);
            return PageConstant.PAGE_OPERATOR_MERCHANT_LIST;
        }
        catch (RpcException e)
        {
            logger.debug("[merchantOperatorList()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
        }
        catch (Exception e)
        {
            logger.debug("[merchantOperatorList()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }
}
