package com.yuecheng.hops.aportal.web.identity;


import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.account.entity.role.IdentityAccountRole;
import com.yuecheng.hops.account.service.IdentityAccountRoleService;
import com.yuecheng.hops.aportal.web.BaseControl;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.enump.OperatorType;
import com.yuecheng.hops.common.security.RSAUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.operator.Operator;
import com.yuecheng.hops.privilege.entity.MenuSelect;
import com.yuecheng.hops.privilege.entity.Role;
import com.yuecheng.hops.privilege.service.IdentityRoleQueryService;
import com.yuecheng.hops.privilege.service.RoleMenuQueryService;
import com.yuecheng.hops.privilege.service.RolePrivilegeQueryService;
import com.yuecheng.hops.security.service.SecurityCredentialManagerService;
import com.yuecheng.hops.security.service.SecurityCredentialService;
import com.yuecheng.hops.security.service.SecurityKeystoreService;


/**
 * LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)， 真正登录的POST请求由Filter完成,
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController extends BaseControl
{

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private IdentityRoleQueryService identityRoleQueryService;

    @Autowired
    private RolePrivilegeQueryService rpService;

    @Autowired
    private SecurityCredentialService securityCredentialService;

    @Autowired
    private RoleMenuQueryService roleMenuQueryService;

    @Autowired
    private SecurityCredentialManagerService securityCredentialManagerService;

    @Autowired
    private SecurityKeystoreService securityKeystoreService;

    @Autowired
    private IdentityAccountRoleService identityAccountRoleService;

    @Value("${session.timeout}")
    private int timeout;

    @Autowired
    HttpSession session;

    @RequestMapping(method = RequestMethod.GET)
    public String login(ModelMap model)
    {
        Object userid = session.getAttribute(Constant.Common.LOGIN_NAME_CACHE);
        String username = StringUtil.initString();
        if (userid != null)
        {
            username = (String)userid;
        }
        try
        {
            Object object = session.getAttribute(EntityConstant.Session.LOGIN_SESSION_NAME);
            if (object != null)
            {
                Operator operator = (Operator)object;
                List<Role> roleList = identityRoleQueryService.queryRoleByIdentity(
                    operator.getId(), IdentityType.OPERATOR);
                List<MenuSelect> menuSelectList0 = roleMenuQueryService.queryMenuSelectByParams(
                    Constant.MenuLevel.ZERO, roleList);
                List<MenuSelect> menuSelectList1 = roleMenuQueryService.queryMenuSelectByParams(
                    Constant.MenuLevel.ONE, roleList);
                List<MenuSelect> menuSelectList2 = roleMenuQueryService.queryMenuSelectByParams(
                    Constant.MenuLevel.TWO, roleList);
                List<MenuSelect> menuSelectList3 = roleMenuQueryService.queryMenuSelectByParams(
                    Constant.MenuLevel.THREE, roleList);

                model.addAttribute("menulist0", menuSelectList0);
                model.addAttribute("menulist1", menuSelectList1);
                model.addAttribute("menulist2", menuSelectList2);
                model.addAttribute("menulist3", menuSelectList3);
                return "index/main.ftl";

            }

            Map<String, Object> keyMap = securityKeystoreService.getKeyObjectToMap(Constant.SecurityCredential.RSA_KEY);
            RSAPublicKey publicKey = RSAUtils.getKeyByString(keyMap.get(Constant.RSACacheKey.RSA_PUBLICKEY)
                                                             + "");

            model.addAttribute("modulus", RSAUtils.getModulus(publicKey));
            model.addAttribute("exponent", RSAUtils.getPublicExponent(publicKey));
            model.addAttribute("username", username);
            return "login/login.ftl";
        }
        catch (RpcException e)
        {
            logger.error("[LoginController:login(Get)]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            model.addAttribute("username", username);
            return "login/login.ftl";
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public String dologin(@RequestParam(value = "username", defaultValue = "")
    String username, @RequestParam(value = "rsaPassword", defaultValue = "")
    String rsaPassword, ModelMap model)
    {
        try
        {
            logger.debug("用户登录，用户名：" + username + "密码：" + rsaPassword);
            // 验证参数
            if (StringUtils.isBlank(username) || StringUtils.isBlank(rsaPassword))
            {
                model.addAttribute("errmsg", "用户名或密码不能为空");
                return "login/login.ftl";
            }
            session.setAttribute(Constant.Common.LOGIN_NAME_CACHE, username);
            session.setMaxInactiveInterval(timeout);

            SecurityUtils.getSubject().login(new UsernamePasswordToken(username, rsaPassword));
            Operator operator = (Operator)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
            if (operator != null)
            {
                if (!operator.getOperatorType().equals(OperatorType.MERCHANT_OPERATOR))
                {
                    model.addAttribute("errmsg", "此账号没有权限使用商户管理后台");
                    return "login/login.ftl";
                }

                session.setAttribute(EntityConstant.Session.LOGIN_SESSION_NAME, operator);
                session.setMaxInactiveInterval(timeout);
                // Long accountTypeId, Long identityId, String identityType,
                // String relation, Long transactionNo
                IdentityAccountRole ir = identityAccountRoleService.getIdentityAccountRoleByParams(
                    Constant.AccountType.MERCHANT_DEBIT, operator.getOwnerIdentityId(),
                    IdentityType.MERCHANT.toString(), Constant.Account.ACCOUNT_RELATION_TYPE_OWN,
                    null);
                session.setAttribute(EntityConstant.Account.ACCOUNT_ID, ir.getAccountId());
                List<Role> roleList = identityRoleQueryService.queryRoleByIdentity(
                    operator.getId(), IdentityType.OPERATOR);
                if (roleList == null || roleList.size() <= 0)
                {
                    model.addAttribute("errmsg", "此账号没有分配角色，请联系管理员。");
                    session.setAttribute(EntityConstant.Session.LOGIN_SESSION_NAME, null);
                    return "login/login.ftl";
                }
                List<MenuSelect> menuSelectList0 = roleMenuQueryService.queryMenuSelectByParams(
                    Constant.MenuLevel.ZERO, roleList);
                List<MenuSelect> menuSelectList1 = roleMenuQueryService.queryMenuSelectByParams(
                    Constant.MenuLevel.ONE, roleList);
                List<MenuSelect> menuSelectList2 = roleMenuQueryService.queryMenuSelectByParams(
                    Constant.MenuLevel.TWO, roleList);
                List<MenuSelect> menuSelectList3 = roleMenuQueryService.queryMenuSelectByParams(
                    Constant.MenuLevel.THREE, roleList);

                model.addAttribute("menulist0", menuSelectList0);
                model.addAttribute("menulist1", menuSelectList1);
                model.addAttribute("menulist2", menuSelectList2);
                model.addAttribute("menulist3", menuSelectList3);
                return "index/main.ftl";
            }
            else
            {
                model.addAttribute("errmsg", "用户名或密码不正确");
                session.setAttribute(EntityConstant.Session.LOGIN_SESSION_NAME, null);
                return "login/login.ftl";
            }
        }
        catch (UnknownAccountException uae)
        {
            model.addAttribute("errmsg", "操作失败[" + uae.getLocalizedMessage() + "]");
            return "login/login.ftl";
        }
        catch (IncorrectCredentialsException ice)
        {
            model.addAttribute("errmsg", "操作失败[" + ice.getLocalizedMessage() + "]");
            return "login/login.ftl";
        }
        catch (LockedAccountException lae)
        {
            model.addAttribute("errmsg", "操作失败[" + lae.getLocalizedMessage() + "]");
            return "login/login.ftl";
        }
        catch (RpcException e)
        {
            logger.debug("[LoginController:dologin(GET)]" + e.getMessage());
            model.addAttribute("errmsg", "操作失败[" + e.getMessage() + "]");
            session.setAttribute(EntityConstant.Session.LOGIN_SESSION_NAME, null);
            return "login/login.ftl";
        }
        catch (AuthenticationException ae)
        {
            model.addAttribute("errmsg", "操作失败[" + ae.getLocalizedMessage() + "]");
            session.setAttribute(EntityConstant.Session.LOGIN_SESSION_NAME, null);
            return "login/login.ftl";
        }
        catch (Exception ae)
        {
            logger.debug("[LoginController:dologin(GET)]" + ae.getMessage());
            model.addAttribute("errmsg", "操作失败[" + ae.getLocalizedMessage() + "]");
            session.setAttribute(EntityConstant.Session.LOGIN_SESSION_NAME, null);
            return "login/login.ftl";
        }
    }

    @RequestMapping(value = "loginout", method = RequestMethod.GET)
    public String loginout(ModelMap model)
    {
        try
        {
            logger.debug("用户登出");
            session.setAttribute(EntityConstant.Session.LOGIN_SESSION_NAME, null);
            return "redirect:/login";
        }
        catch (RpcException e)
        {
            logger.error("[LoginController:dologin(Post)]" + e.getMessage());
            session.setAttribute(EntityConstant.Session.LOGIN_SESSION_NAME, null);
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return "login/login.ftl";
        }
    }

}
