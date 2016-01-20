package com.yuecheng.hops.mportal.web.identity;


/**
 * Customer界面控制层
 * 
 * @author：Jinger
 * @date：2013-10-02
 */
import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.security.RSAUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.AbstractIdentity;
import com.yuecheng.hops.identity.entity.IdentityStatus;
import com.yuecheng.hops.identity.entity.customer.Customer;
import com.yuecheng.hops.identity.entity.mirror.Person;
import com.yuecheng.hops.identity.service.IdentityService;
import com.yuecheng.hops.identity.service.customer.CustomerService;
import com.yuecheng.hops.identity.service.customer.PersonService;
import com.yuecheng.hops.mportal.constant.PageConstant;
import com.yuecheng.hops.mportal.web.BaseControl;
import com.yuecheng.hops.privilege.entity.IdentityRoleSelect;
import com.yuecheng.hops.privilege.service.IdentityRoleQueryService;
import com.yuecheng.hops.privilege.service.IdentityRoleService;
import com.yuecheng.hops.privilege.service.MenuService;
import com.yuecheng.hops.privilege.service.RolePrivilegeQueryService;
import com.yuecheng.hops.security.service.LoginService;
import com.yuecheng.hops.security.service.SecurityCredentialManagerService;
import com.yuecheng.hops.security.service.SecurityKeystoreService;


@Controller
@RequestMapping(value = "/Customer")
public class CustomerController extends BaseControl
{
    @Autowired
    private CustomerService customerService;

    @Autowired
    private PersonService personService;

    @Autowired
    private IdentityRoleService identityRoleService;

    @Autowired
    private RolePrivilegeQueryService rpService;

    @Autowired
    private IdentityRoleQueryService iRoleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private LoginService loginService;
    
    @Autowired
    private SecurityKeystoreService securityKeystoreService;
    
    @Autowired
    private SecurityCredentialManagerService securityCredentialManagerService;
    

