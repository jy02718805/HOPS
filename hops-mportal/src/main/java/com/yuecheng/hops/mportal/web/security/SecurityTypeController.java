/**
 * @Title: SecurityTypeController.java
 * @Package com.yuecheng.hops.mportal.web.identity
 * @Description: TODO Copyright: Copyright (c) 2011 Company:湖南跃程网络科技有限公司
 * @author 肖进
 * @date 2014年9月2日 下午5:35:35
 * @version V1.0
 */

package com.yuecheng.hops.mportal.web.security;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.mportal.constant.PageConstant;
import com.yuecheng.hops.mportal.web.BaseControl;
import com.yuecheng.hops.security.entity.SecurityCredential;
import com.yuecheng.hops.security.entity.SecurityCredentialRule;
import com.yuecheng.hops.security.entity.SecurityCredentialType;
import com.yuecheng.hops.security.service.SecurityCredentialService;
import com.yuecheng.hops.security.service.SecurityRuleService;
import com.yuecheng.hops.security.service.SecurityTypeService;


/**
 * 密钥类型
 */
@Controller
@RequestMapping(value = "/securitytype")
public class SecurityTypeController extends BaseControl
{
    @Autowired
    SecurityTypeService securitytypeservice;

    @Autowired
    SecurityRuleService securityRuleservice;

    @Autowired
    SecurityCredentialService securityCredentialService;

    private static final Logger logger = LoggerFactory.getLogger(SecurityTypeController.class);

