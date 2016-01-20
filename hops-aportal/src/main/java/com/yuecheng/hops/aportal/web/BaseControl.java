package com.yuecheng.hops.aportal.web;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import com.yuecheng.hops.identity.entity.operator.Operator;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateModelException;


public class BaseControl
{

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseControl.class);

    protected static void setDefaultStaticModel(Model modelMap, Class[] staticClasses)
    {
        for (Class clz : staticClasses)
        {
            String name = clz.getSimpleName();
            try
            {
                BeansWrapper wrapper = BeansWrapper.getDefaultInstance();

                modelMap.addAttribute(name, wrapper.getStaticModels().get(clz.getName()));
            }
            catch (TemplateModelException ex)
            {
                LOGGER.info("setDefaultStatic[" + name + "] fail");
            }

        }

    }

    public static void setDefaultEnumModel(Model modelMap, Class[] enumClasses)
    {
        for (Class clz : enumClasses)
        {
            String name = clz.getSimpleName();
            try
            {
                BeansWrapper wrapper = BeansWrapper.getDefaultInstance();

                modelMap.addAttribute(name, wrapper.getEnumModels().get(clz.getName()));
            }
            catch (TemplateModelException ex)
            {
                LOGGER.info("setDefaultEnumModel[" + name + "] fail");
            }

        }
    }

    public static Operator getLoginUser()
    {
        try
        {
            Operator operator = (Operator)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
            if (operator != null)
            {
                return operator;
            }
            else
            {
                throw new AuthenticationException("授权未通过，认证用户信息不存在！");
            }
        }
        catch (AuthenticationException ae)
        {
            throw new AuthenticationException("获取认证信息失败！");
        }
    }
}
