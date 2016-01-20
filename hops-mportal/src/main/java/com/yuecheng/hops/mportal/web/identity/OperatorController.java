package com.yuecheng.hops.mportal.web.identity;


import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

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
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.Constant.IdentityConstants;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.common.enump.OperatorType;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.security.RSAUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.IdentityStatus;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.entity.mirror.Person;
import com.yuecheng.hops.identity.entity.operator.Operator;
import com.yuecheng.hops.identity.entity.sp.SP;
import com.yuecheng.hops.identity.service.IdentityService;
import com.yuecheng.hops.identity.service.customer.PersonService;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.identity.service.operator.OperatorPasswordManagement;
import com.yuecheng.hops.identity.service.operator.OperatorService;
import com.yuecheng.hops.identity.service.sp.SpService;
import com.yuecheng.hops.mportal.constant.PageConstant;
import com.yuecheng.hops.mportal.web.BaseControl;
import com.yuecheng.hops.privilege.entity.IdentityRoleSelect;
import com.yuecheng.hops.privilege.service.IdentityRoleQueryService;
import com.yuecheng.hops.privilege.service.IdentityRoleService;
import com.yuecheng.hops.security.entity.SecurityCredential;
import com.yuecheng.hops.security.entity.SecurityCredentialRule;
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
    @Autowired
    private OperatorService operatorService;

    @Autowired
    private OperatorPasswordManagement operatorPasswordManagement;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private IdentityRoleService identityRoleService;

    @Autowired
    private IdentityRoleQueryService iRoleService;

    @Autowired
    private SpService spService;

    @Autowired
    private SecurityCredentialService securityCredentialService;

    @Autowired
    IdentityService identityService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private PersonService personService;

    @Autowired
    private SecurityCredentialManagerService securityCredentialManagerService;

    @Autowired
    private SecurityKeystoreService securityKeystoreService;

    @Autowired
    private SecurityTypeService securityTypeService;

    private org.springside.modules.persistence.SearchFilter.Operator springSideOperator;

    private static Logger logger = LoggerFactory.getLogger(OperatorController.class);

    @RequestMapping(value = "/addMerchantOperator", method = RequestMethod.GET)
    public String addMerchantOperatorView(ModelMap model)
    {
        Map<String, Object> keyMap = securityKeystoreService.getKeyObjectToMap(Constant.SecurityCredential.RSA_KEY);
        RSAPublicKey publicKey = RSAUtils.getKeyByString(keyMap.get(Constant.RSACacheKey.RSA_PUBLICKEY)
                                                         + "");

        model.addAttribute("modulus", RSAUtils.getModulus(publicKey));
        model.addAttribute("exponent", RSAUtils.getPublicExponent(publicKey));
        try
        {
            List<Merchant> merchantList = merchantService.queryAllMerchant(MerchantType.AGENT,
                null);
            model.addAttribute("merchantlist", merchantList);
            return PageConstant.PAGE_MERCHANT_OPERATOR_ADD;
        }
        catch (RpcException e)
        {
            logger.debug("[OperatorController:addMerchantOperatorView()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (Exception e)
        {
            logger.debug("[OperatorController:addMerchantOperatorView()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/addSystemOperator", method = RequestMethod.GET)
    public String addSystemOperatorView(ModelMap model)
    {
        Map<String, Object> keyMap = securityKeystoreService.getKeyObjectToMap(Constant.SecurityCredential.RSA_KEY);
        RSAPublicKey publicKey = RSAUtils.getKeyByString(keyMap.get(Constant.RSACacheKey.RSA_PUBLICKEY)
                                                         + "");

        model.addAttribute("modulus", RSAUtils.getModulus(publicKey));
        model.addAttribute("exponent", RSAUtils.getPublicExponent(publicKey));

        try
        {
            SP sp = spService.getSP();
            model.addAttribute("sp", sp);
            return PageConstant.PAGE_SYSTEM_OPERATOR_ADD;
        }
        catch (RpcException e)
        {
            logger.debug("[OperatorController:addSystemOperatorView()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (Exception e)
        {
            logger.debug("[OperatorController:addSystemOperatorView()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/addMerchantOperator", method = RequestMethod.POST)
    public String addMerchantOperatorDo(@ModelAttribute("operator")
    Operator operator, @RequestParam(value = "type", defaultValue = "")
    String type, ModelMap model)
    {
        try
        {
            Operator operatorIs = operatorService.queryOperatorByOperatorName(operator.getOperatorName());
            if (operatorIs != null)
            {
                model.put("message", "操作失败[操作员名称已存在]");
                model.put("canback", true);
                return PageConstant.PAGE_COMMON_NOTIFY;
            }
            securityCredentialManagerService.checkSecurity(
                Constant.SecurityCredentialType.PASSWORD, operator.getLoginPassword());

            Operator loginPerson = getLoginUser();
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

    @RequestMapping(value = "/addSystemOperator", method = RequestMethod.POST)
    public String addSystemOperatorDo(@ModelAttribute("operator")
    Operator operator, @RequestParam(value = "type", defaultValue = "")
    String type, ModelMap model)
    {

        try
        {
            Operator operatorIs = operatorService.queryOperatorByOperatorName(operator.getOperatorName());
            if (operatorIs != null)
            {
                model.put("message", "操作失败[操作员名称已存在]");
                model.put("canback", true);
                return PageConstant.PAGE_COMMON_NOTIFY;
            }

            securityCredentialManagerService.checkSecurity(
                Constant.SecurityCredentialType.PASSWORD, operator.getLoginPassword());

            Operator loginPerson = getLoginUser();
            Person person = operator.getPerson();
            operator.setOwnerIdentityType(IdentityType.SP);
            operator.setOperatorType(OperatorType.SP_OPERATOR);
            // 设置操作员基本信息
            person.setUpdateName(loginPerson.getOperatorName());
            person.setUpdateTime(new Date());
            person.setCreateTime(new Date());
            person = personService.savePerson(person);
            operator.setPerson(person);
            operator.setIdentityStatus(new IdentityStatus(IdentityConstants.OPERATOR_DISABLE));
            // 设置密钥信息

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
                model.put("next_url", "/Operator/sysOperatorList");
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
            logger.debug("[OperatorController:addSystemOperator()]" + e.getMessage());
            model.put("message", "操作失败 :[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (Exception e)
        {
            logger.debug("[OperatorController:addSystemOperator()]" + e.getMessage());
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
        catch (Exception e)
        {
            logger.debug("[OperatorController:editView()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/tosetrole", method = RequestMethod.GET)
    public String toSetOpertorRole(@RequestParam("oid")
    Long oid,@RequestParam("source")
    String source, ModelMap model)
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
            if ("merchantOperator".equalsIgnoreCase(source))
            {
            	model.put("backUrl", "/Operator/merchantOperatorList");
            }
            else
            {
            	model.put("backUrl", "/Operator/sysOperatorList");
            }
            model.put("rolelist", irSelectlist);
            model.put("operator", operator);
            model.put("source", source);
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
    String roleid,@RequestParam("source")
    String source)
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
            model.put("message", "角色列表不能为空");

        }
        if ("merchantOperator".equalsIgnoreCase(source))
        {
        	model.put("backUrl", "/Operator/merchantOperatorList");
        }
        else
        {
        	model.put("backUrl", "/Operator/sysOperatorList");
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
            Person person = operator.getPerson();
            if (person.getPersonId() != null)
            {
                person = personService.savePerson(person);
            }
            Operator result = operatorService.editOperatorInfo(operator);
            if (result == null)
            {
                model.put("message", "操作失败");
                model.put("canback", true);
            }
            else
            {
                if (result.getOperatorType().equals(OperatorType.MERCHANT_OPERATOR))
                {
                    model.put("message", "修改成功");
                    model.put("canback", false);
                    model.put("next_url", "Operator/merchantOperatorList");
                    model.put("next_msg", "系统操作员列表");
                }
                else
                {
                    model.put("message", "修改成功");
                    model.put("canback", false);
                    model.put("next_url", "Operator/sysOperatorList");
                    model.put("next_msg", "商户操作员列表");
                }
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
        catch (Exception e)
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
            result.put("msg", "id不能为空。");
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
        try
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
        catch (RpcException e)
        {
            logger.debug("[OperatorController:toModifyPassword()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (Exception e)
        {
            logger.debug("[OperatorController:toModifyPassword()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/viewinfo", method = RequestMethod.GET)
    public Object viewinfo(@RequestParam(value = "id")
    String id, ModelMap model)
    {
        try
        {
            Operator op = (Operator)identityService.findIdentityByIdentityId(new Long(id),
                IdentityType.OPERATOR);
            model.addAttribute("operator", op);
            return PageConstant.PAGE_OPERATOR_VIEWINFO;
        }
        catch (RpcException e)
        {
            logger.debug("[OperatorController:viewinfo()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (Exception e)
        {
            logger.debug("[OperatorController:viewinfo()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/modifyPasswordDO", method = RequestMethod.POST)
    public String modifyPassordDo(@RequestParam(value = "id")
    String id, @RequestParam(value = "rsaOldpwd")
    String rsaOldpwd, @RequestParam(value = "rsaNewpwd")
    String rsaNewpwd, ModelMap model)
    {
        try
        {
            if (StringUtils.isBlank(id) || StringUtils.isBlank(rsaNewpwd)
                || StringUtils.isBlank(rsaOldpwd))
            {
                model.put("message", "数据不完整，操作失败");
                model.put("canback", true);
            }
            Operator loginPerson = getLoginUser();
            securityCredentialManagerService.checkSecurity(
                Constant.SecurityCredentialType.PASSWORD, rsaNewpwd);
            Long result = operatorPasswordManagement.resetPassword(new Long(id), rsaOldpwd,
                rsaNewpwd, loginPerson.getOperatorName());

            if (result != null)
            {
                model.put("message", "操作成功");
                model.put("canback", true);
                model.put("next_url", "Operator/sysOperatorList");
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
    String id, ModelMap model)
    {
        try
        {
            Operator loginPerson = getLoginUser();
            logger.info("进入重置密码方法");
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
                    securityTyp != null ? securityTyp.getSecurityTypeId() : null, null);
                @SuppressWarnings("unused")
                String tempPassword = securityCredential.getSecurityValue();
                String msg = "重置密码成功";
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
        catch (RpcException e)
        {
            logger.debug("[OperatorController:resetPassword()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
        }
        catch (Exception e)
        {
            logger.debug("[OperatorController:resetPassword()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;

    }

    @SuppressWarnings("static-access")
    @RequestMapping(value = "/sysOperatorList", method = RequestMethod.GET)
    public String sysOperatorList(@RequestParam(value = "operatorName", defaultValue = "")
    String operatorName, @RequestParam(value = "displayName", defaultValue = "")
    String displayName, @RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PageConstant.LIST_PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = PageConstant.LIST_ORDER_SORT)
    String sortType, ModelMap model, ServletRequest request)
    {
        try
        {
            Map<String, Object> searchParams = new HashMap<String, Object>();
            displayName = new String(displayName.getBytes("ISO-8859-1"), "UTF-8");
            operatorName = new String(operatorName.getBytes("ISO-8859-1"), "UTF-8");
            if (StringUtil.isNotBlank(operatorName))
            {
                searchParams.put(springSideOperator.LIKE + "_"
                                 + EntityConstant.Operator.OPERATOR_NAME, operatorName);
            }
            if (StringUtil.isNotBlank(displayName))
            {
                searchParams.put(springSideOperator.LIKE + "_"
                                 + EntityConstant.Operator.DISPLAY_NAME, displayName);
            }
            SP sp = spService.getSP();
            Long ownerIdentityId = sp.getId();

            if (ownerIdentityId != 0)
            {
                searchParams.put(springSideOperator.EQ + "_"
                                 + EntityConstant.Operator.OWNER_IDENTITY_ID,
                    ownerIdentityId.toString());
            }

            YcPage<Operator> page = operatorService.queryOperator(searchParams, pageNumber,
                pageSize, null, IdentityType.SP);
            List<Operator> list = page.getList();
            if (null != list && list.size() > 0)
            {
            	for (Operator o : list)
            	{
            		Long oid = o.getId();
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
                    String roleName = "";
                    if (null != irSelectlist && irSelectlist.size() > 0)
                    {
                    	for (IdentityRoleSelect i : irSelectlist)
                    	{
                    		if ("0".equals(i.getStatus()))
                    		{
                    			roleName = roleName + i.getRole().getRoleName() + ",";
                    		}
                    	}
                    }
                    o.setRemark(StringUtil.initString());
                    if(roleName.length()>0)
                    {
                        o.setRemark(roleName.substring(0,roleName.length()-1));
                    }
            	}
            }
            
            String pagetotal = page.getPageTotal() + "";
            String countTotal = page.getCountTotal() + "";
            model.addAttribute("mlist", list);
            model.addAttribute("page", pageNumber);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("pagetotal", pagetotal);
            model.addAttribute("counttotal", countTotal);
            model.addAttribute("operatorName", operatorName);
            model.addAttribute("displayName", displayName);
            return PageConstant.PAGE_OPERATOR_SYSTEM_LIST;
        }
        catch (RpcException e)
        {
            logger.debug("[OperatorController:sysOperatorlist()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
        }
        catch (Exception e)
        {
            logger.debug("[OperatorController:sysOperatorlist()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @SuppressWarnings("static-access")
    @RequestMapping(value = "/merchantOperatorList", method = RequestMethod.GET)
    public String merchantOperatorList(@RequestParam(value = "ownerIdentityId", defaultValue = "0")
                                       Long ownerIdentityId,
                                       @RequestParam(value = "operatorName", defaultValue = "")
                                       String operatorName,
                                       @RequestParam(value = "displayName", defaultValue = "")
                                       String displayName,
                                       @RequestParam(value = "page", defaultValue = "1")
                                       int pageNumber,
                                       @RequestParam(value = "pageSize", defaultValue = PageConstant.LIST_PAGE_SIZE)
                                       int pageSize,
                                       @RequestParam(value = "sortType", defaultValue = PageConstant.LIST_ORDER_SORT)
                                       String sortType, ModelMap model, ServletRequest request)
    {
        try
        {
            Map<String, Object> searchParams = new HashMap<String, Object>();
            displayName = new String(displayName.getBytes("ISO-8859-1"), "UTF-8");
            operatorName = new String(operatorName.getBytes("ISO-8859-1"), "UTF-8");
            if (StringUtil.isNotBlank(operatorName))
            {
                searchParams.put(springSideOperator.LIKE + "_"
                                 + EntityConstant.Operator.OPERATOR_NAME, operatorName);
            }
            if (StringUtil.isNotBlank(displayName))
            {
                searchParams.put(springSideOperator.LIKE + "_"
                                 + EntityConstant.Operator.DISPLAY_NAME, displayName);
            }
            if (ownerIdentityId != 0)
            {
                searchParams.put(springSideOperator.EQ + "_"
                                 + EntityConstant.Operator.OWNER_IDENTITY_ID,
                    ownerIdentityId.toString());
            }
            List<Merchant> merchantList = merchantService.queryAllMerchant(MerchantType.AGENT,
                null);

            YcPage<Operator> page = operatorService.queryOperator(searchParams, pageNumber,
                pageSize, null, IdentityType.MERCHANT);
            List<Operator> list = page.getList();
            if (null != list && list.size() > 0)
            {
            	for (Operator o : list)
            	{
            		Long oid = o.getId();
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
                    String roleName = "";
                    if (null != irSelectlist && irSelectlist.size() > 0)
                    {
                    	for (IdentityRoleSelect i : irSelectlist)
                    	{
                    		if ("0".equals(i.getStatus()))
                    		{
                    			roleName = roleName + i.getRole().getRoleName() + ",";
                    		}
                    	}
                    }
                    o.setRemark(StringUtil.initString());
                    if(roleName.length()>0)
                    {
                        o.setRemark(roleName.substring(0,roleName.length()-1));
                    }
            	}
            }
            
            String pagetotal = page.getPageTotal() + "";
            String countTotal = page.getCountTotal() + "";
            model.addAttribute("merchantlist", merchantList);
            model.addAttribute("ownerIdentityId", ownerIdentityId);
            model.addAttribute("mlist", list);
            model.addAttribute("page", pageNumber);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("pagetotal", pagetotal);
            model.addAttribute("counttotal", countTotal);
            model.addAttribute("operatorName", operatorName);
            model.addAttribute("displayName", displayName);
            return PageConstant.PAGE_OPERATOR_MERCHANT_LIST;
        }
        catch (RpcException e)
        {
            logger.debug("[merchantOperatorList()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (Exception e)
        {
            logger.debug("[merchantOperatorList()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 检查登录密码
     * 
     * @param securityRule
     * @param securityValue
     * @return
     */
    public boolean checkSecurity(SecurityCredentialRule securityRule, String securityValue)
    {
        boolean bl = false;
        try
        {

        }
        catch (Exception e)
        {
            logger.error("[OperatorController: checkSecurity(SecurityCredentialRule securityRule, String securityValue)] [异常:"
                         + e.getMessage() + "]");
            return false;
        }
        return bl;
    }
}