    @RequestMapping(value = "/securitytypepagelist")
    public String securityRtypePageList(@RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = PageConstant.LIST_ORDER_SORT)
    String sortType, @RequestParam(value = "securitytypename", defaultValue = "")
    String securitytypename, @RequestParam(value = "modeltype", defaultValue = "")
    String modeltype, @RequestParam(value = "encrypttype", defaultValue = "")
    String encrypttype, @RequestParam(value = "status", defaultValue = "")
    String statusstr, @RequestParam(value = "securityruleid", defaultValue = "")
    String securityruleids, ModelMap model)
    {
        try
        {
            Long status = null;
            if (StringUtil.isNotBlank(statusstr))
            {
                status = Long.valueOf(statusstr);
            }
            Long securityruleid = null;
            if (StringUtil.isNotBlank(securityruleids))
            {
                securityruleid = Long.valueOf(securityruleids);
            }
            YcPage<SecurityCredentialType> page_list = securitytypeservice.queryPageSecurityType(
                securitytypename, modeltype, encrypttype, securityruleid, status, pageNumber,
                pageSize);

            List<SecurityCredentialRule> securityRuleList = securityRuleservice.queryAllDelStatusSecurityRule();
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("securitytypename", securitytypename);
            model.addAttribute("modeltype", modeltype);
            model.addAttribute("encrypttype", encrypttype);
            model.addAttribute("securityruleid", securityruleid);
            model.addAttribute("status", status);
            model.addAttribute("securitytypelist", page_list.getList());
            model.addAttribute("securityRuleList", securityRuleList);
            model.addAttribute("page", Integer.valueOf(pageNumber));
            model.addAttribute("counttotal",
                (new StringBuilder(String.valueOf(page_list.getCountTotal()))).toString());
            model.addAttribute("pagetotal",
                (new StringBuilder(String.valueOf(page_list.getPageTotal()))).toString());
            return "security/securitytype/securitytypepagelist";
        }
        catch (RpcException e)
        {
            logger.debug("[SecurityTypeController: securityRtypePageList()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 跳转添加页面
     * 
     * @param model
     * @return
     * @see
     */
    @RequestMapping(value = "/addpagesecuritytype")
    public String addPageSecurityType(ModelMap model)
    {
        try
        {
            List<SecurityCredentialRule> securityRuleList = securityRuleservice.queryAllDelStatusSecurityRule();
            model.addAttribute("securityRuleList", securityRuleList);
            return "security/securitytype/addsecuritytype";
        }
        catch (RpcException e)
        {
            logger.debug("[SecurityTypeController: addPageSecurityType()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 添加提交
     * 
     * @param securitytypename
     * @param modeltype
     * @param encrypttype
     * @param minlength
     * @param maxlength
     * @param validity
     * @param status
     * @param identitytype
     * @param securityruleid
     * @param model
     * @return
     * @see
     */
    @RequestMapping(value = "/addsecuritytype")
    public String addSecurityType(@RequestParam("securitytypename")
    String securitytypename, @RequestParam("modeltype")
    String modeltype, @RequestParam("encrypttype")
    String encrypttype, @RequestParam(value = "minlength", defaultValue = "0")
    String minlength, @RequestParam(value = "maxlength", defaultValue = "0")
    String maxlength, @RequestParam(value = "validity", defaultValue = "0")
    String validity, @RequestParam(value = "status", defaultValue = "0")
    String status, @RequestParam("identitytype")
    String identitytype, @RequestParam(value = "securityruleid", defaultValue = "0")
    String securityruleid, ModelMap model)
    {

       
        try
        {
            SecurityCredentialType securityCredentialType = securitytypeservice.querySecurityTypeByTypeName(securitytypename);

            if (securityCredentialType != null)
            {
                model.put("message", "操作失败:[密钥类型名称重复!]");
                model.put("canback", true);
                return PageConstant.PAGE_COMMON_NOTIFY;
            }

            
            SecurityCredentialType st = new SecurityCredentialType();
            st.setSecurityTypeName(securitytypename);
            st.setModelType(modeltype);
            st.setEncryptType(encrypttype);
            st.setMinLength(Long.parseLong(minlength));
            st.setMaxLength(Long.parseLong(maxlength));
            st.setValidity(Long.parseLong(validity));
            st.setStatus(Long.parseLong(status));
            st.setIdentityType(identitytype);
            SecurityCredentialRule securityRule = securityRuleservice.querySecurityRuleById(Long.parseLong(securityruleid));
            st.setSecurityRule(securityRule);

            securitytypeservice.addSecurityType(st);
            return "redirect:/securitytype/securitytypepagelist";
        }
        catch (RpcException e)
        {
            logger.debug("[SecurityTypeController: addSecurityType()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 跳转修改页面
     * 
     * @param securitytypeid
     * @param model
     * @return
     * @see
     */
    @RequestMapping(value = "/editpagesecuritytype")
    public String editPageSecurityType(@RequestParam("securitytypeid")
    long securitytypeid, ModelMap model)
    {
        try
        {
            SecurityCredentialType st = securitytypeservice.getSecurityType(securitytypeid);
            List<SecurityCredentialRule> securityRuleList = securityRuleservice.queryAllDelStatusSecurityRule();
            model.addAttribute("securityRuleList", securityRuleList);
            model.addAttribute("securitytype", st);
            return "security/securitytype/editsecuritytype";
        }
        catch (RpcException e)
        {
            logger.debug("[SecurityTypeController: editPageSecurityType()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 修改提交
     * 
     * @param securitytypeid
     * @param securitytypename
     * @param modeltype
     * @param encrypttype
     * @param minlength
     * @param maxlength
     * @param validity
     * @param status
     * @param identitytype
     * @param securityruleid
     * @param model
     * @return
     * @see
     */
    @RequestMapping(value = "/editsecuritytype")
    public String editSecurityType(@RequestParam("securitytypeid")
    long securitytypeid, @RequestParam("securitytypename")
    String securitytypename, @RequestParam("modeltype")
    String modeltype, @RequestParam("encrypttype")
    String encrypttype, @RequestParam(value = "minlength", defaultValue = "0")
    String minlength, @RequestParam(value = "maxlength", defaultValue = "0")
    String maxlength, @RequestParam(value = "validity", defaultValue = "0")
    String validity, @RequestParam(value = "status", defaultValue = "0")
    String status, @RequestParam("identitytype")
    String identitytype, @RequestParam(value = "securityruleid", defaultValue = "0")
    String securityruleid, ModelMap model)
    {
        
        try
        {
            SecurityCredentialType securityCredentialType = securitytypeservice.querySecurityTypeByTypeName(securitytypename);

            if (securityCredentialType != null
                && securityCredentialType.getSecurityTypeId() != securitytypeid)
            {
                model.put("message", "操作失败:[密钥类型名称重复!]");
                model.put("canback", true);
                return PageConstant.PAGE_COMMON_NOTIFY;
            }
            
            SecurityCredentialType st = securitytypeservice.getSecurityType(securitytypeid);
            st.setSecurityTypeName(securitytypename);
            st.setModelType(modeltype);
            st.setEncryptType(encrypttype);
            st.setMinLength(Long.parseLong(minlength));
            st.setMaxLength(Long.parseLong(maxlength));
            st.setValidity(Long.parseLong(validity));
            st.setStatus(Long.parseLong(status));
            st.setIdentityType(identitytype);
            SecurityCredentialRule securityRule = securityRuleservice.querySecurityRuleById(Long.parseLong(securityruleid));
            st.setSecurityRule(securityRule);
            securitytypeservice.editSecurityType(st);
            return "redirect:/securitytype/securitytypepagelist";
        }
        catch (RpcException e)
        {
            logger.debug("[SecurityTypeController: editSecurityType()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 删除
     * 
     * @param securitytypeid
     * @param model
     * @return
     * @see
     */
    @RequestMapping(value = "/delsecuritytype")
    public String delSecurityRtype(@RequestParam("securitytypeid")
    long securitytypeid, ModelMap model)
    {

        try
        {
            List<SecurityCredential> sclistCredentials = securityCredentialService.getSecurityCredentialByType(securitytypeid);

            if (sclistCredentials.size() > 0)
            {
                model.put("message", "操作失败:[该类型已被密钥使用，请先清除关联关系。]");
                model.put("canback", true);
                return PageConstant.PAGE_COMMON_NOTIFY;
            }

            securitytypeservice.delSecurityType(securitytypeid);
            return "redirect:/securitytype/securitytypepagelist";
        }
        catch (RpcException e)
        {
            logger.debug("[SecurityTypeController: delSecurityRtype()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 修改状态
     * 
     * @param securitytypeid
     * @param status
     * @param model
     * @return
     * @see
     */
    @RequestMapping(value = "/editstatussecuritytype")
    public String editStatusSecurityType(@RequestParam("securitytypeid")
    long securitytypeid, @RequestParam("status")
    long status, ModelMap model)
    {
        try
        {
            securitytypeservice.updateSecurityTypeStatus(securitytypeid, status);
            return "redirect:/securitytype/securitytypepagelist";
        }
        catch (RpcException e)
        {
            logger.debug("[SecurityTypeController: editStatusSecurityType()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

}