    private static Logger logger = LoggerFactory.getLogger(CustomerController.class);

    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 进入角色添加页面
     * 
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addCustomer(ModelMap model)
    {
        try
        {
//            // 获取认证的用户信息
//            Subject currentUser = SecurityUtils.getSubject();
//            // 验证授权信息是否存在
//            currentUser.checkPermission(PermissionConstant.Customer.CUSTOMER_ADD_SHOW);
            
            Map<String, Object> keyMap = securityKeystoreService.getKeyObjectToMap(Constant.SecurityCredential.RSA_KEY);
            RSAPublicKey publicKey = RSAUtils.getKeyByString(keyMap.get(Constant.RSACacheKey.RSA_PUBLICKEY) + "");

            model.addAttribute("modulus", RSAUtils.getModulus(publicKey));
            model.addAttribute("exponent", RSAUtils.getPublicExponent(publicKey));
            return PageConstant.PAGE_CUSTOMER_ADD;
        }
        catch (RpcException e)
        {
            logger.error("[CustomerController:addCustomer(GET)]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        catch (UnauthorizedException ue)
        {
            logger.error("[CustomerController:addCustomer(GET)][" + ue.getMessage() + "]");
            model.addAttribute("message", "对不起，您没有操作权限。");
            model.put("canback", true);
        }
        catch (AuthenticationException ae)
        {
            model.addAttribute("message", "操作失败[" + ae.getMessage() + "]");
            model.put("canback", true);
        }
        catch (Exception ae)
        {
            logger.debug("[CustomerController:addCustomer(GET)]" + ae.getMessage());
            model.addAttribute("message", "操作失败[" + ae.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    /**
     * 添加用户
     * 
     * @param customer
     * @param result
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveCustomer(@ModelAttribute("customer") Customer customer, ModelMap model,
                               BindingResult result)
    {
        try
        {
//            // 获取认证的用户信息
//            Subject currentUser = SecurityUtils.getSubject();
//            // 验证授权信息是否存在
//            currentUser.checkPermission(PermissionConstant.Customer.CUSTOMER_ADD_EXECUTE);
                logger.info("[CustomerController:saveCustomer(" + customer != null ? customer.toString() : null
                                                                                                           + ")]");
            com.yuecheng.hops.identity.entity.operator.Operator loginPerson = BaseControl.getLoginUser();
            Person person = customer.getPerson();
            person.setCreateTime(new Date());
            person.setUpdateName(loginPerson.getOperatorName());
            person.setUpdateTime(new Date());
            person = personService.savePerson(person);
            customer.setPerson(person);
            IdentityStatus identityStatus = new IdentityStatus();
            identityStatus.setStatus(Constant.CustomerStatus.OPEN_STATUS);
            customer.setIdentityStatus(identityStatus);
            customer.setCreateTime(new Date());
            customer.setUpdateName(loginPerson.getOperatorName());
            customer.setUpdateTime(new Date());
            customer.setIdentityType(IdentityType.CUSTOMER);
            securityCredentialManagerService.checkSecurity(
                Constant.SecurityCredentialType.PASSWORD, customer.getLoginPwd());
            
            customer = (Customer)customerService.regist(customer,customer.getLoginPwd(),customer.getPayPwd(), loginPerson.getOperatorName());
            if (customer != null)
            {
                model.put("message", "操作成功");
                model.put("canback", false);
                model.put("next_url", "Customer/list");
                model.put("next_msg", "用户列表");
            }
            else
            {
                model.put("message", "操作失败");
                model.put("canback", true);
            }
        }
        catch (RpcException e)
        {
            logger.error("[CustomerController:saveCustomer(POST)]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        catch (UnauthorizedException ue)
        {
            logger.error("[CustomerController:addCustomer(POST)][" + ue.getMessage() + "]");
            model.addAttribute("message", "对不起，您没有操作权限。");
            model.put("canback", true);
        }
        catch (AuthenticationException ae)
        {
            model.addAttribute("message", "操作失败[" + ae.getMessage() + "]");
            model.put("canback", true);
        }
        catch (Exception ae)
        {
            logger.debug("[CustomerController:addCustomer(POST)]" + ae.getMessage());
            model.addAttribute("message", "操作失败[" + ae.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    /**
     * 查看用户
     * 
     * @param customer
     * @param result
     * @return
     */
    @RequestMapping(value = "/customer_view", method = RequestMethod.GET)
    public String viewCustomer(@RequestParam("id") String id, ModelMap model)
    {
        try
        {
//            // 获取认证的用户信息
//            Subject currentUser = SecurityUtils.getSubject();
//            // 验证授权信息是否存在
//            currentUser.checkPermission(PermissionConstant.Customer.CUSTOMER_VIEW);

            logger.debug("[CustomerController:viewCustomer(" + id + ")]");
            Customer customer = (Customer)identityService.findIdentityByIdentityId(new Long(id),
                IdentityType.CUSTOMER);// customerService.findOne(new Long(id));
            model.addAttribute("customer", customer);
            return PageConstant.PAGE_CUSTOMER_VIEW;
        }
        catch (RpcException e)
        {
                logger.error("[CustomerController:viewCustomer()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        catch (UnauthorizedException ue)
        {
            logger.error("[CustomerController:viewCustomer(GET)][" + ue.getMessage() + "]");
            model.addAttribute("message", "对不起，您没有操作权限。");
            model.put("canback", true);
        }
        catch (AuthenticationException ae)
        {
            logger.error("[CustomerController:viewCustomer(GET)][" + ae.getMessage() + "]");
            model.addAttribute("message", "操作失败[" + ae.getMessage() + "]");
            model.put("canback", true);
        }
        catch (Exception ae)
        {
            logger.debug("[CustomerController:viewCustomer(GET)]" + ae.getMessage());
            model.addAttribute("message", "操作失败[" + ae.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    /**
     * 删除用户
     * 
     * @param customer
     * @param result
     * @return
     */

    @RequestMapping(value = "/customer_delete", method = RequestMethod.GET)
    public String deleteCustomer(@RequestParam("id") String id, ModelMap model)
    {
        try
        {
//            // 获取认证的用户信息
//            Subject currentUser = SecurityUtils.getSubject();
//            // 验证授权信息是否存在
//            currentUser.checkPermission(PermissionConstant.Customer.CUSTOMER_DELETE);
            logger.info("[CustomerController:deleteCustomer(" + id + ")]");
            com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
            Customer customer = (Customer)identityService.findIdentityByIdentityId(new Long(id),
                IdentityType.CUSTOMER);// customerService.findOne(new Long(id));
            IdentityStatus identityStatus = new IdentityStatus();
            identityStatus.setStatus(Constant.IdentityStatus.DELETE_STATUS);
            customer.setIdentityStatus(identityStatus);
            customer.setIdentityType(IdentityType.CUSTOMER);
            identityService.saveIdentity(customer, loginPerson.getOperatorName());// customerService.saveCustomer(customer);
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "Customer/list");
            model.put("next_msg", "用户列表");
        }
        catch (RpcException e)
        {
            logger.error("[CustomerController:deleteCustomer()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        catch (UnauthorizedException ue)
        {
            logger.error("[CustomerController:deleteCustomer(GET)][" + ue.getMessage() + "]");
            model.addAttribute("message", "对不起，您没有操作权限。");
            model.put("canback", true);
        }
        catch (AuthenticationException ae)
        {
            logger.error("[CustomerController:deleteCustomer(GET)][" + ae.getMessage() + "]");
            model.addAttribute("message", "操作失败[" + ae.getMessage() + "]");
            model.put("canback", true);
        }
        catch (Exception ae)
        {
            logger.debug("[CustomerController:deleteCustomer(GET)]" + ae.getMessage());
            model.addAttribute("message", "操作失败[" + ae.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    /**
     * 进入用户角色编辑界面
     * 
     * @param customer
     * @param result
     * @return
     */
    @RequestMapping(value = "/addCustomerRole", method = RequestMethod.GET)
    public String addCustomerRole(@RequestParam("id") String ids, ModelMap model)
    {
        try
        {
//            // 获取认证的用户信息
//            Subject currentUser = SecurityUtils.getSubject();
//            // 验证授权信息是否存在
//            currentUser.checkPermission(PermissionConstant.Customer.CUSTOMER_ADDROLES_SHOW);
            logger.info("[CustomerController:addCustomerRole(" + ids + ")]");
            Long id = new Long(ids);
            Customer customer = (Customer)identityService.findIdentityByIdentityId(new Long(id),
                IdentityType.CUSTOMER);// customerService.findOne(id);
            List<IdentityRoleSelect> irSelectlist = iRoleService.queryIRSelectByRoleType(id,
                IdentityType.CUSTOMER, Constant.RoleType.ROLE_TYPE_CUSTOMER);
            model.addAttribute("customer", customer);
            model.addAttribute("roleList", irSelectlist);
            return PageConstant.PAGE_CUSTOMER_ADDROLE;
        }
        catch (RpcException e)
        {
            logger.error("[CustomerController:addCustomerRole(GET)]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        catch (UnauthorizedException ue)
        {
            logger.error("[CustomerController:addCustomerRole(GET)][" + ue.getMessage() + "]");
            model.addAttribute("message", "对不起，您没有操作权限。");
            model.put("canback", true);
        }
        catch (AuthenticationException ae)
        {
            logger.error("[CustomerController:addCustomerRole(GET)][" + ae.getMessage() + "]");
            model.addAttribute("message", "操作失败[" + ae.getMessage() + "]");
            model.put("canback", true);
        }
        catch (Exception ae)
        {
            logger.debug("[CustomerController:addCustomerRole(GET)]" + ae.getMessage());
            model.addAttribute("message", "操作失败[" + ae.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    /**
     * 用户分配角色
     * 
     * @param customer
     * @param result
     * @return
     */
    @RequestMapping(value = "/addCustomerRole", method = RequestMethod.POST)
    public String addCustomerRoleDo(@RequestParam("cid") String cid,
                                    @RequestParam("roleid") String roleid, ModelMap model)
    {
        try
        {
//            // 获取认证的用户信息
//            Subject currentUser = SecurityUtils.getSubject();
//            // 验证授权信息是否存在
//            currentUser.checkPermission(PermissionConstant.Customer.CUSTOMER_ADDROLES_EXECUTE);
                logger.info("[CustomerController:addCustomerRoleDo(" + cid + "," + roleid + ")]");
            com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
            if (StringUtil.isNotBlank(cid))
            {
                if (StringUtil.isNotBlank(roleid))
                {
                    String[] roleidlist = roleid.split(Constant.StringSplitUtil.DECODE);
                    String result = identityRoleService.saveIdentityRoleList(roleidlist, new Long(
                        cid), IdentityType.CUSTOMER, loginPerson.getOperatorName());
                    if (result != null)
                    {
                        model.put("message", "操作成功");
                        model.put("canback", false);
                        model.put("next_url", "Customer/list");
                        model.put("next_msg", "用户列表");
                    }
                    else
                    {
                        model.put("message", "操作失败");
                        model.put("canback", true);
                    }
                }
            }
        }
        catch (RpcException e)
        {
            logger.error("[CustomerController:addCustomerRoleDo(POST)]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        catch (UnauthorizedException ue)
        {
            logger.error("[CustomerController:addCustomerRoleDo(POST)][" + ue.getMessage()+ "]");
            model.addAttribute("message", "对不起，您没有操作权限。");
            model.put("canback", true);
        }
        catch (AuthenticationException ae)
        {
            logger.error("[CustomerController:addCustomerRoleDo(POST)][" + ae.getMessage()+ "]");
            model.addAttribute("message", "操作失败[" + ae.getMessage() + "]");
            model.put("canback", true);
        }
        catch (Exception ae)
        {
            logger.debug("[CustomerController:addCustomerRoleDo(POST)]" + ae.getMessage());
            model.addAttribute("message", "操作失败[" + ae.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    /**
     * 进入用户列表页面
     * 
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listdCustomer(@RequestParam(value = "person.userName", defaultValue = "") String userName,
                                @RequestParam(value = "person.displayName", defaultValue = "") String displayName,
                                @RequestParam(value = "person.sex", defaultValue = "") String sex,
                                @RequestParam(value = "page", defaultValue = "1") int pageNumber,
                                @RequestParam(value = "pageSize", defaultValue = PageConstant.LIST_PAGE_SIZE) int pageSize,
                                @RequestParam(value = "sortType", defaultValue = PageConstant.LIST_ORDER_SORT) String sortType,
                                ModelMap model, ServletRequest request)
    {
        try
        {
            userName = new String(userName.getBytes("ISO-8859-1"), "UTF-8");
            displayName = new String(displayName.getBytes("ISO-8859-1"), "UTF-8");
                logger.info("[CustomerController:listdCustomer(" + userName + "," + displayName
                            + "," + sex + ")]");
            Map<String, Object> searchParams = new HashMap<String, Object>();
            searchParams.put(Operator.EQ + "_" + EntityConstant.Customer.STATUS,
                Constant.CustomerStatus.OPEN_STATUS);
            if (StringUtil.isNotBlank(userName))
            {
                searchParams.put(Operator.EQ + "_" + EntityConstant.Customer.CUSTOMER_NAME, userName);
            }
            if (StringUtil.isNotBlank(displayName))
            {
                searchParams.put(Operator.EQ + "_" + EntityConstant.Customer.DISPLAY_NAME,
                    displayName);
            }
            if (StringUtil.isNotBlank(sex))
            {
                searchParams.put(Operator.EQ + "_" + EntityConstant.Customer.SEX, sex);
            }
            BSort bsort = new BSort(BSort.Direct.DESC, EntityConstant.Customer.CREATE_TIME);
            YcPage<AbstractIdentity> page_list = (YcPage<AbstractIdentity>)identityService.queryIdentity(
                searchParams, pageNumber, pageSize, bsort, IdentityType.CUSTOMER);// customerService.queryCustomer(searchParams,pageNumber,pageSize,bsort);
            List<AbstractIdentity> list = page_list.getList();
            String pagetotal = page_list.getPageTotal() + "";
            String countTotal = page_list.getCountTotal() + "";
            model.addAttribute("mlist", list);
            model.addAttribute("page", pageNumber);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("pagetotal", pagetotal);
            model.addAttribute("counttotal", countTotal);
            model.addAttribute("userName", userName);
            model.addAttribute("displayName", displayName);
            model.addAttribute("sex", sex);

            // 根据用户名获取该用户的角色
            return PageConstant.PAGE_CUSTOMER_LIST;
        }
        catch (RpcException e)
        {
            logger.error("[CustomerController:listdCustomer()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        // catch (UnauthorizedException ue) {
        // if (logger.isErrorEnabled()) {
        // logger.error("[CustomerController:listdCustomer()]["+ue.getMessage()+"]");
        // }
        // model.addAttribute("message", "对不起，您没有操作权限。");
        // model.put("canback", true);
        // }catch (AuthenticationException ae) {
        // model.addAttribute("message", "操作失败["+ae.getMessage()+"]");
        // model.put("canback", true);
        // }
        catch (Exception ae)
        {
            logger.debug("[CustomerController:listdCustomer()]" + ae.getMessage());
            model.addAttribute("message", "操作失败[" + ae.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    /**
     * 进入分配角色
     * 
     * @param customer
     * @param result
     * @return
     */
    @RequestMapping(value = "/customer_edit", method = RequestMethod.GET)
    public String editCustomer(@RequestParam("id") String id, ModelMap model)
    {
        try
        {
            // //获取认证的用户信息
            // Subject currentUser = SecurityUtils.getSubject();
            // //验证授权信息是否存在
            // currentUser.checkPermission(PermissionConstant.Customer.CUSTOMER_EDIT_SHOW);
            
            Map<String, Object> keyMap = securityKeystoreService.getKeyObjectToMap(Constant.SecurityCredential.RSA_KEY);
            RSAPublicKey publicKey = RSAUtils.getKeyByString(keyMap.get(Constant.RSACacheKey.RSA_PUBLICKEY) + "");
            logger.info("[CustomerController:editCustomer(" + id + ")]");
            Customer customer = (Customer)identityService.findIdentityByIdentityId(new Long(id),
                IdentityType.CUSTOMER);// customerService.findOne(new Long(id));
            model.addAttribute("modulus", RSAUtils.getModulus(publicKey));
            model.addAttribute("exponent", RSAUtils.getPublicExponent(publicKey));
            model.addAttribute("customer", customer);
            return PageConstant.PAGE_CUSTOMER_EDIT;
        }
        catch (RpcException e)
        {
            logger.error("[CustomerController:editCustomer(GET)]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        catch (UnauthorizedException ue)
        {
            logger.error("[CustomerController:editCustomer(GET)][" + ue.getMessage() + "]");
            model.addAttribute("message", "对不起，您没有操作权限。!!!");
            model.put("canback", true);
        }
        catch (AuthenticationException ae)
        {
            logger.error("[CustomerController:editCustomer(GET)][" + ae.getMessage() + "]");
            model.addAttribute("message", "操作失败[" + ae.getMessage() + "]");
            model.put("canback", true);
        }
        catch (Exception ae)
        {
            logger.debug("[CustomerController:editCustomer(GET)]" + ae.getMessage());
            model.addAttribute("message", "操作失败[" + ae.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    /**
     * 编辑用户后保存
     * 
     * @param customer
     * @param result
     * @return
     */

    @RequestMapping(value = "/customer_edit", method = RequestMethod.POST)
    public String editCustomerDo(@ModelAttribute("customer") Customer customer, ModelMap model)
    {
        try
        {
//            // 获取认证的用户信息
//            Subject currentUser = SecurityUtils.getSubject();
//            // 验证授权信息是否存在
//            currentUser.checkPermission(PermissionConstant.Customer.CUSTOMER_EDIT_EXECUTE);
            logger.info("[CustomerController:editCustomerDo(" + customer != null ? customer.toString() : null
                                                                                                             + ")]");
            com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
            Person person = customer.getPerson();
            Person person1 = personService.findOne(person.getPersonId());
            person.setCreateTime(person1.getCreateTime());
            person = personService.savePerson(person);
            Customer customer1 = (Customer)identityService.findIdentityByIdentityId(
                customer.getId(), IdentityType.CUSTOMER);// customerService.findOne(customer.getId());
            customer.setCreateTime(customer1.getCreateTime());
            customer.setIdentityStatus(customer1.getIdentityStatus());
            customer.setUpdateTime(new Date());
            customer.setUpdateName(loginPerson.getOperatorName());
            customer.setIdentityType(IdentityType.CUSTOMER);
            customer = (Customer)identityService.saveIdentity(customer, loginPerson.getOperatorName());// customerService.saveCustomer(customer);
            if (customer != null)
            {
                model.put("message", "操作成功");
                model.put("canback", false);
                model.put("next_url", "Customer/list");
                model.put("next_msg", "用户列表");
            }
            else
            {
                model.put("message", "操作失败");
                model.put("canback", true);
            }
        }
        catch (RpcException e)
        {
            logger.error("[CustomerController:editCustomerDo()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        catch (UnauthorizedException ue)
        {
            logger.error("[CustomerController:editCustomer(POST)][" + ue.getMessage() + "]");
            model.addAttribute("message", "对不起，您没有操作权限。!!!");
            model.put("canback", true);
        }
        catch (AuthenticationException ae)
        {
            logger.error("[CustomerController:editCustomer(POST)][" + ae.getMessage() + "]");
            model.addAttribute("message", "操作失败[" + ae.getMessage() + "]");
            model.put("canback", true);
        }
        catch (Exception ae)
        {
            logger.debug("[CustomerController:editCustomer(POST)]" + ae.getMessage());
            model.addAttribute("message", "操作失败[" + ae.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }
}
