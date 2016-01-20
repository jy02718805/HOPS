package com.yuecheng.hops.mportal.web.account;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Maps;
import com.yuecheng.hops.account.entity.AccountType;
import com.yuecheng.hops.account.service.AccountTypeService;
import com.yuecheng.hops.common.Constant;


@Controller
@RequestMapping(value = "/accountType")
public class AccountTypeController
{

    @Autowired
    AccountTypeService accountTypeService;

    /**
     * 进入新建账户类型页面
     * 
     * @return
     */
    @RequestMapping(value = "/addAccountType")
    public String addAccountType()
    {
        return "account/accountTypeRegister";
    }

    /**
     * 新建账户类型
     * 
     * @param at
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/saveAccountType")
    public String saveAccountType(AccountType at, RedirectAttributes redirectAttributes)
    {
        at.setAccountTypeStatus(Constant.AccountType.ENABLE);
        accountTypeService.saveAccountType(at);
        return "redirect:/accountType/accountTypeList";
    }

    /**
     * 进入修改账户类型页面
     * 
     * @return
     */
    @RequestMapping(value = "/toEditAccountType")
    public String toEditAccountType()
    {
        return "account/accountType";
    }

    /**
     * 修改账户类型
     * 
     * @param at
     * @return
     */
    @RequestMapping(value = "/doEditAccountType")
    public String doEditAccountType(AccountType at)
    {
        accountTypeService.editAccountType(at.getAccountTypeId(), at.getAccountTypeName(), at.getAccountTypeStatus(), at.getCcy(), at.getScope(), at.getType(), at.getDirectory(), at.getIdentityType(), at.getTypeModel());
        return "redirect:/accountType/accountTypeList";
    }

    /**
     * 账户类型列表
     * 
     * @return
     */
    @RequestMapping(value = "/accountTypeList")
    public String accountTypeList(Model model)
    {
        List<AccountType> accountTypeList = accountTypeService.getAllAccountType();
        model.addAttribute("accountTypeList", accountTypeList);
        return "account/accountTypeList";
    }

    private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
    static
    {
        sortTypes.put("auto", "自动");
        sortTypes.put("accountId", "标题");
    }

}
