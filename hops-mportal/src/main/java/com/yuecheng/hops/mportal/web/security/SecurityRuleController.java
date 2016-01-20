/**
 * @Title: SecurityRuleController.java
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
import com.yuecheng.hops.mportal.constant.PageConstant;
import com.yuecheng.hops.mportal.web.BaseControl;
import com.yuecheng.hops.security.entity.SecurityCredentialRule;
import com.yuecheng.hops.security.entity.SecurityCredentialType;
import com.yuecheng.hops.security.service.SecurityRuleService;
import com.yuecheng.hops.security.service.SecurityTypeService;


/**
 * 密钥规则
 */
@Controller
@RequestMapping(value = "/securityrule")
public class SecurityRuleController extends BaseControl
{
    @Autowired
    SecurityRuleService securityruleservice;

    @Autowired
    SecurityTypeService securitytypeservice;

    private static final Logger logger = LoggerFactory.getLogger(SecurityRuleController.class);

    @RequestMapping(value = "/securityrulepagelist")
    public String securityRrulePageList(@RequestParam(value = "pageNumber", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PageConstant.LIST_PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = PageConstant.LIST_ORDER_SORT)
    String sortType, @RequestParam(value = "securityrulename", defaultValue = "")
    String securityrulename, @RequestParam(value = "letter", defaultValue = "")
    String letter, @RequestParam(value = "figure", defaultValue = "")
    String figure, @RequestParam(value = "specialcharacter", defaultValue = "")
    String specialcharacter, @RequestParam(value = "status", defaultValue = "")
    String statusstr, ModelMap model)
    {
        try
        {
            Long status = null;
            if (!"".equals(statusstr)) status = Long.valueOf(Long.parseLong(statusstr));
            YcPage<SecurityCredentialRule> page_list = securityruleservice.queryPageSecurityRule(
                securityrulename, letter, figure, specialcharacter, status, pageNumber, pageSize);
            model.addAttribute("securityrulename", securityrulename);
            model.addAttribute("letter", letter);
            model.addAttribute("figure", figure);
            model.addAttribute("specialcharacter", specialcharacter);
            model.addAttribute("status", status);
            model.addAttribute("securityrulelist", page_list.getList());
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("page", Integer.valueOf(pageNumber));
            model.addAttribute("counttotal",
                (new StringBuilder(String.valueOf(page_list.getCountTotal()))).toString());
            model.addAttribute("pagetotal",
                (new StringBuilder(String.valueOf(page_list.getPageTotal()))).toString());
            return "security/securityrule/securityrulepagelist";
        }
        catch (RpcException e)
        {
            logger.debug("[SecurityRuleController: securityRrulePageList()]" + e.getMessage());
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
    @RequestMapping(value = "/addpagesecurityrule")
    public String addPageSecurityRule(ModelMap model)
    {
        try
        {
            return "security/securityrule/addsecurityrule";
        }
        catch (RpcException e)
        {
            logger.debug("[SecurityRuleController: addPageSecurityRule()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 添加提交
     * 
     * @param securityrulename
     * @param letter
     * @param figure
     * @param specialcharacter
     * @param status
     * @param isupperCase
     * @param islowercase
     * @param model
     * @return
     * @see
     */
    @RequestMapping(value = "/addsecurityrule")
    public String addSecurityRule(@RequestParam(value = "securityrulename", defaultValue = "")
    String securityrulename, @RequestParam(value = "letter", defaultValue = "")
    String letter, @RequestParam(value = "figure", defaultValue = "")
    String figure, @RequestParam(value = "specialcharacter", defaultValue = "")
    String specialcharacter, @RequestParam(value = "status", defaultValue = "")
    long status, @RequestParam(value = "isupperCase", defaultValue = "")
    String isupperCase, @RequestParam(value = "islowercase", defaultValue = "")
    String islowercase, ModelMap model)
    {

        
        try
        {
            logger.debug("addSecurityRule-------securityrulename=" + securityrulename);
            
            SecurityCredentialRule securityCredentialRule = securityruleservice.getSecurityRuleByName(securityrulename);
            if (securityCredentialRule != null)
            {
                model.put("message", "操作失败:[密钥规则名称重复!]");
                model.put("canback", true);
                return PageConstant.PAGE_COMMON_NOTIFY;
            }
            
            SecurityCredentialRule sr = new SecurityCredentialRule();
            sr.setSecurityRuleName(securityrulename);
            sr.setLetter(letter);
            sr.setFigure(figure);
            sr.setSpecialCharacter(specialcharacter);
            sr.setStatus(status);
            sr.setIsUpperCase(isupperCase);
            sr.setIsLowercase(islowercase);
            securityruleservice.addSecurityRule(sr);
            return "redirect:/securityrule/securityrulepagelist";
        }
        catch (RpcException e)
        {
            logger.debug("[SecurityRuleController: addSecurityRule()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 跳转修改页面
     * 
     * @param securityruleid
     * @param model
     * @return
     * @see
     */
    @RequestMapping(value = "/editpagesecurityrule")
    public String editPageSecurityRule(@RequestParam(value = "securityruleid", defaultValue = "")
    long securityruleid, ModelMap model)
    {
        try
        {
            SecurityCredentialRule sr = securityruleservice.querySecurityRuleById(securityruleid);
            model.addAttribute("securityrule", sr);
            return "security/securityrule/editsecurityrule";
        }
        catch (RpcException e)
        {
            logger.debug("[SecurityRuleController: editPageSecurityRule()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 修改提交
     * 
     * @param securityruleid
     * @param securityrulename
     * @param letter
     * @param figure
     * @param specialcharacter
     * @param status
     * @param isupperCase
     * @param islowercase
     * @param model
     * @return
     * @see
     */
    @RequestMapping(value = "/editsecurityrule")
    public String editSecurityRule(@RequestParam(value = "securityruleid", defaultValue = "")
    long securityruleid, @RequestParam(value = "securityrulename", defaultValue = "")
    String securityrulename, @RequestParam(value = "letter", defaultValue = "")
    String letter, @RequestParam(value = "figure", defaultValue = "")
    String figure, @RequestParam(value = "specialcharacter", defaultValue = "")
    String specialcharacter, @RequestParam(value = "status", defaultValue = "")
    long status, @RequestParam(value = "isupperCase", defaultValue = "")
    String isupperCase, @RequestParam(value = "islowercase", defaultValue = "")
    String islowercase, ModelMap model)
    {
        

        try
        {
            SecurityCredentialRule securityCredentialRule = securityruleservice.getSecurityRuleByName(securityrulename);
            if (securityCredentialRule != null
                && securityCredentialRule.getSecurityRuleId() != securityruleid)
            {
                model.put("message", "操作失败:[密钥规则名称重复!]");
                model.put("canback", true);
                return PageConstant.PAGE_COMMON_NOTIFY;
            }
            
            SecurityCredentialRule sr = securityruleservice.querySecurityRuleById(securityruleid);
            sr.setSecurityRuleName(securityrulename);
            sr.setLetter(letter);
            sr.setFigure(figure);
            sr.setSpecialCharacter(specialcharacter);
            sr.setStatus(status);
            sr.setIsUpperCase(isupperCase);
            sr.setIsLowercase(islowercase);
            securityruleservice.editSecurityRule(sr);
            return "redirect:/securityrule/securityrulepagelist";
        }
        catch (RpcException e)
        {
            logger.debug("[SecurityRuleController: editSecurityRule()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 删除
     * 
     * @param securityruleid
     * @param model
     * @return
     * @see
     */
    @RequestMapping(value = "/delsecurityrule")
    public String delSecurityRrule(@RequestParam("securityruleid")
    long securityruleid, ModelMap model)
    {
        try
        {
            List<SecurityCredentialType> scList = securitytypeservice.querySecurityTypeByRule(securityruleid);
            if (scList.size() > 0)
            {
                model.put("message", "操作失败: [该规则已被类型使用，请先清除关联关系。]");
                model.put("canback", true);
                return PageConstant.PAGE_COMMON_NOTIFY;
            }
            securityruleservice.delSecurityRule(securityruleid);
            return "redirect:/securityrule/securityrulepagelist";
        }
        catch (RpcException e)
        {
            logger.debug("[SecurityRuleController: delSecurityRrule()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    // 修改状态
    @RequestMapping(value = "/editstatussecurityrule")
    public String editStatusSecurityRule(@RequestParam("securityruleid")
    long securityruleid, @RequestParam("status")
    long status, ModelMap model)
    {
        try
        {
            securityruleservice.updateSecurityRuleStatus(securityruleid, status);
            logger.debug("editStatusSecurityRule-----securityruleid=" + securityruleid
                         + " ;status=" + status);
            return "redirect:/securityrule/securityrulepagelist";
        }
        catch (RpcException e)
        {
            logger.debug("[SecurityRuleController: editStatusSecurityRule()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

}
